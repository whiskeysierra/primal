#!/bin/sh

set -e

version=$(git tag | sort -t. -k 1,1n -k 2,2n -k 3,3n -k 4,4n | tail -n1)

python site/_bin/render.py README.template.md > README.md
git add README.md
