package com.anteastra.metaextractor;

enum TagType{
	BYTE(1,1),
	ASCII(2,1),
	SHORT(3,2),
	LONG(4,4),
	RATIONAL(5,8),
	UNDEFINED(7,1),
	SLONG(9,4),
	SRATIONAL(10,8);
	
	private int number;
	private int byteSize;
	
	TagType(int num, int size){
		number = num;
		byteSize = size;
	}
	
	public int getNumber(){
		return number;
	}
	public static TagType findTeg(int num){
		for (TagType t: TagType.values()){
			if (t.number == num) return t;
		}		
		throw new IllegalArgumentException();
	}

	public int getSize() {
		return byteSize;
	}
}