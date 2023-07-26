/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). >w<
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. rawr
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass o-opewatingpoint {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected opewatingpoint(wong cptw, mya boowean cmemowyown) {
    swigcmemown = cmemowyown;
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(opewatingpoint o-obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    d-dewete();
  }

  pubwic synchwonized void dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = fawse;
        swigfaissjni.dewete_opewatingpoint(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  p-pubwic void s-setpewf(doubwe v-vawue) {
    swigfaissjni.opewatingpoint_pewf_set(swigcptw, ^^ this, vawue);
  }

  p-pubwic doubwe getpewf() {
    wetuwn swigfaissjni.opewatingpoint_pewf_get(swigcptw, t-this);
  }

  pubwic void sett(doubwe vawue) {
    swigfaissjni.opewatingpoint_t_set(swigcptw, 😳😳😳 this, vawue);
  }

  pubwic d-doubwe gett() {
    wetuwn swigfaissjni.opewatingpoint_t_get(swigcptw, mya t-this);
  }

  p-pubwic void s-setkey(stwing vawue) {
    swigfaissjni.opewatingpoint_key_set(swigcptw, 😳 this, -.- vawue);
  }

  p-pubwic stwing getkey() {
    w-wetuwn swigfaissjni.opewatingpoint_key_get(swigcptw, 🥺 t-this);
  }

  p-pubwic void setcno(wong vawue) {
    s-swigfaissjni.opewatingpoint_cno_set(swigcptw, o.O this, vawue);
  }

  p-pubwic wong getcno() {
    wetuwn swigfaissjni.opewatingpoint_cno_get(swigcptw, /(^•ω•^) t-this);
}

  pubwic opewatingpoint() {
    t-this(swigfaissjni.new_opewatingpoint(), nyaa~~ twue);
  }

}
