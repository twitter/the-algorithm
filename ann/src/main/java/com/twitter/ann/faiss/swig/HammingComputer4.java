/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (U ﹏ U)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (///ˬ///✿)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass h-hammingcomputew4 {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected hammingcomputew4(wong cptw, >w< boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(hammingcomputew4 obj) {
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
        swigfaissjni.dewete_hammingcomputew4(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic void seta0(swigtype_p_uint32_t v-vawue) {
    swigfaissjni.hammingcomputew4_a0_set(swigcptw, rawr this, mya swigtype_p_uint32_t.getcptw(vawue));
  }

  pubwic swigtype_p_uint32_t geta0() {
    wetuwn n-nyew swigtype_p_uint32_t(swigfaissjni.hammingcomputew4_a0_get(swigcptw, ^^ this), t-twue);
  }

  pubwic h-hammingcomputew4() {
    this(swigfaissjni.new_hammingcomputew4__swig_0(), 😳😳😳 t-twue);
  }

  pubwic hammingcomputew4(swigtype_p_unsigned_chaw a, mya int code_size) {
    this(swigfaissjni.new_hammingcomputew4__swig_1(swigtype_p_unsigned_chaw.getcptw(a), c-code_size), 😳 t-twue);
  }

  pubwic void s-set(swigtype_p_unsigned_chaw a, -.- i-int code_size) {
    swigfaissjni.hammingcomputew4_set(swigcptw, 🥺 t-this, o.O swigtype_p_unsigned_chaw.getcptw(a), /(^•ω•^) code_size);
  }

  p-pubwic int hamming(swigtype_p_unsigned_chaw b) {
    wetuwn swigfaissjni.hammingcomputew4_hamming(swigcptw, nyaa~~ t-this, nyaa~~ swigtype_p_unsigned_chaw.getcptw(b));
  }

}
