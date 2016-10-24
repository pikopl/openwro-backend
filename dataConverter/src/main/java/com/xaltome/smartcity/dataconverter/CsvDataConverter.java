/**
 * 
 */
package com.xaltome.smartcity.dataconverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

/**
 * @author kopajczy
 *
 */
public class CsvDataConverter {
	
	protected static final Logger LOGGER = Logger.getLogger(CsvDataConverter.class);
	
	public static List<Map<String, Object>> convert(final String data) throws IOException {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering convert(%s)", data);
		} else {
			LOGGER.info("Entering convert()");
		}
		List<Map<String, Object>> csvFileData = new LinkedList<Map<String, Object>>();
		Reader bufferedReader = new StringReader(data);
		ICsvMapReader mapReader = new CsvMapReader(bufferedReader, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
		String[] csvHeaders;
		try {
			// the header columns are used as the keys to the Map
			csvHeaders = mapReader.getHeader(true);
			final CellProcessor[] processors = getOptionalProcessorForEveryCol(csvHeaders.length);
			// load each row to a map
			Map<String, Object> record;
			while ((record = mapReader.read(csvHeaders, processors)) != null) {
				csvFileData.add(record);
			}
		}finally {
			if (mapReader != null) {
				mapReader.close();
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.tracef("Leaving convert(): %s", csvFileData);
			} else {
				LOGGER.info("Leaving convert()");
			}
		}
		return csvFileData;
	}
	
	/**
	 * Cell processors are an integral part of reading and writing with Super
	 * CSV - they automate the data type conversions, and enforce constraints.
	 * 
	 * More about CellProcessors can be found in SuperCSV documentation.
	 * 
	 * @param colsNumber
	 *            number of columns in each row of CSV file
	 * @return array with cells processors (according to 'colsNumber')
	 */
	private static CellProcessor[] getOptionalProcessorForEveryCol(Integer colsNumber) {
		CellProcessor[] processors = new CellProcessor[colsNumber];

		for (int i = 0; i < processors.length; i++) {
			processors[i] = new Optional();
		}

		return processors;
	}
}
