/**
 * 
 */
package ca.datamagic.nexrad.dto;

/**
 * @author Greg
 *
 */
public class OSTypeDTO {
	private static final Object _lockObj = new Object();
	private static String _os = null;
	private static boolean _windows = false;
	private static boolean _mac = false;
	private static boolean _unix = false;
	private static boolean _solaris = false;
	
	static {
		synchronized (_lockObj) {
			_os = System.getProperty("os.name");
			if (_os != null) {
				_windows = (_os.toLowerCase().indexOf("win") > -1) ? true : false;
				_mac = (_os.toLowerCase().indexOf("mac") > -1) ? true : false;
				_unix = ((_os.toLowerCase().indexOf("nix") > -1) || (_os.indexOf("nux") > -1) || (_os.indexOf("aix") > -1)) ? true : false;
				_solaris = (_os.toLowerCase().indexOf("sunos") > -1) ? true : false;
			}
		}
	}
	
	public static String getOS() {
		return _os;
	}
	
	public static boolean isWindows() {
		return _windows;
	}
	
	public static boolean isMac() {
		return _mac;
	}
	
	public static boolean isUnix() {
		return _unix;
	}
	
	public static boolean isSolaris() {
		return _solaris;
	}	
}
