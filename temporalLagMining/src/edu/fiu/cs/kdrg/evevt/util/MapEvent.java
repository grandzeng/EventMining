package edu.fiu.cs.kdrg.evevt.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MapEvent {

	public static Object[] loadMap(String path) throws IOException {
		ArrayList<String> array = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = null;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.equals(""))
				continue;
			String[] temp = line.split(":", 2);
			int pos = Integer.parseInt(temp[0].trim());
			String[] type = temp[1].split("=", 2);
			array.add(type[1].trim());
		}

		return  array.toArray();
	}

}
