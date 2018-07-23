/**
 * 
 */
package ca.datamagic.nexrad.dto;

/**
 * @author Greg
 *
 */
public class SwaggerConfigurationDTO {
	private String _validatorUrl = "validatorUrl";
	private String _docExpansion = "none";
	private String _apisSorter = "alpha";
	private String _defaultModelRendering = "schema";
	private String[] _supportedSubmitMethods = new String[] { "get", "post", "put", "delete", "patch" };
	private boolean _jsonEditor = false;
	private boolean _showRequestHeaders = true;
	
	public String getValidatorUrl() {
		return _validatorUrl;
	}
	
	public String getDocExpansion() {
		return _docExpansion;
	}
	
	public String getApisSorter() {
		return _apisSorter;
	}
	
	public String getDefaultModelRendering() {
		return _defaultModelRendering;
	}
	
	public String[] getSupportedSubmitMethods() {
		return _supportedSubmitMethods;
	}
	
	public boolean isJsonEditor() {
		return _jsonEditor;
	}
	
	public boolean isShowRequestHeaders() {
		return _showRequestHeaders;
	}
	
	public void setValidatorUrl(String newVal) {
		_validatorUrl = newVal;
	}
	
	public void setDocExpansion(String newVal) {
		_docExpansion = newVal;
	}
	
	public void setApisSorter(String newVal) {
		_apisSorter = newVal;
	}
	
	public void setDefaultModelRendering(String newVal) {
		_defaultModelRendering = newVal;
	}
	
	public void setSupportedSubmitMethods(String[] newVal) {
		_supportedSubmitMethods = newVal;
	}
	
	public void setJsonEditor(boolean newVal) {
		_jsonEditor = newVal;
	}
	
	public void setShowRequestHeaders(boolean newVal) {
		_showRequestHeaders = newVal;
	}
}
