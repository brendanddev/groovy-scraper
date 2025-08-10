package brendanddev.scraper

import groovy.cli.picocli.CliBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CliUtils {



    static def createCliParser() {

        // Create the cli parser
        def cli = new CliBuilder(usage: 'groovy MainScraper.groovy [options]')

        // Define command line options
        cli.h(longOpt: 'help', 'Show this help message')
        cli.a(longOpt: 'all', 'Run all scraping examples (default)')
        cli.t(longOpt: 'tables', 'Scrape table data only')
        cli.j(longOpt: 'json', 'Scrape JSON data only')
        cli.f(longOpt: 'forms', 'Scrape form data only')
        cli.r(longOpt: 'robots', 'Check robots.txt only')
        cli.o(longOpt: 'output', args: 1, argName: 'file', 'Output file for results')
        cli.v(longOpt: 'verbose', 'Verbose output')

        return cli
    }

    
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