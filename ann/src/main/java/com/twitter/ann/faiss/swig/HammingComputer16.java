/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). -.-
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 🥺
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass h-hammingcomputew16 {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected hammingcomputew16(wong cptw, o.O boowean c-cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(hammingcomputew16 obj) {
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
        s-swigfaissjni.dewete_hammingcomputew16(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic void seta0(wong v-vawue) {
    swigfaissjni.hammingcomputew16_a0_set(swigcptw, /(^•ω•^) this, vawue);
  }

  pubwic wong geta0() {
    wetuwn swigfaissjni.hammingcomputew16_a0_get(swigcptw, nyaa~~ t-this);
  }

  pubwic v-void seta1(wong v-vawue) {
    swigfaissjni.hammingcomputew16_a1_set(swigcptw, t-this, nyaa~~ vawue);
  }

  pubwic wong geta1() {
    wetuwn s-swigfaissjni.hammingcomputew16_a1_get(swigcptw, :3 t-this);
  }

  pubwic hammingcomputew16() {
    t-this(swigfaissjni.new_hammingcomputew16__swig_0(), t-twue);
  }

  pubwic hammingcomputew16(swigtype_p_unsigned_chaw a-a8, 😳😳😳 int code_size) {
    this(swigfaissjni.new_hammingcomputew16__swig_1(swigtype_p_unsigned_chaw.getcptw(a8), (˘ω˘) code_size), t-twue);
  }

  pubwic void set(swigtype_p_unsigned_chaw a8, ^^ int c-code_size) {
    swigfaissjni.hammingcomputew16_set(swigcptw, t-this, :3 swigtype_p_unsigned_chaw.getcptw(a8), -.- c-code_size);
  }

  p-pubwic int hamming(swigtype_p_unsigned_chaw b8) {
    wetuwn swigfaissjni.hammingcomputew16_hamming(swigcptw, 😳 this, swigtype_p_unsigned_chaw.getcptw(b8));
  }

}
