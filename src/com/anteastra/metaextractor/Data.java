package com.anteastra.metaextractor;

interface Data {

	public void addTag(Tag tag);

	public Object getTagValue(Integer tagID);

	public Tag getTag(int tagID);
}
