import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SkinDetector extends AccuracyCalculation {
    BufferedImage mainImage=null,muskImage=null;

    static File[] listOfMaskFiles=null ;
    static File[] listOfMainFiles = null;

    int width,height;

    double[][][] skin = new double[256][256][256],nonSkin = new double[256][256][256];
  // AccuracyCalculation a=new AccuracyCalculation();

    BufferedWriter output2 = new BufferedWriter(new FileWriter("data\\output2.txt"));

    public SkinDetector(File[] ln,File[] lm) throws IOException {
        listOfMainFiles=ln;
        listOfMaskFiles=lm;

    }

    public void arrayInitializer(){
        for (int i=0;i<256;i++)
        {
            for (int j=0;j<256;j++)
            {
                for (int k=0;k<256;k++)
                {
                    skin[i][j][k]=0;
                    nonSkin[i][j][k]=0;

                }
            }
        }
    }



    public double trainer(File[] folderUnit, int[] previousIndexLimit, int[] postIndexLimit, int folderNumber) throws IOException {

        System.out.println(previousIndexLimit[folderNumber]);
        for(int l=0; l<555;l++)
        {
            if(l<previousIndexLimit[folderNumber] || l>=postIndexLimit[folderNumber]){

                File mainFile = listOfMainFiles[l];
                File muskFile = listOfMaskFiles[l];


                try
                {
                    muskImage= ImageIO.read(muskFile);
                    mainImage=ImageIO.read(mainFile);

                    width=muskImage.getWidth();
                    height=muskImage.getHeight();

                    for (int i = 0; i < width; i++)
                    {

                        for (int j = 0; j < height; j++)
                        {

                            Color muskColor = new Color(muskImage.getRGB(i, j));

                            Color normalColor = new Color(mainImage.getRGB(i, j));



                            if (muskColor.getRed() > 245 && muskColor.getBlue() > 245 && muskColor.getGreen() > 245)
                            {


                                nonSkin[normalColor.getRed()][normalColor.getGreen()][normalColor.getBlue()]++;

                            }

                            else {

                                // output1.write((int) skin[normalColor.getRed()][normalColor.getGreen()][normalColor.getBlue()]++);
                                skin[normalColor.getRed()][normalColor.getGreen()][normalColor.getBlue()]++;
                            }


                        }

                    }

                }

                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        calculation(skin,nonSkin);
        accuracyCalculator(folderUnit,previousIndexLimit,postIndexLimit,folderNumber);
       double r= getAccuracy();

       return r;

    }


}
