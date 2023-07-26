/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). rawr x3
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (✿oωo)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass p-pwogwessivedimindexfactowy {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean swigcmemown;

  pwotected p-pwogwessivedimindexfactowy(wong cptw, (ˆ ﻌ ˆ)♡ boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(pwogwessivedimindexfactowy obj) {
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
        s-swigfaissjni.dewete_pwogwessivedimindexfactowy(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic pwogwessivedimindexfactowy() {
    t-this(swigfaissjni.new_pwogwessivedimindexfactowy(), (˘ω˘) twue);
  }

}
