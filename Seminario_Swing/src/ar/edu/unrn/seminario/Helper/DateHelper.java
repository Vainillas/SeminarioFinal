package ar.edu.unrn.seminario.Helper;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
	private static Calendar calendarDate;
	private static Date today;

	public static  Date getDateFrom(int a, int m, int d) {
		calendarDate = Calendar.getInstance();
		
		calendarDate.set(a, m-1, d);
		
		today = calendarDate.getTime();
		
		return today;
	}
	public static String changeFormat(Date date) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");//para crear el formato dd//mm/yyyy
		return formato.format(date);
		
	}
	public static Date getDate() {
		calendarDate=Calendar.getInstance();
		 today = calendarDate.getTime();
		return today;
	}

	public static boolean getDateLess(Date previous,Date later) {
		return previous.before(later);
	}
	
	public static int getAmountOfDays(Date dateEntry,Date dateExit) {//obtiene los dias que hay entre dos fechas
		int days=0;
		while(DateHelper.getDateLess(dateEntry, dateExit)) {
				days++;
				dateEntry=DateHelper.addDays(dateEntry, 1);
		
		
	}
		return days;
	}
	public static Date addDays(Date date,int days) {
		calendarDate.setTime(date);
		calendarDate.add(Calendar.DATE, days);
		date=calendarDate.getTime();
		return date;
	}
	public static boolean getDateLess(Date date) {
		calendarDate.setTime(date);
		return Calendar.getInstance().before(calendarDate); //si la fecha es menor a la pasada como parametro ;
	
	}
	public static boolean equals(Date date1,Date date2) {
		boolean validacion=false;
		calendarDate.setTime(date1);
		Calendar fecha2=Calendar.getInstance();
		fecha2.setTime(date2);
		if(calendarDate.get(Calendar.DAY_OF_MONTH)==fecha2.get(Calendar.DAY_OF_MONTH)) {
			if(calendarDate.get(Calendar.MONTH)==fecha2.get(Calendar.MONTH)) {
				if(calendarDate.get(Calendar.YEAR)==fecha2.get(Calendar.YEAR)) {
					validacion=true;
				}
			}
		}
		return validacion;
	}
}