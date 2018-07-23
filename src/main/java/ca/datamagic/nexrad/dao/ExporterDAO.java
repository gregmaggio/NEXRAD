/**
 * 
 */
package ca.datamagic.nexrad.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ca.datamagic.nexrad.dto.DateDTO;
import ca.datamagic.nexrad.dto.OSTypeDTO;
import ca.datmagic.nexrad.inject.Performance;
import ca.datmagic.nexrad.inject.Retry;

/**
 * @author Greg
 *
 */
public class ExporterDAO {
	private static final Logger _logger = LogManager.getLogger(ExporterDAO.class);
	private static final String _bucketURL = "http://noaa-nexrad-level2.s3.amazonaws.com";
	private static String _resourcePath = "C:/Dev/Applications/NEXRAD/src/test/resources";

	public static void setResourcePath(String newVal) {
		_resourcePath = newVal;
	}

	@Performance
	@Retry
	public List<String> getKeys(String site) throws ParserConfigurationException, SAXException, IOException {
		return getKeys(site, (new DateDTO()).toString());
	}
	
	@Performance
	@Retry
	public List<String> getKeys(String site, String date) throws ParserConfigurationException, SAXException, IOException {
		List<String> keys = new ArrayList<String>();
		String dirListURL = MessageFormat.format("{0}/?prefix={1}/{2}", _bucketURL, date, site);
		_logger.debug("dirListURL: " + dirListURL);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(dirListURL);
		NodeList keyList = document.getElementsByTagName("Key");
		
		for (int ii = 0; ii < keyList.getLength(); ii++) {
			String key = getTextValue(keyList.item(ii));
			keys.add(key);
		}
		
		return keys;
	}
	
	@Performance
	@Retry
	public byte[] getGeoTiff(String key) throws IOException, InterruptedException {
		File[] files = null;
		FileInputStream input = null;
		try {
			_logger.debug("key: " + key);
			
			String type = "tif";
			_logger.debug("type: " + type);
			
			int index = key.lastIndexOf('/');
			final String fileName = key.substring(index + 1);
			_logger.debug("fileName: " + fileName);
			
			String command = null;
			if (OSTypeDTO.isWindows()) {
				command = MessageFormat.format("{0}/wct-4.1.0-win32/wct-export.bat \"{1}/{2}\" \"{3}\" \"{4}\" \"{0}/wct-4.1.0-win32/wctBatchConfig.xml\"", _resourcePath, _bucketURL, key, fileName, type);
			} else if (OSTypeDTO.isUnix()) {
				command = MessageFormat.format("sh {0}/wct-4.1.0-linux/wct-export \"{1}/{2}\" \"{3}\" \"{4}\" \"{0}/wct-4.1.0-linux/wctBatchConfig.xml\"", _resourcePath, _bucketURL, key, fileName, type);
			}
			
			if (command != null) {
				_logger.debug("command: " + command);
				
				Process process = Runtime.getRuntime().exec(command);
				int exitCode = process.waitFor();
				_logger.debug("exitCode: " + exitCode);
				
				File dir = new File(".");
				files = dir.listFiles(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
							return name.toLowerCase().contains(fileName.toLowerCase());
						}
					}
				);
				for (int ii = 0; ii < files.length; ii++) {
					_logger.debug("file: " + files[ii].getAbsolutePath());
				}
				if (files.length > 0) {
					input = new FileInputStream(files[0]);
					return IOUtils.toByteArray(input);
				}
			}
			
			return null;
		} finally {
			if (input != null) {
				IOUtils.closeQuietly(input);
			}
			if (files != null) {
				for (int ii = 0; ii < files.length; ii++) {
					files[ii].delete();
				}
			}
		}
	}
	
	private static String getTextValue(Node node) {
		if (node.hasChildNodes()) {
			NodeList childNodes = node.getChildNodes();
			for (int ii = 0; ii < childNodes.getLength(); ii++) {
				Node child = childNodes.item(ii);
				if (child.getNodeType() == Node.TEXT_NODE) {
					return child.getNodeValue();
				}
			}
		}
		return null;
	}
}
