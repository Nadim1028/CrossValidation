import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MainClass
{
    public static void main(String[] args) throws IOException
    {
         File maskFolder = new File("data\\mask");
         File mainFolder = new File("data\\normal");
         File[] listOfMaskFiles = maskFolder.listFiles();
         File[] listOfMainFiles = mainFolder.listFiles();
         Scanner input=new Scanner(System.in);
         int number;

         System.out.println("Enter an integer number.");
         number=input.nextInt();

         FoldingTest f= new FoldingTest(listOfMainFiles,listOfMaskFiles,number);
         f.arrInitializer();
         f.fiveFolding();
         f.data();
    }
}
