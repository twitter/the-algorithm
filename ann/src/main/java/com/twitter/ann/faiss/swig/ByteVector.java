/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). o.O
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. /(^•ω•^)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass b-bytevectow {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean swigcmemown;

  pwotected b-bytevectow(wong cptw, nyaa~~ boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(bytevectow obj) {
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
        s-swigfaissjni.dewete_bytevectow(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic bytevectow() {
    t-this(swigfaissjni.new_bytevectow(), nyaa~~ twue);
  }

  pubwic void push_back(showt a-awg0) {
    swigfaissjni.bytevectow_push_back(swigcptw, :3 this, awg0);
  }

  pubwic void cweaw() {
    swigfaissjni.bytevectow_cweaw(swigcptw, 😳😳😳 t-this);
  }

  pubwic s-swigtype_p_unsigned_chaw d-data() {
    w-wong cptw = swigfaissjni.bytevectow_data(swigcptw, (˘ω˘) this);
    wetuwn (cptw == 0) ? n-nyuww : n-new swigtype_p_unsigned_chaw(cptw, ^^ fawse);
  }

  p-pubwic wong s-size() {
    wetuwn swigfaissjni.bytevectow_size(swigcptw, :3 t-this);
  }

  pubwic s-showt at(wong ny) {
    wetuwn swigfaissjni.bytevectow_at(swigcptw, -.- this, ny);
  }

  p-pubwic void wesize(wong ny) {
    s-swigfaissjni.bytevectow_wesize(swigcptw, 😳 this, ny);
  }

  p-pubwic void wesewve(wong n-ny) {
    swigfaissjni.bytevectow_wesewve(swigcptw, mya this, ny);
  }

  pubwic void swap(bytevectow othew) {
    swigfaissjni.bytevectow_swap(swigcptw, (˘ω˘) this, bytevectow.getcptw(othew), >_< o-othew);
  }

}
