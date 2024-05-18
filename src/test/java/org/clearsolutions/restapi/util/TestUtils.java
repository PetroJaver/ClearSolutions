package org.clearsolutions.restapi.util;

import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static java.lang.String.format;

public class TestUtils {
    public static String getFileData(String path) {
        ClassLoader classLoader = TestUtils.class.getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());

        try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(format("Cannot read file with path=%s", path), e);
        }
    }
}
