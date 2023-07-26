/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (U ﹏ U)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (⑅˘꒳˘)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-intewwuptcawwback {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected intewwuptcawwback(wong cptw, òωó boowean c-cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(intewwuptcawwback obj) {
    w-wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    d-dewete();
  }

  pubwic s-synchwonized v-void dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        swigcmemown = fawse;
        s-swigfaissjni.dewete_intewwuptcawwback(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic boowean w-want_intewwupt() {
    wetuwn swigfaissjni.intewwuptcawwback_want_intewwupt(swigcptw, ʘwʘ this);
  }

  pubwic static void cweaw_instance() {
    swigfaissjni.intewwuptcawwback_cweaw_instance();
  }

  pubwic static v-void check() {
    swigfaissjni.intewwuptcawwback_check();
  }

  p-pubwic static b-boowean is_intewwupted() {
    w-wetuwn swigfaissjni.intewwuptcawwback_is_intewwupted();
  }

  pubwic static wong get_pewiod_hint(wong fwops) {
    w-wetuwn swigfaissjni.intewwuptcawwback_get_pewiod_hint(fwops);
  }

}
