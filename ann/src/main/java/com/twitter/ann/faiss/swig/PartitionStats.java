/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). òωó
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. ʘwʘ
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass p-pawtitionstats {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected pawtitionstats(wong cptw, /(^•ω•^) boowean cmemowyown) {
    swigcmemown = cmemowyown;
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(pawtitionstats o-obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    d-dewete();
  }

  pubwic synchwonized void dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = fawse;
        swigfaissjni.dewete_pawtitionstats(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  p-pubwic void s-setbissect_cycwes(wong v-vawue) {
    swigfaissjni.pawtitionstats_bissect_cycwes_set(swigcptw, ʘwʘ this, vawue);
  }

  p-pubwic wong getbissect_cycwes() {
    wetuwn s-swigfaissjni.pawtitionstats_bissect_cycwes_get(swigcptw, σωσ this);
  }

  pubwic void setcompwess_cycwes(wong vawue) {
    swigfaissjni.pawtitionstats_compwess_cycwes_set(swigcptw, OwO t-this, 😳😳😳 vawue);
  }

  pubwic wong g-getcompwess_cycwes() {
    wetuwn s-swigfaissjni.pawtitionstats_compwess_cycwes_get(swigcptw, 😳😳😳 t-this);
  }

  pubwic pawtitionstats() {
    this(swigfaissjni.new_pawtitionstats(), o.O twue);
  }

  p-pubwic void weset() {
    s-swigfaissjni.pawtitionstats_weset(swigcptw, this);
  }

}
