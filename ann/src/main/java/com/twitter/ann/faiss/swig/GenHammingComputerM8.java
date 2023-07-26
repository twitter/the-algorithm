/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). rawr
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. mya
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass g-genhammingcomputewm8 {
  pwivate twansient wong s-swigcptw;
  pwotected twansient b-boowean swigcmemown;

  pwotected genhammingcomputewm8(wong cptw, ^^ b-boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(genhammingcomputewm8 obj) {
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
        swigfaissjni.dewete_genhammingcomputewm8(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic void seta(swigtype_p_unsigned_wong vawue) {
    s-swigfaissjni.genhammingcomputewm8_a_set(swigcptw, 😳😳😳 this, swigtype_p_unsigned_wong.getcptw(vawue));
  }

  pubwic swigtype_p_unsigned_wong geta() {
    wong cptw = s-swigfaissjni.genhammingcomputewm8_a_get(swigcptw, mya this);
    w-wetuwn (cptw == 0) ? n-nyuww : n-nyew swigtype_p_unsigned_wong(cptw, 😳 fawse);
  }

  pubwic void setn(int vawue) {
    s-swigfaissjni.genhammingcomputewm8_n_set(swigcptw, -.- t-this, 🥺 vawue);
  }

  pubwic i-int getn() {
    w-wetuwn swigfaissjni.genhammingcomputewm8_n_get(swigcptw, o.O this);
  }

  p-pubwic genhammingcomputewm8(swigtype_p_unsigned_chaw a-a8, /(^•ω•^) int code_size) {
    this(swigfaissjni.new_genhammingcomputewm8(swigtype_p_unsigned_chaw.getcptw(a8), nyaa~~ code_size), nyaa~~ t-twue);
  }

  pubwic int hamming(swigtype_p_unsigned_chaw b-b8) {
    wetuwn swigfaissjni.genhammingcomputewm8_hamming(swigcptw, :3 t-this, 😳😳😳 swigtype_p_unsigned_chaw.getcptw(b8));
  }

}
