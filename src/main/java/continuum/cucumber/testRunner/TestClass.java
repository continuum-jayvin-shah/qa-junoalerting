package continuum.cucumber.testRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TestClass {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		/*Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = TimeZone.getTimeZone("America/Los_Angeles");
		calendar.setTimeZone(timeZone);
		int date = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);

		int day = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int miliSecond = calendar.get(Calendar.MILLISECOND);

		String time =  */
				
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String formatted = format1.format(cal.getTime());
		System.out.println("Timeformatted : " + formatted);
		
		final Date currentParsed = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2011-08-23 14:59:26.662");
		final Date previousParsed = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2011-08-23 14:57:26.662");
		long difference = currentParsed.getTime() - previousParsed.getTime();
		System.out.println(difference);

		/*String startTime = "2017-07-15";
		String endTime = "2017-07-15";
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		Date d1 = sdf.parse(startTime);
		Date d2 = sdf.parse(endTime);
		long laps = d1.getTime() - d2.getTime();
		System.out.println(laps);

		String startTime1 = "2017-07-15";
		String endTime1 = "2017-07-15";
		SimpleDateFormat sdff = new SimpleDateFormat("HH:mm:ss");
		Date d11 = sdff.parse(startTime1);
		Date d22 = sdff.parse(endTime1);
		long laps1 = d11.getTime() - d22.getTime();
		System.out.println(laps1);*/
	}

}
