package controller;

import driver.Filter;
import driver.FilterFactory;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import model.ImageModel;
import model.Observer;
import model.Subject;

public class Controller implements Subject{

    private FilterFactory ff;
    private Filter f;
    
    private static Controller instance;
    private ArrayList<Observer> observers;
    private ImageModel model;

    public Controller() {
        observers = new ArrayList();
        ff = FilterFactory.getInstance();
        f = null;
        instance = null;
        model = new ImageModel();
        createDefaultBlurFilter();
        createDefaultBrightenFilter();
        createDefaultDarkenFilter();
        createDefaultEmbossFilter();
        createDefaultIdentityFilter();
        createDefaultSharpenFilter();
        createDefaultEdgeDetectFilter();
        createDefaultEdgeEnhanceFilter();
    }
    
    public BufferedImage getOriginal(){
        return model.getOriginal();
    }
    
    public BufferedImage getFiltered(){
        return model.getFiltered();
    }
    
    public BufferedImage filterImage(String filter, Double divisor, int kernelDimension){
        double[][] kernel;
        if(kernelDimension == 3)
            kernel = ff.getFilter(filter).getMatrix3x3();
        else
            kernel = ff.getFilter(filter).getMatrix5x5();
        return model.filterImage(kernel, kernelDimension, kernelDimension, divisor);
    }
    
    public BufferedImage filterImage(double[][] kernel, Double divisor, int kernelDimension){
        return model.filterImage(kernel, kernelDimension, kernelDimension, divisor);
    }
    
    public void setOriginal(BufferedImage img){
        model.setOriginal(img);
    }
    
    public void setFiltered(BufferedImage img){
        model.setFiltered(img);
    }
    
    public void setImageName(String name)
    {
    	model.setImageName(name);
    }
   
    public String getImageName()
    {
    	return model.getImageName();
    }
    
    public double[][] getFilterArray(int size, String filterName) {
        f = ff.getFilter(filterName);
        if(size == 3) {
            return f.getMatrix3x3();
        } else if(size == 5) {
            return f.getMatrix5x5();
        }
        return null;
        
    } 
    
    public Filter getFilter(String string) {
        return ff.getFilter(string);
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void createDefaultSharpenFilter() {
        this.f = new Filter();
        this.f.setName("Sharpen");

        double[][] matrix3x3 = new double[][]{
            {-9, -9, -9},
            {-9, 81, -9},
            {-9, -9, -9}
        };
        double[][] matrix5x5 = new double[][]{
            {-25, -25, -25, -25, -25},
            {-25, 50, 50, 50, -25},
            {-25, 50, 25, 50, -25},
            {-25, 50, 50, 50, -25},
            {-25, -25, -25, -25, -25}
        };
        f.setMatrix3x3(matrix3x3);
        f.setMatrix5x5(matrix5x5);
        ff.registerFilter("Sharpen", f);
    }
    
    public void createDefaultEdgeDetectFilter() {
        this.f = new Filter();
        this.f.setName("Edge Detect");

        double[][] matrix3x3 = new double[][]{
            {-9, -9, -9},
            {-9, 72, -9},
            {-9, -9, -9}
        };
        double[][] matrix5x5 = new double[][]{
            {-25, -25, -25, -25, -25},
            {-25, 50, 50, 50, -25},
            {-25, 50, 0, 50, -25},
            {-25, 50, 50, 50, -25},
            {-25, -25, -25, -25, -25}
        };
        f.setMatrix3x3(matrix3x3);
        f.setMatrix5x5(matrix5x5);
        ff.registerFilter("Edge Detect", f);
    }
    
    public void createDefaultEdgeEnhanceFilter() {
        this.f = new Filter();
        this.f.setName("Edge Enhance");

        double[][] matrix3x3 = new double[][]{
            {0, 9, 0},
            {9, -36, 9},
            {0, 9, 0}
        };
        double[][] matrix5x5 = new double[][]{
            {0, 0, 6, 0, 0},
            {0, 0, 6, 0, 0},
            {6, 6, -50, 6, 6},
            {0, 0, 6, 0, 0},
            {0, 0, 6, 0, 0}
        };
        f.setMatrix3x3(matrix3x3);
        f.setMatrix5x5(matrix5x5);
        ff.registerFilter("Edge Enhance", f);
    }

    public void createDefaultBlurFilter() {
        this.f = new Filter();
        this.f.setName("Blur");

        double[][] matrix3x3 = new double[][]{
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
        };

        double[][] matrix5x5 = new double[][]{
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},};
        f.setMatrix3x3(matrix3x3);
        f.setMatrix5x5(matrix5x5);
        ff.registerFilter("Blur", f);
    }

    public void createDefaultBrightenFilter() {
        this.f = new Filter();
        this.f.setName("Brighten");

        double[][] matrix3x3 = new double[][]{
            {0, 0, 0},
            {0, 12, 0},
            {0, 0, 0}
        };

        double[][] matrix5x5 = new double[][]{
            {0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0,},
            {0, 0, 28, 0, 0,},
            {0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0,}
        };
        f.setMatrix3x3(matrix3x3);
        f.setMatrix5x5(matrix5x5);
        ff.registerFilter("Brighten", f);
    }

    public void createDefaultEmbossFilter() {
        this.f = new Filter();
        this.f.setName("Emboss");

        double[][] matrix3x3 = new double[][]{
            {-18, -9, 0},
            {-9, 9, 9},
            {0, 9, 18}
        };

        double[][] matrix5x5 = new double[][]{
            {-75, -50, -25, 0, 0},
            {-50, -50, -25, 0, 0},
            {-25, -25, 25, 25, 25},
            {0, 0, 25, 50, 50},
            {0, 0, 25, 50, 75}
        };
        f.setMatrix3x3(matrix3x3);
        f.setMatrix5x5(matrix5x5);
        ff.registerFilter("Emboss", f);
    }

    public void createDefaultIdentityFilter() {
        this.f = new Filter();
        this.f.setName("Identity");

        double[][] matrix3x3 = new double[][]{
            {0, 0, 0},
            {0, 9, 0},
            {0, 0, 0}
        };

        double[][] matrix5x5 = new double[][]{
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 25, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},};
        f.setMatrix3x3(matrix3x3);
        f.setMatrix5x5(matrix5x5);
        ff.registerFilter("Identity", f);
    }

    public void createDefaultDarkenFilter() {
        this.f = new Filter();
        this.f.setName("Darken");

        double[][] matrix3x3 = new double[][]{
            {0, 0, 0},
            {0, 6, 0},
            {0, 0, 0}
        };

        double[][] matrix5x5 = new double[][]{
            {0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0,},
            {0, 0, 22, 0, 0,},
            {0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0,}
        };
        f.setMatrix3x3(matrix3x3);
        f.setMatrix5x5(matrix5x5);
        ff.registerFilter("Darken", f);
    }

    @Override
    public void registerObserver(Observer o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void unRegisterObserver(Observer o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyObservers() {
        for(Observer o : observers){
            o.update();
        }
    }
}
