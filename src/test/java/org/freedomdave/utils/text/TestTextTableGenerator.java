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

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestTextTableGenerator {

    @Test
    public void testTableGenerator() {
        StringBuilder sb = new StringBuilder();
        sb.append("+---------------+-------------+------------------+\n");
        sb.append("|   EMPLOYEE    |  JOB TITLE  |      SALARY      |\n");
        sb.append("+---------------+-------------+------------------+\n");
        sb.append("|  Jane Doe     |  CEO        |      $1,200,000  |\n");
        sb.append("|  John Doe     |  Developer  |         $51,232  |\n");
        sb.append("|  Joe Sellers  |  Sales      |  (Base) $20,000  |\n");
        sb.append("+---------------+-------------+------------------+\n");

        final String expected = sb.toString();

        TextTableGenerator generator = new TextTableGenerator();
        generator.addColumn(new TextTableColumn("EMPLOYEE"));
        generator.addColumn(new TextTableColumn("JOB TITLE"));
        generator.addColumn(new TextTableColumn("SALARY"));

        generator.addRow(Arrays.asList(new String[]{"Jane Doe", "CEO", "$1,200,000"}));
        generator.addRow(Arrays.asList(new String[]{"John Doe", "Developer", "$51,232"}));
        generator.addRow(Arrays.asList(new String[]{"Joe Sellers", "Sales", "(Base) $20,000"}));

        generator.getColumnByName("SALARY").setAlignment(TextFormatter.Alignment.RIGHT);
        String actual = generator.generate();
        Assert.assertEquals(expected, actual);
    }
}
