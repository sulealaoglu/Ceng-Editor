import enigma.console.TextAttributes;
import enigma.console.java2d.Java2DTextWindow;
import enigma.core.Enigma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
public class Editor {
    KeyListener klis;
    static boolean colorful=false;
    MultiLinkedList mll = new MultiLinkedList();
    int keypr, rkey = 0;
    static int replacedX = 1;
    static boolean musicOn = true;
    static int type = 0;
    static Color foreground = new Color(15, 129, 229);
    static Color descriptionColor = new Color(15, 129, 229);
    static Color highlight = new Color(229, 252, 229, 255);
    Font fonts[][] = {
            {new Font("Monospaced", 0, 13), new Font("Monospaced", 1, 13), new Font("Monospaced", 2, 13)},
            {new Font("Courier New", 0, 15), new Font("Courier New", 1, 15), new Font("Courier New", 2, 15)},
            {new Font("Consolas", 0, 14), new Font("Consolas", 1, 14), new Font("Consolas", 2, 14)},
            {new Font("Lucida Console", 0, 15), new Font("Lucida Console", 1, 15), new Font("Lucida Console", 2, 15)}};
    static int index = 0;
    static Color clr = new Color(210, 120, 120);
    static TextAttributes frame = new TextAttributes(clr, clr);
    static Color paintColor = Color.BLACK;
    TextAttributes reset = new TextAttributes(Color.WHITE);
    static enigma.console.Console console = Enigma.getConsole("Ceng Editor", 130, 35, 15);
    boolean mode = true;
    boolean justified =false;
    int mode_count = 1;
    static enigma.console.java2d.Java2DTextWindow cn = (Java2DTextWindow) console.getTextWindow();
    Menu menu;

    public Editor() {
    }

    public void reader() {
        paint();
        cn.setFont(fonts[index][type]);
        cn.setCursorType(2);
        try {
            FileReader fr = new FileReader("frame.txt");
            BufferedReader br = new BufferedReader(fr);
            int x1 = 0;
            int y1 = 0;
            String line = br.readLine();
            while (line != null) {
                for (int i = 0; i < line.length(); i++) {
                    cn.setCursorPosition(x1, y1);
                    if (line.charAt(i) != ' ') {
                        cn.output(Character.toString(line.charAt(i)), frame);

                    } else {
                        cn.output(" ", reset);
                    }
                    x1++;
                }
                y1++;
                x1 = 0;
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException error) {
            System.out.println("An error occurred");
            error.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String str =
                " F1 :Selection Start+" +
                " F2 :Selection End+" +
                " F3 :Cut+" +
                " F4 :Copy+" +
                " F5 :Paste+" +
                " F6 :Find+" +
                " F7 :Replace+" +
                " F8 :Next+" +
                " F9 :Align Left+" +
                " F10:Justify+" +
                " F11:Load+" +
                " F12:Save++" +
                "Ctrl:Menu+" +
                "+" +
                "Mode:++" +
                "Alignment:";
        int str_x, str_y;
        str_x = 65;
        str_y = 2;
        TextAttributes att = new TextAttributes(descriptionColor);
        TextAttributes reset = new TextAttributes(Color.white);
        TextAttributes attr = att;
        for (int i = 0; i < str.length(); i++) {
            cn.setCursorPosition(str_x, str_y);
            if (str.charAt(i) == ':') {
                cn.output(':', attr);
                str_x++;
                if (attr == reset) {
                    attr = att;
                } else
                    attr = reset;

            } else if (str.charAt(i) == '+') {
                cn.output(" ");
                attr = att;
                str_y++;
                str_x = 65;
            } else {
                str_x++;
                cn.output(str.charAt(i), attr);
            }

        }
        cn.setCursorPosition(str_x - 5, str_y - 2);
        cn.output("Insert");

        cn.setCursorPosition(str_x , str_y );
        cn.output("Aligned to the left");

    }

    public void edit() throws InterruptedException, IOException {

        int next = 1;
        String findWord = null;
        int scroll = 0;
        int x = 1, y = 1;
        reader();
        klis = new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (keypr == 0) {
                    keypr = 1;
                    rkey = e.getKeyCode();
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        };
        cn.addKeyListener(klis);
        mll.addRow(y);
        int row_num = 1;
        Music msc = new Music ();
        cn.setCursorType(0);
        CellNode start = null;
        CellNode end = null;
        CellNode[] copiedNodes = new CellNode[1200];
        boolean capitalControl = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
        while (true) {
            if (colorful) {
                Color clr = new Color(menu.RandomInRange(0,256),menu.RandomInRange(0,256),(menu.RandomInRange(0,256)));
                frame=new TextAttributes(clr,clr);
                reader();
                cn.setCursorType(0);
            }
            if (justified==false) {
                mll.display(scroll);

            }
            else {
                mll.justify(y);
            }
            cn.setCursorPosition(x,y);

            if (keypr == 1) {
                if (rkey == KeyEvent.VK_F1) { //selection start(f1)
                    start = mll.selectStart(x, y);
                } else if (rkey == KeyEvent.VK_F2) { //selection end(f2)
                    end = mll.selectEnd(x, y);
                    if (start != null && start.getI() <= end.getI()) {
                        if (!(start.getI() == end.getI() && start.getJ() > end.getJ())) {
                            if (copiedNodes != null) {
                                mll.resetSelect(copiedNodes);
                            }
                            copiedNodes = mll.select(start, end);
                        }
                    }
                } else if (rkey == KeyEvent.VK_F3) { //cut(f3)
                    if (start != null && start.getI() <= end.getI()) {
                        mll.cut(copiedNodes);
                        x = start.getJ();
                        y = start.getI();
                        row_num = y;
                    }
                } else if (rkey == KeyEvent.VK_F4) { //paste(f4)
                    int old = row_num;
                    mll.addCell(y, x, " ");
                    mll.paste(x + 1, y, copiedNodes);
                    y = mll.sizeRow();
                    x = mll.sizeCell(y) + 1;
                    int diff = y - old;
                    row_num = y;
                    scroll = scrollControl(scroll, diff);
                    mll.resetSelect(copiedNodes);
                    if (y > 20) {
                        y = 19;
                        x = 1;
                    }
                } else if (rkey == KeyEvent.VK_F6) { //find
                    next = 1;
                    findWord = findEvents(scroll, x, y, false)[0];
                    mll.find(findWord, next);
                } else if (rkey == KeyEvent.VK_F8 && findWord != null) {
                    next++;
                    mll.find(findWord, next);
                } else if (rkey == KeyEvent.VK_F7) { //replace
                    next = 1;
                    findEvents(scroll, x, y, true);
                    x = replacedX;
                }else if( rkey==KeyEvent.VK_F9){
                    justified=false;
                    cn.setCursorPosition(75,19);
                    cn.output("                     ");
                    cn.setCursorPosition(75,19);
                    cn.output("Aligned to the left");
                }
                else if( rkey==KeyEvent.VK_F10){
                    justified=true;
                    cn.setCursorPosition(75,19);
                    cn.output("                     ");
                    cn.setCursorPosition(75,19);
                    cn.output("Justified");
                }
                else if (rkey == KeyEvent.VK_F12) {
                    mll.printFile("dosya");

                } else if (rkey == KeyEvent.VK_F11) {
                    mll = new MultiLinkedList();
                    mll.loadFile("dosya.txt", x);

                } else if (rkey == KeyEvent.VK_PAGE_UP) {//up (page up)
                    if (scroll > 0) {
                        x = mll.sizeCell(row_num - 1) + 1;
                        scroll--;
                        row_num--;
                    }
                } else if (rkey == KeyEvent.VK_CONTROL) {
                    menu = new Menu();
                    menu.clearMenu();
                    klis = new KeyListener() {
                        public void keyTyped(KeyEvent e) {
                        }

                        public void keyPressed(KeyEvent e) {
                            if (keypr == 0) {
                                keypr = 1;
                                rkey = e.getKeyCode();
                            }
                        }

                        public void keyReleased(KeyEvent e) {
                        }
                    };
                    reader();
                    Font newFont = fonts[index][type];
                    cn.setFont(fonts[index][type]);
                    mll.setBackAttr();
                    cn.addKeyListener(klis);
                    cn.setCursorType(0);
                } else if (rkey == KeyEvent.VK_PAGE_DOWN) {//down
                    scroll++;
                    int size = mll.sizeRow();
                    if (mll.sizeRow() == row_num) {
                        mll.addRow(size + 1);
                        x = 1;
                    } else {
                        x = mll.sizeCell(row_num + 1) + 1;
                    }
                    row_num++;
                } else if (rkey == KeyEvent.VK_HOME) { //home
                    x = 1;
                    cn.setCursorPosition(x, y);
                } else if (rkey == KeyEvent.VK_END) { //end
                    x = mll.sizeCell(x) + 1;
                    cn.setCursorPosition(x, y);
                } else if (rkey == KeyEvent.VK_INSERT) {
                    cn.setCursorPosition(76, 16);
                    cn.output("   ");
                    mode_count++;
                    cn.setCursorPosition(70, 16);
                    if (mode_count % 2 != 0) {
                        cn.output("Insert");
                        mode = true;
                    } else {
                        cn.output("Overwrite");
                        mode = false;
                    }
                } else if ((rkey > 64 && rkey < 91) || (rkey > 96 && rkey < 123) || (rkey > 47 && rkey < 58) || (rkey == 58)
                        || (rkey == 59) || (rkey == 63) || (rkey == KeyEvent.VK_MULTIPLY) || (rkey > 43 && rkey < 47)) {
                    String str = Character.toString((char) rkey);
                    if (musicOn)
                        msc.playMusic("key.wav");
                    if (rkey == KeyEvent.VK_MULTIPLY)
                        str = "*";
                    if (rkey == KeyEvent.VK_DIVIDE)
                        str = "/";
                    if (capitalControl)
                        str = str.toUpperCase(Locale.ROOT);
                    else
                        str = str.toLowerCase(Locale.ROOT);
                    if (mode) {
                        mll.addCell(row_num, x, str);
                        x++;
                    } else {
                        mll.overwrite(row_num, x, str);
                        x++;
                    }
                    int check = mll.isLineFull(x, row_num, false, mode);
                    if (check > 0) {
                        row_num++;
                        y++;
                        x = check;
                    } else if (check == -1) {
                        x = 1;
                        y++;
                        row_num++;
                    } else if (check == -2) //default insert
                    {
                        if (x == 62) {
                            row_num++;
                            x = 2;
                            y++;
                        } else if (x == 61) {
                            row_num++;
                            x = 1;
                            y++;
                        }

                    } else if (check == -3) { //overwrite sona yapılıyosa
                        y++;
                        row_num++;
                        x = 1;
                    }
                } else if (rkey == KeyEvent.VK_SPACE && mode) {
                    mll.addCell(row_num, x, " ");
                    x++;
                    boolean flag = x >= 59;
                    int check = mll.isLineFull(x, row_num, flag, mode);
                    if (check > 0) {
                        row_num++;
                        y++;
                        x = check;
                    } else if (check == -1) {
                        x = 1;
                        y++;
                        row_num++;
                    } else if (check == -2) //default insert
                    {
                        if (x == 62) {
                            row_num++;
                            x = 2;
                            y++;
                        } else if (x == 61) {
                            row_num++;
                            x = 1;
                            y++;
                        }
                    } else if (check == -3) { //overwrite sona yapılıyosa
                        y++;
                        row_num++;
                        x = 1;
                    }
                } else if (rkey == KeyEvent.VK_BACK_SPACE) {
                    if (x > 1) {
                        mll.remove(x, row_num);
                        x--;
                    } else if (y >= 2 && mll.isLineEmpty(x, row_num)) {
                        int check = mll.removeRow(row_num); //bir üst satırdaki x'in konumunu döndürüyor (set cursor icin)
                        if (check != 0) { //-1 ise satır silinmemiş demektir
                            y--;
                            row_num--;
                            x = check + 1;
                        }
                    } else if (row_num >= 2 && x == 1) {
                        mll.deleteWords(y);
                        int size = mll.sizeCell(row_num - 1);
                        if (y == 1)
                            scroll--;
                        else
                            y--;
                        row_num--;
                        x = size + 1;

                    }
                } else if (rkey == KeyEvent.VK_DELETE) {
                    if (x > 1 && x != mll.sizeCell(row_num) + 1) {
                        mll.remove(x + 1, row_num);
                    } else if (y >= 2 && mll.isLineEmpty(x, row_num)) {
                        int check = mll.removeRow(row_num);
                        if (check != -1) {
                            y--;
                            row_num--;
                            x = check + 1;
                        }
                    }
                } else if (rkey == KeyEvent.VK_CAPS_LOCK) {
                    capitalControl = !capitalControl;

                } else if (rkey == KeyEvent.VK_ENTER && y < 20) {
                    cn.setCursorPosition(x, y);
                    mll.enterEvents(x, row_num);
                    x = 1;
                    y++;
                    row_num++;



                } else if (rkey == KeyEvent.VK_SHIFT) {
                    if (shiftEvents(x, y)) {
                        x++;
                        int check = mll.isLineFull(x, row_num, false, mode);
                        if (check > 0) {
                            row_num++;
                            y++;
                            x = check;
                        } else if (check == -1) {
                            x = 1;
                            y++;
                            row_num++;
                        } else if (check == -2) //default insert
                        {
                            if (x == 62) {
                                row_num++;
                                x = 2;
                                y++;
                            } else if (x == 61) {
                                row_num++;
                                x = 1;
                                y++;
                            }

                        } else if (check == -3) { //overwrite sona yapılıyosa
                            y++;
                            row_num++;
                            x = 1;
                        }
                    }
                } else if (rkey == KeyEvent.VK_LEFT && x > 1) {
                    x--;
                } else if (rkey == KeyEvent.VK_RIGHT && x < 61 && mll.sizeCell(row_num) + 1 > x) {
                    x++;
                } else if (rkey == KeyEvent.VK_UP && y > 1) {
                    y--;
                    if (mll.sizeCell(row_num) > mll.sizeCell(row_num - 1))
                        x = mll.sizeCell(row_num - 1) + 1;
                    row_num--;
                } else if (rkey == KeyEvent.VK_DOWN && y < 20 && mll.sizeRow() > row_num) {
                    y++;
                    if (mll.sizeCell(row_num + 1) <= mll.sizeCell(row_num))
                        x = mll.sizeCell(row_num + 1) + 1;
                    row_num++;
                }
                if (musicOn)
                    msc.playMusic("key.wav");
            }
            keypr = 0;
            Thread.sleep(20);
        }
    }

    public String[] findEvents(int scroll, int cursor_x, int cursor_y, boolean isReplace) throws InterruptedException {
        boolean flag = false;
        CellNode[] nodes;
        int next=1;
        String[] words = new String[2];
        String first = JOptionPane.showInputDialog("The word you want to find:");
        words[0] = first;
        nodes = mll.find(words[0], next);
        if (isReplace) {
            while (true) {
                mll.display(scroll);
                keypr = 0;
                while (true) {
                    Thread.sleep(10);
                    if (keypr == 1)
                        break;
                }
                if (keypr == 1) {
                    if ((rkey == KeyEvent.VK_F6 || rkey == KeyEvent.VK_F7)) {
                        if (words[0] != null) {
                            flag = false;
                            if (rkey == KeyEvent.VK_F7 && words[0] != null) {
                                String word = JOptionPane.showInputDialog("The word you want to replace:");
                                words[1] = word;
                                if (nodes != null) {
                                    mll.replace(nodes, words[1]);
                                    replacedX = mll.sizeCell(cursor_y) + 1;
                                    break;
                                }
                                return words;
                            } else
                                return words;
                        }
                    }
                    if (rkey == KeyEvent.VK_F8) {
                        if (words[0] != null) {
                            next++;
                            nodes = mll.find(words[0], next);
                            mll.display(scroll);
                        }
                    }
                }
                Thread.sleep(20);
                keypr = 0;
            }
        }
        return words;
    }

    public boolean shiftEvents(int x, int y) throws InterruptedException {
        while (true) {
            keypr = 0;
            while (true) {
                Thread.sleep(10);
                if (keypr == 1)
                    break;
            }
            if (keypr == 1) {
                String str = null;
                if (rkey == 46) {
                    str = ":";
                } else if (rkey == 44) {
                    str = ";";
                } else if (rkey == 49) {
                    str = "!";
                } else if (rkey == 52) {
                    str = "+";
                } else if (rkey == 151) {
                    str = "?";
                } else if (rkey == 16)
                    continue;
                if (str != null) {
                    mll.addCell(y, x, str);
                    return true;
                } else return false;
            }
        }
    }

    public void paint() {
        TextAttributes attr = new TextAttributes(paintColor, paintColor);
        console.setTextAttributes(attr);
        for (int i = 1; i < 21; i++) {
            for (int j = 1; j < 60; j++) {
                cn.output(j, i, ' ', attr);
            }
        }
    }

    public int scrollControl(int scroll, int diff) {
        int a = 0;
        if (mll.sizeRow() >= 20 && mll.sizeCell(20 + scroll) != 0 && diff != 0) {
            a = mll.sizeRow() - 20 + 1;
        }
        return a;
    }

}
