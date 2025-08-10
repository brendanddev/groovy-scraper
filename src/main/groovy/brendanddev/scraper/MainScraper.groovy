package brendanddev.scraper




class MainScraper {

    static void main(String[] args) {

        // Create the CLI parser
        def cli = CliUtils.createCliParser()

        // Parse the command line arguments into options
        def options = cli.parse(args)

        // If no options provided,
        if (!options || options.h) {
            cli.usage()
            return
        }

        // Initialize logging
        boolean verbose = options.v
        String outputFile = options.o ?: null

        // Print application banner
        CliUtils.printBanner()

        if (verbose) {
            println "Starting scraper at ${CliUtils.getCurrentTimestamp()}"
            println "Verbose mode: ON"
            if (outputFile) println "Output file: ${outputFile}"
        }

        try {
            // Determine what to run based on options
            if (options.r || options.a) {
                runRobotsCheck(verbose)
            }

            if (options.t || options.a) {
                runTableScraper(verbose)
            }

            if (options.j || options.a) {
                runJsonScraper(verbose)
            }

            if (options.f || options.a) {
                runFormScraper(verbose)
            }

            // Save output if requested
            if (outputFile) {
                String summary = generateSummary()
                ScraperUtils.saveToFile(summary, outputFile)
            }

        } catch (Exception e) {
            System.err.println "Error during scraping: ${e.message}"
            if (verbose) {
                e.printStackTrace()
            }
        } finally {
            println "\n" + "=" * 60
            println "Scraping session completed at ${getCurrentTimestamp()}"
            println "Remember: Always scrape responsibly!"
        }
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



}