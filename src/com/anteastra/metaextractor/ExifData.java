package com.anteastra.metaextractor;

import java.util.HashMap;
import java.util.Map;

final class ExifData implements Data {
	    
	private Map<Integer, Tag> exifMap = new HashMap<Integer, Tag>();
	private static Map<Integer, String> stringTagMap= new HashMap<Integer, String>();
	
	static {
		stringTagMap.put(MetaDataHandler.EXIF_TAG_WIDTH, "ImageWidth");
		stringTagMap.put(MetaDataHandler.EXIF_TAG_HEIGHT, "ImageLength");
		stringTagMap.put(MetaDataHandler.EXIF_TAG_ORIENTATION, "Orientation");
		stringTagMap.put(MetaDataHandler.EXIF_TAG_MAKE, "Make");
		stringTagMap.put(MetaDataHandler.EXIF_TAG_MODEL, "Model");		
		stringTagMap.put(MetaDataHandler.EXIF_TAG_X_RESOLUTION, "XResolution");
		stringTagMap.put(MetaDataHandler.EXIF_TAG_Y_RESOLUTION, "YResolution");		
	}        
    
    public static String getTagName(Integer key) {
    	
    	return stringTagMap.get(key);
    }

    @Override
	public void addTag(Tag tag) {
		
    	exifMap.put(tag.getID(), tag);
	}
	
	@Override
	public Object getTagValue(Integer tagID) {
		
		Tag tag = exifMap.get(tagID);
		return tag!=null?tag.getValue():null;
	}

	@Override
	public Tag getTag(int tagID) {		
		
		return exifMap.get(tagID);
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		for (Tag tag: exifMap.values()){
			String tagName = stringTagMap.get(tag.getID());
			sb.append(tagName);
			sb.append(" - ");
			sb.append(tag.getValue());
			sb.append("\n");
		}
		
		String result = sb.toString();
		return result;
	}
}