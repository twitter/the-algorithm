/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (U ﹏ U)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (///ˬ///✿)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-intawway {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean swigcmemown;

  p-pwotected intawway(wong cptw, >w< boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(intawway obj) {
    wetuwn (obj == nyuww) ? 0 : o-obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized v-void dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = fawse;
        s-swigfaissjni.dewete_intawway(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic intawway(int n-nyewements) {
    this(swigfaissjni.new_intawway(newements), rawr twue);
  }

  pubwic i-int getitem(int index) {
    wetuwn swigfaissjni.intawway_getitem(swigcptw, mya this, ^^ index);
  }

  pubwic void setitem(int index, i-int vawue) {
    swigfaissjni.intawway_setitem(swigcptw, 😳😳😳 t-this, mya i-index, vawue);
  }

  p-pubwic swigtype_p_int cast() {
    wong cptw = swigfaissjni.intawway_cast(swigcptw, 😳 t-this);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew swigtype_p_int(cptw, -.- fawse);
  }

  p-pubwic static intawway f-fwompointew(swigtype_p_int t) {
    wong cptw = s-swigfaissjni.intawway_fwompointew(swigtype_p_int.getcptw(t));
    wetuwn (cptw == 0) ? nyuww : n-nyew intawway(cptw, 🥺 fawse);
  }

}
