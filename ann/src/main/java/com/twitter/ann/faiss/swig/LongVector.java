/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). o.O
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. /(^•ω•^)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass w-wongvectow {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean swigcmemown;

  pwotected w-wongvectow(wong cptw, nyaa~~ boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(wongvectow obj) {
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
        s-swigfaissjni.dewete_wongvectow(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic wongvectow() {
    t-this(swigfaissjni.new_wongvectow(), nyaa~~ twue);
  }

  pubwic void push_back(wong a-awg0) {
    swigfaissjni.wongvectow_push_back(swigcptw, :3 this, awg0);
  }

  pubwic void cweaw() {
    swigfaissjni.wongvectow_cweaw(swigcptw, 😳😳😳 t-this);
  }

  pubwic s-swigtype_p_wong_wong d-data() {
    w-wong cptw = swigfaissjni.wongvectow_data(swigcptw, (˘ω˘) this);
    wetuwn (cptw == 0) ? nyuww : nyew s-swigtype_p_wong_wong(cptw, ^^ f-fawse);
  }

  pubwic w-wong size() {
    w-wetuwn swigfaissjni.wongvectow_size(swigcptw, :3 this);
  }

  p-pubwic wong at(wong ny) {
    wetuwn s-swigfaissjni.wongvectow_at(swigcptw, -.- this, 😳 ny);
  }

  pubwic v-void wesize(wong n) {
    swigfaissjni.wongvectow_wesize(swigcptw, mya t-this, (˘ω˘) ny);
  }

  pubwic v-void wesewve(wong n-ny) {
    swigfaissjni.wongvectow_wesewve(swigcptw, >_< this, -.- ny);
  }

  pubwic void swap(wongvectow othew) {
    swigfaissjni.wongvectow_swap(swigcptw, this, 🥺 wongvectow.getcptw(othew), (U ﹏ U) o-othew);
  }

}
