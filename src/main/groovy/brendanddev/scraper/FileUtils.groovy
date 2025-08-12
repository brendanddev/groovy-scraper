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

    /**
     * Saves the given string content to a file at the specified path.
     *
     * @param content The text content to write to the file.
     * @param filePath The path (including filename) where the content should be saved.
     */
    static void saveToFile(String content, String filePath) {
        try {
            new File(filePath).text = content
        } catch (IOException e) {
            println "Error saving content to file: ${e.message}"
        }
    }

    /**
     * Writes the given content to a file in the output directory.
     *
     * @param content The content to write to the file
     * @param fileName The name of the file to create
     * @return true if the file was written successfully, false otherwise
     */
    static boolean writeToFile(String content, String fileName) {
        try {
            ensureOutputDirectoryExists()
            String fullPath = "${OUTPUT_DIR}/${fileName}"
            new File(fullPath).text = content 
            return true
        } catch (IOException e) {
            println TerminalStyles.error("Failed to write to file: ${fileName}. Error: ${e.message}")
            return false
        }
    }

    /**
     * Reads the content of a file from the specified path.
     *
     * @param filePath The path to the file to read
     * @return The content of the file as a String, or null if an error occurs
     */
    static String readFromFile(String filePath) {
        try {
            return new File(filePath).text
        } catch (IOException e) {
            println TerminalStyles.error("Failed to read from file: ${filePath}. Error: ${e.message}")
            return null
        }

    }

}