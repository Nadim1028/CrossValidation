import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Folding
{
    public static void main(String[] args) throws IOException {
        File maskFolder = new File("data\\mask");
        File mainFolder = new File("data\\normal");

        File[] listOfMaskFiles=maskFolder.listFiles();
        File[] listOfMainFiles=maskFolder.listFiles();

        File[] folder1 = new File[55];
        File[] folder2 = new File[500];
        int f1=0,f2=0;

        BufferedImage mainImage=null,muskImage=null;

        int width,height;double ArraySumSkin=0,ArraySumNonSkin=0;
        double[][][] skin = new double[256][256][256],nonSkin = new double[256][256][256],probOfSkin = new double[256][256][256];

        BufferedWriter output1 = new BufferedWriter(new FileWriter("data\\output1.txt"));
        BufferedWriter output2 = new BufferedWriter(new FileWriter("data\\output2.txt"));

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

        for(int i=0;i<=554;i++)
        {

            if(i<55)
            {
                folder1[f1++] = listOfMainFiles[i];
            }

            else if(i>=55){
                folder2[f2++] = listOfMainFiles[i];
            }
        }

       /* for(int j=0;j<folder2.length;j++){
            System.out.println(folder2[j]);
        }*/

        for(int l=55; l<555;l++)
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

        File input = new File(String.valueOf(folder1[2]));

        BufferedImage testImg=null;
        try {
            testImg=ImageIO.read(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(testImg);
        int newHeight=testImg.getHeight(),newWidth=testImg.getWidth();


        for (int i = 0; i < newWidth; i++)
        {

            for (int j = 0; j < newHeight; j++) {

                Color newColor = new Color(testImg.getRGB(i, j));
                if (probOfSkin[newColor.getRed()][newColor.getGreen()][newColor.getBlue()] > 0.45)
                {
                    testImg.setRGB(i, j, Color.green.getRGB());
                }

                else
                {
                    testImg.setRGB(i, j, 0);

                }

            }

        }

        //ImageIO.write(testImg,"jpg",new File("data\\output.jpg"));
        ImageIO.write(testImg,"jpg",new File("data\\newOutput.jpg"));

    }
}