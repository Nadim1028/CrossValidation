import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class FoldingTest extends SkinDetector {

    File[] folder1 = new File[111];
    File[] folder2 = new File[111];
    File[] folder3 = new File[111];
    File[] folder4 = new File[111];
    File[] folder5 = new File[111];

    static int[] previousIndexLimit= new int[5],postIndexLimit= new int[5];
    static int p=555,m=1,limit=0,previousLimit=0,preIndex=0,postIndex=0;
    int  c=555/5;

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
               while(m<=5)
               {
                   limit = c*m;

                   if(m==1){
                       previousIndexLimit[m-1]=previousLimit;
                       postIndexLimit[m-1]=limit;
                       folder1=folderMaker(folder1,previousLimit,limit);
                   }
                   else if(m==2){
                       previousIndexLimit[m-1]=previousLimit;
                       postIndexLimit[m-1]=limit;
                       folder2=folderMaker(folder2,previousLimit,limit);
                   }

                   else if(m==3){
                   previousIndexLimit[m-1]=previousLimit;
                       postIndexLimit[m-1]=limit;
                       folder3=folderMaker(folder3,previousLimit,limit);
                   }
                   else if(m==4){
                   previousIndexLimit[m-1]=previousLimit;
                       postIndexLimit[m-1]=limit;
                       folder4=folderMaker(folder4,previousLimit,limit);
               }
                   else{
                   previousIndexLimit[m-1]=previousLimit;
                       postIndexLimit[m-1]=limit;
                       folder5=folderMaker(folder5,previousLimit,limit);
                   }

                   previousLimit=limit;
                   m++;
               }


           }



    private static File[] folderMaker(File[] folder, int previousLimit, int limit)
    {
        int i=0;
        for(int j=previousLimit;j<limit;j++)
        {
            folder[i++] = listOfMainFiles[j];
        }
        return folder;
    }

    public void data() throws IOException {
       /* s.arrayInitializer();
        double t1,t2,t3,t4,t5;
        t1=s.trainer(folder1,previousIndexLimit,postIndexLimit,0);
        t2=s.trainer(folder2,previousIndexLimit,postIndexLimit,1);
        t3=s.trainer(folder3,previousIndexLimit,postIndexLimit,2);
        t4=s.trainer(folder4,previousIndexLimit,postIndexLimit,3);
        t5=s.trainer(folder5,previousIndexLimit,postIndexLimit,4);

        double t=(t1+t2+t3+t4+t5)/5;
        System.out.format("Final Accuracy = %.3f",t);*/

       arrInitializer();
        double t1,t2,t3,t4,t5;
        t1=trainer(folder1,previousIndexLimit,postIndexLimit,0);
        t2=trainer(folder2,previousIndexLimit,postIndexLimit,1);
        t3=trainer(folder3,previousIndexLimit,postIndexLimit,2);
        t4=trainer(folder4,previousIndexLimit,postIndexLimit,3);
        t5=trainer(folder5,previousIndexLimit,postIndexLimit,4);
        double t=(t1+t2+t3+t4+t5)/5;
        System.out.format("Final Accuracy = %.3f",t);

    }
}


