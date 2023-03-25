#include <stdio.h>
#include <ctype.h>
#include <string.h>

int charClass;
char lexeme[100];
char nextChar;
int lexLen;
int token;
int nextToken;
int floatFlag;
char tokenName[40];

int lookup(char c);
void addChar(void);
void getChar(void);
void getNonSpace(void);
int lex(void);
FILE *in_fp;

#define LETTER 0
#define DIGIT 1
#define PERIOD 2
#define UNKNOWN 99

#define INT_LIT 10
#define FLOAT_LIT 11
#define IDENT 12
#define ASSIGN_OP 20
#define EQUAL_OP 21
#define ADD_OP 22
#define SUB_OP 23
#define MUL_OP 24
#define DIV_OP 25
#define MOD_OP 26
#define LESS_OP 27
#define LESSOREQUAL_OP 28
#define GREATER_OP 29
#define GREATEROREQUAL_OP 30
#define AND_OP 31
#define OR_OP 32
#define LEFT_PAREN 33
#define RIGHT_PAREN 34

/*enter your text on a separate file titled file.txt in the same directory as this program, or change file.txt to your file's name.*/
int main(void) {
	if((in_fp = fopen("file.txt", "r")) == NULL)
		printf("ERROR - cannot open file");
	else {
		getChar();
        do {
            lex();
        }
        while (nextToken != EOF);
    }
	return 0;
}
/*  Addition: +
    Subtraction: -
    Multiplication: *
    Division: \
    Modulo: %
    Equals: ==
    Assignment: =
    Less Than: <
    Less Than or Equal: <=
    Greater Than: >
    Greater Than or Equal: >=
    Logical And: &&
    Logical Or: ||
    Left Parenthesis: (
    Right Parenthesis: )
    Int Literal: [0-9]+
    Float Literal: [0-9]+.[0-9]
*/

/* This function is utilized to match operands or parenthesis, as well as set the token name for each case */
int lookup(char ch) {
	switch (ch) {
		case '+':
			addChar();
			nextToken = ADD_OP;
			strcpy(tokenName, "ADD_OP");
			break;
		case '-':
			addChar();
			nextToken = SUB_OP;
			strcpy(tokenName, "SUB_OP");
			break;
		case '*':
			addChar();
			nextToken = MUL_OP;
			strcpy(tokenName, "MUL_OP");
			break;
		case '/':
			addChar();
			nextToken = DIV_OP;
			strcpy(tokenName, "DIV_OP");
			break;
		case '%':
			addChar();
			nextToken = MOD_OP;
			strcpy(tokenName, "MOD_OP");
			break;
		case '>':
			addChar();
			if ((nextChar = getc(in_fp)) == '=')
			{
				addChar();
				nextToken = GREATEROREQUAL_OP;
				strcpy(tokenName, "GREATEROREQUAL_OP");
				break;
			}
			else
			{
				nextToken = GREATER_OP;
				strcpy(tokenName, "GREATER_OP");
				break;
			}
		case '<':
			addChar();
			if ((nextChar = getc(in_fp)) == '=')
			{

				addChar();
				nextToken = LESSOREQUAL_OP;
				strcpy(tokenName, "LESSOREQUAL_OP");
				break;
			}
			else
			{
				nextToken = LESS_OP;
				strcpy(tokenName, "LESS_OP");
				break;
			}
		case '=':
			addChar();
			if ((nextChar = getc(in_fp)) == '=')
			{

				addChar();
				nextToken = EQUAL_OP;
				strcpy(tokenName, "EQUAL_OP");
				break;
			}
			else
			{
				nextToken = ASSIGN_OP;
				strcpy(tokenName, "ASSIGN_OP");
				break;
			}
		case '&':
			addChar();
			if ((nextChar = getc(in_fp)) == '&')
			{
				addChar();
				nextToken = AND_OP;
				strcpy(tokenName, "AND_OP");
				break;
			}
			else
			{
				break;
			}
		case '|':
			addChar();
			if ((nextChar = getc(in_fp)) == '|')
			{
				addChar();
				nextToken = OR_OP;
				strcpy(tokenName, "OR_OP");
				break;
			}
			else
			{
				break;
			}
		default:
			addChar();
			nextToken = EOF;
			strcpy(tokenName, "EOF");
			break;
	}
	return nextToken;
}

/* This function adds the current lexeme to the lexeme array */
void addChar(void) {
	if (lexLen <=98) {
		lexeme[lexLen++] = nextChar;
		lexeme[lexLen] = '\0';
	}
	else
	printf("ERROR - Lexeme is too long \n");
}

/* This function assigns the characters class; Letter, digit, or period, the latter of which is utilized for floating point literals */
void getChar(void) {
	if ((nextChar = getc(in_fp)) != EOF) {
		if (isalpha(nextChar))
			charClass = LETTER;
		else if (isdigit(nextChar))
			charClass = DIGIT;
		else if (nextChar == '.'){
            floatFlag = 1;
			charClass = PERIOD;
		}
		else
			charClass = UNKNOWN;
	}
	else
		charClass = EOF;
}

/* Fetches the next character until a non-whitespace character is encountered */
void getNonSpace(void) {
	while(isspace(nextChar)){
		getChar();
	}
}

/* This function fetches the next character and assigns it a character class and token identifier before printing the value */
int lex(void) {
	lexLen = 0;
	getNonSpace();
		switch(charClass) {
			case LETTER:
				addChar();
				getChar();
				while (charClass == LETTER || charClass == DIGIT) {
					addChar();
					getChar();
				}
				nextToken = IDENT;
				strcpy(tokenName, "IDENT");
				break;
			case DIGIT:
				addChar();
				getChar();
				while (charClass == DIGIT || charClass == PERIOD)
				{
					addChar();
					getChar();
				}
                    if(floatFlag == 1){
					nextToken = FLOAT_LIT;
					strcpy(tokenName, "FLOAT_LIT");
					floatFlag = 0;
                    }
					else{
                    nextToken = INT_LIT;
                    strcpy(tokenName, "INT_LIT");
					}
				break;
			case UNKNOWN:
				lookup(nextChar);
				getChar();
				break;
			case EOF:
				nextToken = EOF;
				lexeme[0] = 'E';
				lexeme[1] = 'O';
				lexeme[2] = 'F';
				lexeme[3] = '\0';
				strcpy(tokenName, "EOF");
				break;
		}
	printf("Token\{TYPE = %s, VALUE = '%s'\}\n", tokenName, lexeme);
	return nextToken;
}

