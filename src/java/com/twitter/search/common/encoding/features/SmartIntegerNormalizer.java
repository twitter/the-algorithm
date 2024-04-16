package com.tw ter.search.common.encod ng.features;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

/**
 * A smart  nteger normal zer that converts an  nteger of a known range to a small  nteger up to
 * 8 b s long. T  normal zer generates a boundary value array  n t  constructor as t  buckets
 * for d fferent values.
 * <p/>
 * T  normal zed value has a n ce propert es:
 * 1)   ma nta ns t  order of or g nal value:  f a > b, t n normal ze(a) > normal ze(b).
 * 2) t  value 0  s always normal zed to byte 0.
 * 3) t  normal zed values are (almost) evenly d str buted on t  log scale
 * 4) no waste  n code space, all poss ble values representable by normal zed b s are used,
 * each correspond ng to a d fferent value.
 */
publ c class Smart ntegerNormal zer extends ByteNormal zer {
  // T  max value   want to support  n t  normal zer.  f t   nput  s larger than t  value,
  //  's normal zed as  f  's t  maxValue.
  pr vate f nal  nt maxValue;
  // Number of b s used for normal zed value, t  largest normal zed value
  // would be (1 << numB s) - 1.
  pr vate f nal  nt numB s;
  // T   nclus ve lo r bounds of all buckets. A normal zed value k corresponds to or g nal values
  //  n t   nclus ve-exclus ve range
  //   [ boundaryValues[k], boundaryValues[k+1] )
  pr vate f nal  nt[] boundaryValues;
  // T  length of t  boundaryValues array, or t  number of buckets.
  pr vate f nal  nt length;

  /**
   * Construct a normal zer.
   *
   * @param maxValue max value   supports, must be larger than m nValue. Anyth ng larger than t 
   * would be treated as maxValue.
   * @param numB s number of b s   want to use for t  normal zat on, bet en 1 and 8.
   * h g r resolut on for t  lo r numbers.
   */
  publ c Smart ntegerNormal zer( nt maxValue,  nt numB s) {
    Precond  ons.c ckArgu nt(maxValue > 0);
    Precond  ons.c ckArgu nt(numB s > 0 && numB s <= 8);

    t .maxValue = maxValue;
    t .numB s = numB s;

    t .length = 1 << numB s;
    t .boundaryValues = new  nt[length];


     nt  ndex;
    for ( ndex = length - 1;  ndex >= 0; -- ndex) {
      // values are evenly d str buted on t  log scale
       nt boundary = ( nt) Math.pow(maxValue, (double)  ndex / length);
      //   have more byte slots left than   have poss ble boundary values (buckets),
      // just g ve consecut ve boundary values to all rema n ng slots, start ng from 0.
       f (boundary <=  ndex) {
        break;
      }
      boundaryValues[ ndex] = boundary;
    }
     f ( ndex >= 0) {
      for ( nt   = 1;   <=  ndex; ++ ) {
        boundaryValues[ ] =  ;
      }
    }
    boundaryValues[0] = 0;  // t  f rst one  s always 0.
  }

  @Overr de
  publ c byte normal ze(double val) {
     nt  ntVal = ( nt) (val > maxValue ? maxValue : val);
    return  ntToUns gnedByte(b narySearch( ntVal, boundaryValues));
  }

  /**
   * Return t  lo r bound of t  bucket represent by norm. T  s mply returns t  boundary
   * value  ndexed by current norm.
   */
  @Overr de
  publ c double unnormLo rBound(byte norm) {
    return boundaryValues[uns gnedByteTo nt(norm)];
  }

  /**
   * Return t  upper bound of t  bucket represent by norm. T  returns t  next boundary value
   * m nus 1.  f norm represents t  last bucket,   returns t  maxValue.
   */
  @Overr de
  publ c double unnormUpperBound(byte norm) {
    //  f  's already t  last poss ble normal zed value, just return t  correspond ng last
    // boundary value.
     nt  ntNorm = uns gnedByteTo nt(norm);
     f ( ntNorm == length - 1) {
      return maxValue;
    }
    return boundaryValues[ ntNorm + 1] - 1;
  }

  /**
   * Do a b nary search on array and f nd t   ndex of t   em that's no b gger than value.
   */
  pr vate stat c  nt b narySearch( nt value,  nt[] array) {
    // corner cases
     f (value <= array[0]) {
      return 0;
    } else  f (value >= array[array.length - 1]) {
      return array.length - 1;
    }
     nt left = 0;
     nt r ght = array.length - 1;
     nt p vot = (left + r ght) >> 1;
    do {
       nt m dVal = array[p vot];
       f (value == m dVal) {
        break;
      } else  f (value > m dVal) {
        left = p vot;
      } else {
        r ght = p vot;
      }
      p vot = (left + r ght) >> 1;
    } wh le (p vot != left);
    return p vot;
  }

  @Overr de
  publ c Str ng toStr ng() {
    Str ngBu lder sb = new Str ngBu lder(Str ng.format(
        "Smart  nteger Normal zer (numB s = %d, max = %d)\n",
        t .numB s, t .maxValue));
    for ( nt   = 0;   < t .length;  ++) {
      sb.append(Str ng.format(
          "[%2d] boundary = %6d, range [ %6d, %6d ), norm: %4d | %4d | %4d %s\n",
           , boundaryValues[ ],
          ( nt) unnormLo rBound( ntToUns gnedByte( )),
          ( nt) unnormUpperBound( ntToUns gnedByte( )),
          uns gnedByteTo nt(normal ze(boundaryValues[ ] - 1)),
          uns gnedByteTo nt(normal ze(boundaryValues[ ])),
          uns gnedByteTo nt(normal ze(boundaryValues[ ] + 1)),
            == boundaryValues[ ] ? "*" : ""));
    }
    return sb.toStr ng();
  }

  @V s bleForTest ng
   nt[] getBoundaryValues() {
    return boundaryValues;
  }
}
