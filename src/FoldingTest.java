import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class FoldingTest extends SkinDetector {

    static int[] previousIndexLimit= new int[5],postIndexLimit= new int[5];
    static int p=555, counter =1,postLimit=0, preLimit =0,preIndex=0,postIndex=0;
    int folderUnitSize =111;
    int n=5;
    ArrayList<File>[] folder= new ArrayList [n];
    Scanner input=new Scanner(System.in);

    public FoldingTest(File[] ln, File[] lm) throws IOException {
        super(ln, lm);
    }

    public void arrInitializer(){
        for(int i=0;i<5;i++){
            previousIndexLimit[i]=0;
            postIndexLimit[i]=0;
        }

    }

    public void fiveFolding()
    {

        /*System.out.println("Enter a integer number.");
        n=input.nextInt();*/
        for (int i = 0; i < n; i++) {
            folder[i] = new ArrayList<File>();
        }
        while(counter <=5)
        {
           postLimit = folderUnitSize * counter;

           for(int k=preLimit;k<postLimit;k++)
           {
               folder[counter-1].add(listOfMainFiles[k]);
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

        System.out.format("Final Accuracy = %.3f",finalAccuracy/5);
    }
}


