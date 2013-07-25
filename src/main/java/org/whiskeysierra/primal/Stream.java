package org.whiskeysierra.primal;

// TODO change toString to return "stdin", "stdout", "stderr"
public interface Stream {

    public static final Output INPUT = Output.INPUT;
    public static final Input OUTPUT = Input.OUTPUT;
    public static final Input ERROR = Input.ERROR;

    public enum Input implements Stream {
        OUTPUT, ERROR
    }

    public enum Output implements Stream {
        INPUT
    }

}
