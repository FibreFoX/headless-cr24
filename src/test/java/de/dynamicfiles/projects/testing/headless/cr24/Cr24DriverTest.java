/*
 * Copyright 2017 Danny Althoff
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.dynamicfiles.projects.testing.headless.cr24;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Please go away, this is my playground for stuff, until I found the time to fully test my stuff. Sorry.
 *
 * @author Danny Althoff
 */
public class Cr24DriverTest {

//    @Test
    public void getWebdriverWithDefaultConfiguration() throws IOException, Cr24ConfigurationBuilderException {
        ChromeDriver chromeDriver = Cr24DriverBuilder.getWebdriverWithDefaultConfiguration();
        chromeDriver.get("https://www.dynamicfiles.de");
        Files.copy(chromeDriver.getScreenshotAs(OutputType.FILE), new File("test.png"));

        chromeDriver.quit();
    }

    public void example() throws IOException, Cr24ConfigurationBuilderException {
        Cr24DriverBuilder.getDefaultConfiguration().customize(config -> {
            config.set64bit(true);
        }).build();
    }

}
