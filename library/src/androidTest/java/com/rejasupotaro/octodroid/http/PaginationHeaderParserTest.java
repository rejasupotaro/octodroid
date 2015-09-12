package com.rejasupotaro.octodroid.http;

import android.support.test.runner.AndroidJUnit4;

import com.squareup.okhttp.Headers;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class PaginationHeaderParserTest {

    @Test
    public void parse() throws Exception {
        {
            Pagination pagination = PaginationHeaderParser.parse((Response<?>) null);
            assertThat(pagination).isNotNull();
        }
        {
            Pagination pagination = PaginationHeaderParser.parse((Headers) null);
            assertThat(pagination).isNotNull();
        }
        {
            String link = "</resource?page=2&per_page=50>; rel=\"prev\"";
            Headers headers = new Headers.Builder()
                    .add(PaginationHeaderParser.HEADER_KEY_LINK, link)
                    .build();

            Pagination pagination = PaginationHeaderParser.parse(headers);
            assertThat(pagination).isNotNull();
            assertThat(pagination.hasPrev()).isTrue();
            assertThat(pagination.hasPrev()).isTrue();
            assertThat(pagination.hasNext()).isFalse();
            assertThat(pagination.hasLast()).isFalse();

            Link prevLink = pagination.getPrevLink();
            assertThat(prevLink).isNotNull();
            assertThat(Link.Rel.PREV).isEqualTo(prevLink.getRel());
            assertThat(2).isEqualTo(prevLink.getPage());
            assertThat(50).isEqualTo(prevLink.getPerPage());

            Link nextLink = pagination.getNextLink();
            assertThat(nextLink).isNull();

            Link lastLink = pagination.getLastLink();
            assertThat(lastLink).isNull();
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
            assertThat(pagination).isNotNull();
            assertThat(pagination.hasPrev()).isTrue();
            assertThat(pagination.hasNext()).isTrue();
            assertThat(pagination.hasLast()).isTrue();

            Link firstLink = pagination.getFirstLink();
            assertThat(firstLink).isNotNull();
            assertThat(Link.Rel.FIRST).isEqualTo(firstLink.getRel());
            assertThat(1).isEqualTo(firstLink.getPage());
            assertThat(100).isEqualTo(firstLink.getPerPage());

            Link prevLink = pagination.getPrevLink();
            assertThat(prevLink).isNotNull();
            assertThat(Link.Rel.PREV).isEqualTo(prevLink.getRel());
            assertThat(3).isEqualTo(prevLink.getPage());
            assertThat(100).isEqualTo(prevLink.getPerPage());

            Link nextLink = pagination.getNextLink();
            assertThat(nextLink).isNotNull();
            assertThat(Link.Rel.NEXT).isEqualTo(nextLink.getRel());
            assertThat(5).isEqualTo(nextLink.getPage());
            assertThat(100).isEqualTo(nextLink.getPerPage());

            Link lastLink = pagination.getLastLink();
            assertThat(lastLink).isNotNull();
            assertThat(Link.Rel.LAST).isEqualTo(lastLink.getRel());
            assertThat(50).isEqualTo(lastLink.getPage());
            assertThat(100).isEqualTo(lastLink.getPerPage());
        }
    }
}

