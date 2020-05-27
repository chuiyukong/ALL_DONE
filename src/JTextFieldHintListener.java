/**
 * @description:    文本框选中监听类，用于显示提示信息
 * @author: 宇智波Akali
 * @time: 2020/5/22 18:21
 * @fromProject: All_Done
 * @Version: V1.0
 */
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class JTextFieldHintListener implements FocusListener {
    private String hintText;
    private JTextField textField;
    public JTextFieldHintListener(JTextField jTextField,String hintText) {
        this.textField = jTextField;
        this.hintText = hintText;
        jTextField.setText(hintText);  //默认直接显示
        jTextField.setForeground(Color.white);
    }

    @Override
    public void focusGained(FocusEvent e) {
        //获取焦点时，清空提示内容
        String temp = textField.getText();
        if(temp.equals(hintText)) {
            textField.setText("");
//            textField.setForeground(Color.white);
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        //失去焦点时，没有输入内容，显示提示内容
        String temp = textField.getText();
        if(temp.equals("")) {
//            textField.setForeground(Color.white);
            textField.setText(hintText);
        }
    }

}