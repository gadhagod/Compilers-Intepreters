
/**
* Scanner for XML
*
* @author  Aarav Borthakur
* @version 9/12/23
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
    Tag.assertTagsClosed();
    return "END";
%eofval}

/**
 * Pattern definitions
 */
Anything        = [\s\S]*
LineTerminator  = \r|\n|\r\n
WhiteSpace      = [ \t\f]
Letter          = [a-zA-Z] | "_"
Digit           = \d
Identifier      = {Letter}({Letter} | {Digit})*
CommentOpener   = "<!--"
CommentCloser   = "-->"
QuestionMark    = "?"
Dot             = "."
VersionString   = "\""{Digit}(({Dot}{Digit}+) | "")"\""
EncodingString  = "\""{Identifier}*"-"*{Identifier}*"\""

%%
/**
 * lexical rules
 */
{CommentOpener}{Anything}{CommentCloser}	    {return "Comment";}
"<"{WhiteSpace}*{Identifier}{WhiteSpace}*">"	{return "Open " + (new Tag(yytext(), true)).getTagName();}
"</"{WhiteSpace}*{Identifier}{WhiteSpace}*">"	{return "Close " + (new Tag(yytext(), false)).getTagName();}
"<?xml"{WhiteSpace}+"version="{VersionString}{WhiteSpace}+"encoding="{EncodingString}{WhiteSpace}*"?>"	{return "Metadata";}
. | {WhiteSpace} | {LineTerminator}		        {/*do nothing*/}