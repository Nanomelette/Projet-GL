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
   (LETTER | '$' | '_')(LETTER | DIGIT | '$' | '_')*
;


// INT litterals
fragment POSITIVE_DIGIT :
   '1' .. '9'
;

INT :
   '0' | POSITIVE_DIGIT DIGIT*
   {
       try {
           int i = Integer.parseInt(getText());
           int borne = 2147483647;
           assert ((-borne-1 < i) && (i < borne)) : getSourceName() + ":" + getInterpreter().getLine() + ":" + getInterpreter().getCharPositionInLine() + " : Le littéral " + getText() + " is too large !";
       } catch (java.lang.AssertionError e) {
            System.out.println(e.getMessage());
            System.exit(0);
       } catch (java.lang.NumberFormatException e) {
           System.out.println(getSourceName() + ":" + getInterpreter().getLine() + ":" + getInterpreter().getCharPositionInLine() + " : Le littéral " + getText() + " is too large !");
           System.exit(0);
       }
   }
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

fragment EXP : 
   ('E' | 'e') SIGN NUM
;

fragment DEC :
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
   (FLOATDEC | FLOATHEX)
   {
       double res = Double.parseDouble(getText());
       float round = (float)res;
       try { 
        assert (round != Float.POSITIVE_INFINITY) : getSourceName() + ":" + getInterpreter().getLine() + ":" + getInterpreter().getCharPositionInLine() + " : Le littéral " + getText() + " is too large !" ;

        assert (!((round == 0.0) && (res != 0.0))) : getSourceName() + ":" + getInterpreter().getLine() + ":" + getInterpreter().getCharPositionInLine() + " : Le littéral " + getText() + " is too small !";
       } catch (java.lang.AssertionError e) {
            System.out.println(e.getMessage());
            System.exit(0);
       } catch (java.lang.NumberFormatException e) {
           System.out.println(getSourceName() + ":" + getInterpreter().getLine() + ":" + getInterpreter().getCharPositionInLine() + " : Le littéral " + getText() + " is too small !");
           System.exit(0);
       }
       setText(String.valueOf(round));
   }
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
fragment FILENAME : 
   (LETTER | DIGIT | '.' | MINUS | '_')+
;

INCLUDE :
   '#include' (SPACE)* '"' FILENAME '"'
   {
       doInclude(getText());
       skip();
    }
;

DEFAULT:
   .
   {
       try {
            assert false : getSourceName() + ":" + getInterpreter().getLine() + ":" + getInterpreter().getCharPositionInLine() + " : this character is not recognize by our Lexer !";
       } catch (java.lang.AssertionError e) {
            System.out.println(e.getMessage());
            System.exit(0);
       }
   }
;