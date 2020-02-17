import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class FoldingTest extends SkinDetector {

    int[] previousIndexLimit= new int[5],postIndexLimit= new int[5];
    int p=555, counter =1,postLimit=0, preLimit =0,preIndex=0,postIndex=0;
    int folderUnitSize =111;
    int n=0;
    ArrayList<File>[] folder;
   Image[] imageObject= new Image[555];



    public FoldingTest(File[] ln, File[] lm, int number) throws IOException {
        super(ln, lm);
        n=number;
        folder = new ArrayList [number];
    }

    public void imageInitializer(){

        for(int i=0;i<555;i++){
            imageObject[i]=new Image();
        }

        for(int i=0;i<555;i++){
            imageObject[i].setData(listOfMainFiles[i],listOfMaskFiles[i]);
        }
/*
        for(int i=0;i<555;i++) {
            System.out.println(imageObject[i].getImageFile());
            System.out.println(imageObject[i].getMaskFile());
        }*/


        Random rand = new Random();

        for (int i = 0; i < imageObject.length; i++)
        {
            int randomIndexToSwap = rand.nextInt(imageObject.length);
            Image temp = imageObject[randomIndexToSwap];
            imageObject[randomIndexToSwap] = imageObject[i];
            imageObject[i] = temp;
        }


        /*for(int i=0;i<555;i++) {
            System.out.println(imageObject[i].getImageFile());
            System.out.println(imageObject[i].getMaskFile());
        }

*/
        for(int i=0;i<555;i++) {
            listOfMainFiles[i] = imageObject[i].getImageFile();
            listOfMaskFiles[i] = imageObject[i].getMaskFile();
        }

        for(int i=0;i<555;i++) {
            System.out.println(listOfMainFiles[i]);
            System.out.println(listOfMaskFiles[i]);
        }


    }


    public void arrInitializer(){
        for(int i=0;i<n;i++){
            previousIndexLimit[i]=0;
            postIndexLimit[i]=0;
        }

    }

    public void fiveFolding()
    {
        System.out.println(folder.length);

        for (int i = 0; i < n; i++) {
            folder[i] = new ArrayList<File>();


        }

        while(counter <=n)
        {
           postLimit = folderUnitSize * counter;

           for(int k=preLimit;k<postLimit;k++)
           {
               folder[counter-1].add(listOfMainFiles[k]);
               //imageObject[k].add(listOfMainFiles[k],listOfMaskFiles[k]);
           }

           previousIndexLimit[counter-1]=preLimit;
           postIndexLimit[counter -1]=postLimit;

           preLimit =postLimit;
           counter++;
        }


    }


    public void data() throws IOException {

        arrInitializer();
        double[] result= new double[n];
        double finalAccuracy=0;

        for(int i=0;i<n;i++){
            result[i]=trainer(folder[i],previousIndexLimit,postIndexLimit,i);
        }

        for(int i=0;i<n;i++){
            finalAccuracy +=result[i];
        }
        finalAccuracy=finalAccuracy/5;

        String string=String.format("%.3f",finalAccuracy);
        System.out.println("Final Accuracy = "+string+"%");
    }
}


