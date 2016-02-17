/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  jxl.read.biff.BiffException
 *  jxl.write.WriteException
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class MatchPanel
extends JPanel {
    AlliancePanel red = new AlliancePanel(Color.RED);
    AlliancePanel blue = new AlliancePanel(Color.BLUE);
    JButton commit = new JButton("Commit");
    ExcelWriter writer = new ExcelWriter();

    public MatchPanel() throws IOException, BiffException, WriteException {
        this.commit.setBackground(Color.GREEN);
        this.commit.addActionListener(new Commit());
        this.add(this.commit);
        this.add(this.red);
        this.add(this.blue);
    }

    private class Commit
    implements ActionListener {
        private Commit() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] excelArray;
            int count;
            try {
                MatchPanel.this.writer.open();
            }
            catch (IOException ex) {
                Logger.getLogger(MatchPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (BiffException ex) {
                Logger.getLogger(MatchPanel.class.getName()).log(Level.SEVERE, null, (Throwable)ex);
            }
            for (count = 0; count < 3; ++count) {
                excelArray = MatchPanel.this.red.getTeamExcelArray(count);
                try {
                    MatchPanel.this.writer.writeTeamArray(true, MatchPanel.this.red.getTeamNumber(count), count + 1, excelArray);
                    continue;
                }
                catch (WriteException ex) {
                    System.out.println("Array Write Not Working");
                }
            }
            for (count = 0; count < 3; ++count) {
                excelArray = MatchPanel.this.blue.getTeamExcelArray(count);
                try {
                    MatchPanel.this.writer.writeTeamArray(false, MatchPanel.this.blue.getTeamNumber(count), count + 4, excelArray);
                    continue;
                }
                catch (WriteException ex) {
                    System.out.println("Array Write Not Working");
                }
            }
            try {
                MatchPanel.this.writer.close();
            }
            catch (IOException ex) {
                Logger.getLogger(MatchPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (WriteException ex) {
                Logger.getLogger(MatchPanel.class.getName()).log(Level.SEVERE, null, (Throwable)ex);
            }
            MatchPanel.this.red.clear();
            MatchPanel.this.blue.clear();
        }
    }

}

