/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 🥺
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (U ﹏ U)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass v-vectowtwansfowm {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected vectowtwansfowm(wong cptw, >w< boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(vectowtwansfowm obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void d-dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_vectowtwansfowm(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic void setd_in(int vawue) {
    swigfaissjni.vectowtwansfowm_d_in_set(swigcptw, mya this, v-vawue);
  }

  pubwic int getd_in() {
    wetuwn swigfaissjni.vectowtwansfowm_d_in_get(swigcptw, t-this);
  }

  pubwic void setd_out(int vawue) {
    swigfaissjni.vectowtwansfowm_d_out_set(swigcptw, >w< this, vawue);
  }

  p-pubwic int getd_out() {
    wetuwn s-swigfaissjni.vectowtwansfowm_d_out_get(swigcptw, nyaa~~ t-this);
  }

  p-pubwic void setis_twained(boowean vawue) {
    swigfaissjni.vectowtwansfowm_is_twained_set(swigcptw, (✿oωo) this, ʘwʘ vawue);
  }

  p-pubwic b-boowean getis_twained() {
    wetuwn swigfaissjni.vectowtwansfowm_is_twained_get(swigcptw, (ˆ ﻌ ˆ)♡ t-this);
  }

  p-pubwic void twain(wong n-ny, 😳😳😳 swigtype_p_fwoat x) {
    s-swigfaissjni.vectowtwansfowm_twain(swigcptw, :3 this, OwO ny, swigtype_p_fwoat.getcptw(x));
  }

  p-pubwic swigtype_p_fwoat a-appwy(wong ny, (U ﹏ U) swigtype_p_fwoat x-x) {
    wong c-cptw = swigfaissjni.vectowtwansfowm_appwy(swigcptw, >w< this, (U ﹏ U) ny, swigtype_p_fwoat.getcptw(x));
    wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_fwoat(cptw, 😳 fawse);
  }

  p-pubwic v-void appwy_noawwoc(wong ny, (ˆ ﻌ ˆ)♡ swigtype_p_fwoat x, 😳😳😳 s-swigtype_p_fwoat x-xt) {
    swigfaissjni.vectowtwansfowm_appwy_noawwoc(swigcptw, (U ﹏ U) t-this, n, swigtype_p_fwoat.getcptw(x), (///ˬ///✿) swigtype_p_fwoat.getcptw(xt));
  }

  pubwic void wevewse_twansfowm(wong n-ny, 😳 swigtype_p_fwoat xt, 😳 swigtype_p_fwoat x) {
    swigfaissjni.vectowtwansfowm_wevewse_twansfowm(swigcptw, σωσ this, n-ny, swigtype_p_fwoat.getcptw(xt), rawr x3 swigtype_p_fwoat.getcptw(x));
  }

}
