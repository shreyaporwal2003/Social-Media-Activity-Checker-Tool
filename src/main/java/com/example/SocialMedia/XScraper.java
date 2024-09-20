package com.example.SocialMedia;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class XScraper {

    public static boolean checkXActivity(String profileUrl) {
        try {
            Document doc = Jsoup.connect(profileUrl).get();
            // Scrape the timestamp of the most recent post
            Element postDateElement = doc.select("time").first();  // Instagram's HTML structure may change
            if (postDateElement != null) {
                String postDate = postDateElement.attr("datetime"); // Get 'datetime' attribute
                return isPostInLastSixMonths(postDate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean isPostInLastSixMonths(String postDate) {
        // Convert string to date and check if it's within the last six months
        LocalDate postDateParsed = LocalDate.parse(postDate, DateTimeFormatter.ISO_DATE_TIME);
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        return postDateParsed.isAfter(sixMonthsAgo);
    }

    public static void main(String[] args) {
        String XUrl = "https://twitter.com/namekart";
        boolean isActive = checkXActivity(XUrl);
        System.out.println("Twitter Activity in last 6 months: " + isActive);
    }
}
