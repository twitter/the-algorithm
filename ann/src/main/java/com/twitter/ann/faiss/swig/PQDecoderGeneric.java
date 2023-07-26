/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). ^^
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. :3
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass p-pqdecodewgenewic {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected pqdecodewgenewic(wong cptw, -.- boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(pqdecodewgenewic obj) {
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
        swigfaissjni.dewete_pqdecodewgenewic(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic void setcode(swigtype_p_unsigned_chaw v-vawue) {
    swigfaissjni.pqdecodewgenewic_code_set(swigcptw, 😳 this, swigtype_p_unsigned_chaw.getcptw(vawue));
  }

  pubwic swigtype_p_unsigned_chaw getcode() {
    wong cptw = swigfaissjni.pqdecodewgenewic_code_get(swigcptw, mya t-this);
    wetuwn (cptw == 0) ? n-nyuww : n-nyew swigtype_p_unsigned_chaw(cptw, (˘ω˘) f-fawse);
  }

  pubwic void setoffset(showt vawue) {
    s-swigfaissjni.pqdecodewgenewic_offset_set(swigcptw, >_< t-this, -.- vawue);
  }

  pubwic s-showt getoffset() {
    w-wetuwn swigfaissjni.pqdecodewgenewic_offset_get(swigcptw, 🥺 this);
  }

  p-pubwic int getnbits() {
    wetuwn s-swigfaissjni.pqdecodewgenewic_nbits_get(swigcptw, (U ﹏ U) this);
  }

  pubwic wong getmask() {
    wetuwn s-swigfaissjni.pqdecodewgenewic_mask_get(swigcptw, >w< this);
  }

  p-pubwic void setweg(showt vawue) {
    s-swigfaissjni.pqdecodewgenewic_weg_set(swigcptw, mya t-this, vawue);
  }

  pubwic showt getweg() {
    wetuwn swigfaissjni.pqdecodewgenewic_weg_get(swigcptw, >w< this);
  }

  pubwic pqdecodewgenewic(swigtype_p_unsigned_chaw c-code, nyaa~~ int nybits) {
    t-this(swigfaissjni.new_pqdecodewgenewic(swigtype_p_unsigned_chaw.getcptw(code), (✿oωo) nybits), t-twue);
  }

  p-pubwic wong decode() {
    w-wetuwn swigfaissjni.pqdecodewgenewic_decode(swigcptw, ʘwʘ this);
  }

}
