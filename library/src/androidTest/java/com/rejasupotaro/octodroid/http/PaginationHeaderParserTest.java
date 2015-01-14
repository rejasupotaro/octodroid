package com.rejasupotaro.octodroid.http;

import android.support.test.runner.AndroidJUnit4;

import com.squareup.okhttp.Headers;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PaginationHeaderParserTest {

    @Test
    public void parse() throws Exception {
        {
            Pagination pagination = PaginationHeaderParser.parse((Response<?>) null);
            assertNotNull(pagination);
        }
        {
            Pagination pagination = PaginationHeaderParser.parse((Headers) null);
            assertNotNull(pagination);
        }
        {
            String link = "</resource?page=2&per_page=50>; rel=\"prev\"";
            Headers headers = new Headers.Builder()
                    .add(PaginationHeaderParser.HEADER_KEY_LINK, link)
                    .build();

            Pagination pagination = PaginationHeaderParser.parse(headers);
            assertNotNull(pagination);
            assertTrue(pagination.hasPrev());
            assertFalse(pagination.hasNext());
            assertFalse(pagination.hasLast());

            Link prevLink = pagination.getPrevLink();
            assertNotNull(prevLink);
            assertEquals(Link.Rel.PREV, prevLink.getRel());
            assertEquals(2, prevLink.getPage());
            assertEquals(50, prevLink.getPerPage());

            Link nextLink = pagination.getNextLink();
            assertNull(nextLink);

            Link lastLink = pagination.getLastLink();
            assertNull(lastLink);
        }
        {
            String link = "</resource?page=1&per_page=100>; rel=\"first\"; page=\"1\", "
                    + "</resource?page=3&per_page=100>; rel=\"prev\"; page=\"2\", "
                    + "</resource?page=5&per_page=100>; rel=\"next\"; page=\"4\", "
                    + "</resource?page=50&per_page=100>; rel=\"last\"; page=\"6\"";
            Headers headers = new Headers.Builder()
                    .add(PaginationHeaderParser.HEADER_KEY_LINK, link)
                    .build();

            Pagination pagination = PaginationHeaderParser.parse(headers);
            assertNotNull(pagination);
            assertTrue(pagination.hasPrev());
            assertTrue(pagination.hasNext());
            assertTrue(pagination.hasLast());

            Link firstLink = pagination.getFirstLink();
            assertNotNull(firstLink);
            assertEquals(Link.Rel.FIRST, firstLink.getRel());
            assertEquals(1, firstLink.getPage());
            assertEquals(100, firstLink.getPerPage());

            Link prevLink = pagination.getPrevLink();
            assertNotNull(prevLink);
            assertEquals(Link.Rel.PREV, prevLink.getRel());
            assertEquals(3, prevLink.getPage());
            assertEquals(100, prevLink.getPerPage());

            Link nextLink = pagination.getNextLink();
            assertNotNull(nextLink);
            assertEquals(Link.Rel.NEXT, nextLink.getRel());
            assertEquals(5, nextLink.getPage());
            assertEquals(100, nextLink.getPerPage());

            Link lastLink = pagination.getLastLink();
            assertNotNull(lastLink);
            assertEquals(Link.Rel.LAST, lastLink.getRel());
            assertEquals(50, lastLink.getPage());
            assertEquals(100, lastLink.getPerPage());
        }
    }
}

