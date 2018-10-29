
package br.com.eddydata.suporte;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateExtended {
    public static String dateExtended(Date data, boolean mostrar_diasemana) {
       String diaf = null;
       String mesf = null;
       String retorno = null;

       if (data == null) {
           return retorno;
       }

       Calendar calendar = new GregorianCalendar();
       calendar.setTime(data);
       int semana = calendar.get(Calendar.DAY_OF_WEEK);
       int mes = calendar.get(Calendar.MONTH) + 1;
       int dia = calendar.get(Calendar.DAY_OF_MONTH);
       int ano = calendar.get(Calendar.YEAR);

       // semana
       switch (semana) {
           case 1:
               diaf = "Domingo";
               break;
           case 2:
               diaf = "Segunda";
               break;
           case 3:
               diaf = "Ter�a";
               break;
           case 4:
               diaf = "Quarta";
               break;
           case 5:
               diaf = "Quinta";
               break;
           case 6:
               diaf = "Sexta";
               break;
           case 7:
               diaf = "S�bado";
               break;
       }
       // m�s
       switch (mes) {
           case 1:
               mesf = "Janeiro";
               break;
           case 2:
               mesf = "Fevereiro";
               break;
           case 3:
               mesf = "Mar�o";
               break;
           case 4:
               mesf = "Abril";
               break;
           case 5:
               mesf = "Maio";
               break;
           case 6:
               mesf = "Junho";
               break;
           case 7:
               mesf = "Julho";
               break;
           case 8:
               mesf = "Agosto";
               break;
           case 9:
               mesf = "Setembro";
               break;
           case 10:
               mesf = "Outubro";
               break;
           case 11:
               mesf = "Novembro";
               break;
           case 12:
               mesf = "Dezembro";
               break;
       }
       if (mostrar_diasemana) {
           retorno = diaf + ", " + dia + " de " + mesf + ", de " + ano;
       } else {
           retorno = dia + " de " + mesf + " de " + ano;
       }
       return retorno;
   }
}
