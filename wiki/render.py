# -*- coding: utf-8 -*-

from __future__ import unicode_literals

import argparse
import jinja2

parser = argparse.ArgumentParser()
parser.add_argument('file')
parser.add_argument('dictionary', nargs='*')
args = parser.parse_args()
data = dict([x.split('=') for x in args.dictionary])

env = jinja2.Environment(loader=jinja2.FileSystemLoader(''))
template = env.get_template(args.file)
print template.render(data).encode('utf-8')