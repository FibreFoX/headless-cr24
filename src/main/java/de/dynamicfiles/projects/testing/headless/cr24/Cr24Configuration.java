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

import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Contains the real configuration data used for creating the ChromeDriver instance.
 *
 * @author Danny Althoff
 */
public class Cr24Configuration {

    @FunctionalInterface
    public interface StringValueCallback {

        String getValue();

    }

    private boolean offline = false;
    private boolean respectExistingSystemProperty = true;
    private boolean useLocalCache = true;

    private int connectionTimeout = 15;
    private int readTimeout = 30;

    private boolean use64bit = false;

    private StringValueCallback webdriverVersionCallback = () -> "2.33";
    private StringValueCallback webbrowserSnapshotVersionCallback = () -> "512476";

    private StringValueCallback webdriverDownloadUrlFilenameCallback = () -> "chromedriver_win32.zip";
    private StringValueCallback webbrowserDownloadUrlFilenameCallback = () -> "chrome-win32.zip";

    private StringValueCallback webdriverDownloadUrlCallback = () -> "https://chromedriver.storage.googleapis.com/" + webdriverVersionCallback.getValue() + "/" + webdriverDownloadUrlFilenameCallback.getValue();
    private StringValueCallback webbrowserDownloadUrlCallback = () -> "https://commondatastorage.googleapis.com/chromium-browser-snapshots/Win" + (use64bit ? "_x64" : "") + "/" + webbrowserSnapshotVersionCallback.getValue() + "/" + webbrowserDownloadUrlFilenameCallback.getValue();

    private StringValueCallback webdriverDownloadCachePathCallback = () -> System.getProperty("user.home") + "/.testing/webdriver/chromedriver/" + webdriverVersionCallback.getValue() + "/" + webdriverDownloadUrlFilenameCallback.getValue();
    private StringValueCallback webbrowserDownloadCachePathCallback = () -> System.getProperty("user.home") + "/.testing/browser/chromium/" + webbrowserSnapshotVersionCallback.getValue() + "/" + (use64bit ? "64bit" : "32bit") + webbrowserDownloadUrlFilenameCallback.getValue();

    private ChromeOptions chromeOptions = new ChromeOptions();

    public Cr24Configuration() {
        chromeOptions.setHeadless(true);
    }

    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean newValue) {
        this.offline = newValue;
    }

    public boolean is64bit() {
        return use64bit;
    }

    public void set64bit(boolean use64bit) {
        this.use64bit = use64bit;
    }

    public boolean isExistingSystemPropertyToBeRespected() {
        return respectExistingSystemProperty;
    }

    public boolean isUseLocalCache() {
        return useLocalCache;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public StringValueCallback getWebbrowserSnapshotVersionCallback() {
        return webbrowserSnapshotVersionCallback;
    }

    public StringValueCallback getWebdriverVersionCallback() {
        return webdriverVersionCallback;
    }

    public StringValueCallback getWebdriverDownloadUrlCallback() {
        return webdriverDownloadUrlCallback;
    }

    public StringValueCallback getWebbrowserDownloadUrlCallback() {
        return webbrowserDownloadUrlCallback;
    }

    public StringValueCallback getWebdriverDownloadCachePathCallback() {
        return webdriverDownloadCachePathCallback;
    }

    public StringValueCallback getWebbrowserDownloadCachePathCallback() {
        return webbrowserDownloadCachePathCallback;
    }

    public ChromeOptions getChromeOptions() {
        return chromeOptions;
    }

    public Cr24Configuration copy() {
        Cr24Configuration cr24Configuration = new Cr24Configuration();

        cr24Configuration.offline = this.offline;
        cr24Configuration.respectExistingSystemProperty = this.respectExistingSystemProperty;
        cr24Configuration.useLocalCache = this.useLocalCache;

        cr24Configuration.connectionTimeout = this.connectionTimeout;
        cr24Configuration.readTimeout = this.readTimeout;

        cr24Configuration.use64bit = this.use64bit;

        cr24Configuration.webdriverVersionCallback = this.webdriverVersionCallback;
        cr24Configuration.webbrowserSnapshotVersionCallback = this.webbrowserSnapshotVersionCallback;
        cr24Configuration.webdriverDownloadUrlFilenameCallback = this.webdriverDownloadUrlFilenameCallback;
        cr24Configuration.webbrowserDownloadUrlFilenameCallback = this.webbrowserDownloadUrlFilenameCallback;
        cr24Configuration.webdriverDownloadUrlCallback = this.webdriverDownloadUrlCallback;
        cr24Configuration.webbrowserDownloadUrlCallback = this.webbrowserDownloadUrlCallback;
        cr24Configuration.webdriverDownloadCachePathCallback = this.webdriverDownloadCachePathCallback;
        cr24Configuration.webbrowserDownloadCachePathCallback = this.webbrowserDownloadCachePathCallback;

        cr24Configuration.chromeOptions = this.chromeOptions;

        return cr24Configuration;
    }

}
