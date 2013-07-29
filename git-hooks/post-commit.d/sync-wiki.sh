#!/bin/sh

set -e

git subtree pull --prefix wiki --squash git@github.com:whiskeysierra/primal.wiki.git master
git subtree push --prefix wiki git@github.com:whiskeysierra/primal.wiki.git master