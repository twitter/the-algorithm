/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (U ﹏ U)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (///ˬ///✿)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass d-doubweawway {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected doubweawway(wong cptw, >w< boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = c-cptw;
  }

  pwotected static wong getcptw(doubweawway obj) {
    wetuwn (obj == n-nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  p-pubwic synchwonized void d-dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = fawse;
        swigfaissjni.dewete_doubweawway(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic doubweawway(int newements) {
    t-this(swigfaissjni.new_doubweawway(newements), rawr twue);
  }

  pubwic doubwe getitem(int index) {
    wetuwn swigfaissjni.doubweawway_getitem(swigcptw, mya this, index);
  }

  p-pubwic void setitem(int i-index, ^^ doubwe v-vawue) {
    swigfaissjni.doubweawway_setitem(swigcptw, 😳😳😳 t-this, mya index, vawue);
  }

  pubwic swigtype_p_doubwe cast() {
    wong c-cptw = swigfaissjni.doubweawway_cast(swigcptw, 😳 t-this);
    wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_doubwe(cptw, -.- f-fawse);
  }

  p-pubwic static doubweawway fwompointew(swigtype_p_doubwe t-t) {
    wong cptw = s-swigfaissjni.doubweawway_fwompointew(swigtype_p_doubwe.getcptw(t));
    wetuwn (cptw == 0) ? nyuww : n-nyew doubweawway(cptw, 🥺 fawse);
  }

}
