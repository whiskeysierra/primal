# -*- coding: utf-8 -*-

from __future__ import unicode_literals

import jinja2
import sys

env = jinja2.Environment(loader=jinja2.FileSystemLoader(''))
template = env.get_template('README.template.md')
print template.render(version=sys.argv[1]).encode('utf-8')