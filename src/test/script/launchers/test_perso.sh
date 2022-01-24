#!/bin/sh

SOURCE=$0
SOURCE="${SOURCE%/*}/../../deca/syntax/valid/*.deca"

SOURCE_DECA="${SOURCE%/*}/../../../../main/bin/"

for i in $SOURCE
do
    echo "$i"
    # Remplacer <executable> par test_synt ou test_lex
    # ou test_context ou decac
    # test_lex "$i"
    # test_synt "$i"
    cd $SOURCE_DECA
    ./decac "$i"
done