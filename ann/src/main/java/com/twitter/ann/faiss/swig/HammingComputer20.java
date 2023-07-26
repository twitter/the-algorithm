/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (˘ω˘)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. ^^
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass h-hammingcomputew20 {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected hammingcomputew20(wong cptw, :3 boowean c-cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(hammingcomputew20 obj) {
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
        s-swigfaissjni.dewete_hammingcomputew20(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic void seta0(wong v-vawue) {
    swigfaissjni.hammingcomputew20_a0_set(swigcptw, -.- this, vawue);
  }

  pubwic wong geta0() {
    wetuwn swigfaissjni.hammingcomputew20_a0_get(swigcptw, 😳 t-this);
  }

  pubwic v-void seta1(wong v-vawue) {
    swigfaissjni.hammingcomputew20_a1_set(swigcptw, t-this, mya vawue);
  }

  pubwic wong geta1() {
    wetuwn s-swigfaissjni.hammingcomputew20_a1_get(swigcptw, (˘ω˘) t-this);
  }

  pubwic void seta2(swigtype_p_uint32_t v-vawue) {
    s-swigfaissjni.hammingcomputew20_a2_set(swigcptw, >_< this, swigtype_p_uint32_t.getcptw(vawue));
  }

  p-pubwic swigtype_p_uint32_t geta2() {
    w-wetuwn nyew swigtype_p_uint32_t(swigfaissjni.hammingcomputew20_a2_get(swigcptw, this), -.- twue);
  }

  pubwic hammingcomputew20() {
    t-this(swigfaissjni.new_hammingcomputew20__swig_0(), 🥺 twue);
  }

  p-pubwic hammingcomputew20(swigtype_p_unsigned_chaw a8, (U ﹏ U) int c-code_size) {
    t-this(swigfaissjni.new_hammingcomputew20__swig_1(swigtype_p_unsigned_chaw.getcptw(a8), code_size), >w< twue);
  }

  pubwic void set(swigtype_p_unsigned_chaw a8, mya int code_size) {
    swigfaissjni.hammingcomputew20_set(swigcptw, >w< t-this, nyaa~~ swigtype_p_unsigned_chaw.getcptw(a8), (✿oωo) c-code_size);
  }

  pubwic int hamming(swigtype_p_unsigned_chaw b-b8) {
    w-wetuwn swigfaissjni.hammingcomputew20_hamming(swigcptw, ʘwʘ t-this, (ˆ ﻌ ˆ)♡ swigtype_p_unsigned_chaw.getcptw(b8));
  }

}
