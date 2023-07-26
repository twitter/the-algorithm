/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). :3
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 😳😳😳
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-invewtedwistsptwvectow {
  pwivate twansient wong s-swigcptw;
  pwotected twansient b-boowean swigcmemown;

  pwotected invewtedwistsptwvectow(wong cptw, (˘ω˘) boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(invewtedwistsptwvectow obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized v-void dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_invewtedwistsptwvectow(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic invewtedwistsptwvectow() {
    this(swigfaissjni.new_invewtedwistsptwvectow(), ^^ t-twue);
  }

  pubwic void push_back(invewtedwists a-awg0) {
    swigfaissjni.invewtedwistsptwvectow_push_back(swigcptw, :3 this, -.- invewtedwists.getcptw(awg0), 😳 awg0);
  }

  pubwic void cweaw() {
    swigfaissjni.invewtedwistsptwvectow_cweaw(swigcptw, mya t-this);
  }

  pubwic swigtype_p_p_faiss__invewtedwists d-data() {
    w-wong cptw = s-swigfaissjni.invewtedwistsptwvectow_data(swigcptw, (˘ω˘) this);
    wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_p_faiss__invewtedwists(cptw, >_< f-fawse);
  }

  p-pubwic wong size() {
    w-wetuwn swigfaissjni.invewtedwistsptwvectow_size(swigcptw, -.- t-this);
  }

  pubwic i-invewtedwists at(wong ny) {
    w-wong cptw = swigfaissjni.invewtedwistsptwvectow_at(swigcptw, 🥺 this, (U ﹏ U) ny);
    wetuwn (cptw == 0) ? n-nyuww : nyew invewtedwists(cptw, >w< fawse);
  }

  p-pubwic void wesize(wong ny) {
    s-swigfaissjni.invewtedwistsptwvectow_wesize(swigcptw, mya t-this, >w< ny);
  }

  pubwic void wesewve(wong ny) {
    swigfaissjni.invewtedwistsptwvectow_wesewve(swigcptw, nyaa~~ this, (✿oωo) ny);
  }

  pubwic void swap(invewtedwistsptwvectow o-othew) {
    s-swigfaissjni.invewtedwistsptwvectow_swap(swigcptw, ʘwʘ this, i-invewtedwistsptwvectow.getcptw(othew), (ˆ ﻌ ˆ)♡ o-othew);
  }

}
