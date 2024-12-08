package org.powerimo.common.utils;

import org.junit.jupiter.api.Test;
import org.powerimo.common.utils.FileUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FileUtilsTest {

    @Test
    void getFileNameExtension_simple() {
        String fn = "sample.txt";

        assertEquals("txt", FileUtils.getFileNameExtension(fn).orElse(""));
    }

    @Test
    void getFileNameExtension_2() {
        String fn = "sample.non-ext.abc";

        assertEquals("abc", FileUtils.getFileNameExtension(fn).orElse(""));
    }

    @Test
    void getFileNameExtension_3() {
        String fn = "dir//.hidden//sample.non-ext.file";

        assertEquals("file", FileUtils.getFileNameExtension(fn).orElse(""));
    }

    @Test
    void test_getFileNameFromPath_1() {
        String path = "C:\\home\\user\\test.txt";
        assertEquals("test.txt", FileUtils.getFileNameFromPath(path).orElse(""));
    }

    @Test
    void test_getFileNameFromPath_2() {
        String path = "/home/user/test.txt";
        assertEquals("test.txt", FileUtils.getFileNameFromPath(path).orElse(""));
    }

    @Test
    void test_getFileNameFromPath_3() {
        assertNull(FileUtils.getFileNameFromPath(null).orElse(null));
    }
}