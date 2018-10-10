package com.android.ecnu.hospital;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.webkit.WebView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private MainActivity mainActivity;
    private WebView webView;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        mainActivity = mActivityRule.getActivity();
        webView = mainActivity.findViewById(R.id.web_view);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    @UiThreadTest
    public void the_url_should_be_corrrect_after_start() {
        assertEquals("http://jzdc.ecnucpp.com:6100/app/login.html", webView.getOriginalUrl());
    }

    @Test
    @UiThreadTest
    public void the_url_should_be_corrrect_after_log_out() {
        mainActivity.log_out();
        assertEquals("http://jzdc.ecnucpp.com:6100/app/login.html", webView.getOriginalUrl());
    }

    @Test
    @UiThreadTest
    public void the_url_should_be_corrrect_after_ver_update() {
        mainActivity.ver_update();
        assertEquals("http://jzdc.ecnucpp.com:6100/app/version_updating.html", webView.getOriginalUrl());
    }

    @Test
    @UiThreadTest
    public void the_url_should_be_corrrect_after_edit_pass() {
        mainActivity.edit_pass();
        assertEquals("http://jzdc.ecnucpp.com:6100/app/edit_password.html", webView.getOriginalUrl());
    }

    @Test
    @UiThreadTest
    public void the_url_should_be_corrrect_after_med_situ() {
        mainActivity.med_situ();
        assertEquals("http://jzdc.ecnucpp.com:6100/app/medicine_situation.html", webView.getOriginalUrl());
    }

    @Test
    @UiThreadTest
    public void the_url_should_be_corrrect_after_health_situ() {
        mainActivity.health_situ();
        assertEquals("http://jzdc.ecnucpp.com:6100/app/health_situation.html", webView.getOriginalUrl());
    }

    @Test
    @UiThreadTest
    public void the_url_should_be_corrrect_after_health_monitor() {
        mainActivity.med_situ();
        assertEquals("http://jzdc.ecnucpp.com:6100/app/health_monitor.html", webView.getOriginalUrl());
    }

    @Test
    @UiThreadTest
    public void the_url_should_be_corrrect_after_four_diag() {
        mainActivity.med_situ();
        assertEquals("http://jzdc.ecnucpp.com:6100/app/four_diagnostic.html", webView.getOriginalUrl());
    }

    @Test
    @UiThreadTest
    public void the_url_should_be_corrrect_after_ser_res() {
        mainActivity.med_situ();
        assertEquals("http://jzdc.ecnucpp.com:6100/app/service_reservation.html", webView.getOriginalUrl());
    }

    @Test
    @UiThreadTest
    public void the_url_should_be_corrrect_after_ser_inter() {
        mainActivity.med_situ();
        assertEquals("http://jzdc.ecnucpp.com:6100/app/service_interview.html", webView.getOriginalUrl());
    }

    @Test
    @UiThreadTest
    public void the_url_should_be_corrrect_after_ser_monitor() {
        mainActivity.med_situ();
        assertEquals("http://jzdc.ecnucpp.com:6100/app/service_monitor.html", webView.getOriginalUrl());
    }

    @Test
    public void the_result_of_getVersionCode_should_be_1() {
        assertEquals(1, MainActivity.getVersionCode(mainActivity));
    }
}