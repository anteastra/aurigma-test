package com.anteastra.metaextractor;

import java.util.HashMap;
import java.util.Map;

class GPSData extends AbstractData {
	
    private Map<Integer, Tag> gpsMap = new HashMap<Integer, Tag>();
	private static Map<Integer, String> stringTagMap= new HashMap<Integer, String>();
	
	static{
		stringTagMap.put(MetaDataHandler.GPS_TAG_GPSTimeStamp, "GPSTimeStamp");
	}        
    	
    public static String getTagName(Integer key){
    	return stringTagMap.get(key);
    }
	
	@Override
	public void addTag(Tag tag) {
		gpsMap.put(tag.getID(), tag);
	}

	@Override
	public Object getTagValue(Integer tagID) {
		Tag tag = gpsMap.get(tagID);
		return tag!=null?tag.getValue():null;
	}

	@Override
	public Tag getTag(int tagID) {
		return gpsMap.get(tagID);
	}

}
