/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * RenderBoardHeader.java
 *
 * Created on Oct 25, 2010, 12:32:49 PM
 */
package render;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import music.BassBoard;
import music.BoardRegistry;

/**
 *
 * @author Ilya
 */
public class RenderBoardHeader extends javax.swing.JPanel implements ActionListener
{

  RenderBassBoard renderBoard;
  JScrollPane boardScrollPane;
  SeqColumnModel columnModel;

  /** Creates new form RenderBoardHeader */
  public RenderBoardHeader()
  {
    initComponents();
  }

  void initBoardHeader(RenderBassBoard renderBoard, JScrollPane pane, SeqColumnModel model)
  {
    this.renderBoard = renderBoard;
    this.columnModel = model;
    this.boardScrollPane = pane;

    boardScrollPane.setBorder(BorderFactory.createEmptyBorder());

    boardCombo.setModel(new DefaultComboBoxModel(BoardRegistry.mainRegistry().allBoardDefs));
    boardCombo.addActionListener(this);

    // Init to standard 120 bass board
    BoardRegistry.BoardDef initialBoardDef = BoardRegistry.mainRegistry().findByBassCount(120);
    if (initialBoardDef != null) {
      boardCombo.setSelectedItem(initialBoardDef);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == boardCombo) {
      if (renderBoard != null) {
        BoardRegistry.BoardDef def = (BoardRegistry.BoardDef) boardCombo.getSelectedItem();

        BassBoard newBoard = def.createBoard();

        renderBoard.setBassBoard(newBoard);
        columnModel.recomputeSeqs();

        String info = "<html>Range: <b>";
        info += newBoard.getMinRootNote().toString(true);
        info += " to ";
        info += newBoard.getMaxRootNote().toString(true);
        info += "</b></html>";
        this.infoLabel.setText(info);

        boardScrollPane.revalidate();
      }
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

    jLabel1 = new javax.swing.JLabel();
    boardCombo = new javax.swing.JComboBox();
    infoLabel = new javax.swing.JLabel();

    setBackground(java.awt.SystemColor.activeCaption);
    setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

    jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()+5f));
    jLabel1.setText("Current Board:");
    add(jLabel1);

    boardCombo.setFont(boardCombo.getFont().deriveFont(boardCombo.getFont().getStyle() | java.awt.Font.BOLD, boardCombo.getFont().getSize()+5));
    boardCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    add(boardCombo);

    infoLabel.setFont(infoLabel.getFont().deriveFont(infoLabel.getFont().getSize()+5f));
    infoLabel.setText("info");
    add(infoLabel);
  }// </editor-fold>//GEN-END:initComponents
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JComboBox boardCombo;
  private javax.swing.JLabel infoLabel;
  private javax.swing.JLabel jLabel1;
  // End of variables declaration//GEN-END:variables
}
