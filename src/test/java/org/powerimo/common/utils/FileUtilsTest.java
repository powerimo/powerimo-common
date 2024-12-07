package org.powerimo.common.utils;

import org.junit.jupiter.api.Test;
import org.powerimo.common.utils.FileUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

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



}