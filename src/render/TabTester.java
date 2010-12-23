/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TabTester.java
 *
 * Created on Oct 19, 2010, 4:10:10 PM
 */
package render;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author Ilya
 */
public class TabTester extends javax.swing.JPanel
{

  /** Creates new form TabTester */
  public TabTester()
  {
    initComponents();
  }

  @Override
  public void paint(Graphics g)
  {
    super.paint(g);
    
    Graphics2D graphics = (Graphics2D) g;

    graphics.translate(50, 50);

    if (RenderBoardUI.defaultUI == null) {
      return;
    }

    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//    int scale = 5;
//    int imWidth = RenderBoardUI.defaultUI.selectedIM.getWidth() * scale;
//    int imHeight = RenderBoardUI.defaultUI.selectedIM.getHeight() * scale;


   


    //graphics.setComposite(AlphaComposite.SrcAtop);
//    graphics.setColor(new Color(0.f, 1.f, 0.f, 1.0f));
//    graphics.fillRect(0, 0, imWidth, imHeight);
//    graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_ATOP, 1.0f));
//    graphics.drawImage(RenderBoardUI.defaultUI.selectedIM, 0, 0, imWidth, imHeight, null, this);

//  graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN, 1.0f));
//  graphics.setColor(new Color(1.f, 1.f, 0.f, 1.0f));
//  graphics.fillRect(0, 0, imWidth, imHeight);

//    graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
//    graphics.drawImage(RenderBoardUI.defaultUI.selectedIM, 0, 0, imWidth, imHeight, null, this);
//
//
//    graphics.translate(imWidth + 10, 0);
//    graphics.drawImage(RenderBoardUI.defaultUI.unselectedIM, 0, 0, imWidth, imHeight, null, this);
//
//    graphics.translate(imWidth + 10, 0);
//    graphics.drawImage(RenderBoardUI.defaultUI.pressedIM, 0, 0, imWidth, imHeight, null, this);
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 422, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 281, Short.MAX_VALUE)
    );
  }// </editor-fold>//GEN-END:initComponents
  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables
}
