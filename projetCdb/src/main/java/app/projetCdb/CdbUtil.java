package app.projetCdb;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CdbUtil {

	/**
	 * Convert date to timestamp
	 * 
	 * @param date       : string representation of date
	 * @param dateFormat : format of string date given in parameter
	 * @return the timestamp corresponding to date
	 * @throws ParseException if date and dateFormat don,t match
	 */
	public static Timestamp strDateToTimestamp(String date, SimpleDateFormat dateFormat) throws ParseException {
		Date parsedDate = dateFormat.parse(date);
		Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		return timestamp;
	}
}
