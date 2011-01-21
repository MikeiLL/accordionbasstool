/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TabButtonClicker.java
 *
 * Created on Dec 24, 2010, 8:19:14 PM
 */
package render;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import music.ButtonCombo;
import music.Chord;
import music.ParsedChordDef;

/**
 *
 * @author Ilya
 */
public class TabButtonClicker extends ToolPanel
{

  /** Creates new form TabButtonClicker */
  public TabButtonClicker()
  {
    initComponents();

    this.noteChordSelector1.addPropertyChangeListener("Chord", new PropertyChangeListener()
    {

      @Override
      public void propertyChange(PropertyChangeEvent evt)
      {
        Chord chord = (Chord) evt.getNewValue();
        if ((chord == null) || !columnModel.editSelectedColumn(new ParsedChordDef(chord))) {
          syncUIToDataModel();
        }
      }
    });
  }

  @Override
  public void init(SeqColumnModel model)
  {
    super.init(model);
    chordSelInfoPanel.init(model);
  }

  @Override
  protected boolean listenToCols()
  {
    return true;
  }

  @Override
  public void setVisible(boolean vis)
  {
    super.setVisible(vis);
    chordSelInfoPanel.setVisible(vis);
  }

  @Override
  protected void syncUIToDataModel()
  {
//    ButtonCombo activeCombo = columnModel.getSelectedButtonCombo();
    ParsedChordDef activeChordDef = columnModel.getSelectedChordDef();

//    if (activeCombo != null) {
//      this.noteChordSelector1.setChord(activeCombo.getChordMask());
//    } else
    if (activeChordDef != null) {
      this.noteChordSelector1.setChord(activeChordDef.chord.getChordMask());
    } else {
      this.noteChordSelector1.setChord(new Chord.Mask());
    }
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    noteChordSelector1 = new render.NoteChordSelector();
    chordSelInfoPanel = new render.TabChordInfo();

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(chordSelInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
      .addComponent(noteChordSelector1, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(noteChordSelector1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(chordSelInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private render.TabChordInfo chordSelInfoPanel;
  private render.NoteChordSelector noteChordSelector1;
  // End of variables declaration//GEN-END:variables
}
