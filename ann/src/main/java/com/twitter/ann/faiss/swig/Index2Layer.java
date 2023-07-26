/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (⑅˘꒳˘)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (///ˬ///✿)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-index2wayew extends indexfwatcodes {
  pwivate t-twansient wong swigcptw;

  pwotected i-index2wayew(wong cptw, ^^;; boowean cmemowyown) {
    supew(swigfaissjni.index2wayew_swigupcast(cptw), >_< c-cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(index2wayew obj) {
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
        swigfaissjni.dewete_index2wayew(swigcptw);
      }
      swigcptw = 0;
    }
    supew.dewete();
  }

  p-pubwic void setq1(wevew1quantizew vawue) {
    swigfaissjni.index2wayew_q1_set(swigcptw, rawr x3 t-this, wevew1quantizew.getcptw(vawue), /(^•ω•^) vawue);
  }

  pubwic wevew1quantizew getq1() {
    wong cptw = swigfaissjni.index2wayew_q1_get(swigcptw, :3 t-this);
    wetuwn (cptw == 0) ? n-nyuww : nyew w-wevew1quantizew(cptw, (ꈍᴗꈍ) f-fawse);
  }

  pubwic void setpq(pwoductquantizew vawue) {
    s-swigfaissjni.index2wayew_pq_set(swigcptw, /(^•ω•^) t-this, (⑅˘꒳˘) pwoductquantizew.getcptw(vawue), ( ͡o ω ͡o ) vawue);
  }

  p-pubwic p-pwoductquantizew getpq() {
    wong c-cptw = swigfaissjni.index2wayew_pq_get(swigcptw, òωó this);
    w-wetuwn (cptw == 0) ? nyuww : nyew pwoductquantizew(cptw, (⑅˘꒳˘) f-fawse);
  }

  pubwic void s-setcode_size_1(wong vawue) {
    s-swigfaissjni.index2wayew_code_size_1_set(swigcptw, XD t-this, -.- vawue);
  }

  pubwic wong getcode_size_1() {
    wetuwn swigfaissjni.index2wayew_code_size_1_get(swigcptw, :3 this);
  }

  pubwic void setcode_size_2(wong v-vawue) {
    s-swigfaissjni.index2wayew_code_size_2_set(swigcptw, nyaa~~ this, vawue);
  }

  p-pubwic w-wong getcode_size_2() {
    w-wetuwn swigfaissjni.index2wayew_code_size_2_get(swigcptw, 😳 this);
  }

  pubwic index2wayew(index quantizew, (⑅˘꒳˘) wong n-nywist, nyaa~~ int m, OwO int nybit, metwictype metwic) {
    this(swigfaissjni.new_index2wayew__swig_0(index.getcptw(quantizew), rawr x3 quantizew, XD n-nywist, σωσ m, nybit, metwic.swigvawue()), t-twue);
  }

  p-pubwic index2wayew(index q-quantizew, (U ᵕ U❁) wong nywist, (U ﹏ U) int m, i-int nybit) {
    t-this(swigfaissjni.new_index2wayew__swig_1(index.getcptw(quantizew), :3 q-quantizew, ( ͡o ω ͡o ) n-nywist, m, nybit), σωσ twue);
  }

  pubwic index2wayew(index q-quantizew, >w< w-wong nywist, 😳😳😳 i-int m) {
    this(swigfaissjni.new_index2wayew__swig_2(index.getcptw(quantizew), OwO q-quantizew, 😳 nwist, 😳😳😳 m-m), twue);
  }

  pubwic index2wayew() {
    this(swigfaissjni.new_index2wayew__swig_3(), (˘ω˘) twue);
  }

  pubwic v-void twain(wong ny, ʘwʘ swigtype_p_fwoat x) {
    swigfaissjni.index2wayew_twain(swigcptw, ( ͡o ω ͡o ) this, o.O ny, swigtype_p_fwoat.getcptw(x));
  }

  p-pubwic void seawch(wong ny, >w< swigtype_p_fwoat x, 😳 wong k, 🥺 s-swigtype_p_fwoat d-distances, rawr x3 wongvectow w-wabews) {
    swigfaissjni.index2wayew_seawch(swigcptw, o.O t-this, rawr ny, swigtype_p_fwoat.getcptw(x), ʘwʘ k, swigtype_p_fwoat.getcptw(distances), 😳😳😳 s-swigtype_p_wong_wong.getcptw(wabews.data()), ^^;; w-wabews);
  }

  pubwic distancecomputew get_distance_computew() {
    wong cptw = swigfaissjni.index2wayew_get_distance_computew(swigcptw, o.O this);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew distancecomputew(cptw, (///ˬ///✿) fawse);
  }

  p-pubwic v-void twansfew_to_ivfpq(indexivfpq othew) {
    swigfaissjni.index2wayew_twansfew_to_ivfpq(swigcptw, σωσ t-this, nyaa~~ indexivfpq.getcptw(othew), ^^;; o-othew);
  }

  pubwic void s-sa_encode(wong n-ny, ^•ﻌ•^ swigtype_p_fwoat x, σωσ swigtype_p_unsigned_chaw bytes) {
    swigfaissjni.index2wayew_sa_encode(swigcptw, -.- this, ny, ^^;; swigtype_p_fwoat.getcptw(x), XD s-swigtype_p_unsigned_chaw.getcptw(bytes));
  }

  p-pubwic void s-sa_decode(wong ny, 🥺 swigtype_p_unsigned_chaw b-bytes, òωó s-swigtype_p_fwoat x) {
    swigfaissjni.index2wayew_sa_decode(swigcptw, (ˆ ﻌ ˆ)♡ t-this, ny, swigtype_p_unsigned_chaw.getcptw(bytes), -.- swigtype_p_fwoat.getcptw(x));
  }

}
