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

        // Create the CLI parser
        def cli = CliUtils.createCliParser()

        // Parse the command line arguments into options
        def options = cli.parse(args)

        // If no options provided or help requested
        if (!options || options.h) {
            cli.usage()
            ScraperExamples.printUsageExamples()
            return
        }

        // Check for examples list
        if (options.l) {
            printCssSelectorExamples()
            return
        }

        // Check for interactive mode
        if (options.i) {
            CliUtils.runInteractiveMode()
            return
        }

        // Initialize logging
        boolean verbose = options.v
        String outputFile = options.o ?: null
        String format = options.format ?: 'text'

        // Print application banner
        CliUtils.printBanner()

        if (verbose) {
            println "Starting scraper at ${CliUtils.getCurrentTimestamp()}"
            println "Verbose mode: ON"
            if (outputFile) println "Output file: ${outputFile}"
        }

        try {
            // Handle custom URL scraping
            if (options.u) {
                if (!options.s) {
                    println "Error: --selector is required when using --url"
                    return
                }
                runCustomScraping(options.u, options.s, options.m?.toInteger(), outputFile, format, verbose)
                return
            }

            // Determine what to run based on options
            if (options.q) {
                runQuotesScraper(verbose)
            } else if (options.r) {
                runRobotsCheck(verbose)
            } else if (options.t) {
                runTableScraper(verbose)
            } else if (options.j) {
                runJsonScraper(verbose)
            } else if (options.f) {
                runFormScraper(verbose)
            } else if (options.a || (!options.r && !options.t && !options.j && !options.f && !options.q)) {
                runAllExamples(verbose)
            }

            // Save output if requested
            if (outputFile && !options.u) {
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
            println "Scraping session completed at ${CliUtils.getCurrentTimestamp()}"
        }
    }

    /**
     *
     */
    static void runCustomScraping(String url, String selector, Integer limit, String outputFile, String format, boolean verbose) {
        if (verbose) println "\nCustom scraping: $selector from $url"
        
        if (!CliUtils.isValidUrl(url)) {
            println "Error: Invalid URL format"
            return
        }

        List<String> results = ScraperUtils.scrapeElementsToList(url, selector, limit)
        
        if (results.isEmpty()) {
            println "No results found for selector '$selector'"
            return
        }

        // Display results
        CliUtils.displayResults(results)

        // Save to file if requested
        if (outputFile) {
            CliUtils.saveResults(results, outputFile, format, url, selector)
        }
    }

    /**
     * Runs all built-in examples
     */
    static void runAllExamples(boolean verbose) {
        println "\nRunning all scraping examples..."
        runRobotsCheck(verbose)
        runQuotesScraper(verbose)
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