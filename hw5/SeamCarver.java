import edu.princeton.cs.algs4.Picture;

import java.awt.*;


public class SeamCarver {

    private Picture pic;
    private double[][] energy;
    //A constructor
    public SeamCarver(Picture picture){
        this.pic = new Picture(picture);
        energy = new double[pic.height()][pic.width()];
        for (int i = 0; i < pic.height(); i++){
            for (int j = 0; j < pic.width(); j++){
                energy[i][j] = energy(j, i);
            }
        }
    }

    // current picture
    public Picture picture(){
        return pic;
    }

    // width of current picture
    public int width(){
        return pic.width();
    }

    // height of current picture
    public int height(){
        return pic.height();
    }

    /**By convention, the indices x and y are integers between 0 and W − 1
     * and between 0 and H − 1 respectively.
     * Throw a java.lang.IndexOutOfBoundsException
     * if either x or y is outside its prescribed range.*/
    // energy of pixel at column x and row y
    public double energy(int x, int y){
        if (x < 0 || x >= pic.width() || y < 0 || y >= pic.height()){
            throw new IndexOutOfBoundsException("Invalid Index");
        }
        //calculate the energy at position (x, y)
        double energyXY = 0.0;
        Color colorLeft = x == 0 ? pic.get(pic.width()-1, y) : pic.get(x-1, y);
        Color colorRight = x == pic.width() - 1? pic.get(0, y) : pic.get(x+1, y);
        Color colorUp = y == 0 ? pic.get(x, pic.height()-1) : pic.get(x, y-1);
        Color colorDown = y == pic.height() - 1 ? pic.get(x, 0) :pic.get(x, y+1);
        energyXY = calenergy(colorLeft, colorRight) + calenergy(colorUp, colorDown);

        return energyXY;
    }
    //calculate the energy between two pixels
    private double calenergy(Color color1, Color color2){
        double energy = 0.0;
        energy = (color1.getRed() - color2.getRed()) * (color1.getRed() - color2.getRed())
                + (color1.getGreen() - color2.getGreen()) * (color1.getGreen() - color2.getGreen())
                + (color1.getBlue() - color2.getBlue()) * (color1.getBlue() - color2.getBlue());
        return energy;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam(){
        Picture rotate = new Picture(pic.height(), pic.width());
        for (int i = 0; i < rotate.width(); i++) {
            for (int j = 0; j < rotate.height(); j++) {
                Color color = pic.get(rotate.height() - 1 - j, i);
                rotate.set(i, j, color);
            }
        }
        SeamCarver horizontalCarver = new SeamCarver(rotate);
        int[] seam = horizontalCarver.findVerticalSeam();
        for(int i=0; i<seam.length/2; i++){
            int temp = seam[i];
            seam[i] = seam[seam.length -i -1];
            seam[seam.length -i -1] = temp;
        }
        return seam;
    }




    // sequence of indices for vertical seam
    public int[] findVerticalSeam(){
        int[] VS = new int[pic.height()];
        double[][] matrix = this.min_sum_array();
        double min = Integer.MAX_VALUE;
        int minCol = 0;
        for (int j = 1; j < pic.width() + 1; j++){
            if (min > matrix[pic.height()-1][j]){
                min = matrix[pic.height()-1][j];
                minCol = j;
            }
        }
        VS[pic.height() - 1] = minCol - 1;
        int i = pic.height()-1;
        while(i-1 >= 0){
            min = Math.min(Math.min(matrix[i-1][minCol-1],matrix[i-1][minCol]), matrix[i-1][minCol+1]);
            if (min == matrix[i-1][minCol-1]){
                minCol = minCol-1;
            }
            if (min == matrix[i-1][minCol+1]){
                minCol = minCol + 1;
            }
            i--;
            VS[i] = minCol-1;
        }
        return VS;
    }




    //construct the minimal path sum array
    private double[][] min_sum_array(){
        double[][] M = new double[pic.height()][pic.width() + 2];
        for (int i = 0; i < pic.height(); i++){
            M[i][0] = Integer.MAX_VALUE;
            M[i][pic.width()+1] = Integer.MAX_VALUE;
        }
        for (int j = 1; j < pic.width() + 1; j++){
            M[0][j] = energy[0][j-1];
        }
        for (int i = 1; i < pic.height(); i++){
            for (int j = 1; j < pic.width() + 1; j++){
                M[i][j] = Math.min(Math.min(M[i-1][j], M[i-1][j+1]), M[i-1][j-1])+ energy[i][j-1];
            }
        }
        return M;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam){

    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam){

    }
}
