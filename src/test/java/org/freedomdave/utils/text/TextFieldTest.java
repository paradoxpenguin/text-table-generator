/*
 * Copyright 2019 David Robertson (freedomdave.org)
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

public class TextFieldTest {
    @Test
    public void testTextFieldLeftAlign() {
        TextField textField = new TextField("Hello World!", 20, TextFormatter.Alignment.LEFT);
        Assert.assertEquals("Hello World!        ", textField.toString());
    }

    @Test
    public void testTextFieldRightAlign() {
        TextField textField = new TextField("Hello World!", 20, TextFormatter.Alignment.RIGHT);
        Assert.assertEquals("        Hello World!", textField.toString());
    }

    @Test
    public void testTextFieldCenterAlign() {
        TextField textField = new TextField("Hello World!", 20, TextFormatter.Alignment.CENTER);
        Assert.assertEquals("    Hello World!    ", textField.toString());
    }

    @Test
    public void testTextFieldFieldTooBig() {
        TextField textField = new TextField("Hello World!", 8, TextFormatter.Alignment.CENTER);
        Assert.assertEquals("Hello...", textField.toString());
    }
}