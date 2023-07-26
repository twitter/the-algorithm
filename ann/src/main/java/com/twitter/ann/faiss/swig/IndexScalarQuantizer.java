/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 😳
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (ˆ ﻌ ˆ)♡
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexscawawquantizew extends indexfwatcodes {
  pwivate twansient w-wong swigcptw;

  pwotected indexscawawquantizew(wong c-cptw, 😳😳😳 boowean cmemowyown) {
    supew(swigfaissjni.indexscawawquantizew_swigupcast(cptw), (U ﹏ U) cmemowyown);
    s-swigcptw = cptw;
  }

  pwotected s-static wong g-getcptw(indexscawawquantizew obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected v-void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        swigcmemown = f-fawse;
        s-swigfaissjni.dewete_indexscawawquantizew(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setsq(swigtype_p_scawawquantizew v-vawue) {
    swigfaissjni.indexscawawquantizew_sq_set(swigcptw, (///ˬ///✿) this, swigtype_p_scawawquantizew.getcptw(vawue));
  }

  p-pubwic swigtype_p_scawawquantizew getsq() {
    wetuwn nyew swigtype_p_scawawquantizew(swigfaissjni.indexscawawquantizew_sq_get(swigcptw, 😳 this), twue);
  }

  pubwic indexscawawquantizew(int d-d, 😳 swigtype_p_scawawquantizew__quantizewtype qtype, σωσ m-metwictype metwic) {
    t-this(swigfaissjni.new_indexscawawquantizew__swig_0(d, rawr x3 s-swigtype_p_scawawquantizew__quantizewtype.getcptw(qtype), OwO metwic.swigvawue()), /(^•ω•^) twue);
  }

  pubwic indexscawawquantizew(int d-d, 😳😳😳 swigtype_p_scawawquantizew__quantizewtype q-qtype) {
    this(swigfaissjni.new_indexscawawquantizew__swig_1(d, ( ͡o ω ͡o ) s-swigtype_p_scawawquantizew__quantizewtype.getcptw(qtype)), >_< t-twue);
  }

  pubwic i-indexscawawquantizew() {
    this(swigfaissjni.new_indexscawawquantizew__swig_2(), >w< t-twue);
  }

  pubwic void twain(wong ny, rawr swigtype_p_fwoat x-x) {
    swigfaissjni.indexscawawquantizew_twain(swigcptw, 😳 t-this, >w< ny, swigtype_p_fwoat.getcptw(x));
  }

  p-pubwic void s-seawch(wong ny, (⑅˘꒳˘) swigtype_p_fwoat x, OwO wong k, (ꈍᴗꈍ) swigtype_p_fwoat distances, 😳 wongvectow wabews) {
    swigfaissjni.indexscawawquantizew_seawch(swigcptw, 😳😳😳 this, mya ny, s-swigtype_p_fwoat.getcptw(x), mya k-k, swigtype_p_fwoat.getcptw(distances), (⑅˘꒳˘) s-swigtype_p_wong_wong.getcptw(wabews.data()), (U ﹏ U) w-wabews);
  }

  p-pubwic distancecomputew get_distance_computew() {
    wong cptw = swigfaissjni.indexscawawquantizew_get_distance_computew(swigcptw, mya t-this);
    wetuwn (cptw == 0) ? nyuww : nyew distancecomputew(cptw, ʘwʘ fawse);
  }

  p-pubwic void sa_encode(wong n-ny, (˘ω˘) swigtype_p_fwoat x-x, (U ﹏ U) swigtype_p_unsigned_chaw b-bytes) {
    swigfaissjni.indexscawawquantizew_sa_encode(swigcptw, ^•ﻌ•^ t-this, ny, (˘ω˘) s-swigtype_p_fwoat.getcptw(x), s-swigtype_p_unsigned_chaw.getcptw(bytes));
  }

  p-pubwic void sa_decode(wong ny, :3 swigtype_p_unsigned_chaw b-bytes, ^^;; s-swigtype_p_fwoat x-x) {
    swigfaissjni.indexscawawquantizew_sa_decode(swigcptw, 🥺 t-this, (⑅˘꒳˘) ny, swigtype_p_unsigned_chaw.getcptw(bytes), nyaa~~ s-swigtype_p_fwoat.getcptw(x));
  }

}
