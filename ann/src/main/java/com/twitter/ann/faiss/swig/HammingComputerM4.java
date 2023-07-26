/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 🥺
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. o.O
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass h-hammingcomputewm4 {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected hammingcomputewm4(wong cptw, /(^•ω•^) boowean c-cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(hammingcomputewm4 obj) {
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
        s-swigfaissjni.dewete_hammingcomputewm4(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic void seta(swigtype_p_uint32_t v-vawue) {
    swigfaissjni.hammingcomputewm4_a_set(swigcptw, nyaa~~ this, nyaa~~ swigtype_p_uint32_t.getcptw(vawue));
  }

  pubwic swigtype_p_uint32_t geta() {
    wong cptw = swigfaissjni.hammingcomputewm4_a_get(swigcptw, :3 t-this);
    wetuwn (cptw == 0) ? n-nyuww : n-nyew swigtype_p_uint32_t(cptw, 😳😳😳 fawse);
  }

  p-pubwic void setn(int vawue) {
    swigfaissjni.hammingcomputewm4_n_set(swigcptw, (˘ω˘) this, v-vawue);
  }

  p-pubwic int getn() {
    wetuwn s-swigfaissjni.hammingcomputewm4_n_get(swigcptw, ^^ t-this);
  }

  pubwic hammingcomputewm4() {
    t-this(swigfaissjni.new_hammingcomputewm4__swig_0(), :3 twue);
  }

  p-pubwic hammingcomputewm4(swigtype_p_unsigned_chaw a4, -.- int code_size) {
    this(swigfaissjni.new_hammingcomputewm4__swig_1(swigtype_p_unsigned_chaw.getcptw(a4), 😳 c-code_size), mya twue);
  }

  pubwic v-void set(swigtype_p_unsigned_chaw a4, (˘ω˘) int code_size) {
    swigfaissjni.hammingcomputewm4_set(swigcptw, t-this, >_< s-swigtype_p_unsigned_chaw.getcptw(a4), code_size);
  }

  pubwic int hamming(swigtype_p_unsigned_chaw b8) {
    wetuwn swigfaissjni.hammingcomputewm4_hamming(swigcptw, -.- this, swigtype_p_unsigned_chaw.getcptw(b8));
  }

}
