/**
 * 
 */
package ca.datamagic.nexrad.main;

import java.io.File;
import java.net.URL;
import java.text.MessageFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Greg
 *
 */
public class Exporter {
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String userDir = System.getProperty("user.dir");
			System.out.println("userDir: " + userDir);
			
			String date = "2018/07/21";
			String site = "KLWX";
			String bucketURL = "http://noaa-nexrad-level2.s3.amazonaws.com";
			String dirListURL = bucketURL+ "/?prefix=" + date + "/" + site;
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(dirListURL);
			NodeList keys = document.getElementsByTagName("Key");
			
			for (int ii = keys.getLength() - 1, jj = 0; (ii > -1) && (jj < 5); ii--, jj++) {
				String key = getTextValue(keys.item(ii));
				System.out.println("key: " + key);
				
				int index = key.lastIndexOf('/');
				String fileName = key.substring(index + 1);
				System.out.println("fileName: " + fileName);
				
				/*
				String command = MessageFormat.format("C:/Users/Greg/Downloads/wct-4.1.0-win32/wct-4.1.0/wct-export.bat \"{0}/{1}\" \"{2}\" \"tif\" \"C:/Users/Greg/Downloads/wct-4.1.0-win32/wct-4.1.0/wctBatchConfig.xml\"", bucketURL, key, fileName);
				//call(["sh", "wct-4.0.1/wct-export", "%s/%s"%(bucketURL,file), "output", "shp", "wct-4.0.1/wctBatchConfig.xml"])
				System.out.println("command: " + command);
				
				Process process = Runtime.getRuntime().exec(command);
				int exitCode = process.waitFor();
				System.out.println("exitCode: " + exitCode);
				*/
				//java -mx256m -Djava.awt.headless=true -classpath "%WCT_HOME%dist\wct-4.1.0.jar" gov.noaa.ncdc.wct.export.WCTExportBatch %*
				//URL dataURL = new URL(MessageFormat.format("{0}/{1}", bucketURL, key));
				//File file = new File(fileName);
				//boolean checkForOpendap = false;
				//WCTExport exporter = new WCTExport();
				//exporter.exportData(dataURL, file, SupportedDataType.IMAGEFILE, checkForOpendap);
			}
		} catch (Throwable t) {
			System.out.println("Exception: " + t.getMessage());
			t.printStackTrace();
		}
	}

}
