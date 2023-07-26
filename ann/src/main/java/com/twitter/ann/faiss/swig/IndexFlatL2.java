/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (˘ω˘)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (⑅˘꒳˘)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexfwatw2 extends indexfwat {
  pwivate twansient w-wong swigcptw;

  pwotected i-indexfwatw2(wong cptw, (///ˬ///✿) boowean cmemowyown) {
    supew(swigfaissjni.indexfwatw2_swigupcast(cptw), 😳😳😳 c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(indexfwatw2 obj) {
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
        s-swigfaissjni.dewete_indexfwatw2(swigcptw);
      }
      s-swigcptw = 0;
    }
    supew.dewete();
  }

  pubwic indexfwatw2(wong d-d) {
    this(swigfaissjni.new_indexfwatw2__swig_0(d), 🥺 twue);
  }

  pubwic indexfwatw2() {
    t-this(swigfaissjni.new_indexfwatw2__swig_1(), mya twue);
  }

}
