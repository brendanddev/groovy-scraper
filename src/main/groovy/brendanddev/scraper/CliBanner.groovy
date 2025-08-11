package brendanddev.scraper

/**
 * Defines a utility class for displaying the application's startup banner with a rainbow gradient.
 */
class CliBanner {

    /**
     * Prints the rainbow banner
     */
    static void printBanner() {
        String banner = """
            ╔══════════════════════════════════════════════════════════════╗
            ║                           Web Scraper                        ║
            ╠══════════════════════════════════════════════════════════════╣                    
            ║                    by brendanddev                            ║
            ╚══════════════════════════════════════════════════════════════╝
        """.stripIndent()

        println(banner)
        println()
        println(TerminalStyles.info("Welcome to the Terminal Web Scraper!"))
        println(TerminalStyles.dim("This tool allows you to scrape data from any website using CSS selectors."))
        println(TerminalStyles.CYAN + "--------------------------------------------------" + TerminalStyles.RESET)
    }
}
