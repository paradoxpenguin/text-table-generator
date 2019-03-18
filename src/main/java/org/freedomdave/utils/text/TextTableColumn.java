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

public class TextTableColumn {
    public static final int AUTO_WIDTH = -1;

    private static final String EMPTY_VALUE = "";

    private String name;
    private List<String> values;
    private TextFormatter.Alignment alignment = TextFormatter.Alignment.LEFT;
    private int width = AUTO_WIDTH;

    public TextTableColumn(String name) {
        this.name = name;
        this.values = new ArrayList<>();
    }

    public TextTableColumn(String name, List<String> values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void clear() {
        values.clear();
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public void addValues(Collection<String> values) {
        this.values.addAll(values);
    }

    public void addValue(String value) {
        this.values.add(value);
    }

    public int getNumRows() {
        return values.size();
    }

    public String getRowValue(int row) {
        return values.get(row);
    }

    public TextFormatter.Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(TextFormatter.Alignment alignment) {
        this.alignment = alignment;
    }

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

    public void setWidth(int width) {
        this.width = width;
    }
}
