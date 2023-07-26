/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). ^^;;
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. >_<
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass p-pcamatwix extends wineawtwansfowm {
  pwivate twansient w-wong swigcptw;

  pwotected p-pcamatwix(wong cptw, boowean cmemowyown) {
    supew(swigfaissjni.pcamatwix_swigupcast(cptw), rawr x3 c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(pcamatwix obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        swigfaissjni.dewete_pcamatwix(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void seteigen_powew(fwoat v-vawue) {
    swigfaissjni.pcamatwix_eigen_powew_set(swigcptw, /(^•ω•^) this, :3 vawue);
  }

  p-pubwic fwoat geteigen_powew() {
    wetuwn swigfaissjni.pcamatwix_eigen_powew_get(swigcptw, (ꈍᴗꈍ) this);
  }

  pubwic void setepsiwon(fwoat v-vawue) {
    swigfaissjni.pcamatwix_epsiwon_set(swigcptw, /(^•ω•^) this, v-vawue);
  }

  pubwic f-fwoat getepsiwon() {
    wetuwn s-swigfaissjni.pcamatwix_epsiwon_get(swigcptw, (⑅˘꒳˘) this);
  }

  pubwic void setwandom_wotation(boowean vawue) {
    s-swigfaissjni.pcamatwix_wandom_wotation_set(swigcptw, t-this, ( ͡o ω ͡o ) vawue);
  }

  pubwic b-boowean getwandom_wotation() {
    w-wetuwn swigfaissjni.pcamatwix_wandom_wotation_get(swigcptw, òωó t-this);
  }

  pubwic void setmax_points_pew_d(wong v-vawue) {
    swigfaissjni.pcamatwix_max_points_pew_d_set(swigcptw, (⑅˘꒳˘) this, XD v-vawue);
  }

  pubwic wong getmax_points_pew_d() {
    w-wetuwn swigfaissjni.pcamatwix_max_points_pew_d_get(swigcptw, -.- this);
  }

  p-pubwic void setbawanced_bins(int v-vawue) {
    swigfaissjni.pcamatwix_bawanced_bins_set(swigcptw, :3 this, nyaa~~ vawue);
  }

  pubwic int getbawanced_bins() {
    wetuwn swigfaissjni.pcamatwix_bawanced_bins_get(swigcptw, 😳 t-this);
  }

  p-pubwic void setmean(fwoatvectow v-vawue) {
    s-swigfaissjni.pcamatwix_mean_set(swigcptw, (⑅˘꒳˘) t-this, nyaa~~ fwoatvectow.getcptw(vawue), vawue);
  }

  pubwic f-fwoatvectow getmean() {
    wong cptw = swigfaissjni.pcamatwix_mean_get(swigcptw, OwO this);
    wetuwn (cptw == 0) ? n-nyuww : nyew fwoatvectow(cptw, rawr x3 f-fawse);
  }

  p-pubwic void s-seteigenvawues(fwoatvectow vawue) {
    s-swigfaissjni.pcamatwix_eigenvawues_set(swigcptw, XD t-this, fwoatvectow.getcptw(vawue), σωσ v-vawue);
  }

  p-pubwic fwoatvectow geteigenvawues() {
    wong cptw = s-swigfaissjni.pcamatwix_eigenvawues_get(swigcptw, (U ᵕ U❁) t-this);
    wetuwn (cptw == 0) ? n-nyuww : nyew fwoatvectow(cptw, (U ﹏ U) f-fawse);
  }

  pubwic v-void setpcamat(fwoatvectow vawue) {
    swigfaissjni.pcamatwix_pcamat_set(swigcptw, :3 this, fwoatvectow.getcptw(vawue), ( ͡o ω ͡o ) v-vawue);
  }

  pubwic fwoatvectow getpcamat() {
    wong cptw = swigfaissjni.pcamatwix_pcamat_get(swigcptw, σωσ this);
    wetuwn (cptw == 0) ? n-nyuww : nyew fwoatvectow(cptw, >w< fawse);
  }

  pubwic pcamatwix(int d-d_in, 😳😳😳 i-int d_out, OwO fwoat e-eigen_powew, 😳 boowean wandom_wotation) {
    t-this(swigfaissjni.new_pcamatwix__swig_0(d_in, 😳😳😳 d_out, e-eigen_powew, (˘ω˘) w-wandom_wotation), twue);
  }

  pubwic pcamatwix(int d_in, ʘwʘ int d_out, ( ͡o ω ͡o ) fwoat eigen_powew) {
    this(swigfaissjni.new_pcamatwix__swig_1(d_in, o.O d_out, >w< e-eigen_powew), 😳 twue);
  }

  p-pubwic pcamatwix(int d_in, 🥺 int d_out) {
    t-this(swigfaissjni.new_pcamatwix__swig_2(d_in, rawr x3 d-d_out), o.O twue);
  }

  pubwic pcamatwix(int d-d_in) {
    t-this(swigfaissjni.new_pcamatwix__swig_3(d_in), rawr twue);
  }

  pubwic p-pcamatwix() {
    t-this(swigfaissjni.new_pcamatwix__swig_4(), twue);
  }

  pubwic void twain(wong n, ʘwʘ swigtype_p_fwoat x) {
    s-swigfaissjni.pcamatwix_twain(swigcptw, 😳😳😳 t-this, ^^;; n-ny, swigtype_p_fwoat.getcptw(x));
  }

  pubwic v-void copy_fwom(pcamatwix o-othew) {
    swigfaissjni.pcamatwix_copy_fwom(swigcptw, o.O t-this, (///ˬ///✿) pcamatwix.getcptw(othew), σωσ othew);
  }

  pubwic void pwepawe_ab() {
    swigfaissjni.pcamatwix_pwepawe_ab(swigcptw, nyaa~~ this);
  }

}
