package br.ufpi.dadosabertosapi.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryUtil {
	

	
	public static String[] getArrayAliases(String query){
		
		String itensJuntos =  query.split("(?i)from")[0].split("(?i)select")[1];
		String[] itens = separateByComma(itensJuntos);
		String[] itensReturn = new String[itens.length];
		
		int count = 0;
		for(String item : itens){
			String[] separated = item.split(" (?i)as ");
			itensReturn[count] = separateWords(separated[separated.length-1].trim());
			count++;
		}
		
		return itensReturn;
	}
	
	private static String[] separateByComma(String text) {

		int first = 0;
		List<String> list = new ArrayList<>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(int i = 0; i < text.length(); i++) {
			
			if(text.charAt(i) == ',' || i == text.length()-1) {
				
				if(containsValueMaiorZero(map)) {
					continue;
				}
				
				list.add(text.substring(first,i));
				first = i+1;
				continue;
			}
			String key = String.valueOf(text.charAt(i));
			
			if(text.charAt(i) == '(') {
				map.put("()", (map.get("()")==null?1:(map.get("()") +1) ));
			}
			if(text.charAt(i) == ')') {
				map.put("()", (map.get("()")==null?0:(map.get("()") -1) ));
			}
			
		}
		
		
		return list.toArray(new String[list.size()]);
	}
	
	private static boolean containsValueMaiorZero(Map<String, Integer> map) {
		boolean ret = false;
		
		for(Integer v : map.values()) {
			if(v > 0) {
				ret = true;
			}
		}
		
		return ret;
		
	}

	private static String separateWords(String words){
		return String.join(" ", words.split("(?=\\p{Upper})"));
	}
	
}

