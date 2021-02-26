package com.dengzii.plugin.fund.design;

import com.dengzii.plugin.fund.tools.ui.XDialog;

import javax.swing.*;

public class AddFundForm extends XDialog {

    protected JPanel contentPanel;
    protected JButton buttonAdd;
    protected JButton buttonCancel;
    protected JList<String> listFunds;
    protected JTextField textField;

    public AddFundForm(String title){
        super(title);
        super.setContentPane(contentPanel);
    }
}
