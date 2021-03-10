package com.dengzii.plugin.fund.design;

import com.dengzii.plugin.fund.tools.ui.XDialog;
import com.dengzii.plugin.fund.ui.FundDetailDialog;
import com.dengzii.plugin.fund.ui.LineChart;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author https://github.com/dengzii/
 */
public class  FundDetailForm extends XDialog {

    protected JPanel contentPanel;
    protected JTextField tfCount;
    protected JPanel container;

    public static void main(String[] args) {
        FundDetailDialog f = new FundDetailDialog();
        LineChart l = new LineChart();
        ArrayList<Double> a = new ArrayList<Double>();
        a.add(-1.20);
        a.add(-1.30);
        a.add(-1.40);
        a.add(-1.10);
        a.add(-0.2);
        a.add(1.40);
        a.add(1.50);
        a.add(1.20);
        a.add(1.50);
        a.add(1.90);
        a.add(1.70);
        a.add(1.50);
        a.add(1.60);
        a.add(1.8);
        a.add(1.40);
        l.setData(a);
        f.contentPanel.add(l, BorderLayout.CENTER);
        f.packAndShow();
    }
}
