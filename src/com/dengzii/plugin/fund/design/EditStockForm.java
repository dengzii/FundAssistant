package com.dengzii.plugin.fund.design;

import com.dengzii.plugin.fund.tools.ui.XDialog;

import javax.swing.*;

public class EditStockForm extends XDialog {
    protected JPanel contentPanel;
    protected JButton buttonOk;
    protected JButton buttonCancel;
    protected JTable table;
    protected JTextField textFieldSearch;

    public EditStockForm(String title) {
        super(title);
        setContentPane(contentPanel);
    }
}
