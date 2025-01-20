package com.sahet.staticnewspage.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class StaticHtmlGenerator {

    public static void generateHtml(String title, String category, String date, String imageUrl, String content, String outputFileName) {
        // Define the HTML template
        String htmlContent = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta http-equiv="X-UA-Compatible" content="ie=edge">
                <link rel="stylesheet" href="/styles/article.css">
                <title>%s</title>
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

        // Ensure the "articles" directory exists
        File directory = new File("src/main/resources/static/articles/");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Write the formatted content to the output file
        File file = new File(directory, outputFileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(htmlContent);
            System.out.println("Static HTML file '" + file.getAbsolutePath() + "' created successfully!");
        } catch (IOException e) {
            System.err.println("Error writing the HTML file: " + e.getMessage());
        }

        // Update the index.html
        updateIndexPage();
    }

    public static void updateIndexPage() {
        File articlesDirectory = new File("src/main/resources/static/articles/");
        File[] files = articlesDirectory.listFiles((dir, name) -> name.endsWith(".html"));

        if (files == null || files.length == 0) {
            System.out.println("No articles found to update the index page.");
            return;
        }

        // Start building the index page content
        StringBuilder indexContent = new StringBuilder("""
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Generated Articles</title>
            <link rel="stylesheet" href="/styles/index.css">
            <link rel="stylesheet" href="../static/styles/index.css">
        </head>
        <body>
            <h1>Latest News</h1>
                <div class="container">
                    <div class="articles-section">
    """);

        // Add each article to the index page
        Arrays.stream(files).forEach(file -> {
            String fileName = file.getName();
            String link = "/articles/" + fileName;

            // Parse details from the file name (assuming file names follow a consistent pattern)
            String title = fileName.replace("_", " ").replace(".html", ""); // Simple parsing for demonstration
            String category = "Unknown"; // This needs to be retrieved dynamically from metadata
            String date = "Unknown"; // This needs to be retrieved dynamically from metadata
            String imageUrl = "/path/to/default/image.jpg"; // Default or parsed value

            // Append the HTML for each article
            indexContent.append(String.format("""
                    <div class="article-card">
                <!-- Text Section -->
                        <div class="text">
                            <span class="category-badge">%s</span>
                            <h3 class="article-title"><a href="%s">%s</a></h3>
                            <div class="article-date">%s</div>
                        </div>

                <!-- Image Section -->
                        <div class="image article-image">
                            <a href="%s"><img src="%s" alt="%s"></a>
                        </div>
                    </div>
        """, category, link, title, date, link, imageUrl, title));
        });

        // Close the HTML structure
        indexContent.append("""
                </div>
            </div>
        </body>
        </html>
    """);

        // Save the index.html file in the templates directory
        File indexFile = new File("src/main/resources/templates/index.html");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(indexFile))) {
            writer.write(indexContent.toString());
            System.out.println("Index page updated: " + indexFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error writing the index.html file: " + e.getMessage());
        }
    }

}
