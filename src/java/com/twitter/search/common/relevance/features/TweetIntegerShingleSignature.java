package com.tw ter.search.common.relevance.features;

 mport java.n o.ByteBuffer;
 mport java.ut l.Arrays;

 mport com.google.common.base.Precond  ons;

/**
 * A T et ntegerSh ngleS gnature object cons sts of 4 bytes, each represent ng t  s gnature of
 * a status text sample. T  s gnature bytes are sorted  n ascend ng order and compacted to an
 *  nteger  n b g end an for ser al zat on.
 *
 * Fuzzy match ng of two T et ntegerSh ngleS gnature objects  s  t w n t  number of match ng
 * bytes bet en t  two  s equal to or above 3.
 */
publ c class T et ntegerSh ngleS gnature {
  publ c stat c f nal  nt NUM_SH NGLES =  nteger.S ZE / Byte.S ZE;
  publ c stat c f nal  nt DEFAULT_NO_S GNATURE = 0;
  publ c stat c f nal T et ntegerSh ngleS gnature NO_S GNATURE_HANDLE =
    deser al ze(DEFAULT_NO_S GNATURE);
  publ c stat c f nal  nt DEFAULT_M N_SH NGLES_MATCH = 3;
  pr vate f nal  nt m nSh nglesMatch;

  pr vate f nal byte[] sh ngles;
  pr vate f nal  nt s gnature;  // redundant  nformat on, for eas er compar son.

  /**
   * Construct from a byte array.
   */
  publ c T et ntegerSh ngleS gnature(byte[] sh ngles,  nt m nSh nglesMatch) {
    Precond  ons.c ckArgu nt(sh ngles.length == NUM_SH NGLES);
    t .sh ngles = sh ngles;
    // sort to byte's natural ascend ng order
    Arrays.sort(t .sh ngles);
    t .m nSh nglesMatch = m nSh nglesMatch;
    t .s gnature = ser al ze nternal(sh ngles);
  }

  /**
   * Construct from a byte array.
   */
  publ c T et ntegerSh ngleS gnature(byte[] sh ngles) {
    t (sh ngles, DEFAULT_M N_SH NGLES_MATCH);
  }

  /**
   * Construct from a ser al zed  nteger s gnature.
   */
  publ c T et ntegerSh ngleS gnature( nt s gnature,  nt m nSh nglesMatch) {
    t .sh ngles = deser al ze nternal(s gnature);
    // sort to byte's natural ascend ng order
    Arrays.sort(t .sh ngles);
    t .m nSh nglesMatch = m nSh nglesMatch;
    // now store t  sorted sh ngles  nto s gnature f eld, may be d fferent from what passed  n.
    t .s gnature = ser al ze nternal(sh ngles);
  }

  /**
   * Construct from a ser al zed  nteger s gnature.
   */
  publ c T et ntegerSh ngleS gnature( nt s gnature) {
    t (s gnature, DEFAULT_M N_SH NGLES_MATCH);
  }

  /**
   * Used by  ngester to generate s gnature.
   * Raw s gnatures are  n byte arrays per sample, and can be more or less
   * than what  s asked for.
   *
   * @param rawS gnature
   */
  publ c T et ntegerSh ngleS gnature( erable<byte[]> rawS gnature) {
    byte[] condensedS gnature = new byte[NUM_SH NGLES];
     nt   = 0;
    for (byte[] s gnature em : rawS gnature) {
      condensedS gnature[ ++] = s gnature em[0];
       f (  == NUM_SH NGLES) {
        break;
      }
    }
    t .sh ngles = condensedS gnature;
    Arrays.sort(t .sh ngles);
    t .m nSh nglesMatch = DEFAULT_M N_SH NGLES_MATCH;
    t .s gnature = ser al ze nternal(sh ngles);
  }

  /**
   * W n used  n a hashtable for dup detect on, take t  f rst byte of each s gnature for fast
   * pass for major y case of no fuzzy match ng. For top quer es, t  opt m zat on losses about
   * only 4% of all fuzzy matc s.
   *
   * @return most s gn f cant byte of t  s gnature as  s hashcode.
   */
  @Overr de
  publ c  nt hashCode() {
    return sh ngles[0] & 0xFF;
  }

  /**
   * Perform fuzzy match ng bet en two T et ntegerSh ngleS gnature objects.
   *
   * @param ot r T et ntegerSh ngleS gnature object to perform fuzzy match aga nst
   * @return true  f at least m nMatch number of bytes match
   */
  @Overr de
  publ c boolean equals(Object ot r) {
     f (t  == ot r) {
      return true;
    }
     f (ot r == null) {
      return false;
    }
     f (getClass() != ot r.getClass()) {
      return false;
    }

    f nal T et ntegerSh ngleS gnature ot rS gnature nteger = (T et ntegerSh ngleS gnature) ot r;

     nt ot rS gnature = ot rS gnature nteger.ser al ze();
     f (s gnature == ot rS gnature) {
      // Both ser al zed s gnature  s t  sa 
      return true;
    } else  f (s gnature != DEFAULT_NO_S GNATURE && ot rS gnature != DEFAULT_NO_S GNATURE) {
      // Ne  r  s NO_S GNATURE, need to compare sh ngles.
      byte[] ot rSh ngles = ot rS gnature nteger.getSh ngles();
       nt numberMatc sNeeded = m nSh nglesMatch;
      // expect bytes are  n ascend ng sorted order
       nt   = 0;
       nt j = 0;
      wh le (((numberMatc sNeeded <= (NUM_SH NGLES -  )) // early term nat on for  
              || (numberMatc sNeeded <= (NUM_SH NGLES - j))) // early term nat on j
             && (  < NUM_SH NGLES) && (j < NUM_SH NGLES)) {
         f (sh ngles[ ] == ot rSh ngles[j]) {
           f (sh ngles[ ] != 0) {  //   only cons der two sh ngles equal  f t y are non zero
            numberMatc sNeeded--;
             f (numberMatc sNeeded == 0) {
              return true;
            }
          }
           ++;
          j++;
        } else  f (sh ngles[ ] < ot rSh ngles[j]) {
           ++;
        } else  f (sh ngles[ ] > ot rSh ngles[j]) {
          j++;
        }
      }
    }
    // One  s NO_S GNATURE and one  s not.
    return false;
  }

  /**
   * Returns t  sorted array of s gnature bytes.
   */
  publ c byte[] getSh ngles() {
    return sh ngles;
  }

  /**
   * Ser al ze 4 sorted s gnature bytes  nto an  nteger  n b g end an order.
   *
   * @return compacted  nt s gnature
   */
  pr vate stat c  nt ser al ze nternal(byte[] sh ngles) {
    ByteBuffer byteBuffer = ByteBuffer.allocate(NUM_SH NGLES);
    byteBuffer.put(sh ngles, 0, NUM_SH NGLES);
    return byteBuffer.get nt(0);
  }

  /**
   * Deser al ze an  nteger  nto a 4-byte array.
   * @param s gnature T  s gnature  nteger.
   * @return A byte array w h 4 ele nts.
   */
  pr vate stat c byte[] deser al ze nternal( nt s gnature) {
    return ByteBuffer.allocate(NUM_SH NGLES).put nt(s gnature).array();
  }

  publ c  nt ser al ze() {
    return s gnature;
  }

  publ c stat c boolean  sFuzzyMatch( nt s gnature1,  nt s gnature2) {
    return T et ntegerSh ngleS gnature.deser al ze(s gnature1).equals(
        T et ntegerSh ngleS gnature.deser al ze(s gnature2));
  }

  publ c stat c T et ntegerSh ngleS gnature deser al ze( nt s gnature) {
    return new T et ntegerSh ngleS gnature(s gnature);
  }

  publ c stat c T et ntegerSh ngleS gnature deser al ze( nt s gnature,  nt m nMatchS ngles) {
    return new T et ntegerSh ngleS gnature(s gnature, m nMatchS ngles);
  }

  @Overr de
  publ c Str ng toStr ng() {
    return Str ng.format("%d %d %d %d", sh ngles[0], sh ngles[1], sh ngles[2], sh ngles[3]);
  }
}
