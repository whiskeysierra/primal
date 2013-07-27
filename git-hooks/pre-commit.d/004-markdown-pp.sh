#!/bin/sh

set -e

markdown-pp/markdown-pp.py README.mdpp README.md
git add README.md
