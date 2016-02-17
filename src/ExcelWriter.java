/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  jxl.Cell
 *  jxl.Workbook
 *  jxl.read.biff.BiffException
 *  jxl.write.Label
 *  jxl.write.Number
 *  jxl.write.WritableCell
 *  jxl.write.WritableSheet
 *  jxl.write.WritableWorkbook
 *  jxl.write.WriteException
 */
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelWriter
implements Variables {
    WritableWorkbook MatchData;
    File matchDataFile = new File("Data.xls");
    ArrayList<Integer> teamList = new ArrayList();

    public void open() throws IOException, BiffException {
        boolean createdNewFile = this.matchDataFile.createNewFile();
        if (createdNewFile) {
            this.MatchData = Workbook.createWorkbook((File)this.matchDataFile);
        } else {
            Workbook temp = Workbook.getWorkbook((File)this.matchDataFile);
            this.MatchData = Workbook.createWorkbook((File)this.matchDataFile, (Workbook)temp);
            temp.close();
        }
        if (this.MatchData.getNumberOfSheets() == 0) {
            this.MatchData.createSheet("Compiled Data", 1);
        }
        this.MatchData.createSheet("Match" + this.MatchData.getNumberOfSheets(), this.MatchData.getNumberOfSheets() + 1);
    }

    public void writeTeamArray(boolean red, int team, int column, int[] array) throws WriteException {
        Label label;
        WritableSheet sheet = this.MatchData.getSheet(this.MatchData.getNumberOfSheets() - 1);
        for (int count = 0; count < RESULT_LABELS.length; ++count) {
            label = new Label(0, count + 1, RESULT_LABELS[count]);
            sheet.addCell((WritableCell)label);
        }
        label = new Label(column, 0, "" + team);
        sheet.addCell((WritableCell)label);
        for (int rows = 0; rows < array.length; ++rows) {
            Number num = new Number(column, rows + 1, (double)array[rows]);
            sheet.addCell((WritableCell)num);
        }
    }

    public void close() throws IOException, WriteException {
        this.createTeamList();
        this.compileTeamData();
        this.MatchData.write();
        this.MatchData.close();
    }

    private void createTeamList() {
        for (int sheet = 1; sheet < this.MatchData.getNumberOfSheets(); ++sheet) {
            WritableSheet currentSheet = this.MatchData.getSheet(sheet);
            for (int column = 1; column < currentSheet.getColumns(); ++column) {
                System.out.println(column);
                Cell cell = currentSheet.getCell(column, 0);
                String team = cell.getContents();
                if (!this.teamList.contains(Integer.parseInt(team))) {
                    this.teamList.add(Integer.parseInt(team));
                }
                Collections.sort(teamList);
            }
        }
        System.out.println(this.teamList);
    }

    private void resetTeamData() throws WriteException {
        WritableSheet compiledSheet = this.MatchData.getSheet("Compiled Data");
        for (int column = 1; column < compiledSheet.getColumns(); ++column) {
            for (int row = 1; row < compiledSheet.getRows(); ++row) {
                Number num = new Number(column, row, 0.0);
                compiledSheet.addCell((WritableCell)num);
            }
        }
    }

    private void compileTeamData() throws WriteException {
        Label label;
        Number num;
        int count;
        this.resetTeamData();
        WritableSheet compiledSheet = this.MatchData.getSheet("Compiled Data");
        for (count = 0; count < RESULT_LABELS.length; ++count) {
            label = new Label(count + 1, 0, RESULT_LABELS[count]);
            compiledSheet.addCell((WritableCell)label);
        }
        for (count = 0; count < this.teamList.size(); ++count) {
            label = new Label(0, count + 1, "" + this.teamList.get(count));
            compiledSheet.addCell((WritableCell)label);
        }
        for (int teamIndex = 0; teamIndex < this.teamList.size(); ++teamIndex) {
            ArrayList<Instance> instances = this.getInstancesOfTeam(this.teamList.get(teamIndex));
            for (int instance = 0; instance < instances.size(); ++instance) {
                WritableSheet dataSheet = this.MatchData.getSheet(instances.get((int)instance).sheet);
                for (int row = 1; row <= RESULT_LABELS.length; ++row) {
                    Cell dataCell = dataSheet.getCell(instances.get((int)instance).column, row);
                    String contents = dataCell.getContents();
                    int value = contents.equals("") ? 0 : Integer.parseInt(contents);
                    for (int targetRow = 1; targetRow <= this.teamList.size(); ++targetRow) {
                        if (!compiledSheet.getCell(0, targetRow).getContents().equals("" + this.teamList.get(teamIndex))) continue;
                        int targetValue = compiledSheet.getCell(row, targetRow).getContents().equals("") ? 0 : Integer.parseInt(compiledSheet.getCell(row, targetRow).getContents());
                        num = new Number(row, targetRow, (double)(value + targetValue));
                        compiledSheet.addCell((WritableCell)num);
                    }
                }
            }
        }
        label = new Label(RESULT_LABELS.length + 1, 0, "Games Played");
        compiledSheet.addCell((WritableCell)label);
        label = new Label(RESULT_LABELS.length + 2, 0, "Tele pts/game");
        compiledSheet.addCell((WritableCell)label);
        for (int row = 1; row < compiledSheet.getRows(); ++row) {
            int team = compiledSheet.getCell(0, row).getContents().equals("") ? 0 : Integer.parseInt(compiledSheet.getCell(0, row).getContents());
            num = new Number(RESULT_LABELS.length + 1, row, (double)this.getInstancesOfTeam(team).size());
            compiledSheet.addCell((WritableCell)num);
            int totes = compiledSheet.getCell(6, row).getContents().equals("") ? 0 : Integer.parseInt(compiledSheet.getCell(6, row).getContents());
            int bins = compiledSheet.getCell(7, row).getContents().equals("") ? 0 : Integer.parseInt(compiledSheet.getCell(7, row).getContents());
            int noodles = compiledSheet.getCell(8, row).getContents().equals("") ? 0 : Integer.parseInt(compiledSheet.getCell(8, row).getContents());
            int score = totes + bins + noodles;
            double avg = (double)score / (double)this.getInstancesOfTeam(team).size();
            num = new Number(RESULT_LABELS.length + 2, row, avg);
            compiledSheet.addCell((WritableCell)num);
        }
    }

    private ArrayList<Instance> getInstancesOfTeam(int team) {
        ArrayList<Instance> instances = new ArrayList<Instance>();
        for (int sheet = 1; sheet < this.MatchData.getNumberOfSheets(); ++sheet) {
            WritableSheet currentSheet = this.MatchData.getSheet(sheet);
            for (int column = 1; column < currentSheet.getColumns(); ++column) {
                if (!currentSheet.getCell(column, 0).getContents().equals("" + team)) continue;
                instances.add(new Instance(sheet, column));
            }
        }
        return instances;
    }

    private class Instance {
        public int sheet;
        public int column;

        public Instance(int sheet, int column) {
            this.sheet = sheet;
            this.column = column;
        }
    }

}

