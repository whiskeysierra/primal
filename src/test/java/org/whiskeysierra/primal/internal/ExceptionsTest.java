package org.whiskeysierra.primal.internal;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;

public class ExceptionsTest {

    private static final class Thrower implements Runnable {

        private final IOException e;

        private Thrower(IOException e) {
            this.e = e;
        }

        @Override
        public void run() {
            throw Exceptions.sneakyThrow(e);
        }

    }

    /**
     * Tests {@link Exceptions#sneakyThrow(Throwable)}.
     */
    @Test
    public void test() {
        final IOException e = new IOException();

        try {
            new Thrower(e).run();
            Assert.fail("Should never get here.");
        } catch (Throwable t) {
            assertThat(e, instanceOf(IOException.class));
            assertThat(e, sameInstance(t));
            return;
        }

        Assert.fail("Should never get here.");
    }

}
