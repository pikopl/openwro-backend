/**
 * 
 */
package pl.pikopl.openwro.core.dataconverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

/**
 * @author kopajczy
 *
 */
public class CarParkDataConverter {
	
	public static List<Map<String, Object>> convertCsv(final String data) throws IOException {
		System.out.println("ENTER:> CarParkDataConverter:convertCsv");
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
		} catch(IOException e){
			System.out.println("ERROR:> CarParkDataConverter:convertCsv: " + e);
			throw e;
		}
		finally {
			if (mapReader != null) {
				mapReader.close();
			}
		}
		System.out.println("EXIT:> CarParkDataConverter:convertCsv:\n " + csvFileData);
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
