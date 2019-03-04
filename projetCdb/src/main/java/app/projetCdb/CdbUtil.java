package app.projetCdb;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CdbUtil {

	/**
	 * Convert String date to LocalDate
	 * 
	 * @param date       : string representation of date
	 * @param formatter : format of string date given in parameter
	 * @return the localdate corresponding to date
	 * @throws ParseException if date and dateFormat don,t match
	 */
	public static LocalDate strDateToTimestamp(String date, DateTimeFormatter formatter) throws ParseException {
		return (LocalDate) formatter.parse(date);
	}
}
