package brendanddev.scraper


/**
 * Main entry point to the web scraper cli application
 *
 * This class handles parsing command line arguments, handling user input and options,
 * dispatching to the appropriate scraping functions including interactive mode, built-in
 * scraping examples, and custom scraping based on user-provided URLs and CSS selectors.
 */
class MainScraper {

    static void main(String[] args) {
        try {
            CliUtils.runScraperMenu()

        } catch (Exception e) {
            System.err.println "Fatal error: ${e.message}"
            e.printStackTrace()
        } finally {
            println "\n" + "=" * 60
            println "Scraping session completed at ${CliUtils.getCurrentTimestamp()}"
        }
    }

    /**
     * Generate session summary
     */
    static String generateSummary() {
        return """
            Web Scraping Session Summary
            ============================
            Timestamp: ${CliUtils.getCurrentTimestamp()}
            Tools Used: Groovy + JSoup
        """.trim()
    }

}