/*
 * MIT License
 *
 * Copyright (c) 2017 Barracks Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.barracks.commons.test;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class JsonResourceLoader {

    private static Object getObjectFromResource(Class clazz, String name) throws IOException, ParseException {
        final String fileName = clazz.getSimpleName() + "-" + name + ".json";
        try (
                final InputStream inputStream = clazz.getResourceAsStream(fileName);
                final InputStreamReader isr = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                final BufferedReader reader = new BufferedReader(isr);
        ) {
            final String fileContent = reader.lines().reduce("", (a, b) -> a + b);
            final JSONParser jsonParser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
            return jsonParser.parse(fileContent);
        }
    }

    public static JSONObject getJsonFromResource(Class clazz, String name) throws IOException, ParseException {
        return (JSONObject) JsonResourceLoader.getObjectFromResource(clazz, name);
    }

    public static JSONArray getJsonArrayFromResource(Class clazz, String name) throws IOException, ParseException {
        return (JSONArray) JsonResourceLoader.getObjectFromResource(clazz, name);
    }
}
