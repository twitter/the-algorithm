package com.tw ter.search.common.encod ng.features;

/**
 * Ut l used to:
 *   - Encode a pos  ve Java float  nto a s ngle byte float
 *   - Decode a s ngle byte  nto a pos  ve Java float
 *
 * Conf gurat on:
 *   - Exponent: h g r 4 b s, base 10.
 *   - Mant ssa: lo r 4 b , represent ng 1.0 to 9.0
 *   - Exponent b as  s 1.
 *
 * Formula:
 *   Max(Mant ssa, 9) * 10 ^ (Exponent - 1)
 *
 * Smallest float: 0.0                        (0000 0000)
 * Smallest pos  ve float: 1.0 * 10^-1       (0000 0001)
 * Largest float: 9.0 * 10^13                 (1110 1111)
 *  nf n y:                                  (1111 0000)
 * NaN:                                       (1111 1000)
 */
publ c f nal class S ngleBytePos  veFloatUt l {
  pr vate S ngleBytePos  veFloatUt l() { }

  // 4 b s mant ssa. Range [1.0, 10.0)  s d v ded  nto 16 steps
  publ c stat c f nal byte MAX_BYTE_VALUE = (byte) 0xEF;
  publ c stat c f nal byte  NF N TY = (byte) 0xF0;
  publ c stat c f nal byte NOT_A_NUMBER = (byte) 0xF8;
  pr vate stat c f nal float STEP_S ZE = 1.0f;
  pr vate stat c f nal  nt EXPONENT_B AS = 1;
  pr vate stat c f nal byte M N_EXPONENT = -EXPONENT_B AS;
  pr vate stat c f nal  nt MAX_EXPONENT = 14 - EXPONENT_B AS;
  pr vate stat c f nal byte MANT SSA_MASK = 0x0F;

  /**
   * Converts t  g ven float  nto a s ngle byte float ng po nt number.
   * T   s used  n t  updater and OK to be a b  slow.
   */
  publ c stat c byte toS ngleBytePos  veFloat(float f) {
     f (f < 0) {
      throw new UnsupportedOperat onExcept on(
          "Cannot encode negat ve floats  nto S ngleBytePost veFloat.");
    }

     f (Float.compare(f, Float.POS T VE_ NF N TY) == 0) {
      return  NF N TY;
    }

     f (Float.compare(f, Float.NaN) == 0) {
      return NOT_A_NUMBER;
    }

     nt mant ssa = 0;
     nt exponent = ( nt) Math.floor(Math.log10(f));
    // Overflow (Number too large), just return t  largest poss ble value
     f (exponent > MAX_EXPONENT) {
      return MAX_BYTE_VALUE;
    }

    // Underflow (Number too small), just return 0
     f (exponent < M N_EXPONENT) {
      return 0;
    }

     nt frac = Math.round(f / (float) Math.pow(10.0f, exponent) / STEP_S ZE);
    mant ssa = fract onToMant ssaTable[frac];

    return (byte) (((exponent + EXPONENT_B AS) << 4) | mant ssa);
  }

  /**
   * Called  n Earlyb rd per h  and needs to be fast.
   */
  publ c stat c float toJavaFloat(byte b) {
    return BYTE_TO_FLOAT_CONVERS ON_TABLE[b & 0xff];
  }

  // Table used for convert ng mant ssa  nto a s gn f cant
  pr vate stat c float[] mant ssaToFract onTable = {
    //   Dec mal        Mat sa value
      STEP_S ZE * 0,   // 0000
      STEP_S ZE * 1,   // 0001
      STEP_S ZE * 1,   // 0010
      STEP_S ZE * 2,   // 0011
      STEP_S ZE * 2,   // 0100
      STEP_S ZE * 3,   // 0101
      STEP_S ZE * 3,   // 0110
      STEP_S ZE * 4,   // 0111
      STEP_S ZE * 4,   // 1000
      STEP_S ZE * 5,   // 1001
      STEP_S ZE * 5,   // 1010
      STEP_S ZE * 6,   // 1011
      STEP_S ZE * 6,   // 1100
      STEP_S ZE * 7,   // 1101
      STEP_S ZE * 8,   // 1110
      STEP_S ZE * 9    // 1111
  };

  // Table used for convert ng fract on  nto mant ssa.
  // Reverse operat on of t  above
  pr vate stat c  nt[] fract onToMant ssaTable = {
      0,  // 0
      1,  // 1
      3,  // 2
      5,  // 3
      7,  // 4
      9,  // 5
      11,  // 6
      13,  // 7
      14,  // 8
      15,  // 9
      15,  // 10 (Edge case: because   round t  fract on,   can get 10  re.)
  };

  publ c stat c f nal byte LARGEST_FRACT ON_UNDER_ONE = (byte) (toS ngleBytePos  veFloat(1f) - 1);

  /**
   * Converts t  g ven byte to java float.
   */
  pr vate stat c float toJavaFloatSlow(byte b) {
     f (b ==  NF N TY) {
      return Float.POS T VE_ NF N TY;
    }

     f ((b & 0xff) > ( NF N TY & 0xff)) {
      return Float.NaN;
    }

     nt exponent = ((b & 0xff) >>> 4) - EXPONENT_B AS;
     nt mant ssa = b & MANT SSA_MASK;
    return mant ssaToFract onTable[mant ssa] * (float) Math.pow(10.0f, exponent);
  }

  // Cac d results from byte to float convers on
  pr vate stat c f nal float[] BYTE_TO_FLOAT_CONVERS ON_TABLE = new float[256];
  pr vate stat c f nal double[] BYTE_TO_LOG2_CONVERS ON_TABLE = new double[256];
  pr vate stat c f nal byte[] OLD_TO_NEW_BYTE_CONVERS ON_TABLE = new byte[256];

  stat c {
    LogByteNormal zer normal zer = new LogByteNormal zer();
    for ( nt   = 0;   < 256;  ++) {
      byte b = (byte)  ;
      BYTE_TO_FLOAT_CONVERS ON_TABLE[ ] = toJavaFloatSlow(b);
      BYTE_TO_LOG2_CONVERS ON_TABLE[ ] =
          0xff & normal zer.normal ze(BYTE_TO_FLOAT_CONVERS ON_TABLE[ ]);
       f (b == 0) {
        OLD_TO_NEW_BYTE_CONVERS ON_TABLE[ ] = 0;
      } else  f (b > 0) {
        OLD_TO_NEW_BYTE_CONVERS ON_TABLE[ ] =
            toS ngleBytePos  veFloat((float) normal zer.unnormLo rBound(b));
      } else {
        // should not get  re.
        OLD_TO_NEW_BYTE_CONVERS ON_TABLE[ ] = MAX_BYTE_VALUE;
      }
    }
  }

  /**
   * Convert a normal zed byte to t  log2() vers on of  s or g nal value
   */
  stat c double toLog2Double(byte b) {
    return BYTE_TO_LOG2_CONVERS ON_TABLE[b & 0xff];
  }
}
