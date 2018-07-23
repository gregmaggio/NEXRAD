/**
 * 
 */
package ca.datamagic.nexrad.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Greg
 *
 */
public class ExporterDAOTester {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DOMConfigurator.configure("src/test/resources/META-INF/log4j.cfg.xml");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test1() throws Exception {
		ExporterDAO dao = new ExporterDAO();
		List<String> keys = dao.getKeys("KLWX");
		for (int ii = keys.size() - 1, jj = 0; (ii > -1) && (jj < 5); ii--, jj++) {
			//List<String> fileNames = dao.export(keys.get(ii), "tif");
		}
	}

}
