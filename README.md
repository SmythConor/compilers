# Compilers
Compilers Assingments

#Compilers Assignments for 2014

##Lexical & Syntax Analyser

* The aim of this assignment is to implement a lexical and syntax analyser using JavaCC for a simple language called basicL.
* Case is insignificant in basicL.
* The lexical analyser should recognise the following tokens:
  * **keywords**: ```and, bool, const, do, else, false, if, int, main, not, or, return, then, true, var, void, while, begin, end```
	* **identifiers**: ```Any other string of letters, digits or underscore character ('_') beginning with a letter```
	* **numbers:**: ```A string of (decimal) digits. Integer numbers are represented by a string of digits```
	* **operators and relations**: ```+  -  *  /  =  !=  <  >  <=  >=  (  )  ,  ;  :  :=```
* Comments in basicL can appear between any two tokens. There are two forms of comment: one is delimited by "/*" and "*/" and can be nested; the other begins with -- and is delimited by the end of line and this type of comments may not be nested.
* Tokens are represented by the Arial font. Capitalised Arial font represents keywords. Italicised font represents non-terminals. Bold parentheses ( ) are used for grouping. Bold curly braces { } indicate optional items. The vertical bar | represents alternation ("or"). A Kleene star * indicates that the preceding item can be repeated zero or more times. Epsilon ε  denotes the empty string.

```
program := ( decl )*
           ( function )*
					 main_prog
```
```
decl := ( var_decl | const_decl )
```
```
var_decl := VAR ident_list:type ( , ident_list:type )* ;
```
```
const_decl := CONST identifier:type = expression ( , identifier:type = expression )* ;
```
```
function := type identifier (param_list)
            BEGIN
					  ( decl )*
						( statement ; )*
				    RETURN ( expression | ε  ) ;
						END
```
```
param_list := ( identifier:type ( , identifier:type )* | ε  )
```
```
type := INT | BOOL | VOID
```
```
main_prog := MAIN
             BEGIN
						 ( decl )* 
						 ( statement ; )* 
						 END
```
```
statement := identifier := expression
          | identifier ( arg_list )
				  | BEGIN ( statement ; )* END
				  | IF condition THEN statement ; ELSE statement
					| WHILE condition DO statement
					| ε 
```
```
expression := fragment ( ( + | - | * | / )  fragment )*
           | ( expression ) 
					 | identifier ( arg_list )
```
```
fragment := identifier | TRUE | FALSE | number | ( + | - ) fragment | expression
```
```
condition :=   NOT condition
          | expression ( = | != | < | > | <= | >= ) expression
					| condition (AND | OR) condition
					| identifier ( arg_list )
```
```
ident_list := identifier ( , identifier )*
```
```
arg_list := ( identifier ( , identifier )* | ε )
```

###Submission

This is an individual assignment. You should submit, by email to David.Sinclair@computing.dcu.ie, all the source files (JavaCC file and other files which were used to generate your parser) in a Winzip file along with a declaration that this is solely your own work (except elements that are explicitly attributed to another source), a brief description of each of the major components of your lexical and syntax analyser that describes how you implemented them, by **10am on Monday 16th November 2015.**

Submissions without the declaration of that the assignment is the student's own work will not be assessed. The assignment carries 15 marks and **late submissions will incur a 1.5 mark penalty for each 24 hours after the submission.**
