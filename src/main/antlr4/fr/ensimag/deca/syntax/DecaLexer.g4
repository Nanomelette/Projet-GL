lexer grammar DecaLexer;


options {
   language=Java;

   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;

}

@members {
}

// Deca lexer rules.
fragment LETTER :
   'a' .. 'z' |
   'A' .. 'Z'
;

fragment DIGIT :
   '0' .. '9'
;

fragment EPS :
   /* epsilon */
;

fragment EOL :
   '\n'
;

fragment SPACE :
   ' '
;

fragment FORMAT :
   ( '\n'
   | '\r'
   | '\t'
   )
;

// Reserved words

ASM :
   'asm'
;

CLASS :
   'class'
;

EXTENDS :
   'extends'
;

ELSE :
   'else'
;

FALSE :
   'false'
;

IF :
   'if'
;

INSTANCEOF :
   'instanceof'
;

NEW :
   'new'
;

NULL :
   'null'
;

READINT :
   'readInt'
;

READFLOAT :
   'readFloat'
;

PRINT :
   'print'
;

PRINTLN :
   'println'
;

PRINTLNX :
   'printlnx'
;

PRINTX :
   'printx'
;

PROTECTED :
   'protected'
;

RETURN :
   'return'
;

THIS :
   'this'
;

TRUE :
   'true'
;

WHILE :
   'while'
;



// Special symbols
LT :
   '<'
;

GT :
   '>'
;

EQUALS :
   '='
;

PLUS :
   '+'
;

MINUS :
   '-'
;

TIMES :
   '*'
;

SLASH :
   '/'
;

PERCENT :
   '%'
;

DOT :
   '.'
;

COMMA :
   ','
;

OPARENT :
   '('
;

CPARENT :
   ')'
;

OBRACE :
   '{'
;

CBRACE :
   '}'
;

EXCLAM :
   '!'
;

SEMI :
   ';'
;

EQEQ :
   '=='
;

NEQ :
   '!='
;

GEQ :
   '>='
;

LEQ :
   '<='
;

AND :
   '&&'
;

OR :
   '||'
;

// Identifiers : reserved words are not identifiers that's why they are before in the file
IDENT :
   (LETTER | '$' | '_')(LETTER DIGIT | '$' | '_')*
;


// INT litterals
POSITIVE_DIGIT :
   '1' .. '9'
;

INT :
   '0' | POSITIVE_DIGIT DIGIT*
;


// Float litterals
NUM :
   DIGIT+
;

fragment SIGN :
   ( PLUS 
   | MINUS 
   | EPS
   )
;

EXP : 
   ('E' | 'e') SIGN NUM
;

DEC :
   NUM '.' NUM
;

fragment FLOATDEC :
   (DEC | DEC EXP) ('F' | 'f ' | EPS)
;

fragment DIGITHEX :
   '0' .. '9' | 'A' .. 'F' | 'a' .. 'f'
;

fragment NUMHEX :
   DIGITHEX+
;

fragment FLOATHEX :
   ('0x' | '0X') NUMHEX '.' NUMHEX ('P' | 'p') SIGN NUM ('F' | 'f' | EPS)
;

FLOAT :
   FLOATDEC | FLOATHEX
;

// Strings
fragment STRING_CAR :
   ~('"' | '\\' | '\n')
;

STRING :
   '"' (STRING_CAR | '\\"' | '\\\\')* '"'
;

MULTI_LINE_STRING :
   '"' (STRING_CAR | EOL | '\\"' | '\\\\')* '"'
;

// Comments
CLASSIC_COMMENT :
   '/*' .*? '*/'
   { skip(); }
;

MONO_LINE_COMMENT :
   ('//' .*? EOL)
   { skip(); }
;

// Separators
WS :
   ( SPACE
   | FORMAT
   ) 
   { skip(); }
;

// File inclusion
FILENAME : 
   (LETTER | DIGIT | '.' | MINUS | '_')+
;

INCLUDE :
   '#include' (SPACE)* '"' FILENAME '"'
;

DEFAULT:
   .
;