package app.projetCdb;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class CdbUtil {
	
	/**
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp strDateToTimestamp(String date,SimpleDateFormat dateFormat) throws ParseException {
		Date parsedDate = dateFormat.parse(date);
		 Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		 return timestamp;
	}
}
