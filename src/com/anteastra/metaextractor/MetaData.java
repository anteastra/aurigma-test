package com.anteastra.metaextractor;

final class MetaData {
	
	ExifData exifData = new ExifData();
	GPSData gpsData = new GPSData();
	
	byte[] segmentData = null;
	
	public String toString(){
		return super.toString();		
	}

	public void setExifArray(byte[] app1Segment) {
		segmentData = app1Segment;
	}

	public void parseExifArray(byte[] app1Segment) {
				
		ParseHelper helper = new ParseHelper(app1Segment);
		int headerIndex = 0;
		if (!checkHeaderExif(app1Segment)) {
			return;
		}
		headerIndex = headerIndex+6;
		
		String byteOrderString = helper.getString(headerIndex, 2);
		
		if ("II".equals(byteOrderString)) {
			helper.setStraightByteOrder(true);
        } else {            
        	helper.setStraightByteOrder(false);
        }
				
		int firstDirectoryOffset = helper.getInt32(4 + headerIndex) + headerIndex;
		parseDirectory(exifData, helper, firstDirectoryOffset);
	}
	
	private void parseDirectory(Data data, ParseHelper helper, int firstDirectoryOffset){
		int dirTagCount = helper.getUInt16(firstDirectoryOffset);
		
		for (int i=0; i< dirTagCount; i++){
			int tagOffset = firstDirectoryOffset+2+i*12;
			int tagID = helper.getUInt16(tagOffset);
			if (tagID == MetaDataHandler.EXIF_TAG_GPS_IFD){
				int subDirOffset = 6 + helper.getInt32(tagOffset+8);
				parseDirectory(gpsData, helper, subDirOffset);
			}else{
				Tag tag;
				if (data == gpsData) {
					tag= new GPSTag(helper, tagOffset);
				}
				else {
					tag = new ExifTag(helper, tagOffset);
				}
				data.addTag(tag);
			}			
		}
	}

	private boolean checkHeaderExif(byte[] app1Segment) {		
		return (new String(app1Segment, 0, 6).equals("Exif\0\0"));
	}
	
	public String getRepresetation(int tagID) {
		Tag tag = exifData.getTag(tagID);
		return tag==null?null:tag.getStringRepresetation();
	}
	
	public String getGPSRepresetation(int tagID) {
		Tag tag = gpsData.getTag(tagID);
		return tag==null?null:tag.getStringRepresetation();
	}
	
}
