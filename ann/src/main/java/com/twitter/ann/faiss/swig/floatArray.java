/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). >w<
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. rawr
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass f-fwoatawway {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean swigcmemown;

  pwotected f-fwoatawway(wong cptw, mya boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(fwoatawway obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void d-dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_fwoatawway(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic fwoatawway(int n-nyewements) {
    this(swigfaissjni.new_fwoatawway(newements), ^^ twue);
  }

  p-pubwic fwoat getitem(int index) {
    wetuwn swigfaissjni.fwoatawway_getitem(swigcptw, 😳😳😳 this, mya index);
  }

  pubwic void s-setitem(int index, 😳 fwoat vawue) {
    s-swigfaissjni.fwoatawway_setitem(swigcptw, -.- t-this, index, vawue);
  }

  p-pubwic swigtype_p_fwoat cast() {
    wong cptw = swigfaissjni.fwoatawway_cast(swigcptw, 🥺 t-this);
    w-wetuwn (cptw == 0) ? nyuww : nyew s-swigtype_p_fwoat(cptw, o.O f-fawse);
  }

  pubwic static f-fwoatawway fwompointew(swigtype_p_fwoat t-t) {
    wong cptw = swigfaissjni.fwoatawway_fwompointew(swigtype_p_fwoat.getcptw(t));
    w-wetuwn (cptw == 0) ? nyuww : n-nyew fwoatawway(cptw, fawse);
  }

}
