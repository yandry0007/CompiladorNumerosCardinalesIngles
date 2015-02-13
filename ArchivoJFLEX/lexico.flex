package archivojflex;
/*----------Seccion de importacion de librerias de java------------------------*/
import java_cup.runtime.*;
import java.io.Reader;
				//inicio de opciones
%% 	
/* ---------Seccion de opciones y declaraciones de JFlex -------------------- */
				//Nonbre de la clase q genera el analizador lexico
%class AnalizadorLexico
%public
				//Activar el contador de lineas, variable yyline
				//Activar el contador de columna, variable yycolumn
%line
%column
				//Activamos la compatibilidad con Java CUP
				//Para analizadores sintacticos(parser)
%cup
				//El codigo entre %{ y %} sera copiado integramente en el analizador generado.
%{
	public static String failLexico="";
				//Generamos un java_cup.Symbol para guardar el tipo de token encontrado
	private Symbol symbol(int type){
		return new Symbol(type, yyline, yycolumn);
	}
				//Generamos un Symbol para el tipo de token encontrado junto con su valor
	private Symbol symbol(int type, Object value){
		return new Symbol(type, yyline, yycolumn, value);
	}   
%}
/* ---------Seccion donde declaramos expresiones regulares -----------------*/
				//Un salto de linea es un \n, \r o \r\n dependiendo del SO
Salto = \r|\n|\r\n
				//Espacio es un espacio en blanco, tabulador \t, salto de linea o avance de pagina \f
Espacio     = {Salto} | [ \t\f]
				// Un digito del 1 al 9 seguido de 0 o mas digitos del 0 al 9 
Entero = [0-9][0-9]*
%%
				//Fin de opciones
				
 /*---------Seccion contiene expresiones regulares y acciones----------------*/
<YYINITIAL> {
	//YYINITIAL es el estado inicial del analizador lexico al escanear
	//expresion 	==>		accion
	";" {return symbol(sym.PC);}
	//Regresa que el token TRADUCIR declarado en la clase sym fue encontrado. */
	"traducir" {return symbol(sym.TRADUCIR);}
	//Regresa que el token NUM declarado en la clase sym fue encontrado.
	{Entero} {return new Symbol(sym.NUM, new Integer(yytext())); }
	{Espacio}       { /* ignora el espacio */ } 
}
	//Si el token contenido en la entrada no coincide con ninguna regla entonces se marca un token ilegal
[^]                    { 
System.err.println("CARACTER DESCONOCIDO: " + " << "+ yytext() +" >>"+"["+ yyline +":"+ yycolumn + "]" );
failLexico = "CARACTER DESCONOCIDO: " + " << "+ yytext() +" >>"+"["+ yyline +":"+ yycolumn + "]";
}