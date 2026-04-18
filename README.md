# rose25a

This repository contains the `rose25a` Java Selenium + Cucumber test automation project. It uses Maven to manage dependencies and build the project. The project demonstrates Web UI automation, Cucumber step definitions, and utility helpers.

## Project metadata
- GroupId: `com.skillio`
- ArtifactId: `rose25a`
- Version: `1.0.0`
- Java version: 21 (configured in `pom.xml`)
- Main frameworks: Selenium (4.40.0), Cucumber (7.x), TestNG (7.10.2), Apache POI (for Excel). Note: WebDriverManager is now integrated to simplify driver management in most local runs (see `app.properties`).

## Package structure
Root source: `src/main/java`

Top-level package: `com.skillio`

- `com.skillio.base`
  - `Hooks.java` - test lifecycle hooks (before/after). 
  - `Keyword.java` - keyword-style wrapper/helper for WebDriver actions.

- `com.skillio.exceptions`
  - `InvalidBrowserNameException.java` - custom runtime exception for invalid browser names.

- `com.skillio.pages`
  - `LoginPage.java` - page object model for the application's login page.
  - `HomePage.java` - page object for the application's home page.

- `com.skillio.stepdefinitions`
  - `LoginSteps.java` - Cucumber step definitions for login flows.
  - `Parameterization.java` - Cucumber step definitions demonstrating parameterization and DataTable usage.

- `com.skillio.utils`
  - `App.java` - application-level utilities / entry helpers.
  - `PropUtil.java` - property file reader utility.
  - `ExcelUtil.java` - helper for reading Excel files using Apache POI.
  - `Locator.java` - centralized locators or locator helpers.
  - `WaitFor.java` - explicit wait helpers.

## Project layout (important files)
- `pom.xml` - Maven build file and dependency list.
- `src/main/java` - Java source for page objects, steps, and utilities.
- `src/test/resources` - (if present) Cucumber feature files and test resources.
- `test-output/` - generated TestNG/Cucumber HTML reports (after running tests).

## Recent framework changes (summary)
This project has been updated to support more flexible execution environments, improved driver management, safer cleanup, and better reporting. Key changes:

- Thread-safe WebDriver handling
  - All WebDriver instances are stored in a ThreadLocal so tests can run in parallel safely.
  - `Keyword` and `Hooks` were updated to create and quit drivers safely and to always clear the ThreadLocal on shutdown.

- Selenium Grid / Remote execution
  - Tests can run on a remote Selenium Grid (or compatible hub such as Selenoid) using `grid.url` and the `isOnGrid` toggle in `app.properties`.
  - `App.getGridUrl()` and `App.isOnGrid()` expose these values and are used by `Keyword.openBrowser(...)` to create a `RemoteWebDriver` when needed.
  - The remote creation code builds appropriate Options objects (e.g., `ChromeOptions`, `FirefoxOptions`) and forwards them to the hub.

- WebDriverManager / local driver management
  - WebDriverManager is available to automatically resolve local browser driver binaries in typical local runs. This reduces the need to manually install or configure `chromedriver`/`geckodriver` on CI or developer machines.

- Improved logging and diagnostics
  - More informative logs are produced during session creation and teardown to make failures (SessionNotCreatedException etc.) easier to diagnose.

- Allure reporting support
  - Test results are collected so they can be displayed with Allure. Test artifacts are available in `allure-results/` (if tests are configured to write there).

- Properties and configuration
  - `src/main/resources/app.properties` has new and clarified keys (examples below). The `PropUtil` helper reads these safely.

## Important configuration (app.properties)
Example keys you may find in `src/main/resources/app.properties`:

```ini
browser_name=chrome          # local browser: chrome, firefox, etc.
headless=false               # run headless when true
isOnGrid=false               # enable remote execution
grid.url=http://<hub-host>:4444   # Selenium Grid hub endpoint (no /ui)
parallel.threads=4           # optional: number of parallel threads when using TestNG
allure.enabled=true          # optional: enable writing Allure results
```

Notes:
- Do NOT use the Grid UI path such as `/ui` when setting `grid.url`. Use the hub endpoint only. Examples:
  - Correct: `http://192.168.0.116:4444`
  - Sometimes valid for older Grid: `http://192.168.0.116:4444/wd/hub`
  - Incorrect (do not use): `http://192.168.0.116:4444/ui`

## How to build
From the project root (`rose25a/`):

```bash
mvn -DskipTests package
```

This will compile the code and package the project without running tests.

To run the full build including tests:

```bash
mvn test
```

You can pass common properties on the mvn command line to override `app.properties` values at runtime. For example:

```bash
mvn test -Dbrowser_name=chrome -DisOnGrid=false
```

## How to run Cucumber/TestNG tests
- Run all tests with Maven:

```bash
mvn test
```

- Run tests with a specific Cucumber tag (if using Cucumber runner):

```bash
mvn test -Dcucumber.options="--tags @smoke"
```

- Run tests on the Grid (example):

```bash
mvn test -Dbrowser_name=chrome -DisOnGrid=true -Dgrid.url=http://192.168.0.116:4444
```

- Run a specific TestNG suite file (if you have `testng.xml`):

```bash
mvn -Dtestng.suiteXmlFiles=testng.xml test
```

- Run a single test class:

```bash
mvn -Dtest=com.skillio.tests.LoginTest test
```

## Allure reporting (quick)
If Allure is available on your machine (CLI) or in CI:

- After a test run, artifacts are stored in `allure-results/` or `target/allure-results` depending on your configuration.

- Serve a quick local report (requires `allure` command installed):

```bash
# run tests then serve results from local directory
mvn test
allure serve allure-results
```

Or generate the static report:

```bash
mvn test
allure generate allure-results -o allure-report --clean
allure open allure-report
```

If Allure is not needed in your environment, you can disable it via property flags or by removing the reporter configuration from the surefire/failsafe runner.

## Quick runtime checks for Grid connectivity
- Curl the hub status endpoint (replace host/port):

```bash
curl -sS http://192.168.0.116:4444/status
```

You should get a JSON payload indicating hub/node status.

- Open the hub UI in a browser: `http://<hub-host>:4444` and verify nodes are registered and list the browsers and versions available.

## Common causes of SessionNotCreatedException
- Wrong `grid.url` (e.g., using `/ui` path) or hub not reachable.
- No matching node available for the requested browser/version.
- Browser driver (chromedriver/geckodriver) mismatch on the node (incompatible versions).
- Selenium versions mismatch between client and hub/node in some setups.

If you get a SessionNotCreatedException, collect these and re-run:
- The full Java stack trace (paste it into the issue).
- Hub logs and node logs (they usually show the reason).
- The value of `grid.url` and `browser_name` used.

## Why you may have seen "No driver instance found for this thread to quit."
- That message indicates a cleanup call tried to quit a driver that was never created for the current thread. This sometimes happens when session creation failed earlier. The updated `Keyword.quitBrowser()` now behaves safely and logs clearer diagnostics. If you still encounter it, share the session creation stack trace and the logs.

## Troubleshooting steps (recommended order)
1. Confirm `grid.url` is correct and reachable:
   - `curl http://<hub-host>:4444/status`
2. Check the hub UI and node registrations in `http://<hub-host>:4444`.
3. Verify requested browser/version is available on a node.
4. Check node logs for underlying browser startup or driver errors.
5. Run locally (`isOnGrid=false`) to confirm tests and locators work without Grid.
6. If session creation still fails, paste the full SessionNotCreatedException stack trace and hub logs and I can help analyze them.

## Notes & Troubleshooting
- If you see errors related to Java version mismatch, ensure Maven is configured to use JDK 21. On macOS you can set JAVA_HOME, e.g. `export JAVA_HOME=$(/usr/libexec/java_home -v21)`.
- If WebDriver binary errors occur, either install the appropriate driver and add to PATH or use the built-in WebDriverManager support.
- If feature files are not found, ensure they are placed under `src/test/resources` and the Cucumber runner points to them.

## Quick references
- Source: `src/main/java/com/skillio`
- Compiled classes: `target/classes`
- Test reports: `target/surefire-reports` and `test-output/`

## License
This repository does not contain a license file. Add one if you intend to open-source this project.

---

If you'd like, I can:
- Add a minimal `test` runner or `testng.xml` if missing.
- Add a `scripts/` helper to run commonly used commands.
- Add or tune WebDriverManager usage in the project to avoid manually installing drivers.

Tell me which of those you'd like next.