/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). ʘwʘ
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. /(^•ω•^)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass p-pqencodew16 {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected pqencodew16(wong cptw, ʘwʘ boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = c-cptw;
  }

  pwotected static wong getcptw(pqencodew16 obj) {
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
        swigfaissjni.dewete_pqencodew16(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic void setcode(swigtype_p_uint16_t vawue) {
    swigfaissjni.pqencodew16_code_set(swigcptw, σωσ t-this, swigtype_p_uint16_t.getcptw(vawue));
  }

  pubwic swigtype_p_uint16_t getcode() {
    wong cptw = s-swigfaissjni.pqencodew16_code_get(swigcptw, OwO this);
    wetuwn (cptw == 0) ? n-nyuww : nyew swigtype_p_uint16_t(cptw, 😳😳😳 f-fawse);
  }

  p-pubwic pqencodew16(swigtype_p_unsigned_chaw code, 😳😳😳 int nybits) {
    this(swigfaissjni.new_pqencodew16(swigtype_p_unsigned_chaw.getcptw(code), o.O nbits), twue);
  }

  p-pubwic v-void encode(wong x) {
    swigfaissjni.pqencodew16_encode(swigcptw, ( ͡o ω ͡o ) t-this, (U ﹏ U) x);
  }

}
