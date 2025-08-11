package brendanddev.scraper

/**
 * Defines a utility class for displaying the application's startup banner with a rainbow gradient.
 */
class CliBanner {

    /**
     * Prints the rainbow banner
     */
    static void printBanner() {
        int contentWidth = 62  // width inside the borders

        println TerminalStyles.CYAN + "╔" + "═".repeat(contentWidth) + "╗" + TerminalStyles.RESET
        println TerminalStyles.CYAN + "║" + TerminalStyles.BOLD + TerminalStyles.center("Web Scraper", contentWidth) + TerminalStyles.RESET + TerminalStyles.CYAN + "║" + TerminalStyles.RESET
        println TerminalStyles.CYAN + "╠" + "═".repeat(contentWidth) + "╣" + TerminalStyles.RESET
        println TerminalStyles.CYAN + "║" + TerminalStyles.center("by brendanddev", contentWidth) + "║" + TerminalStyles.RESET
        println TerminalStyles.CYAN + "╚" + "═".repeat(contentWidth) + "╝" + TerminalStyles.RESET
        println()
        println TerminalStyles.info("Welcome to the Terminal Web Scraper!")
        println TerminalStyles.error("TEST ERROR MESSAGE")
        println TerminalStyles.success("TEST SUCCESS MESSAGE")
        println TerminalStyles.warn("TEST WARNING MESSAGE")
        println TerminalStyles.dim("This tool allows you to scrape data from any website using CSS selectors.")
        println TerminalStyles.WHITE + "--------------------------------------------------" + TerminalStyles.RESET
    }

}
