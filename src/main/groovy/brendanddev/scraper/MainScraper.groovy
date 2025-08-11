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
     * Runs all built-in examples
     */
    static void runAllExamples(boolean verbose) {
        println "\nRunning all scraping examples..."
        runRobotsCheck(verbose)
        runTableScraper(verbose)
        runJsonScraper(verbose)
        runFormScraper(verbose)
    }
    

    /**
     * Runs the robots.txt checker
     * 
     * @param verbose Whether to print detailed output
     */
    static void runRobotsCheck(boolean verbose) {
        if (verbose) println "\nChecking robots.txt compliance..."
        ScraperUtils.checkRobotsTxt("http://quotes.toscrape.com")
        ScraperUtils.respectfulDelay()
    }

    /**
     * Run table scraper
     */
    static void runTableScraper(boolean verbose) {
        if (verbose) println "\nScraping table data..."
        ScraperUtils.scrapeTableData()
        ScraperUtils.respectfulDelay()
    }

    /**
     * Run JSON scraper
     */
    static void runJsonScraper(boolean verbose) {
        if (verbose) println "\nScraping JSON API data..."
        ScraperUtils.scrapeJsonData()
        ScraperUtils.respectfulDelay()
    }
    
    /**
     * Run form scraper
     */
    static void runFormScraper(boolean verbose) {
        if (verbose) println "\nAnalyzing form structures..."
        ScraperUtils.scrapeWithFormData()
        ScraperUtils.respectfulDelay()
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
            Targets: Educational websites only

            Examples Demonstrated:
            - HTML element extraction
            - Table data parsing  
            - JSON API consumption
            - Form structure analysis
            - Ethical robots.txt checking

        """.trim()
    }



}