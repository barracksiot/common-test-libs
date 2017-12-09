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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;

public abstract class ServiceClientTest {

    protected String getServiceUrl() {
        return "http://not.barracks.io";
    }

    public JSONObject getJsonFromResource(String name) throws IOException, ParseException {
        return JsonResourceLoader.getJsonFromResource(getClass(), name);
    }

    public JSONArray getJsonArrayFromResource(String name) throws IOException, ParseException {
        return JsonResourceLoader.getJsonArrayFromResource(getClass(), name);
    }

    protected MultiValueMap<String, String> queryFrom(Pageable pageable) {
        MultiValueMap<String, String> result = new LinkedMultiValueMap<>();
        result.add("page", String.valueOf(pageable.getPageNumber()));
        result.add("size", String.valueOf(pageable.getPageSize()));
        if (pageable.getSort() != null) {
            for (Sort.Order order : pageable.getSort()) {
                result.add("sort", order.getProperty() + "," + order.getDirection().name().toLowerCase(Locale.US));
            }
        }
        return result;
    }

    protected URI getUri(String... pathSegements) {
        return getUri((MultiValueMap<String, String>) null, false, pathSegements);
    }

    protected URI getUri(Map<String, String> query, String... pathSegments) {
        LinkedMultiValueMap<String, String> newQuery = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : query.entrySet()) {
            newQuery.add(entry.getKey(), entry.getValue());
        }
        return getUri(newQuery, pathSegments);
    }

    protected URI getUri(Map<String, String> query, boolean forceTrailingSlash, String... pathSegments) {
        LinkedMultiValueMap<String, String> newQuery = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : query.entrySet()) {
            newQuery.add(entry.getKey(), entry.getValue());
        }
        return getUri(newQuery, forceTrailingSlash, pathSegments);
    }

    protected URI getUri(MultiValueMap<String, String> query, String... pathSegments) {
        return getUri(query, false, pathSegments);
    }

    protected URI getUri(boolean forceTrailingSlash, String... pathSegments) {
        return getUri((MultiValueMap<String, String>) null, forceTrailingSlash, pathSegments);
    }

    protected URI getUri(MultiValueMap<String, String> queryParams, boolean forceTrailingSlash, String... pathSegments) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(getServiceUrl())
                .pathSegment(pathSegments);
        if (forceTrailingSlash) {
            builder.path("/");
        }
        builder.queryParams(queryParams);
        return builder.build().toUri();
    }

}
