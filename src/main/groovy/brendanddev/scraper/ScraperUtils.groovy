package brendanddev.scraper

import org.jsoup.Jsoup

class ScraperUtils {

    static final String USER_AGENT = "Mozilla/5.0"
    static final int TIMEOUT = 5000
    static final int DELAY_BETWEEN_REQUESTS = 1000


















    

    /**
     * Saves the given string content to a file at the specified path.
     *
     * @param content The text content to write to the file.
     * @param filePath The path (including filename) where the content should be saved.
     */
    static void saveToFile(String content, String filePath) {
        try {
            new File(filePath).text = content
            println "Content saved to ${filePath}"
        } catch (IOException e) {
            println "Error saving content to file: ${e.message}"
        }
    }

    /**
     * Fetches the robots.txt file for a given base URL.
     * Prints the content of the robots.txt file if it exists.
     *
     * @param baseUrl The base URL of the website to check.
     */
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

    /**
     * Pauses the execution for a delay between HTTP requests to avoid overwhelming the target server
     */
    static void respectfulDelay() {
        try {
            Thread.sleep(DELAY_BETWEEN_REQUESTS)
        } catch (InterruptedException e) {
            println "Delay interrupted: ${e.message}"
        }
    }




}