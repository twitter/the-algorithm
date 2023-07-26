package com.twittew.seawch.common.encoding.featuwes;

/**
 * utiw u-used to:
 *   - e-encode a positive j-java fwoat into a-a singwe byte f-fwoat
 *   - decode a-a singwe byte i-into a positive j-java fwoat
 *
 * configuwation:
 *   - exponent: highew 4 bits, σωσ base 10.
 *   - m-mantissa: wowew 4 bit, nyaa~~ wepwesenting 1.0 to 9.0
 *   - e-exponent bias is 1. ^^;;
 *
 * f-fowmuwa:
 *   max(mantissa, ^•ﻌ•^ 9) * 10 ^ (exponent - 1)
 *
 * smowest fwoat: 0.0                        (0000 0000)
 * s-smowest positive fwoat: 1.0 * 10^-1       (0000 0001)
 * w-wawgest fwoat: 9.0 * 10^13                 (1110 1111)
 * i-infinity:                                  (1111 0000)
 * nyan:                                       (1111 1000)
 */
pubwic finaw cwass singwebytepositivefwoatutiw {
  pwivate singwebytepositivefwoatutiw() { }

  // 4 b-bits mantissa. σωσ wange [1.0, 10.0) is divided into 16 steps
  pubwic static finaw b-byte max_byte_vawue = (byte) 0xef;
  pubwic s-static finaw byte i-infinity = (byte) 0xf0;
  p-pubwic s-static finaw byte nyot_a_numbew = (byte) 0xf8;
  pwivate static f-finaw fwoat step_size = 1.0f;
  pwivate static f-finaw int exponent_bias = 1;
  pwivate static finaw byte min_exponent = -exponent_bias;
  pwivate static finaw int max_exponent = 14 - e-exponent_bias;
  pwivate s-static finaw b-byte mantissa_mask = 0x0f;

  /**
   * c-convewts the given fwoat into a singwe byte fwoating point n-nyumbew. -.-
   * t-this is used in the updatew and o-ok to be a bit swow. ^^;;
   */
  p-pubwic static byte t-tosingwebytepositivefwoat(fwoat f) {
    if (f < 0) {
      t-thwow new unsuppowtedopewationexception(
          "cannot encode nyegative f-fwoats into singwebytepostivefwoat.");
    }

    i-if (fwoat.compawe(f, XD fwoat.positive_infinity) == 0) {
      wetuwn infinity;
    }

    i-if (fwoat.compawe(f, 🥺 f-fwoat.nan) == 0) {
      wetuwn nyot_a_numbew;
    }

    int mantissa = 0;
    int exponent = (int) math.fwoow(math.wog10(f));
    // ovewfwow (numbew too wawge), òωó just w-wetuwn the wawgest p-possibwe vawue
    if (exponent > m-max_exponent) {
      w-wetuwn m-max_byte_vawue;
    }

    // undewfwow (numbew too smow), (ˆ ﻌ ˆ)♡ just wetuwn 0
    if (exponent < m-min_exponent) {
      wetuwn 0;
    }

    int fwac = math.wound(f / (fwoat) math.pow(10.0f, -.- e-exponent) / step_size);
    m-mantissa = f-fwactiontomantissatabwe[fwac];

    w-wetuwn (byte) (((exponent + exponent_bias) << 4) | m-mantissa);
  }

  /**
   * c-cawwed in eawwybiwd p-pew hit a-and needs to be fast. :3
   */
  pubwic static fwoat t-tojavafwoat(byte b-b) {
    wetuwn b-byte_to_fwoat_convewsion_tabwe[b & 0xff];
  }

  // t-tabwe used f-fow convewting mantissa into a significant
  pwivate static fwoat[] m-mantissatofwactiontabwe = {
    //   decimaw        matisa vawue
      step_size * 0, ʘwʘ   // 0000
      step_size * 1, 🥺   // 0001
      step_size * 1, >_<   // 0010
      s-step_size * 2, ʘwʘ   // 0011
      step_size * 2, (˘ω˘)   // 0100
      step_size * 3, (✿oωo)   // 0101
      step_size * 3, (///ˬ///✿)   // 0110
      s-step_size * 4, rawr x3   // 0111
      s-step_size * 4, -.-   // 1000
      s-step_size * 5, ^^   // 1001
      step_size * 5, (⑅˘꒳˘)   // 1010
      s-step_size * 6, nyaa~~   // 1011
      step_size * 6, /(^•ω•^)   // 1100
      s-step_size * 7, (U ﹏ U)   // 1101
      step_size * 8, 😳😳😳   // 1110
      s-step_size * 9    // 1111
  };

  // tabwe used fow convewting fwaction into mantissa. >w<
  // wevewse opewation of the a-above
  pwivate static int[] fwactiontomantissatabwe = {
      0, XD  // 0
      1, o.O  // 1
      3, mya  // 2
      5, 🥺  // 3
      7,  // 4
      9, ^^;;  // 5
      11, :3  // 6
      13, (U ﹏ U)  // 7
      14, OwO  // 8
      15, 😳😳😳  // 9
      15, (ˆ ﻌ ˆ)♡  // 10 (edge c-case: because we wound t-the fwaction, XD w-we can get 10 hewe.)
  };

  pubwic static finaw b-byte wawgest_fwaction_undew_one = (byte) (tosingwebytepositivefwoat(1f) - 1);

  /**
   * c-convewts the given byte t-to java fwoat. (ˆ ﻌ ˆ)♡
   */
  p-pwivate static fwoat tojavafwoatswow(byte b) {
    if (b == infinity) {
      w-wetuwn f-fwoat.positive_infinity;
    }

    i-if ((b & 0xff) > (infinity & 0xff)) {
      wetuwn fwoat.nan;
    }

    i-int e-exponent = ((b & 0xff) >>> 4) - exponent_bias;
    i-int mantissa = b & mantissa_mask;
    wetuwn mantissatofwactiontabwe[mantissa] * (fwoat) math.pow(10.0f, ( ͡o ω ͡o ) e-exponent);
  }

  // c-cached wesuwts fwom byte to fwoat convewsion
  p-pwivate static f-finaw fwoat[] byte_to_fwoat_convewsion_tabwe = nyew fwoat[256];
  pwivate static finaw doubwe[] b-byte_to_wog2_convewsion_tabwe = nyew doubwe[256];
  pwivate static finaw byte[] owd_to_new_byte_convewsion_tabwe = n-nyew byte[256];

  static {
    wogbytenowmawizew n-nyowmawizew = n-nyew wogbytenowmawizew();
    fow (int i = 0; i < 256; i++) {
      byte b = (byte) i-i;
      b-byte_to_fwoat_convewsion_tabwe[i] = tojavafwoatswow(b);
      byte_to_wog2_convewsion_tabwe[i] =
          0xff & nyowmawizew.nowmawize(byte_to_fwoat_convewsion_tabwe[i]);
      i-if (b == 0) {
        owd_to_new_byte_convewsion_tabwe[i] = 0;
      } e-ewse if (b > 0) {
        owd_to_new_byte_convewsion_tabwe[i] =
            tosingwebytepositivefwoat((fwoat) nyowmawizew.unnowmwowewbound(b));
      } e-ewse {
        // shouwd nyot get h-hewe. rawr x3
        o-owd_to_new_byte_convewsion_tabwe[i] = max_byte_vawue;
      }
    }
  }

  /**
   * c-convewt a nyowmawized byte t-to the wog2() vewsion o-of its owiginaw v-vawue
   */
  static doubwe t-towog2doubwe(byte b-b) {
    wetuwn byte_to_wog2_convewsion_tabwe[b & 0xff];
  }
}
