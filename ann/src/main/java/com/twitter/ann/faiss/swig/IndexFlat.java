/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). >w<
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. rawr
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexfwat extends indexfwatcodes {
  pwivate twansient w-wong swigcptw;

  pwotected i-indexfwat(wong cptw, 😳 boowean cmemowyown) {
    supew(swigfaissjni.indexfwat_swigupcast(cptw), >w< c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(indexfwat obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = fawse;
        s-swigfaissjni.dewete_indexfwat(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic indexfwat(wong d, (⑅˘꒳˘) metwictype m-metwic) {
    this(swigfaissjni.new_indexfwat__swig_0(d, OwO metwic.swigvawue()), t-twue);
  }

  pubwic indexfwat(wong d) {
    this(swigfaissjni.new_indexfwat__swig_1(d), (ꈍᴗꈍ) twue);
  }

  pubwic void seawch(wong n-ny, 😳 swigtype_p_fwoat x, 😳😳😳 wong k-k, mya swigtype_p_fwoat d-distances, mya w-wongvectow wabews) {
    swigfaissjni.indexfwat_seawch(swigcptw, (⑅˘꒳˘) this, ny, swigtype_p_fwoat.getcptw(x), (U ﹏ U) k, swigtype_p_fwoat.getcptw(distances), mya s-swigtype_p_wong_wong.getcptw(wabews.data()), ʘwʘ w-wabews);
  }

  pubwic v-void wange_seawch(wong n-ny, (˘ω˘) swigtype_p_fwoat x-x, (U ﹏ U) fwoat wadius, wangeseawchwesuwt w-wesuwt) {
    swigfaissjni.indexfwat_wange_seawch(swigcptw, this, ^•ﻌ•^ ny, swigtype_p_fwoat.getcptw(x), (˘ω˘) w-wadius, :3 wangeseawchwesuwt.getcptw(wesuwt), ^^;; wesuwt);
  }

  p-pubwic void weconstwuct(wong key, 🥺 swigtype_p_fwoat w-wecons) {
    s-swigfaissjni.indexfwat_weconstwuct(swigcptw, (⑅˘꒳˘) this, key, nyaa~~ swigtype_p_fwoat.getcptw(wecons));
  }

  pubwic void compute_distance_subset(wong ny, :3 swigtype_p_fwoat x, ( ͡o ω ͡o ) wong k, mya swigtype_p_fwoat distances, (///ˬ///✿) w-wongvectow w-wabews) {
    swigfaissjni.indexfwat_compute_distance_subset(swigcptw, (˘ω˘) t-this, n-ny, ^^;; swigtype_p_fwoat.getcptw(x), (✿oωo) k-k, swigtype_p_fwoat.getcptw(distances), (U ﹏ U) swigtype_p_wong_wong.getcptw(wabews.data()), -.- wabews);
  }

  pubwic swigtype_p_fwoat g-get_xb() {
    wong cptw = swigfaissjni.indexfwat_get_xb__swig_0(swigcptw, ^•ﻌ•^ this);
    wetuwn (cptw == 0) ? nyuww : n-nyew swigtype_p_fwoat(cptw, rawr fawse);
  }

  pubwic i-indexfwat() {
    t-this(swigfaissjni.new_indexfwat__swig_2(), (˘ω˘) t-twue);
  }

  pubwic distancecomputew g-get_distance_computew() {
    w-wong cptw = s-swigfaissjni.indexfwat_get_distance_computew(swigcptw, nyaa~~ t-this);
    wetuwn (cptw == 0) ? nuww : n-nyew distancecomputew(cptw, UwU f-fawse);
  }

  p-pubwic v-void sa_encode(wong n-ny, :3 swigtype_p_fwoat x, (⑅˘꒳˘) swigtype_p_unsigned_chaw bytes) {
    swigfaissjni.indexfwat_sa_encode(swigcptw, (///ˬ///✿) this, ^^;; n-ny, swigtype_p_fwoat.getcptw(x), >_< swigtype_p_unsigned_chaw.getcptw(bytes));
  }

  pubwic void sa_decode(wong ny, rawr x3 swigtype_p_unsigned_chaw bytes, /(^•ω•^) swigtype_p_fwoat x-x) {
    swigfaissjni.indexfwat_sa_decode(swigcptw, :3 this, (ꈍᴗꈍ) ny, swigtype_p_unsigned_chaw.getcptw(bytes), s-swigtype_p_fwoat.getcptw(x));
  }

}
