
/**
* This file defines a simple lexer for the compilers course 2014-2015
*
* @author  Aarav Borthakur
* @version 9/7/23
* 
*/
import java.io.*;

%%
/* lexical functions */
/* specify that the class will be called Scanner and the function to get the next
 * token is called nextToken.  
 */
%class Scannerabb
%unicode
%line
%column
%public
%function nextToken
/*  return String objects - the actual lexemes */
/*  returns the String "END: at end of file */
%type String
%eofval{
    return "END";
%eofval}

/**
 * Pattern definitions
 */
LineTerminator  = \r|\n|\r\n
WhiteSpace      = {LineTerminator} | [ \t\f]
Letter          = [a-zA-Z]
Digit           = \d
Identifier      = {Letter}({Letter} | {Digit})*
Quote           = "\""
Attribute       = ({Identifier}=({Digit} | ({Quote}{Identifier}{Quote})))|{Identifier}
CommentOpener   = "<!--"
CommentCloser   = "-->"
Anything        = [\s\S]*
 
%%
/**
 * lexical rules
 */
{CommentOpener}{Anything}{CommentCloser}	                        {return "Comment";}
"<"{Identifier}{WhiteSpace}*({Attribute}|({Attribute}{WhiteSpace}*)*)">"	                        {return "Open " + (new Tag(yytext())).getTagName();}
"</"{WhiteSpace}*{Identifier}{WhiteSpace}*">"	                                                {return "Close " + (new Tag(yytext())).getTagName();}
{WhiteSpace}		        {}
.			{ /* do nothing */ }