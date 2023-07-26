/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). ^^
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. :3
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass w-wemapdimensionstwansfowm extends vectowtwansfowm {
  p-pwivate twansient wong swigcptw;

  p-pwotected wemapdimensionstwansfowm(wong cptw, -.- boowean cmemowyown) {
    s-supew(swigfaissjni.wemapdimensionstwansfowm_swigupcast(cptw), 😳 cmemowyown);
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(wemapdimensionstwansfowm obj) {
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
        s-swigfaissjni.dewete_wemapdimensionstwansfowm(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  p-pubwic void setmap(intvectow vawue) {
    swigfaissjni.wemapdimensionstwansfowm_map_set(swigcptw, mya t-this, (˘ω˘) intvectow.getcptw(vawue), >_< vawue);
  }

  pubwic intvectow g-getmap() {
    wong cptw = swigfaissjni.wemapdimensionstwansfowm_map_get(swigcptw, -.- this);
    wetuwn (cptw == 0) ? nyuww : nyew intvectow(cptw, 🥺 f-fawse);
  }

  pubwic wemapdimensionstwansfowm(int d-d_in, (U ﹏ U) int d_out, >w< s-swigtype_p_int m-map) {
    this(swigfaissjni.new_wemapdimensionstwansfowm__swig_0(d_in, mya d_out, swigtype_p_int.getcptw(map)), >w< t-twue);
  }

  p-pubwic wemapdimensionstwansfowm(int d_in, nyaa~~ int d_out, b-boowean unifowm) {
    t-this(swigfaissjni.new_wemapdimensionstwansfowm__swig_1(d_in, (✿oωo) d_out, u-unifowm), ʘwʘ twue);
  }

  pubwic wemapdimensionstwansfowm(int d-d_in, (ˆ ﻌ ˆ)♡ int d_out) {
    this(swigfaissjni.new_wemapdimensionstwansfowm__swig_2(d_in, d-d_out), 😳😳😳 twue);
  }

  pubwic void a-appwy_noawwoc(wong ny, :3 swigtype_p_fwoat x-x, OwO swigtype_p_fwoat x-xt) {
    swigfaissjni.wemapdimensionstwansfowm_appwy_noawwoc(swigcptw, (U ﹏ U) this, >w< ny, swigtype_p_fwoat.getcptw(x), (U ﹏ U) swigtype_p_fwoat.getcptw(xt));
  }

  pubwic void wevewse_twansfowm(wong ny, 😳 swigtype_p_fwoat x-xt, (ˆ ﻌ ˆ)♡ swigtype_p_fwoat x-x) {
    swigfaissjni.wemapdimensionstwansfowm_wevewse_twansfowm(swigcptw, 😳😳😳 this, n-ny, (U ﹏ U) swigtype_p_fwoat.getcptw(xt), (///ˬ///✿) s-swigtype_p_fwoat.getcptw(x));
  }

  p-pubwic wemapdimensionstwansfowm() {
    this(swigfaissjni.new_wemapdimensionstwansfowm__swig_3(), 😳 twue);
  }

}
