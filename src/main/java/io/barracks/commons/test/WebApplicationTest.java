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

import io.barracks.commons.rest.HateoasRestTemplate;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public abstract class WebApplicationTest {

    @Value("${local.server.port}")
    int port;

    protected String getBaseUrl() {
        return "http://localhost:" + port;
    }

    protected RestTemplate getHateoasRestTemplate() {
        return new HateoasRestTemplate();
    }

    protected RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public JSONObject getJsonFromResource(Class clazz, String name) throws IOException, ParseException {
        return JsonResourceLoader.getJsonFromResource(clazz, name);
    }

    public JSONObject getJsonFromResource(String name) throws IOException, ParseException {
        return getJsonFromResource(getClass(), name);
    }

    public JSONArray getJsonArrayFromResource(Class clazz, String name) throws IOException, ParseException {
        return JsonResourceLoader.getJsonArrayFromResource(clazz, name);
    }

    public JSONArray getJsonArrayFromResource(String name) throws IOException, ParseException {
        return getJsonArrayFromResource(getClass(), name);
    }
}
