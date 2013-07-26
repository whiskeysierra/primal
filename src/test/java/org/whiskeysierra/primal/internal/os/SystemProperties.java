package org.whiskeysierra.primal.internal.os;

public final class SystemProperties {

    public static void main(String[] args) {
        for (Object key : System.getProperties().keySet()) {
            if (key.toString().startsWith("os.")) {
                System.out.printf("%s: %s\n", key, System.getProperties().get(key));
            }
        }
    }

}
