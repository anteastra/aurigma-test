package com.anteastra.metaextractor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MetaDataHandler {
	private MetaData data;
	private boolean isParsedOK = true;
	private String errorMsg;
	
	public static final int EXIF_TAG_WIDTH = 0x0100;
    public static final int EXIF_TAG_HEIGHT = 0x0101;
    public static final int EXIF_TAG_ORIENTATION = 0x0112;
	public static final int EXIF_TAG_MAKE = 0x010F;
    public static final int EXIF_TAG_MODEL = 0x0110;    
    public static final int EXIF_TAG_X_RESOLUTION = 0x011A;
    public static final int EXIF_TAG_Y_RESOLUTION = 0x011B;
    public static final int EXIF_TAG_GPS_IFD = 0x8825;
    
    public static final int GPS_TAG_GPSTimeStamp = 0x7;

	public MetaDataHandler(File file) {
		data = new MetaData();	
        parseFile(file,data);
	}

	private void parseFile(File file, MetaData data) {
		
		try{
			BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			int SOImarker = readMagicNumber(inputStream);
			
			if ((SOImarker &  0xFFD8) != SOImarker){
				throw new IllegalArgumentException("It is not jpeg file");
			}
			
			byte[] app1 = readApp1Segment(inputStream);
			data.parseExifArray(app1);
		} catch (Exception e){
			isParsedOK = false;
			errorMsg = e.toString();
		}
	}

	private byte[] readApp1Segment(BufferedInputStream inputStream) throws IOException {
		boolean isApp1SegFound = false;		
		while(!isApp1SegFound){
			byte segMarker = (byte)(inputStream.read() & 0xFF);
			if ((segMarker&0xFF) == 0xFF){
				byte segID = (byte)(inputStream.read() & 0xFF);
				if ((segID&0xE1) == 0xE1) isApp1SegFound = true;
			}
		}
		int segSize = inputStream.read() << 8 | inputStream.read();
		segSize = segSize -2;
		byte[] app1Data = new byte[segSize];
		inputStream.read(app1Data);
		return app1Data;
	}

	private int readMagicNumber(BufferedInputStream inputStream) throws IOException {
		inputStream.mark(2);
		int result = inputStream.read() << 8 | inputStream.read();
		return result;
	}

	public String getExifTagName(int exifTag) {
		return ExifData.getTagName(exifTag);
	}

	public String getExifTagValue(int exifTag) {
		return data.getRepresetation(exifTag);
	}

	public String getGPSTagName(int gpsTag) {
		return GPSData.getTagName(gpsTag);
	}

	public String getGPSTagValue(int gpsTag) {
		return data.getGPSRepresetation(gpsTag);
	}

	public boolean isParsedOK() {
		return isParsedOK;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	
}
