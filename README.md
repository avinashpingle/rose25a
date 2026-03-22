# rose25a

This repository contains the `rose25a` Java Selenium + Cucumber test automation project. It uses Maven to manage dependencies and build the project. The project demonstrates Web UI automation, Cucumber step definitions, and utility helpers.

## Project metadata
- GroupId: `com.skillio`
- ArtifactId: `rose25a`
- Version: `1.0.0`
- Java version: 21 (configured in `pom.xml`)
- Main frameworks: Selenium (4.40.0), Cucumber (7.x), TestNG (7.10.2), Apache POI (for Excel).

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

## Prerequisites
- Java 21 JDK installed and JAVA_HOME set.
- Maven 3.8+ installed and available on PATH.
- A browser installed (Chrome, Firefox) and optionally the matching WebDriver binaries if not handled automatically.
- Optional: `chromedriver` or `geckodriver` on PATH, or configure the tests to use WebDriverManager (not present by default).

Check Java and Maven versions:

```bash
java -version
mvn -v
```

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

Note: Cucumber/TestNG execution may rely on property files or system properties. If tests open a browser, ensure the appropriate WebDriver binary is available.

## How to run Cucumber tests
If the project uses Cucumber with TestNG, there is usually a test runner class or a `testng.xml` file (check `src/test` or project root). To run tests via Maven:

```bash
mvn test -Dcucumber.options="--tags @smoke"
```

Or simply:

```bash
mvn test
```

If tests require a specific browser, set the browser property on the command line. Example if `PropUtil` or test setup reads `browser` system property:

```bash
mvn test -Dbrowser=chrome
```

## Example: Running a single TestNG suite or Cucumber runner
If there's a `testng.xml` under project root or `src/test/resources`, you can run:

```bash
mvn -Dtestng.suiteXmlFiles=testng.xml test
```

Or run a specific TestNG class:

```bash
mvn -Dtest=com.skillio.tests.LoginTest test
```

(Replace with the actual fully-qualified test class name present in the project.)

## Notes & Troubleshooting
- If you see errors related to Java version mismatch, ensure Maven is configured to use JDK 21. On macOS you can set JAVA_HOME, e.g. `export JAVA_HOME=$(/usr/libexec/java_home -v21)`.
- If WebDriver binary errors occur, either install the appropriate driver and add to PATH or integrate a manager like WebDriverManager.
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
- Add WebDriverManager to the project to avoid manually installing drivers.

Tell me which of those you'd like next.