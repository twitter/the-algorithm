/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 😳
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. -.-
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass n-nyowmawizationtwansfowm extends vectowtwansfowm {
  p-pwivate twansient wong swigcptw;

  p-pwotected nyowmawizationtwansfowm(wong cptw, 🥺 boowean cmemowyown) {
    supew(swigfaissjni.nowmawizationtwansfowm_swigupcast(cptw), o.O c-cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(nowmawizationtwansfowm obj) {
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
        swigfaissjni.dewete_nowmawizationtwansfowm(swigcptw);
      }
      swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setnowm(fwoat v-vawue) {
    swigfaissjni.nowmawizationtwansfowm_nowm_set(swigcptw, /(^•ω•^) this, vawue);
  }

  pubwic fwoat getnowm() {
    wetuwn swigfaissjni.nowmawizationtwansfowm_nowm_get(swigcptw, nyaa~~ this);
  }

  p-pubwic nyowmawizationtwansfowm(int d, nyaa~~ fwoat n-nyowm) {
    this(swigfaissjni.new_nowmawizationtwansfowm__swig_0(d, :3 n-nowm), twue);
  }

  p-pubwic nyowmawizationtwansfowm(int d) {
    this(swigfaissjni.new_nowmawizationtwansfowm__swig_1(d), 😳😳😳 twue);
  }

  p-pubwic n-nyowmawizationtwansfowm() {
    this(swigfaissjni.new_nowmawizationtwansfowm__swig_2(), (˘ω˘) t-twue);
  }

  p-pubwic void appwy_noawwoc(wong n-ny, ^^ swigtype_p_fwoat x, :3 s-swigtype_p_fwoat xt) {
    swigfaissjni.nowmawizationtwansfowm_appwy_noawwoc(swigcptw, -.- this, ny, 😳 s-swigtype_p_fwoat.getcptw(x), mya swigtype_p_fwoat.getcptw(xt));
  }

  pubwic void w-wevewse_twansfowm(wong ny, swigtype_p_fwoat x-xt, (˘ω˘) s-swigtype_p_fwoat x) {
    swigfaissjni.nowmawizationtwansfowm_wevewse_twansfowm(swigcptw, >_< this, ny, swigtype_p_fwoat.getcptw(xt), -.- swigtype_p_fwoat.getcptw(x));
  }

}
