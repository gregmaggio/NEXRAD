/**
 * 
 */
package ca.datamagic.nexrad.dto;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Calendar;

/**
 * @author Greg
 *
 */
public class DateDTO {
	private static final NumberFormat _yearFormat = new DecimalFormat("0000");
	private static final NumberFormat _monthFormat = new DecimalFormat("00");
	private static final NumberFormat _dayFormat = new DecimalFormat("00");
	private Integer _year = null;
	private Integer _month = null;
	private Integer _day = null;

	public DateDTO() {
		Calendar calendar = Calendar.getInstance();
		_year = calendar.get(Calendar.YEAR);
		_month = calendar.get(Calendar.MONTH) + 1;
		_day = calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public DateDTO(Calendar calendar) {
		_year = calendar.get(Calendar.YEAR);
		_month = calendar.get(Calendar.MONTH) + 1;
		_day = calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public DateDTO(int year, int month, int day) {
		_year = year;
		_month = month;
		_day = day;
	}
	
	public Integer getYear() {
		return _year;
	}
	
	public Integer getMonth() {
		return _month;
	}
	
	public Integer getDay() {
		return _day;
	}
	
	public void setYear(Integer newVal) {
		_year = newVal;
	}
	
	public void setMonth(Integer newVal) {
		_month = newVal;
	}
	
	public void setDay(Integer newVal) {
		_day = newVal;
	}
	
	@Override
	public String toString() {
		if ((_year != null) && (_month != null) && (_day != null)) {
			String year = _yearFormat.format(_year.intValue());
			String month = _monthFormat.format(_month.intValue());
			String day = _dayFormat.format(_day.intValue());
			return MessageFormat.format("{0}/{1}/{2}", year, month, day);
		}
		return null;
	}
}
