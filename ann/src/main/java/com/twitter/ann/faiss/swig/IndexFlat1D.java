/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). -.-
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 🥺
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexfwat1d extends indexfwatw2 {
  pwivate twansient w-wong swigcptw;

  pwotected i-indexfwat1d(wong cptw, boowean cmemowyown) {
    supew(swigfaissjni.indexfwat1d_swigupcast(cptw), (U ﹏ U) c-cmemowyown);
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(indexfwat1d obj) {
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
        s-swigfaissjni.dewete_indexfwat1d(swigcptw);
      }
      swigcptw = 0;
    }
    supew.dewete();
  }

  p-pubwic void setcontinuous_update(boowean vawue) {
    s-swigfaissjni.indexfwat1d_continuous_update_set(swigcptw, >w< this, vawue);
  }

  pubwic boowean getcontinuous_update() {
    wetuwn swigfaissjni.indexfwat1d_continuous_update_get(swigcptw, mya t-this);
  }

  pubwic v-void setpewm(swigtype_p_std__vectowt_int64_t_t vawue) {
    s-swigfaissjni.indexfwat1d_pewm_set(swigcptw, >w< t-this, nyaa~~ swigtype_p_std__vectowt_int64_t_t.getcptw(vawue));
  }

  pubwic swigtype_p_std__vectowt_int64_t_t getpewm() {
    wong cptw = swigfaissjni.indexfwat1d_pewm_get(swigcptw, (✿oωo) t-this);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew swigtype_p_std__vectowt_int64_t_t(cptw, ʘwʘ f-fawse);
  }

  pubwic i-indexfwat1d(boowean continuous_update) {
    t-this(swigfaissjni.new_indexfwat1d__swig_0(continuous_update), (ˆ ﻌ ˆ)♡ twue);
  }

  pubwic indexfwat1d() {
    t-this(swigfaissjni.new_indexfwat1d__swig_1(), 😳😳😳 twue);
  }

  p-pubwic void update_pewmutation() {
    swigfaissjni.indexfwat1d_update_pewmutation(swigcptw, :3 t-this);
  }

  pubwic v-void add(wong n, OwO swigtype_p_fwoat x) {
    swigfaissjni.indexfwat1d_add(swigcptw, (U ﹏ U) this, >w< ny, swigtype_p_fwoat.getcptw(x));
  }

  pubwic void w-weset() {
    s-swigfaissjni.indexfwat1d_weset(swigcptw, (U ﹏ U) this);
  }

  p-pubwic void s-seawch(wong ny, 😳 s-swigtype_p_fwoat x, (ˆ ﻌ ˆ)♡ wong k, 😳😳😳 swigtype_p_fwoat distances, (U ﹏ U) wongvectow wabews) {
    s-swigfaissjni.indexfwat1d_seawch(swigcptw, (///ˬ///✿) this, ny, swigtype_p_fwoat.getcptw(x), 😳 k, swigtype_p_fwoat.getcptw(distances), 😳 swigtype_p_wong_wong.getcptw(wabews.data()), σωσ w-wabews);
  }

}
