package com.marcelolopes.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	
	public static String decodeString(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		} 
	}

	public static List<Integer> decodeIntList(String s) {
//		String [] elementos = s.split(",");
//		List<Integer> decoded = new ArrayList<Integer>();
//		for(String e : elementos) {
//			decoded.add(Integer.parseInt(e));
//		}
//		return decoded;
		return Arrays.asList(s.split(",")).stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList());
	}
}
