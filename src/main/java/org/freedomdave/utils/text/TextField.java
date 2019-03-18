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

public class TextField {
    private static final String ELLIPSIS = "...";
    private static final char DEFAULT_PAD_CHAR = ' ';

    private int width;
    private String text;
    private char padChar;
    private TextFormatter.Alignment alignment;

    public TextField(String text, int width) {
        this(text, width, TextFormatter.Alignment.LEFT, DEFAULT_PAD_CHAR);
    }

    public TextField(String text, int width, TextFormatter.Alignment alignment) {
        this(text, width, alignment, DEFAULT_PAD_CHAR);
    }

    public TextField(String text, int width, TextFormatter.Alignment alignment, char padChar) {
        this.text = text;
        this.width = width;
        this.alignment = alignment;
        this.padChar = padChar;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TextFormatter.Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(TextFormatter.Alignment alignment) {
        this.alignment = alignment;
    }

    public char getPadChar() {
        return padChar;
    }

    public void setPadChar(char padChar) {
        this.padChar = padChar;
    }

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
