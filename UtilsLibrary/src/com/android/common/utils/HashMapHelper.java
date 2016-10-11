package com.android.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * 
 * @author zhang_xinyuan
 *
 */
public class HashMapHelper {
	public static String getValue(HashMap<String, String> hashMap,int index)
	{
		String value =null;
		Map map = new HashMap();   
		Iterator iter = hashMap.entrySet().iterator(); 
		int i=0;
		while (iter.hasNext()){       
			Map.Entry entry = (Map.Entry)iter.next();      
			Object key = entry.getKey();      
			Object val = entry.getValue();  
			String k = Integer.toString(i); 
			map.put(k, val);
			i++;
		}  
		String indx = Integer.toString(index); 
		value = map.get(indx).toString();
		return value;
	}
}
