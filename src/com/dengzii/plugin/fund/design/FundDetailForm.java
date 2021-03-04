package com.dengzii.plugin.fund.design;

import com.dengzii.plugin.fund.tools.ui.XDialog;
import com.dengzii.plugin.fund.ui.FundDetailDialog;

import javax.swing.*;

/**
 * @author https://github.com/dengzii/
 */
public class  FundDetailForm extends XDialog {

    protected JPanel contentPanel;
    protected JTextField tfCount;
    protected JPanel container;

    public static void main(String[] args) {
        FundDetailDialog f = new FundDetailDialog();
        f.packAndShow();
    }
}
