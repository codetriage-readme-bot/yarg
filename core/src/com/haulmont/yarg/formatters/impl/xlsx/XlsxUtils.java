/**
 *
 * @author degtyarjov
 * @version $Id$
 */
package com.haulmont.yarg.formatters.impl.xlsx;

import com.haulmont.yarg.formatters.impl.XlsxFormatter;
import org.xlsx4j.sml.*;

import java.util.ArrayList;
import java.util.List;

public class XlsxUtils {
    private XlsxFormatter.Document document;

    public XlsxUtils(XlsxFormatter.Document document) {
        this.document = document;
    }

    public String getCellValue(Cell cell) {
        if (cell.getV() == null) return null;
        if (cell.getT().equals(STCellType.S)) {
            return document.sharedStrings.getJaxbElement().getSi().get(Integer.parseInt(cell.getV())).getT().getValue();
        } else {
            return cell.getV();
        }
    }

    public Worksheet getSheetByName(String name) {
        for (Sheet sheet : document.workbook.getSheets().getSheet()) {
            if (sheet.getName().equals(name) && document.worksheets.size() >= sheet.getSheetId()) {
                return document.worksheets.get((int) sheet.getSheetId() - 1).getJaxbElement();
            }
        }

        return null;
    }

    public CTDefinedName getDefinedName(String name) {
        List<CTDefinedName> definedName = document.workbook.getDefinedNames().getDefinedName();
        CTDefinedName targetRange = null;
        for (CTDefinedName namedRange : definedName) {
            if (name.equals(namedRange.getName())) {
                targetRange = namedRange;
            }
        }

        return targetRange;
    }

    public List<Cell> getCellsByRange(String formula) {
        Range range = Range.fromFormula(formula);
        return getCellsByRange(range);
    }

    public List<Cell> getCellsByRange(Range range) {
        Worksheet sheet = getSheetByName(range.sheet);
        SheetData data = sheet.getSheetData();

        List<Cell> result = new ArrayList<>();
        for (int i = 1; i <= data.getRow().size(); i++) {
            Row row = data.getRow().get(i - 1);
            if (range.firstRow <= row.getR() && row.getR() <= range.lastRow) {
                List<Cell> c = row.getC();
                for (int j = 1; j <= c.size(); j++) {
                    if (range.firstColumn <= j && j <= range.lastColumn) {
                        Cell cell = c.get(j - 1);
                        result.add(cell);
                    }
                }
            }
        }
        return result;
    }

    public static int getNumberFromColumnReference(String columnReference) {
        int sum = 0;

        for (int i = 0; i < columnReference.length(); i++) {
            char c = columnReference.charAt(i);
            int number = ((int) c) - 64 - 1;

            int pow = columnReference.length() - i - 1;
            sum += Math.pow(26, pow) * (number + 1);
        }
        return sum;
    }

    public static String getColumnReferenceFromNumber(int number) {
        int remain = 0;
        StringBuilder ref = new StringBuilder();
        do {

            remain = (number - 1) % 26;
            number = (number - 1) / 26;

            ref.append((char) (remain + 64 + 1));
        } while (number > 0);

        return ref.reverse().toString();
    }

}