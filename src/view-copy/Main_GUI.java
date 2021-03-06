package view;

import controller.Controller;
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import model.Observer;

public class Main_GUI extends JFrame implements ActionListener, Observer, KeyListener {

    private Controller controller;
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
    private ArrayList<ArrayList<Matrix_Block>> listBlock;
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
        controller = Controller.getInstance();
        getContentPane().setLayout(new BorderLayout(0, 0));
        this.setTitle("ADVDISC MP2");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screenSize.width / 1.2), 450);

        listBlock = new ArrayList<ArrayList<Matrix_Block>>();
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
        System.out.println("Screen size: " + (int) (this.getSize().width / 4));
        panel_image.setPreferredSize(new Dimension((int) (this.getSize().width / 1.5), 10));
        splitPane.setLeftComponent(panel_image);
        panel_image.setLayout(new BorderLayout(0, 0));

        panel_image_content = new JPanel();
        panel_image.add(panel_image_content, BorderLayout.CENTER);
        panel_image_content.setLayout(new BorderLayout(0, 0));

        panel_image_original = new JPanel();
        panel_image_original.setBorder(new TitledBorder(null, "Original", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_image_original.setBackground(new Color(255, 255, 255));
        panel_image_original.setPreferredSize(new Dimension(panel_image.getPreferredSize().width / 2, 10));
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
        cmb_image_filters.setModel(new DefaultComboBoxModel(new String[]{"Custom", "Blur", "Brighten", "Edge Detect", "Edge Enhance", "Emboss", "Darken", "Identity", "Sharpen", "Threshold"}));

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
        textField.setText("9.0");

        btn_apply_filter = new JButton("Apply Filter");
        btn_apply_filter.addActionListener(this);
        panel_filter.add(btn_apply_filter, BorderLayout.SOUTH);
        btn_apply_filter.setEnabled(false);

        menuBar = new JMenuBar();
        getContentPane().add(menuBar, BorderLayout.NORTH);

        menu_file = new JMenu("File");
        menuBar.add(menu_file);

        menuItem_newImage = new JMenuItem("New Image");
        menuItem_newImage.addActionListener(this);
        menu_file.add(menuItem_newImage);

        menuItem_saveImage = new JMenuItem("Save Image");
        menuItem_saveImage.addActionListener(this);
        menu_file.add(menuItem_saveImage);

        menu_matrix_size = new JMenu("Matrix Size");
        menuBar.add(menu_matrix_size);

        menuItem_3x3 = new JMenuItem("3X3");
        menuItem_3x3.addActionListener(this);
        menu_matrix_size.add(menuItem_3x3);

        menuItem_5x5 = new JMenuItem("5X5");
        menuItem_5x5.addActionListener(this);
        menu_matrix_size.add(menuItem_5x5);

        setMatrix(3);
        setImageWidth((int) (panel_image.getPreferredSize().width / 2.2));
        setImageHeight((int) (this.getSize().width / 3.2));
        System.out.println("image size: " + image_height + "-" + image_width);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setImageHeight(int h) {
        this.image_height = h;
    }

    public void setImageWidth(int w) {
        this.image_width = w;
        panel_image.getHeight();
    }

    public int getImageHeight() {
        return this.image_height;
    }

    public int getImageWidth() {
        return this.image_width;
    }

    public void setMatrix(int size) {

        String title = size + "X" + size + " Matrix";
        panel_filter_content.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), title, TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_matrix.removeAll();
        for (int y = 0; y < size; y++) {
        	ArrayList<Matrix_Block> listBlockCol = new ArrayList<Matrix_Block>();
        	
            for (int x = 0; x < size; x++) {
                Matrix_Block mb = new Matrix_Block();
                mb.setKeyListener(this);
                GridBagConstraints gbc_textField = new GridBagConstraints();
                gbc_textField.fill = GridBagConstraints.HORIZONTAL;
                gbc_textField.gridx = x;
                gbc_textField.gridy = y;
                gbc_textField.weightx = 30;
                gbc_textField.weighty = 30;
                listBlockCol.add(mb);
                panel_matrix.add(mb, gbc_textField);
            }
            listBlock.add(listBlockCol);
        }

        this.revalidate();
        this.repaint();
        cmb_image_filters.addActionListener(this);
    }

    public void resetMatrix() {
        for (int i = 0; i < listBlock.size(); i++) {
        	for(int j = 0; j<listBlock.get(i).size(); j++)
        		listBlock.get(i).get(j).setText("");
        }
    }

    public void setFiltered(Image i) {
        image_filtered.setIcon(getScaledImage(i, image_width, image_height));
    }

    public void setPicture(String pic) {

        String picture = pic;
        image_filtered.setIcon(getScaledImage(new ImageIcon(picture).getImage(), image_width, image_height));
        image_original.setIcon(getScaledImage(new ImageIcon(picture).getImage(), image_width, image_height));
    }

    public ImageIcon getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return new ImageIcon(resizedImg);
    }

    public boolean checkBoxes() {//returns true if all boxes are filled
        int size = listBlock.size();
        
        //System.out.println("LIST BLOCK SIZ: "+listBlock.size());
        for (int i = 0; i < listBlock.size(); i++) {
            for (int j = 0; j < listBlock.get(i).size(); j++) {
            	  // System.out.println("LIST BLOCK SIZE COL: "+listBlock.get(i).size());
                if (listBlock.get(i).get(j).getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        if (e.getSource() == btn_reset_filter) {
            this.resetMatrix();
        } else if (e.getSource() == cmb_image_filters) {
            String filterName = cmb_image_filters.getSelectedItem().toString();
            if (!filterName.equals("Custom")) {
               
                double[][] filterArray = controller.getFilterArray(listBlock.size(), filterName);
                
                for(int i = 0; i<3; i++)
                {
                	for(int j = 0; j<3; j++)
                		System.out.printf("%d ", (int)filterArray[i][j] );
                	
                	System.out.println();
                }
                for (int i = 0; i < listBlock.size(); i++) {
                    for (int j = 0; j < listBlock.get(i).size(); j++) {
                        listBlock.get(i).get(j).setText(String.valueOf((int) filterArray[i][j]));
                      //  System.out.printf("%s ", listBlock.get(i).get(j).getText());
                    }
                    //System.out.println();
                }
            }
        } else if (e.getSource() == btn_apply_filter) {
        	System.out.println("CHECK BOXES: "+ checkBoxes());
            if (checkBoxes() == true) {
                BufferedImage filteredImage = null;
                if (!cmb_image_filters.getSelectedItem().toString().equals("Custom")) {
                    filteredImage = controller.filterImage(cmb_image_filters.getSelectedItem().toString(), 
                    			Double.parseDouble(textField.getText()), listBlock.size());
                 //   System.out.println("HELLo");
                } else {
                	//System.out.println("LIST BLOCK SIZSE: "+ listBlock.size());
                    double[][] kernel = new double[listBlock.size()][listBlock.size()];
                    for (int i = 0; i < listBlock.size(); i++) {
                        for (int j = 0; j < listBlock.get(i).size(); j++) {
                        //	System.out.println("LIST BLOCK SIZSE COL : "+ listBlock.get(i).size());
                            kernel[i][j] = Double.parseDouble(listBlock.get(i).get(j).getText());
                          //  System.out.printf("%.2lf " + kernel[i][j]);
                        }
                       // System.out.println();
                    }
                    filteredImage = controller.filterImage(kernel, Double.parseDouble(textField.getText()), listBlock.size());
                }
                setFiltered(filteredImage);
            }
        } else if (e.getSource() == menuItem_newImage) {
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    setPicture(file.getAbsolutePath());

                    BufferedImage img = ImageIO.read(file);
                    controller.setOriginal(img);
                    controller.setFiltered(img);
                    controller.setImageName(file.getName());
                } catch (IOException ex) {
                    System.out.println("CRAP");
                }

                btn_apply_filter.setEnabled(true);
                //filepathText.setText(file.getPath());
            }

        }
        else if (e.getSource() == menuItem_saveImage)
        {
        	JFileChooser chooserfile = new JFileChooser();
        	chooserfile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = chooserfile.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = chooserfile.getSelectedFile();
                    File output = new File(file.getPath()+"\\filtered-" + controller.getImageName());
                    try {
						ImageIO.write(controller.getFiltered(), "jpg", output);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            

               // btn_apply_filter.setEnabled(true);
                //filepathText.setText(file.getPath());
            }

        	//File output = new File("C:\\Users\\user\\workspace\\aADVDISCMP2\\src\\filtered-" + input.getName());
            //ImageIO.write(image, "jpg", output);
        }
        else if (e.getSource() == menuItem_3x3) {
        	cmb_image_filters.setSelectedItem(""+cmb_image_filters.getSelectedItem().toString());
            //resetMatrix();
            textField.setText("9.0");
            listBlock.clear();
            setMatrix(3);
            String filterName = cmb_image_filters.getSelectedItem().toString();
            System.out.println("Nana " + filterName);
            if (!filterName.equals("Custom")) {
               
                double[][] filterArray = controller.getFilterArray(listBlock.size(), filterName);
                
                for(int i = 0; i<3; i++)
                {
                	for(int j = 0; j<3; j++)
                		System.out.printf("%d ", (int)filterArray[i][j]);
                	
                	System.out.println();
                }
                for (int i = 0; i < listBlock.size(); i++) {
                    for (int j = 0; j < listBlock.get(i).size(); j++) {
                        listBlock.get(i).get(j).setText(String.valueOf((int) filterArray[i][j]));
                      //  System.out.printf("%s ", listBlock.get(i).get(j).getText());
                    }
                    //System.out.println();
                }
            }
            
        } else if (e.getSource() == menuItem_5x5) {
        	cmb_image_filters.setSelectedItem(""+cmb_image_filters.getSelectedItem().toString());
            //resetMatrix();
            textField.setText("25.0");
            listBlock.clear();
            setMatrix(5);
            String filterName = cmb_image_filters.getSelectedItem().toString();
            System.out.println("Nana " + filterName);
            if (!filterName.equals("Custom")) {
               
                double[][] filterArray = controller.getFilterArray(listBlock.size(), filterName);
                
                for(int i = 0; i<3; i++)
                {
                	for(int j = 0; j<3; j++)
                		System.out.printf("%d ", (int)filterArray[i][j]);
                	
                	System.out.println();
                }
                for (int i = 0; i < listBlock.size(); i++) {
                    for (int j = 0; j < listBlock.get(i).size(); j++) {
                        listBlock.get(i).get(j).setText(String.valueOf((int) filterArray[i][j]));
                      //  System.out.printf("%s ", listBlock.get(i).get(j).getText());
                    }
                    //System.out.println();
                }
            }
        } 
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //get filtered image from controller
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        cmb_image_filters.setSelectedItem("Custom");
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        cmb_image_filters.setSelectedItem("Custom");
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        cmb_image_filters.setSelectedItem("Custom");
    }

}
