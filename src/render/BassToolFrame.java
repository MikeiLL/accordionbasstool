/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BassToolFrame.java
 *
 * Created on Jun 19, 2010, 1:51:26 AM
 */
package render;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JSplitPane;

/**
 *
 * @author Ilya
 */
public class BassToolFrame extends javax.swing.JFrame implements PropertyChangeListener, ActionListener
{

  boolean editorVis = true;

  /** Creates new form BassToolFrame */
  public BassToolFrame()
  {
    initComponents();

    seqTablePanel.initChordPicker(this.chordPicker1);
    seqTablePanel.initTextParser(this.textParserPanel1);
   
    this.renderBoardHeader1.initBoardHeader(renderBassBoard, renderBoardScrollPane, seqTablePanel.columnModel);

    this.renderBassBoard.setSelectedButtonCombo(seqTablePanel.columnModel.selComboModel);

    seqTablePanel.toggleChordPicker.addActionListener(this);

    controlSplitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, this);

    //this.splitPane.setDividerLocation(controlSplitPane.getMinimumDividerLocation());
    this.controlSplitPane.setDividerLocation(controlSplitPane.getMaximumDividerLocation());
    
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
      int newLoc = ((Integer) evt.getNewValue()).intValue();

      this.editorVis = (newLoc <= controlSplitPane.getMaximumDividerLocation() + 10);

      //System.out.println("newLoc: " + newLoc + "Width: " + controlSplitPane.getWidth() + " Max: " + this.controlSplitPane.getMaximumDividerLocation());
      if (editorVis) {
        seqTablePanel.toggleChordPicker.setText("Hide Editor >>");
      } else {
        seqTablePanel.toggleChordPicker.setText("<< Show Editor");
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    if (editorVis) {
      controlSplitPane.setDividerLocation(1.0);
    } else {
      controlSplitPane.setDividerLocation(controlSplitPane.getMaximumDividerLocation());
    }
  }

  private static RenderBassBoard theBoard = null;

  public static RenderBassBoard getRenderBoard()
  {
    if (theBoard == null) {
      theBoard = new RenderBassBoard();
    }

    return theBoard;
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    splitPane = new javax.swing.JSplitPane();
    jPanel1 = new javax.swing.JPanel();
    renderBoardScrollPane = new javax.swing.JScrollPane();
    renderBassBoard = getRenderBoard();
    renderBoardHeader1 = new render.RenderBoardHeader();
    controlSplitPane = new javax.swing.JSplitPane();
    seqTablePanel = new render.SeqTablePanel();
    toolTabs = new javax.swing.JTabbedPane();
    chordPicker1 = new render.ChordPicker();
    textParserPanel1 = new render.TextParserPanel();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Accordion Bass Tool v0.5");
    setLocationByPlatform(true);

    splitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

    jPanel1.setLayout(new java.awt.BorderLayout());

    javax.swing.GroupLayout renderBassBoardLayout = new javax.swing.GroupLayout(renderBassBoard);
    renderBassBoard.setLayout(renderBassBoardLayout);
    renderBassBoardLayout.setHorizontalGroup(
      renderBassBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 1280, Short.MAX_VALUE)
    );
    renderBassBoardLayout.setVerticalGroup(
      renderBassBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 311, Short.MAX_VALUE)
    );

    renderBoardScrollPane.setViewportView(renderBassBoard);

    renderBoardScrollPane.getVerticalScrollBar().setBlockIncrement(24);
    renderBoardScrollPane.getVerticalScrollBar().setUnitIncrement(8);
    jPanel1.add(renderBoardScrollPane, java.awt.BorderLayout.CENTER);
    jPanel1.add(renderBoardHeader1, java.awt.BorderLayout.PAGE_START);

    splitPane.setRightComponent(jPanel1);

    controlSplitPane.setDividerSize(20);
    controlSplitPane.setAutoscrolls(true);
    controlSplitPane.setOneTouchExpandable(true);
    controlSplitPane.setLeftComponent(seqTablePanel);

    toolTabs.addTab("Chord Editor", chordPicker1);
    toolTabs.addTab("Advanced Sequence Editor", textParserPanel1);

    controlSplitPane.setRightComponent(toolTabs);

    splitPane.setLeftComponent(controlSplitPane);

    getContentPane().add(splitPane, java.awt.BorderLayout.CENTER);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private render.ChordPicker chordPicker1;
  private javax.swing.JSplitPane controlSplitPane;
  private javax.swing.JPanel jPanel1;
  private render.RenderBassBoard renderBassBoard;
  private render.RenderBoardHeader renderBoardHeader1;
  private javax.swing.JScrollPane renderBoardScrollPane;
  private render.SeqTablePanel seqTablePanel;
  private javax.swing.JSplitPane splitPane;
  private render.TextParserPanel textParserPanel1;
  private javax.swing.JTabbedPane toolTabs;
  // End of variables declaration//GEN-END:variables
}
