import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AccuracyCalculation
{
    double ArraySumSkin=0,ArraySumNonSkin=0;
    double[][][] probOfSkin = new double[256][256][256];
    //int[] truePosCount= new int[555], trueNegCount = new int[555];
    int truePosCount= 0, trueNegCount = 0;
    double[] perImageAccuracy = new double[111];

    public  void calculation( double[][][] skin, double[][][] nonSkin) throws IOException {

        for (int i=0;i<256;i++)
        {
            for (int j=0;j<256;j++)
            {
                for (int k=0;k<256;k++)
                {
                    ArraySumSkin += skin[i][j][k];
                    ArraySumNonSkin += nonSkin[i][j][k];

                }
            }
        }

        for (int i=0;i<256;i++)
        {
            for (int j=0;j<256;j++)
            {
                for (int k=0;k<256;k++)
                {
                    skin[i][j][k] = skin[i][j][k]/ArraySumSkin;
                    nonSkin[i][j][k] = nonSkin[i][j][k]/ArraySumNonSkin;

                }
            }
        }

        for (int i=0;i<256;i++)
        {
            for (int j=0;j<256;j++)
            {
                for (int k=0;k<256;k++)
                {
                    if(skin[i][j][k]==0 && nonSkin[i][j][k]==0)
                        probOfSkin[i][j][k]=0;

                    else if(nonSkin[i][j][k]==0)
                        probOfSkin[i][j][k]=0.5;

                    else
                        probOfSkin[i][j][k]=skin[i][j][k]/nonSkin[i][j][k];



                }
            }
        }

    }

    public void accuracyCalculator(ArrayList<File> folderUnit, int[] previousIndexLimit, int[] postIndexLimit, int folderNumber) throws IOException
    {
        BufferedImage testImg=null,testImg2=null;

        for (int fileNumber=0;fileNumber<111;fileNumber++)
        {

            File input = new File(String.valueOf(folderUnit.get(fileNumber)));
            File maskInput = new File(String.valueOf(FoldingTest.listOfMaskFiles[fileNumber+folderNumber*111]));
            try {
                testImg= ImageIO.read(input);
                testImg2=ImageIO.read(maskInput);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int newHeight=testImg.getHeight(),newWidth=testImg.getWidth();

            for (int i = 0; i < newWidth; i++)
            {
                for (int j = 0; j < newHeight; j++) {
                    Color newColor = new Color(testImg.getRGB(i, j));
                    Color muskColor = new Color(testImg2.getRGB(i,j));
                   // System.out.println(muskColor.getRed()+","+ muskColor.getGreen() + ","+muskColor.getBlue());
                    //System.out.println(newColor.getRed()+","+ newColor.getGreen() + ","+newColor.getBlue());
                    if (probOfSkin[newColor.getRed()][newColor.getGreen()][newColor.getBlue()] > 0.4)
                    {
                        testImg.setRGB(i, j, Color.black.getRGB());
                    }

                    else
                    {
                        testImg.setRGB(i, j, Color.white.getRGB());

                    }

                    newColor = new Color(testImg.getRGB(i, j));

                    if ((muskColor.getRed() < 240 && muskColor.getBlue() < 240 && muskColor.getGreen() < 240) )
                    {
                        if((newColor.getRed() < 240 && newColor.getBlue() < 240 && newColor.getGreen() < 240)) {
                            truePosCount++;
                        }
                    }


                    if ((muskColor.getRed() >= 240 && muskColor.getBlue() >= 240 && muskColor.getGreen() >= 240) )
                    {
                        if((newColor.getRed() >= 240 && newColor.getBlue() >= 240 && newColor.getGreen() >= 240)) {
                            trueNegCount++;
                        }
                    }
                }
            }

            perImageAccuracy[fileNumber]=(double) (truePosCount+trueNegCount)/(newHeight*newWidth);
           // System.out.println(perImageAccuracy[fileNumber]);
            truePosCount=0;
            trueNegCount=0;
            // ImageIO.write(testImg,"jpg",new File("data\\outputImage\\outputImg"+fileNumber+".jpg"));

        }
    }

    public  double getAccuracy()
    {
        double folderAccuracy=0;
        for(int i=0;i<perImageAccuracy.length;i++){
            folderAccuracy += perImageAccuracy[i];
        }

        double overallFolderAccuracy;
        overallFolderAccuracy=(folderAccuracy/111)*100;

        String string=String.format("%.3f",overallFolderAccuracy);
        System.out.println(string+"%");

        return overallFolderAccuracy;
    }

}
