/**
 * 
 */
package ca.datamagic.nexrad.dto;

/**
 * @author Greg
 *
 */
public class SwaggerResourceDTO {
	private String _name = "default";
	private String _location = "/v2/api-docs";
	private String _swaggerVersion = "2.0";
	
	public String getName() {
		return _name;
	}
	
	public String getLocation() {
		return _location;
	}
	
	public String getSwaggerVersion() {
		return _swaggerVersion;
	}
	
	public void setName(String newVal) {
		_name = newVal;
	}
	
	public void setLocation(String newVal) {
		_location = newVal;
	}
	
	public void setSwaggerVersion(String newVal) {
		_swaggerVersion = newVal;
	}
}
