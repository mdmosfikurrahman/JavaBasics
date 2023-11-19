package org.epde;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import javax.imageio.ImageIO;

public class ProjectStructureToImage {

    public static void main(String[] args) {
        String projectPath = "E:\\Project\\RandomThings\\src\\main\\java\\com\\epde\\rt";
        File projectDirectory = new File(projectPath);

        if (projectDirectory.exists() && projectDirectory.isDirectory()) {
            String consoleOutput = captureConsoleOutput(() -> listProjectStructure(projectDirectory, 0));

            // Create an image from the console output
            createImageFromText(consoleOutput, "output.png", 2000, 2000); // Set the image dimensions here
        } else {
            System.out.println("The specified project path does not exist or is not a directory.");
        }
    }

    public static String captureConsoleOutput(Runnable codeToRun) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        codeToRun.run();

        System.setOut(System.out);
        return baos.toString();
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

    public static void createImageFromText(String text, String outputFileName, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        graphics.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.PLAIN, 18); // Increase font size
        graphics.setFont(font);

        String[] lines = text.split("\n");
        int lineHeight = graphics.getFontMetrics().getHeight() + 5; // Increase line height
        int y = lineHeight;
        for (String line : lines) {
            graphics.drawString(line, 10, y);
            y += lineHeight;
        }

        graphics.dispose();

        try {
            File imageFile = new File(outputFileName);
            ImageIO.write(image, "png", imageFile);
            System.out.println("Image saved as " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
