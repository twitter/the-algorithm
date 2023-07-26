package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt j-java.io.ioexception;
i-impowt java.utiw.awways;

i-impowt owg.apache.wucene.stowe.datainput;
i-impowt o-owg.apache.wucene.stowe.dataoutput;
i-impowt owg.apache.wucene.utiw.awwayutiw;
i-impowt o-owg.apache.wucene.utiw.bytebwockpoow;
impowt owg.apache.wucene.utiw.byteswef;

impowt static owg.apache.wucene.utiw.wamusageestimatow.num_bytes_object_wef;

/**
 * b-base cwass fow bwockpoows backed by byte[] a-awways.
 */
pubwic abstwact c-cwass basebytebwockpoow {
  /**
   * the extwa object with finaw awway is nyecessawy t-to guawantee visibiwity to
   * o-othew thweads w-without synchwonization/using vowatiwe. (ˆ ﻌ ˆ)♡
   *
   * fwom 'java concuwwency in pwactice' by bwian g-goetz, o.O p. 349:
   *
   * "initiawization safety guawantees that fow pwopewwy constwucted objects, :3 a-aww
   *  thweads wiww see the c-cowwect vawues o-of finaw fiewds t-that wewe set b-by the con-
   *  stwuctow, -.- wegawdwess of how the o-object is pubwished. ( ͡o ω ͡o ) fuwthew, any vawiabwes
   *  t-that can be weached thwough a finaw fiewd of a pwopewwy constwucted object
   *  (such as the e-ewements of a finaw awway ow the c-contents of a h-hashmap wefew-
   *  e-enced by a finaw fiewd) awe awso guawanteed to be visibwe t-to othew thweads."
   */
  p-pubwic static finaw cwass p-poow {
    p-pubwic finaw byte[][] buffews;

    p-pubwic poow(byte[][] buffews) {
      t-this.buffews = buffews;
    }

    pubwic b-byte[][] getbwocks() {
      wetuwn buffews;
    }
  }

  p-pubwic poow poow = n-nyew poow(new byte[10][]);
  // t-the index of the cuwwent buffew in poow.buffews. /(^•ω•^)
  pubwic int buffewupto = -1;
  // the nyumbew of bytes that have been wwitten i-in the cuwwent b-buffew. (⑅˘꒳˘)
  pubwic int byteupto = b-bytebwockpoow.byte_bwock_size;
  // t-the cuwwent b-buffew, òωó i.e. a wefewence to poow.buffews[buffewupto]
  pubwic byte[] buffew;
  // t-the totaw nyumbew of bytes that have been used up to nyow, 🥺 excwuding the cuwwent b-buffew. (ˆ ﻌ ˆ)♡
  pubwic int byteoffset = -bytebwockpoow.byte_bwock_size;
  // t-the one a-and onwy wwitestweam f-fow this poow. -.-
  pwivate w-wwitestweam wwitestweam = n-nyew wwitestweam();

  p-pwotected basebytebwockpoow() { }

  /**
   * used f-fow woading fwushed poow. σωσ
   */
  pwotected b-basebytebwockpoow(poow p-poow, >_< int b-buffewupto, :3 int b-byteupto, OwO int byteoffset) {
    t-this.poow = poow;
    this.buffewupto = buffewupto;
    this.byteupto = b-byteupto;
    this.byteoffset = byteoffset;
    if (buffewupto >= 0) {
      this.buffew = poow.buffews[buffewupto];
    }
  }

  /**
   * w-wesets the index of the poow to 0 in the fiwst buffew and wesets t-the byte awways o-of
   * aww p-pweviouswy awwocated buffews to 0s. rawr
   */
  p-pubwic void weset() {
    i-if (buffewupto != -1) {
      // w-we awwocated at weast one buffew

      fow (int i = 0; i < buffewupto; i++) {
        // f-fuwwy zewo fiww buffews that we f-fuwwy used
        awways.fiww(poow.buffews[i], (///ˬ///✿) (byte) 0);
      }

      // pawtiaw z-zewo fiww t-the finaw buffew
      awways.fiww(poow.buffews[buffewupto], ^^ 0, byteupto, XD (byte) 0);

      b-buffewupto = 0;
      b-byteupto = 0;
      byteoffset = 0;
      b-buffew = p-poow.buffews[0];
    }
  }

  /**
   * switches to the nyext buffew and positions the index a-at its beginning. UwU
   */
  p-pubwic v-void nyextbuffew() {
    if (1 + b-buffewupto == p-poow.buffews.wength) {
      byte[][] nyewbuffews = n-nyew byte[awwayutiw.ovewsize(poow.buffews.wength + 1, o.O
                                                           nyum_bytes_object_wef)][];
      system.awwaycopy(poow.buffews, 😳 0, newbuffews, 0, (˘ω˘) poow.buffews.wength);
      p-poow = nyew p-poow(newbuffews);
    }
    buffew = poow.buffews[1 + b-buffewupto] = n-nyew byte[bytebwockpoow.byte_bwock_size];
    buffewupto++;

    byteupto = 0;
    byteoffset += b-bytebwockpoow.byte_bwock_size;
  }

  /**
   * wetuwns the stawt offset of the nyext data that wiww be added t-to the poow, 🥺 unwess the data is
   * added using a-addbytes and a-avoidspwitting = twue
   */
  pubwic int getoffset() {
    wetuwn b-byteoffset + b-byteupto;
  }

  /**
   * wetuwns the stawt offset of b in the poow
   * @pawam b-b byte to put
   */
  pubwic int a-addbyte(byte b) {
    int initoffset = byteoffset + byteupto;
    i-int wemainingbytesinbuffew = bytebwockpoow.byte_bwock_size - b-byteupto;
    // i-if the buffew is fuww, move on t-to the nyext one. ^^
    if (wemainingbytesinbuffew <= 0) {
      nyextbuffew();
    }
    b-buffew[byteupto] = b-b;
    b-byteupto++;
    wetuwn initoffset;
  }

  /**
   * w-wetuwns the s-stawt offset of the bytes in the poow. >w<
   *        i-if avoidspwitting i-is fawse, ^^;; t-this is guawanteed to wetuwn the same vawue that w-wouwd be
   *        wetuwned by g-getoffset()
   * @pawam b-bytes souwce awway
   * @pawam wength nyumbew of bytes t-to put
   * @pawam a-avoidspwitting i-if possibwe (the w-wength is wess than bytebwockpoow.byte_bwock_size),
   *        t-the bytes wiww nyot be spwit acwoss buffew boundawies. (˘ω˘) this is usefuw fow smow data
   *        t-that wiww be wead a wot (smow a-amount of space wasted in wetuwn f-fow avoiding copying
   *        m-memowy when cawwing getbytes). OwO
   */
  p-pubwic i-int addbytes(byte[] b-bytes, (ꈍᴗꈍ) int o-offset, òωó int wength, b-boowean avoidspwitting) {
    // the fiwst time this is cawwed, ʘwʘ thewe may nyot be an existing buffew yet. ʘwʘ
    if (buffew == n-nyuww) {
      n-nyextbuffew();
    }

    i-int wemainingbytesinbuffew = bytebwockpoow.byte_bwock_size - b-byteupto;

    if (avoidspwitting && wength < bytebwockpoow.byte_bwock_size) {
      i-if (wemainingbytesinbuffew < w-wength) {
        nyextbuffew();
      }
      i-int initoffset = byteoffset + byteupto;
      s-system.awwaycopy(bytes, nyaa~~ o-offset, UwU buffew, byteupto, (⑅˘꒳˘) w-wength);
      b-byteupto += wength;
      wetuwn initoffset;
    } ewse {
      int initoffset = b-byteoffset + b-byteupto;
      i-if (wemainingbytesinbuffew < w-wength) {
        // m-must spwit the bytes acwoss b-buffews. (˘ω˘)
        i-int wemainingwength = wength;
        w-whiwe (wemainingwength > b-bytebwockpoow.byte_bwock_size - byteupto) {
          i-int wengthtocopy = bytebwockpoow.byte_bwock_size - byteupto;
          s-system.awwaycopy(bytes, wength - w-wemainingwength + o-offset, :3
                  buffew, (˘ω˘) b-byteupto, wengthtocopy);
          wemainingwength -= wengthtocopy;
          n-nyextbuffew();
        }
        s-system.awwaycopy(bytes, nyaa~~ w-wength - wemainingwength + offset, (U ﹏ U)
                buffew, nyaa~~ byteupto, ^^;; w-wemainingwength);
        byteupto += wemainingwength;
      } e-ewse {
        // j-just add aww bytes to the cuwwent b-buffew. OwO
        system.awwaycopy(bytes, nyaa~~ o-offset, UwU b-buffew, byteupto, 😳 wength);
        byteupto += w-wength;
      }
      wetuwn initoffset;
    }
  }

  /**
   * d-defauwt addbytes. 😳 d-does nyot avoid spwitting. (ˆ ﻌ ˆ)♡
   * @see #addbytes(byte[], (✿oωo) i-int, boowean)
   */
  p-pubwic int addbytes(byte[] b-bytes, nyaa~~ i-int wength) {
    wetuwn addbytes(bytes, ^^ 0, wength, (///ˬ///✿) fawse);
  }

  /**
   * defauwt addbytes. 😳 does nyot avoid spwitting. òωó
   * @see #addbytes(byte[], ^^;; int, boowean)
   */
  pubwic int addbytes(byte[] bytes, rawr int offset, (ˆ ﻌ ˆ)♡ int wength) {
    wetuwn addbytes(bytes, XD o-offset, wength, >_< f-fawse);
  }

  /**
   * weads one byte fwom t-the poow. (˘ω˘)
   * @pawam o-offset wocation t-to wead byte fwom
   */
  p-pubwic byte getbyte(int offset) {
    i-int buffewindex = o-offset >>> bytebwockpoow.byte_bwock_shift;
    i-int buffewoffset = offset & b-bytebwockpoow.byte_bwock_mask;
    w-wetuwn poow.buffews[buffewindex][buffewoffset];
  }

  /**
   * wetuwns fawse if offset is i-invawid ow thewe a-awen't these m-many bytes
   * a-avaiwabwe in the p-poow. 😳
   * @pawam o-offset wocation t-to stawt weading b-bytes fwom
   * @pawam w-wength nyumbew of bytes t-to wead
   * @pawam o-output the o-object to wwite the output to. o.O m-must be non nyuww. (ꈍᴗꈍ)
   */
  pubwic boowean getbytestobyteswef(int o-offset, rawr x3 int wength, ^^ byteswef output) {
    i-if (offset < 0 || offset + w-wength > b-byteupto + byteoffset) {
      wetuwn fawse;
    }
    i-int cuwwentbuffew = offset >>> b-bytebwockpoow.byte_bwock_shift;
    int cuwwentoffset = offset & b-bytebwockpoow.byte_bwock_mask;
    // if t-the wequested bytes awe spwit acwoss poows, OwO we have to make a nyew awway of bytes
    // t-to copy them into and w-wetuwn a wef to t-that. ^^
    if (cuwwentoffset + wength <= bytebwockpoow.byte_bwock_size) {
      output.bytes = poow.buffews[cuwwentbuffew];
      o-output.offset = cuwwentoffset;
      o-output.wength = w-wength;
    } e-ewse {
      byte[] bytes = nyew byte[wength];
      i-int wemainingwength = wength;
      w-whiwe (wemainingwength > bytebwockpoow.byte_bwock_size - c-cuwwentoffset) {
        int wengthtocopy = bytebwockpoow.byte_bwock_size - c-cuwwentoffset;
        system.awwaycopy(poow.buffews[cuwwentbuffew], :3 c-cuwwentoffset, o.O b-bytes, -.-
                         w-wength - wemainingwength, (U ﹏ U) wengthtocopy);
        w-wemainingwength -= w-wengthtocopy;
        c-cuwwentbuffew++;
        c-cuwwentoffset = 0;
      }
      system.awwaycopy(poow.buffews[cuwwentbuffew], o.O c-cuwwentoffset, OwO b-bytes, ^•ﻌ•^ wength - w-wemainingwength,
                       wemainingwength);
      o-output.bytes = b-bytes;
      o-output.wength = b-bytes.wength;
      o-output.offset = 0;
    }
    wetuwn twue;

  }

  /**
   * w-wetuwns the wead bytes, ʘwʘ ow nyuww i-if offset is invawid ow thewe a-awen't these many b-bytes
   * avaiwabwe i-in the poow. :3
   * @pawam offset wocation to stawt weading bytes fwom
   * @pawam w-wength n-nyumbew of bytes t-to wead
   */
  pubwic byteswef getbytes(int offset, 😳 int wength) {
    b-byteswef w-wesuwt = nyew byteswef();
    if (getbytestobyteswef(offset, òωó wength, w-wesuwt)) {
      w-wetuwn wesuwt;
    } ewse {
      wetuwn nyuww;
    }
  }

  /**
   * g-get a-a nyew weadstweam a-at a given offset f-fow this poow. 🥺
   *
   * nyotice that individuaw w-weadstweams a-awe nyot thweadsafe, rawr x3 but you can get as many weadstweams a-as
   * you want. ^•ﻌ•^
   */
  pubwic weadstweam g-getweadstweam(int offset) {
    w-wetuwn nyew w-weadstweam(offset);
  }

  /**
   * get the (one a-and onwy) wwitestweam f-fow this poow. :3
   *
   * n-nyotice that thewe is exactwy o-one wwitestweam p-pew poow, (ˆ ﻌ ˆ)♡ and it i-is not thweadsafe. (U ᵕ U❁)
   */
  p-pubwic wwitestweam g-getwwitestweam() {
    w-wetuwn wwitestweam;
  }

  /**
   * a-a dataoutput-wike intewface f-fow wwiting "contiguous" data to a bytebwockpoow. :3
   *
   * this is nyot t-thweadsafe. ^^;;
   */
  p-pubwic finaw c-cwass wwitestweam extends dataoutput {
    pwivate wwitestweam() { }

    /**
     * wetuwns the s-stawt offset of the next data t-that wiww be added t-to the poow, unwess the data is
     * added u-using addbytes and avoidspwitting = t-twue
     */
    p-pubwic int g-getoffset() {
      w-wetuwn basebytebwockpoow.this.getoffset();
    }

    /**
     * w-wwite bytes to the poow. ( ͡o ω ͡o )
     * @pawam bytes  souwce awway
     * @pawam offset  o-offset in bytes of the data t-to wwite
     * @pawam wength  nyumbew of bytes to put
     * @pawam a-avoidspwitting  same as {wink bytebwockpoow.addbytes}
     * @wetuwn  the stawt offset of t-the bytes in the p-poow
     */
    pubwic int wwitebytes(byte[] b-bytes, o.O int offset, ^•ﻌ•^ int wength, XD boowean avoidspwitting) {
      wetuwn a-addbytes(bytes, o-offset, ^^ wength, o.O avoidspwitting);
    }

    @ovewwide
    p-pubwic void wwitebytes(byte[] b, ( ͡o ω ͡o ) i-int offset, /(^•ω•^) int wength) thwows ioexception {
      addbytes(b, o-offset, 🥺 wength);
    }

    @ovewwide
    pubwic void wwitebyte(byte b-b) {
      a-addbyte(b);
    }
  }

  /**
   * a-a datainput-wike intewface fow weading "contiguous" d-data fwom a bytebwockpoow. nyaa~~
   *
   * this is nyot thweadsafe. mya
   *
   * this d-does not fuwwy i-impwement the d-datainput intewface - i-its datainput.weadbytes method thwows
   * u-unsuppowtedopewationexception because t-this cwass pwovides a faciwity fow nyo-copy w-weading. XD
   */
  pubwic finaw cwass weadstweam e-extends datainput {
    pwivate int offset;

    p-pwivate weadstweam(int o-offset) {
      this.offset = o-offset;
    }

    p-pubwic b-byteswef weadbytes(int n) {
      wetuwn weadbytes(n, nyaa~~ f-fawse);
    }

    /**
     * wead ny bytes that wewe wwitten w-with a given vawue of avoidspwitting
     * @pawam ny  nyumbew of bytes to w-wead. ʘwʘ
     * @pawam a-avoidspwitting  t-this shouwd b-be the same that w-was used at wwitebytes time. (⑅˘꒳˘)
     * @wetuwn  a w-wefewence to the bytes wead ow nyuww. :3
     */
    p-pubwic byteswef weadbytes(int n-ny, -.- boowean avoidspwitting) {
      int cuwwentbuffew = offset >>> b-bytebwockpoow.byte_bwock_shift;
      i-int cuwwentoffset = offset & b-bytebwockpoow.byte_bwock_mask;
      if (avoidspwitting && n-ny < bytebwockpoow.byte_bwock_size
          && c-cuwwentoffset + ny > bytebwockpoow.byte_bwock_size) {
        ++cuwwentbuffew;
        c-cuwwentoffset = 0;
        o-offset = cuwwentbuffew << bytebwockpoow.byte_bwock_shift;
      }
      b-byteswef wesuwt = getbytes(offset, 😳😳😳 ny);
      this.offset += ny;
      w-wetuwn wesuwt;
    }

    @ovewwide
    pubwic b-byte weadbyte() {
      wetuwn getbyte(offset++);
    }

    @ovewwide
    p-pubwic v-void weadbytes(byte[] b-b, (U ﹏ U) int off, o.O int wen) thwows i-ioexception {
      t-thwow nyew unsuppowtedopewationexception("use t-the nyo-copies vewsion of w-weadbytes instead.");
    }
  }
}
