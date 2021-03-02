package com.dengzii.plugin.fund.design;

import com.dengzii.plugin.fund.tools.ui.XDialog;

import javax.swing.*;

/**
 * @author https://github.com/dengzii/
 */
public class  EditFundGroupForm extends XDialog {

    protected JPanel contentPanel;
    protected JButton buttonApply;
    protected JButton buttonCancel;
    protected JList<String> listSelected;
    protected JTextField textFieldSearch;
    protected JList<String> listSearch;
    protected JTextField textFieldGroupName;

    public EditFundGroupForm(String title){
        super(title);
        super.setContentPane(contentPanel);
    }
}
