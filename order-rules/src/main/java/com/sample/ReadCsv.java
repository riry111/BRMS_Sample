package com.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.manager.CsvEntityManager;

public class ReadCsv {
	
	private ReadCsv() {}
	
	private static final String ENCODING = "UTF-8";	
	private static CsvEntityManager csvMgr;
	private static CsvConfig cfg ;
	
	private static void init() {
		System.out.println("create new CsvEntityManager");
    	cfg = new CsvConfig(',', '"', '"');
    	cfg.setIgnoreEmptyLines(true);	// 空白行無視
    	csvMgr = new CsvEntityManager(cfg);		
	}
	
	public static List<?> exec(Class<?> clazz, String filepath) throws IOException {
		
		if (csvMgr == null) {
			init();
		}
		
		File file = new File(filepath);
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file), ENCODING);

		// CSVから読み込み
		List<?> list = csvMgr.load(clazz).from(isr);

		return list;
	}


}
