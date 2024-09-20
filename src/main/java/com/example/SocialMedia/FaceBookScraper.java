package com.example.SocialMedia;

import java.io.IOException;
import java.time.LocalDate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class FaceBookScraper {

    public static boolean checkFacebookActivity(String pageUrl) {
        try {
            Document doc = Jsoup.connect(pageUrl).get();
            // Scrape the latest post date (the actual selector depends on Facebook's HTML structure)
            Element postDateElement = doc.select("abbr").first();  // Example selector
            if (postDateElement != null) {
                String postDate = postDateElement.attr("data-utime"); // Extract Unix timestamp
                return isPostInLastSixMonths(postDate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean isPostInLastSixMonths(String postDate) {
        // Convert Unix timestamp to LocalDate and check if it's within the last six months
        long timestamp = Long.parseLong(postDate);
        LocalDate postDateParsed = LocalDate.ofEpochDay(timestamp / (24 * 60 * 60));
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        return postDateParsed.isAfter(sixMonthsAgo);
    }

    public static void main(String[] args) {
        String facebookUrl = "https://www.facebook.com/namekart";
        boolean isActive = checkFacebookActivity(facebookUrl);
        System.out.println("Facebook Activity in last 6 months: " + isActive);
    }
}

