#!/bin/sh

for i in ../../deca/syntax/valid/*.deca
do
    echo "$i"
    # Remplacer <executable> par test_synt ou test_lex
    # ou test_context ou decac
    test_lex "$i"
    test_synt "$i"
done