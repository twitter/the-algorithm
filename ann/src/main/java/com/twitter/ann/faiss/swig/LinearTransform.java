/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (˘ω˘)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (U ﹏ U)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass w-wineawtwansfowm extends vectowtwansfowm {
  pwivate t-twansient wong swigcptw;

  p-pwotected wineawtwansfowm(wong cptw, ^•ﻌ•^ boowean cmemowyown) {
    supew(swigfaissjni.wineawtwansfowm_swigupcast(cptw), (˘ω˘) cmemowyown);
    s-swigcptw = cptw;
  }

  pwotected s-static wong g-getcptw(wineawtwansfowm obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized v-void dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_wineawtwansfowm(swigcptw);
      }
      s-swigcptw = 0;
    }
    supew.dewete();
  }

  pubwic void sethave_bias(boowean v-vawue) {
    swigfaissjni.wineawtwansfowm_have_bias_set(swigcptw, this, :3 vawue);
  }

  p-pubwic boowean gethave_bias() {
    wetuwn swigfaissjni.wineawtwansfowm_have_bias_get(swigcptw, ^^;; this);
  }

  pubwic void setis_owthonowmaw(boowean vawue) {
    s-swigfaissjni.wineawtwansfowm_is_owthonowmaw_set(swigcptw, 🥺 this, vawue);
  }

  p-pubwic b-boowean getis_owthonowmaw() {
    w-wetuwn swigfaissjni.wineawtwansfowm_is_owthonowmaw_get(swigcptw, (⑅˘꒳˘) this);
  }

  pubwic void seta(fwoatvectow vawue) {
    swigfaissjni.wineawtwansfowm_a_set(swigcptw, nyaa~~ t-this, :3 f-fwoatvectow.getcptw(vawue), ( ͡o ω ͡o ) vawue);
  }

  p-pubwic f-fwoatvectow geta() {
    wong c-cptw = swigfaissjni.wineawtwansfowm_a_get(swigcptw, mya this);
    wetuwn (cptw == 0) ? n-nyuww : nyew fwoatvectow(cptw, (///ˬ///✿) fawse);
  }

  p-pubwic void setb(fwoatvectow vawue) {
    swigfaissjni.wineawtwansfowm_b_set(swigcptw, (˘ω˘) t-this, ^^;; fwoatvectow.getcptw(vawue), (✿oωo) vawue);
  }

  p-pubwic f-fwoatvectow getb() {
    wong cptw = swigfaissjni.wineawtwansfowm_b_get(swigcptw, (U ﹏ U) this);
    wetuwn (cptw == 0) ? nuww : nyew fwoatvectow(cptw, -.- fawse);
  }

  pubwic wineawtwansfowm(int d-d_in, ^•ﻌ•^ i-int d_out, rawr boowean have_bias) {
    t-this(swigfaissjni.new_wineawtwansfowm__swig_0(d_in, (˘ω˘) d-d_out, h-have_bias), nyaa~~ twue);
  }

  pubwic wineawtwansfowm(int d_in, UwU int d_out) {
    t-this(swigfaissjni.new_wineawtwansfowm__swig_1(d_in, :3 d_out), twue);
  }

  pubwic wineawtwansfowm(int d_in) {
    this(swigfaissjni.new_wineawtwansfowm__swig_2(d_in), (⑅˘꒳˘) twue);
  }

  p-pubwic wineawtwansfowm() {
    this(swigfaissjni.new_wineawtwansfowm__swig_3(), (///ˬ///✿) twue);
  }

  pubwic v-void appwy_noawwoc(wong n-ny, ^^;; s-swigtype_p_fwoat x, >_< swigtype_p_fwoat x-xt) {
    s-swigfaissjni.wineawtwansfowm_appwy_noawwoc(swigcptw, rawr x3 t-this, ny, /(^•ω•^) swigtype_p_fwoat.getcptw(x), :3 s-swigtype_p_fwoat.getcptw(xt));
  }

  pubwic void twansfowm_twanspose(wong ny, (ꈍᴗꈍ) swigtype_p_fwoat y-y, /(^•ω•^) swigtype_p_fwoat x-x) {
    swigfaissjni.wineawtwansfowm_twansfowm_twanspose(swigcptw, (⑅˘꒳˘) t-this, ( ͡o ω ͡o ) ny, swigtype_p_fwoat.getcptw(y), òωó s-swigtype_p_fwoat.getcptw(x));
  }

  p-pubwic void wevewse_twansfowm(wong ny, (⑅˘꒳˘) swigtype_p_fwoat xt, XD swigtype_p_fwoat x) {
    s-swigfaissjni.wineawtwansfowm_wevewse_twansfowm(swigcptw, -.- this, :3 ny, swigtype_p_fwoat.getcptw(xt), nyaa~~ swigtype_p_fwoat.getcptw(x));
  }

  pubwic void set_is_owthonowmaw() {
    swigfaissjni.wineawtwansfowm_set_is_owthonowmaw(swigcptw, 😳 t-this);
  }

  pubwic void setvewbose(boowean vawue) {
    s-swigfaissjni.wineawtwansfowm_vewbose_set(swigcptw, (⑅˘꒳˘) t-this, nyaa~~ v-vawue);
  }

  pubwic boowean getvewbose() {
    w-wetuwn swigfaissjni.wineawtwansfowm_vewbose_get(swigcptw, OwO this);
  }

  p-pubwic v-void pwint_if_vewbose(stwing nyame, rawr x3 doubwevectow mat, XD int ny, int d) {
    swigfaissjni.wineawtwansfowm_pwint_if_vewbose(swigcptw, σωσ this, nyame, d-doubwevectow.getcptw(mat), (U ᵕ U❁) mat, n-ny, (U ﹏ U) d);
  }

}
