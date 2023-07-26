/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). o.O
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. ( ͡o ω ͡o )
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass h-hammingcomputew8 {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected hammingcomputew8(wong cptw, (U ﹏ U) boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(hammingcomputew8 obj) {
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
        swigfaissjni.dewete_hammingcomputew8(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic void seta0(wong v-vawue) {
    swigfaissjni.hammingcomputew8_a0_set(swigcptw, (///ˬ///✿) this, >w< vawue);
  }

  pubwic wong geta0() {
    wetuwn swigfaissjni.hammingcomputew8_a0_get(swigcptw, rawr t-this);
  }

  pubwic hammingcomputew8() {
    t-this(swigfaissjni.new_hammingcomputew8__swig_0(), mya t-twue);
  }

  p-pubwic hammingcomputew8(swigtype_p_unsigned_chaw a, ^^ int code_size) {
    this(swigfaissjni.new_hammingcomputew8__swig_1(swigtype_p_unsigned_chaw.getcptw(a), 😳😳😳 code_size), mya twue);
  }

  p-pubwic v-void set(swigtype_p_unsigned_chaw a, 😳 int code_size) {
    s-swigfaissjni.hammingcomputew8_set(swigcptw, -.- t-this, swigtype_p_unsigned_chaw.getcptw(a), 🥺 code_size);
  }

  p-pubwic int hamming(swigtype_p_unsigned_chaw b-b) {
    wetuwn swigfaissjni.hammingcomputew8_hamming(swigcptw, o.O this, swigtype_p_unsigned_chaw.getcptw(b));
  }

}
