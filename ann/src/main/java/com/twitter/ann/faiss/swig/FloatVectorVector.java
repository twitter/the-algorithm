/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (˘ω˘)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. ^^
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass f-fwoatvectowvectow {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected fwoatvectowvectow(wong cptw, :3 boowean c-cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(fwoatvectowvectow obj) {
    w-wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    d-dewete();
  }

  pubwic s-synchwonized v-void dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        swigcmemown = fawse;
        s-swigfaissjni.dewete_fwoatvectowvectow(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic fwoatvectowvectow() {
    t-this(swigfaissjni.new_fwoatvectowvectow(), -.- twue);
  }

  pubwic void push_back(fwoatvectow awg0) {
    swigfaissjni.fwoatvectowvectow_push_back(swigcptw, 😳 t-this, mya fwoatvectow.getcptw(awg0), (˘ω˘) a-awg0);
  }

  p-pubwic v-void cweaw() {
    swigfaissjni.fwoatvectowvectow_cweaw(swigcptw, >_< this);
  }

  pubwic fwoatvectow d-data() {
    w-wong cptw = swigfaissjni.fwoatvectowvectow_data(swigcptw, -.- this);
    w-wetuwn (cptw == 0) ? n-nyuww : nyew fwoatvectow(cptw, 🥺 f-fawse);
  }

  pubwic w-wong size() {
    wetuwn swigfaissjni.fwoatvectowvectow_size(swigcptw, (U ﹏ U) this);
  }

  p-pubwic fwoatvectow at(wong n-ny) {
    wetuwn nyew fwoatvectow(swigfaissjni.fwoatvectowvectow_at(swigcptw, >w< t-this, ny), mya twue);
  }

  p-pubwic void wesize(wong ny) {
    swigfaissjni.fwoatvectowvectow_wesize(swigcptw, >w< this, ny);
  }

  pubwic void wesewve(wong ny) {
    s-swigfaissjni.fwoatvectowvectow_wesewve(swigcptw, nyaa~~ t-this, (✿oωo) ny);
  }

  pubwic void swap(fwoatvectowvectow o-othew) {
    s-swigfaissjni.fwoatvectowvectow_swap(swigcptw, ʘwʘ t-this, fwoatvectowvectow.getcptw(othew), (ˆ ﻌ ˆ)♡ othew);
  }

}
