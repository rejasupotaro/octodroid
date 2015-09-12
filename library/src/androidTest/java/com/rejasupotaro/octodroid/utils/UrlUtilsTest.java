package com.rejasupotaro.octodroid.utils;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(AndroidJUnit4.class)
public class UrlUtilsTest {

    @Test
    public void encodeNull() {
        try {
            UrlUtils.encode(null);
            fail("NPE should be thrown");
        } catch (NullPointerException e) {
            // success
        }
    }

    @Test
    public void encodeEmptyString() {
        assertThat(UrlUtils.encode("")).isEqualTo("");
    }

    @Test
    public void encode() {
        assertThat(UrlUtils.encode("Java + You")).isEqualTo("Java+%2B+You");
    }
}
