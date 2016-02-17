import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class ButtonPanel
extends JPanel
implements Variables {
    JButton[] buttons;
    int[] presses;
    final Dimension dim = new Dimension(100, 100);
    Click click;
    PressAction press;

    public ButtonPanel(int index) {
        this.click = new Click();
        this.press = new PressAction();
        this.buttons = new JButton[LABELS.length];
        this.presses = new int[LABELS.length];
        for (int count = 0; count < LABELS.length; ++count) {
            this.buttons[count] = new JButton(LABELS[count]);
            this.add(this.buttons[count]);
            this.buttons[count].setPreferredSize(this.dim);
            this.presses[count] = 0;
            this.buttons[count].addActionListener(this.click);
            this.buttons[count].getInputMap(2).put(KeyStroke.getKeyStroke(KEY_GROUPS[index][count]), this.buttons[count].getText());
            this.buttons[count].getActionMap().put(this.buttons[count].getText(), this.press);
        }
        this.setLayout(new GridLayout(4, 3, 3, 3));
    }

    private class Click implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            for(int count = 0; count<buttons.length; count++ )
            {
                if(ae.getSource().equals(buttons[count]))
                {
                    presses[count]++;
                }
            }
        }

    }
    private class PressAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            ((JButton) ae.getSource()).doClick();
        }
    }
}

