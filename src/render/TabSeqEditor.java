/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TabSeqEditor.java
 *
 * Created on Oct 21, 2010, 8:29:48 PM
 */
package render;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Ilya
 */
public class TabSeqEditor extends ToolPanel implements TableModelListener
{
  JTable seqTable;
  boolean isUpdating = false;
  DefaultComboBoxModel recentSeqs;

  /** Creates new form TabSeqEditor */
  public TabSeqEditor()
  {
    initComponents();

    recentSeqs = new DefaultComboBoxModel();
    comboRecent.setModel(recentSeqs);
  }

  @Override
  public void init(SeqColumnModel theModel)
  {
    super.init(theModel);
    //seqTable = theTable;
    columnModel.getDataModel().addTableModelListener(this);

    //seqPicker1.init(theModel);

    //transNotePicker.addPropertyChangeListener(this);
  }

  @Override
  public void tableChanged(TableModelEvent e)
  {
    syncUIToDataModel();
  }

  @Override
  protected void syncUIToDataModel()
  {
    if (isUpdating) {
      return;
    }

    if ((columnModel != null)) {
      String text = columnModel.toString();

      isUpdating = true;

      int index = recentSeqs.getIndexOf(text);

      if (index < 0) {
        recentSeqs.insertElementAt(text, 0);
        index = 0;
      }
      this.comboRecent.setSelectedIndex(index);

      isUpdating = false;
    }

//    int selColumn = columnModel.getSelectedColumn();
//    if ((selColumn >= 0) && (selColumn < columnModel.getColumnCount())) {
//      //isUpdating = true;
//      //this.transNotePicker.setNote(columnModel.getChordDef(selColumn).rootNote);
//      isUpdating = false;
//    }
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPanel1 = new javax.swing.JPanel();
    computeButton = new javax.swing.JButton();
    comboRecent = new javax.swing.JComboBox();
    buttonClearRecent = new javax.swing.JButton();
    checkAllowDups = new javax.swing.JCheckBox();

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Full Chord Sequence:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

    computeButton.setText("Update Sequence");
    computeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        computeButtonActionPerformed(evt);
      }
    });

    comboRecent.setEditable(true);
    comboRecent.setFont(new java.awt.Font("Monospaced", 0, 18));
    comboRecent.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        comboRecentActionPerformed(evt);
      }
    });

    buttonClearRecent.setText("Clear Recent");
    buttonClearRecent.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonClearRecentActionPerformed(evt);
      }
    });

    checkAllowDups.setSelected(true);
    checkAllowDups.setText("Duplicate Note for Bass");

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(computeButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(buttonClearRecent)
            .addGap(35, 35, 35)
            .addComponent(checkAllowDups))
          .addComponent(comboRecent, 0, 416, Short.MAX_VALUE))
        .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
        .addComponent(comboRecent, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(buttonClearRecent)
          .addComponent(computeButton)
          .addComponent(checkAllowDups)))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

    private void computeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_computeButtonActionPerformed
    {//GEN-HEADEREND:event_computeButtonActionPerformed
      Object obj = comboRecent.getSelectedItem();

      String text;

      if (obj == null) {
        text = "C";
      } else {
        text = obj.toString();
        if (text.isEmpty()) {
          text = "C";
        }
      }

//      if (columnModel.toString().equals(text)) {
//        return;
//      }

      if (columnModel != null) {
        columnModel.populateFromText(text, !checkAllowDups.isSelected(), null);
      }
      //seqTable.requestFocusInWindow();
}//GEN-LAST:event_computeButtonActionPerformed

    private void comboRecentActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_comboRecentActionPerformed
    {//GEN-HEADEREND:event_comboRecentActionPerformed
      if (isUpdating) {
        return;
      }

      computeButton.doClick();
    }//GEN-LAST:event_comboRecentActionPerformed

    private void buttonClearRecentActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonClearRecentActionPerformed
    {//GEN-HEADEREND:event_buttonClearRecentActionPerformed
      isUpdating = true;

      Object sel = recentSeqs.getSelectedItem();
      recentSeqs.removeAllElements();
      recentSeqs.addElement(sel);
      //recentSeqs.setSelectedItem(sel);

      isUpdating = false;
    }//GEN-LAST:event_buttonClearRecentActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonClearRecent;
  private javax.swing.JCheckBox checkAllowDups;
  private javax.swing.JComboBox comboRecent;
  private javax.swing.JButton computeButton;
  private javax.swing.JPanel jPanel1;
  // End of variables declaration//GEN-END:variables
}
