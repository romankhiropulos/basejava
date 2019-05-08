import java.io.File;
import java.util.Date;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        //File dir = new File("/home/roman/basejava");
        File dir = new File("/home/roman");

//        String filePath = "README.md";
//
//        File file = new File(filePath);
//        try {
//            System.out.println(file.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }
//
//
//        System.out.println(dir.isDirectory());
//        String[] list = dir.list();
//        if (list != null) {
//            for (String name : list) {
//                System.out.println(name);
//            }
//        }
//
//        try (FileInputStream fis = new FileInputStream(filePath)) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        System.out.println("Below you can see the all files from basejava directory.\n");
        getFilesNames(dir);
    }

    private static void getFilesNames(File folder) {
        StringBuilder indent = new StringBuilder();
        Date date = new Date();
        walkDirectory(folder, indent);
        Date date1 = new Date();
        walkDirectoryConcatenation(folder, " ");
        Date date2 = new Date();
        System.out.println("StringBuilder: " + (date1.getTime() - date.getTime()) + "\n" +
                "Concatenation: " + (date2.getTime() - date1.getTime()));
    }

    private static void walkDirectory(File folder, StringBuilder indent) {
        indent.append("  ");
        for(File file : Objects.requireNonNull(folder.listFiles())) {
            StringBuilder strOut = new StringBuilder();
            if(file.isDirectory()) {
                strOut.append(indent);
                strOut.append(file.getName());
                System.out.println(strOut);
                walkDirectory(file, indent);
            } else {
                strOut.append(indent);
                strOut.append(file.getName());
                System.out.println(strOut);
            }
        }
        indent.delete(indent.length() - 2, indent.length());
    }

    private static void walkDirectoryConcatenation(File folder, String indent) {
        indent += "  ";
        for(File file : Objects.requireNonNull(folder.listFiles())) {
            if(file.isDirectory()) {
                System.out.println(indent + file.getName());
                walkDirectoryConcatenation(file, indent);
            } else {
                System.out.println(indent + file.getName());
            }
        }
        indent.substring(0, indent.length() - 2);
    }
}
