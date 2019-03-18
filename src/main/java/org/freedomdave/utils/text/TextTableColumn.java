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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class encapsulates a column within a text table.  A column has a name (header label),
 * an alignment (LEFT, RIGHT, CENTER), width (either fixed or AUTO_WIDTH), and associated
 * data values.
 */
public class TextTableColumn {
    /**
     * Constant that specifies that width should be auto-calculated.
     */
    public static final int AUTO_WIDTH = -1;

    /**
     * Constant that can be used to specify an empty value.
     */
    public static final String EMPTY_VALUE = "";

    private String name;
    private List<String> values;
    private TextFormatter.Alignment alignment = TextFormatter.Alignment.LEFT;
    private int width = AUTO_WIDTH;

    /**
     * Constructor
     *
     * @param name [in] The name of the column (and header label if headers are activated.
     */
    public TextTableColumn(String name) {
        this.name = name;
        this.values = new ArrayList<>();
    }

    /**
     * Constructor
     *
     * @param name [in] The name of the column (and header label if headers are activated.
     * @param values [in] An array of string values for the column.
     */
    public TextTableColumn(String name, List<String> values) {
        this.name = name;
        this.values = values;
    }

    /**
     * Returns the name (header label) of the column.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name (header label) of the column.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Clears the data values of the column. Does not affect the name/header label of the column.
     */
    public void clear() {
        values.clear();
    }

    /**
     * Returns the current data values for the column.
     */
    public List<String> getValues() {
        return values;
    }

    /**
     * Sets the data values for the column.  Replaces any existing values.
     */
    public void setValues(List<String> values) {
        this.values = values;
    }

    /**
     * Adds a series of values to the existing column data.
     */
    public void addValues(Collection<String> values) {
        this.values.addAll(values);
    }

    /**
     * Adds a single value to the existing column data.
     */
    public void addValue(String value) {
        this.values.add(value);
    }

    /**
     * Returns the number of rows (data values) in the column.  This may differ from the number
     * of rows reported by a table that contains this column.
     */
    public int getNumRows() {
        return values.size();
    }

    /**
     * Returns the value for a particular (zero based) row index.
     */
    public String getRowValue(int row) {
        return values.get(row);
    }

    /**
     * Returns the current alignment set for the column.
     * @see TextFormatter.Alignment
     */
    public TextFormatter.Alignment getAlignment() {
        return alignment;
    }

    /**
     * Sets the current alignment set for the column.
     * @see TextFormatter.Alignment
     */
    public void setAlignment(TextFormatter.Alignment alignment) {
        this.alignment = alignment;
    }

    /**
     * Returns the text width of this column.  This value does NOT include padding.  If width
     * is set to AUTO_WIDTH, this method returns the calculated width of the column based on
     * the current data values.
     */
    public int getWidth() {
        int currentWidth = width;
        if (width == AUTO_WIDTH) {
            int max = name.length();
            for (String value : values) {
                max = Math.max(max, value.length());
            }
            currentWidth = max;
        }
        return currentWidth;
    }

    /**
     * Sets the width of the column.  Default is AUTO_WIDTH but can be set to another fixed value.
     * Values that are longer than a fixed width will be truncated with ellipses (...) when
     * generated as part of a table.
     */
    public void setWidth(int width) {
        this.width = width;
    }
}
