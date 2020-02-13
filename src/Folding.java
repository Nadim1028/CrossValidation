import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Folding
{
    File maskFolder = new File("data\\mask");
    File mainFolder = new File("data\\normal");
    File[] listOfMaskFiles = maskFolder.listFiles();
    File[] listOfMainFiles = mainFolder.listFiles();


    int n=5,preLimit=0,postLimit=0,counter=1,unit=111;
    int[] previousIndexLimit= new int[5],postIndexLimit= new int[5];
    ArrayList<File>[] a= new ArrayList [n];

    public void arrInitializer(){
        for(int i=0;i<5;i++){
            previousIndexLimit[i]=0;
            postIndexLimit[i]=0;
        }
    }

    public void folding(){

        for (int i = 0; i < n; i++) {
            a[i] = new ArrayList<File>();
        }

        while (counter<=n){
            postLimit=unit*counter;
            for(int k=preLimit;k<postLimit;k++)
            {
                a[counter-1].add(listOfMainFiles[k]);
            }

            previousIndexLimit[counter-1]=preLimit;
            postIndexLimit[counter-1]=postLimit;
            preLimit=postLimit;
            counter++;
        }

        for (int l=0;l<a[0].size();l++){
            //System.out.println();
            System.out.println(a[2].get(l));
        }
    }



    public static void main(String[] args) throws IOException
    {
        Folding f= new Folding();

    }
}