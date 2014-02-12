package com.anteastra.metaextractor;

abstract class Tag {

	protected int tagID;
	protected TagType tagType;
	protected int tagCount;
	protected int offsetValue;
	protected int tagOffset;
	
	protected Object value;
	protected double[] valueArr;
	protected boolean isArray = false;
	
	public Tag(ParseHelper helper, int offSet){
		tagID = helper.getUInt16(offSet);
		short tmpTagType = (short) helper.getUInt16(offSet+2);
		tagCount = helper.getInt32(offSet+4);
		offsetValue = helper.getInt32(offSet+8);
		tagType = TagType.findTeg(tmpTagType);
		
		int byteCount = tagCount*tagType.getSize();
		if (byteCount>4){
			tagOffset = offsetValue+6;
		}else{
			tagOffset = offSet + 8;
		}		
		
		updateValue(helper);
	}
	
	private void updateValue(ParseHelper helper){		
		
		switch(tagType){
			case ASCII:
				value = helper.getString(tagOffset, tagCount).trim();
				break;
			case LONG:
				value = helper.getInt32(tagOffset);
				break;
			case SHORT:
				value = helper.getInt16(tagOffset);
				break;
			case RATIONAL:
				if (tagCount==1){
					value = (double)helper.getInt32(tagOffset)/helper.getInt32(tagOffset+4);
				}else{
					valueArr = new double[tagCount];
					isArray = true;
					for (int i=0; i < tagCount; i++){
						valueArr[i] = (double)helper.getInt32(tagOffset+i*tagType.getSize())
								/helper.getInt32(tagOffset+4+i*tagType.getSize());
					}
				}
				break;
			default:{};
		}
	}

	public Object getValue() {
		if (isArray) return valueArr;
		return value;
	}
	
	public abstract String getStringRepresetation();
	

	public Integer getID() {		
		return (int) tagID;
	}
}

