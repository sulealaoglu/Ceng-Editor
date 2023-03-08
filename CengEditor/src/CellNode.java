import enigma.console.TextAttributes;

import java.awt.*;

public class CellNode {
    private Object data;
    private CellNode next;
    private CellNode prev;
    private RowNode prevRow;
    private RowNode row;
    private int i,j;
    private TextAttributes attr;
    Color back = Editor.paintColor;
    Color clr =Editor.foreground;

    public CellNode(Object dataToAdd){
        data=dataToAdd;
        next=null;
        prev=null;
        prevRow=null;
        row=null;
        attr = new TextAttributes(clr, back);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public CellNode getNext() {
        return next;
    }

    public void setNext(CellNode next) {
        this.next = next;
    }

    public CellNode getPrev() {
        return prev;
    }

    public void setPrev(CellNode prev) {
        this.prev = prev;
    }

    public RowNode getPrevRow() {
        return prevRow;
    }

    public void setPrevRow(RowNode prevRow) {
        this.prevRow = prevRow;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public TextAttributes getAttr() {
        return attr;
    }

    public void setAttr(TextAttributes attr) {
        this.attr = attr;
    }

    public RowNode getRow() {
        return row;
    }

    public Color getBack() {
        return back;
    }

    public void setBack(Color back) {
        this.back = back;
    }

    public Color getClr() {
        return clr;
    }

    public void setClr(Color clr) {
        this.clr = clr;
    }

    public void setRow(RowNode row) {
        this.row = row;
    }

}
