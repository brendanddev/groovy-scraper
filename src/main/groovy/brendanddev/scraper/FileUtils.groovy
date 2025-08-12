package brendanddev.scraper

/**
 * Defines a utility class for common file operations in the web scraper application.
 * This class provides methods to ...
 */
class FileUtils {

    static final String OUTPUT_DIR = "scraper_output"
    static final String LOGS_DIR = "scraper_logs"

    /**
     * Ensures that the output directory exists, creating it if necessary.
     */ 
    static void ensureOutputDirectoryExists() {
        File outputDir = new File(OUTPUT_DIR)
        if (!outputDir.exists()) {
            outputDir.mkdirs()
            println TerminalStyles.info("Output directory created: ${OUTPUT_DIR}")
        }
    }

    /**
     * Ensures that the logs directory exists, creating it if necessary.
     */
    static void ensureLogsDirectoryExists() {
        File logsDir = new File(LOGS_DIR)
        if (!logsDir.exists()) {
            logsDir.mkdirs()
            println TerminalStyles.info("Logs directory created: ${LOGS_DIR}")
        }
    }

    






    
}