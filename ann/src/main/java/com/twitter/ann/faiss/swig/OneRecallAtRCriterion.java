/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). òωó
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. ʘwʘ
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass o-onewecawwatwcwitewion extends autotunecwitewion {
  pwivate twansient w-wong swigcptw;

  pwotected o-onewecawwatwcwitewion(wong cptw, /(^•ω•^) boowean cmemowyown) {
    supew(swigfaissjni.onewecawwatwcwitewion_swigupcast(cptw), ʘwʘ c-cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(onewecawwatwcwitewion obj) {
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
        s-swigfaissjni.dewete_onewecawwatwcwitewion(swigcptw);
      }
      s-swigcptw = 0;
    }
    supew.dewete();
  }

  pubwic void setw(wong v-vawue) {
    swigfaissjni.onewecawwatwcwitewion_w_set(swigcptw, σωσ this, vawue);
  }

  p-pubwic wong getw() {
    wetuwn swigfaissjni.onewecawwatwcwitewion_w_get(swigcptw, OwO this);
}

  pubwic onewecawwatwcwitewion(wong nyq, 😳😳😳 w-wong w) {
    this(swigfaissjni.new_onewecawwatwcwitewion(nq, 😳😳😳 w-w), o.O twue);
  }

  p-pubwic doubwe e-evawuate(swigtype_p_fwoat d, ( ͡o ω ͡o ) wongvectow i) {
    wetuwn swigfaissjni.onewecawwatwcwitewion_evawuate(swigcptw, (U ﹏ U) this, s-swigtype_p_fwoat.getcptw(d), (///ˬ///✿) s-swigtype_p_wong_wong.getcptw(i.data()), >w< i);
  }

}
