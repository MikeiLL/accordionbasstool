/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

/**
 *
 * @author Ilya
 */
public class ParsedChordDef
{

  public enum BassSetting
  {

    NoLowestAllowed,
    NotLowestBass,
    LowestBass,
  }
  public String namePlain;
  public String nameHtml;
  public String detail;

  public final Note rootNote;
  public final Note addedBassNote;
  public final Chord chord;
  //private final RegistryChordDef registryDef;
  public final RelChord relChord;
  //public final short regRow, regCol;
  public final BassSetting bassSetting;

  public static ParsedChordDef getDefaultChordDef()
  {
    return new ParsedChordDef(new Note(), null, new RelChord(), BassSetting.NotLowestBass);
  }

  public ParsedChordDef(Note root)
  {
    this(new Chord(root, false));
  }

  public ParsedChordDef(Chord achord)
  {
    rootNote = achord.getRootNote();
    addedBassNote = null; //assume none
    chord = achord;

    if (achord.isSingleNote()) {
      namePlain = achord.toString();
      nameHtml = chord.toHtmlString();
    } else {
      namePlain = "[" + chord.toString() + "]";
      nameHtml = chord.toHtmlString();
    }

    relChord = new RelChord(chord);
    detail = "";
    bassSetting = BassSetting.NotLowestBass;
  }

  public ParsedChordDef(Note root,
          Note addedBass,
          RelChord pickedChord,
          BassSetting addedBassLowest)
  {
    rootNote = root;

    relChord = (pickedChord != null ? pickedChord : new RelChord());

    addedBassNote = addedBass;

    Interval[] ivals;

    {
      RegistryChordDef origDef = relChord.getOrigDef();

      if (origDef == null) {
        Chord simpleChord = relChord.buildChord(root);
        ivals = simpleChord.extractInterval();
      } else {
        ivals = origDef.ivals;
      }
    }

    Chord aChord = new Chord(rootNote, ivals,
            addedBassNote, (addedBassLowest == BassSetting.LowestBass));

    if (addedBassLowest == BassSetting.NoLowestAllowed) {
      chord = aChord.getUndupedChord();
    } else {
      chord = aChord;
    }
    
    bassSetting = addedBassLowest;

    updateStrings();
  }

  public void updateStrings()
  {
    String tempAbbrevHtml;
    String tempAbbrevPlain;
    String tempDet;

    {
      RegistryChordDef origDef = relChord.getOrigDef();

      // If custom chord
      if (origDef == null) {
        Chord simpleChord = relChord.buildChord(rootNote);

        //tempAbbrevHtml = rootNote.toString(true) + relChord.toString("+");
        //tempAbbrevPlain = rootNote.toString(false) + relChord.toString("+");
        tempAbbrevHtml = simpleChord.toHtmlString();
        tempAbbrevPlain = "[" + simpleChord.toString() + "]";
        tempDet = "";
      } else {

        tempAbbrevHtml = rootNote.toString(true) + origDef.abbrevHtml;

        tempAbbrevPlain = rootNote.toString();

        if (origDef.ivals.length > 0) {
          tempAbbrevPlain += origDef.abbrevPlain;
        }

        tempDet = origDef.name;
      }
    }

    // -- Set HTML Abbrev
    String html = tempAbbrevHtml;

    if (addedBassNote != null) {
      html += "/" + addedBassNote.toString(true);
    }
    nameHtml = html;

    // -- Set Plain Abbrev

    String plain = tempAbbrevPlain;

    if (addedBassNote != null) {
      plain += "/" + addedBassNote.toString();
    }
    namePlain = plain;

    // -- Set Name
    String det = tempDet;

    if ((addedBassNote != null) && !det.isEmpty()) {
      det += " over " + addedBassNote.toString(true);
    }
    detail = det;
  }

  
  public ParsedChordDef transposeBy(Interval ival)
  {
    Note newAddedBass = null;

    if (addedBassNote != null) {
      newAddedBass = addedBassNote.add(ival);
    }

    if (relChord == null) {
      return new ParsedChordDef(chord.transposeBy(ival));
    } else {
      return new ParsedChordDef(rootNote.add(ival), newAddedBass, this.relChord, this.bassSetting);
    }
  }
}
