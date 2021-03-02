package com.dengzii.plugin.fund.http;

import junit.framework.TestCase;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.junit.Assert;

import java.io.IOException;

/**
 * @author https://github.com/dengzii/
 */
public class  HttpTest extends TestCase {

    public void testGet() {

        Http h = new Http();
        try {
            String res = h.get("http://fund.eastmoney.com/js/fundcode_search.js");
            System.out.println(res);
            Assert.assertNotNull(res);
        } catch (InterruptedException| RequestAbortedException e) {
            // ignore
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testPost() {
    }
}