package brendanddev.scraper

/**
 * Defines a utility class for displaying the applications startup banner.
 */
class CliBanner {

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

}