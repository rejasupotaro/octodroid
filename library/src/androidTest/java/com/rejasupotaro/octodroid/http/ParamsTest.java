package com.rejasupotaro.octodroid.http;

import android.support.test.runner.AndroidJUnit4;

import com.rejasupotaro.octodroid.http.Params;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class ParamsTest {

    @Test
    public void buildEmptyQuery() {
        Params params = new Params();
        assertThat(params.toString()).isEqualTo("page=1");
    }

    @Test
    public void buildQuery() {
        {
            Params params = new Params()
                    .add("q", "Android Java");
            assertThat(params.toString()).isEqualTo("q=Android+Java&page=1");
        }
        {
            Params params = new Params()
                    .add("order", "desc")
                    .add("all", "true")
                    .add("sort", "stars")
                    .add("type", "owner");
            assertThat(params.toString()).isEqualTo("all=true&order=desc&sort=stars&type=owner&page=1");
        }
    }

    @Test
    public void incrementPage() {
        Params params = new Params();
        assertThat(params.toString()).isEqualTo("page=1");
        params.incrementPage();
        assertThat(params.toString()).isEqualTo("page=2");
    }
}
