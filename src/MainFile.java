import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = "ExampleTextFile";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("/home/roman/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Below you can see the all files from basejava directory.");
        getFilesNames(dir);
    }

    private static void getFilesNames(File folder){
        for (File currentFile : folder.listFiles()) {
            if (!currentFile.isDirectory()) {
                System.out.println(currentFile.getName());
            }
            else {
                System.out.println(currentFile.getName());
                getFilesNames(currentFile);
            }
        }
    }
}
