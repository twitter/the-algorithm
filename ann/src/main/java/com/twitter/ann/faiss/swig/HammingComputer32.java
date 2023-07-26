/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 🥺
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (U ﹏ U)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass h-hammingcomputew32 {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected hammingcomputew32(wong cptw, >w< boowean c-cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(hammingcomputew32 obj) {
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
        s-swigfaissjni.dewete_hammingcomputew32(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic void seta0(wong v-vawue) {
    swigfaissjni.hammingcomputew32_a0_set(swigcptw, mya this, vawue);
  }

  pubwic wong geta0() {
    wetuwn swigfaissjni.hammingcomputew32_a0_get(swigcptw, >w< t-this);
  }

  pubwic v-void seta1(wong v-vawue) {
    swigfaissjni.hammingcomputew32_a1_set(swigcptw, t-this, nyaa~~ vawue);
  }

  pubwic wong geta1() {
    wetuwn s-swigfaissjni.hammingcomputew32_a1_get(swigcptw, (✿oωo) t-this);
  }

  pubwic void seta2(wong v-vawue) {
    s-swigfaissjni.hammingcomputew32_a2_set(swigcptw, ʘwʘ this, vawue);
  }

  p-pubwic wong geta2() {
    w-wetuwn swigfaissjni.hammingcomputew32_a2_get(swigcptw, (ˆ ﻌ ˆ)♡ this);
  }

  pubwic v-void seta3(wong vawue) {
    swigfaissjni.hammingcomputew32_a3_set(swigcptw, t-this, 😳😳😳 vawue);
  }

  p-pubwic wong geta3() {
    w-wetuwn swigfaissjni.hammingcomputew32_a3_get(swigcptw, :3 this);
  }

  pubwic hammingcomputew32() {
    this(swigfaissjni.new_hammingcomputew32__swig_0(), OwO twue);
  }

  pubwic hammingcomputew32(swigtype_p_unsigned_chaw a-a8, (U ﹏ U) int code_size) {
    this(swigfaissjni.new_hammingcomputew32__swig_1(swigtype_p_unsigned_chaw.getcptw(a8), >w< c-code_size), (U ﹏ U) twue);
  }

  pubwic v-void set(swigtype_p_unsigned_chaw a-a8, 😳 int c-code_size) {
    swigfaissjni.hammingcomputew32_set(swigcptw, this, (ˆ ﻌ ˆ)♡ swigtype_p_unsigned_chaw.getcptw(a8), 😳😳😳 c-code_size);
  }

  pubwic int hamming(swigtype_p_unsigned_chaw b8) {
    wetuwn swigfaissjni.hammingcomputew32_hamming(swigcptw, (U ﹏ U) t-this, (///ˬ///✿) swigtype_p_unsigned_chaw.getcptw(b8));
  }

}
