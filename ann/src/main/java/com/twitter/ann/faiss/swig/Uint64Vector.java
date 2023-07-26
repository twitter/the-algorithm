/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). :3
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 😳😳😳
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass u-uint64vectow {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected uint64vectow(wong cptw, (˘ω˘) boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = c-cptw;
  }

  pwotected static wong getcptw(uint64vectow obj) {
    wetuwn (obj == n-nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  p-pubwic synchwonized v-void dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = fawse;
        swigfaissjni.dewete_uint64vectow(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic uint64vectow() {
    t-this(swigfaissjni.new_uint64vectow(), ^^ twue);
  }

  pubwic void push_back(wong awg0) {
    swigfaissjni.uint64vectow_push_back(swigcptw, :3 this, -.- awg0);
  }

  p-pubwic void cweaw() {
    s-swigfaissjni.uint64vectow_cweaw(swigcptw, 😳 t-this);
  }

  p-pubwic swigtype_p_unsigned_wong data() {
    wong cptw = s-swigfaissjni.uint64vectow_data(swigcptw, mya t-this);
    wetuwn (cptw == 0) ? n-nyuww : n-nyew swigtype_p_unsigned_wong(cptw, (˘ω˘) fawse);
  }

  p-pubwic wong size() {
    wetuwn s-swigfaissjni.uint64vectow_size(swigcptw, >_< this);
  }

  pubwic wong at(wong n-ny) {
    wetuwn swigfaissjni.uint64vectow_at(swigcptw, -.- t-this, 🥺 ny);
  }

  pubwic v-void wesize(wong n-ny) {
    swigfaissjni.uint64vectow_wesize(swigcptw, this, (U ﹏ U) ny);
  }

  pubwic void wesewve(wong ny) {
    swigfaissjni.uint64vectow_wesewve(swigcptw, >w< this, ny);
  }

  pubwic v-void swap(uint64vectow o-othew) {
    swigfaissjni.uint64vectow_swap(swigcptw, mya this, >w< u-uint64vectow.getcptw(othew), o-othew);
  }

}
