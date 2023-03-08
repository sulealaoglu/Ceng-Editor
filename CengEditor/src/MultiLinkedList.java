import enigma.console.TextAttributes;

import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class MultiLinkedList {
    private RowNode head;
    private RowNode tail;
    private CellNode cellTail;

    public MultiLinkedList() {
        head = null;
        tail = null;
        cellTail = null;
    }

    public void addRow(int dataToAdd) {
        if (head == null) {
            RowNode newnode = new RowNode(dataToAdd);
            head = newnode;
            tail = newnode;

        } else {
            boolean flag = true;
            RowNode newnode = new RowNode(dataToAdd);
            RowNode temp = head;
            RowNode prev = head;
            while (temp != null) {//araya ekleme
                if (temp.getRowNum() >= dataToAdd) {
                    newnode.setDown(temp);
                    newnode.setUp(prev);
                    prev.setDown(newnode);
                    temp.setUp(newnode);
                    flag = false;
                    break;
                }
                prev = temp;
                temp = temp.getDown();
            }
            if (flag) {//sona ekleme
                newnode.setUp(tail);
                tail.setDown(newnode);
                tail = newnode;
            }
            updateRowsData();
        }
    }

    public void updateRowsData() {
        int num = 1;
        RowNode temp = head;
        while (temp != null) {
            temp.setRowNum(num);
            num++;
            temp = temp.getDown();
        }
    }

    public void updateCellPositions() {
        int i = 1;
        int j;
        RowNode temp = head;
        while (temp != null) {
            j = 1;
            CellNode cell = temp.getRight();
            while (cell != null) {
                cell.setI(i);
                cell.setJ(j);
                cell = cell.getNext();
                j++;
            }
            temp = temp.getDown();
            i++;
        }
    }

    public void enterEvents(int x, int y) {
        int count = 1;
        RowNode tempRow = head;
        CellNode tempCell = null;
        boolean flag = false;
        while (tempRow != null) {
            if (y == tempRow.getRowNum()) {
                tempCell = tempRow.getRight();
                break;
            }
            tempRow = tempRow.getDown();
        }
        addRow(y + 1);
        updateRowsData();
        int cellNum = 1;
        while (tempCell != null) {
            if (x == count) {
                flag = true;
            }
            if (flag) {
                addCell(y + 1, cellNum, tempCell.getData());
                tempCell = tempCell.getNext();
                if (x == 1) {
                    tempRow.setRight(null);
                } else
                    remove(x + 1, y);
                cellNum++;
            } else
                tempCell = tempCell.getNext();
            count++;
        }
    }

    public CellNode addCell(int row, int cellNum, Object data) {
        CellNode newnode=null;
        boolean flag = true;
        if (head == null)
            System.out.println("add a row before cell");
        else {
            RowNode temp = head;
            while (temp != null) {
                if (row == temp.getRowNum()) {
                    CellNode temp2 = temp.getRight();
                    if (temp2 == null) {
                        newnode = new CellNode(data);
                        temp.setRight(newnode);
                        newnode.setPrevRow(temp);
                        newnode.setRow(temp);
                        newnode.setI(row);
                        newnode.setJ(cellNum);
                    } else {
                        while (temp2 != null) {
                            if (temp2.getJ() == cellNum) {
                                if (cellNum == 1) {  // add beginning of the list
                                    newnode = new CellNode(data);
                                    temp.setRight(newnode);
                                    newnode.setPrevRow(temp);
                                    temp2.setPrevRow(null);
                                    newnode.setNext(temp2);
                                    temp2.setPrev(newnode);
                                    newnode.setI(row);
                                    newnode.setJ(cellNum);
                                    newnode.setRow(temp);
                                    reposition(cellNum + 1, row, temp2);
                                    flag = false;
                                    break;
                                } else { // add between the nodes
                                    newnode = new CellNode(data);
                                    temp2.getPrev().setNext(newnode);
                                    newnode.setPrev(temp2.getPrev());
                                    newnode.setNext(temp2);
                                    temp2.setPrev(newnode);
                                    newnode.setI(row);
                                    newnode.setJ(cellNum);
                                    newnode.setRow(temp);
                                    reposition(cellNum + 1, row, temp2);
                                    flag = false;
                                    break;
                                }
                            }
                            cellTail = temp2;
                            temp2 = temp2.getNext();
                        }
                        if (!flag) break;
                        else { // add end of the list
                            newnode = new CellNode(data);
                            cellTail.setNext(newnode);
                            newnode.setPrev(cellTail);
                            newnode.setI(row);
                            newnode.setJ(cellNum);
                            newnode.setRow(temp);
                            break;
                        }
                    }
                }
                temp = temp.getDown();
            }
        }
        return newnode;
    }

    public void overwrite(int row, int cellNum, Object data) {
        RowNode temp = head;
        while (temp != null) {
            if (row == temp.getRowNum()) {
                CellNode temp2 = temp.getRight();
                CellNode newNode = new CellNode(data);
                if (temp2 != null) {
                    while (temp2 != null) {
                        if (temp2.getJ() == cellNum) {
                            temp2.setData(newNode.getData());
                        }
                        temp2 = temp2.getNext();
                    }
                }
            }
            temp = temp.getDown();
        }
    }

    public void reposition(int x, int y, CellNode node) {
        while (node != null) {
            if (x == 61) {
                x = 1;
                y++;
                node.setJ(x);
                node.setI(y);
                node = node.getNext();
                x++;
            } else {
                node.setJ(x);
                node.setI(y);
                node = node.getNext();
                x++;
            }

        }

    }

    public boolean isLineEmpty(int x, int y) {
        RowNode temp = head;
        while (temp != null) {
            if (y == temp.getRowNum()) {
                break;
            }
            temp = temp.getDown();
        }
        return temp != null && temp.getRight() == null && x == 1;
    }

    public int removeRow(int y) {
        int check = -1;
        RowNode temp = head;
        while (temp != null) {
            if (y == temp.getRowNum()) {
                break;
            }
            temp = temp.getDown();
        }
        if (temp.getRight() == null && y >= 2) { //DELETE ROW
            RowNode deleted = temp;
            check = sizeCell(deleted.getUp().getRowNum());
            if (deleted == tail) {
                tail = deleted.getUp();
            }
            if (deleted.getDown() != null)
                deleted.getDown().setUp(deleted.getUp());
            if (deleted.getUp() != null)
                deleted.getUp().setDown(deleted.getDown());
        }
        updateRowsData();
        return check;
    }

    public void clear(TextAttributes attr) {
        for (int i = 1; i < 61; i++) {
            for (int j = 1; j < 21; j++) {
                Editor.cn.output(i, j, ' ', attr);
            }
        }
    }

    public void moveWords(int y, RowNode temp) {
        if (sizeCell(temp.getRowNum()) >= 60) {
            while (sizeCell(temp.getRowNum()) >= 60) {
                CellNode cell = temp.getRight();
                CellNode space = cell;
                while (cell != null) {
                    if (cell.getData() == " " && cell.getNext() != null)
                        space = cell;
                    cell = cell.getNext();
                }
                if (sizeRow() < y + 1)
                    addRow(sizeRow() + 1);
                updateRowsData();
                if (space.getData() != " ")
                    break;
                int num = 0;
                space = space.getNext();
                while (space != null) {
                    num++;
                    Object data = space.getData();
                    remove(space.getJ() + 1, y);
                    updateCellPositions();
                    addCell(y + 1, num, data);
                    space = space.getNext();
                }
                updateCellPositions();
            }
            while (temp.getDown() != null) {
                temp = temp.getDown();
                moveWords(y + 1, temp);
            }
        }
    }

    public void deleteWords(int y) {
        RowNode temp = head;
        while (temp != null) {
            if (y == temp.getRowNum())
                break;
            temp = temp.getDown();
        }
        if (temp != null) {
            RowNode upRow = temp.getUp();
            if (upRow != null) {
                CellNode temp_cell = upRow.getRight();
                while (temp_cell.getNext() != null)
                    temp_cell = temp_cell.getNext(); //last node
                int cellNum = temp_cell.getJ() + 1;
                cellNum++;
                CellNode cell = temp.getRight();
                while (cell != null) {
                    addCell(y - 1, cellNum, cell.getData());
                    cellNum++;
                    cell = cell.getNext();
                }
                temp.setRight(null);
                updateRowsData();
                updateCellPositions();
                moveWords(y - 1, upRow);
            }
        }
    }

    public CellNode selectStart(int x, int y) {
        RowNode temp = head;
        while (temp != null) {
            if (temp.getRowNum() == y)
                break;
            temp = temp.getDown();
        }
        CellNode cell = temp.getRight();
        while (cell != null) {
            if (cell.getJ() == x)
                break;
            cell = cell.getNext();
        }
        return cell;
    }

    public CellNode selectEnd(int x, int y) {
        RowNode temp = head;
        while (temp != null) {
            if (temp.getRowNum() == y)
                break;
            temp = temp.getDown();
        }
        CellNode cell = temp.getRight();
        int count = 1;
        while (cell != null) {
            if (cell.getJ() == x - 1)
                break;
            cell = cell.getNext();
            count++;
        }
        return cell;
    }

    public CellNode[] select(CellNode start, CellNode end) {
        CellNode[] nodes = new CellNode[1200];
        int i = 0;
        Color back = Editor.highlight;
        Color clr =Editor.foreground;
        TextAttributes attr = new TextAttributes(clr,back);
        CellNode temp = start;
        RowNode tempRow = head;
        if (start == end) {
            temp.setAttr(attr);
            nodes[0] = temp;
            return nodes;
        }

        while (tempRow != null) {
            if (tempRow.getRowNum() == start.getI())
                break;
            tempRow = tempRow.getDown();
        }
        boolean breakCheck = false;
        while (tempRow != null) {
            while (temp != null) {
                temp.setAttr(attr);
                nodes[i] = temp;
                temp = temp.getNext();
                if (temp == end) {
                    i++;
                    end.setAttr(attr);
                    nodes[i] = temp;
                    breakCheck = true;
                    break;
                }

                i++;
            }
            if (breakCheck)
                break;
            tempRow = tempRow.getDown();
            if (tempRow == null)
                break;
            temp = tempRow.getRight();
        }
        return nodes;
    }

    public void replace(CellNode[] nodes,String word) {
        int x = 0, y = 0;
        CellNode startNode = nodes[0];
        if (startNode != null) {
            x = startNode.getJ();
            y = startNode.getI();
        }
        cut(nodes);
        int i=0;
        CellNode[] replacedNodes=new CellNode[1200];
        String letter;
        while (i<word.length())
            {  letter=String.valueOf(word.charAt(i));
                CellNode node=new CellNode(letter);
                replacedNodes[i]= node;
                i++;
                updateCellPositions();
                updateRowsData();
            }
        paste(x,y,replacedNodes);
    }

    public void cut(CellNode[] copied) {
        CellNode temp;
        int start = copied[0].getI();
        for (int i = 0; i < copied.length - 1; i++) {
            if (copied[i] == null)
                break;
            temp = copied[i];
            RowNode tempRow = head;
            while (tempRow != null) {
                if (tempRow == temp.getRow())
                    break;
                tempRow = tempRow.getDown();
            }
            CellNode cell = tempRow.getRight();
            while (cell != null) {
                if (temp == cell)
                    break;
                cell = cell.getNext();
            }
            remove(temp.getJ() + 1, temp.getI());
            updateRowsData();
            updateCellPositions();

        }
        deleteWords(start + 1);
    }

    public void resetSelect(CellNode[] copied) {
        for (int i = 0; i < copied.length - 1; i++) {
            if (copied[i] == null)
                break;
            Color clr=copied[i].getClr();
            Color back=copied[i].getBack();
            TextAttributes attr = new TextAttributes(clr, back);
            copied[i].setAttr(attr);
        }
    }

    public void paste(int x, int y, CellNode[] copied) {
        CellNode cell;
        int count = x;
        for (int i = 0; i < copied.length - 1; i++) {
            if (copied[i] == null)
                break;
            cell = copied[i];
            addCell(y, count, cell.getData());
            count++;
        }
        RowNode temp = head;
        while (temp != null) {
            if (temp.getRowNum() == y) {
                break;
            }
            temp = temp.getDown();
        }
        updateRowsData();
        updateCellPositions();
        moveWords(y, temp);
        paste_move(y, temp);
    }

    public CellNode[] find(String str, int next) {
        int count = 0;
        CellNode preselect[] = null;
        RowNode temp = head;
        while (temp != null) {
            String tempword = null;
            int j = 1;
            CellNode[] word = new CellNode[1200];
            CellNode temp2 = temp.getRight();
            word[0] = temp2;
            if (temp2 != null){
                tempword = (String) temp2.getData();
                temp2 = temp2.getNext();
            }
            while (temp2 != null) {
                tempword = tempword + (String) temp2.getData();
                word[j] = temp2;
                j++;
                temp2 = temp2.getNext();
            }
            if (tempword!=null&&tempword.length() == str.length() && tempword.equalsIgnoreCase(str)) {
                count++;
                if (count <= next) {
                    if (preselect != null) resetSelect(preselect);
                    preselect = select(word[0], word[str.length() - 1]);
                }
            }
            if (tempword!=null&&tempword.length() >= str.length()) {
                for (int i = 0; i <= tempword.length() - str.length(); i++) {
                    if (tempword.substring(i, i + str.length()).equalsIgnoreCase(str)) {
                        count++;
                        if (count <= next) {
                            if (preselect != null) resetSelect(preselect);
                            preselect = select(word[i], word[i + str.length() - 1]);
                        }
                    }
                }
            }
            temp = temp.getDown();
        }
        return preselect;
    }

    public int wordSize(int y) {
        int number = 0;
        RowNode temp = head;
        while (temp != null) {
            if (temp.getRowNum() == y)
                break;
            temp = temp.getDown();
        }
        CellNode cell = temp.getRight();
        CellNode space = cell;
        while (cell != null) {
            if (cell.getData() == " ")
                space = cell;
            cell = cell.getNext();
        }
        while (space != null) {
            number++;
            space = space.getNext();
        }
        return number;
    }
    public void setBackAttr(){
        RowNode temp=head;
        CellNode temp2;
        while (temp!=null){
            temp2= temp.getRight();
            while (temp2!=null){
                temp2.setAttr(new TextAttributes(temp2.getClr(),Editor.paintColor));
                temp2=temp2.getNext();
            }
            temp=temp.getDown();
        }
    }
    public void display(int scroll) {
        TextAttributes attr = new TextAttributes(Editor.foreground,Editor.paintColor);
        clear(attr);
        RowNode temp=head;
        int cellNum = 1;
        if (head == null)
            System.out.println("linked list is empty1");
        else {
            while (temp != null) {
                cellNum = 1;

                if (temp.getRowNum() >= scroll + 1) {
                    CellNode temp2 = temp.getRight();
                    while (temp2 != null) {
                        int rowNumber = temp.getRowNum() - scroll;
                        Editor.cn.setCursorPosition(cellNum, rowNumber);
                        Object data = temp2.getData();
                        attr = temp2.getAttr();
                        Editor.cn.output(data + "", attr);
                        temp2 = temp2.getNext();
                        cellNum++;
                    }
                }
                if (temp.getRowNum() == scroll + 20)
                    break;
                temp = temp.getDown();
            }
        }
    }
    public void justify (int rowNum) {
        TextAttributes attr = new TextAttributes(Editor.foreground, Editor.paintColor);
        clear(attr);
        int i = 0;
        int empty = 0;
        int cellNum = 1;
        RowNode temp = head;
        while (temp != null) {
            if (temp.getRight() != null) {
                cellNum = 1;
                empty = 60 - searchCell(temp.getRowNum()).getJ();
                if (empty > emptyCounter(temp.getRowNum())) {
                    i = (empty / emptyCounter(temp.getRowNum())) + 1;
                } else if (emptyCounter(temp.getRowNum()) > empty) {
                    i = (emptyCounter(temp.getRowNum()) / empty) + 1;
                } else i = 1;
                if (temp.getRowNum() <= rowNum && empty != 0) {
                    CellNode temp2 = temp.getRight();
                    while (temp2 != null) {
                        String data = temp2.getData().toString();
                        if (temp2.getData().equals(" ") && empty > 0 && temp2.getNext() != null && temp2.getPrevRow() == null) {
                            Editor.cn.setCursorPosition(cellNum, temp.getRowNum());
                            attr = temp2.getAttr();
                            Editor.cn.output(data + "", attr);
                            cellNum++;
                            for (int j = 0; j < i; j++) {
                                if (empty <= 0) break;
                                Editor.cn.setCursorPosition(cellNum, temp.getRowNum());
                                Editor.cn.output("" + "", attr);
                                cellNum++;
                                empty--;
                            }
                            temp2 = temp2.getNext();

                        } else {
                            int rowNumber = temp.getRowNum();
                            Editor.cn.setCursorPosition(cellNum, rowNumber);
                            Object data1 = temp2.getData();
                            attr = temp2.getAttr();
                            Editor.cn.output(data1 + "", attr);
                            cellNum++;
                            temp2 = temp2.getNext();
                        }
                    }
                }

            }

            temp = temp.getDown();
        }

    }
    public int emptyCounter(int y) {
        int count=0;
        RowNode temp=head;
        while(temp!=null){
            if(temp.getRowNum()==y) break;
            temp=temp.getDown();
        }
        CellNode temp2=temp.getRight();
        while(temp2!=null){
            if(temp2.getData().equals(" ")) count++;
            temp2=temp2.getNext();
        }
        return count;

    }
    public CellNode searchCell(int y) {
        RowNode temp = head;
        while (temp != null) {
            if (temp.getRowNum() == y)
                break;
            temp = temp.getDown();
        }
        CellNode temp2=temp.getRight();
        while(temp2.getNext()!=null){
            temp2=temp2.getNext();
        }
        return temp2;
    }

    public int sizeRow() {
        int count = 0;
        if (head == null)
            System.out.println("linked list is empty");
        else {
            RowNode temp = head;
            while (temp != null) {
                count++;
                temp = temp.getDown();
            }
        }
        return count;
    }

    public int sizeCell(int number) {
        int counter = 0;
        RowNode temp = head;
        while (temp != null) {
            if (number == temp.getRowNum()) {
                break;
            }
            temp = temp.getDown();
        }
        if (temp == null)
            return 0;
        else {
            CellNode temp2 = temp.getRight();
            while (temp2 != null) {
                counter++;
                temp2 = temp2.getNext();
            }
        }
        return counter;
    }

    public void remove(int x, int y) {
        RowNode temp = head;
        CellNode cell = null;
        while (temp != null) {
            if (y == temp.getRowNum()) {
                break;
            }
            temp = temp.getDown();
        }
        int a = 0;
        int count = 1;
        CellNode current = temp.getRight();
        while (current != null) {
            if (count == x - 1) {
                cell = current;
                break;
            }
            count++;
            current = current.getNext();
        }
        if (current == null) {
            System.out.println(a);
        }
        if (cell != null && x == 2) {
            temp.setRight(cell.getNext());
            if (cell.getNext() != null)
                cell.getNext().setPrevRow(temp);
        }
        if (cell.getNext() != null && cell.getPrev() != null)
            cell.getNext().setPrev(cell.getPrev());
        if (cell.getPrev() != null)
            cell.getPrev().setNext(cell.getNext());
        if (cell == temp.getDouble_tail()) {
            temp.setDouble_tail(cell.getPrev());
            if (temp.getDouble_tail() == null) {
                temp.setRight(null);
                return;
            }
            temp.getDouble_tail().setNext(null);
            updateRowsData();
            updateCellPositions();
        }
    }

    public void move(int y, RowNode temp) {
        if (sizeCell(temp.getRowNum()) > 60) {
            while (sizeCell(temp.getRowNum()) > 60) {
                CellNode tempCell = temp.getRight();
                while (tempCell.getNext() != null)
                    tempCell = tempCell.getNext(); //last node
                if (sizeRow() < y + 1)
                    addRow(sizeRow() + 1);
                Object data = tempCell.getData();
                remove(62, y);
                addCell(y + 1, 1, data);
                updateCellPositions();
            }
            while (temp.getDown() != null) {
                temp = temp.getDown();
                move(y + 1, temp);
            }
        }
    }

    public void paste_move(int y, RowNode temp) {
        if (sizeCell(temp.getRowNum()) > 60) {
            while (sizeCell(temp.getRowNum()) > 60) {
                CellNode tempCell = temp.getRight();
                int counter = 1;
                while (tempCell.getNext() != null) {
                    tempCell = tempCell.getNext(); //61den buyuk node
                    if (counter == 61)
                        break;
                    counter++;
                }
                if (sizeRow() < y + 1)
                    addRow(sizeRow() + 1);
                Object data = tempCell.getData();
                remove(62, y);
                addCell(y + 1, 1, data);
                updateCellPositions();
            }
            while (temp.getDown() != null) {
                temp = temp.getDown();
                move(y + 1, temp);
            }
        }
    }

    public int isLineFull(int x, int y, boolean flag, boolean insertMode) {
        int check = 0;
        if (insertMode) {
            RowNode temp = head;
            check = 0;
            while (temp != null) {
                if (temp.getRowNum() == y) {
                    if (x == 61 || sizeCell(temp.getRowNum()) >= 60) { //insert when line is full
                        if (sizeCell(temp.getRowNum()) >= 60) {
                            check = wordSize(y);
                            moveWords(y, temp);
                            if (check >= 60) {
                                check = -2;
                                move(y, temp);
                                return check;
                            }
                            return check;
                        } else {
                            addRow(sizeRow() + 1);
                            if (!flag) { //satır sonunda kelimeye devam mı ediyor space ile baska kelimeye mi geçiyor
                                check = wordSize(y);
                                moveWords(y, temp);
                            } else
                                check = -1;
                            updateRowsData();
                            return check;
                        }
                    }
                }
                temp = temp.getDown();
            }
        } else {
            if (x == 62) {
                check = -3;
            }
        }
        return check;
    }

    public void loadFile(String path, int x) throws IOException {
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        int y = 1;
        while (line != null) {
            addRow(y);
            for (int i = 0; i < line.length(); i++) {
                Object letter = Character.toString(line.charAt(i));
                if (letter.equals(" ")) {
                    letter=" ";
                }
                addCell(y, x, letter);
                x++;
            }
            line = br.readLine();
            y++;
        }
        br.close();
        moveWords(1,head);
    }

    public void printFile(String name) throws IOException {
        File file = new File(name + ".txt");//
        BufferedWriter br = new BufferedWriter(new FileWriter(file));
        RowNode temp = head;
        while (temp != null) {
            CellNode temp2 = temp.getRight();
            while (temp2 != null) {
                br.write(temp2.getData().toString());
                temp2 = temp2.getNext();
            }
            temp = temp.getDown();
            br.newLine();
        }
        br.close();
    }

}


