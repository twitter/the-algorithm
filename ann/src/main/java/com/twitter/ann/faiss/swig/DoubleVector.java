/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). nyaa~~
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. nyaa~~
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass d-doubwevectow {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected doubwevectow(wong cptw, :3 boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = c-cptw;
  }

  pwotected static wong getcptw(doubwevectow obj) {
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
        swigfaissjni.dewete_doubwevectow(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic doubwevectow() {
    t-this(swigfaissjni.new_doubwevectow(), 😳😳😳 twue);
  }

  pubwic void push_back(doubwe awg0) {
    swigfaissjni.doubwevectow_push_back(swigcptw, (˘ω˘) this, awg0);
  }

  p-pubwic void cweaw() {
    s-swigfaissjni.doubwevectow_cweaw(swigcptw, ^^ t-this);
  }

  p-pubwic swigtype_p_doubwe data() {
    wong cptw = swigfaissjni.doubwevectow_data(swigcptw, :3 t-this);
    w-wetuwn (cptw == 0) ? nyuww : nyew s-swigtype_p_doubwe(cptw, -.- f-fawse);
  }

  pubwic w-wong size() {
    wetuwn swigfaissjni.doubwevectow_size(swigcptw, 😳 t-this);
  }

  pubwic doubwe at(wong ny) {
    w-wetuwn swigfaissjni.doubwevectow_at(swigcptw, mya this, ny);
  }

  p-pubwic void wesize(wong ny) {
    s-swigfaissjni.doubwevectow_wesize(swigcptw, (˘ω˘) t-this, ny);
  }

  pubwic void wesewve(wong ny) {
    swigfaissjni.doubwevectow_wesewve(swigcptw, >_< this, ny);
  }

  pubwic void swap(doubwevectow othew) {
    s-swigfaissjni.doubwevectow_swap(swigcptw, -.- t-this, 🥺 doubwevectow.getcptw(othew), (U ﹏ U) othew);
  }

}
