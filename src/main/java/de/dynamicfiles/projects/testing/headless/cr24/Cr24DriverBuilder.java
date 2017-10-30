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

import java.io.IOException;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Wrapper for generating the ChromeDriver instance. Please call RemoteWebDriver::quit after usage,
 * otherwise chromedriver will be still running.
 *
 * @author Danny Althoff
 */
public class Cr24DriverBuilder {

    public static Cr24ConfigurationBuilder getDefaultConfiguration() {
        return new Cr24ConfigurationBuilder(new Cr24Configuration());
    }

    public static ChromeDriver getWebdriverWithDefaultConfiguration() throws IOException, ConfigurationBuilderException {
        return getDefaultConfiguration().build();
    }
}
