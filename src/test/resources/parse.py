#!/usr/bin/env python

import sys

with open(sys.argv[1]) as f:

    name = None
    version = None
    arch = None
    separator = None

    for line in f.readlines():
        line = line.replace("// ", "")

        if line.startswith("os.") or line.startswith("path.separator"):
            parts = line.split("=")
            value = parts[1].split('" "')[0].replace('"', '').strip()

            if line.startswith("os.name"):
                name = value
                arch = None
                version = None
                separator = None

            if line.startswith("os.arch"):
                arch = value

            if line.startswith("os.version"):
                version = value

            if line.startswith("path.separator"):
                separator = value

                print '{{"{0}", "{1}", "{2}", "{3}"}},'.format(name, version, arch, separator)