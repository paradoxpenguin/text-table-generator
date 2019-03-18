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

import org.junit.Test;

import java.util.Arrays;

public class TestTextTableGenerator {

    @Test
    public void testTableGenerator() {
        TextTableGenerator generator = new TextTableGenerator();
        generator.addColumn(new TextTableColumn("HOST"));
        generator.addColumn(new TextTableColumn("VERSION"));
        generator.addColumn(new TextTableColumn( "BUILD"));

        generator.addRow(Arrays.asList(new String[] { "wloolt2cs1.ts.mitra.com", "8.1.2", "1550.R80.b119.20181228"}));
        generator.addRow(Arrays.asList(new String[] { "wloolt3cs1.ts.mitra.com", "8.1.4", "1700.R81.b123.20190201"}));
        generator.addRow(Arrays.asList(new String[] { "WTLLF421", "8.1.4", "SNAPSHOT-1.0.0"}));

        generator.getColumnByName("BUILD").setAlignment(TextFormatter.Alignment.CENTER);
        generator.print();
    }
}
