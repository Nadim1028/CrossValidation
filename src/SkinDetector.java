import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SkinDetector {
    BufferedImage mainImage=null,muskImage=null;
    static File[] listOfMaskFiles=null ;
    static File[] listOfMainFiles = null;


    int width,height;double ArraySumSkin=0,ArraySumNonSkin=0;
    double[][][] skin = new double[256][256][256],nonSkin = new double[256][256][256],probOfSkin = new double[256][256][256];

    BufferedWriter output1 = new BufferedWriter(new FileWriter("data\\output1.txt"));
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



    public void trainer(File[] f1) throws IOException {
        //System.out.println(f1.length);
        for(int l=f1.length; l<555;l++)
        {

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

        calculation();
        accuracyCalculator(f1);

    }

    public  void calculation() throws IOException {

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

    public void accuracyCalculator(File[] f1) throws IOException {

        for (int fileNumber=0;fileNumber<f1.length;fileNumber++){

        }

        File input = new File(String.valueOf(f1[2]));
        File maskInput = new File(String.valueOf(listOfMaskFiles[2]));

        BufferedImage testImg=null,testImg2=null;
        try {
            testImg=ImageIO.read(input);
            testImg2=ImageIO.read(maskInput);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int newHeight=testImg.getHeight(),newWidth=testImg.getWidth();
        int maskCount=0,outputCount=0;

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
                    maskCount++;
                    if((newColor.getRed() < 240 && newColor.getBlue() < 240 && newColor.getGreen() < 240)) {
                    outputCount++;
                    }
                }

            }

        }

        double accuracy=0;
        System.out.println(outputCount);
        System.out.println(maskCount);
        accuracy=(double) (outputCount*100)/(maskCount);
        System.out.format("%.3f",accuracy);

        ImageIO.write(testImg,"jpg",new File("data\\output.jpg"));

    }

}
