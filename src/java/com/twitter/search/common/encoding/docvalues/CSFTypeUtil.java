package com.tw ter.search.common.encod ng.docvalues;

publ c f nal class CSFTypeUt l {
  pr vate CSFTypeUt l() {
  }

  /**
   * Convert a long  nto a byte array, stored  nto dest.
   */
  publ c stat c vo d convertToBytes(byte[] dest,  nt value ndex,  nt value) {
     nt offset = value ndex *  nteger.BYTES;
    dest[offset] = (byte) (value >>> 24);
    dest[offset + 1] = (byte) (value >>> 16);
    dest[offset + 2] = (byte) (value >>> 8);
    dest[offset + 3] = (byte) value;
  }

  /**
   * Convert bytes  nto a long value.  nverse funct on of convertToBytes.
   */
  publ c stat c  nt convertFromBytes(byte[] data,  nt startOffset,  nt value ndex) {
    // T  should rarely happen, eg. w n   get a corrupt Thr ft ndex ngEvent,    nsert a new
    // Docu nt wh ch  s blank. Such a docu nt results  n a length 0 BytesRef.
     f (data.length == 0) {
      return 0;
    }

     nt offset = startOffset + value ndex *  nteger.BYTES;
    return ((data[offset] & 0xFF) << 24)
        | ((data[offset + 1] & 0xFF) << 16)
        | ((data[offset + 2] & 0xFF) << 8)
        | (data[offset + 3] & 0xFF);
  }
}
