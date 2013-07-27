package org.whiskeysierra.process;

public interface Stream {

    public static final Input INPUT = Input.INPUT;
    public static final Output OUTPUT = Output.OUTPUT;
    public static final Output ERROR = Output.ERROR;

    public enum Output implements Stream {

        OUTPUT("stdout"), ERROR("stderr");

        private final String name;

        Output(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    public enum Input implements Stream {

        INPUT("stdin");

        private final String name;

        Input(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

}
