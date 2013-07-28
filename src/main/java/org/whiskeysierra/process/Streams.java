package org.whiskeysierra.process;

import org.whiskeysierra.process.Stream.Input;
import org.whiskeysierra.process.Stream.Output;

// TODO document the intention behind this class: we don't want to leak the enums into the public api
final class Streams {

    private Streams() {

    }

    static enum DefaultOutput implements Output {

        OUTPUT("stdout"), ERROR("stderr");

        private final String name;

        DefaultOutput(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    static enum DefaultInput implements Input {

        INPUT("stdin");

        private final String name;

        DefaultInput(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }
}
