/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). nyaa~~
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. nyaa~~
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-intvectow {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean swigcmemown;

  p-pwotected intvectow(wong cptw, :3 boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = c-cptw;
  }

  pwotected static wong getcptw(intvectow obj) {
    wetuwn (obj == nyuww) ? 0 : o-obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized v-void dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_intvectow(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic intvectow() {
    t-this(swigfaissjni.new_intvectow(), 😳😳😳 twue);
  }

  pubwic v-void push_back(int awg0) {
    swigfaissjni.intvectow_push_back(swigcptw, (˘ω˘) this, awg0);
  }

  pubwic void cweaw() {
    s-swigfaissjni.intvectow_cweaw(swigcptw, this);
  }

  p-pubwic swigtype_p_int d-data() {
    w-wong cptw = swigfaissjni.intvectow_data(swigcptw, ^^ this);
    wetuwn (cptw == 0) ? nyuww : nyew s-swigtype_p_int(cptw, :3 f-fawse);
  }

  pubwic wong s-size() {
    wetuwn s-swigfaissjni.intvectow_size(swigcptw, -.- this);
  }

  p-pubwic int at(wong ny) {
    w-wetuwn swigfaissjni.intvectow_at(swigcptw, 😳 this, ny);
  }

  pubwic void w-wesize(wong ny) {
    swigfaissjni.intvectow_wesize(swigcptw, t-this, mya ny);
  }

  p-pubwic void wesewve(wong n-ny) {
    swigfaissjni.intvectow_wesewve(swigcptw, (˘ω˘) this, ny);
  }

  pubwic void swap(intvectow othew) {
    swigfaissjni.intvectow_swap(swigcptw, >_< t-this, i-intvectow.getcptw(othew), -.- othew);
  }

}
