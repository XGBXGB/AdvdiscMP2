package controller;

import driver.Filter;
import driver.FilterFactory;

public class Controller {
	
	private FilterFactory ff;
	private Filter f;
	private double[][] matrix3x3;
	private double[][] matrix5x5;
	private double[][] matrix7x7;
	private double[][] matrix9x9;
	private static Controller instance = null;
	 
	public Controller(){
		ff = FilterFactory.getInstance();
		f = null;
		matrix3x3 = null;
		matrix5x5 = null;
		matrix7x7 = null;
		matrix9x9 = null;
	}
	
    public static Controller getInstance(){
        if(instance == null)
            instance = new Controller();
        return instance;
    }
	 public void createDefaultSharpenFilter(){
		 this.f = new Filter();
		 this.f.setName("Sharpen");
		 
		 matrix3x3 = new double[][]{
				{-1, -1, -1}, 
			    {-1,  8, -1}, 
			    {-1, -1, -1} 
		 };
		 
		 matrix5x5 = new double[][]{
					{-1, -1, -1, -1, -1}, 
					{-1,  2,  2,  2, -1}, 
				    {-1,  2,  8,  2, -1}, 
					{-1,  2,  2,  2, -1},
				    {-1, -1, -1, -1, -1} 
		 };
		 
		 
	 }
}
