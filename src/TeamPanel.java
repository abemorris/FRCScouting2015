/*
 * Decompiled with CFR 0_110.
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class TeamPanel
extends JPanel
implements Variables {
    JTextField teamNum;
    GridBagLayout layout;
    VisualPanel visual;
    JPanel buttonPanel;
    JButton[] buttons;
    PressAction press;
    Click click;
    int[] results;
    int score;
    int[] excelArray = new int[RESULT_LABELS.length];
    int index;
    int[] stack;
    int toteCount = 0;
    boolean hasBin = false;
    boolean hasNoodle = false;
    boolean addingToStack = false;

    public TeamPanel(int index) {
        int count;
        this.index = index;
        this.score = 0;
        this.results = new int[LABELS.length];
        for (count = 0; count < this.results.length; ++count) {
            this.results[count] = 0;
        }
        for (count = 0; count < this.excelArray.length; ++count) {
            this.excelArray[count] = 0;
        }
        this.press = new PressAction();
        this.click = new Click();
        this.buttons = new JButton[LABELS.length];
        this.buttonPanel = new JPanel();
        this.buttonPanel.setBackground(new Color(50, 0, 50));
        for (count = 0; count < LABELS.length; ++count) {
            this.buttons[count] = new JButton(LABELS[count]);
            this.buttonPanel.add(this.buttons[count]);
            this.buttons[count].setPreferredSize(new Dimension(100, 100));
            this.buttons[count].addActionListener(this.click);
            this.buttons[count].getInputMap(2).put(KeyStroke.getKeyStroke(KEY_GROUPS[index][count]), this.buttons[count].getText());
            this.buttons[count].getActionMap().put(this.buttons[count].getText(), this.press);
            this.buttons[count].setBackground(Color.GREEN);
        }
        this.buttonPanel.setLayout(new GridLayout(4, 3, 3, 3));
        this.teamNum = new JTextField(4);
        this.teamNum.setText("0000");
        this.visual = new VisualPanel();
        this.add(this.teamNum);
        this.add(this.buttonPanel);
        this.add(this.visual);
        this.setBackground(new Color(50, 0, 50));
    }

    public void clearData() {
        int count;
        this.excelArray = new int[RESULT_LABELS.length];
        this.results = new int[LABELS.length];
        for (count = 0; count < this.results.length; ++count) {
            this.results[count] = 0;
        }
        for (count = 0; count < this.excelArray.length; ++count) {
            this.excelArray[count] = 0;
        }
        this.teamNum.setText("0000");
        this.score = 0;
        this.visual.clear();
    }

    public int getTeamNumber() {
        return Integer.parseInt(this.teamNum.getText());
    }

    public int[] getExcelArray() {
        return this.excelArray;
    }

    private class Click
    implements ActionListener {
        private Click() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            block14 : for (int count = 0; count < TeamPanel.this.buttons.length; ++count) {
                if (!e.getSource().equals(TeamPanel.this.buttons[count])) continue;
                switch (count) {
                    case 0: {
                        int[] arrn = TeamPanel.this.results;
                        arrn[0] = arrn[0] + 1;
                        int[] arrn2 = TeamPanel.this.excelArray;
                        arrn2[0] = arrn2[0] + 1;
                        continue block14;
                    }
                    case 1: {
                        int[] arrn = TeamPanel.this.results;
                        arrn[1] = arrn[1] + 1;
                        int[] arrn3 = TeamPanel.this.excelArray;
                        arrn3[1] = arrn3[1] + 1;
                        continue block14;
                    }
                    case 2: {
                        TeamPanel.this.results[2] = 1;
                        TeamPanel.this.excelArray[2] = 1;
                        continue block14;
                    }
                    case 3: {
                        TeamPanel.this.results[3] = 1;
                        TeamPanel.this.excelArray[3] = 1;
                        continue block14;
                    }
                    case 4: {
                        int[] arrn = TeamPanel.this.results;
                        arrn[4] = arrn[4] + 1;
                        int[] arrn4 = TeamPanel.this.excelArray;
                        arrn4[4] = arrn4[4] + 1;
                        continue block14;
                    }
                    case 5: {
                        TeamPanel.this.visual.addTote();
                        continue block14;
                    }
                    case 6: {
                        TeamPanel.this.visual.addBin();
                        continue block14;
                    }
                    case 7: {
                        TeamPanel.this.visual.addNoodle();
                        continue block14;
                    }
                    case 8: {
                        int[] arrn = TeamPanel.this.excelArray;
                        arrn[8] = arrn[8] + 1;
                        continue block14;
                    }
                    case 9: {
                        TeamPanel.this.visual.clear();
                        continue block14;
                    }
                    case 10: {
                        TeamPanel.this.stack = TeamPanel.this.visual.getCurrentStack();
                        for (int i = 0; i < TeamPanel.this.stack.length; ++i) {
                            if (TeamPanel.this.stack[i] == 2) {
                                ++TeamPanel.this.toteCount;
                            }
                            if (TeamPanel.this.stack[i] == 3) {
                                TeamPanel.this.hasBin = true;
                            }
                            if (TeamPanel.this.stack[i] != 6) continue;
                            TeamPanel.this.hasNoodle = true;
                        }
                        TeamPanel.this.visual.clear();
                        TeamPanel.this.addingToStack = true;
                        continue block14;
                    }
                    case 11: {
                        TeamPanel.this.stack = TeamPanel.this.visual.getCurrentStack();
                        for (int l = 0; l < TeamPanel.this.stack.length; ++l) {
                            System.out.println(TeamPanel.this.stack[l]);
                        }
                        int totes = 0;
                        boolean bin = false;
                        boolean noodle = false;
                        for (int j = 0; j < TeamPanel.this.stack.length; ++j) {
                            if (TeamPanel.this.stack[j] == 2) {
                                ++totes;
                            }
                            if (TeamPanel.this.stack[j] == 3) {
                                bin = true;
                            }
                            if (TeamPanel.this.stack[j] != 6) continue;
                            noodle = true;
                        }
                        if (!TeamPanel.this.addingToStack) {
                            int[] arrn = TeamPanel.this.excelArray;
                            arrn[5] = arrn[5] + 2 * totes;
                            if (bin) {
                                int[] arrn5 = TeamPanel.this.excelArray;
                                arrn5[6] = arrn5[6] + 4 * totes;
                            }
                            if (noodle) {
                                int[] arrn6 = TeamPanel.this.excelArray;
                                arrn6[7] = arrn6[7] + 6;
                            }
                        } else {
                            int[] arrn = TeamPanel.this.excelArray;
                            arrn[5] = arrn[5] + 2 * TeamPanel.this.toteCount;
                            if (TeamPanel.this.hasBin) {
                                int[] arrn7 = TeamPanel.this.excelArray;
                                arrn7[6] = arrn7[6] + 4 * (totes + TeamPanel.this.toteCount);
                            }
                            if (TeamPanel.this.hasNoodle) {
                                int[] arrn8 = TeamPanel.this.excelArray;
                                arrn8[7] = arrn8[7] + 6;
                            }
                        }
                        TeamPanel.this.visual.clear();
                        TeamPanel.this.addingToStack = false;
                        TeamPanel.this.hasBin = false;
                        TeamPanel.this.hasNoodle = false;
                        TeamPanel.this.toteCount = 0;
                        for (int a = 0; a < TeamPanel.this.excelArray.length; ++a) {
                            System.out.println(Variables.RESULT_LABELS[a] + ": " + TeamPanel.this.excelArray[a]);
                        }
                        continue block14;
                    }
                }
            }
            TeamPanel.this.repaint();
        }
    }

    private class PressAction
    extends AbstractAction {
        private PressAction() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ((JButton)e.getSource()).doClick();
        }
    }

}

