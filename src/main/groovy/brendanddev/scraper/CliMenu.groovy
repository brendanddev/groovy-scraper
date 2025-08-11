package brendanddev.scraper

import java.util.Scanner

/**
 * Defines a utility class for command line interface operations in the web scraper application.
 * 
 * This class provides methods to display the cli help menu, handle custom scraping from user input, and control
 * the main flow of the cli-based scraper application.
 */
class CliMenu {
        
    /**
     * Controls the main flow of the cli based scraper application.
     * It displays the menu to the user with options to choose from, and uses a scanner to read user input and
     * perform actions based on the selected option.
     */
    static void runScraperMenu() {
        Scanner scanner = new Scanner(System.in)
        CliBanner.printBanner()

        while (true) {
            println TerminalStyles.BOLD + "Menu Options:" + TerminalStyles.RESET
            println TerminalStyles.GREEN + "1) Custom Scraping" + TerminalStyles.RESET
            println TerminalStyles.GREEN + "2) Run Built-in Examples" + TerminalStyles.RESET
            println TerminalStyles.GREEN + "3) Help & Usage" + TerminalStyles.RESET
            println TerminalStyles.GREEN + "4) Exit" + TerminalStyles.RESET
            println TerminalStyles.BOLD + "\nChoose an option (1-4): " + TerminalStyles.RESET

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
                    println TerminalStyles.error("Invalid choice. Please select a valid option.")
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
            println TerminalStyles.info("Enter the website URL to scrape: ")
            String url = scanner.nextLine().trim()

            if (!UrlUtils.isValidUrl(url)) {
                println TerminalStyles.error("Invalid URL format. Please include http:// or https://")
                return
            }

            // Check robots.txt
            String baseUrl = UrlUtils.extractBaseUrl(url)
            println TerminalStyles.info("Checking robots.txt for $baseUrl ...")
            ScraperUtils.checkRobotsTxt(baseUrl)

            // Get CSS selector from user
            print TerminalStyles.info("Enter a CSS selector (e.g., h1, .class, #id): ")
            String selector = scanner.nextLine().trim()

            if (selector.isEmpty()) {
                println TerminalStyles.error("No selector provided. Returning to menu.")
                return
            }

            // Prompt for number of results
            print TerminalStyles.info("Limit results (press Enter for no limit): ")
            String limitStr = scanner.nextLine().trim()
            Integer limit = limitStr.isEmpty() ? null : limitStr.toInteger()

            println TerminalStyles.info("Scraping '$selector' from $url ...")
            List<String> results = ScraperUtils.scrapeElementsToList(url, selector, limit)

            if (results.isEmpty()) {
                println TerminalStyles.error("No results found for selector '$selector'")
            } else {
                OutputFormatter.displayResults(results)

                // Ask to save results
                print TerminalStyles.info("Save results to file? (y/n): ")
                String saveResponse = scanner.nextLine().trim().toLowerCase()

                // Ask for filename and format
                if (saveResponse.startsWith('y')) {
                    println "Enter filename: "
                    String filename = scanner.nextLine().trim()

                    if (!filename.isEmpty()) {
                        print "Choose format (text/json/csv) [text]: "
                        String format = scanner.nextLine().trim()
                        format = format.isEmpty() ? 'text' : format
                        OutputFormatter.saveResults(results, filename, format, url, selector)
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


}