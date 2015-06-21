/*
 * Grammer Rules for a small language that describes basic arthmetric
 * expressions:
 * 
 * expr     :=      number
 *          |       expr '+' expr
 *          |       expr '-' expr
 *          |       expr '*' expr
 *          |       expr '/' expr
 *          |       '(' expr ')'
 *          |       - expr
 * number   :=      digit+ ('.' digit+)?
 * digit    :=      '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9'
 * 
 * Three production rules define the grammer elements:
 *      - expr
 *      - number
 *      - digit
 * 
 * The following grammer will be used to build a simple command-line calculator.
 * First, we will need to translate the above EBNF grammer into JavaCC format.
 * 
 * USAGE:
 *      % javacc SimpleCalc1.jj
 *      % java SimpleCalc1.java
 *      % java SimpleCalc1
 */

options {
    LOOKAHEAD=2;
}

PARSER_BEGIN(LexicalAnalyser)

public class LexicalAnalyser {

    public static void main(String[] args) throws ParseException {
        LexicalAnalyser parser = new LexicalAnalyser(System.in);
        while (true) {
            parser.parseOneLine();
        }
    }

}

PARSER_END(LexicalAnalyser)

SKIP:
{
    " " | "\r" | "\t" | "\n"
}

TOKEN:
{
    < NUMBER: ( <DIGIT> ) + ( "." ( <DIGIT> )+ )? >
    |
    < DIGIT:  [ "0"-"9" ] >
    |
    < EOL: "\n" >
}

void parseOneLine():
{
    double a;
}
{
    a=expr() <EOL>  { System.out.println(a); }
    |
    <EOL>
    |
    <EOF>   { System.exit(-1); }
}

double expr():
{
    double a;
    double b;
}
{
    a=term()
    (
        "+" b=expr()    {a += b;}
        |
        "-" b=expr()    {a -= b;}
    )*
        { return a;}
}

double term():
{
    double a;
    double b;
}
{
    a=unary()
    (
        "*" b=term()    {a *= b;}
        |
        "/" b=term()    {a /= b;}
    )*
        {return a;}
}

double unary():
{
    double a;
}
{
    "-" a=element()     {return -a;}
    |
    a=element()         {return a;}
}

double element():
{
    Token t;
    double a;
}
{
    t=<NUMBER>          {return Double.parseDouble(t.toString()); }
    |
    "(" a=expr() ")"    {return a;}
}
