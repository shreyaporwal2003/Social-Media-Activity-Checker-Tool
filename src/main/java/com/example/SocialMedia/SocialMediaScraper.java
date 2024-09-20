package com.example.SocialMedia;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SocialMediaScraper {

    // List of known social media domains
    private static final Set<String> socialMediaDomains = Set.of(
            "facebook.com", "instagram.com", "linkedin.com", "twitter.com"
    );

    public static Set<String> scrapeSocialMediaLinks(String url) {
        Set<String> socialMediaLinks = new HashSet<>();
        try {
            // Connect to the website and parse its HTML
            Document doc = Jsoup.connect(url).get();

            // Get all anchor tags (links)
            Elements links = doc.select("a[href]");

            // Loop through links and find those pointing to social media platforms
            for (Element link : links) {
                String href = link.attr("href");
                for (String domain : socialMediaDomains) {
                    if (href.contains(domain)) {
                        socialMediaLinks.add(href);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to scrape the website: " + e.getMessage());
        }
        return socialMediaLinks;
    }

    public static void main(String[] args) {
        // Example company website
        String companyWebsite = "https://namekart.com/";

        // Scrape for social media links
        Set<String> socialMediaLinks = scrapeSocialMediaLinks(companyWebsite);

        // Output the found links
        if (!socialMediaLinks.isEmpty()) {
            System.out.println("Found social media links:");
            socialMediaLinks.forEach(System.out::println);
        } else {
            System.out.println("No social media links found.");
        }
    }
}
