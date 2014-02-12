package com.anteastra.metaextractor;

interface Data {

	/**
	 * Then tag is created, it should be memorized
	 * @param tag
	 */
	public void addTag(Tag tag);
	
	public Object getTagValue(Integer tagID);

	public Tag getTag(int tagID);
}
