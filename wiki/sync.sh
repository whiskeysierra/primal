#!/bin/sh

set -e

cd $(dirname $0)/..

version=$(git tag | sort -t. -k 1,1n -k 2,2n -k 3,3n -k 4,4n | tail -n1)

for template in $(ls wiki/.templates); do
    python wiki/render.py wiki/.templates/${template} version=${version} > wiki/${template}
    git add wiki/${template}
done

git subtree push --prefix wiki git@github.com:whiskeysierra/primal.wiki.git master