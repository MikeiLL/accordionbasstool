/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import music.BassBoard.Pos;
import music.ButtonCombo;

/**
 *
 * @author Ilya
 */
public class SeqAnimController implements ActionListener
{

  Timer playTimer, playStopTimer;
  SeqColumnModel columnModel;
  SoundController sound;
  RenderBassBoard renderBoard;

  public SeqAnimController(RenderBassBoard board,
          SeqColumnModel model,
          SoundController sound,
          int playDuration, int pauseDuration)
  {
    columnModel = model;
    this.sound = sound;
    sound.playDuration = playDuration;
    renderBoard = board;

    playTimer = new Timer(playDuration + pauseDuration, this);
    playTimer.setActionCommand("Timer");

    playStopTimer = new Timer(playDuration, this);
    playStopTimer.setActionCommand("TimerStop");
    playStopTimer.setRepeats(false);
    playStopTimer.stop();
  }

  public boolean isRunning()
  {
    return playTimer.isRunning();
  }

  public boolean toggleRun()
  {
    if (!playTimer.isRunning()) {
      sound.play(columnModel.getSelectedButtonCombo(), false);
      playTimer.restart();
      return true;
    } else {
      playTimer.stop();
      sound.stop();
      renderBoard.repaint();
      playStopTimer.stop();
      return false;
    }
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == playTimer) {
      int index = columnModel.getSelectedColumn();

      index++;
      if (index >= columnModel.getColumnCount()) {
        index = 0;
      }

      columnModel.selComboModel.setSelectionInterval(index, index);
      //seqTable.scrollRectToVisible(seqTable.getCellRect(seqTable.getSelectedRow(), index, true));

      playStopTimer.setRepeats(false);
      playStopTimer.restart();

    } else if (e.getActionCommand().equals("TimerStop")) {
      ButtonCombo combo = columnModel.getSelectedButtonCombo();
      if (combo == null) {
        return;
      }

      for (Pos pos : combo.getAllPos()) {
        renderBoard.drawPos(pos, RenderBoardUI.BoardButtonImage.SELECTED);
      }
      sound.stop();
    }
  }
}