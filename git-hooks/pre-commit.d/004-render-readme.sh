#!/bin/sh

set -e

version=$(git tag | sort -t. -k 1,1n -k 2,2n -k 3,3n -k 4,4n | tail -n1)
python docs/render.py ${version} | .markdown-pp/markdown-pp.py /dev/stdin README.md

git add README.md