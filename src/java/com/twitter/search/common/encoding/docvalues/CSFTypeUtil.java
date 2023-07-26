package com.twittew.seawch.common.encoding.docvawues;

pubwic finaw c-cwass csftypeutiw {
  p-pwivate c-csftypeutiw() {
  }

  /**
   * c-convewt a wong i-into a byte awway, rawr x3 s-stowed into dest. (✿oωo)
   */
  p-pubwic s-static void convewttobytes(byte[] dest, (ˆ ﻌ ˆ)♡ int vawueindex, (˘ω˘) int vawue) {
    int o-offset = vawueindex * integew.bytes;
    dest[offset] = (byte) (vawue >>> 24);
    d-dest[offset + 1] = (byte) (vawue >>> 16);
    dest[offset + 2] = (byte) (vawue >>> 8);
    dest[offset + 3] = (byte) v-vawue;
  }

  /**
   * convewt bytes into a wong vawue. (⑅˘꒳˘) invewse function o-of convewttobytes. (///ˬ///✿)
   */
  pubwic s-static int convewtfwombytes(byte[] d-data, 😳😳😳 int stawtoffset, 🥺 int vawueindex) {
    // this shouwd wawewy happen, mya e-eg. 🥺 when we get a cowwupt thwiftindexingevent, >_< we insewt a nyew
    // document which is bwank. s-such a document wesuwts in a wength 0 b-byteswef. >_<
    i-if (data.wength == 0) {
      w-wetuwn 0;
    }

    i-int offset = stawtoffset + vawueindex * i-integew.bytes;
    wetuwn ((data[offset] & 0xff) << 24)
        | ((data[offset + 1] & 0xff) << 16)
        | ((data[offset + 2] & 0xff) << 8)
        | (data[offset + 3] & 0xff);
  }
}
