/**
 * 
 */
package ca.datamagic.nexrad.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.inject.Guice;
import com.google.inject.Injector;

import ca.datamagic.nexrad.dao.ExporterDAO;
import ca.datamagic.nexrad.dto.DateDTO;
import ca.datamagic.nexrad.dto.GeoTiffPostParameters;
import ca.datamagic.nexrad.dto.SwaggerConfigurationDTO;
import ca.datamagic.nexrad.dto.SwaggerResourceDTO;
import ca.datmagic.nexrad.inject.DAOModule;

/**
 * @author Greg
 *
 */
@Controller
@RequestMapping("")
public class IndexController {
	private static Logger _logger = LogManager.getLogger(IndexController.class);
	private static Injector _injector = null;
	private static ExporterDAO _exporterDAO = null;
	private static SwaggerConfigurationDTO _swaggerConfiguration = null;
	private static SwaggerResourceDTO[] _swaggerResources = null;
	private static String _swagger = null;
	
	static {
		FileInputStream swaggerStream = null;
		try {
			DefaultResourceLoader loader = new DefaultResourceLoader();
			Resource classResource = loader.getResource("");
		    Resource dataResource = loader.getResource("classpath:data");
		    Resource metaInfResource = loader.getResource("META-INF");
		    String classPath = classResource.getFile().getAbsolutePath().replace('\\', '/');
		    String dataPath = dataResource.getFile().getAbsolutePath().replace('\\', '/');
		    String metaInfPath = metaInfResource.getFile().getAbsolutePath().replace('\\', '/');
		    _logger.debug("classPath: " + classPath);
		    _logger.debug("dataPath: " + dataPath);
		    _logger.debug("metaInfPath: " + metaInfPath);
		    
		    ExporterDAO.setResourcePath(classPath);
		    System.setProperty("user.dir", dataPath);
		    String swaggerFileName = MessageFormat.format("{0}/swagger.json", metaInfPath);
		    swaggerStream = new FileInputStream(swaggerFileName);
		    _swagger = IOUtils.toString(swaggerStream, "UTF-8");
			_injector = Guice.createInjector(new DAOModule());
			_exporterDAO = _injector.getInstance(ExporterDAO.class);
			_swaggerConfiguration = new SwaggerConfigurationDTO();
			_swaggerResources = new SwaggerResourceDTO[] { new SwaggerResourceDTO() };
		} catch (Throwable t) {
			_logger.error("Exception", t);
		}
		if (swaggerStream != null) {
			IOUtils.closeQuietly(swaggerStream);
		}
	}
	
	@RequestMapping(value="/api/keys/{site}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
    public List<String> keys(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable String site) throws Exception {		
		try {
			_logger.debug("keys");
			_logger.debug("site: " + site);
			return _exporterDAO.getKeys(site);
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
    }
	
	@RequestMapping(value="/api/keys/{site}/{year}/{month}/{day}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
    public List<String> keys(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable String site, @PathVariable String year, @PathVariable String month, @PathVariable String day) throws Exception {		
		try {
			_logger.debug("keys");
			_logger.debug("site: " + site);
			_logger.debug("year: " + year);
			_logger.debug("month: " + month);
			_logger.debug("day: " + day);
			DateDTO date = new DateDTO(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
			return _exporterDAO.getKeys(site, date.toString());
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
    }
	
	@RequestMapping(value="/api/geotiff", method=RequestMethod.POST)
    public ResponseEntity<byte[]> geoTiff(Model model, HttpServletRequest request, HttpServletResponse response, @RequestBody GeoTiffPostParameters parameters) throws Exception {		
		try {
			_logger.debug("geoTiff");
			_logger.debug("key: " + parameters.getKey());
			byte[] bytes = _exporterDAO.getGeoTiff(parameters.getKey());
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(bytes);			
		} catch (Throwable t) {
			_logger.error("Exception", t);
			throw new Exception(t);
		}
    }
	
	@RequestMapping(value="/swagger-resources/configuration/ui", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public SwaggerConfigurationDTO getSwaggerConfigurationUI() {
		return _swaggerConfiguration;
	}
	
	@RequestMapping(value="/swagger-resources", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public SwaggerResourceDTO[] getSwaggerResources() {
		return _swaggerResources;
	}
	
	@RequestMapping(value="/v2/api-docs", method=RequestMethod.GET, produces="application/json")
	public void getSwagger(Writer responseWriter) throws IOException {
		responseWriter.write(_swagger);
	}
}
