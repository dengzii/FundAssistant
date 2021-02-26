package com.dengzii.plugin.fund.http;

import junit.framework.TestCase;

public class HttpTest extends TestCase {

    public void testGet() {

        Http h = new Http();
        h.get("http://fund.eastmoney.com/js/fundcode_search.js");
    }

    public void testPost() {
    }
}