package com.anteastra.metaextractor;

final class GPSTag extends Tag{
	
	public GPSTag(ParseHelper helper, int tagOffset) {
		super(helper, tagOffset);
	}

	@Override
	public String getStringRepresetation() {
		String result;
		if (isArray) {
			result = valueArr.toString();
		}
		else {
			result = value.toString();
		}
		
		if (tagID == MetaDataHandler.GPS_TAG_GPSTimeStamp) {
			result = (int)valueArr[0]+":"
					+(int)valueArr[1]+":"
					+(int)valueArr[2]+" UTC";
		}
		
		return result;
	}

}
