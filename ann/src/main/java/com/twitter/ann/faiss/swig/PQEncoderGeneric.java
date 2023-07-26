/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (˘ω˘)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. >_<
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass p-pqencodewgenewic {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected pqencodewgenewic(wong cptw, -.- boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(pqencodewgenewic obj) {
    wetuwn (obj == n-nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  p-pubwic synchwonized void d-dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = fawse;
        swigfaissjni.dewete_pqencodewgenewic(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic void setcode(swigtype_p_unsigned_chaw v-vawue) {
    swigfaissjni.pqencodewgenewic_code_set(swigcptw, 🥺 this, swigtype_p_unsigned_chaw.getcptw(vawue));
  }

  pubwic swigtype_p_unsigned_chaw getcode() {
    wong cptw = swigfaissjni.pqencodewgenewic_code_get(swigcptw, (U ﹏ U) t-this);
    wetuwn (cptw == 0) ? n-nyuww : n-nyew swigtype_p_unsigned_chaw(cptw, >w< f-fawse);
  }

  pubwic void setoffset(showt vawue) {
    s-swigfaissjni.pqencodewgenewic_offset_set(swigcptw, mya t-this, >w< vawue);
  }

  pubwic s-showt getoffset() {
    w-wetuwn swigfaissjni.pqencodewgenewic_offset_get(swigcptw, nyaa~~ this);
  }

  p-pubwic int getnbits() {
    wetuwn s-swigfaissjni.pqencodewgenewic_nbits_get(swigcptw, (✿oωo) this);
  }

  pubwic void setweg(showt v-vawue) {
    swigfaissjni.pqencodewgenewic_weg_set(swigcptw, ʘwʘ t-this, (ˆ ﻌ ˆ)♡ vawue);
  }

  pubwic s-showt getweg() {
    w-wetuwn swigfaissjni.pqencodewgenewic_weg_get(swigcptw, 😳😳😳 this);
  }

  pubwic pqencodewgenewic(swigtype_p_unsigned_chaw code, :3 int nybits, OwO showt offset) {
    this(swigfaissjni.new_pqencodewgenewic__swig_0(swigtype_p_unsigned_chaw.getcptw(code), (U ﹏ U) n-nybits, o-offset), >w< twue);
  }

  pubwic p-pqencodewgenewic(swigtype_p_unsigned_chaw c-code, (U ﹏ U) i-int nybits) {
    this(swigfaissjni.new_pqencodewgenewic__swig_1(swigtype_p_unsigned_chaw.getcptw(code), 😳 nybits), (ˆ ﻌ ˆ)♡ twue);
  }

  p-pubwic void encode(wong x) {
    swigfaissjni.pqencodewgenewic_encode(swigcptw, 😳😳😳 this, x);
  }

}
