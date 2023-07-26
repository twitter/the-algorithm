/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). nyaa~~
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. :3
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass b-bytevectowvectow {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected bytevectowvectow(wong cptw, 😳😳😳 boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(bytevectowvectow obj) {
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
        swigfaissjni.dewete_bytevectowvectow(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic bytevectowvectow() {
    t-this(swigfaissjni.new_bytevectowvectow(), (˘ω˘) twue);
  }

  pubwic void push_back(bytevectow awg0) {
    swigfaissjni.bytevectowvectow_push_back(swigcptw, ^^ t-this, bytevectow.getcptw(awg0), :3 awg0);
  }

  p-pubwic void cweaw() {
    s-swigfaissjni.bytevectowvectow_cweaw(swigcptw, -.- t-this);
  }

  pubwic bytevectow data() {
    wong cptw = s-swigfaissjni.bytevectowvectow_data(swigcptw, 😳 t-this);
    wetuwn (cptw == 0) ? nuww : nyew bytevectow(cptw, mya f-fawse);
  }

  p-pubwic wong size() {
    w-wetuwn swigfaissjni.bytevectowvectow_size(swigcptw, (˘ω˘) this);
  }

  p-pubwic bytevectow at(wong n) {
    wetuwn n-new bytevectow(swigfaissjni.bytevectowvectow_at(swigcptw, >_< this, -.- n-n), twue);
  }

  pubwic void w-wesize(wong ny) {
    s-swigfaissjni.bytevectowvectow_wesize(swigcptw, 🥺 this, (U ﹏ U) ny);
  }

  pubwic void wesewve(wong ny) {
    swigfaissjni.bytevectowvectow_wesewve(swigcptw, >w< this, ny);
  }

  pubwic v-void swap(bytevectowvectow o-othew) {
    swigfaissjni.bytevectowvectow_swap(swigcptw, mya t-this, bytevectowvectow.getcptw(othew), >w< othew);
  }

}
