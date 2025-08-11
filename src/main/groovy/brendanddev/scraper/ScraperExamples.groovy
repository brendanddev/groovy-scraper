package brendanddev.scraper

/**
 * This class contains all built in scraping examples to demonstrate various scraping techniques.
 */
class ScraperExamples {


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

}