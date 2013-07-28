package org.whiskeysierra.process;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public final class PathMatchers {

    public static Matcher<Path> isDirectory(final LinkOption... options) {
        return new TypeSafeMatcher<Path>() {
            Path fileTested;

            public boolean matchesSafely(Path item) {
                fileTested = item;
                return Files.isDirectory(item, options);
            }

            public void describeTo(Description description) {
                description.appendText(" that ");
                description.appendValue(fileTested);
                description.appendText("is a directory");
            }
        };
    }

    public static Matcher<Path> exists(final LinkOption... options) {
        return new TypeSafeMatcher<Path>() {
            Path fileTested;

            public boolean matchesSafely(Path item) {
                fileTested = item;
                return Files.exists(item, options);
            }

            public void describeTo(Description description) {
                description.appendText(" that file ");
                description.appendValue(fileTested);
                description.appendText(" exists");
            }
        };
    }

    public static Matcher<Path> isFile(final LinkOption... options) {
        return new TypeSafeMatcher<Path>() {
            Path fileTested;

            public boolean matchesSafely(Path item) {
                fileTested = item;
                return Files.isRegularFile(item, options);
            }

            public void describeTo(Description description) {
                description.appendText(" that ");
                description.appendValue(fileTested);
                description.appendText("is a file");
            }
        };
    }

    public static Matcher<Path> readable() {
        return new TypeSafeMatcher<Path>() {
            Path fileTested;

            public boolean matchesSafely(Path item) {
                fileTested = item;
                return Files.isReadable(item);
            }

            public void describeTo(Description description) {
                description.appendText(" that file ");
                description.appendValue(fileTested);
                description.appendText("is readable");
            }
        };
    }

    public static Matcher<Path> writable() {
        return new TypeSafeMatcher<Path>() {
            Path fileTested;

            public boolean matchesSafely(Path item) {
                fileTested = item;
                return Files.isWritable(item);
            }

            public void describeTo(Description description) {
                description.appendText(" that file ");
                description.appendValue(fileTested);
                description.appendText("is writable");
            }
        };
    }

    public static Matcher<Path> sized(long size) {
        return sized(Matchers.equalTo(size));
    }

    public static Matcher<Path> sized(final Matcher<Long> size) {
        return new TypeSafeMatcher<Path>() {
            Path fileTested;
            long length;

            public boolean matchesSafely(Path item) {
                fileTested = item;
                try {
                    length = Files.size(item);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
                return size.matches(length);
            }

            public void describeTo(Description description) {
                description.appendText(" that file ");
                description.appendValue(fileTested);
                description.appendText(" is sized ");
                description.appendDescriptionOf(size);
                description.appendText(", not " + length);
            }
        };
    }

    public static Matcher<Path> named(final Matcher<String> name) {
        return new TypeSafeMatcher<Path>() {
            Path fileTested;

            public boolean matchesSafely(Path item) {
                fileTested = item;
                return name.matches(item.getFileName().toString());
            }

            public void describeTo(Description description) {
                description.appendText(" that file ");
                description.appendValue(fileTested);
                description.appendText(" is named");
                description.appendDescriptionOf(name);
                description.appendText(" not ");
                description.appendValue(fileTested.getFileName());
            }
        };
    }

    public static Matcher<Path> withCanonicalPath(final Matcher<String> path, final LinkOption... options) {
        return new TypeSafeMatcher<Path>() {
            public boolean matchesSafely(Path item) {
                try {
                    return path.matches(item.toRealPath(options).toString());
                } catch (IOException e) {
                    return false;
                }
            }

            public void describeTo(Description description) {
                description.appendText("with canonical path '");
                description.appendDescriptionOf(path);
                description.appendText("'");
            }
        };
    }

    public static Matcher<Path> withAbsolutePath(final Matcher<String> path) {
        return new TypeSafeMatcher<Path>() {
            Path fileTested;

            public boolean matchesSafely(Path item) {
                fileTested = item;
                return path.matches(item.toAbsolutePath().toString());
            }

            public void describeTo(Description description) {
                description.appendText("with absolute path '");
                description.appendDescriptionOf(path);
                description.appendText("'");
            }
        };
    }
}
