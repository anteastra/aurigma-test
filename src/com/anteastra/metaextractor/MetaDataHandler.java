package com.anteastra.metaextractor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Public class for meta data extractor library.<br>
 * To use this class you should create instance of this class
 * and pass to constructor a File instance.<br>
 * <br>
 * To get common exif tag values use getExifTagName and getExifTagValue methods.<br>
 * <br>
 * To get gps tag values use getGPSTagName and getGPSTagValue methods.<br>
 * <br>
 * Also where are ID of tag as public static constants.
 * 
 * @author anteastra
 *
 */
public class MetaDataHandler {
	private MetaData data;
	private boolean isParsedOK = true;
	private String errorMsg;
	
	/**
	 * ImageWidth tag ID
	 */
	public static final int EXIF_TAG_WIDTH = 0x0100;
	
	/**
	 * ImageLength tag ID
	 */
    public static final int EXIF_TAG_HEIGHT = 0x0101;
    
    /**
     * Orientation tag ID
     */
    public static final int EXIF_TAG_ORIENTATION = 0x0112;
    
    /**
     * Make
     */
	public static final int EXIF_TAG_MAKE = 0x010F;
	
	/**
	 * Model tag ID
	 */
    public static final int EXIF_TAG_MODEL = 0x0110;
    
    /**
     * XResolution tag ID
     */
    public static final int EXIF_TAG_X_RESOLUTION = 0x011A;
    
    /**
     * YResolution tag ID
     */
    public static final int EXIF_TAG_Y_RESOLUTION = 0x011B;
    
    static final int EXIF_TAG_GPS_IFD = 0x8825;
        
    /**
     * GPSTimeStamp tag ID
     */
    public static final int GPS_TAG_GPSTimeStamp = 0x7;

    /**
     * File will be read during the creation of instance
     * @param file
     */
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
			inputStream.close();
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

	/**
	 * There can be used public static constants from MetaDataHanler class.<br>
	 * <br>
	 * Return the tag name from JEITA CP-3451 specification.
	 * @param exifTag 
	 * @return String or null 
	 */
	public String getExifTagName(int exifTag) {
		return ExifData.getTagName(exifTag);
	}
	
	/**
	 * There can be used public static constants from MetaDataHanler class.<br>
	 * <br>
	 * Return the tag value string representation.
	 * @param exifTag 
	 * @return String or null 
	 */
	public String getExifTagValue(int exifTag) {
		return data.getRepresetation(exifTag);
	}

	/**
	 * There can be used public static constants from MetaDataHanler class.<br>
	 * <br>
	 * Return the GPS tag name from JEITA CP-3451 specification.
	 * @param gpsTag 
	 * @return String or null 
	 */
	public String getGPSTagName(int gpsTag) {
		return GPSData.getTagName(gpsTag);
	}

	/**
	 * There can be used public static constants from MetaDataHanler class.<br>
	 * <br>
	 * Return the GPS tag value string representation.
	 * @param gpsTag 
	 * @return String or null 
	 */
	public String getGPSTagValue(int gpsTag) {
		return data.getGPSRepresetation(gpsTag);
	}

	/**
	 * 
	 * @return true, if file parsed successfully
	 */
	public boolean isParsedOK() {
		return isParsedOK;
	}

	/**
	 * Assumed that return value will be null if there was no error<br>
	 * and it will contain explanation of why where was error
	 * @return error message as String
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	
}
