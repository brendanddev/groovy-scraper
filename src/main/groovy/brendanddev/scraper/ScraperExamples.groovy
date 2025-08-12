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
        print TerminalStyles.info("\nChecking robots.txt compliance...")
        ScraperUtils.checkRobotsTxt("http://quotes.toscrape.com/")
        ScraperUtils.respectfulDelay()
    }

    /**
     * Run table scraper
     */
    static void runTableScraper() {
        print TerminalStyles.info("\nScraping table data...")
        ScraperUtils.scrapeTableData()
        ScraperUtils.respectfulDelay()
    }

    /**
     * Run JSON scraper
     */
    static void runJsonScraper() {
        print TerminalStyles.info("\nScraping JSON API data...")
        ScraperUtils.scrapeJsonData()
        ScraperUtils.respectfulDelay()
    }
    
    /**
     * Run form scraper
     */
    static void runFormScraper() {
        print TerminalStyles.info("\nAnalyzing form structures...")
        ScraperUtils.scrapeWithFormData()
        ScraperUtils.respectfulDelay()
    }

}