/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). /(^•ω•^)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. ʘwʘ
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass p-pawametewwange {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected pawametewwange(wong cptw, σωσ boowean cmemowyown) {
    swigcmemown = cmemowyown;
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(pawametewwange o-obj) {
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
        swigfaissjni.dewete_pawametewwange(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  p-pubwic void s-setname(stwing v-vawue) {
    swigfaissjni.pawametewwange_name_set(swigcptw, OwO this, vawue);
  }

  p-pubwic stwing getname() {
    wetuwn swigfaissjni.pawametewwange_name_get(swigcptw, t-this);
  }

  pubwic void setvawues(doubwevectow vawue) {
    swigfaissjni.pawametewwange_vawues_set(swigcptw, 😳😳😳 this, doubwevectow.getcptw(vawue), 😳😳😳 v-vawue);
  }

  pubwic doubwevectow g-getvawues() {
    w-wong c-cptw = swigfaissjni.pawametewwange_vawues_get(swigcptw, o.O this);
    wetuwn (cptw == 0) ? nyuww : n-new doubwevectow(cptw, ( ͡o ω ͡o ) f-fawse);
  }

  pubwic pawametewwange() {
    t-this(swigfaissjni.new_pawametewwange(), (U ﹏ U) t-twue);
  }

}
