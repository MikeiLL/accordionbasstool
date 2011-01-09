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

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import music.ButtonCombo;
import music.Chord;
import music.ChordRegistry;
import music.ParsedChordDef;

/**
 *
 * @author Ilya
 */
public class TabButtonClicker extends javax.swing.JPanel
{

  ButtonCombo activeCombo;
  SeqColumnModel columnModel;
  ParsedChordDef activeParsedChord;
  SelListener colModListener, rowModListener;
  Vector<ParsedChordDef> matches;
  boolean optIncludeInversion = false;

  class SelListener extends SeqTableEventAdapter
  {

    @Override
    public void selectionChanged(int index)
    {
      matches = null;
      syncUI();
    }
  }

  /** Creates new form TabButtonClicker */
  public TabButtonClicker()
  {
    initComponents();

    colModListener = new SelListener();
    rowModListener = new SelListener();
    this.possChordList.setCellRenderer(new ChordListRender());

    this.noteChordSelector1.addPropertyChangeListener("Chord", new PropertyChangeListener()
    {

      @Override
      public void propertyChange(PropertyChangeEvent evt)
      {
        Chord chord = (Chord) evt.getNewValue();
        if ((chord == null) || !columnModel.editSelectedColumn(new ParsedChordDef(chord))) {
          matches = null;
          syncUI();
        }
      }
    });
  }

  public void setSeqColModel(SeqColumnModel model)
  {
    columnModel = model;

    if (columnModel != null) {
      columnModel.addColumnModelListener(colModListener);
      columnModel.getRowSelModel().addListSelectionListener(rowModListener);
    }
  }

  @Override
  public void setVisible(boolean visible)
  {
    if (visible) {
      //renderBoard.addMouseListener(listener);
      //renderBoard.addMouseMotionListener(listener);
      //renderBoard.addPropertyChangeListener(BassBoard.class.getSimpleName(), this);

      if (columnModel != null) {
        columnModel.addColumnModelListener(colModListener);
        columnModel.getRowSelModel().addListSelectionListener(rowModListener);
      }

      syncUI();

    } else {
      if (columnModel != null) {
        //renderBoard.setSelectedButtonCombo(columnModel.selComboModel);
        columnModel.removeColumnModelListener(rowModListener);
        columnModel.getRowSelModel().removeListSelectionListener(rowModListener);
      }
    }

    super.setVisible(visible);
  }

  private void clearCombo()
  {
    if (columnModel != null) {
      columnModel.clearPrefSeq();
    }
  }

  public void setMatchedChords(Vector<ParsedChordDef> newMatches)
  {
    matches = newMatches;
  }

  private void updateChordDefsList(ButtonCombo prefCombo)
  {
    if (activeCombo == null) {
      this.clickedLabel.setText("<Html>No Possible Combo</html>");
      isTableDriven = true;
      possChordList.setListData(new ParsedChordDef[0]);
      isTableDriven = false;
      return;
    }

    String sortedNotesStr = activeCombo.toSortedNoteString(true);

    //Vector<ParsedChordDef> matches = possChords;

    if (matches == null) {
      matches = ChordRegistry.mainRegistry().
              findChordFromNotes(ButtonCombo.sortedNotes, activeCombo.getChordMask(), optIncludeInversion, true, false);

      if (prefCombo != null) {
        for (ParsedChordDef def : matches) {
          def.setPrefCombo(prefCombo);
        }
      }
    }

    String info = "<html>";
    info += "Buttons: ";
    info += "<b>" + activeCombo.toButtonListingString(true) + "</b><br/>";
    info += "Notes: ";
    //info += "<font size=\'-1\'>";
    info += "<b>" + sortedNotesStr + "</b>";
    //info += "</font>";
    info += "</html>";
    this.clickedLabel.setText(info);

    isTableDriven = true;

    this.noteChordSelector1.setChord(activeCombo.getChordMask());

    // If check show all unknowns, or if the list is empty
    // just set the whole list
    if (checkShowUnknownChords.isSelected() || matches.isEmpty()) {
  //          || (matches.firstElement().relChord.getOrigDef() == null)) {
      possChordList.setListData(matches);
    } else {
      // Otherwise, filter out the unknown chords
      Vector<ParsedChordDef> filtered = new Vector<ParsedChordDef>();

      for (ParsedChordDef def : matches) {
        if (def.relChord.getOrigDef() != null) {
          filtered.add(def);
        }
      }

      possChordList.setListData(filtered);
    }

    //possChordList.repaint();

    isTableDriven = false;
  }
  boolean isTableDriven = false;
  boolean isClickDriven = false;

  private void syncUI()
  {
    if (isClickDriven || isTableDriven) {
      return;
    }

    if (columnModel == null) {
      return;
    }

    //System.out.println(columnModel.selComboModel.getSelectedButtonCombo());

    //syncActiveButtons();
    activeCombo = columnModel.getSelectedButtonCombo();

    updateChordDefsList(null);

    activeParsedChord = columnModel.getSelectedChordDef();

    isTableDriven = true;
    possChordList.setSelectedValue(activeParsedChord, true);
    isTableDriven = false;
  }

  class ChordListRender extends DefaultListCellRenderer
  {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
      ParsedChordDef chordDef = (ParsedChordDef) value;

      String info = "<html>";
      info += chordDef.nameHtml;
      info += "</html>";

      return super.getListCellRendererComponent(list, info, index, isSelected, cellHasFocus);
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

    clickedLabel = new javax.swing.JLabel();
    buttonClear = new javax.swing.JButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    possChordList = new javax.swing.JList();
    checkShowUnknownChords = new javax.swing.JCheckBox();
    buttonSelPref = new javax.swing.JButton();
    checkIncludeInv = new javax.swing.JCheckBox();
    noteChordSelector1 = new render.NoteChordSelector();

    clickedLabel.setFont(new java.awt.Font("Tahoma", 0, 18));
    clickedLabel.setText("<None>");

    buttonClear.setText("Clear Clicked");
    buttonClear.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonClearActionPerformed(evt);
      }
    });

    jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Possible Chords:"));

    possChordList.setFont(possChordList.getFont().deriveFont(possChordList.getFont().getStyle() | java.awt.Font.BOLD, possChordList.getFont().getSize()+3));
    possChordList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
        possChordListValueChanged(evt);
      }
    });
    jScrollPane1.setViewportView(possChordList);

    checkShowUnknownChords.setText("Show All Chord Inversions");
    checkShowUnknownChords.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        checkShowUnknownChordsActionPerformed(evt);
      }
    });

    buttonSelPref.setText("Select Clicked");
    buttonSelPref.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonSelPrefActionPerformed(evt);
      }
    });

    checkIncludeInv.setText("Specify Optional Bass");
    checkIncludeInv.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        checkIncludeInvActionPerformed(evt);
      }
    });

    noteChordSelector1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(clickedLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
          .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addComponent(buttonClear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSelPref))
              .addComponent(checkShowUnknownChords)
              .addComponent(checkIncludeInv)))
          .addComponent(noteChordSelector1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(clickedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(noteChordSelector1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
            .addContainerGap())
          .addGroup(layout.createSequentialGroup()
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(buttonClear)
              .addComponent(buttonSelPref))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(checkShowUnknownChords)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(checkIncludeInv)
            .addGap(20, 20, 20))))
    );
  }// </editor-fold>//GEN-END:initComponents

  private void checkShowUnknownChordsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_checkShowUnknownChordsActionPerformed
  {//GEN-HEADEREND:event_checkShowUnknownChordsActionPerformed
    updateChordDefsList(null);

    isTableDriven = true;

    possChordList.setSelectedValue(activeParsedChord, true);

    isTableDriven = false;
  }//GEN-LAST:event_checkShowUnknownChordsActionPerformed

  private void possChordListValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_possChordListValueChanged
  {//GEN-HEADEREND:event_possChordListValueChanged
    if (isTableDriven) {
      return;
    }

    isClickDriven = true;

    ParsedChordDef chordDef = (ParsedChordDef) possChordList.getSelectedValue();

    if ((chordDef != null) && (chordDef != activeParsedChord)) {
      columnModel.editSelectedColumn(chordDef);
      activeParsedChord = chordDef;
    }

    isClickDriven = false;
  }//GEN-LAST:event_possChordListValueChanged

  private void buttonSelPrefActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonSelPrefActionPerformed
  {//GEN-HEADEREND:event_buttonSelPrefActionPerformed
    columnModel.selPrefSeq();
  }//GEN-LAST:event_buttonSelPrefActionPerformed

  private void checkIncludeInvActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_checkIncludeInvActionPerformed
  {//GEN-HEADEREND:event_checkIncludeInvActionPerformed
    // Reselect the same chord in the list, may however be with an inversion
    int index = possChordList.getSelectedIndex();
    optIncludeInversion = this.checkIncludeInv.isSelected();

    matches = null;
    ParsedChordDef curr = columnModel.getSelectedChordDef();
    ButtonCombo pref = ((curr != null) ? curr.getPrefCombo() : null);
    updateChordDefsList(pref);

    possChordList.setSelectedIndex(index);
  }//GEN-LAST:event_checkIncludeInvActionPerformed

  private void buttonClearActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonClearActionPerformed
  {//GEN-HEADEREND:event_buttonClearActionPerformed
    clearCombo();
  }//GEN-LAST:event_buttonClearActionPerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonClear;
  private javax.swing.JButton buttonSelPref;
  private javax.swing.JCheckBox checkIncludeInv;
  private javax.swing.JCheckBox checkShowUnknownChords;
  private javax.swing.JLabel clickedLabel;
  private javax.swing.JScrollPane jScrollPane1;
  private render.NoteChordSelector noteChordSelector1;
  private javax.swing.JList possChordList;
  // End of variables declaration//GEN-END:variables
}
