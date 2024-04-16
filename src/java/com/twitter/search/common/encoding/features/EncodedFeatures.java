package com.tw ter.search.common.encod ng.features;

/**
 * Encodes mult ple values (bytes or b s)  nto an  nteger.
 */
publ c class EncodedFeatures {
  pr vate  nt value;

  publ c f nal vo d setSer al zedValue( nt val) {
    t .value = val;
  }

  publ c f nal  nt getSer al zedValue() {
    return value;
  }

  // setByte  s agnost c to s gned / uns gned bytes.
  protected f nal EncodedFeatures setByte(byte count,  nt b sh ft, long  nverseMask) {
    value = ( nt) ((value &  nverseMask) | ((count & 0xffL) << b sh ft));
    return t ;
  }

  /**
   * Sets t  value but only  f greater. setByte fGreater assu s uns gned bytes.
   */
  publ c f nal EncodedFeatures setByte fGreater(byte newCount,  nt b sh ft, long  nversemask) {
     f ((getByte(b sh ft) & 0xff) < (newCount & 0xff)) {
      setByte(newCount, b sh ft,  nversemask);
    }
    return t ;
  }

  protected f nal  nt getByte( nt b sh ft) {
    return ( nt) (((value & 0xffffffffL) >>> b sh ft) & 0xffL);
  }

  protected f nal  nt getByteMasked( nt b sh ft, long mask) {
    return ( nt) (((value & mask) >>> b sh ft) & 0xffL);
  }

  protected f nal EncodedFeatures setB ( nt b , boolean flag) {
     f (flag) {
      value |= b ;
    } else {
      value &= ~b ;
    }
    return t ;
  }

  protected f nal boolean getB ( nt b ) {
    return (value & b ) != 0;
  }

  @Overr de
  publ c Str ng toStr ng() {
    return Str ng.format("%x", value);
  }
}
