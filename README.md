# Compilers
Compilers Assingments

#Compilers Assignments for 2014

##Lexical Analyser

Case is insignficant in simpL. The lexical analyser should recognise the following tokens
* **keywords:** ```and, bool, const, do, else, false, if, int, main, not, or, real, return, string, then, true, var, void, while```

* **identifiers:** Any other string of letters, digits or underscore character ('_') beginning with a letter.

* **strings:** As in C string are delimited by double quotes. Strings may contain any alphanumeric found on a standard keyboard or punctuation characters listed below. Strings may contain quotes, backslashes or newlines only if escaped by a backslash.

* **numbers:** A string of (decimal) digits. Real numbers are represented by a string of digits, a period character "." and a string of digits. Examples of valid numbers are 123, 0.123 and 1.23. Numbers such as 123. and .123 are invalid.

* **operators, relations and punctuation marks:** ```+  -  *  /  %  =  !=  <  >  <=  >=  (  )  {   }   ,  ;  :  .  :=  ?  !```

Comments in simpL can appear between any two tokens. There are two forms of comment: one is delimited by "/*" and "*/" and can be nested; the other begins with -- and is delimited by the end of line and this type of comments may not be nested.

###Submission

You should submit, by email to David.Sinclair@computing.dcu.ie, all the source files (JavaCC file and other files which were used to generate your parser) in a Winzip file along with a brief description of each of the major components of your lexiscal analyser describing how you implemented them and justifying why you implemented them in the way you did, by 10am on Monday 17th November. The assignment carries 10 marks and **late submissions will incur a 1 mark penalty for each 24 hours after the submission.**

