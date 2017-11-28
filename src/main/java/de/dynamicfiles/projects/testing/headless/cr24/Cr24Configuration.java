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
public final class Cr24Configuration {

    @FunctionalInterface
    public interface StringValueCallback {

        String getValue();

    }

    private boolean offline = false;
    private boolean respectExistingSystemProperty = true;
    private boolean useLocalCache = true;

    private int connectionTimeout = 15 * 1000;
    private int readTimeout = 30 * 1000;

    private boolean use64bit = true;

    private StringValueCallback os = () -> "win";

    private StringValueCallback webdriverVersionCallback = () -> "2.33";
    private StringValueCallback webbrowserSnapshotVersionCallback = () -> "515679";

    private StringValueCallback webdriverDownloadUrlFilenameCallback = () -> "chromedriver_win32.zip";
    private StringValueCallback webbrowserDownloadUrlFilenameCallback = () -> "chrome-win32.zip";

    private StringValueCallback webdriverDownloadUrlCallback = () -> "https://chromedriver.storage.googleapis.com/" + webdriverVersionCallback.getValue() + "/" + webdriverDownloadUrlFilenameCallback.getValue();
    private StringValueCallback webbrowserDownloadUrlCallback = () -> "https://commondatastorage.googleapis.com/chromium-browser-snapshots/Win" + (use64bit ? "_x64" : "") + "/" + webbrowserSnapshotVersionCallback.getValue() + "/" + webbrowserDownloadUrlFilenameCallback.getValue();

    private StringValueCallback webdriverDownloadCachePathCallback = () -> System.getProperty("user.home") + "/.testing/webdriver/chromedriver/" + os.getValue() + "/" + webdriverVersionCallback.getValue() + "/" + (use64bit ? "64bit" : "32bit") + "/" + webdriverDownloadUrlFilenameCallback.getValue();
    private StringValueCallback webbrowserDownloadCachePathCallback = () -> System.getProperty("user.home") + "/.testing/browser/chromium/" + os.getValue() + "/" + webbrowserSnapshotVersionCallback.getValue() + "/" + (use64bit ? "64bit" : "32bit") + "/" + webbrowserDownloadUrlFilenameCallback.getValue();

    private ChromeOptions chromeOptions = new ChromeOptions();

    public Cr24Configuration() {
        chromeOptions.setHeadless(true);
        if( System.getProperty("os.name").toLowerCase().startsWith("linux") ){
            useBinariesForLinux();
        }
        if( System.getProperty("os.name").toLowerCase().contains("os x") ){
            useBinariesForMacOS();
        }
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

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void useBinariesForWindows() {
        os = () -> "win";
        if( use64bit ){
            webbrowserSnapshotVersionCallback = () -> "515679";
        } else {
            webbrowserSnapshotVersionCallback = () -> "515682";
        }
        webdriverDownloadUrlFilenameCallback = () -> "chromedriver_win32.zip";
        webbrowserDownloadUrlFilenameCallback = () -> "chrome-win32.zip";
        webbrowserDownloadUrlCallback = () -> "https://commondatastorage.googleapis.com/chromium-browser-snapshots/Win" + (use64bit ? "_x64" : "") + "/" + webbrowserSnapshotVersionCallback.getValue() + "/" + webbrowserDownloadUrlFilenameCallback.getValue();
    }

    public void useBinariesForLinux() {
        os = () -> "linux";
        webdriverDownloadUrlFilenameCallback = () -> (use64bit ? "chromedriver_linux64.zip" : "chromedriver_linux32.zip");
        if( use64bit ){
            // 32bit versions seems to not getting updated anymore, this is the last existing
            webbrowserSnapshotVersionCallback = () -> "515696";
        } else {
            webbrowserSnapshotVersionCallback = () -> "382086";
        }
        webbrowserDownloadUrlFilenameCallback = () -> "chrome-linux.zip";
        webbrowserDownloadUrlCallback = () -> "https://commondatastorage.googleapis.com/chromium-browser-snapshots/Linux" + (use64bit ? "_x64" : "") + "/" + webbrowserSnapshotVersionCallback.getValue() + "/" + webbrowserDownloadUrlFilenameCallback.getValue();
    }

    public void useBinariesForMacOS() {
        os = () -> "mac";
        webdriverDownloadUrlFilenameCallback = () -> "chromedriver_mac64.zip";
        webbrowserSnapshotVersionCallback = () -> "515682";
        webbrowserDownloadUrlFilenameCallback = () -> "chrome-mac.zip";
        webbrowserDownloadUrlCallback = () -> "https://commondatastorage.googleapis.com/chromium-browser-snapshots/Mac" + "/" + webbrowserSnapshotVersionCallback.getValue() + "/" + webbrowserDownloadUrlFilenameCallback.getValue();
    }

    public void setWebdriverVersionCallback(StringValueCallback webdriverVersionCallback) {
        this.webdriverVersionCallback = webdriverVersionCallback;
    }

    public void setWebbrowserSnapshotVersionCallback(StringValueCallback webbrowserSnapshotVersionCallback) {
        this.webbrowserSnapshotVersionCallback = webbrowserSnapshotVersionCallback;
    }

    public void setWebdriverDownloadUrlFilenameCallback(StringValueCallback webdriverDownloadUrlFilenameCallback) {
        this.webdriverDownloadUrlFilenameCallback = webdriverDownloadUrlFilenameCallback;
    }

    public void setWebbrowserDownloadUrlFilenameCallback(StringValueCallback webbrowserDownloadUrlFilenameCallback) {
        this.webbrowserDownloadUrlFilenameCallback = webbrowserDownloadUrlFilenameCallback;
    }

    public void setWebdriverDownloadUrlCallback(StringValueCallback webdriverDownloadUrlCallback) {
        this.webdriverDownloadUrlCallback = webdriverDownloadUrlCallback;
    }

    public void setWebbrowserDownloadUrlCallback(StringValueCallback webbrowserDownloadUrlCallback) {
        this.webbrowserDownloadUrlCallback = webbrowserDownloadUrlCallback;
    }

    public void setWebdriverDownloadCachePathCallback(StringValueCallback webdriverDownloadCachePathCallback) {
        this.webdriverDownloadCachePathCallback = webdriverDownloadCachePathCallback;
    }

    public void setWebbrowserDownloadCachePathCallback(StringValueCallback webbrowserDownloadCachePathCallback) {
        this.webbrowserDownloadCachePathCallback = webbrowserDownloadCachePathCallback;
    }

    public void setChromeOptions(ChromeOptions chromeOptions) {
        this.chromeOptions = chromeOptions;
    }

    public String getOS() {
        return os.getValue();
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
