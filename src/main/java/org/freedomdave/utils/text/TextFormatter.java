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

/**
 * This class provides some simple fixed-width text field formatting methods.
 */
public class TextFormatter {

    public enum Alignment {
        LEFT,
        RIGHT,
        CENTER
    }

    /**
     * Generates a fixed-width text field using a specified fill character.
     *
     * @param width    [in] The width of the text field to generate.
     * @param fillChar [in] Character to fill the text field with.
     * @return Fixed width text field filled with the specified character.
     */
    public static String fill(int width, char fillChar) {
        StringBuilder sb = new StringBuilder(width);
        for (int i = 0; i < width; i++) {
            sb.append(fillChar);
        }
        return sb.toString();
    }

    /**
     * Centers text within a fixed width text field
     *
     * @param text     [in] The text in the field.
     * @param width    [in] The width of the text field (i.e., text will be centered within this width)
     * @param padChar [in] The character to pad either side of the text in the field (up to the total width)
     * @return A text field of specified width with text centered within it
     */
    public static String center(String text, int width, char padChar) {
        return align(text, width, ' ', Alignment.CENTER);
    }

    /**
     * Left-justifies text within a fixed width text field.
     *
     * @param text  [in] The text within the field.
     * @param width [in] The total width of the field.
     * @return A text field of fixed width with text left justified (padded to the right)
     */
    public static String leftJustify(String text, int width) {
        return align(text, width, ' ', Alignment.LEFT);
    }

    /**
     * Right-justifies text within a fixed width text field.
     *
     * @param text  [in] The text within the field.
     * @param width [in] The total width of the field.
     * @return A text field of fixed width with text right-justified (padded to the left)
     */
    public static String rightJustify(String text, int width) {
        return align(text, width, ' ', Alignment.RIGHT);
    }

    /**
     * Justifies text within a fixed width text field.
     *
     * @param text    [in] The text within the field.
     * @param width   [in] The total width of the field.
     * @param padChar [in] The character to use when padding text field to specified width.
     * @param align    [in] The desired alignment for the text in the field (i.e. left, right, or center)
     * @return A fixed width text field with the text justified to the specified side.
     */
    public static String align(String text, int width, char padChar, Alignment align) {
        StringBuilder sb = new StringBuilder(width);
        int padSize = width - text.length();
        switch (align) {
            case RIGHT:
                sb.append(fill(padSize, padChar));
                sb.append(text);
                break;
            case LEFT:
                sb.append(text);
                sb.append(fill(padSize, padChar));
                break;
            case CENTER: {
                int leading = (width - text.length()) / 2;
                sb.append(fill(leading, ' '));
                sb.append(text);
                sb.append(fill(width - leading - text.length(), padChar));
            }
        }
        return sb.toString();
    }
}
