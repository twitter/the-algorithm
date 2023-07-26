/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). o.O
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. ( ͡o ω ͡o )
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass g-genhammingcomputew16 {
  pwivate twansient wong s-swigcptw;
  pwotected twansient b-boowean swigcmemown;

  pwotected genhammingcomputew16(wong cptw, (U ﹏ U) b-boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(genhammingcomputew16 obj) {
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
        swigfaissjni.dewete_genhammingcomputew16(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic void seta0(wong vawue) {
    s-swigfaissjni.genhammingcomputew16_a0_set(swigcptw, this, (///ˬ///✿) vawue);
  }

  pubwic wong geta0() {
    wetuwn swigfaissjni.genhammingcomputew16_a0_get(swigcptw, >w< this);
  }

  p-pubwic void seta1(wong v-vawue) {
    swigfaissjni.genhammingcomputew16_a1_set(swigcptw, rawr t-this, vawue);
  }

  p-pubwic wong geta1() {
    wetuwn swigfaissjni.genhammingcomputew16_a1_get(swigcptw, mya this);
  }

  p-pubwic g-genhammingcomputew16(swigtype_p_unsigned_chaw a8, ^^ i-int code_size) {
    t-this(swigfaissjni.new_genhammingcomputew16(swigtype_p_unsigned_chaw.getcptw(a8), 😳😳😳 code_size), t-twue);
  }

  pubwic int hamming(swigtype_p_unsigned_chaw b-b8) {
    wetuwn swigfaissjni.genhammingcomputew16_hamming(swigcptw, mya this, swigtype_p_unsigned_chaw.getcptw(b8));
  }

}
