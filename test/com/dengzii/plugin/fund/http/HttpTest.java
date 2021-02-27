package com.dengzii.plugin.fund.http;

import junit.framework.TestCase;
import org.junit.Assert;

import java.io.IOException;

public class HttpTest extends TestCase {

    public void testGet() {

        Http h = new Http();
        try {
            String res = h.get("http://fund.eastmoney.com/js/fundcode_search.js");
            System.out.println(res);
            Assert.assertNotNull(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testPost() {
    }
}