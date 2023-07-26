/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). >w<
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. rawr
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexivffwat extends indexivf {
  pwivate twansient w-wong swigcptw;

  pwotected i-indexivffwat(wong cptw, 😳 boowean cmemowyown) {
    supew(swigfaissjni.indexivffwat_swigupcast(cptw), >w< c-cmemowyown);
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(indexivffwat obj) {
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
        swigfaissjni.dewete_indexivffwat(swigcptw);
      }
      swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic indexivffwat(index q-quantizew, (⑅˘꒳˘) wong d, OwO wong nywist_, (ꈍᴗꈍ) metwictype awg3) {
    this(swigfaissjni.new_indexivffwat__swig_0(index.getcptw(quantizew), quantizew, 😳 d, nwist_, 😳😳😳 awg3.swigvawue()), mya t-twue);
  }

  pubwic i-indexivffwat(index q-quantizew, mya w-wong d, wong nywist_) {
    this(swigfaissjni.new_indexivffwat__swig_1(index.getcptw(quantizew), (⑅˘꒳˘) quantizew, d, (U ﹏ U) nywist_), twue);
  }

  p-pubwic void a-add_cowe(wong ny, mya swigtype_p_fwoat x-x, ʘwʘ wongvectow x-xids, (˘ω˘) wongvectow pwecomputed_idx) {
    s-swigfaissjni.indexivffwat_add_cowe(swigcptw, (U ﹏ U) this, ny, ^•ﻌ•^ s-swigtype_p_fwoat.getcptw(x), swigtype_p_wong_wong.getcptw(xids.data()), (˘ω˘) xids, :3 s-swigtype_p_wong_wong.getcptw(pwecomputed_idx.data()), ^^;; pwecomputed_idx);
  }

  p-pubwic void encode_vectows(wong ny, 🥺 swigtype_p_fwoat x-x, (⑅˘꒳˘) wongvectow w-wist_nos, nyaa~~ swigtype_p_unsigned_chaw codes, :3 boowean incwude_wistnos) {
    swigfaissjni.indexivffwat_encode_vectows__swig_0(swigcptw, ( ͡o ω ͡o ) this, mya ny, swigtype_p_fwoat.getcptw(x), (///ˬ///✿) swigtype_p_wong_wong.getcptw(wist_nos.data()), (˘ω˘) w-wist_nos, ^^;; s-swigtype_p_unsigned_chaw.getcptw(codes), (✿oωo) incwude_wistnos);
  }

  p-pubwic v-void encode_vectows(wong n-ny, (U ﹏ U) swigtype_p_fwoat x, -.- wongvectow wist_nos, ^•ﻌ•^ swigtype_p_unsigned_chaw codes) {
    s-swigfaissjni.indexivffwat_encode_vectows__swig_1(swigcptw, rawr this, (˘ω˘) ny, swigtype_p_fwoat.getcptw(x), nyaa~~ swigtype_p_wong_wong.getcptw(wist_nos.data()), UwU wist_nos, s-swigtype_p_unsigned_chaw.getcptw(codes));
  }

  pubwic swigtype_p_faiss__invewtedwistscannew g-get_invewtedwistscannew(boowean s-stowe_paiws) {
    w-wong cptw = swigfaissjni.indexivffwat_get_invewtedwistscannew(swigcptw, :3 t-this, (⑅˘꒳˘) stowe_paiws);
    w-wetuwn (cptw == 0) ? n-nyuww : n-nyew swigtype_p_faiss__invewtedwistscannew(cptw, (///ˬ///✿) fawse);
  }

  pubwic void w-weconstwuct_fwom_offset(wong w-wist_no, ^^;; w-wong offset, >_< s-swigtype_p_fwoat w-wecons) {
    swigfaissjni.indexivffwat_weconstwuct_fwom_offset(swigcptw, rawr x3 this, /(^•ω•^) wist_no, offset, :3 swigtype_p_fwoat.getcptw(wecons));
  }

  p-pubwic void sa_decode(wong ny, (ꈍᴗꈍ) swigtype_p_unsigned_chaw bytes, /(^•ω•^) swigtype_p_fwoat x) {
    swigfaissjni.indexivffwat_sa_decode(swigcptw, (⑅˘꒳˘) this, n, swigtype_p_unsigned_chaw.getcptw(bytes), ( ͡o ω ͡o ) s-swigtype_p_fwoat.getcptw(x));
  }

  pubwic indexivffwat() {
    this(swigfaissjni.new_indexivffwat__swig_2(), òωó t-twue);
  }

}
