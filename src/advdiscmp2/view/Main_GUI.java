package view;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JSplitPane;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JPanel;

import java.awt.Dimension;

import javax.swing.JButton;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import java.awt.GridBagLayout;

import javax.swing.JTextField;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.awt.SystemColor;

public class Main_GUI extends JFrame implements ActionListener{
	private JSplitPane splitPane;
	private JPanel panel_image, panel_image_content, panel_image_original, panel_image_filtered;
	private JPanel panel_filter_content, panel_filter, panel_filter_header;
	private JLabel lbl_image_filters;
	private JComboBox cmb_image_filters;
	private JButton btn_reset_filter;
	private JPanel panel_buttons;
	private JPanel panel_filter_options;
	private JMenuBar menuBar;
	private JMenu menu_file;
	private JMenuItem menuItem_saveImage;
	private JMenuItem menuItem_newImage;
	private JMenu menu_matrix_size;
	private JMenuItem menuItem_3x3;
	private JMenuItem menuItem_5x5;
	private JMenuItem menuItem_7x7;
	private JMenuItem menuItem_9x9;
	private JButton btn_apply_filter;
	private ArrayList<Matrix_Block> listBlock;
	private JFileChooser fc;
	private JLabel image_original;
	private JLabel image_filtered;
	private int image_height;
	private int image_width;
	private JPanel panel_matrix;
	private JPanel panel_factor;
	private JLabel lbl_weight;
	private JTextField textField;
	public Main_GUI() {
		
		super();
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setTitle("ADVDISC MP2");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int)(screenSize.width/1.2),450);
		
		listBlock = new ArrayList<Matrix_Block>();
		fc = new JFileChooser();
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		splitPane = new JSplitPane();
		getContentPane().add(splitPane);
		
		panel_image = new JPanel();
		System.out.println("Screen size: "+(int)(this.getSize().width/4));
		panel_image.setPreferredSize(new Dimension((int)(this.getSize().width/1.5), 10));
		splitPane.setLeftComponent(panel_image);
		panel_image.setLayout(new BorderLayout(0, 0));
		
		panel_image_content = new JPanel();
		panel_image.add(panel_image_content, BorderLayout.CENTER);
		panel_image_content.setLayout(new BorderLayout(0, 0));
		
		panel_image_original = new JPanel();
		panel_image_original.setBorder(new TitledBorder(null, "Original", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_image_original.setBackground(new Color(255, 255, 255));
		panel_image_original.setPreferredSize(new Dimension(panel_image.getPreferredSize().width/2, 10));
		panel_image_content.add(panel_image_original, BorderLayout.WEST);
		panel_image_original.setLayout(new BorderLayout(0, 0));
		
		image_original = new JLabel("No Image");
		image_original.setHorizontalAlignment(SwingConstants.CENTER);
		panel_image_original.add(image_original);
		
		panel_image_filtered = new JPanel();
		panel_image_filtered.setBorder(new TitledBorder(null, "Filtered", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_image_filtered.setBackground(new Color(255, 255, 255));
		panel_image_content.add(panel_image_filtered, BorderLayout.CENTER);
		panel_image_filtered.setLayout(new BorderLayout(0, 0));
		
		image_filtered = new JLabel("No Image");
		image_filtered.setHorizontalAlignment(SwingConstants.CENTER);
		panel_image_filtered.add(image_filtered, BorderLayout.CENTER);
		
		panel_filter = new JPanel();
		splitPane.setRightComponent(panel_filter);
		panel_filter.setLayout(new BorderLayout(0, 0));
		
	    panel_filter_header = new JPanel();
		panel_filter_header.setBorder(new TitledBorder(null, "Filter Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_filter_header.setPreferredSize(new Dimension(10, 90));
		panel_filter.add(panel_filter_header, BorderLayout.NORTH);
		panel_filter_header.setLayout(new BorderLayout(0, 0));
		
		panel_buttons = new JPanel();
		panel_filter_header.add(panel_buttons, BorderLayout.SOUTH);
		panel_buttons.setLayout(new BorderLayout(0, 0));
		
		panel_filter_options = new JPanel();
		panel_filter_header.add(panel_filter_options, BorderLayout.NORTH);
		panel_filter_options.setLayout(new BorderLayout(0, 0));
		
		lbl_image_filters = new JLabel("Image Filter :  ");
		panel_filter_options.add(lbl_image_filters, BorderLayout.WEST);
		
		cmb_image_filters = new JComboBox<String>();
		cmb_image_filters.setBackground(SystemColor.window);
		panel_filter_options.add(cmb_image_filters);
		cmb_image_filters.setModel(new DefaultComboBoxModel(new String[] {"Custom", "Blur", "Brighten", "Edge Detect ", "Edge Enhance", "Emboss", "Grayscale", "Identity", "Sharpen", "Threshold"}));
		
		btn_reset_filter = new JButton("Reset Filter");
		btn_reset_filter.addActionListener(this);
		panel_filter_options.add(btn_reset_filter, BorderLayout.SOUTH);
		
		panel_filter_content = new JPanel();
		panel_filter_content.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "3x3 Matrix", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_filter.add(panel_filter_content, BorderLayout.CENTER);
		panel_filter_content.setLayout(new BorderLayout(0, 0));
		
		panel_matrix = new JPanel();
		panel_filter_content.add(panel_matrix);
		GridBagLayout gbl_panel_matrix = new GridBagLayout();
		gbl_panel_matrix.columnWidths = new int[]{0};
		gbl_panel_matrix.rowHeights = new int[]{0};
		gbl_panel_matrix.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panel_matrix.rowWeights = new double[]{Double.MIN_VALUE};
		panel_matrix.setLayout(gbl_panel_matrix);
		
		panel_factor = new JPanel();
		panel_factor.setBackground(SystemColor.controlLtHighlight);
		panel_filter_content.add(panel_factor, BorderLayout.NORTH);
		panel_factor.setLayout(new BorderLayout(0, 0));
		
		lbl_weight = new JLabel("Weight :  ");
		panel_factor.add(lbl_weight, BorderLayout.WEST);
		
		textField = new JTextField();
		panel_factor.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		btn_apply_filter = new JButton("Apply Filter");
		btn_apply_filter.addActionListener(this);
		panel_filter.add(btn_apply_filter, BorderLayout.SOUTH);
		
		menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);
		
		menu_file = new JMenu("File");
		menuBar.add(menu_file);
		
		menuItem_newImage = new JMenuItem("New Image");
		menuItem_newImage.addActionListener(this);
		menu_file.add(menuItem_newImage);
		
		menuItem_saveImage = new JMenuItem("Save Image");
		menu_file.add(menuItem_saveImage);
		
		menu_matrix_size = new JMenu("Matrix Size");
		menuBar.add(menu_matrix_size);
		
		menuItem_3x3 = new JMenuItem("3X3");
		menuItem_3x3.addActionListener(this);
		menu_matrix_size.add(menuItem_3x3);
		
		menuItem_5x5 = new JMenuItem("5X5");
		menuItem_5x5.addActionListener(this);
		menu_matrix_size.add(menuItem_5x5);
		
		menuItem_7x7 = new JMenuItem("7X7");
		menuItem_7x7.addActionListener(this);
		menu_matrix_size.add(menuItem_7x7);
		
		menuItem_9x9 = new JMenuItem("9x9");
		menuItem_9x9.addActionListener(this);
		menu_matrix_size.add(menuItem_9x9);
	
		setMatrix(3);
		setImageWidth((int)(panel_image.getPreferredSize().width/2.2));
		setImageHeight((int)(this.getSize().width/3.2));
		System.out.println("image size: "+ image_height + "-"+image_width);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void setImageHeight(int h){
		this.image_height = h;
	}
	public void setImageWidth(int w){
		this.image_width = w;panel_image.getHeight();
	}
	
	public int getImageHeight(){
		return this.image_height;
	}
	
	public int getImageWidth(){
		return this.image_width;
	}
	
	public void setMatrix(int size){
		
		String title = size+"X"+size+" Matrix";
		panel_filter_content.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), title, TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_matrix.removeAll();
		for(int x = 0; x<size; x++)
			for(int y = 0; y<size; y++){
				Matrix_Block mb = new Matrix_Block();
				GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField.gridx = x;
				gbc_textField.gridy = y;
				gbc_textField.weightx = 30;
				gbc_textField.weighty = 30;
				listBlock.add(mb);
				panel_matrix.add(mb, gbc_textField);
			}
		
		this.revalidate();
		this.repaint();
	}
	
	public void resetMatrix(){
		 for(int i = 0; i<listBlock.size(); i++)
			 listBlock.get(i).setText("");
	}
	
	public void setFiltered(Image i){
		image_filtered.setIcon(getScaledImage(i,image_width, image_height));
	}
	public void setPicture(String pic)
	{
		
		String picture=pic;
		image_filtered.setIcon(getScaledImage(new ImageIcon(picture).getImage(), image_width, image_height));
		image_original.setIcon(getScaledImage(new ImageIcon(picture).getImage(), image_width, image_height));
	}
	
	public ImageIcon getScaledImage(Image srcImg, int w, int h)
	{
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return new ImageIcon(resizedImg);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() ==  btn_reset_filter){
			this.resetMatrix();
		}
		else if(e.getSource() == btn_apply_filter){
			
		}else if(e.getSource() == menuItem_newImage){
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					setPicture(file.getAbsolutePath());
					//filepathText.setText(file.getPath());
				};
				
		}else if (e.getSource() == menuItem_3x3){
		
			resetMatrix();
			listBlock.clear();
			setMatrix(3);
		}
		else if (e.getSource() == menuItem_5x5){
			resetMatrix();
			listBlock.clear();
			setMatrix(5);
		}
		else if (e.getSource() == menuItem_7x7){
			resetMatrix();
			listBlock.clear();
			setMatrix(7);
		}
		else if (e.getSource() == menuItem_9x9){
			resetMatrix();
			listBlock.clear();
			setMatrix(9);
		}
		
	}

}
