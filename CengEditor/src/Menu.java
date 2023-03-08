import enigma.console.TextAttributes;
import enigma.console.java2d.Java2DTextWindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Menu {
    private KeyListener klis;
    private int keypr, rkey = 0;
    private Java2DTextWindow cn = Editor.cn;
    Color backcolor = new Color(0xf3d5bf);
    int i;
    int j;
    private TextAttributes back;
    String[][] categories = {{"Text Color", "Color1", "Color2", "Color3", "Color4", "Color5","Color6","Color7","Color8","Color9","Color10"}, {"Highlight Color", "a", "b", "c", "d", "e"}, {"Theme", "Theme1", "Theme2", "Theme3", "Theme4", "Theme5"}, {"Sound", "on", "off"},
            {"Font", "Monospaced", "Courier New", "Consolas", "Lucida Console"}, {"Type", "Plain", "Bold", "Italik"}};

    public Menu() throws InterruptedException {
         TextAttributes back=new TextAttributes(Color.YELLOW.brighter());
        int x = 30, y = 23;
        cn.setCursorPosition(30, 23);
        i = 2; j=0;
        printMenu();
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
        cn.setCursorType(2);
        Boolean isOn=true;
        while (isOn) {
            printMenu();
                if (keypr == 1) {
                    if (rkey == KeyEvent.VK_LEFT &&j==0) {
                        if (i == 0)
                            i = 5;
                        else
                            i--;
                        cn.setCursorPosition(30, 23);
                        cn.output(categories[i][0],back);
                    } else if (rkey == KeyEvent.VK_RIGHT&&j==0) {
                        if (i == 5)
                            i = 0;
                        else
                            i++;
                        cn.setCursorPosition(30, 23);
                        cn.output(categories[i][0],back);
                    }
                    else if (rkey == KeyEvent.VK_UP && i == 0) {
                        if (j == 0) {
                            j = 10;
                            y = 33;
                        } else {
                            j--;
                            y--;
                        }
                        cn.setCursorPosition(30, y);
                        cn.output(categories[i][j]);
                    }
                    else if (rkey == KeyEvent.VK_DOWN && i == 0) {
                        if (j == 10) {
                            j = 0;
                            y = 23;
                        } else {
                            j++;
                            y++;
                        }
                        cn.setCursorPosition(30, y);
                        cn.output(categories[i][j]);
                    }
                    else if (rkey == KeyEvent.VK_UP && i == 4) {
                        if (j == 0) {
                            j = 4;
                            y = 27;
                        } else {
                            j--;
                            y--;
                        }
                        cn.setCursorPosition(30, y);
                        cn.output(categories[i][j]);
                    }
                     else if (rkey == KeyEvent.VK_DOWN && i == 4) {
                        if (j == 4) {
                            j = 0;
                            y = 23;
                        } else {
                            j++;
                            y++;
                        }

                        cn.setCursorPosition(30, y);
                        cn.output(categories[i][j]);
                    }  else if (rkey == KeyEvent.VK_DOWN && i == 3) {
                        if (j == 2) {
                            j = 0;
                            y = 23;
                        } else {
                            j++;
                            y++;
                        }

                        cn.setCursorPosition(30, y);
                        cn.output(categories[i][j],back);
                    } else if (rkey == KeyEvent.VK_UP && i == 3) {
                        if (j == 0) {
                            j = 2;
                            y = 25;
                        } else {
                            j--;
                            y--;
                        }
                        cn.setCursorPosition(30, y);
                        cn.output(categories[i][j]);
                    } else if (rkey == KeyEvent.VK_DOWN && i == 5) {
                        if (j == 3) {
                            j = 0;
                            y = 23;
                        } else {
                            j++;
                            y++;
                        }

                        cn.setCursorPosition(30, y);
                        cn.output(categories[i][j]);
                    }
                    else if (rkey == KeyEvent.VK_UP && i == 5) {
                        if (j == 0) {
                            j = 3;
                            y = 26;
                        } else {
                            j--;
                            y--;
                        }
                        cn.setCursorPosition(30, y);
                        cn.output(categories[i][j]);
                    } else if (rkey == KeyEvent.VK_UP) {
                        if (j == 0) {
                            j = 5;
                            y = 28;
                        } else {
                            j--;
                            y--;
                        }

                        cn.setCursorPosition(30, y);
                        cn.output(categories[i][j],back);
                    } else if (rkey == KeyEvent.VK_DOWN) {
                        if (j == 5) {
                            j = 0;
                            y = 23;
                        } else {
                            j++;
                            y++;
                        }
                        cn.setCursorPosition(30, y);
                        cn.output(categories[i][j],back);
                    }

                    else if (rkey == KeyEvent.VK_CONTROL) {
                        isOn=false;
                        break;
                    }
                    else if (rkey == KeyEvent.VK_ENTER) {
                        switch (i) {
                            case 0: {//color
                                Color color;
                                switch (j) {
                                    case 1:
                                        Editor.foreground=Color.blue;
                                        break;
                                    case 2:
                                        Editor.foreground=new Color(107, 198, 166);
                                        break;
                                    case 3:
                                        Editor.foreground=new Color(248, 123, 85);
                                        break;
                                    case 4:
                                        Editor.foreground=new Color(241, 208, 53);
                                        break;
                                    case 5:
                                        Editor.foreground=Color.magenta;
                                        break;
                                    case 6:
                                        Editor.foreground=Color.BLACK;
                                        break;
                                    case 7:
                                        Editor.foreground= Color.WHITE;
                                        break;
                                    case 8:
                                        Editor.foreground=new Color(53, 117, 245);
                                        break;
                                    case 9:
                                        Editor.foreground=new Color(181, 161, 203);
                                        break;
                                    case 10:
                                        Editor.foreground= new Color(234, 95, 37);
                                        break;
                                }
                                break;
                            }
                            case 1: {// higlight color
                                switch (j) {
                                    case 0:
                                        Editor.highlight=new Color(200,200,200);
                                        break;
                                    case 1:
                                        Editor.highlight=new Color(25, 249, 152).brighter();
                                        break;
                                    case 2:
                                        Editor.highlight=new Color(104, 50, 182);
                                        break;
                                    case 3:
                                        Editor.highlight=new Color(236, 235, 57).brighter();
                                        break;
                                    case 4:
                                        Editor.highlight=new Color(68, 252, 49).brighter();
                                        break;
                                }
                                break;
                            }
                            case 2: {
                                Color clr;
                                TextAttributes frame = new TextAttributes(Color.magenta, Color.magenta);
                                Color clr2=Color.white;
                                Color clr3=Color.white;
                                switch (j) {//theme
                                    case 3:
                                        Editor.colorful=false;
                                        clr = new Color(16, 10, 101);
                                        frame = new TextAttributes(clr, clr);
                                        clr2 = new Color(169, 185, 227);
                                        clr3=new Color(248, 123, 85);
                                        break;
                                    case 2:
                                        Editor.colorful=true;
                                        clr = new Color(255, 122, 9, 255);
                                        frame = new TextAttributes(clr, clr);
                                        clr2 = new Color(0, 0, 0);
                                        clr3=Color.YELLOW.brighter();
                                        break;
                                    case 1:
                                        Editor.colorful=false;
                                        clr = new Color(210, 120, 120);
                                        frame = new TextAttributes(clr, clr);
                                        clr2 = new Color(0, 0, 0);
                                        clr3=new Color(15, 129, 229);
                                        break;
                                    case 4:
                                        Editor.colorful=false;
                                        clr = new Color(113, 52, 97);
                                        frame = new TextAttributes(clr, clr);
                                        clr2 = new Color(202, 229, 226);
                                        clr3=new Color(241, 208, 53);
                                        break;
                                    case 5:
                                        Editor.colorful=false;
                                        clr=new Color(189, 184, 241);
                                        frame = new TextAttributes(clr, clr);
                                        clr2 = Color.white;
                                        clr3=new Color(165, 132, 222);
                                        break;
                                }
                                Editor.paintColor = clr2;
                                Editor.frame = frame;
                                Editor.descriptionColor=clr3;
                                break;
                            }
                            case 3: {// Sound
                                switch (j) {
                                    case 1:
                                        Editor.musicOn = true;
                                        break;
                                    case 2 :
                                        Editor.musicOn = false;
                                        break;
                                }
                                break;
                            }
                            case 4: {// font
                                switch (j) {
                                    case 1:
                                        Editor.index = 0;
                                        break;
                                    case 2:
                                        Editor.index = 1;
                                        break;
                                    case 3:
                                        Editor.index = 2;
                                        break;
                                    case 4:
                                        Editor.index = 3;
                                        break;
                                    default:
                                        Editor.index=1;
                                }
                                break;
                            }
                            case 5: {// TYPE
                                switch (j) {
                                    case 1:
                                        Editor.type = 0;
                                        break;
                                    case 2:
                                        Editor.type = 1;
                                        break;
                                    case 3:
                                        Editor.type = 2;
                                        break;
                                }
                                break;
                            }
                        }
                        isOn=false; break;
                    }
                    clearMenu();
                    printMenu();
                    keypr = 0;
                }
                Thread.sleep(20);

            }
        }


    public void clearMenu(){
        Editor. cn.setCursorPosition(30, 22);
        for (int i =23; i <34 ; i++) {
            for (int j =30; j < 60; j++) {
                Editor.cn.output(j,i,' ');
            }
        }
    }
    public int RandomInRange(int min, int max) {
        Random rnd = new Random();
        return rnd.nextInt((max - min)) + min;
    }


    public void printMenu(){
        TextAttributes back=new TextAttributes(Color.YELLOW.brighter());
        int cursorX=30;
        int cursorY=23;
            for (int k = 0; k <categories[i].length ; k++) {
                Editor.cn.setCursorPosition(cursorX,cursorY);
                if (k ==j){
                    Editor.cn.output(categories[i][k],back);
                }
                else
                Editor.cn.output(categories[i][k]);
                cursorY++;
        }
    }
}