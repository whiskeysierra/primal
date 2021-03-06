package org.whiskeysierra.process;

import java.util.Set;

public final class OsUsage {

    public void test() {
        final Os os = Os.getCurrent();

        System.out.println(os.getName());
        System.out.println(os.getArch());
        System.out.println(os.getVersion());

        for (Family family : os.getFamilies()) {
            System.out.print(family);
        }
    }

    public void unix() {
        final Os os = Os.getCurrent();
        assert os.getFamilies().contains(Family.UNIX);
    }

    public void osx() {
        final Os os = Os.getCurrent();
        final Set<Family> families = os.getFamilies();

        assert families.contains(Family.MAC) && families.contains(Family.UNIX);
    }

}
