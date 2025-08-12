package brendanddev.scraper

/**
 * This class contains all built in scraping examples to demonstrate various scraping techniques.
 */
class ScraperExamples {


    /**
     * Runs all built-in examples
     */
    static void runAllExamples() {
        println "\nRunning all scraping examples..."
        runRobotsCheck()
        runTableScraper()
        runJsonScraper()
        runFormScraper()
    }

    /**
     * Runs the robots.txt checker
     * 
     * @param verbose Whether to print detailed output
     */
    static void runRobotsCheck() {
        println TerminalStyles.info("\nChecking robots.txt compliance...")
        ScraperUtils.checkRobotsTxt("https://www.wikipedia.org")
        ScraperUtils.respectfulDelay()
    }

    /**
     * Run table scraper
     */
    static void runTableScraper() {
        println TerminalStyles.info("\nScraping table data...")
        ScraperUtils.scrapeTableData()
        ScraperUtils.respectfulDelay()
    }

    /**
     * Run JSON scraper
     */
    static void runJsonScraper() {
        println TerminalStyles.info("\nScraping JSON API data...")
        ScraperUtils.scrapeJsonData()
        ScraperUtils.respectfulDelay()
    }
    
    /**
     * Run form scraper
     */
    static void runFormScraper() {
        println TerminalStyles.info("\nAnalyzing form structures...")
        ScraperUtils.scrapeWithFormData()
        ScraperUtils.respectfulDelay()
    }

}