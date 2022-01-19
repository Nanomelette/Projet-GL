// Generated from /user/9/.base/marthoma/home/Projet_GL/src/main/antlr4/fr/ensimag/deca/syntax/DecaLexer.g4 by ANTLR 4.8
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DecaLexer extends AbstractDecaLexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ASM=1, CLASS=2, EXTENDS=3, ELSE=4, FALSE=5, IF=6, INSTANCEOF=7, NEW=8, 
		NULL=9, READINT=10, READFLOAT=11, PRINT=12, PRINTLN=13, PRINTLNX=14, PRINTX=15, 
		PROTECTED=16, RETURN=17, THIS=18, TRUE=19, WHILE=20, LT=21, GT=22, EQUALS=23, 
		PLUS=24, MINUS=25, TIMES=26, SLASH=27, PERCENT=28, DOT=29, COMMA=30, OPARENT=31, 
		CPARENT=32, OBRACE=33, CBRACE=34, EXCLAM=35, SEMI=36, EQEQ=37, NEQ=38, 
		GEQ=39, LEQ=40, AND=41, OR=42, IDENT=43, INT=44, NUM=45, FLOAT=46, STRING=47, 
		MULTI_LINE_STRING=48, CLASSIC_COMMENT=49, MONO_LINE_COMMENT=50, WS=51, 
		INCLUDE=52, DEFAULT=53;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"LETTER", "DIGIT", "EPS", "EOL", "SPACE", "FORMAT", "ASM", "CLASS", "EXTENDS", 
			"ELSE", "FALSE", "IF", "INSTANCEOF", "NEW", "NULL", "READINT", "READFLOAT", 
			"PRINT", "PRINTLN", "PRINTLNX", "PRINTX", "PROTECTED", "RETURN", "THIS", 
			"TRUE", "WHILE", "LT", "GT", "EQUALS", "PLUS", "MINUS", "TIMES", "SLASH", 
			"PERCENT", "DOT", "COMMA", "OPARENT", "CPARENT", "OBRACE", "CBRACE", 
			"EXCLAM", "SEMI", "EQEQ", "NEQ", "GEQ", "LEQ", "AND", "OR", "IDENT", 
			"POSITIVE_DIGIT", "INT", "NUM", "SIGN", "EXP", "DEC", "FLOATDEC", "DIGITHEX", 
			"NUMHEX", "FLOATHEX", "FLOAT", "STRING_CAR", "STRING", "MULTI_LINE_STRING", 
			"CLASSIC_COMMENT", "MONO_LINE_COMMENT", "WS", "FILENAME", "INCLUDE", 
			"DEFAULT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'asm'", "'class'", "'extends'", "'else'", "'false'", "'if'", "'instanceof'", 
			"'new'", "'null'", "'readInt'", "'readFloat'", "'print'", "'println'", 
			"'printlnx'", "'printx'", "'protected'", "'return'", "'this'", "'true'", 
			"'while'", "'<'", "'>'", "'='", "'+'", "'-'", "'*'", "'/'", "'%'", "'.'", 
			"','", "'('", "')'", "'{'", "'}'", "'!'", "';'", "'=='", "'!='", "'>='", 
			"'<='", "'&&'", "'||'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ASM", "CLASS", "EXTENDS", "ELSE", "FALSE", "IF", "INSTANCEOF", 
			"NEW", "NULL", "READINT", "READFLOAT", "PRINT", "PRINTLN", "PRINTLNX", 
			"PRINTX", "PROTECTED", "RETURN", "THIS", "TRUE", "WHILE", "LT", "GT", 
			"EQUALS", "PLUS", "MINUS", "TIMES", "SLASH", "PERCENT", "DOT", "COMMA", 
			"OPARENT", "CPARENT", "OBRACE", "CBRACE", "EXCLAM", "SEMI", "EQEQ", "NEQ", 
			"GEQ", "LEQ", "AND", "OR", "IDENT", "INT", "NUM", "FLOAT", "STRING", 
			"MULTI_LINE_STRING", "CLASSIC_COMMENT", "MONO_LINE_COMMENT", "WS", "INCLUDE", 
			"DEFAULT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}




	public DecaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DecaLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 50:
			INT_action((RuleContext)_localctx, actionIndex);
			break;
		case 59:
			FLOAT_action((RuleContext)_localctx, actionIndex);
			break;
		case 63:
			CLASSIC_COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		case 64:
			MONO_LINE_COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		case 65:
			WS_action((RuleContext)_localctx, actionIndex);
			break;
		case 67:
			INCLUDE_action((RuleContext)_localctx, actionIndex);
			break;
		case 68:
			DEFAULT_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void INT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:

			       try {
			           int i = Integer.parseInt(getText());
			           int borne = 2147483647;
			           assert ((-borne-1 < i) && (i < borne)) : getSourceName() + ":" + getInterpreter().getLine() + ":" + getInterpreter().getCharPositionInLine() + " : " + getText() + " is too large !";
			       } catch (java.lang.AssertionError e) {
			            System.out.println(e.getMessage());
			            System.exit(0);
			       } catch (java.lang.NumberFormatException e) {
			           System.out.println(getSourceName() + ":" + getInterpreter().getLine() + ":" + getInterpreter().getCharPositionInLine() + " : " + getText() + " is too large !");
			           System.exit(0);
			       }
			   
			break;
		}
	}
	private void FLOAT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:

			       double res = Double.parseDouble(getText());
			       float round = (float)res;
			       try { 
			        assert (round != Float.POSITIVE_INFINITY) : getSourceName() + ":" + getInterpreter().getLine() + ":" + getInterpreter().getCharPositionInLine() + " : " + getText() + " is too large !" ;

			        assert (!((round == 0.0) && (res != 0.0))) : getSourceName() + ":" + getInterpreter().getLine() + ":" + getInterpreter().getCharPositionInLine() + " : " + getText() + " is too small !";
			       } catch (java.lang.AssertionError e) {
			            System.out.println(e.getMessage());
			            System.exit(0);
			       } catch (java.lang.NumberFormatException e) {
			           System.out.println(getSourceName() + ":" + getInterpreter().getLine() + ":" + getInterpreter().getCharPositionInLine() + " : " + getText() + " is too small !");
			           System.exit(0);
			       }
			       setText(String.valueOf(round));
			   
			break;
		}
	}
	private void CLASSIC_COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:
			 skip(); 
			break;
		}
	}
	private void MONO_LINE_COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 3:
			 skip(); 
			break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 4:
			 skip(); 
			break;
		}
	}
	private void INCLUDE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 5:

			       doInclude(getText());
			       skip();
			    
			break;
		}
	}
	private void DEFAULT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 6:

			       try {
			            assert false : getSourceName() + ":" + getInterpreter().getLine() + ":" + getInterpreter().getCharPositionInLine() + " : this character is not recognize by our Lexer !";
			       } catch (java.lang.AssertionError e) {
			            System.out.println(e.getMessage());
			            System.exit(0);
			       }
			   
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\67\u0201\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\3\2\3\2\3\3\3"+
		"\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\16\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3"+
		"\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3"+
		"\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3"+
		"\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3"+
		"\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3"+
		"(\3)\3)\3*\3*\3+\3+\3,\3,\3,\3-\3-\3-\3.\3.\3.\3/\3/\3/\3\60\3\60\3\60"+
		"\3\61\3\61\3\61\3\62\3\62\5\62\u0153\n\62\3\62\3\62\3\62\7\62\u0158\n"+
		"\62\f\62\16\62\u015b\13\62\3\63\3\63\3\64\3\64\3\64\7\64\u0162\n\64\f"+
		"\64\16\64\u0165\13\64\3\64\3\64\5\64\u0169\n\64\3\65\6\65\u016c\n\65\r"+
		"\65\16\65\u016d\3\66\3\66\3\66\5\66\u0173\n\66\3\67\3\67\3\67\3\67\38"+
		"\38\38\38\39\39\39\39\59\u0181\n9\39\39\59\u0185\n9\3:\3:\3;\6;\u018a"+
		"\n;\r;\16;\u018b\3<\3<\3<\3<\5<\u0192\n<\3<\3<\3<\3<\3<\3<\3<\3<\5<\u019c"+
		"\n<\3=\3=\5=\u01a0\n=\3=\3=\3>\3>\3?\3?\3?\3?\3?\3?\7?\u01ac\n?\f?\16"+
		"?\u01af\13?\3?\3?\3@\3@\3@\3@\3@\3@\3@\7@\u01ba\n@\f@\16@\u01bd\13@\3"+
		"@\3@\3A\3A\3A\3A\7A\u01c5\nA\fA\16A\u01c8\13A\3A\3A\3A\3A\3A\3B\3B\3B"+
		"\3B\7B\u01d3\nB\fB\16B\u01d6\13B\3B\3B\3B\3B\3C\3C\5C\u01de\nC\3C\3C\3"+
		"D\3D\3D\3D\3D\6D\u01e7\nD\rD\16D\u01e8\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\7"+
		"E\u01f5\nE\fE\16E\u01f8\13E\3E\3E\3E\3E\3E\3F\3F\3F\4\u01c6\u01d4\2G\3"+
		"\2\5\2\7\2\t\2\13\2\r\2\17\3\21\4\23\5\25\6\27\7\31\b\33\t\35\n\37\13"+
		"!\f#\r%\16\'\17)\20+\21-\22/\23\61\24\63\25\65\26\67\279\30;\31=\32?\33"+
		"A\34C\35E\36G\37I K!M\"O#Q$S%U&W\'Y([)]*_+a,c-e\2g.i/k\2m\2o\2q\2s\2u"+
		"\2w\2y\60{\2}\61\177\62\u0081\63\u0083\64\u0085\65\u0087\2\u0089\66\u008b"+
		"\67\3\2\n\4\2C\\c|\4\2\13\f\17\17\4\2&&aa\4\2GGgg\4\2HHhh\5\2\62;CHch"+
		"\4\2RRrr\5\2\f\f$$^^\2\u020f\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2"+
		"\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3"+
		"\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2"+
		"\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67"+
		"\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2"+
		"\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2"+
		"\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]"+
		"\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2y\3\2"+
		"\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3\2\2\2\2\u0085"+
		"\3\2\2\2\2\u0089\3\2\2\2\2\u008b\3\2\2\2\3\u008d\3\2\2\2\5\u008f\3\2\2"+
		"\2\7\u0091\3\2\2\2\t\u0093\3\2\2\2\13\u0095\3\2\2\2\r\u0097\3\2\2\2\17"+
		"\u0099\3\2\2\2\21\u009d\3\2\2\2\23\u00a3\3\2\2\2\25\u00ab\3\2\2\2\27\u00b0"+
		"\3\2\2\2\31\u00b6\3\2\2\2\33\u00b9\3\2\2\2\35\u00c4\3\2\2\2\37\u00c8\3"+
		"\2\2\2!\u00cd\3\2\2\2#\u00d5\3\2\2\2%\u00df\3\2\2\2\'\u00e5\3\2\2\2)\u00ed"+
		"\3\2\2\2+\u00f6\3\2\2\2-\u00fd\3\2\2\2/\u0107\3\2\2\2\61\u010e\3\2\2\2"+
		"\63\u0113\3\2\2\2\65\u0118\3\2\2\2\67\u011e\3\2\2\29\u0120\3\2\2\2;\u0122"+
		"\3\2\2\2=\u0124\3\2\2\2?\u0126\3\2\2\2A\u0128\3\2\2\2C\u012a\3\2\2\2E"+
		"\u012c\3\2\2\2G\u012e\3\2\2\2I\u0130\3\2\2\2K\u0132\3\2\2\2M\u0134\3\2"+
		"\2\2O\u0136\3\2\2\2Q\u0138\3\2\2\2S\u013a\3\2\2\2U\u013c\3\2\2\2W\u013e"+
		"\3\2\2\2Y\u0141\3\2\2\2[\u0144\3\2\2\2]\u0147\3\2\2\2_\u014a\3\2\2\2a"+
		"\u014d\3\2\2\2c\u0152\3\2\2\2e\u015c\3\2\2\2g\u0168\3\2\2\2i\u016b\3\2"+
		"\2\2k\u0172\3\2\2\2m\u0174\3\2\2\2o\u0178\3\2\2\2q\u0180\3\2\2\2s\u0186"+
		"\3\2\2\2u\u0189\3\2\2\2w\u0191\3\2\2\2y\u019f\3\2\2\2{\u01a3\3\2\2\2}"+
		"\u01a5\3\2\2\2\177\u01b2\3\2\2\2\u0081\u01c0\3\2\2\2\u0083\u01ce\3\2\2"+
		"\2\u0085\u01dd\3\2\2\2\u0087\u01e6\3\2\2\2\u0089\u01ea\3\2\2\2\u008b\u01fe"+
		"\3\2\2\2\u008d\u008e\t\2\2\2\u008e\4\3\2\2\2\u008f\u0090\4\62;\2\u0090"+
		"\6\3\2\2\2\u0091\u0092\3\2\2\2\u0092\b\3\2\2\2\u0093\u0094\7\f\2\2\u0094"+
		"\n\3\2\2\2\u0095\u0096\7\"\2\2\u0096\f\3\2\2\2\u0097\u0098\t\3\2\2\u0098"+
		"\16\3\2\2\2\u0099\u009a\7c\2\2\u009a\u009b\7u\2\2\u009b\u009c\7o\2\2\u009c"+
		"\20\3\2\2\2\u009d\u009e\7e\2\2\u009e\u009f\7n\2\2\u009f\u00a0\7c\2\2\u00a0"+
		"\u00a1\7u\2\2\u00a1\u00a2\7u\2\2\u00a2\22\3\2\2\2\u00a3\u00a4\7g\2\2\u00a4"+
		"\u00a5\7z\2\2\u00a5\u00a6\7v\2\2\u00a6\u00a7\7g\2\2\u00a7\u00a8\7p\2\2"+
		"\u00a8\u00a9\7f\2\2\u00a9\u00aa\7u\2\2\u00aa\24\3\2\2\2\u00ab\u00ac\7"+
		"g\2\2\u00ac\u00ad\7n\2\2\u00ad\u00ae\7u\2\2\u00ae\u00af\7g\2\2\u00af\26"+
		"\3\2\2\2\u00b0\u00b1\7h\2\2\u00b1\u00b2\7c\2\2\u00b2\u00b3\7n\2\2\u00b3"+
		"\u00b4\7u\2\2\u00b4\u00b5\7g\2\2\u00b5\30\3\2\2\2\u00b6\u00b7\7k\2\2\u00b7"+
		"\u00b8\7h\2\2\u00b8\32\3\2\2\2\u00b9\u00ba\7k\2\2\u00ba\u00bb\7p\2\2\u00bb"+
		"\u00bc\7u\2\2\u00bc\u00bd\7v\2\2\u00bd\u00be\7c\2\2\u00be\u00bf\7p\2\2"+
		"\u00bf\u00c0\7e\2\2\u00c0\u00c1\7g\2\2\u00c1\u00c2\7q\2\2\u00c2\u00c3"+
		"\7h\2\2\u00c3\34\3\2\2\2\u00c4\u00c5\7p\2\2\u00c5\u00c6\7g\2\2\u00c6\u00c7"+
		"\7y\2\2\u00c7\36\3\2\2\2\u00c8\u00c9\7p\2\2\u00c9\u00ca\7w\2\2\u00ca\u00cb"+
		"\7n\2\2\u00cb\u00cc\7n\2\2\u00cc \3\2\2\2\u00cd\u00ce\7t\2\2\u00ce\u00cf"+
		"\7g\2\2\u00cf\u00d0\7c\2\2\u00d0\u00d1\7f\2\2\u00d1\u00d2\7K\2\2\u00d2"+
		"\u00d3\7p\2\2\u00d3\u00d4\7v\2\2\u00d4\"\3\2\2\2\u00d5\u00d6\7t\2\2\u00d6"+
		"\u00d7\7g\2\2\u00d7\u00d8\7c\2\2\u00d8\u00d9\7f\2\2\u00d9\u00da\7H\2\2"+
		"\u00da\u00db\7n\2\2\u00db\u00dc\7q\2\2\u00dc\u00dd\7c\2\2\u00dd\u00de"+
		"\7v\2\2\u00de$\3\2\2\2\u00df\u00e0\7r\2\2\u00e0\u00e1\7t\2\2\u00e1\u00e2"+
		"\7k\2\2\u00e2\u00e3\7p\2\2\u00e3\u00e4\7v\2\2\u00e4&\3\2\2\2\u00e5\u00e6"+
		"\7r\2\2\u00e6\u00e7\7t\2\2\u00e7\u00e8\7k\2\2\u00e8\u00e9\7p\2\2\u00e9"+
		"\u00ea\7v\2\2\u00ea\u00eb\7n\2\2\u00eb\u00ec\7p\2\2\u00ec(\3\2\2\2\u00ed"+
		"\u00ee\7r\2\2\u00ee\u00ef\7t\2\2\u00ef\u00f0\7k\2\2\u00f0\u00f1\7p\2\2"+
		"\u00f1\u00f2\7v\2\2\u00f2\u00f3\7n\2\2\u00f3\u00f4\7p\2\2\u00f4\u00f5"+
		"\7z\2\2\u00f5*\3\2\2\2\u00f6\u00f7\7r\2\2\u00f7\u00f8\7t\2\2\u00f8\u00f9"+
		"\7k\2\2\u00f9\u00fa\7p\2\2\u00fa\u00fb\7v\2\2\u00fb\u00fc\7z\2\2\u00fc"+
		",\3\2\2\2\u00fd\u00fe\7r\2\2\u00fe\u00ff\7t\2\2\u00ff\u0100\7q\2\2\u0100"+
		"\u0101\7v\2\2\u0101\u0102\7g\2\2\u0102\u0103\7e\2\2\u0103\u0104\7v\2\2"+
		"\u0104\u0105\7g\2\2\u0105\u0106\7f\2\2\u0106.\3\2\2\2\u0107\u0108\7t\2"+
		"\2\u0108\u0109\7g\2\2\u0109\u010a\7v\2\2\u010a\u010b\7w\2\2\u010b\u010c"+
		"\7t\2\2\u010c\u010d\7p\2\2\u010d\60\3\2\2\2\u010e\u010f\7v\2\2\u010f\u0110"+
		"\7j\2\2\u0110\u0111\7k\2\2\u0111\u0112\7u\2\2\u0112\62\3\2\2\2\u0113\u0114"+
		"\7v\2\2\u0114\u0115\7t\2\2\u0115\u0116\7w\2\2\u0116\u0117\7g\2\2\u0117"+
		"\64\3\2\2\2\u0118\u0119\7y\2\2\u0119\u011a\7j\2\2\u011a\u011b\7k\2\2\u011b"+
		"\u011c\7n\2\2\u011c\u011d\7g\2\2\u011d\66\3\2\2\2\u011e\u011f\7>\2\2\u011f"+
		"8\3\2\2\2\u0120\u0121\7@\2\2\u0121:\3\2\2\2\u0122\u0123\7?\2\2\u0123<"+
		"\3\2\2\2\u0124\u0125\7-\2\2\u0125>\3\2\2\2\u0126\u0127\7/\2\2\u0127@\3"+
		"\2\2\2\u0128\u0129\7,\2\2\u0129B\3\2\2\2\u012a\u012b\7\61\2\2\u012bD\3"+
		"\2\2\2\u012c\u012d\7\'\2\2\u012dF\3\2\2\2\u012e\u012f\7\60\2\2\u012fH"+
		"\3\2\2\2\u0130\u0131\7.\2\2\u0131J\3\2\2\2\u0132\u0133\7*\2\2\u0133L\3"+
		"\2\2\2\u0134\u0135\7+\2\2\u0135N\3\2\2\2\u0136\u0137\7}\2\2\u0137P\3\2"+
		"\2\2\u0138\u0139\7\177\2\2\u0139R\3\2\2\2\u013a\u013b\7#\2\2\u013bT\3"+
		"\2\2\2\u013c\u013d\7=\2\2\u013dV\3\2\2\2\u013e\u013f\7?\2\2\u013f\u0140"+
		"\7?\2\2\u0140X\3\2\2\2\u0141\u0142\7#\2\2\u0142\u0143\7?\2\2\u0143Z\3"+
		"\2\2\2\u0144\u0145\7@\2\2\u0145\u0146\7?\2\2\u0146\\\3\2\2\2\u0147\u0148"+
		"\7>\2\2\u0148\u0149\7?\2\2\u0149^\3\2\2\2\u014a\u014b\7(\2\2\u014b\u014c"+
		"\7(\2\2\u014c`\3\2\2\2\u014d\u014e\7~\2\2\u014e\u014f\7~\2\2\u014fb\3"+
		"\2\2\2\u0150\u0153\5\3\2\2\u0151\u0153\t\4\2\2\u0152\u0150\3\2\2\2\u0152"+
		"\u0151\3\2\2\2\u0153\u0159\3\2\2\2\u0154\u0158\5\3\2\2\u0155\u0158\5\5"+
		"\3\2\u0156\u0158\t\4\2\2\u0157\u0154\3\2\2\2\u0157\u0155\3\2\2\2\u0157"+
		"\u0156\3\2\2\2\u0158\u015b\3\2\2\2\u0159\u0157\3\2\2\2\u0159\u015a\3\2"+
		"\2\2\u015ad\3\2\2\2\u015b\u0159\3\2\2\2\u015c\u015d\4\63;\2\u015df\3\2"+
		"\2\2\u015e\u0169\7\62\2\2\u015f\u0163\5e\63\2\u0160\u0162\5\5\3\2\u0161"+
		"\u0160\3\2\2\2\u0162\u0165\3\2\2\2\u0163\u0161\3\2\2\2\u0163\u0164\3\2"+
		"\2\2\u0164\u0166\3\2\2\2\u0165\u0163\3\2\2\2\u0166\u0167\b\64\2\2\u0167"+
		"\u0169\3\2\2\2\u0168\u015e\3\2\2\2\u0168\u015f\3\2\2\2\u0169h\3\2\2\2"+
		"\u016a\u016c\5\5\3\2\u016b\u016a\3\2\2\2\u016c\u016d\3\2\2\2\u016d\u016b"+
		"\3\2\2\2\u016d\u016e\3\2\2\2\u016ej\3\2\2\2\u016f\u0173\5=\37\2\u0170"+
		"\u0173\5? \2\u0171\u0173\5\7\4\2\u0172\u016f\3\2\2\2\u0172\u0170\3\2\2"+
		"\2\u0172\u0171\3\2\2\2\u0173l\3\2\2\2\u0174\u0175\t\5\2\2\u0175\u0176"+
		"\5k\66\2\u0176\u0177\5i\65\2\u0177n\3\2\2\2\u0178\u0179\5i\65\2\u0179"+
		"\u017a\7\60\2\2\u017a\u017b\5i\65\2\u017bp\3\2\2\2\u017c\u0181\5o8\2\u017d"+
		"\u017e\5o8\2\u017e\u017f\5m\67\2\u017f\u0181\3\2\2\2\u0180\u017c\3\2\2"+
		"\2\u0180\u017d\3\2\2\2\u0181\u0184\3\2\2\2\u0182\u0185\t\6\2\2\u0183\u0185"+
		"\5\7\4\2\u0184\u0182\3\2\2\2\u0184\u0183\3\2\2\2\u0185r\3\2\2\2\u0186"+
		"\u0187\t\7\2\2\u0187t\3\2\2\2\u0188\u018a\5s:\2\u0189\u0188\3\2\2\2\u018a"+
		"\u018b\3\2\2\2\u018b\u0189\3\2\2\2\u018b\u018c\3\2\2\2\u018cv\3\2\2\2"+
		"\u018d\u018e\7\62\2\2\u018e\u0192\7z\2\2\u018f\u0190\7\62\2\2\u0190\u0192"+
		"\7Z\2\2\u0191\u018d\3\2\2\2\u0191\u018f\3\2\2\2\u0192\u0193\3\2\2\2\u0193"+
		"\u0194\5u;\2\u0194\u0195\7\60\2\2\u0195\u0196\5u;\2\u0196\u0197\t\b\2"+
		"\2\u0197\u0198\5k\66\2\u0198\u019b\5i\65\2\u0199\u019c\t\6\2\2\u019a\u019c"+
		"\5\7\4\2\u019b\u0199\3\2\2\2\u019b\u019a\3\2\2\2\u019cx\3\2\2\2\u019d"+
		"\u01a0\5q9\2\u019e\u01a0\5w<\2\u019f\u019d\3\2\2\2\u019f\u019e\3\2\2\2"+
		"\u01a0\u01a1\3\2\2\2\u01a1\u01a2\b=\3\2\u01a2z\3\2\2\2\u01a3\u01a4\n\t"+
		"\2\2\u01a4|\3\2\2\2\u01a5\u01ad\7$\2\2\u01a6\u01ac\5{>\2\u01a7\u01a8\7"+
		"^\2\2\u01a8\u01ac\7$\2\2\u01a9\u01aa\7^\2\2\u01aa\u01ac\7^\2\2\u01ab\u01a6"+
		"\3\2\2\2\u01ab\u01a7\3\2\2\2\u01ab\u01a9\3\2\2\2\u01ac\u01af\3\2\2\2\u01ad"+
		"\u01ab\3\2\2\2\u01ad\u01ae\3\2\2\2\u01ae\u01b0\3\2\2\2\u01af\u01ad\3\2"+
		"\2\2\u01b0\u01b1\7$\2\2\u01b1~\3\2\2\2\u01b2\u01bb\7$\2\2\u01b3\u01ba"+
		"\5{>\2\u01b4\u01ba\5\t\5\2\u01b5\u01b6\7^\2\2\u01b6\u01ba\7$\2\2\u01b7"+
		"\u01b8\7^\2\2\u01b8\u01ba\7^\2\2\u01b9\u01b3\3\2\2\2\u01b9\u01b4\3\2\2"+
		"\2\u01b9\u01b5\3\2\2\2\u01b9\u01b7\3\2\2\2\u01ba\u01bd\3\2\2\2\u01bb\u01b9"+
		"\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc\u01be\3\2\2\2\u01bd\u01bb\3\2\2\2\u01be"+
		"\u01bf\7$\2\2\u01bf\u0080\3\2\2\2\u01c0\u01c1\7\61\2\2\u01c1\u01c2\7,"+
		"\2\2\u01c2\u01c6\3\2\2\2\u01c3\u01c5\13\2\2\2\u01c4\u01c3\3\2\2\2\u01c5"+
		"\u01c8\3\2\2\2\u01c6\u01c7\3\2\2\2\u01c6\u01c4\3\2\2\2\u01c7\u01c9\3\2"+
		"\2\2\u01c8\u01c6\3\2\2\2\u01c9\u01ca\7,\2\2\u01ca\u01cb\7\61\2\2\u01cb"+
		"\u01cc\3\2\2\2\u01cc\u01cd\bA\4\2\u01cd\u0082\3\2\2\2\u01ce\u01cf\7\61"+
		"\2\2\u01cf\u01d0\7\61\2\2\u01d0\u01d4\3\2\2\2\u01d1\u01d3\13\2\2\2\u01d2"+
		"\u01d1\3\2\2\2\u01d3\u01d6\3\2\2\2\u01d4\u01d5\3\2\2\2\u01d4\u01d2\3\2"+
		"\2\2\u01d5\u01d7\3\2\2\2\u01d6\u01d4\3\2\2\2\u01d7\u01d8\5\t\5\2\u01d8"+
		"\u01d9\3\2\2\2\u01d9\u01da\bB\5\2\u01da\u0084\3\2\2\2\u01db\u01de\5\13"+
		"\6\2\u01dc\u01de\5\r\7\2\u01dd\u01db\3\2\2\2\u01dd\u01dc\3\2\2\2\u01de"+
		"\u01df\3\2\2\2\u01df\u01e0\bC\6\2\u01e0\u0086\3\2\2\2\u01e1\u01e7\5\3"+
		"\2\2\u01e2\u01e7\5\5\3\2\u01e3\u01e7\7\60\2\2\u01e4\u01e7\5? \2\u01e5"+
		"\u01e7\7a\2\2\u01e6\u01e1\3\2\2\2\u01e6\u01e2\3\2\2\2\u01e6\u01e3\3\2"+
		"\2\2\u01e6\u01e4\3\2\2\2\u01e6\u01e5\3\2\2\2\u01e7\u01e8\3\2\2\2\u01e8"+
		"\u01e6\3\2\2\2\u01e8\u01e9\3\2\2\2\u01e9\u0088\3\2\2\2\u01ea\u01eb\7%"+
		"\2\2\u01eb\u01ec\7k\2\2\u01ec\u01ed\7p\2\2\u01ed\u01ee\7e\2\2\u01ee\u01ef"+
		"\7n\2\2\u01ef\u01f0\7w\2\2\u01f0\u01f1\7f\2\2\u01f1\u01f2\7g\2\2\u01f2"+
		"\u01f6\3\2\2\2\u01f3\u01f5\5\13\6\2\u01f4\u01f3\3\2\2\2\u01f5\u01f8\3"+
		"\2\2\2\u01f6\u01f4\3\2\2\2\u01f6\u01f7\3\2\2\2\u01f7\u01f9\3\2\2\2\u01f8"+
		"\u01f6\3\2\2\2\u01f9\u01fa\7$\2\2\u01fa\u01fb\5\u0087D\2\u01fb\u01fc\7"+
		"$\2\2\u01fc\u01fd\bE\7\2\u01fd\u008a\3\2\2\2\u01fe\u01ff\13\2\2\2\u01ff"+
		"\u0200\bF\b\2\u0200\u008c\3\2\2\2\32\2\u0152\u0157\u0159\u0163\u0168\u016d"+
		"\u0172\u0180\u0184\u018b\u0191\u019b\u019f\u01ab\u01ad\u01b9\u01bb\u01c6"+
		"\u01d4\u01dd\u01e6\u01e8\u01f6\t\3\64\2\3=\3\3A\4\3B\5\3C\6\3E\7\3F\b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}