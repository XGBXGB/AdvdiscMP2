package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Matrix_Block extends JPanel{
	private JTextField textField_block;
	private JPanel panel_block;
	
	public Matrix_Block() {
		setLayout(new BorderLayout(0, 0));
		
		panel_block = new JPanel();
		panel_block.setPreferredSize(new Dimension(30, 30));
		add(panel_block);
		panel_block.setLayout(new BorderLayout(0, 0));
		
		textField_block = new JTextField("1");
		textField_block.setDocument(new JTextFieldFilter(JTextFieldFilter.FLOAT));
		((JTextFieldFilter)textField_block.getDocument()).setNegativeAccepted(true);
		panel_block.add(textField_block);
		textField_block.setColumns(10);
	}
        
        public void setKeyListener(KeyListener k){
            textField_block.addKeyListener(k);
        }
	
	public String getText(){
		return this.textField_block.getText();
	}
	
	public void setText(String text){
		this.textField_block.setText(text);
	}
}
