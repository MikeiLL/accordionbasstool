/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NotePicker.java
 *
 * Created on Oct 7, 2010, 3:41:57 PM
 */
package render;

import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import music.Note;

/**
 *
 * @author Ilya
 */
public class NotePicker extends javax.swing.JPanel
{

  String accStr;
  String noteStr;
  Note note;

  /** Creates new form NotePicker */
  public NotePicker()
  {
    initComponents();
    noteStr = "C";
    accStr = "";
    note = new Note();
  }

//  @Override
//  public void updateUI()
//  {
//    super.updateUI();
//
//
//    //TODO: Margin hack for nimbus
//    Component[] comps = this.getComponents();
//
//    for (Component comp : comps)
//    {
//      if (comp instanceof JButton)
//        ((JButton)comp).setMargin(getButtonMargins());
//    }
//  }


  public void setNote(Note note)
  {
    noteStr = note.toString(); //normalize to 1 flat or sharp only
    Enumeration<AbstractButton> noteButs = rootButtonGroup.getElements();
    while (noteButs.hasMoreElements()) {
      AbstractButton but = noteButs.nextElement();
      if (noteStr.startsWith(but.getActionCommand())) {
        but.setSelected(true);
        break;
      }
    }

    if (noteStr.length() == 1) {
      rootNatural.setSelected(true);
    } else if (note.getSharpOrFlat() > 0) {
      rootSharp.setSelected(true);
    } else if (note.getSharpOrFlat() < 0) {
      rootFlat.setSelected(true);
    } else {
      rootNatural.setSelected(true);
    }
  }

  private Insets getButtonMargins()
  {
    //TODO: solve this hack
    if (!UIManager.getLookAndFeel().getName().contains("Nimbus"))
      return new Insets(2, 8, 2, 8);
    else
      return new Insets(0, 0, 0, 0);
  }

  @Override
  public void setEnabled(boolean enable)
  {
    super.setEnabled(enable);

    Enumeration<AbstractButton> noteButs;
    noteButs = rootButtonGroup.getElements();
    if (!enable) {
      rootButtonGroup.clearSelection();
    }
    while (noteButs.hasMoreElements()) {
      AbstractButton but = noteButs.nextElement();
      but.setEnabled(enable);
    }

    noteButs = accButtonGroup.getElements();
    if (!enable) {
      accButtonGroup.clearSelection();
    }
    while (noteButs.hasMoreElements()) {
      AbstractButton but = noteButs.nextElement();
      but.setEnabled(enable);
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

    rootButtonGroup = new javax.swing.ButtonGroup();
    accButtonGroup = new javax.swing.ButtonGroup();
    rootA = new javax.swing.JToggleButton();
    rootB = new javax.swing.JToggleButton();
    rootC = new javax.swing.JToggleButton();
    rootD = new javax.swing.JToggleButton();
    rootE = new javax.swing.JToggleButton();
    rootF = new javax.swing.JToggleButton();
    rootG = new javax.swing.JToggleButton();
    rootFlat = new javax.swing.JToggleButton();
    rootNatural = new javax.swing.JToggleButton();
    rootSharp = new javax.swing.JToggleButton();

    setBorder(javax.swing.BorderFactory.createTitledBorder(null, getName(), javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));
    setName("Root:"); // NOI18N

    rootButtonGroup.add(rootA);
    rootA.setFont(new java.awt.Font("Tahoma", 1, 11));
    rootA.setText("A");
    rootA.setFocusPainted(false);
    rootA.setFocusable(false);
    rootA.setMargin(getButtonMargins());
    rootA.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        NoteItemChanged(evt);
      }
    });

    rootButtonGroup.add(rootB);
    rootB.setFont(new java.awt.Font("Tahoma", 1, 11));
    rootB.setText("B");
    rootB.setFocusPainted(false);
    rootB.setFocusable(false);
    rootB.setMargin(getButtonMargins());
    rootB.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        NoteItemChanged(evt);
      }
    });

    rootButtonGroup.add(rootC);
    rootC.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    rootC.setSelected(true);
    rootC.setText("C");
    rootC.setFocusPainted(false);
    rootC.setFocusable(false);
    rootC.setMargin(getButtonMargins());
    rootC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        NoteItemChanged(evt);
      }
    });

    rootButtonGroup.add(rootD);
    rootD.setFont(new java.awt.Font("Tahoma", 1, 11));
    rootD.setText("D");
    rootD.setFocusPainted(false);
    rootD.setFocusable(false);
    rootD.setMargin(getButtonMargins());
    rootD.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        NoteItemChanged(evt);
      }
    });

    rootButtonGroup.add(rootE);
    rootE.setFont(new java.awt.Font("Tahoma", 1, 11));
    rootE.setText("E");
    rootE.setFocusPainted(false);
    rootE.setFocusable(false);
    rootE.setMargin(getButtonMargins());
    rootE.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        NoteItemChanged(evt);
      }
    });

    rootButtonGroup.add(rootF);
    rootF.setFont(new java.awt.Font("Tahoma", 1, 11));
    rootF.setText("F");
    rootF.setFocusPainted(false);
    rootF.setFocusable(false);
    rootF.setMargin(getButtonMargins());
    rootF.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        NoteItemChanged(evt);
      }
    });

    rootButtonGroup.add(rootG);
    rootG.setFont(new java.awt.Font("Tahoma", 1, 11));
    rootG.setText("G");
    rootG.setFocusPainted(false);
    rootG.setFocusable(false);
    rootG.setMargin(getButtonMargins());
    rootG.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        NoteItemChanged(evt);
      }
    });

    accButtonGroup.add(rootFlat);
    rootFlat.setFont(new java.awt.Font("Tahoma", 1, 11));
    rootFlat.setText("b");
    rootFlat.setFocusPainted(false);
    rootFlat.setFocusable(false);
    rootFlat.setMargin(getButtonMargins());
    rootFlat.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        NoteItemChanged(evt);
      }
    });

    accButtonGroup.add(rootNatural);
    rootNatural.setFont(new java.awt.Font("Tahoma", 1, 11));
    rootNatural.setSelected(true);
    rootNatural.setText("-");
    rootNatural.setActionCommand(" ");
    rootNatural.setFocusPainted(false);
    rootNatural.setFocusable(false);
    rootNatural.setMargin(getButtonMargins());
    rootNatural.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        NoteItemChanged(evt);
      }
    });

    accButtonGroup.add(rootSharp);
    rootSharp.setFont(new java.awt.Font("Tahoma", 1, 11));
    rootSharp.setText("#");
    rootSharp.setFocusPainted(false);
    rootSharp.setFocusable(false);
    rootSharp.setMargin(getButtonMargins());
    rootSharp.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        NoteItemChanged(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(rootA)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(rootB)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(rootC)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(rootD)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(rootE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(rootF)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(rootG)
        .addGap(41, 41, 41)
        .addComponent(rootFlat)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(rootNatural)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(rootSharp)
        .addContainerGap(17, Short.MAX_VALUE))
    );

    layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {rootA, rootB, rootC, rootD, rootE, rootF, rootG});

    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
        .addComponent(rootA)
        .addComponent(rootB)
        .addComponent(rootC)
        .addComponent(rootD)
        .addComponent(rootE)
        .addComponent(rootF)
        .addComponent(rootG)
        .addComponent(rootFlat)
        .addComponent(rootNatural)
        .addComponent(rootSharp))
    );
  }// </editor-fold>//GEN-END:initComponents

    private void NoteItemChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NoteItemChanged
      if (evt.getStateChange() != ItemEvent.SELECTED) {
        return;
      }

      if (!(evt.getSource() instanceof JToggleButton)) {
        return;
      }

      JToggleButton but = (JToggleButton) evt.getSource();

      Note oldNote = note;

      if (rootButtonGroup.isSelected(but.getModel())) {
        noteStr = but.getActionCommand();
      } else if (accButtonGroup.isSelected(but.getModel())) {
        accStr = but.getActionCommand();
      }
//      else if (but == rootSharp)
//      {
//        if (but.isSelected())
//          rootFlat.setSelected(false);
//
//        accStr = but.getActionCommand();
//      }
//      else if (but == rootFlat)
//      {
//        if (but.isSelected())
//          rootSharp.setSelected(false);
//
//        accStr = but.getActionCommand();
//      }


      this.firePropertyChange("Note", oldNote, Note.fromString(noteStr + accStr));
    }//GEN-LAST:event_NoteItemChanged
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.ButtonGroup accButtonGroup;
  private javax.swing.JToggleButton rootA;
  private javax.swing.JToggleButton rootB;
  private javax.swing.ButtonGroup rootButtonGroup;
  private javax.swing.JToggleButton rootC;
  private javax.swing.JToggleButton rootD;
  private javax.swing.JToggleButton rootE;
  private javax.swing.JToggleButton rootF;
  private javax.swing.JToggleButton rootFlat;
  private javax.swing.JToggleButton rootG;
  private javax.swing.JToggleButton rootNatural;
  private javax.swing.JToggleButton rootSharp;
  // End of variables declaration//GEN-END:variables
}
