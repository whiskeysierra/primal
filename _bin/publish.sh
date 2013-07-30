#!/bin/sh

set -e

if [ -z "$(grep -E "^url:" site/_config.yml)" ]; then
    echo "Did you miss to set the url key in site/_config.yml?"
    exit 1
fi

version=$(git tag | sort -t. -k 1,1n -k 2,2n -k 3,3n -k 4,4n | tail -n1)

sed -E -i.tmp "s/^version: .*$/version: $version/g" site/index.md
rm site/index.md.tmp

git add site/index.md

set +e
git commit -m "Updated site to version $version" --no-verify
set -e

git subtree push --prefix site origin gh-pages