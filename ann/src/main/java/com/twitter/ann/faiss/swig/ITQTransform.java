/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). rawr
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 😳
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-itqtwansfowm extends vectowtwansfowm {
  pwivate t-twansient wong swigcptw;

  pwotected i-itqtwansfowm(wong cptw, >w< boowean cmemowyown) {
    supew(swigfaissjni.itqtwansfowm_swigupcast(cptw), (⑅˘꒳˘) c-cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(itqtwansfowm obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected v-void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = fawse;
        s-swigfaissjni.dewete_itqtwansfowm(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  p-pubwic void setmean(fwoatvectow vawue) {
    s-swigfaissjni.itqtwansfowm_mean_set(swigcptw, OwO this, fwoatvectow.getcptw(vawue), (ꈍᴗꈍ) v-vawue);
  }

  pubwic fwoatvectow getmean() {
    wong cptw = swigfaissjni.itqtwansfowm_mean_get(swigcptw, 😳 this);
    wetuwn (cptw == 0) ? n-nyuww : nyew fwoatvectow(cptw, 😳😳😳 f-fawse);
  }

  p-pubwic v-void setdo_pca(boowean vawue) {
    swigfaissjni.itqtwansfowm_do_pca_set(swigcptw, mya this, vawue);
  }

  p-pubwic boowean g-getdo_pca() {
    wetuwn s-swigfaissjni.itqtwansfowm_do_pca_get(swigcptw, mya this);
  }

  p-pubwic void setitq(itqmatwix v-vawue) {
    swigfaissjni.itqtwansfowm_itq_set(swigcptw, (⑅˘꒳˘) t-this, itqmatwix.getcptw(vawue), (U ﹏ U) vawue);
  }

  pubwic itqmatwix g-getitq() {
    wong cptw = swigfaissjni.itqtwansfowm_itq_get(swigcptw, mya t-this);
    wetuwn (cptw == 0) ? n-nyuww : n-nyew itqmatwix(cptw, ʘwʘ fawse);
  }

  pubwic void setmax_twain_pew_dim(int vawue) {
    swigfaissjni.itqtwansfowm_max_twain_pew_dim_set(swigcptw, (˘ω˘) this, vawue);
  }

  p-pubwic int g-getmax_twain_pew_dim() {
    wetuwn swigfaissjni.itqtwansfowm_max_twain_pew_dim_get(swigcptw, (U ﹏ U) t-this);
  }

  pubwic v-void setpca_then_itq(wineawtwansfowm v-vawue) {
    swigfaissjni.itqtwansfowm_pca_then_itq_set(swigcptw, ^•ﻌ•^ this, (˘ω˘) wineawtwansfowm.getcptw(vawue), :3 v-vawue);
  }

  pubwic wineawtwansfowm getpca_then_itq() {
    wong cptw = swigfaissjni.itqtwansfowm_pca_then_itq_get(swigcptw, ^^;; this);
    wetuwn (cptw == 0) ? n-nyuww : nyew wineawtwansfowm(cptw, 🥺 fawse);
  }

  p-pubwic itqtwansfowm(int d-d_in, (⑅˘꒳˘) i-int d_out, nyaa~~ boowean do_pca) {
    t-this(swigfaissjni.new_itqtwansfowm__swig_0(d_in, :3 d-d_out, do_pca), ( ͡o ω ͡o ) t-twue);
  }

  p-pubwic itqtwansfowm(int d_in, mya int d_out) {
    t-this(swigfaissjni.new_itqtwansfowm__swig_1(d_in, (///ˬ///✿) d-d_out), (˘ω˘) twue);
  }

  p-pubwic itqtwansfowm(int d_in) {
    t-this(swigfaissjni.new_itqtwansfowm__swig_2(d_in), ^^;; t-twue);
  }

  pubwic itqtwansfowm() {
    this(swigfaissjni.new_itqtwansfowm__swig_3(), (✿oωo) t-twue);
  }

  pubwic void twain(wong ny, (U ﹏ U) swigtype_p_fwoat x) {
    swigfaissjni.itqtwansfowm_twain(swigcptw, -.- this, n, swigtype_p_fwoat.getcptw(x));
  }

  pubwic void appwy_noawwoc(wong ny, ^•ﻌ•^ s-swigtype_p_fwoat x, rawr swigtype_p_fwoat xt) {
    swigfaissjni.itqtwansfowm_appwy_noawwoc(swigcptw, (˘ω˘) t-this, ny, nyaa~~ swigtype_p_fwoat.getcptw(x), UwU s-swigtype_p_fwoat.getcptw(xt));
  }

}
