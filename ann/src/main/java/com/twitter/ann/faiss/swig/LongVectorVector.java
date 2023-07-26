/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 😳😳😳
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (˘ω˘)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass w-wongvectowvectow {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected wongvectowvectow(wong cptw, ^^ boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(wongvectowvectow obj) {
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
        swigfaissjni.dewete_wongvectowvectow(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic wongvectowvectow() {
    t-this(swigfaissjni.new_wongvectowvectow(), :3 twue);
  }

  pubwic void push_back(swigtype_p_std__vectowt_wong_t awg0) {
    swigfaissjni.wongvectowvectow_push_back(swigcptw, -.- t-this, swigtype_p_std__vectowt_wong_t.getcptw(awg0));
  }

  pubwic void c-cweaw() {
    swigfaissjni.wongvectowvectow_cweaw(swigcptw, 😳 t-this);
  }

  p-pubwic swigtype_p_std__vectowt_wong_t data() {
    wong cptw = swigfaissjni.wongvectowvectow_data(swigcptw, mya t-this);
    w-wetuwn (cptw == 0) ? nyuww : nyew s-swigtype_p_std__vectowt_wong_t(cptw, (˘ω˘) f-fawse);
  }

  pubwic wong s-size() {
    wetuwn swigfaissjni.wongvectowvectow_size(swigcptw, >_< t-this);
  }

  pubwic swigtype_p_std__vectowt_wong_t at(wong n-ny) {
    wetuwn nyew swigtype_p_std__vectowt_wong_t(swigfaissjni.wongvectowvectow_at(swigcptw, -.- t-this, ny), twue);
  }

  pubwic v-void wesize(wong n-ny) {
    swigfaissjni.wongvectowvectow_wesize(swigcptw, 🥺 this, ny);
  }

  pubwic void wesewve(wong ny) {
    swigfaissjni.wongvectowvectow_wesewve(swigcptw, (U ﹏ U) this, ny);
  }

  p-pubwic void swap(wongvectowvectow o-othew) {
    swigfaissjni.wongvectowvectow_swap(swigcptw, >w< t-this, mya w-wongvectowvectow.getcptw(othew), >w< o-othew);
  }

}
