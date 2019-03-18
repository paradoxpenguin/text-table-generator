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

/**
 * This class generates text tables for tabular data. The data may be provided by row or by
 * column.  Column widths are auto-calculated by default or can be set to a fixed width.
 * Values that are longer than the overridden specified width are truncated with ellipsis.
 * Each column may also have an alignment set for its values: LEFT, RIGHT, or CENTER
 * <p><em>The API is designed to be simple with reasonable defaults for most usages.  A
 * table can be generated in just a few lines of code.</em></p>
 */
public class TextTableGenerator {
    private static final Logger log = LoggerFactory.getLogger(TextTableGenerator.class);

    protected static final char CELL_CONNECTOR_CHAR = '+';
    protected static final char HORIZ_LINE_CHAR = '-';
    protected static final char VERT_LINE_CHAR = '|';
    protected static final char PADDING_CHAR = ' ';

    protected static final String EMPTY_CELL = "";
    protected static final String NEWLINE = "\n";
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

    /**
     * Creates a copy of values for a row given an existing set of values and the number of
     * columns in the row. If there are fewer items in the provided row than there are
     * columns then the row is padded with empty values. If there are more values than there
     * are rows, the extra values are ignored.
     *
     * @param src  [in] The set of values for the row.
     * @param cols [in] The number of columns in the row.
     * @return A set of values for a row in the table (exactly <em>cols</em> items.)
     */
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

    /**
     * Returns the current padding for the table.
     */
    public int getPadding() {
        return padding;
    }

    /**
     * Sets the number of padding characters for each cell in the table.  For example, a value of
     * 2 means that each cell value in the table will be padded with 2 spaces to the left and
     * right of the cell's displayed value.
     * @param padding [in] Number of characters to be used for padding each cell in the table.
     */
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
    public String generate() {
        StringBuilder sb = new StringBuilder();
        int rows = getNumRows();
        if (printHeaders) {
            sb.append(generateHorizontalLine());
            sb.append(generateHeader());
        }
        sb.append(generateHorizontalLine());
        for (int i = 0; i < rows; i++) {
            sb.append(generateRow(i));
        }
        sb.append(generateHorizontalLine());
        return sb.toString();
    }

    /**
     * Generates a horizontal line for the table, taking into account each of the columns in the
     * table and their widths.
     */
    protected String generateHorizontalLine() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            sb.append(CELL_CONNECTOR_CHAR);
            sb.append(TextFormatter.fill(columns.get(i).getWidth() + 2 * getPadding(), HORIZ_LINE_CHAR));
        }
        sb.append(CELL_CONNECTOR_CHAR);
        sb.append(NEWLINE);
        return sb.toString();
    }

    /**
     * Generates the header for the table. The header is a row surrounded by horizontal lines
     * that indicates the name of each column (centered).
     */
    protected String generateHeader() {
        final String padding = TextFormatter.fill(getPadding(), PADDING_CHAR);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            TextTableColumn col = columns.get(i);
            TextField header = new TextField(col.getName(), col.getWidth(), TextFormatter.Alignment.CENTER);
            sb.append(generateCell(header));
        }
        sb.append(VERT_LINE_CHAR);
        sb.append(NEWLINE);
        return sb.toString();
    }

    /**
     * Generates the string containing the padded cells value. It includes the vertical separator
     * disignating the start of the column and the cells fitted text value including any padding.
     */
    protected String generateCell(TextField cell) {
        final String padding = TextFormatter.fill(getPadding(), PADDING_CHAR);
        StringBuilder sb = new StringBuilder();
        sb.append(VERT_LINE_CHAR);
        sb.append(padding);
        sb.append(cell.getFittedText());
        sb.append(padding);
        return sb.toString();
    }

    /**
     * Generates a string that is the rendering of a single row of the table. Includes fitted
     * values for each of the columns and vertical separators between each of the columns.
     */
    protected String generateRow(int row) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            TextTableColumn col = columns.get(i);
            String text = (row < col.getNumRows()) ? col.getRowValue(row) : EMPTY_CELL;
            TextField cell = new TextField(text, col.getWidth(), col.getAlignment());
            sb.append(generateCell(cell));
        }
        sb.append(VERT_LINE_CHAR);
        sb.append(NEWLINE);
        return sb.toString();
    }
}
