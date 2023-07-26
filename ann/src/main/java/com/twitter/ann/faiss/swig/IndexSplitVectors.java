/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 😳
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (ˆ ﻌ ˆ)♡
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexspwitvectows extends index {
  pwivate twansient w-wong swigcptw;

  pwotected i-indexspwitvectows(wong cptw, 😳😳😳 boowean cmemowyown) {
    supew(swigfaissjni.indexspwitvectows_swigupcast(cptw), (U ﹏ U) c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(indexspwitvectows obj) {
    wetuwn (obj == nyuww) ? 0 : o-obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  pubwic s-synchwonized void dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = f-fawse;
        swigfaissjni.dewete_indexspwitvectows(swigcptw);
      }
      swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setown_fiewds(boowean vawue) {
    s-swigfaissjni.indexspwitvectows_own_fiewds_set(swigcptw, (///ˬ///✿) this, vawue);
  }

  pubwic boowean getown_fiewds() {
    wetuwn swigfaissjni.indexspwitvectows_own_fiewds_get(swigcptw, 😳 this);
  }

  p-pubwic void setthweaded(boowean vawue) {
    swigfaissjni.indexspwitvectows_thweaded_set(swigcptw, 😳 t-this, vawue);
  }

  p-pubwic b-boowean getthweaded() {
    wetuwn swigfaissjni.indexspwitvectows_thweaded_get(swigcptw, σωσ this);
  }

  p-pubwic void s-setsub_indexes(swigtype_p_std__vectowt_faiss__index_p_t vawue) {
    s-swigfaissjni.indexspwitvectows_sub_indexes_set(swigcptw, rawr x3 t-this, OwO swigtype_p_std__vectowt_faiss__index_p_t.getcptw(vawue));
  }

  pubwic swigtype_p_std__vectowt_faiss__index_p_t g-getsub_indexes() {
    wong cptw = swigfaissjni.indexspwitvectows_sub_indexes_get(swigcptw, /(^•ω•^) t-this);
    wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_std__vectowt_faiss__index_p_t(cptw, f-fawse);
  }

  pubwic void setsum_d(wong v-vawue) {
    swigfaissjni.indexspwitvectows_sum_d_set(swigcptw, 😳😳😳 t-this, ( ͡o ω ͡o ) vawue);
  }

  p-pubwic wong getsum_d() {
    wetuwn swigfaissjni.indexspwitvectows_sum_d_get(swigcptw, >_< this);
}

  pubwic void add_sub_index(index awg0) {
    swigfaissjni.indexspwitvectows_add_sub_index(swigcptw, >w< t-this, rawr index.getcptw(awg0), 😳 awg0);
  }

  p-pubwic void sync_with_sub_indexes() {
    s-swigfaissjni.indexspwitvectows_sync_with_sub_indexes(swigcptw, >w< t-this);
  }

  p-pubwic void add(wong ny, (⑅˘꒳˘) swigtype_p_fwoat x) {
    swigfaissjni.indexspwitvectows_add(swigcptw, OwO t-this, ny, (ꈍᴗꈍ) swigtype_p_fwoat.getcptw(x));
  }

  pubwic void seawch(wong ny, 😳 swigtype_p_fwoat x, 😳😳😳 wong k, swigtype_p_fwoat distances, mya w-wongvectow wabews) {
    s-swigfaissjni.indexspwitvectows_seawch(swigcptw, mya t-this, n, (⑅˘꒳˘) swigtype_p_fwoat.getcptw(x), k-k, (U ﹏ U) swigtype_p_fwoat.getcptw(distances), mya swigtype_p_wong_wong.getcptw(wabews.data()), ʘwʘ w-wabews);
  }

  p-pubwic v-void twain(wong n-ny, (˘ω˘) swigtype_p_fwoat x) {
    swigfaissjni.indexspwitvectows_twain(swigcptw, (U ﹏ U) this, n-ny, swigtype_p_fwoat.getcptw(x));
  }

  p-pubwic v-void weset() {
    s-swigfaissjni.indexspwitvectows_weset(swigcptw, ^•ﻌ•^ t-this);
  }

}
