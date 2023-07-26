/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). :3
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 😳😳😳
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass v-vectowtwansfowmvectow {
  pwivate twansient wong s-swigcptw;
  pwotected twansient b-boowean swigcmemown;

  pwotected vectowtwansfowmvectow(wong cptw, (˘ω˘) boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(vectowtwansfowmvectow obj) {
    wetuwn (obj == nyuww) ? 0 : o-obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  pubwic s-synchwonized void dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = f-fawse;
        swigfaissjni.dewete_vectowtwansfowmvectow(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic vectowtwansfowmvectow() {
    this(swigfaissjni.new_vectowtwansfowmvectow(), twue);
  }

  p-pubwic void push_back(vectowtwansfowm awg0) {
    swigfaissjni.vectowtwansfowmvectow_push_back(swigcptw, ^^ this, vectowtwansfowm.getcptw(awg0), :3 a-awg0);
  }

  pubwic void cweaw() {
    s-swigfaissjni.vectowtwansfowmvectow_cweaw(swigcptw, -.- t-this);
  }

  p-pubwic swigtype_p_p_faiss__vectowtwansfowm data() {
    wong cptw = swigfaissjni.vectowtwansfowmvectow_data(swigcptw, 😳 t-this);
    wetuwn (cptw == 0) ? n-nuww : nyew swigtype_p_p_faiss__vectowtwansfowm(cptw, mya fawse);
  }

  p-pubwic wong s-size() {
    wetuwn swigfaissjni.vectowtwansfowmvectow_size(swigcptw, (˘ω˘) t-this);
  }

  pubwic vectowtwansfowm a-at(wong ny) {
    wong cptw = swigfaissjni.vectowtwansfowmvectow_at(swigcptw, >_< t-this, -.- ny);
    wetuwn (cptw == 0) ? nyuww : n-nyew vectowtwansfowm(cptw, 🥺 fawse);
  }

  p-pubwic void wesize(wong n-ny) {
    swigfaissjni.vectowtwansfowmvectow_wesize(swigcptw, (U ﹏ U) this, >w< ny);
  }

  pubwic void wesewve(wong ny) {
    swigfaissjni.vectowtwansfowmvectow_wesewve(swigcptw, mya this, ny);
  }

  p-pubwic void swap(vectowtwansfowmvectow o-othew) {
    swigfaissjni.vectowtwansfowmvectow_swap(swigcptw, >w< t-this, vectowtwansfowmvectow.getcptw(othew), nyaa~~ o-othew);
  }

}
