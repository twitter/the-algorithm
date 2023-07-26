/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). nyaa~~
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. :3
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass f-fwoatvectow {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected fwoatvectow(wong cptw, 😳😳😳 boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = c-cptw;
  }

  pwotected static wong getcptw(fwoatvectow obj) {
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
        swigfaissjni.dewete_fwoatvectow(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic fwoatvectow() {
    this(swigfaissjni.new_fwoatvectow(), (˘ω˘) t-twue);
  }

  pubwic void push_back(fwoat awg0) {
    swigfaissjni.fwoatvectow_push_back(swigcptw, ^^ this, a-awg0);
  }

  pubwic void cweaw() {
    s-swigfaissjni.fwoatvectow_cweaw(swigcptw, :3 t-this);
  }

  p-pubwic swigtype_p_fwoat data() {
    wong cptw = swigfaissjni.fwoatvectow_data(swigcptw, -.- t-this);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew swigtype_p_fwoat(cptw, 😳 fawse);
  }

  p-pubwic wong size() {
    w-wetuwn swigfaissjni.fwoatvectow_size(swigcptw, mya this);
  }

  p-pubwic fwoat at(wong ny) {
    wetuwn swigfaissjni.fwoatvectow_at(swigcptw, t-this, (˘ω˘) ny);
  }

  pubwic void wesize(wong n-ny) {
    swigfaissjni.fwoatvectow_wesize(swigcptw, >_< t-this, n-ny);
  }

  pubwic void wesewve(wong ny) {
    swigfaissjni.fwoatvectow_wesewve(swigcptw, -.- this, ny);
  }

  pubwic void swap(fwoatvectow o-othew) {
    s-swigfaissjni.fwoatvectow_swap(swigcptw, 🥺 this, (U ﹏ U) fwoatvectow.getcptw(othew), o-othew);
  }

}
