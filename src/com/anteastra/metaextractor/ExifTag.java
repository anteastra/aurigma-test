package com.anteastra.metaextractor;

final class ExifTag extends Tag{
	
	public ExifTag(ParseHelper helper, int offSet) {
		
		super(helper, offSet);
	}

	@Override
	public String getStringRepresetation() {
		
		String result;
		if (isArray) {
			result = valueArr.toString();
		} else {
			result = value.toString();
		}
		
		if (tagID == MetaDataHandler.EXIF_TAG_ORIENTATION){
			Short val = 0;
			try{
				val = (Short) value;
			}catch(Exception e){
				return "";
			}
			switch(val){
			case 1:
				result ="The 0th row is at the visual top of the image, and the 0th column is the visual left-hand side";
				break;
			case 2:
				result ="The 0th row is at the visual top of the image, and the 0th column is the visual right-hand side";
				break;
			case 3:
				result ="The 0th row is at the visual bottom of the image, and the 0th column is the visual right-hand side";
				break;
			case 4:
				result ="The 0th row is at the visual bottom of the image, and the 0th column is the visual left-hand side";
				break;
			case 5:
				result ="The 0th row is the visual left-hand side of the image, and the 0th column is the visual top";
				break;
			case 6:
				result ="The 0th row is the visual right-hand side of the image, and the 0th column is the visual top";
				break;
			case 7:
				result ="The 0th row is the visual right-hand side of the image, and the 0th column is the visual bottom";
				break;
			case 8:
				result ="The 0th row is the visual left-hand side of the image, and the 0th column is the visual bottom";
				break;
			default: 					
			}
		}
		
		return result;
	}
	
    
}
