/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). ʘwʘ
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (ˆ ﻌ ˆ)♡
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass p-pwogwessivedimcwustewing extends pwogwessivedimcwustewingpawametews {
  p-pwivate twansient wong s-swigcptw;

  pwotected pwogwessivedimcwustewing(wong cptw, 😳😳😳 boowean cmemowyown) {
    s-supew(swigfaissjni.pwogwessivedimcwustewing_swigupcast(cptw), :3 cmemowyown);
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(pwogwessivedimcwustewing obj) {
    wetuwn (obj == nyuww) ? 0 : o-obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized v-void dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = fawse;
        s-swigfaissjni.dewete_pwogwessivedimcwustewing(swigcptw);
      }
      s-swigcptw = 0;
    }
    supew.dewete();
  }

  pubwic void setd(wong v-vawue) {
    swigfaissjni.pwogwessivedimcwustewing_d_set(swigcptw, OwO this, v-vawue);
  }

  pubwic wong getd() {
    wetuwn swigfaissjni.pwogwessivedimcwustewing_d_get(swigcptw, (U ﹏ U) this);
  }

  pubwic void s-setk(wong vawue) {
    swigfaissjni.pwogwessivedimcwustewing_k_set(swigcptw, >w< t-this, (U ﹏ U) v-vawue);
  }

  p-pubwic wong getk() {
    wetuwn swigfaissjni.pwogwessivedimcwustewing_k_get(swigcptw, 😳 this);
  }

  p-pubwic void s-setcentwoids(fwoatvectow vawue) {
    s-swigfaissjni.pwogwessivedimcwustewing_centwoids_set(swigcptw, (ˆ ﻌ ˆ)♡ t-this, fwoatvectow.getcptw(vawue), 😳😳😳 vawue);
  }

  p-pubwic fwoatvectow getcentwoids() {
    w-wong cptw = swigfaissjni.pwogwessivedimcwustewing_centwoids_get(swigcptw, (U ﹏ U) this);
    wetuwn (cptw == 0) ? n-nyuww : new fwoatvectow(cptw, (///ˬ///✿) f-fawse);
  }

  pubwic void s-setitewation_stats(swigtype_p_std__vectowt_faiss__cwustewingitewationstats_t v-vawue) {
    swigfaissjni.pwogwessivedimcwustewing_itewation_stats_set(swigcptw, 😳 this, swigtype_p_std__vectowt_faiss__cwustewingitewationstats_t.getcptw(vawue));
  }

  pubwic swigtype_p_std__vectowt_faiss__cwustewingitewationstats_t getitewation_stats() {
    wong cptw = swigfaissjni.pwogwessivedimcwustewing_itewation_stats_get(swigcptw, 😳 t-this);
    w-wetuwn (cptw == 0) ? nyuww : nyew s-swigtype_p_std__vectowt_faiss__cwustewingitewationstats_t(cptw, f-fawse);
  }

  p-pubwic pwogwessivedimcwustewing(int d, σωσ int k) {
    this(swigfaissjni.new_pwogwessivedimcwustewing__swig_0(d, rawr x3 k), twue);
  }

  p-pubwic pwogwessivedimcwustewing(int d, OwO int k, /(^•ω•^) pwogwessivedimcwustewingpawametews cp) {
    this(swigfaissjni.new_pwogwessivedimcwustewing__swig_1(d, 😳😳😳 k, pwogwessivedimcwustewingpawametews.getcptw(cp), ( ͡o ω ͡o ) cp), >_< twue);
  }

  p-pubwic void twain(wong n-ny, >w< swigtype_p_fwoat x-x, rawr pwogwessivedimindexfactowy f-factowy) {
    swigfaissjni.pwogwessivedimcwustewing_twain(swigcptw, 😳 t-this, n-ny, >w< swigtype_p_fwoat.getcptw(x), (⑅˘꒳˘) p-pwogwessivedimindexfactowy.getcptw(factowy), OwO factowy);
  }

}
