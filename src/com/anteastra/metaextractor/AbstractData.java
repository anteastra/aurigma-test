package com.anteastra.metaextractor;

abstract class AbstractData {

	public abstract void addTag(Tag tag);

	public abstract Object getTagValue(Integer tagID);

	public abstract Tag getTag(int tagID);
}
