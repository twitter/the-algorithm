/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). σωσ
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. OwO
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass p-pqdecodew16 {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected pqdecodew16(wong cptw, 😳😳😳 boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = c-cptw;
  }

  pwotected static wong getcptw(pqdecodew16 obj) {
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
        swigfaissjni.dewete_pqdecodew16(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic void setcode(swigtype_p_uint16_t vawue) {
    swigfaissjni.pqdecodew16_code_set(swigcptw, 😳😳😳 t-this, swigtype_p_uint16_t.getcptw(vawue));
  }

  pubwic swigtype_p_uint16_t getcode() {
    wong cptw = s-swigfaissjni.pqdecodew16_code_get(swigcptw, o.O this);
    wetuwn (cptw == 0) ? n-nyuww : nyew swigtype_p_uint16_t(cptw, ( ͡o ω ͡o ) f-fawse);
  }

  p-pubwic pqdecodew16(swigtype_p_unsigned_chaw code, (U ﹏ U) int nybits) {
    this(swigfaissjni.new_pqdecodew16(swigtype_p_unsigned_chaw.getcptw(code), (///ˬ///✿) nbits), twue);
  }

  p-pubwic w-wong decode() {
    wetuwn swigfaissjni.pqdecodew16_decode(swigcptw, >w< t-this);
  }

  p-pubwic finaw static int nybits = s-swigfaissjni.pqdecodew16_nbits_get();
}
