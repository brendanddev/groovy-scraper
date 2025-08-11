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
     * Controls the main flow of the cli based scraper application.
     * It displays the menu to the user with options to choose from, and uses a scanner to read user input and
     * perform actions based on the selected option.
     */
    static void runScraperMenu() {
        Scanner scanner = new Scanner(System.in)
        printBanner()
        println "\nWelcome to the Terminal Web Scraper!"
        println "This tool allows you to scrape data from any website using CSS selectors."

        while (true) {
            println "-" * 50
            println "1) Custom Scraping"
            println "2) Run Built-in Examples"
            println "3) Help & Usage"
            println "4) Exit"
            print "\nChoose an option (1-4): "

            String choice = scanner.nextLine().trim()

            switch (choice) {
                case '1':
                    handleCustomScraping(scanner)
                    break
                case '2':
                    ScraperExamples.runAllExamples(true)
                    break
                case '3':
                    handleHelpMenu()
                    break
                case '4':
                    println "Exiting. Goodbye!"
                    scanner.close()
                    return
                default:
                    println "Invalid choice. Please select a valid option."
                    break
            }
        }
    }

    /**
     * Handles the custom scraping flow where the user provides a URL and CSS selector.
     *
     * @param scanner The Scanner instance for reading user input
     */
    static void handleCustomScraping(Scanner scanner) {
        try {
            // Get url from user
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

            // Get selector from user
            println "\nCommon selectors:"
            println "  h1, h2, h3        - Headers"
            println "  .class-name       - Elements with a class"
            println "  #element-id       - Element with specific ID"
            println "  a                 - All links"
            println "  p                 - All paragraphs"

            print "\nEnter a CSS selector (e.g., h1, .class, #id): "
            String selector = scanner.nextLine().trim()

            if (selector.isEmpty()) {
                println "No selector provided. Returning to menu."
                return
            }

            // Prompt for number of results
            print "\nLimit results (press Enter for no limit): "
            String limitStr = scanner.nextLine().trim()
            Integer limit = limitStr.isEmpty() ? null : limitStr.toInteger()

            println "\nScraping '$selector' from $url ..."
            List<String> results = ScraperUtils.scrapeElementsToList(url, selector, limit)

            if (results.isEmpty()) {
                println "No results found for selector '$selector'"
            } else {
                displayResults(results)

                // Ask to save results
                print "\nSave results to file? (y/n): "
                String saveResponse = scanner.nextLine().trim().toLowerCase()

                // Ask for filename and format
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
            println "Error during custom scraping: ${e.message}"
        }
    }

    /**
     * Displays help and usage information
     */
    static void handleHelpMenu() {
        println """
            Help & Usage
            ============

            This tool lets you scrape websites by providing URLs and CSS selectors.

            Menu Options:
            1) Custom Scraping - Enter a URL and selector to scrape specific data.
            2) Run Built-in Examples - Run pre-defined scraping examples on sample sites.
            3) Help & Usage - Show this help information.
            4) Exit - Quit the application.

            URL Format:
            - Must start with http:// or https://

            CSS Selectors Examples:
            - h1, h2, h3: Headers
            - .class-name: Elements with a CSS class
            - #element-id: Element with specific ID
            - a: All links
            - p: All paragraphs

            You can save scraping results to a file in text, JSON, or CSV format.
        """.stripIndent()
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