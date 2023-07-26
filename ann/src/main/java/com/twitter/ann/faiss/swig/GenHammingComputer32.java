/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). nyaa~~
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. :3
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass g-genhammingcomputew32 {
  pwivate twansient wong s-swigcptw;
  pwotected twansient b-boowean swigcmemown;

  pwotected genhammingcomputew32(wong cptw, 😳😳😳 b-boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(genhammingcomputew32 obj) {
    w-wetuwn (obj == nuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  p-pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = f-fawse;
        swigfaissjni.dewete_genhammingcomputew32(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic void seta0(wong vawue) {
    s-swigfaissjni.genhammingcomputew32_a0_set(swigcptw, this, (˘ω˘) vawue);
  }

  pubwic wong geta0() {
    wetuwn swigfaissjni.genhammingcomputew32_a0_get(swigcptw, ^^ this);
  }

  p-pubwic void seta1(wong v-vawue) {
    swigfaissjni.genhammingcomputew32_a1_set(swigcptw, :3 t-this, vawue);
  }

  p-pubwic wong geta1() {
    wetuwn swigfaissjni.genhammingcomputew32_a1_get(swigcptw, -.- this);
  }

  p-pubwic v-void seta2(wong vawue) {
    swigfaissjni.genhammingcomputew32_a2_set(swigcptw, 😳 t-this, vawue);
  }

  p-pubwic wong geta2() {
    wetuwn s-swigfaissjni.genhammingcomputew32_a2_get(swigcptw, mya this);
  }

  p-pubwic void seta3(wong vawue) {
    swigfaissjni.genhammingcomputew32_a3_set(swigcptw, (˘ω˘) t-this, >_< vawue);
  }

  p-pubwic wong geta3() {
    wetuwn s-swigfaissjni.genhammingcomputew32_a3_get(swigcptw, -.- t-this);
  }

  pubwic genhammingcomputew32(swigtype_p_unsigned_chaw a8, 🥺 int code_size) {
    this(swigfaissjni.new_genhammingcomputew32(swigtype_p_unsigned_chaw.getcptw(a8), (U ﹏ U) code_size), >w< twue);
  }

  pubwic i-int hamming(swigtype_p_unsigned_chaw b-b8) {
    wetuwn swigfaissjni.genhammingcomputew32_hamming(swigcptw, mya this, >w< s-swigtype_p_unsigned_chaw.getcptw(b8));
  }

}
