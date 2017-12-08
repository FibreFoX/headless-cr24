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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Create your own configuration for creating the ChromeDriver instance. For special configurations, you should use the "customize"-method.
 *
 * @author Danny Althoff
 */
public class Cr24ConfigurationBuilder {

    private Cr24Configuration configuration;

    private Utils utils = new Utils();

    public Cr24ConfigurationBuilder(Cr24Configuration cr24Configuration) {
        configuration = cr24Configuration;
    }

    public ChromeDriver build() throws IOException, Cr24ConfigurationBuilderException {
        File localWebdriverArchiveFileToUse = new File(configuration.getWebdriverDownloadCachePathCallback().getValue());
        File localWebbrowserArchiveFileToUse = new File(configuration.getWebbrowserDownloadCachePathCallback().getValue());

        boolean hasLocalVersionWebdriverArchive = false;
        boolean hasLocalVersionWebbrowserArchive = false;
        // check if binaries are already existing
        if( configuration.isUseLocalCache() ){
            hasLocalVersionWebdriverArchive = localWebdriverArchiveFileToUse.exists();
            hasLocalVersionWebbrowserArchive = localWebbrowserArchiveFileToUse.exists();
        }

        // download webdriver if needed
        if( !hasLocalVersionWebdriverArchive ){
            if( !configuration.isOffline() ){
                try{
                    utils.download(configuration.getWebdriverDownloadUrlCallback().getValue(), localWebdriverArchiveFileToUse, configuration.getConnectionTimeout(), configuration.getReadTimeout());
                } catch(MalformedURLException ex){
                    throw new IOException("Problems while downloading webdriver", ex);
                }
            }
        }
        // download webdriver if needed
        if( !configuration.isUseLocalBrowserInstallation() && !hasLocalVersionWebbrowserArchive ){
            if( !configuration.isOffline() ){
                try{
                    utils.download(configuration.getWebbrowserDownloadUrlCallback().getValue(), localWebbrowserArchiveFileToUse, configuration.getConnectionTimeout(), configuration.getReadTimeout());
                } catch(MalformedURLException ex){
                    throw new IOException("Problems while downloading browser", ex);
                }
            }
        }

        File webdriverExecutable = null;
        File webbrowserExecutable = null;

        // extract downloaded archives into temporary folder for execution
        if( localWebdriverArchiveFileToUse.exists() ){
            // TODO handle deletion for later
            Path targetTempPath = Files.createTempDirectory("webdriver-");
            utils.unarchive(localWebdriverArchiveFileToUse.toPath(), configuration.getWebdriverArchiveFoldernameOfExecutable().getValue(), targetTempPath);
            webdriverExecutable = targetTempPath.resolve(configuration.getWebdriverExecutableFilename().getValue()).toAbsolutePath().toFile();
        }
        if( !configuration.isUseLocalBrowserInstallation() && localWebbrowserArchiveFileToUse.exists() ){
            // TODO handle deletion for later
            Path targetTempPath = Files.createTempDirectory("webbrowser-");
            utils.unarchive(localWebbrowserArchiveFileToUse.toPath(), configuration.getWebbrowserArchiveFoldernameOfExecutable().getValue(), targetTempPath);
            webbrowserExecutable = targetTempPath.resolve(configuration.getWebbrowserExecutableFilename().getValue()).toAbsolutePath().toFile();
        }

        // fail if any attempt failed
        if( configuration.isUseLocalBrowserInstallation() ){
            if( webdriverExecutable == null ){
                throw new Cr24ConfigurationBuilderException("Webdriver executable does not exist");
            }
        } else {
            if( webdriverExecutable == null || webbrowserExecutable == null ){
                throw new Cr24ConfigurationBuilderException("Not all executables are existing");
            }
        }

        if( configuration.isExistingSystemPropertyToBeRespected() ){
            if( System.getProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, null) == null ){
                System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, webdriverExecutable.getAbsolutePath());
                configuration.getWebdriverExecutableCallback().workOnExecutable(webdriverExecutable.toPath());
            }
        } else {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, webdriverExecutable.getAbsolutePath());
        }

        ChromeOptions options = configuration.getChromeOptions();

        // when having to use locally installed browser, do not set exe-property, as chromedriver has its own detection routine
        if( !configuration.isUseLocalBrowserInstallation() ){
            options.setBinary(webbrowserExecutable.getAbsolutePath());
            configuration.getWebbrowserExecutableCallback().workOnExecutable(webbrowserExecutable.toPath());
        }

        ChromeDriverService createDefaultService = ChromeDriverService.createDefaultService();
        return new ChromeDriver(createDefaultService, options);
    }

    public Cr24ConfigurationBuilder customize(Consumer<Cr24Configuration> configCallback) {
        Cr24ConfigurationBuilder newBuilder = new Cr24ConfigurationBuilder(configuration.copy());
        configCallback.accept(newBuilder.configuration);
        return newBuilder;
    }

    public Cr24ConfigurationBuilder offlineOnly() {
        Cr24ConfigurationBuilder newBuilder = new Cr24ConfigurationBuilder(configuration.copy());
        newBuilder.configuration.setOffline(true);
        return newBuilder;
    }

    public Cr24ConfigurationBuilder online() {
        Cr24ConfigurationBuilder newBuilder = new Cr24ConfigurationBuilder(configuration.copy());
        newBuilder.configuration.setOffline(false);
        return newBuilder;
    }

    public Cr24ConfigurationBuilder use32bit() {
        Cr24ConfigurationBuilder newBuilder = new Cr24ConfigurationBuilder(configuration.copy());
        newBuilder.configuration.set64bit(false);
        return newBuilder;
    }

    public Cr24ConfigurationBuilder use64bit() {
        Cr24ConfigurationBuilder newBuilder = new Cr24ConfigurationBuilder(configuration.copy());
        newBuilder.configuration.set64bit(true);
        return newBuilder;
    }

}
