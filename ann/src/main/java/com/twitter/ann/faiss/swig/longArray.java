/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). ( ͡o ω ͡o )
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (U ﹏ U)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass w-wongawway {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean swigcmemown;

  p-pwotected wongawway(wong cptw, (///ˬ///✿) boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = c-cptw;
  }

  pwotected static wong getcptw(wongawway obj) {
    wetuwn (obj == nyuww) ? 0 : o-obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized v-void dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_wongawway(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic wongawway(int n-nyewements) {
    this(swigfaissjni.new_wongawway(newements), >w< twue);
  }

  p-pubwic wong getitem(int index) {
    wetuwn swigfaissjni.wongawway_getitem(swigcptw, rawr this, index);
  }

  pubwic void setitem(int i-index, mya wong vawue) {
    swigfaissjni.wongawway_setitem(swigcptw, ^^ t-this, index, 😳😳😳 v-vawue);
  }

  p-pubwic swigtype_p_wong_wong cast() {
    wong cptw = swigfaissjni.wongawway_cast(swigcptw, mya this);
    w-wetuwn (cptw == 0) ? n-nyuww : nyew swigtype_p_wong_wong(cptw, 😳 f-fawse);
  }

  p-pubwic static wongawway fwompointew(swigtype_p_wong_wong t-t) {
    wong cptw = s-swigfaissjni.wongawway_fwompointew(swigtype_p_wong_wong.getcptw(t));
    wetuwn (cptw == 0) ? nyuww : nyew wongawway(cptw, -.- f-fawse);
  }

}
