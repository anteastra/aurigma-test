package com.anteastra.metaextractor;

final class ParseHelper {
	
	private byte[] segment;
	
	private boolean straightOrder = true;

	public ParseHelper(byte[] app1Segment) {
		segment = app1Segment;
	}

	public String getString(int index, int length) {
		return new String(segment, index, length);
	}
	
	public void setStraightByteOrder(boolean value) {
		straightOrder = value;
	}

	
	public int getUInt16(int index) {

        if (!straightOrder) {
            return (segment[index    ] << 8 & 0xFF00) |
                   (segment[index + 1]      & 0xFF);
        } else {
            return (segment[index + 1] << 8 & 0xFF00) |
                   (segment[index    ]      & 0xFF);
        }
    }

	public short getInt16(int index) {
        if (!straightOrder) {
            return (short) (((short)segment[index    ] << 8 & (short)0xFF00) |
                            ((short)segment[index + 1]      & (short)0xFF));
        } else {
            return (short) (((short)segment[index + 1] << 8 & (short)0xFF00) |
                            ((short)segment[index    ]      & (short)0xFF));
        }
    }
	
	public int getInt32(int index) {

        if (!straightOrder) {
            return (segment[index    ] << 24 & 0xFF000000) |
                   (segment[index + 1] << 16 & 0xFF0000) |
                   (segment[index + 2] << 8  & 0xFF00) |
                   (segment[index + 3]       & 0xFF);
        } else {
            return (segment[index + 3] << 24 & 0xFF000000) |
                   (segment[index + 2] << 16 & 0xFF0000) |
                   (segment[index + 1] << 8  & 0xFF00) |
                   (segment[index    ]       & 0xFF);
        }
    }
	
}
