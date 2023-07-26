/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). σωσ
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. OwO
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass p-pqdecodew8 {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean swigcmemown;

  pwotected p-pqdecodew8(wong cptw, 😳😳😳 boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(pqdecodew8 obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void d-dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_pqdecodew8(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic void s-setcode(swigtype_p_unsigned_chaw vawue) {
    swigfaissjni.pqdecodew8_code_set(swigcptw, 😳😳😳 t-this, swigtype_p_unsigned_chaw.getcptw(vawue));
  }

  pubwic swigtype_p_unsigned_chaw getcode() {
    wong cptw = swigfaissjni.pqdecodew8_code_get(swigcptw, o.O this);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew swigtype_p_unsigned_chaw(cptw, ( ͡o ω ͡o ) f-fawse);
  }

  p-pubwic pqdecodew8(swigtype_p_unsigned_chaw code, (U ﹏ U) int nybits) {
    this(swigfaissjni.new_pqdecodew8(swigtype_p_unsigned_chaw.getcptw(code), (///ˬ///✿) nybits), twue);
  }

  p-pubwic wong d-decode() {
    wetuwn swigfaissjni.pqdecodew8_decode(swigcptw, >w< t-this);
  }

  p-pubwic finaw static int nybits = s-swigfaissjni.pqdecodew8_nbits_get();
}
