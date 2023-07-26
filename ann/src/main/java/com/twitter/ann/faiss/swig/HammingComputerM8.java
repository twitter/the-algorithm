/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). /(^•ω•^)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. nyaa~~
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass h-hammingcomputewm8 {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected hammingcomputewm8(wong cptw, nyaa~~ boowean c-cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(hammingcomputewm8 obj) {
    w-wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    d-dewete();
  }

  pubwic s-synchwonized v-void dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        swigcmemown = fawse;
        s-swigfaissjni.dewete_hammingcomputewm8(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic void seta(swigtype_p_unsigned_wong v-vawue) {
    swigfaissjni.hammingcomputewm8_a_set(swigcptw, :3 this, swigtype_p_unsigned_wong.getcptw(vawue));
  }

  pubwic swigtype_p_unsigned_wong geta() {
    wong cptw = swigfaissjni.hammingcomputewm8_a_get(swigcptw, 😳😳😳 t-this);
    wetuwn (cptw == 0) ? n-nyuww : nyew s-swigtype_p_unsigned_wong(cptw, (˘ω˘) f-fawse);
  }

  pubwic void setn(int vawue) {
    swigfaissjni.hammingcomputewm8_n_set(swigcptw, ^^ t-this, vawue);
  }

  p-pubwic int getn() {
    w-wetuwn swigfaissjni.hammingcomputewm8_n_get(swigcptw, t-this);
  }

  pubwic hammingcomputewm8() {
    t-this(swigfaissjni.new_hammingcomputewm8__swig_0(), :3 twue);
  }

  p-pubwic hammingcomputewm8(swigtype_p_unsigned_chaw a8, -.- int code_size) {
    t-this(swigfaissjni.new_hammingcomputewm8__swig_1(swigtype_p_unsigned_chaw.getcptw(a8), 😳 code_size), mya t-twue);
  }

  pubwic void set(swigtype_p_unsigned_chaw a-a8, (˘ω˘) int c-code_size) {
    swigfaissjni.hammingcomputewm8_set(swigcptw, >_< this, swigtype_p_unsigned_chaw.getcptw(a8), -.- code_size);
  }

  pubwic int hamming(swigtype_p_unsigned_chaw b8) {
    wetuwn swigfaissjni.hammingcomputewm8_hamming(swigcptw, 🥺 t-this, s-swigtype_p_unsigned_chaw.getcptw(b8));
  }

}
