package org.whiskeysierra.process;

public enum Family {

    MAC("mac"),

    NETWARE("netware"),

    OPENVMS("openvms"),

    OS_2("os/2"),

    OS_400("os/400"),

    UNIX("unix") {

        @Override
        boolean matches(String name, String pathSeparator) {
            return pathSeparator.equals(":") &&
                !OPENVMS.matches(name, pathSeparator) &&
                (!MAC.matches(name, pathSeparator) || name.endsWith("x"));
        }

    },

    WIN9X("win9x") {

        @Override
        boolean matches(String name, String pathSeparator) {
            return WINDOWS.matches(name, pathSeparator) &&
                (name.contains("95") || name.contains("98") || name.contains("me") || name.contains("ce"));
        }

    },

    WINDOWS("windows"),

    Z_OS("z/os") {

        @Override
        boolean matches(String name, String pathSeparator) {
            return name.contains(getName()) || name.contains("os/390");
        }

    };

    private final String name;

    Family(String name) {
        this.name = name;
    }

    boolean matches(String name, String pathSeparator) {
        return name.contains(getName());
    }

    String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
