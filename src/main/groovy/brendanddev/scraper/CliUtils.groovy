package brendanddev.scraper

import groovy.cli.picocli.CliBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Scanner

/**
 * Defines a utility class for command line interface operations in the web scraper application.
 * 
 * The class provides methods to create and configure the CLI parser with all available options,
 * validate URLs, print banners and usage examples, and handle interactive scraping mode.
 * It centralizes CLI-related logic to keep the main application code clean and modular.
 */
class CliUtils {

    /**
     * Creates and configures the CLI parser with all options
     */
    static CliBuilder createCliParser() {
        def cli = new CliBuilder(usage: 'groovy MainScraper.groovy [options]')

        // Basic options
        cli.h(longOpt: 'help', 'Show this help message')
        cli.a(longOpt: 'all', 'Run all scraping examples (default)')
        cli.t(longOpt: 'tables', 'Scrape table data only')
        cli.j(longOpt: 'json', 'Scrape JSON data only')
        cli.f(longOpt: 'forms', 'Scrape form data only')
        cli.r(longOpt: 'robots', 'Check robots.txt only')
        cli.q(longOpt: 'quotes', 'Scrape quotes from quotes.toscrape.com')

        // Custom scraping options
        cli.u(longOpt: 'url', args: 1, argName: 'URL', 'Custom URL to scrape')
        cli.s(longOpt: 'selector', args: 1, argName: 'CSS_SELECTOR', 'CSS selector for custom scraping (use with --url)')
        // cli.a(longOpt: 'attribute', args: 1, argName: 'ATTRIBUTE', 'Extract specific attribute instead of text (optional)')
        cli.i(longOpt: 'interactive', 'Start interactive mode for guided scraping')
        cli.l(longOpt: 'list-examples', 'Show CSS selector examples and exit')

        // Output and formatting options
        cli.o(longOpt: 'output', args: 1, argName: 'file', 'Output file for results')
        // cli.f(longOpt: 'format', args: 1, argName: 'FORMAT', 'Output format: text, json, csv (default: text)')
        cli.v(longOpt: 'verbose', 'Verbose output')
        cli.m(longOpt: 'limit', args: 1, argName: 'N', 'Limit results to N items')

        return cli
    }

    /**
     * Validates the provided URL
     *
     * @param url The URL to validate
     * @return true if the URL is valid, false otherwise
     */
    static boolean isValidUrl(String url) {
        return url ==~ /^https?:\/\/.+/
    }


    /**
     * Attempts to extract the base URL from a full URL
     *
     * @param url The full URL
     * @return The base URL (protocol + host) or the original URL if extraction fails
     */
    static String extractBaseUrl(String url) {
        try {
            URL urlObj = new URL(url)
            return "${urlObj.protocol}://${urlObj.host}"

        } catch (Exception e) {
            println "Error extracting base URL: ${e.message}"
            return url
        }
    }
    
    /**
     * Controls the interactive mode for the scraper
     */
    static void runInteractiveMode() {
        Scanner scanner = new Scanner(System.in)

        printBanner()
        println "\nWelcome to Interactive Scraping Mode!"
        println "This will guide you through scraping any website."
        println "=" * 50

        try {
            // Get URL
            print "\nEnter the website URL to scrape: "
            String url = scanner.nextLine().trim()

            if (!isValidUrl(url)) {
                println "Invalid URL format. Please include http:// or https://"
                return
            }

            // Check robots.txt 
            String baseUrl = extractBaseUrl(url)
            println "\nChecking robots.txt for $baseUrl ..."
            ScraperUtils.checkRobotsTxt(baseUrl)

            // Ask for a CSS selector
            println "\nCommon selectors:"
            println "  h1, h2, h3        - Headers"
            println "  .class-name       - Elements with a class"
            println "  #element-id       - Element with specific ID"
            println "  a                 - All links"
            println "  p                 - All paragraphs"
            
            print "\nEnter a CSS selector (e.g., h1, .class, #id): "
            String selector = scanner.nextLine().trim()

            if (selector.isEmpty()) {
                println "No selector provided. Exiting interactive mode."
                return
            }

            // Ask for limit
            print "\nLimit results (press Enter for no limit): "
            String limitStr = scanner.nextLine().trim()
            Integer limit = limitStr.isEmpty() ? null : limitStr.toInteger()

            // Run scraping
            println "\nScraping '$selector' from $url ..."
            List<String> results = ScraperUtils.scrapeElementsToList(url, selector, limit)
            
            if (results.isEmpty()) {
                println "No results found for selector '$selector'"
            } else {
                displayResults(results)
                
                // Ask if user wants to save results
                print "\nSave results to file? (y/n): "
                String saveResponse = scanner.nextLine().trim().toLowerCase()
                
                if (saveResponse.startsWith('y')) {
                    print "Enter filename: "
                    String filename = scanner.nextLine().trim()
                    
                    if (!filename.isEmpty()) {
                        print "Choose format (text/json/csv) [text]: "
                        String format = scanner.nextLine().trim()
                        format = format.isEmpty() ? 'text' : format
                        
                        saveResults(results, filename, format, url, selector)
                    }
                }
            }
        } catch (Exception e) {
            println "Error in interactive mode: ${e.message}"
        } finally {
            scanner.close()
        }
    }
    
    
    /**
     * Prints the application banner to be shown at startup
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
     * Get current timestamp
     */
    static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }


    /** Displays a list of 10 scraping results in the console */
    private static void displayResults(List<String> results) {
        println "\nFound ${results.size()} result(s):"
        println "-" * 50
        
        results.eachWithIndex { result, index ->
            println "${index + 1}: ${result}"
            if (index >= 9 && results.size() > 10) {
                println "... and ${results.size() - 10} more results"
                return
            }
        }
    }

    
    /**
     * Saves the scraping results to a file in the specified format
     *
     * @param results  The list of scraped data items
     * @param filename The name of the file to save the results
     * @param format   The output format: 'text', 'json', or 'csv'
     * @param url      The URL that was scraped (for metadata)
     * @param selector The CSS selector used during scraping (for metadata)
     */
    static void saveResults(List<String> results, String filename, String format, String url, String selector) {
        String content = generateOutput(results, format, url, selector)
        ScraperUtils.saveToFile(content, filename)
        println "Results saved to ${filename}"
    }

    /**
     * Generates the output content from scraping results in the specified format
     * 
     * @param results  The list of scraped data items
     * @param format   The output format requested 
     * @param url      The URL that was scraped 
     * @param selector The CSS selector used during scraping
     * @return A formatted string representing the scraping results
     */
    private static String generateOutput(List<String> results, String format, String url, String selector) {
        switch (format.toLowerCase()) {
            case 'json':
                return generateJsonOutput(results, url, selector)
            case 'csv':
                return generateCsvOutput(results, selector)
            // Text
            default:
                return generateTextOutput(results, url, selector)
        }
    }

    /**
     * Generates JSON formatted output of the scraping results, including metadata about the session
     *
     * @param results  The list of scraped data items
     * @param url The URL that was scraped
     * @param selector The CSS selector used during scraping
     * @return A pretty-printed JSON string
     */
    private static String generateJsonOutput(List<String> results, String url, String selector) {
        def data = [
            scraping_info: [
                url: url,
                selector: selector,
                timestamp: getCurrentTimestamp(),
                count: results.size()
            ],
            results: results
        ]
        return groovy.json.JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(data))
    }

    /**
     * Generates CSV formatted output of the scraping results
     *
     * @param results  The list of scraped data items.
     * @param selector The CSS selector used (used as CSV header).
     * @return A CSV formatted string with index and results.
     */
    private static String generateCsvOutput(List<String> results, String selector) {
        StringBuilder csv = new StringBuilder()
        csv.append("index,${selector}\n")
        results.eachWithIndex { result, index ->
            String escapedResult = result.replace('"', '""')
            csv.append("${index + 1},\"${escapedResult}\"\n")
        }
        return csv.toString()
    }
    
    /**
     * Generates plain text formatted output of the scraping results, including metadata about the session
     */
    private static String generateTextOutput(List<String> results, String url, String selector) {
        StringBuilder text = new StringBuilder()
        text.append("Web Scraping Results\n")
        text.append("===================\n")
        text.append("URL: ${url}\n")
        text.append("Selector: ${selector}\n")
        text.append("Timestamp: ${getCurrentTimestamp()}\n")
        text.append("Count: ${results.size()}\n\n")
        text.append("Results:\n")
        text.append("-" * 30 + "\n")
        
        results.eachWithIndex { result, index ->
            text.append("${index + 1}: ${result}\n")
        }
        return text.toString()
    }


}