package brendanddev.scraper

import org.jsoup.Jsoup

class ScraperUtils {

    static final String USER_AGENT = "Mozilla/5.0"
    static final int TIMEOUT = 5000
    static final int DELAY_BETWEEN_REQUESTS = 1000


    static void checkRobotsTxt(String baseUrl) {
        try {
            String robotsUrl = "${baseUrl}/robots.txt"
            String robotsContent = Jsoup.connect(robotsUrl)
                .userAgent(USER_AGENT)
                .timeout(TIMEOUT)
                .ignoreContentType(true)
                .execute()
                .body()
            
            if (robotsContent) {
                println "Robots.txt content for ${baseUrl}:"
                println robotsContent
            }

        } catch (Exception e) {
            println "Could not fetch robots.txt: ${e.message}"
            println "Site may not have a robots.txt or it's inaccessible."
        }
    }




}