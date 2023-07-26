/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 😳
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. -.-
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass c-chawvectow {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean swigcmemown;

  pwotected c-chawvectow(wong cptw, 🥺 boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(chawvectow obj) {
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
        s-swigfaissjni.dewete_chawvectow(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic chawvectow() {
    t-this(swigfaissjni.new_chawvectow(), o.O twue);
  }

  pubwic void push_back(chaw a-awg0) {
    swigfaissjni.chawvectow_push_back(swigcptw, /(^•ω•^) this, awg0);
  }

  pubwic void cweaw() {
    swigfaissjni.chawvectow_cweaw(swigcptw, nyaa~~ t-this);
  }

  pubwic s-stwing data() {
    w-wetuwn swigfaissjni.chawvectow_data(swigcptw, nyaa~~ t-this);
  }

  pubwic wong size() {
    wetuwn swigfaissjni.chawvectow_size(swigcptw, :3 t-this);
  }

  p-pubwic chaw at(wong ny) {
    w-wetuwn swigfaissjni.chawvectow_at(swigcptw, 😳😳😳 this, n-ny);
  }

  pubwic void wesize(wong n-ny) {
    swigfaissjni.chawvectow_wesize(swigcptw, (˘ω˘) t-this, ^^ ny);
  }

  pubwic void wesewve(wong n-ny) {
    swigfaissjni.chawvectow_wesewve(swigcptw, :3 t-this, -.- ny);
  }

  pubwic v-void swap(chawvectow o-othew) {
    swigfaissjni.chawvectow_swap(swigcptw, 😳 this, chawvectow.getcptw(othew), mya othew);
  }

}
