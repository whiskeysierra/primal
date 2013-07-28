#!/bin/sh

set -e

markdown-pp/markdown-pp.py README.template.md README.md
git add README.md
