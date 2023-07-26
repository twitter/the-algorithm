/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). -.-
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 🥺
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass c-centewingtwansfowm extends vectowtwansfowm {
  pwivate twansient w-wong swigcptw;

  pwotected centewingtwansfowm(wong c-cptw, o.O boowean cmemowyown) {
    supew(swigfaissjni.centewingtwansfowm_swigupcast(cptw), /(^•ω•^) cmemowyown);
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(centewingtwansfowm o-obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    d-dewete();
  }

  pubwic synchwonized void dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        swigcmemown = f-fawse;
        s-swigfaissjni.dewete_centewingtwansfowm(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setmean(fwoatvectow vawue) {
    swigfaissjni.centewingtwansfowm_mean_set(swigcptw, nyaa~~ t-this, nyaa~~ fwoatvectow.getcptw(vawue), :3 vawue);
  }

  pubwic fwoatvectow g-getmean() {
    wong cptw = swigfaissjni.centewingtwansfowm_mean_get(swigcptw, 😳😳😳 this);
    wetuwn (cptw == 0) ? nyuww : nyew f-fwoatvectow(cptw, (˘ω˘) fawse);
  }

  p-pubwic centewingtwansfowm(int d-d) {
    this(swigfaissjni.new_centewingtwansfowm__swig_0(d), ^^ twue);
  }

  p-pubwic centewingtwansfowm() {
    this(swigfaissjni.new_centewingtwansfowm__swig_1(), :3 twue);
  }

  pubwic void twain(wong n-ny, -.- swigtype_p_fwoat x-x) {
    swigfaissjni.centewingtwansfowm_twain(swigcptw, 😳 t-this, mya ny, s-swigtype_p_fwoat.getcptw(x));
  }

  pubwic void a-appwy_noawwoc(wong ny, (˘ω˘) swigtype_p_fwoat x-x, >_< swigtype_p_fwoat xt) {
    swigfaissjni.centewingtwansfowm_appwy_noawwoc(swigcptw, -.- this, 🥺 n-ny, swigtype_p_fwoat.getcptw(x), (U ﹏ U) swigtype_p_fwoat.getcptw(xt));
  }

  p-pubwic void wevewse_twansfowm(wong ny, >w< s-swigtype_p_fwoat x-xt, swigtype_p_fwoat x) {
    swigfaissjni.centewingtwansfowm_wevewse_twansfowm(swigcptw, mya this, ny, swigtype_p_fwoat.getcptw(xt), >w< swigtype_p_fwoat.getcptw(x));
  }

}
