/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). OwO
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (U ﹏ U)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass s-swidingindexwindow {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected swidingindexwindow(wong cptw, >w< boowean c-cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(swidingindexwindow obj) {
    wetuwn (obj == n-nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_swidingindexwindow(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic void setindex(index v-vawue) {
    swigfaissjni.swidingindexwindow_index_set(swigcptw, (U ﹏ U) this, 😳 index.getcptw(vawue), (ˆ ﻌ ˆ)♡ vawue);
  }

  p-pubwic index getindex() {
    wong cptw = swigfaissjni.swidingindexwindow_index_get(swigcptw, 😳😳😳 this);
    wetuwn (cptw == 0) ? n-nyuww : nyew index(cptw, (U ﹏ U) f-fawse);
  }

  p-pubwic void setiws(awwayinvewtedwists v-vawue) {
    swigfaissjni.swidingindexwindow_iws_set(swigcptw, (///ˬ///✿) this, 😳 awwayinvewtedwists.getcptw(vawue), 😳 vawue);
  }

  p-pubwic awwayinvewtedwists g-getiws() {
    wong cptw = s-swigfaissjni.swidingindexwindow_iws_get(swigcptw, σωσ t-this);
    wetuwn (cptw == 0) ? n-nyuww : nyew awwayinvewtedwists(cptw, rawr x3 f-fawse);
  }

  pubwic void setn_swice(int v-vawue) {
    swigfaissjni.swidingindexwindow_n_swice_set(swigcptw, OwO t-this, /(^•ω•^) vawue);
  }

  pubwic i-int getn_swice() {
    w-wetuwn swigfaissjni.swidingindexwindow_n_swice_get(swigcptw, 😳😳😳 this);
  }

  pubwic void setnwist(wong vawue) {
    swigfaissjni.swidingindexwindow_nwist_set(swigcptw, ( ͡o ω ͡o ) this, >_< vawue);
  }

  p-pubwic wong g-getnwist() {
    wetuwn swigfaissjni.swidingindexwindow_nwist_get(swigcptw, >w< t-this);
  }

  pubwic v-void setsizes(swigtype_p_std__vectowt_std__vectowt_unsigned_wong_t_t v-vawue) {
    swigfaissjni.swidingindexwindow_sizes_set(swigcptw, rawr this, 😳 swigtype_p_std__vectowt_std__vectowt_unsigned_wong_t_t.getcptw(vawue));
  }

  p-pubwic swigtype_p_std__vectowt_std__vectowt_unsigned_wong_t_t getsizes() {
    wong cptw = swigfaissjni.swidingindexwindow_sizes_get(swigcptw, >w< this);
    wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_std__vectowt_std__vectowt_unsigned_wong_t_t(cptw, (⑅˘꒳˘) f-fawse);
  }

  pubwic swidingindexwindow(index i-index) {
    t-this(swigfaissjni.new_swidingindexwindow(index.getcptw(index), OwO i-index), (ꈍᴗꈍ) twue);
  }

  pubwic v-void step(index s-sub_index, 😳 boowean w-wemove_owdest) {
    s-swigfaissjni.swidingindexwindow_step(swigcptw, 😳😳😳 this, mya index.getcptw(sub_index), mya sub_index, (⑅˘꒳˘) w-wemove_owdest);
  }

}
