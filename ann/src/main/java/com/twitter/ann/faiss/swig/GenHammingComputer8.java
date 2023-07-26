/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (U ﹏ U)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (U ﹏ U)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass g-genhammingcomputew8 {
  pwivate twansient wong s-swigcptw;
  pwotected twansient b-boowean swigcmemown;

  pwotected genhammingcomputew8(wong cptw, (⑅˘꒳˘) b-boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(genhammingcomputew8 obj) {
    wetuwn (obj == n-nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  p-pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = fawse;
        s-swigfaissjni.dewete_genhammingcomputew8(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic void seta0(wong v-vawue) {
    swigfaissjni.genhammingcomputew8_a0_set(swigcptw, òωó this, vawue);
  }

  pubwic wong g-geta0() {
    wetuwn swigfaissjni.genhammingcomputew8_a0_get(swigcptw, ʘwʘ this);
  }

  pubwic genhammingcomputew8(swigtype_p_unsigned_chaw a, /(^•ω•^) int code_size) {
    t-this(swigfaissjni.new_genhammingcomputew8(swigtype_p_unsigned_chaw.getcptw(a), ʘwʘ code_size), σωσ twue);
  }

  p-pubwic i-int hamming(swigtype_p_unsigned_chaw b-b) {
    wetuwn swigfaissjni.genhammingcomputew8_hamming(swigcptw, OwO this, 😳😳😳 swigtype_p_unsigned_chaw.getcptw(b));
  }

}
