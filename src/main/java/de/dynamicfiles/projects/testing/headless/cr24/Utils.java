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
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author Danny Althoff
 */
class Utils {

    public void download(String source, File target, int connectionTimeout, int readTimeout) throws IOException {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectionTimeout).setConnectionRequestTimeout(readTimeout).build();
        CloseableHttpClient client = HttpClientBuilder.create().useSystemProperties().setDefaultRequestConfig(requestConfig).build();

        Files.createDirectories(target.toPath().getParent());
        try(CloseableHttpResponse response = client.execute(new HttpGet(source))){
            HttpEntity entity = response.getEntity();
            if( entity != null ){
                try(FileOutputStream outstream = new FileOutputStream(target)){
                    entity.writeTo(outstream);
                }
            }
        }
    }

    public void unarchive(Path sourceArchive, String rootInsideArchive, Path target) throws IOException {
        Path currentWorkingFolder = sourceArchive;
        Path targetFolder = Files.createDirectories(target);

        URI chromeDriverUri = currentWorkingFolder.toUri();
        try(FileSystem zipFileSystem = FileSystems.newFileSystem(URI.create("jar:" + chromeDriverUri.toString()), new HashMap<>())){
            Path rootEntry = zipFileSystem.getPath(rootInsideArchive);
            copyRecursive(rootEntry, targetFolder, Logger.getAnonymousLogger());
        }
    }

    public void copyRecursive(Path sourceFolder, Path targetFolder, Logger logger) throws IOException {
        Files.walkFileTree(sourceFolder, new FileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path subfolder, BasicFileAttributes attrs) throws IOException {
                // do create subfolder (if needed)
                Files.createDirectories(targetFolder.resolve(sourceFolder.relativize(subfolder).toString()));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path sourceFile, BasicFileAttributes attrs) throws IOException {
                // do copy, and replace, as the resource might already be existing
                Files.copy(sourceFile, targetFolder.resolve(sourceFolder.relativize(sourceFile).toString()), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path source, IOException ioe) throws IOException {
                // don't fail, just inform user
                logger.warning(String.format("Couldn't copy resource %s with reason %s", source.toString(), ioe.getLocalizedMessage()));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path source, IOException ioe) throws IOException {
                // nothing to do here
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
