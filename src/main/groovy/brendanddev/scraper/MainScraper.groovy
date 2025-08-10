package brendanddev.scraper

import groovy.cli.picocli.CliBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainScraper {

    static void main(String[] args) {

        // Create the cli parser
        def cli = new CliBuilder(usage: 'groovy MainScraper.groovy [options]')

    }

    /**
     * Prints application banner
     */
    static void printBanner() {
        println """
        ╔══════════════════════════════════════════════════════════════╗
        ║                           Web Scraper                        ║
        ╠══════════════════════════════════════════════════════════════╣                    
        ║                    by brendanddev                            ║
        ╠══════════════════════════════════════════════════════════════╣
        ╚══════════════════════════════════════════════════════════════╝
        """
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
        if (verbose) println "\n📊 Scraping table data..."
        ScraperUtils.scrapeTableData()
        ScraperUtils.respectfulDelay()
    }

    /**
     * Run JSON scraper
     */
    static void runJsonScraper(boolean verbose) {
        if (verbose) println "\n🔗 Scraping JSON API data..."
        ScraperUtils.scrapeJsonData()
        ScraperUtils.respectfulDelay()
    }
    
    /**
     * Run form scraper
     */
    static void runFormScraper(boolean verbose) {
        if (verbose) println "\n📝 Analyzing form structures..."
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
            Timestamp: ${getCurrentTimestamp()}
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
    
    /**
     * Get current timestamp
     */
    static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }


}