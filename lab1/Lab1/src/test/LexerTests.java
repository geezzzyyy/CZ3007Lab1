package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import lexer.Lexer;

import org.junit.Test;

import frontend.Token;
import frontend.Token.Type;
import static frontend.Token.Type.*;

/**
 * This class contains unit tests for your lexer. Currently, there is only one test, but you
 * are strongly encouraged to write your own tests.
 */
public class LexerTests {
	// helper method to run tests; no need to change this
	private final void runtest(String input, Token... output) {
		Lexer lexer = new Lexer(new StringReader(input));
		int i=0;
		Token actual=new Token(MODULE, 0, 0, ""), expected;
		try {
			do {
				assertTrue(i < output.length);
				expected = output[i++];
				try {
					actual = lexer.nextToken();
					assertEquals(expected, actual);
				} catch(Error e) {
					if(expected != null)
						fail(e.getMessage());
					/* return; */
				}
			} while(!actual.isEOF());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/** Example unit test. */
	@Test
	public void testKWs() {
		// first argument to runtest is the string to lex; the remaining arguments
		// are the expected tokens
		runtest("module false return while",
				new Token(MODULE, 0, 0, "module"),
				new Token(FALSE, 0, 7, "false"),
				new Token(RETURN, 0, 13, "return"),
				new Token(WHILE, 0, 20, "while"),
				new Token(EOF, 0, 25, ""));
	}

	@Test
	public void testStringLiteralWithDoubleQuote() {
		runtest("\"\"\"",
				new Token(STRING_LITERAL, 0, 0, ""),
				(Token)null,
				new Token(EOF, 0, 3, ""));
	}

	@Test
	public void testStringLiteral() {
		runtest("\"\\n\"", 
				new Token(STRING_LITERAL, 0, 0, "\\n"),
				new Token(EOF, 0, 4, ""));
	}
	
	@Test
	public void testfalseStringLiteral() {
		runtest("\"a\\\"\"", 
				new Token(STRING_LITERAL, 0, 0, "a\\"),
				(Token)null,
				new Token(EOF, 0, 5, ""));
	}
	
	@Test
	public void testNormalStringLiteral() {
		runtest("\"test String\"", 
				new Token(STRING_LITERAL, 0, 0, "test String"),
				new Token(EOF, 0, 13, ""));
	}
	
	@Test
	public void testPunctuation() {
		runtest(",[{(  )}];", 
				new Token(COMMA, 0, 0, ","),
				new Token(LBRACKET, 0, 1, "["),
				new Token(LCURLY, 0, 2, "{"),
				new Token(LPAREN, 0, 3, "("),
				new Token(RPAREN, 0, 6, ")"),
				new Token(RCURLY, 0, 7, "}"),
				new Token(RBRACKET, 0, 8, "]"),
				new Token(SEMICOLON, 0, 9, ";"),
				new Token(EOF, 0, 10, ""));
	}
	
	@Test
	public void testPlus() {
		runtest("+", 
				new Token(PLUS, 0, 0, "+"),
				new Token(EOF, 0, 1, ""));
	}
	
	@Test
	public void testMinus() {
		runtest("-", 
				new Token(MINUS, 0, 0, "-"),
				new Token(EOF, 0, 1, ""));
	}
	
	@Test
	public void testTimes() {
		runtest("*", 
				new Token(TIMES, 0, 0, "*"),
				new Token(EOF, 0, 1, ""));
	}
	
	@Test
	public void testDiv() {
		runtest("/", 
				new Token(DIV, 0, 0, "/"),
				new Token(EOF, 0, 1, ""));
	}
	
	@Test
	public void testGt() {
		runtest(">", 
				new Token(GT, 0, 0, ">"),
				new Token(EOF, 0, 1, ""));
	}
	
	@Test
	public void testLt() {
		runtest("<", 
				new Token(LT, 0, 0, "<"),
				new Token(EOF, 0, 1, ""));
	}
	
	@Test
	public void testGeq() {
		runtest(">=", 
				new Token(GEQ, 0, 0, ">="),
				new Token(EOF, 0, 2, ""));
	}
	
	@Test
	public void testLeq() {
		runtest("<=", 
				new Token(LEQ, 0, 0, "<="),
				new Token(EOF, 0, 2, ""));
	}
	
	@Test
	public void testEql() {
		runtest("=", 
				new Token(EQL, 0, 0, "="),
				new Token(EOF, 0, 1, ""));
	}
	
	@Test
	public void testEqeq() {
		runtest("==", 
				new Token(EQEQ, 0, 0, "=="),
				new Token(EOF, 0, 2, ""));
	}
	
	@Test
	public void testNeq() {
		runtest("!=", 
				new Token(NEQ, 0, 0, "!="),
				new Token(EOF, 0, 2, ""));
	}
	
	@Test
	public void testInt_literal() {
		runtest("+0090", 
				new Token(INT_LITERAL, 0, 0, "+0090"),
				new Token(EOF, 0, 5, ""));
	}
	
	@Test
	public void testFalseInt_literal() {
		runtest("a090",
				(Token)null,
				(Token)null,
				(Token)null,
				(Token)null,
		new Token(EOF, 0, 4, ""));
	}
	
	
	@Test
	public void testID() {
		runtest("aA_09A", 
				new Token(ID, 0, 0, "aA_09A"),
				new Token(EOF, 0, 6, ""));
	}
	
	@Test
	public void testFalseIDWithUnderscope() {
		runtest("_09A", 
				(Token)null,
				(Token)null,
				(Token)null,
				(Token)null,
				new Token(EOF, 0, 4, ""));
	}

}
