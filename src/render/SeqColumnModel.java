package render;

import music.ParsedChordDef;
import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import music.BoardSearcher;
import music.ButtonCombo;
import music.ButtonComboSequence;
import music.ChordParser;
import music.CollSequence;
import music.FingerCombo;
import music.FingerComboSequence;
import music.FingerSearcher;
import music.Interval;
import music.Note;
import music.StringParser;

public class SeqColumnModel extends DefaultTableColumnModel
{

  SelectedButtonCombo selComboModel;
  private ButtonComboSequence[] allComboSeqs;
  private BoardSearcher searcher;
  private FingerComboSequence[] fingerComboSeqs;
  private FingerSearcher fingerSearcher;
  private RenderBassBoard renderBoard;
  private CollSequence[] currSeqArray;
  private SeqRowHeaderData rowHeaderDataModel;
  private SeqDataModel dataModel;
  private ListSelectionModel rowSelModel;
  public boolean optFingerSearch = false;
  final static int DEFAULT_COL_WIDTH = 120;

  SeqColumnModel(RenderBassBoard rBoard, ListSelectionModel selM)
  {
    selComboModel = new SelectedButtonCombo();
    selComboModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    allComboSeqs = new ButtonComboSequence[0];
    searcher = new BoardSearcher();

    fingerComboSeqs = new FingerComboSequence[0];
    fingerSearcher = new FingerSearcher();

    currSeqArray = (optFingerSearch ? fingerComboSeqs : allComboSeqs);

    renderBoard = rBoard;
    rowSelModel = selM;

    dataModel = new SeqDataModel();
    rowHeaderDataModel = new SeqRowHeaderData();

    this.setSelectionModel(selComboModel);
  }

  void addColumn(int index)
  {
    addColumn(ParsedChordDef.getDefaultChordDef(), index);
  }

  void addColumn(ParsedChordDef def, int index)
  {
    if (def == null) {
      return;
    }
    int lastColIndex = getColumnCount();
    if (index < 0) {
      index = lastColIndex;
    }
    TableColumn column = new TableColumn(lastColIndex, DEFAULT_COL_WIDTH, null, null);
    column.setHeaderValue(def);
    addColumn(column);
    if (index < lastColIndex) {
      moveColumn(lastColIndex, index);
    } else {
      computeSeqs(index);
    }
  }

  @Override
  public void moveColumn(int columnIndex, int newIndex)
  {
    super.moveColumn(columnIndex, newIndex);
    if (columnIndex != newIndex) {
      syncModelToView(Math.min(columnIndex, newIndex), newIndex);
    }
  }

  int getSelectedColumn()
  {
    if (this.getSelectedColumnCount() > 0) {
      return this.getSelectedColumns()[0];
    } else {
      return -1;
    }
  }

  ButtonCombo getSelectedButtonCombo()
  {
    int row = this.rowSelModel.getAnchorSelectionIndex();
    int col = this.getSelectedColumn();

    Object obj = this.getDataModel().getValueAt(row, col);

    if (obj == null) {
      return null;
    }

    if (obj instanceof ButtonCombo) {
      return (ButtonCombo) obj;
    } else if (obj instanceof FingerCombo) {
      return ((FingerCombo) obj).getButtonCombo();
    }

    return null;
  }

  boolean editSelectedColumn(ParsedChordDef newDef)
  {
    int index = getSelectedColumn();
    if (index >= 0) {
      return editColumn(index, newDef);
    }

    return false;
  }

  void resetToSingleColumn()
  {
    this.tableColumns.clear();
    this.addColumn(0);
  }

  void removeSelectedColumn()
  {
    if (getColumnCount() < 2) {
      return;
    }
    int index = getSelectedColumn();
    if (index < 0) {
      return;
    }
    removeColumn(getColumn(index));
    if (index < getColumnCount()) {
      syncModelToView(index, index);
    } else {
      computeSeqs(index - 1);
    }
  }
  boolean isPopulating = false;

  public void populateFromText(String text, boolean removeDupNotes)
  {
    StringParser parser = new StringParser(text);
    Vector<ParsedChordDef> chords = ChordParser.parseChords(parser, removeDupNotes);

    this.tableColumns.clear();

    isPopulating = true;

    for (int i = 0; i < chords.size(); i++) {
      this.addColumn(chords.elementAt(i), i);
    }

    isPopulating = false;
    syncModelToView(0, 0);
  }

  private void syncModelToView(int index, int selIndex)
  {
    for (int i = index; i < getColumnCount(); i++) {
      getColumn(i).setModelIndex(i);
    }

    computeSeqs(selIndex);
  }

  public void transposeAllFromSelectedColumn(Note newNote)
  {
    TableColumn column = this.getColumn(getSelectedColumn());

    // Find interval diff between new and old and apply to all
    ParsedChordDef existingDef = (ParsedChordDef) column.getHeaderValue();

    Interval transDiff = newNote.diff(existingDef.rootNote);

    transposeAllByInterval(transDiff);
  }

  public void transposeAllByInterval(Interval transDiff)
  {
    ParsedChordDef existingDef;

    for (int i = 0; i < getColumnCount(); i++) {
      TableColumn currCol = this.getColumn(i);
      existingDef = (ParsedChordDef) currCol.getHeaderValue();
      currCol.setHeaderValue(existingDef.transposeBy(transDiff));
    }

    recomputeSeqs();
  }

  private boolean editColumn(int index, ParsedChordDef newChordDef)
  {
    TableColumn column = this.getColumn(index);

    if (newChordDef == null) {
      return false;
    }

    ParsedChordDef oldChordDef = (ParsedChordDef) column.getHeaderValue();
    column.setHeaderValue(newChordDef);

    if (!oldChordDef.equalForRecompute(newChordDef)) {
      computeSeqs(index);
      return true;
    } else {
      this.fireColumnMarginChanged();
      return false;
    }
  }

  ParsedChordDef getChordDef(int index)
  {
    assert ((index >= 0) && (index < getColumnCount()));
    return (ParsedChordDef) getColumn(index).getHeaderValue();
  }

  ParsedChordDef getSelectedChordDef()
  {
    int index = getSelectedColumn();
    return ((index >= 0) && (index < getColumnCount()) ? getChordDef(index) : null);
  }

  Vector<ParsedChordDef> getAllChords()
  {
    Vector<ParsedChordDef> vec = new Vector<ParsedChordDef>();
    for (int i = 0; i < getColumnCount(); i++) {
      vec.add(getChordDef(i));
    }
    return vec;
  }

  SeqDataModel getDataModel()
  {
    return dataModel;
  }

  SeqRowHeaderData getRowHeaderDataModel()
  {
    return rowHeaderDataModel;
  }

  public ListSelectionModel getRowSelModel()
  {
    return this.rowSelModel;
  }

  void addTableAndColumnListener(SeqTableEventAdapter listener)
  {
    this.addColumnModelListener(listener);
    this.getDataModel().addTableModelListener(listener);
  }

  boolean setSelectedSeq(int index)
  {
    if (index < 0) {
      selComboModel.setButtonComboSeq(null);
      return false;
    }

    if (optFingerSearch) {
      if (index < fingerComboSeqs.length) {
        selComboModel.setFingerComboSeq(fingerComboSeqs[index]);
        return true;
      } else {
        selComboModel.setButtonComboSeq(null);
      }
    } else {
      if (index < allComboSeqs.length) {
        selComboModel.setButtonComboSeq(allComboSeqs[index]);
        return true;
      } else {
        selComboModel.setButtonComboSeq(null);
      }
    }

    return false;
  }

  @Override
  public String toString()
  {
    String str = "";
    for (int i = 0; i < getColumnCount(); i++) {
      if (i > 0) {
        str += ", ";
      }

      ParsedChordDef def = getChordDef(i);
      str += def.namePlain;
    }

    return str;
  }

  public String toHtmlString(boolean hiliteSelected)
  {
    String str = "";

    int selIndex = -1;
    if (hiliteSelected) {
      selIndex = this.getSelectedColumn();
    }

    for (int i = 0; i < getColumnCount(); i++) {
      if (i > 0) {
        str += ", ";
      }

      ParsedChordDef def = getChordDef(i);

      if (i == selIndex) {
        str += "<b>" + def.nameHtml + "</b>";
      } else {
        str += def.nameHtml;
      }
    }

    return str;
  }

  void computeSeqs(int colSel)
  {
    if (renderBoard == null) {
      return;
    }

    if (isPopulating) {
      return;
    }

    int rowSel = rowSelModel.getAnchorSelectionIndex();

    music.BassBoard currBassBoard = renderBoard.getBassBoard();
    allComboSeqs = searcher.parseSequence(currBassBoard, getAllChords());
    assert (allComboSeqs != null);

    if (optFingerSearch) {
      fingerComboSeqs = fingerSearcher.findAllFingers(allComboSeqs);
      currSeqArray = fingerComboSeqs;
    } else {
      currSeqArray = allComboSeqs;
    }

    rowHeaderDataModel.fireListDataChanged();
    dataModel.fireTableDataChanged();

    updateRowSel(rowSel);

    if (colSel < currSeqArray.length) {
      selComboModel.setSelectionInterval(colSel, colSel);
    } else {
      selComboModel.setSelectionInterval(0, 0);
    }
  }

  public void recomputeSeqs()
  {
    computeSeqs(getSelectedColumn());
  }

  public void selPrefSeq()
  {
    int index = findRowForPrefSeq();

    if (index >= 0) {
      rowSelModel.setSelectionInterval(index, index);
    }
  }

  private void updateRowSel(int prevRow)
  {
    int index = findRowForPrefSeq();

    if (index < 0) {
      index = prevRow;
    }

    if ((index >= dataModel.getRowCount()) || (index < 0)) {
      index = 0;
    }

    rowSelModel.setSelectionInterval(index, index);
  }

  public void clearPrefSeq()
  {
    boolean clearedAny = false;

    for (int col = 0; col < this.getColumnCount(); col++) {
      ParsedChordDef def = this.getChordDef(col);
      if (def.getPrefCombo() != null) {
        def.setPrefCombo(null);
        clearedAny = true;
      }
    }

    if (clearedAny) {
      this.recomputeSeqs();
    }
  }

  private int findRowForPrefSeq()
  {
    boolean hasPrefCombo = false;

    for (int row = 0; row < allComboSeqs.length; row++) {

      boolean rowMatches = true;

      if (allComboSeqs[row].getNumCombos() < this.getColumnCount()) {
        continue;
      }

      for (int col = 0; col < this.getColumnCount(); col++) {

        ButtonCombo matchCombo = this.getChordDef(col).getPrefCombo();

        if (matchCombo == null) {
          continue;
        }

        hasPrefCombo = true;

        if (!allComboSeqs[row].getCombo(col).equals(matchCombo)) {
          rowMatches = false;
        }
      }

      if (!hasPrefCombo) {
        return -1;
      }

      if (rowMatches) {
        //rowSelModel.setSelectionInterval(row, row);
        return row;
      }
    }

    //rowSelModel.clearSelection();

    //rowSelModel.setSelectionInterval(-1, -1);
    return -1;
  }

  class SeqDataModel extends AbstractTableModel
  {

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
      return CollSequence.class;
    }

    @Override
    public String getColumnName(int column)
    {
      return SeqColumnModel.this.getChordDef(column).nameHtml;
    }

    public SeqDataModel()
    {
    }

    @Override
    public int getColumnCount()
    {
      return SeqColumnModel.this.getColumnCount();
    }

    @Override
    public int getRowCount()
    {
      if (currSeqArray.length == 0) {
        return 1;
      }

      return currSeqArray.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
      if ((rowIndex < 0) || (rowIndex >= currSeqArray.length)) {
        return null;
      }

      if ((columnIndex < 0) || (columnIndex >= currSeqArray[rowIndex].getNumCombos())) {
        return null;
      }

      return currSeqArray[rowIndex].getCombo(columnIndex);
    }
  }

  class SeqRowHeaderData extends AbstractListModel
  {

    SeqRowHeaderData()
    {
    }

    void fireListDataChanged()
    {
      fireContentsChanged(this, 0, currSeqArray.length);
    }

    @Override
    public int getSize()
    {
      if (currSeqArray.length == 0) {
        return 1;
      }

      return currSeqArray.length;
    }

    @Override
    public Object getElementAt(int index)
    {
      if (currSeqArray.length == 0) {
        return null;
      }

      return currSeqArray[index];
    }
  }
}
