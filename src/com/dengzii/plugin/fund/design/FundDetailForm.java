package com.dengzii.plugin.fund.design;

import com.dengzii.plugin.fund.tools.ui.XDialog;
import com.dengzii.plugin.fund.ui.FundDetailDialog;

import javax.swing.*;

public class FundDetailForm extends XDialog {

    protected JPanel contentPanel;
    protected JTextField textField1;

    public static void main(String[] args) {
        FundDetailDialog f = new FundDetailDialog();
        f.packAndShow();
    }
}
