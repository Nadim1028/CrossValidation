import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class FoldingTest {

    static File maskFolder = new File("data\\mask");
    static  File mainFolder = new File("data\\normal");

    static File[] listOfMaskFiles = maskFolder.listFiles();
    static File[] listOfMainFiles = mainFolder.listFiles();

     static    File[] folder1 = new File[111];
        File[] folder2 = new File[111];
        File[] folder3 = new File[111];
        File[] folder4 = new File[111];
        File[] folder5 = new File[111];


        public int p=555,m=1,limit=0,previousLimit=0;
           int  c=555/5;




    public void fiveFolding()
           {
               while(m<=5)
               {
                   limit = c * m;

                   if(m==1)
                    folder1=folderMaker(folder1,previousLimit,limit);
                   else if(m==2)
                     folder2=folderMaker(folder2,previousLimit,limit);
                   else if(m==3)
                    folder3=folderMaker(folder3,previousLimit,limit);
                   else if(m==4)
                    folder4=folderMaker(folder4,previousLimit,limit);
                   else
                    folder5=folderMaker(folder5,previousLimit,limit);

                   previousLimit=limit;
                   m++;
               }

               for(int k=0;k<folder1.length;k++){
                  // System.out.println(folder1[k]);
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

    SkinDetector s;
    {
        try {
            s = new SkinDetector(listOfMainFiles,listOfMaskFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void data() throws IOException {
        s.arrayInitializer();
        s.trainer(folder1);

    }


}



/*
            while(m<=5)
            {
                limit=c*m;
                i=0;

                if(m==1){
                    for(int j=previousLimit;j<limit;j++)
                    {
                        folder1[i++] = listOfMainFiles[j];
                    }
                }



                else if(m==2){
                    System.out.println(previousLimit);
                    System.out.println(limit);
                    for(int j=previousLimit;j<limit;j++)
                    {
                        folder2[i++] = listOfMainFiles[j];
                    }
                }

                else if(m==3){
                    for(int j=previousLimit;j<limit;j++)
                    {
                        folder3[i++] = listOfMainFiles[j];
                    }
                }

                else if(m==4){
                    for(int j=previousLimit;j<limit;j++)
                    {
                        folder4[i++] = listOfMainFiles[j];
                    }
                }

                else{
                    for(int j=previousLimit;j<limit;j++)
                    {
                        folder5[i++] = listOfMainFiles[j];
                    }
                }

                previousLimit=limit;
                m++;
            }*/
