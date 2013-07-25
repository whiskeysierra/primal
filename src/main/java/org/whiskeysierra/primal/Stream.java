package org.whiskeysierra.primal;

public interface Stream {

    public static final Output INPUT = Output.INPUT;
    public static final Input OUTPUT = Input.OUTPUT;
    public static final Input ERROR = Input.ERROR;

    public enum Input implements Stream {

        OUTPUT("stdout"), ERROR("stderr");

        private final String name;

        Input(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    public enum Output implements Stream {

        INPUT("stdin");

        private final String name;

        Output(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

}
