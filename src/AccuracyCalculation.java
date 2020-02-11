import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AccuracyCalculation
{
    double ArraySumSkin=0,ArraySumNonSkin=0;
    double[][][] probOfSkin = new double[256][256][256];
    int[] maskCount= new int[555], outputCount = new int[555];

    BufferedWriter output1;
    {
        try {
            output1 = new BufferedWriter(new FileWriter("data\\output1.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




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

        for (int i=0;i<256;i++)
        {
            for (int j=0;j<256;j++)
            {
                for (int k=0;k<256;k++)
                {
                    output1.write( probOfSkin[i][j][k]+"\n");

                }
            }
        }

    }

    public void accuracyCalculator(File[] folderUnit) throws IOException
    {
        BufferedImage testImg=null,testImg2=null;

        for (int fileNumber=0;fileNumber<folderUnit.length;fileNumber++)
        {

            File input = new File(String.valueOf(folderUnit[fileNumber]));
            File maskInput = new File(String.valueOf(FoldingTest.listOfMaskFiles[fileNumber]));
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
                    //System.out.println(muskColor.getRed()+","+ muskColor.getGreen() + ","+muskColor.getBlue());
                    //System.out.println(newColor.getRed()+","+ newColor.getGreen() + ","+newColor.getBlue());
                    if (probOfSkin[newColor.getRed()][newColor.getGreen()][newColor.getBlue()] > 0.4)
                    {
                        testImg.setRGB(i, j, Color.black.getRGB());
                    }

                    else
                    {
                        testImg.setRGB(i, j, Color.white.getRGB());

                    }

                    if ((muskColor.getRed() < 240 && muskColor.getBlue() < 240 && muskColor.getGreen() < 240) )
                    {
                        maskCount[fileNumber]++;
                        if((newColor.getRed() < 240 && newColor.getBlue() < 240 && newColor.getGreen() < 240)) {
                            outputCount[fileNumber]++;
                        }
                    }

                }

            }

            // ImageIO.write(testImg,"jpg",new File("data\\outputImage\\outputImg"+fileNumber+".jpg"));

        }
    }

    public  double getAccuracy(){
        int sumOFMask=0,sumOfOutput=0;
        for(int i=0;i<maskCount.length;i++){
            sumOFMask += maskCount[i];
            sumOfOutput += outputCount[i];
        }

        double accuracy=0;
        accuracy=(double) (sumOfOutput*100)/(sumOFMask);
        System.out.format("Accuracy = %.3f",accuracy);

        return accuracy;

    }

}
