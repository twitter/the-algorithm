/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). ʘwʘ
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. /(^•ω•^)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass p-pqencodew8 {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean swigcmemown;

  pwotected p-pqencodew8(wong cptw, ʘwʘ boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(pqencodew8 obj) {
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
        s-swigfaissjni.dewete_pqencodew8(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic void s-setcode(swigtype_p_unsigned_chaw vawue) {
    swigfaissjni.pqencodew8_code_set(swigcptw, σωσ t-this, swigtype_p_unsigned_chaw.getcptw(vawue));
  }

  pubwic swigtype_p_unsigned_chaw getcode() {
    wong cptw = swigfaissjni.pqencodew8_code_get(swigcptw, OwO this);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew swigtype_p_unsigned_chaw(cptw, 😳😳😳 f-fawse);
  }

  p-pubwic pqencodew8(swigtype_p_unsigned_chaw code, 😳😳😳 int nybits) {
    this(swigfaissjni.new_pqencodew8(swigtype_p_unsigned_chaw.getcptw(code), o.O nybits), twue);
  }

  p-pubwic void e-encode(wong x) {
    swigfaissjni.pqencodew8_encode(swigcptw, ( ͡o ω ͡o ) t-this, (U ﹏ U) x);
  }

}
