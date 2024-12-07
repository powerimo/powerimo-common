package org.powerimo.common.utils;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

public class FileUtils {

    /**
     * Get version from package information ("title": "version")
     * @return package version
     */
    public String getVersionString() {
        return getVersionString(this.getClass());
    }

    /**
     * Get version from package information ("title": "version")
     * @param class1 class belonging package
     * @return package version
     */
    public static String getVersionString(Class class1) {
        final Package pack = Objects.requireNonNull(class1.getPackage());
        final String b = pack.getImplementationTitle();
        final String version = pack.getImplementationVersion();
        return b + ": " + version;
    }

    /**
     * Get version from package information
     * @return package version
     */
    public String getVersion() {
        return getVersion(getClass());
    }

    /**
     * Get version from package information
     * @param class1 class belonging package
     * @return package version
     */
    public static String getVersion(Class class1) {
        return class1.getPackage().getImplementationVersion();
    }

    /**
     * Get the file extension
     * @param fileName the file name or file path
     * @return the extension of file
     */
    public static Optional<String> getFileNameExtension(String fileName) {
        return Optional.ofNullable(fileName)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(fileName.lastIndexOf(".") + 1));
    }

    public static Optional<String> getFileNameFromPath(String path) {
        if (path == null)
            return Optional.empty();
        var delimiterPosition = path.lastIndexOf(File.pathSeparator);
        var fn = path.substring(delimiterPosition + 1);
        return Optional.of(fn);
    }


}
