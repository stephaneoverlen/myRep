import java_cup.runtime.*;
import java.util.Vector;
import java.io.*;

%%
%class Lexer
%line
%column
%cup

value = ["\""](-?[0-9]+([\.|,][0-9]+)?)["\""]   // int/float positif/negatif entre guillemets
cond = [a-z]+[ ]*[=][=][ ]*{value}              // id=="12"
param = [a-z]+[ ]*[=][ ]*{value}                // id="12"
myIf = <#[i][f][ ]+{cond}>                      // <#if id=="12">
myEndif = <#[i][f][ ]*[/]>                      // <#if/>
set = <#set[ ]+{param}[/]>                      // exemple : <#set id="12">
open = <[A-Z]+([ ]+{param})*>                   // ouvrante avec/sans paramètres : <A> / <A id="123">
close = (<[/][A-Z]+>)                           // fermante : </A>
selfclose = (<[A-Z]+[ ]*[/]>)                   // autofermante : </C>
text = [^"\t\r\n <"]+                           // tous les caractères excepté les tabulations, retours à la ligne et les balises
text2 = <[^"\t\r\n <>"]+                        // idem qui commence par "<"

%%

{set}           { return new Symbol(sym.SET,        yytext()); }
{open}          { return new Symbol(sym.OPEN,       yytext()); }
{myIf}          { return new Symbol(sym.MYIF,       yytext()); }
{myEndif}       { return new Symbol(sym.MYENDIF,    yytext()); }
{close}		    { return new Symbol(sym.CLOSE,      yytext()); }
{selfclose}     { return new Symbol(sym.SELFCLOSE,  yytext()); }
{text}		    { return new Symbol(sym.TEXT,       yytext()); }    // doit être en dernier
{text2}		    { return new Symbol(sym.TEXT,       yytext()); }    // doit être en dernier

/* -------------------------------------------------
	Caracteres non pris en compte
   ------------------------------------------------- */

[\ |\t|\n|\r|\r\n]                  {}
