/*
 * Decompiled with CFR 0_110.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import javax.swing.JPanel;

public class VisualPanel
extends JPanel {
    ArrayList<Tote> totes = new ArrayList();
    boolean hasBin;
    boolean hasNoodle;
    int score;

    public VisualPanel() {
        this.setPreferredSize(new Dimension(200, 450));
        this.hasBin = false;
        this.hasNoodle = false;
        this.score = 0;
    }

    @Override
    public void paint(Graphics page) {
        this.score = 0;
        for (int count = 0; count < this.totes.size(); ++count) {
            this.totes.get(count).draw(page);
            this.score += 2;
        }
        if (this.hasBin) {
            new Bin(450 - this.totes.size() * 50).draw(page);
            this.score *= 3;
            if (this.hasNoodle) {
                new Noodle(350 - this.totes.size() * 50).draw(page);
                this.score += 6;
            }
        }
        page.setColor(Color.ORANGE);
        page.drawString("Stack Score: " + this.score, 100, 200);
    }

    public void addTote() {
        if (this.totes.size() < 6) {
            this.totes.add(new Tote(450 - this.totes.size() * 50));
        }
    }

    public void removeTote() {
        this.totes.remove(this.totes.size() - 1);
    }

    public void addBin() {
        this.hasBin = true;
    }

    public void removeBin() {
        this.hasBin = false;
    }

    public void addNoodle() {
        this.hasNoodle = true;
    }

    public void removeNoodle() {
        this.hasNoodle = false;
    }

    public void clear() {
        this.hasNoodle = false;
        this.hasBin = false;
        this.totes.clear();
        this.score = 0;
    }

    public int[] getCurrentStack() {
        int[] stack = new int[10];
        for (int count = 0; count < this.totes.size(); ++count) {
            stack[count] = 2;
        }
        if (this.hasBin) {
            stack[this.totes.size()] = 3;
            if (this.hasNoodle) {
                stack[this.totes.size() + 1] = 6;
            }
        }
        return stack;
    }

    private class Noodle {
        int y;

        public Noodle(int y) {
            this.y = y;
        }

        public void draw(Graphics page) {
            int[] xs = new int[]{40, 40, 50, 50};
            int[] ys = new int[]{this.y - 50, this.y, this.y, this.y - 50};
            Polygon noodle = new Polygon(xs, ys, 4);
            page.setColor(Color.GREEN);
            page.fillPolygon(noodle);
            page.setColor(Color.BLACK);
            page.drawPolygon(noodle);
        }
    }

    private class Bin {
        int y;

        public Bin(int y) {
            this.y = y;
        }

        public void draw(Graphics page) {
            int[] xs = new int[]{10, 15, 75, 80};
            int[] ys = new int[]{this.y - 100, this.y, this.y, this.y - 100};
            Polygon bin = new Polygon(xs, ys, 4);
            page.setColor(new Color(0, 125, 0));
            page.fillPolygon(bin);
            page.setColor(Color.BLACK);
            page.drawPolygon(bin);
        }
    }

    private class Tote {
        int y;

        public Tote(int y) {
            this.y = y;
        }

        public void draw(Graphics page) {
            int[] xs = new int[]{10, 15, 75, 80};
            int[] ys = new int[]{this.y - 50, this.y, this.y, this.y - 50};
            Polygon tote = new Polygon(xs, ys, 4);
            page.setColor(Color.GRAY);
            page.fillPolygon(tote);
            page.setColor(Color.BLACK);
            page.drawPolygon(tote);
        }
    }

}

