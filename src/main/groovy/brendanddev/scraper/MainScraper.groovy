package brendanddev.scraper

import groovy.cli.picocli.CliBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainScraper {



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
     * Get current timestamp
     */
    static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }


}