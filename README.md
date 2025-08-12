# Groovy Web Scraper

A powerful, interactive **terminal-based web scraper application** built with Groovy and JSoup.  
Designed for real-world scraping tasks as well as learning and experimentation with ethical scraping techniques.

---

## Overview

This application offers a **user-friendly command-line interface** to scrape data from websites by specifying URLs and CSS selectors.  
It combines reusable Groovy utilities with practical features such as:

- Interactive terminal menu for custom scraping and pre-built examples  
- Scraping data from demo and real-world websites  
- Fetching and parsing HTML tables and JSON APIs  
- Checking and respecting `robots.txt` policies to ensure ethical scraping  
- Built-in delays to prevent overwhelming target servers  
- Saving scraped results in text, JSON, or CSV formats  
- Clear, color-coded terminal output for easy reading  

Whether you’re a developer wanting to quickly extract data or someone learning how to build scrapers responsibly, this app is ready to use out-of-the-box.

---

## Features

- **Custom Scraping:** Input any URL and CSS selector to scrape live data  
- **Built-in Examples:** Demonstrations of table scraping, JSON API parsing, and robots.txt compliance  
- **Robots.txt Checker:** Verifies site scraping permissions and informs you if scraping is disallowed or robots.txt is missing  
- **Result Saving:** Export scraped data easily to text, JSON, or CSV files  
- **Respectful Scraping:** Implements pacing between requests to avoid hitting servers too hard  
- **Terminal UI:** Intuitive prompts and colorful messages guide you through scraping tasks  

---

## Requirements

- Java 11 or later
- Groovy 3.x
- Internet connection to run scraping tasks

---

## Libraries

- [JSoup](https://jsoup.org/) — for parsing and extracting data from HTML
- Groovy standard libraries — for scripting and CLI utilities

---
