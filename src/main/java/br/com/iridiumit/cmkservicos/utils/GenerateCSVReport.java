package br.com.iridiumit.cmkservicos.utils;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;

import br.com.iridiumit.cmkservicos.modelos.Usuario;

import java.io.PrintWriter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateCSVReport {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(GenerateCSVReport.class);

	    @SuppressWarnings({ "rawtypes", "unchecked" })
		public static void writeUsers(PrintWriter writer, List<Usuario> users)  {

	        try {

	            ColumnPositionMappingStrategy mapStrategy
	                    = new ColumnPositionMappingStrategy();

	            mapStrategy.setType(Usuario.class);
	           

	            String[] columns = new String[]{"id", "cpf", "nome", "email", "telefone1", "login"};
	            mapStrategy.setColumnMapping(columns);
	            mapStrategy.generateHeader(columns);
	            StatefulBeanToCsv btcsv = new StatefulBeanToCsvBuilder(writer)
	                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
	                    .withMappingStrategy(mapStrategy)
	                    .withSeparator(',')
	                    .build();

	            btcsv.write(users);

	        } catch (CsvException ex) {

	            LOGGER.error("Error mapping Bean to CSV", ex);
	        }
	    }

	    @SuppressWarnings({ "rawtypes", "unchecked" })
		public static void writeUser(PrintWriter writer, Usuario user) {

	        try {

	            ColumnPositionMappingStrategy mapStrategy
	                    = new ColumnPositionMappingStrategy();

	            mapStrategy.setType(Usuario.class);

	            String[] columns = new String[]{"id", "cpf", "nome", "email", "telefone1", "login"};
	            mapStrategy.setColumnMapping(columns);

	            StatefulBeanToCsv btcsv = new StatefulBeanToCsvBuilder(writer)
	                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
	                    .withMappingStrategy(mapStrategy)
	                    .withSeparator(',')
	                    .build();

	            btcsv.write(user);

	        } catch (CsvException ex) {

	            LOGGER.error("Error mapping Bean to CSV", ex);
	        }
	    }
}
