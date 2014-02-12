package com.anteastra.test;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;
import com.anteastra.metaextractor.MetaDataHandler;

public class MetaDataHandlerTest {

	@Test
	public void testParsingGoodFile() {
		
		File f= new File("test_img/canon-ixus.jpg");
		MetaDataHandler mdh = new MetaDataHandler(f);
		assertTrue(mdh.isParsedOK());
	}
	
	@Test
	public void testParsingFailFile() {
		
		File f= new File("test_img/fail.jpg");
		MetaDataHandler mdh = new MetaDataHandler(f);
		assertFalse(mdh.isParsedOK());
	}
	
	@Test
	public void testGetModel() {
		
		File f= new File("test_img/canon-ixus.jpg");
		MetaDataHandler mdh = new MetaDataHandler(f);
		
		String model = mdh.getExifTagValue(MetaDataHandler.EXIF_TAG_MODEL);
		assertEquals("","Canon DIGITAL IXUS", model);
	}
	
	@Test
	public void testGetMake() {
		
		File f= new File("test_img/canon-ixus.jpg");
		MetaDataHandler mdh = new MetaDataHandler(f);
		
		String make = mdh.getExifTagValue(MetaDataHandler.EXIF_TAG_MAKE);
		assertEquals("", "Canon", make);
	}
	
	@Test
	public void testGetTagNameMake() {
		
		File f= new File("test_img/canon-ixus.jpg");
		MetaDataHandler mdh = new MetaDataHandler(f);
		
		String makeName = mdh.getExifTagName(MetaDataHandler.EXIF_TAG_MAKE);
		assertEquals("", "Make", makeName);
	}

}
