/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). o.O
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. /(^•ω•^)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass o-opewatingpointvectow {
  pwivate twansient wong s-swigcptw;
  pwotected twansient b-boowean swigcmemown;

  pwotected opewatingpointvectow(wong cptw, nyaa~~ b-boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(opewatingpointvectow obj) {
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
        swigfaissjni.dewete_opewatingpointvectow(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic opewatingpointvectow() {
    this(swigfaissjni.new_opewatingpointvectow(), nyaa~~ twue);
  }

  p-pubwic void push_back(opewatingpoint awg0) {
    swigfaissjni.opewatingpointvectow_push_back(swigcptw, this, :3 opewatingpoint.getcptw(awg0), 😳😳😳 awg0);
  }

  pubwic void c-cweaw() {
    swigfaissjni.opewatingpointvectow_cweaw(swigcptw, t-this);
  }

  p-pubwic opewatingpoint d-data() {
    wong cptw = swigfaissjni.opewatingpointvectow_data(swigcptw, (˘ω˘) this);
    wetuwn (cptw == 0) ? nuww : nyew opewatingpoint(cptw, ^^ f-fawse);
  }

  p-pubwic wong size() {
    wetuwn s-swigfaissjni.opewatingpointvectow_size(swigcptw, :3 t-this);
  }

  pubwic opewatingpoint a-at(wong ny) {
    wetuwn nyew o-opewatingpoint(swigfaissjni.opewatingpointvectow_at(swigcptw, -.- this, ny), twue);
  }

  pubwic v-void wesize(wong ny) {
    swigfaissjni.opewatingpointvectow_wesize(swigcptw, 😳 this, n-n);
  }

  pubwic void wesewve(wong n-ny) {
    s-swigfaissjni.opewatingpointvectow_wesewve(swigcptw, mya this, (˘ω˘) ny);
  }

  pubwic void swap(opewatingpointvectow othew) {
    swigfaissjni.opewatingpointvectow_swap(swigcptw, >_< this, opewatingpointvectow.getcptw(othew), o-othew);
  }

}
