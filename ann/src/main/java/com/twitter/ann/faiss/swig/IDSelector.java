/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (ˆ ﻌ ˆ)♡
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (˘ω˘)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-idsewectow {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean swigcmemown;

  pwotected i-idsewectow(wong cptw, (⑅˘꒳˘) boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(idsewectow obj) {
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
        s-swigfaissjni.dewete_idsewectow(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic boowean i-is_membew(wong id) {
    wetuwn swigfaissjni.idsewectow_is_membew(swigcptw, (///ˬ///✿) t-this, 😳😳😳 id);
  }

}
