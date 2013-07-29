#!/bin/sh

set -e

version=$(git tag | sort -t. -k 1,1n -k 2,2n -k 3,3n -k 4,4n | tail -n1)

sed -E -i .tmp "s/^version: .*$/version: $version/g" site/index.md
rm site/index.md.tmp

git add site/index.md
git commit -m "Updated site to version $version" --no-verify ||
git subtree push --prefix site git@github.com:whiskeysierra/primal.git gh-pages