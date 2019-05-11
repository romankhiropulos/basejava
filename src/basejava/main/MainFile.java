package basejava.main;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        //File dir = new File("/home/roman/basejava");
        File dir = new File("/home/roman/basejava/src/basejava");
        //File dir = new File("/home/roman");

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
        String str = "";
        StringBuilder stringBuilder = new StringBuilder();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            stringBuilder.append(i);
        }
        long middle = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            str += i;
        }
        long end = System.currentTimeMillis();

        System.out.println("StringBuilder: " + (middle - start) + "\n" +
                "Concatenation: " + (end - middle));

        System.out.println("Below you can see the all files from basejava directory.\n");
        getFilesNames(dir);
    }

    private static void getFilesNames(File folder) {
        StringBuilder indent = new StringBuilder();
        long start = System.currentTimeMillis();
        walkDirectory(folder, indent);
        long middle = System.currentTimeMillis();
        walkDirectoryConcatenation(folder, "");
        long end = System.currentTimeMillis();
        System.out.println("StringBuilder: " + (middle - start) + "\n" +
                "Concatenation: " + (end - middle));
    }

    private static void walkDirectory(File folder, StringBuilder indent) {
        for(File file : Objects.requireNonNull(folder.listFiles())) {
            StringBuilder strOut = new StringBuilder();
            if(file.isDirectory()) {
                System.out.println(strOut.append(indent).append("Directory: ").append(file.getName()));
                walkDirectory(file, indent.append("\t"));
            } else {
                System.out.println(strOut.append(indent).append("File: ").append(file.getName()));
            }
        }
        try {
            indent.delete(indent.length() - 1, indent.length());
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("End of directory have been reached.");
        }
    }

    private static void walkDirectoryConcatenation(File folder, String indent) {
        for(File file : Objects.requireNonNull(folder.listFiles())) {
            if(file.isDirectory()) {
                System.out.println(indent + "Directory: " + file.getName());
                walkDirectoryConcatenation(file, indent + "\t");
            } else {
                System.out.println(indent + "File: " + file.getName());
            }
        }
    }
}
