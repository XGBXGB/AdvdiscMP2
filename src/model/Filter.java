package model;

public class Filter {
	private String name;
	private double [][] matrix3x3;
	private double [][] matrix5x5;
	private double [][] matrix7x7;
	private double [][] matrix9x9;
	
	public Filter(){
		this.name = "";
		this.matrix3x3 = new double[3][3];
		this.matrix5x5 = new double[5][5];
		this.matrix7x7 = new double[7][7];
		this.matrix9x9 = new double[9][9];
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double[][] getMatrix3x3() {
		return matrix3x3;
	}

	public void setMatrix3x3(double[][] matrix3x3) {
		this.matrix3x3 = matrix3x3;
	}

	public double[][] getMatrix5x5() {
		return matrix5x5;
	}

	public void setMatrix5x5(double[][] matrix5x5) {
		this.matrix5x5 = matrix5x5;
	}

	public double[][] getMatrix7x7() {
		return matrix7x7;
	}

	public void setMatrix7x7(double[][] matrix7x7) {
		this.matrix7x7 = matrix7x7;
	}

	public double[][] getMatrix9x9() {
		return matrix9x9;
	}

	public void setMatrix9x9(double[][] matrix9x9) {
		this.matrix9x9 = matrix9x9;
	}
	
	public Filter createFilter(){
		return new Filter();
	}

}
