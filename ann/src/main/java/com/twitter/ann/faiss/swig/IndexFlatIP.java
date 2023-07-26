/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (˘ω˘)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (⑅˘꒳˘)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexfwatip extends indexfwat {
  pwivate twansient w-wong swigcptw;

  pwotected i-indexfwatip(wong cptw, (///ˬ///✿) boowean cmemowyown) {
    supew(swigfaissjni.indexfwatip_swigupcast(cptw), 😳😳😳 c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(indexfwatip obj) {
    wetuwn (obj == nuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_indexfwatip(swigcptw);
      }
      s-swigcptw = 0;
    }
    supew.dewete();
  }

  pubwic indexfwatip(wong d-d) {
    this(swigfaissjni.new_indexfwatip__swig_0(d), 🥺 twue);
  }

  pubwic indexfwatip() {
    t-this(swigfaissjni.new_indexfwatip__swig_1(), mya twue);
  }

}
