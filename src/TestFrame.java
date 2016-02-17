/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  jxl.read.biff.BiffException
 *  jxl.write.WriteException
 */
import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import javax.swing.JFrame;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class TestFrame {
    public static void main(String[] args) throws IOException, BiffException, WriteException {
        JFrame frame = new JFrame("Recycle Rush Scouting");
        MatchPanel panel = new MatchPanel();
        frame.add(panel);
        panel.setBackground(new Color(50, 0, 50));
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(6);
        frame.setDefaultCloseOperation(2);
    }
}

