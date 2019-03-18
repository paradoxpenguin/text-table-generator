/*
 * Copyright 2019 David Robertson
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.freedomdave.utils.text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TextTableGenerator {
    private static final Logger log = LoggerFactory.getLogger(TextTableGenerator.class);

    protected static final char CELL_CONNECTOR_CHAR = '+';
    protected static final char HORIZ_LINE_CHAR = '-';
    protected static final char VERT_LINE_CHAR = '|';
    protected static final char PADDING_CHAR = ' ';

    protected static final String EMPTY_CELL = "";
    protected static final int DEFAULT_PADDING = 2;

    protected int padding = DEFAULT_PADDING;

    private boolean printHeaders = true;
    private List<TextTableColumn> columns;

    /**
     * Constructor
     */
    TextTableGenerator() {
        this.columns = new ArrayList<>();
    }

    protected static String[] copyRow(String[] src, int cols) {
        String[] row = new String[cols];
        for (int i = 0; i < cols; i++) {
            if (i < src.length) {
                row[i] = src[i];
            } else {
                row[i] = EMPTY_CELL;
            }
        }
        return row;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        if (padding < 0) {
            String msg = String.format("setPadding: invalid padding [%d] -> padding must be > 0", padding);
            throw new IllegalArgumentException(msg);
        }
        this.padding = padding;
    }

    /**
     * Returns column object by index
     *
     * @param index [in] The (zero based) column index
     *
     * @return The desired column object.
     */
    public TextTableColumn getColumnByIndex(int index) {
        return columns.get(index);
    }

    /**
     * Returns column object by name
     *
     * @param name [in] The name of the desired column
     *
     * @return The desired column object.
     */
    public TextTableColumn getColumnByName(String name) {
        TextTableColumn found = null;
        for (TextTableColumn column : columns) {
            if (column.getName().equals(name)) {
                found = column;
                break;
            }
        }
        return found;
    }

    /**
     * Adds a column to the table. Columns are added to the right of the existing columns.
     *
     * @param col [in] The column to add.
     */
    public void addColumn(TextTableColumn col) {
        columns.add(col);
    }

    /**
     * Returns the number of rows in the table.  The returned value is the number of rows of the largest column.
     * The returned value also cooresponds to the number of rows in the printed table.
     *
     * @return The number of rows in the table.
     */
    public int getNumRows() {
        int max = 0;
        for (TextTableColumn column : columns) {
            max = Math.max(max, column.getNumRows());
        }
        return max;
    }

    /**
     * Adds a new row to the table by giving a list of row values.
     *
     * @param row [in] A list of row values.  If there are more columns in the table than row values, empty cells
     *            will be added to the columns to fill the entire row.
     */
    public void addRow(List<String> row) {
        int numRows = getNumRows();
        int numCols = Math.min(row.size(), columns.size());
        for (int i = 0; i < numCols; i++) {
            TextTableColumn col = columns.get(i);
            if (col.getNumRows() < numRows) {
                while (col.getNumRows() < numRows){
                    col.addValue(EMPTY_CELL);
                }
            }
            col.addValue(row.get(i));
        }
    }

    /**
     * This method generates the entire table based on the current values and settings.
     */
    public void print() {
        int rows = getNumRows();
        if (printHeaders) {
            System.out.println(printHorizontalLine());
            System.out.println(printHeader());
        }
        System.out.println(printHorizontalLine());
        for (int i = 0; i < rows; i++) {
            System.out.println(printRow(i));
        }
        System.out.println(printHorizontalLine());
    }

    /**
     *
     * @return
     */
    protected String printHorizontalLine() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            sb.append(CELL_CONNECTOR_CHAR);
            sb.append(TextFormatter.fill(columns.get(i).getWidth() + 2 * getPadding(), HORIZ_LINE_CHAR));
        }
        sb.append(CELL_CONNECTOR_CHAR);
        return sb.toString();
    }

    protected String printHeader() {
        final String padding = TextFormatter.fill(getPadding(), PADDING_CHAR);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            TextTableColumn col = columns.get(i);
            TextField header = new TextField(col.getName(), col.getWidth(), TextFormatter.Alignment.CENTER);
            sb.append(printCell(header));
        }
        sb.append(VERT_LINE_CHAR);
        return sb.toString();
    }

    protected String printCell(TextField cell) {
        final String padding = TextFormatter.fill(getPadding(), PADDING_CHAR);
        StringBuilder sb = new StringBuilder();
        sb.append(VERT_LINE_CHAR);
        sb.append(padding);
        sb.append(cell.getFittedText());
        sb.append(padding);
        return sb.toString();
    }

    protected String printRow(int row) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            TextTableColumn col = columns.get(i);
            String text = (row < col.getNumRows()) ? col.getRowValue(row) : EMPTY_CELL;
            TextField cell = new TextField(text, col.getWidth(), col.getAlignment());
            sb.append(printCell(cell));
        }
        sb.append(VERT_LINE_CHAR);
        return sb.toString();
    }
}
