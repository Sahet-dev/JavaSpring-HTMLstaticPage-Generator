package com.sahet.newsbackend.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class StaticHtmlGenerator {

    // List to store all generated HTML filenames
    private static final List<String> generatedFiles = new ArrayList<>();

    // Method to generate static HTML content for each specific document
    public static void generateHtml(String title, String category, String date, String imageUrl, String content, String outputFileName) {
        // Define the static HTML content (will be different based on input parameters)
        String htmlContent = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta http-equiv="X-UA-Compatible" content="ie=edge">
                <title>%s</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f4f4f9;
                        color: #333;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        width: 80%;
                        margin: 20px auto;
                        background-color: #fff;
                        padding: 20px;
                        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                    }
                    h1 {
                        font-size: 24px;
                        color: #0056b3;
                    }
                    .category {
                        font-size: 14px;
                        color: #777;
                        margin-bottom: 10px;
                    }
                    .date {
                        font-size: 14px;
                        color: #777;
                    }
                    .content {
                        font-size: 16px;
                        line-height: 1.6;
                        margin-top: 20px;
                    }
                    .image {
                        width: 100%;
                        height: auto;
                        margin: 20px 0;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>%s</h1>
                    <p class="category">Category: %s</p>
                    <p class="date">Published on: %s</p>
                    <img class="image" src="%s" alt="Image">
                    <div class="content">
                        <p>%s</p>
                    </div>
                </div>
            </body>
            </html>
        """;

        // Format the HTML content with the provided dynamic values
        htmlContent = String.format(htmlContent, title, title, category, date, imageUrl, content);

        // Create the output file in a directory (make sure the directory exists)
        File file = new File("static/" + outputFileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(htmlContent);
            System.out.println("Static HTML file '" + outputFileName + "' created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to update the index.html file with a list of generated HTML files
    private static void updateIndexPage() {
        // Define the HTML structure for the index page
        StringBuilder indexContent = new StringBuilder();
        indexContent.append("<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n");
        indexContent.append("<meta charset=\"UTF-8\">\n<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        indexContent.append("<title>Index of Static Pages</title>\n</head>\n<body>\n");
        indexContent.append("<h1>Index of All Static HTML Pages</h1>\n<ul>\n");

        // Add links to each generated HTML file in the index page
        for (String fileName : generatedFiles) {
            indexContent.append("<li><a href=\"" + fileName + "\">" + fileName + "</a></li>\n");
        }

        indexContent.append("</ul>\n</body>\n</html>");

        // Create or update the index.html file
        File indexFile = new File("index.html");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(indexFile))) {
            writer.write(indexContent.toString());
            System.out.println("Index page 'index.html' updated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Example 1: First article
        generateHtml(
                "Tramp bütin materigi eýelemekçi: hemmä haýran galdy",
                "Entertainment",
                "2025-01-11",
                "https://storage.kun.uz/source/thumbnails/_medium/10/8FVdC_BtO2BcHgYA9pJAmFROQ-uyLGPL_medium.jpg",
                "Çeşme: kun.uz, awtor: Ötkir Jalolhonow ...",
                "article1.html"
        );

        // Example 2: Second article
        generateHtml(
                "Another Dynamic Article Title",
                "News",
                "2025-01-12",
                "https://example.com/image.jpg",
                "This is the content of the second article...",
                "article2.html"
        );
    }
}
