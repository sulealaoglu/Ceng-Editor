public class RowNode {
    private int rowNum;
    private RowNode down;
    private RowNode up;
    private CellNode right;
    private CellNode double_tail;

    public RowNode(int dataToAdd){
        rowNum =dataToAdd;
        down=null;
        up=null;
        right=null;
        double_tail=null;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public RowNode getUp() {
        return up;
    }

    public void setUp(RowNode up) {
        this.up = up;
    }

    public RowNode getDown() {
        return down;
    }

    public void setDown(RowNode down) {
        this.down = down;
    }

    public CellNode getRight() {
        return right;
    }

    public void setRight(CellNode right) {
        this.right = right;
    }

    public CellNode getDouble_tail() {
        return double_tail;
    }

    public void setDouble_tail(CellNode double_tail) {
        this.double_tail = double_tail;
    }
}
