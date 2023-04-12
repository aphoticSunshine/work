package homework4;

import java.util.ArrayList;

public class Rda {

	static ArrayList<String> tokens;
	static String nextTokens;
	static int i = 0;
	public static void stmt() throws Exception {
		//<STMT> --> <IF_STMT> | <WHILE_STMT> | <BLOCK> | <EXPR>
		//System.out.println(nextTokens + " enter stmt");
		getNextToken(tokens, Rda.i);
		if (nextTokens == "IF_STMT") {
			//System.out.println(nextTokens + " stmt:ifstmt");
			ifStmt();
		}
		else if (nextTokens == "OBRACKET") {
			block();
		}
		else if (nextTokens == "WHILE_STMT") {
			whileLoop();
		}
		else if (nextTokens == "EOF") {
			return;
		}
		else {
			expr();
		}
	

		//System.out.println(nextTokens + " exit stmt");
	}
	
	public static void getNextToken(ArrayList<String> s, int i) {
		//Used to iterate through the list of tokens
		if (Rda.i < s.size()) {
		Rda.i++;
		Rda.nextTokens = s.get(i).toString();
		}
	}
	
	public static void stmtList() throws Exception {
		//<STMTLIST> --> { STMT ';' }
		//System.out.println(nextTokens + " enter stmtlist");
		getNextToken(tokens, Rda.i);
		stmt();
		if (nextTokens == "EOF") {
			return;
		}
		else if (nextTokens != "SEMICOLON") {
			throw new Exception("Missing a semicolon");
		}
		else if (nextTokens == "SEMICOLON") {
			getNextToken(tokens, i);
			stmtList();
		}
		//System.out.println(nextTokens + " exit stmtlist");
	}
	
	public static void whileLoop() throws Exception {
		//<WHILE_LOOP> --> `while` `(` <BOOL_EXPR> `)` ( <STMT> `;` | <BLOCK> )
		if (nextTokens != "WHILE_STMT") {
			throw new Exception("Not in the language");
		}
		else {
			getNextToken(tokens, Rda.i);
			if (nextTokens != "OPAREN") {
				throw new Exception("Not in the language");
			}
			else {
				getNextToken(tokens, Rda.i);
				boolExpr();
				if (nextTokens != "CPAREN") {
					throw new Exception("Not in the language");
				}
				else {
					getNextToken(tokens, Rda.i);
					if(nextTokens == "OBRACKET") {
						block();
					}
					else {
						getNextToken(tokens, Rda.i);
						stmt();
						if (nextTokens != "SEMICOLON")
							throw new Exception("Not in the language");
						else if (nextTokens == "SEMICOLON") {
							getNextToken(tokens, Rda.i);
							stmt();
						}
						else {
							getNextToken(tokens, Rda.i);
							block();
						}
					}
				}
			}
		}
	}
	
	public static void ifStmt() throws Exception {
		//<IF_STMT> --> `if` `(` <BOOL_EXPR> `)` ( <STMT> `;` | <BLOCK> ) [ `else` ( <STMT> `;` | <BLOCK> )] 
		//System.out.println(nextTokens + " enter ifstmt");
		if (nextTokens != "IF_STMT") {
			throw new Exception("missing if");
		}
		else {
			getNextToken(tokens, Rda.i);
			if (nextTokens != "OPAREN") {
				throw new Exception("Missing open parenthesis");
			}
			else {
				getNextToken(tokens, Rda.i);
				boolExpr();
				if (nextTokens != "CPAREN") {
					throw new Exception("Missing closing parenthesis");
				}
				else {
					getNextToken(tokens, Rda.i);
					if(nextTokens == "OBRACKET") {
						
						block();
					}
					else {
						getNextToken(tokens, Rda.i);
						stmt();
						
						if (nextTokens == "ELSE") {
							getNextToken(tokens, Rda.i);
							stmt();
							if(nextTokens == "SEMICOLON") {
								getNextToken(tokens, Rda.i);
							}
							else {
								if(nextTokens != "OBRACKET") {
									throw new Exception("missing block statement start");
								}
								else {
									getNextToken(tokens, Rda.i);
									block();
								}
							}
						}
						else if (nextTokens != "SEMICOLON")
							throw new Exception("missing semicolon2");
						else {
							getNextToken(tokens, i);
							block();
						}
					}
				}
			}
		}
		//System.out.println("exit ifstmt");
	}
	
	public static void block() throws Exception {
		//<BLOCK> --> `{` <STMT_LIST> `}`
		//System.out.println(nextTokens + " enter block");
		if (nextTokens != "OBRACKET") {
			throw new Exception("missing opening bracket");
		}
		else {
			getNextToken(tokens, Rda.i);
			stmtList();
			if (nextTokens == "EOF") {
				return;
			}
			else if (nextTokens != "SEMICOLON") {
				throw new Exception("semicolon");
			}
			else {
				getNextToken(tokens, Rda.i);
				stmtList();
				if (nextTokens == "CBRACKET") {
					getNextToken(tokens, Rda.i);
					stmtList();	
				}
				else if (nextTokens == "EOF") {
					return;
				}
				else {
					if (nextTokens == "SEMICOLON") {
						getNextToken(tokens, Rda.i);
						stmtList();
					}
					else {
						throw new Exception("missing close bracket");
					}
				}
			}
		}
		//System.out.println("exit block");
	}
	
	public static void expr() throws Exception {
		//<EXPR> --> <TERM> {(`+`|`-`) <TERM>}
		//System.out.println(nextTokens + " enter expr");
		term();
		while(nextTokens == "ADD_OP" || nextTokens == "SUB_OP" || nextTokens == "ASSIGNMENT_OP") {
			getNextToken(tokens, Rda.i);
			term();
		}
		//System.out.println("exit expr");
	}
	
	public static void term() throws Exception {
		//<TERM> --> <FACT> {(`*`|`/`|`%`) <FACT>}
		//System.out.println(nextTokens + " enter term");
		factor();
		while(nextTokens == "MUL_OP" || nextTokens == "DIV_OP" || nextTokens == "MOD_OP") {
			getNextToken(tokens, Rda.i);
			factor();
		}
		//System.out.println("exit term");
	}
	
	public static void factor() throws Exception {
		//<FACT> --> ID | INT_LIT | FLOAT_LIT | `(` <EXPR> `)`
		//System.out.println(nextTokens + " enter factor");
		if (nextTokens == "ID" || nextTokens == "INT" || nextTokens == "FLOAT") {
			getNextToken(tokens, Rda.i);
		}
		else {
			if (nextTokens == "OPAREN" ) {
				getNextToken(tokens, Rda.i);
				expr();
				if (nextTokens == "CPAREN" ) 
					getNextToken(tokens, Rda.i);
				else if (nextTokens == "NOTEQUAL_OP" || nextTokens == "EQUAL_OP") {
					getNextToken(tokens, Rda.i);
					expr();
				}
				else {
					System.out.println(nextTokens + " " + i);
					throw new Exception("missing closing parenthesis (factor)");
				}
			}
			else 
				throw new Exception("not an int, ID, float, or cparen ");
		}
		//System.out.println("exit factor");
	}
	
	public static void boolExpr() throws Exception {
		//<BOOL_EXPR> --> <BTERM> {(`>`|`<`|`>=`|`<=`) <BTERM>}
		//System.out.println(nextTokens + " enter boolexpr");
		bTerm();
		while (nextTokens == "GREATER_OP" || nextTokens == "LESS_OP" || nextTokens == "GREATEROREQUAL_OP" || nextTokens == "LESSOREQUAL") {
			getNextToken(tokens, Rda.i);
			bTerm();
		}
		//System.out.println(nextTokens + " exit boolexpr");

	}
	
	public static void bTerm() throws Exception {
		//<BTERM> --> <BAND> {(`==`|`!=`) <BAND>}
		//System.out.println(nextTokens+" enter bTerm");
		bAnd();
		while (nextTokens == "EQUAL_OP" || nextTokens == "NOTEQUAL_OP") {
			getNextToken(tokens, Rda.i);
			bAnd();
		}
		//System.out.println("exit bTerm");
	}
	
	public static void bAnd() throws Exception {
		//<BAND> --> <BOR> {`&&` <BOR>}
		//System.out.println(nextTokens+" enter bAnd");
		bOr();
		while (nextTokens == "AND_OP") {
			getNextToken(tokens, Rda.i);
			bOr();
		}
		//System.out.println("exit bAnd");
	}
	
	public static void bOr() throws Exception {
		//<BOR> --> <EXPR> {`&&` <EXPR>}
		//System.out.println(nextTokens+" enter bOr");
		expr();
		while (nextTokens == "OR_OP") {
			getNextToken(tokens, Rda.i);
			expr();
		}
		//System.out.println("exit bOr");
	}
	
	
}
