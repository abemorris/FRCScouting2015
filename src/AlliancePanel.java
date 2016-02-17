import java.awt.Color;
import java.awt.Component;
import javax.swing.JPanel;

public class AlliancePanel
extends JPanel {
    TeamPanel[] teams = new TeamPanel[3];

    public AlliancePanel(Color color) {
        for (int count = 0; count < this.teams.length; ++count) {
            this.teams[count] = new TeamPanel(count);
            this.add(this.teams[count]);
        }
        this.setBackground(color);
    }

    public int[] getTeamExcelArray(int index) {
        return this.teams[index].getExcelArray();
    }

    public int getTeamNumber(int index) {
        return this.teams[index].getTeamNumber();
    }

    public void clear() {
        for (int team = 0; team < this.teams.length; ++team) {
            this.teams[team].clearData();
        }
    }
}
