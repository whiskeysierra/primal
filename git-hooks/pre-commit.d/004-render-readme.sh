#!/bin/sh

set -e

version=$(git tag | sort -t. -k 1,1n -k 2,2n -k 3,3n -k 4,4n | tail -n1)
sed "s/#VERSION#/$version/g" README.template.md > README.template.md.tmp

.markdown-pp/markdown-pp.py README.template.md.tmp README.md
git add README.md

rm README.template.md.tmp