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

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

public class PagedResourcesUtils {
    private static final Pageable DEFAULT_PAGEABLE = new PageRequest(0, 10);

    public static <T> PagedResourcesAssembler<T> getPagedResourcesAssembler() {
        return getPagedResourcesAssembler(null);
    }

    public static <T> PagedResourcesAssembler<T> getPagedResourcesAssembler(String baseUri) {
        String uri = baseUri == null ? "https://not.barracks.io" : baseUri;
        HateoasPageableHandlerMethodArgumentResolver argumentResolver = new HateoasPageableHandlerMethodArgumentResolver();
        argumentResolver.setFallbackPageable(DEFAULT_PAGEABLE);
        return new PagedResourcesAssembler<>(argumentResolver, UriComponentsBuilder.fromHttpUrl(uri).build());
    }

    public static <T> PagedResources<T> buildPagedResources(Pageable pageable, List<T> items) {
        return new PagedResources<>(new ArrayList<>(items), new PagedResources.PageMetadata(pageable.getPageSize(), pageable.getPageNumber(), items.size()));
    }

    public static Pageable getDefaultPageable() {
        return DEFAULT_PAGEABLE;
    }

}