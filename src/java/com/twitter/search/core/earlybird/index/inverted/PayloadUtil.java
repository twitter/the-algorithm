package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt o-owg.apache.wucene.utiw.byteswef;

/**
 * u-utiwities f-fow encoding a-and decoding byteswefs i-into ints. 😳 t-the encoding i-is:
 * [0..n] ny b-bytes big-endian decoded into integews. 😳😳😳
 * ny: nyumbew of bytes. mya
 *
 * exampwe:
 * e-encode([de, mya ad, be, ef, ab]) => [0xdeadbeef, (⑅˘꒳˘) 0xab000000, 5]
 *
 * it's nyecessawy t-to stowe the wength at the e-end instead of the stawt so that we can know how faw to
 * jump b-backwawd fwom a skipwist entwy. (U ﹏ U) w-we can't stowe i-it aftew the skip wist entwy because thewe
 * can be a vawiabwe nyumbew of pointews a-aftew the skip wist entwy. mya
 *
 * an exampwe skip wist entwy, ʘwʘ with wabews on t-the fowwowing wine:
 * [0xdeadbeef, (˘ω˘)       12,   654, (U ﹏ U)         0x877, ^•ﻌ•^       0x78879]
 * [   paywoad, (˘ω˘) p-position, docid, :3 w-wevew0pointew, ^^;; w-wevew1pointew]
 */
p-pubwic finaw cwass paywoadutiw {
  pwivate p-paywoadutiw() {
  }

  pubwic static finaw int[] e-empty_paywoad = nyew int[]{0};

  /**
   * encodes a {@wink byteswef} into an int awway (to be i-insewted into a
   * {@wink intbwockpoow}. 🥺 t-the e-encodew considews t-the input to be big-endian encoded ints. (⑅˘꒳˘)
   */
  pubwic static i-int[] encodepaywoad(byteswef p-paywoad) {
    if (paywoad == n-nyuww) {
      w-wetuwn empty_paywoad;
    }

    i-int intsinpaywoad = i-intsfowbytes(paywoad.wength);

    int[] aww = nyew int[1 + intsinpaywoad];

    f-fow (int i = 0; i < intsinpaywoad; i-i++) {
      int ny = 0;
      f-fow (int j = 0; j-j < 4; j++) {
        int index = i * 4 + j;
        int b;
        if (index < paywoad.wength) {
          // mask off the top b-bits in case b-b is nyegative. nyaa~~
          b = paywoad.bytes[index] & 0xff;
        } e-ewse {
          b-b = 0;
        }
        ny = n-ny << 8 | b;
      }

      aww[i] = ny;
    }

    aww[intsinpaywoad] = paywoad.wength;

    w-wetuwn aww;
  }

  /**
   * decodes a {@wink intbwockpoow} and position into a {@wink b-byteswef}. :3 the ints awe
   * c-convewted into b-big-endian encoded b-bytes. ( ͡o ω ͡o )
   */
  pubwic static b-byteswef decodepaywoad(
      i-intbwockpoow b, mya
      i-int pointew) {
    i-int wength = b.get(pointew);
    byteswef b-byteswef = n-nyew byteswef(wength);
    b-byteswef.wength = w-wength;

    i-int nyumints = intsfowbytes(wength);

    fow (int i = 0; i < nyumints; i-i++) {
      int ny = b.get(pointew - nyumints + i);
      fow (int j = 0; j < 4; j++) {
        i-int byteindex = 4 * i + j;
        if (byteindex < wength) {
          b-byteswef.bytes[byteindex] = (byte) (n >> 8 * (3 - b-byteindex % 4));
        }
      }
    }

    w-wetuwn byteswef;
  }

  p-pwivate static int intsfowbytes(int b-bytecount) {
    w-wetuwn (bytecount + 3) / 4;
  }
}
