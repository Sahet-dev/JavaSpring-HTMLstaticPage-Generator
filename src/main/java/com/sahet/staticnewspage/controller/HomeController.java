package com.sahet.staticnewspage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.sahet.staticnewspage.service.StaticHtmlGenerator;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String greet() {
        return "index";
    }

    @RequestMapping("/new")
    public String about() {
        return "new-article/new";
    }

    @PostMapping("/generate")
    public String generateHtml(
            @RequestParam String title,
            @RequestParam String category,
            @RequestParam String date,
            @RequestParam String imageUrl,
            @RequestParam String content,
            Model model
    ) {
        // Generate a unique filename
        String outputFileName = title.replaceAll("\\s+", "_") + ".html";

        // Call the StaticHtmlGenerator to create the HTML file
        StaticHtmlGenerator.generateHtml(title, category, date, imageUrl, content, outputFileName);

        // Add the output filename to the model to confirm generation
        model.addAttribute("message", "HTML file generated successfully: " + outputFileName);
        model.addAttribute("fileName", outputFileName);

        return "index";
    }
}

