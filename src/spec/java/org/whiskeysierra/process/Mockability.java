package org.whiskeysierra.process;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class Mockability {

    private static final class ExampleService {

        private final ProcessService service;

        public ExampleService(ProcessService service) {
            this.service = service;
        }

        public String run() throws IOException {
            return service.read(Paths.get("path/to/your/executable"));
        }

    }

    @Test
    public void test() throws IOException {
        final ProcessService service = mock(ProcessService.class);

        final String expected = "Hello World";

        when(service.read(any(Path.class))).thenReturn(expected);

        final ExampleService unit = new ExampleService(service);
        final String actual = unit.run();

        assertThat(actual, equalTo(expected));
    }

}
