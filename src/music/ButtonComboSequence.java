package music;

import java.util.Enumeration;
import java.util.Vector;


public class ButtonComboSequence implements Cloneable, CollSequence<ButtonCombo>
{
	private final Vector<ButtonCombo> combos;
	private final BassBoard board;
	
	// Cached heuristic of this combo seq
	private int heur = 0;

  private short preferredRank = 0;
  private short extraneous = 0;

  public int iconIndex;
	
	public ButtonComboSequence(BassBoard theBoard)
	{
		combos = new Vector<ButtonCombo>();
		board = theBoard;
	}
	
	private ButtonComboSequence(Vector<ButtonCombo> copyCombos,
								BassBoard theBoard, short prefRank)
	{
		combos = copyCombos;
		board = theBoard;
    preferredRank = prefRank;
	}
	
  @Override
	public ButtonCombo getCombo(int num)
	{
		return combos.elementAt(num);
	}
	
  @Override
	public int getNumCombos()
	{
		return combos.size();
	}

  public BassBoard getBoard()
  {
    return board;
  }
	
	public void add(ButtonCombo newCombo)
	{
		combos.add(newCombo);
		heur = 0;
    if (newCombo.preferred) {
      preferredRank++;
    }
    if (newCombo.extraneous) {
      this.extraneous++;
    }
	}

  @Override
  public int getHeur()
  {
    if (heur == 0)
      return evalHeuristic();
    else
      return heur;
  }

  @Override
	public String toString()
	{
		String str = "Heur: " + evalHeuristic() + " Seq: ";
		for (int i = 0; i < combos.size(); i++)
		{
			str += combos.elementAt(i).toString() + ", ";
		}
		
		return str;
	}
	
  @Override
	public ButtonComboSequence clone()
	{
		ButtonComboSequence copy = 
			new ButtonComboSequence((Vector<ButtonCombo>)combos.clone(), board, preferredRank);
		
		return copy;
	}

  public int getPrefRank()
  {
    return preferredRank;
  }

  public boolean getExtraneous()
  {
    return (extraneous > 0);
  }

  public void markExtraneous()
  {
    extraneous++;
  }

  public int debugForceHeur()
  {
    heur = 0;
    return evalHeuristic();
  }
	
	int evalHeuristic()
	{
		if (heur != 0)
		{
			return heur;
		}
		
		Enumeration<ButtonCombo> combosList = combos.elements();
		
		ButtonCombo prevCombo = null;
		int dist = 0;
		
		GeoPos center = GeoPos.zero();
		GeoPos minP = GeoPos.maxPos();
		GeoPos maxP = GeoPos.minPos();
		
		ButtonCombo firstCombo = null;

    final BassBoard.Pos boardCenter = board.getCenter();
    GeoPos geoCenter = new GeoPos(boardCenter, boardCenter);
		
		while (combosList.hasMoreElements())
		{
			ButtonCombo combo = combosList.nextElement();
			
			dist += combo.evalHeur(geoCenter);
      maxP.max(combo.boundsMax);
      minP.min(combo.boundsMin);
			
			if (prevCombo != null)
			{
				dist += combo.center.manDistTo(prevCombo.center) * 20;
			}
			else
			{
				firstCombo = combo;
			}
			
			prevCombo = combo;
		}
		
		// Total row and col range
		maxP = maxP.subtract(minP);
		dist += maxP.absValue();
		
		// Last to first button distance
		//dist += prevCombo.distanceTo(firstCombo);
		
		//dist += (meanR / combos.size());
		//dist += (meanC / combos.size());
		
		heur = dist;
		
		return heur;
	}

  @Override
  public boolean equals(Object obj)
  {
    ButtonComboSequence seq = (ButtonComboSequence)obj;

    if (getNumCombos() != seq.getNumCombos()) {
      return false;
    }

    if (getHeur() != seq.getHeur()) {
      return false;
    }

    for (int i = 0; i < combos.size(); i++) {
      if (!combos.elementAt(i).equals(seq.combos.elementAt(i))) {
        return false;
      }
    }

    return true;
  }
}