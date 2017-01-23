import java_cup.runtime.*;
import java.util.Vector;
import java.io.*;

%%
%class Lexer
%line
%column
%cup

value = ["\""]((-|\+)?[0-9]+([\.|,][0-9]+)?)["\""]  // nombre positif/negatif entier/flottant entre guillemets
param = [a-z]+[ ]*[=][ ]*{value}                    // id="12"
open = <[A-Z]+([ ]+{param})*>                       // ouvrante avec/sans paramètres : <A> / <A id="123">
close = (<[/][A-Z]+>)                               // fermante : </A>
selfclose = (<[A-Z]+[ ]*[/]>)                       // autofermante : <A/>
text = [^"\t\r\n <"]+                               // tous les caractères excepté les tabulations, retours à la ligne et les balises
text2 = <[^"\t\r\n <>"]+                            // idem qui commence par "<"

%%

{open}          { return new Symbol(sym.OPEN,       yytext()); }
{close}		    { return new Symbol(sym.CLOSE,      yytext()); }
{selfclose}     { return new Symbol(sym.SELFCLOSE,  yytext()); }
{text}		    { return new Symbol(sym.TEXT,       yytext()); }    // doit être dernier
{text2}		    { return new Symbol(sym.TEXT,       yytext()); }    // doit être dernier

/* -------------------------------------------------
	Caracteres non pris en compte
   ------------------------------------------------- */

[\ |\t|\n|\r|\r\n]                  {}
