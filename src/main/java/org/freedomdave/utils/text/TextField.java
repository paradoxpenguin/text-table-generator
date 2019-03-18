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
 * This class represents a fixed width field of text.  The value within the field is padded to the
 * specified length of the field.  The field has an alighment (LEFT, RIGHT, CENTER) that determines
 * where the value is in the field and how it is padded to full length.  If the value of a field
 * is longer than the width, then the field value is truncated and marked with ellipses (...) to
 * designate that the truncation has occurred.
 */
public class TextField {
    private static final String ELLIPSIS = "...";
    private static final char DEFAULT_PAD_CHAR = ' ';

    private int width;
    private String text;
    private char padChar;
    private TextFormatter.Alignment alignment;

    /**
     * Constructor
     *
     * @param text  [in] The text value within the field.
     * @param width [in] The width of the field.
     */
    public TextField(String text, int width) {
        this(text, width, TextFormatter.Alignment.LEFT, DEFAULT_PAD_CHAR);
    }

    /**
     * Constrcutor
     *
     * @param text [in] The text value within the field.
     * @param width [in] The width of the field.
     * @param alignment [in] The alignment for the text in the field.
     */
    public TextField(String text, int width, TextFormatter.Alignment alignment) {
        this(text, width, alignment, DEFAULT_PAD_CHAR);
    }

    /**
     * Constrcutor
     *
     * @param text [in] The text value within the field.
     * @param width [in] The width of the field.
     * @param alignment [in] The alignment for the text in the field.
     * @param padChar [in[ The character used to pad the unused parts of the field.
     */
    public TextField(String text, int width, TextFormatter.Alignment alignment, char padChar) {
        this.text = text;
        this.width = width;
        this.alignment = alignment;
        this.padChar = padChar;
    }

    /**
     * Returns the width of the field.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the field.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the current text value of the field.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text value of the field.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns the current alignment for the field.
     */
    public TextFormatter.Alignment getAlignment() {
        return alignment;
    }

    /**
     * Sets the alignment for the field.
     */
    public void setAlignment(TextFormatter.Alignment alignment) {
        this.alignment = alignment;
    }

    /**
     * Returns the padding character used by the field.
     */
    public char getPadChar() {
        return padChar;
    }

    /**
     * Sets the padding character used by the field.
     */
    public void setPadChar(char padChar) {
        this.padChar = padChar;
    }

    /**
     * Returms the remdered value for the text. It returns the fitted text value within the
     * field. If the text is shorter than the field length, then the return value will be
     * padded to the full field width. If the text length is greater than the field width then
     * the value is truncated and marked with ellipsis (...) to denote the trunctation. This
     * method always returns a string that is exactly the length of the field width.
     */
    public String getFittedText() {
        if (text.length() <= width) {
            return TextFormatter.align(text, width, padChar, alignment);
        }

        String truncated = text.substring(0, width - ELLIPSIS.length());
        return (truncated + ELLIPSIS);
    }

    @Override
    public String toString() {
        if (!text.equals(getFittedText())) {
            return getFittedText();
        }
        return TextFormatter.align(text, width, padChar, alignment);
    }
}
