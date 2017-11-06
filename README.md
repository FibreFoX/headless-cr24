Headless-Cr24
=============

Headless testing sometimes can be very frustrating, because you need to download some special native binary for the webdriver-communication, setting JVM-properties and having some browser installed and wired everything together.

To make your lives easier, this library was created, which downloads all required dependencies (native webdriver bridge and browser), sets the required JVM-properties and returns some ready-to-use WebDriver-instance.


Why that strange name?
======================

Inside the periodic table of elements on position "24" there is "Chromium", the symbol is "Cr". I wanted to avoid putting "Chrome" or "Chromium" as being part of this library name, mostly to avoid possible legal consequences.


How to use?
===========

**Step 1: adding headless-cr24 as dependency**

*When using maven*
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <!-- ... other stuff ... -->

    <dependencies>
        <!-- ... other dependencies ... -->
        <dependency>
            <groupId>de.dynamicfiles.projects.testing</groupId>
            <artifactId>headless-cr24</artifactId>
            <version>1.0.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

*When using gradle*
```groovy
apply plugin: 'java'

// ... other stuff ...

dependencies {
    // ... other dependencies ...
    testCompile 'de.dynamicfiles.projects.testing:headless-cr24:1.0.0'
}
```

**Step 2: create your WebDriver instance**

```java
import org.openqa.selenium.WebDriver;
import org.junit.Test;
import de.dynamicfiles.projects.testing.headless.cr24.Cr24DriverBuilder;


public class YourWebbrowserTest {

    @Test
    public void getWebdriverWithDefaultConfiguration() throws IOException, Cr24ConfigurationBuilderException {
        WebDriver webDriver = Cr24DriverBuilder.getWebdriverWithDefaultConfiguration();

        webDriver.get("https://github.com/FibreFoX/headless-cr24");

        // ... your real testing stuff ...
    }
}
```