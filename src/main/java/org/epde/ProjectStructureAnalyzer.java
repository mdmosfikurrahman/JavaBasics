package org.epde;

import java.io.File;

public class ProjectStructureAnalyzer {

    public static void main(String[] args) {
        String projectPath = "E:\\Project\\RandomThings\\src\\main\\java\\com\\epde\\rt";
        File projectDirectory = new File(projectPath);

        if (projectDirectory.exists() && projectDirectory.isDirectory()) {
            listProjectStructure(projectDirectory, 0);
        } else {
            System.out.println("The specified project path does not exist or is not a directory.");
        }
    }

    public static void listProjectStructure(File directory, int indentLevel) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println(getIndentation(indentLevel) + "- " + file.getName());
                    listProjectStructure(file, indentLevel + 1);
                } else {
                    System.out.println(getIndentation(indentLevel) + "- " + file.getName());
                }
            }
        }
    }

    public static String getIndentation(int indentLevel) {
        return "  ".repeat(Math.max(0, indentLevel));
    }
}

