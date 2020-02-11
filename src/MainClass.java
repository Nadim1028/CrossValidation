import java.io.File;
import java.io.IOException;

public class MainClass
{
    public static void main(String[] args) throws IOException {

         File maskFolder = new File("data\\mask");
         File mainFolder = new File("data\\normal");
         File[] listOfMaskFiles = maskFolder.listFiles();
         File[] listOfMainFiles = mainFolder.listFiles();
         FoldingTest f= new FoldingTest(listOfMainFiles,listOfMaskFiles);f.arrInitializer();
         f.fiveFolding();
         f.data();

    }
}
