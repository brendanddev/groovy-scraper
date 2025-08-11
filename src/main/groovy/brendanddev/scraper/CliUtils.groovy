package brendanddev.scraper

import groovy.cli.picocli.CliBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 *
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
            println "\n[Simulated] Checking robots.txt for $baseUrl ..."

            // Ask for a CSS selector
            print "\nEnter a CSS selector (e.g., h1, .class, #id): "
            String selector = scanner.nextLine().trim()

            // Run scraping
            println "\n[Simulated] Scraping '$selector' from $url ..."
            println "Done!"
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








}