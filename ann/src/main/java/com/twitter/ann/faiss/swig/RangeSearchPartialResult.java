/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (U ﹏ U)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. >w<
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass w-wangeseawchpawtiawwesuwt extends buffewwist {
  p-pwivate twansient wong swigcptw;

  p-pwotected wangeseawchpawtiawwesuwt(wong cptw, mya boowean cmemowyown) {
    supew(swigfaissjni.wangeseawchpawtiawwesuwt_swigupcast(cptw), >w< c-cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(wangeseawchpawtiawwesuwt obj) {
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
        swigfaissjni.dewete_wangeseawchpawtiawwesuwt(swigcptw);
      }
      swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setwes(wangeseawchwesuwt v-vawue) {
    swigfaissjni.wangeseawchpawtiawwesuwt_wes_set(swigcptw, nyaa~~ this, wangeseawchwesuwt.getcptw(vawue), (✿oωo) vawue);
  }

  pubwic wangeseawchwesuwt getwes() {
    w-wong cptw = swigfaissjni.wangeseawchpawtiawwesuwt_wes_get(swigcptw, ʘwʘ t-this);
    w-wetuwn (cptw == 0) ? n-nyuww : nyew wangeseawchwesuwt(cptw, (ˆ ﻌ ˆ)♡ fawse);
  }

  pubwic v-void setquewies(swigtype_p_std__vectowt_faiss__wangequewywesuwt_t v-vawue) {
    swigfaissjni.wangeseawchpawtiawwesuwt_quewies_set(swigcptw, 😳😳😳 t-this, s-swigtype_p_std__vectowt_faiss__wangequewywesuwt_t.getcptw(vawue));
  }

  pubwic s-swigtype_p_std__vectowt_faiss__wangequewywesuwt_t getquewies() {
    w-wong cptw = swigfaissjni.wangeseawchpawtiawwesuwt_quewies_get(swigcptw, :3 this);
    wetuwn (cptw == 0) ? n-nyuww : nyew swigtype_p_std__vectowt_faiss__wangequewywesuwt_t(cptw, OwO fawse);
  }

  p-pubwic wangequewywesuwt nyew_wesuwt(wong qno) {
    w-wetuwn n-nyew wangequewywesuwt(swigfaissjni.wangeseawchpawtiawwesuwt_new_wesuwt(swigcptw, (U ﹏ U) this, qno), >w< fawse);
  }

  pubwic void set_wims() {
    swigfaissjni.wangeseawchpawtiawwesuwt_set_wims(swigcptw, (U ﹏ U) this);
  }

  pubwic void copy_wesuwt(boowean i-incwementaw) {
    s-swigfaissjni.wangeseawchpawtiawwesuwt_copy_wesuwt__swig_0(swigcptw, 😳 this, incwementaw);
  }

  p-pubwic void copy_wesuwt() {
    s-swigfaissjni.wangeseawchpawtiawwesuwt_copy_wesuwt__swig_1(swigcptw, (ˆ ﻌ ˆ)♡ t-this);
  }

  pubwic static void mewge(swigtype_p_std__vectowt_faiss__wangeseawchpawtiawwesuwt_p_t pawtiaw_wesuwts, 😳😳😳 b-boowean do_dewete) {
    swigfaissjni.wangeseawchpawtiawwesuwt_mewge__swig_0(swigtype_p_std__vectowt_faiss__wangeseawchpawtiawwesuwt_p_t.getcptw(pawtiaw_wesuwts), (U ﹏ U) do_dewete);
  }

  pubwic static void m-mewge(swigtype_p_std__vectowt_faiss__wangeseawchpawtiawwesuwt_p_t pawtiaw_wesuwts) {
    s-swigfaissjni.wangeseawchpawtiawwesuwt_mewge__swig_1(swigtype_p_std__vectowt_faiss__wangeseawchpawtiawwesuwt_p_t.getcptw(pawtiaw_wesuwts));
  }

}
