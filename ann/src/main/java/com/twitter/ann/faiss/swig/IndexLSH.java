/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). :3
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (⑅˘꒳˘)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexwsh extends indexfwatcodes {
  pwivate twansient w-wong swigcptw;

  pwotected i-indexwsh(wong cptw, (///ˬ///✿) boowean cmemowyown) {
    supew(swigfaissjni.indexwsh_swigupcast(cptw), ^^;; cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected static wong getcptw(indexwsh obj) {
    w-wetuwn (obj == n-nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  pubwic s-synchwonized void dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        swigcmemown = f-fawse;
        swigfaissjni.dewete_indexwsh(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic v-void setnbits(int v-vawue) {
    swigfaissjni.indexwsh_nbits_set(swigcptw, >_< this, vawue);
  }

  p-pubwic int getnbits() {
    wetuwn swigfaissjni.indexwsh_nbits_get(swigcptw, rawr x3 this);
  }

  pubwic v-void setwotate_data(boowean vawue) {
    swigfaissjni.indexwsh_wotate_data_set(swigcptw, /(^•ω•^) this, :3 vawue);
  }

  pubwic boowean getwotate_data() {
    wetuwn s-swigfaissjni.indexwsh_wotate_data_get(swigcptw, (ꈍᴗꈍ) this);
  }

  pubwic v-void settwain_thweshowds(boowean v-vawue) {
    s-swigfaissjni.indexwsh_twain_thweshowds_set(swigcptw, /(^•ω•^) this, (⑅˘꒳˘) vawue);
  }

  pubwic boowean gettwain_thweshowds() {
    w-wetuwn s-swigfaissjni.indexwsh_twain_thweshowds_get(swigcptw, ( ͡o ω ͡o ) this);
  }

  p-pubwic void setwwot(wandomwotationmatwix v-vawue) {
    swigfaissjni.indexwsh_wwot_set(swigcptw, òωó t-this, (⑅˘꒳˘) wandomwotationmatwix.getcptw(vawue), XD vawue);
  }

  p-pubwic wandomwotationmatwix getwwot() {
    w-wong cptw = swigfaissjni.indexwsh_wwot_get(swigcptw, -.- t-this);
    wetuwn (cptw == 0) ? n-nyuww : n-nyew wandomwotationmatwix(cptw, :3 fawse);
  }

  pubwic void setthweshowds(fwoatvectow vawue) {
    swigfaissjni.indexwsh_thweshowds_set(swigcptw, nyaa~~ this, 😳 fwoatvectow.getcptw(vawue), v-vawue);
  }

  p-pubwic fwoatvectow getthweshowds() {
    w-wong cptw = swigfaissjni.indexwsh_thweshowds_get(swigcptw, (⑅˘꒳˘) t-this);
    w-wetuwn (cptw == 0) ? nyuww : nyew fwoatvectow(cptw, nyaa~~ fawse);
  }

  p-pubwic indexwsh(wong d, OwO int nbits, boowean wotate_data, rawr x3 boowean twain_thweshowds) {
    t-this(swigfaissjni.new_indexwsh__swig_0(d, XD nybits, w-wotate_data, σωσ t-twain_thweshowds), (U ᵕ U❁) t-twue);
  }

  pubwic indexwsh(wong d-d, (U ﹏ U) int nybits, :3 b-boowean wotate_data) {
    t-this(swigfaissjni.new_indexwsh__swig_1(d, ( ͡o ω ͡o ) n-nybits, σωσ wotate_data), >w< twue);
  }

  pubwic i-indexwsh(wong d-d, 😳😳😳 int nybits) {
    t-this(swigfaissjni.new_indexwsh__swig_2(d, OwO n-nybits), 😳 twue);
  }

  p-pubwic swigtype_p_fwoat appwy_pwepwocess(wong ny, 😳😳😳 swigtype_p_fwoat x-x) {
    wong cptw = swigfaissjni.indexwsh_appwy_pwepwocess(swigcptw, (˘ω˘) this, ny, swigtype_p_fwoat.getcptw(x));
    wetuwn (cptw == 0) ? nyuww : nyew s-swigtype_p_fwoat(cptw, ʘwʘ fawse);
  }

  pubwic void twain(wong ny, ( ͡o ω ͡o ) s-swigtype_p_fwoat x-x) {
    swigfaissjni.indexwsh_twain(swigcptw, o.O t-this, ny, >w< swigtype_p_fwoat.getcptw(x));
  }

  pubwic void seawch(wong n-ny, 😳 swigtype_p_fwoat x, 🥺 w-wong k, rawr x3 swigtype_p_fwoat d-distances, o.O wongvectow wabews) {
    swigfaissjni.indexwsh_seawch(swigcptw, rawr this, ny, swigtype_p_fwoat.getcptw(x), ʘwʘ k, swigtype_p_fwoat.getcptw(distances), 😳😳😳 swigtype_p_wong_wong.getcptw(wabews.data()), ^^;; w-wabews);
  }

  pubwic void twansfew_thweshowds(wineawtwansfowm v-vt) {
    swigfaissjni.indexwsh_twansfew_thweshowds(swigcptw, this, o.O w-wineawtwansfowm.getcptw(vt), (///ˬ///✿) v-vt);
  }

  pubwic indexwsh() {
    this(swigfaissjni.new_indexwsh__swig_3(), σωσ twue);
  }

  p-pubwic v-void sa_encode(wong ny, nyaa~~ swigtype_p_fwoat x-x, ^^;; s-swigtype_p_unsigned_chaw bytes) {
    swigfaissjni.indexwsh_sa_encode(swigcptw, ^•ﻌ•^ this, σωσ ny, swigtype_p_fwoat.getcptw(x), -.- swigtype_p_unsigned_chaw.getcptw(bytes));
  }

  p-pubwic void s-sa_decode(wong n-ny, ^^;; swigtype_p_unsigned_chaw bytes, XD swigtype_p_fwoat x-x) {
    s-swigfaissjni.indexwsh_sa_decode(swigcptw, 🥺 this, n-ny, swigtype_p_unsigned_chaw.getcptw(bytes), òωó swigtype_p_fwoat.getcptw(x));
  }

}
