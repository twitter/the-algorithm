/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). mya
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (⑅˘꒳˘)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass v-vstackinvewtedwists extends weadonwyinvewtedwists {
  pwivate twansient w-wong swigcptw;

  pwotected v-vstackinvewtedwists(wong cptw, (U ﹏ U) boowean cmemowyown) {
    supew(swigfaissjni.vstackinvewtedwists_swigupcast(cptw), mya c-cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(vstackinvewtedwists obj) {
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
        s-swigfaissjni.dewete_vstackinvewtedwists(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setiws(swigtype_p_std__vectowt_faiss__invewtedwists_const_p_t v-vawue) {
    swigfaissjni.vstackinvewtedwists_iws_set(swigcptw, ʘwʘ this, swigtype_p_std__vectowt_faiss__invewtedwists_const_p_t.getcptw(vawue));
  }

  p-pubwic swigtype_p_std__vectowt_faiss__invewtedwists_const_p_t getiws() {
    wong cptw = swigfaissjni.vstackinvewtedwists_iws_get(swigcptw, (˘ω˘) this);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew swigtype_p_std__vectowt_faiss__invewtedwists_const_p_t(cptw, (U ﹏ U) f-fawse);
  }

  p-pubwic void setcumsz(swigtype_p_std__vectowt_int64_t_t vawue) {
    swigfaissjni.vstackinvewtedwists_cumsz_set(swigcptw, ^•ﻌ•^ this, (˘ω˘) s-swigtype_p_std__vectowt_int64_t_t.getcptw(vawue));
  }

  p-pubwic swigtype_p_std__vectowt_int64_t_t g-getcumsz() {
    w-wong cptw = swigfaissjni.vstackinvewtedwists_cumsz_get(swigcptw, :3 t-this);
    wetuwn (cptw == 0) ? n-nyuww : nyew swigtype_p_std__vectowt_int64_t_t(cptw, ^^;; fawse);
  }

  p-pubwic vstackinvewtedwists(int n-nyiw, 🥺 swigtype_p_p_faiss__invewtedwists iws) {
    this(swigfaissjni.new_vstackinvewtedwists(niw, (⑅˘꒳˘) s-swigtype_p_p_faiss__invewtedwists.getcptw(iws)), nyaa~~ t-twue);
  }

  pubwic wong wist_size(wong wist_no) {
    wetuwn swigfaissjni.vstackinvewtedwists_wist_size(swigcptw, :3 this, wist_no);
  }

  pubwic swigtype_p_unsigned_chaw g-get_codes(wong w-wist_no) {
    wong cptw = s-swigfaissjni.vstackinvewtedwists_get_codes(swigcptw, ( ͡o ω ͡o ) t-this, wist_no);
    w-wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_unsigned_chaw(cptw, mya fawse);
  }

  p-pubwic wongvectow get_ids(wong wist_no) {
    wetuwn nyew wongvectow(swigfaissjni.vstackinvewtedwists_get_ids(swigcptw, (///ˬ///✿) t-this, wist_no), (˘ω˘) fawse);
}

  pubwic v-void wewease_codes(wong w-wist_no, s-swigtype_p_unsigned_chaw codes) {
    swigfaissjni.vstackinvewtedwists_wewease_codes(swigcptw, ^^;; t-this, wist_no, (✿oωo) s-swigtype_p_unsigned_chaw.getcptw(codes));
  }

  p-pubwic void w-wewease_ids(wong wist_no, (U ﹏ U) wongvectow ids) {
    s-swigfaissjni.vstackinvewtedwists_wewease_ids(swigcptw, -.- t-this, ^•ﻌ•^ wist_no, s-swigtype_p_wong_wong.getcptw(ids.data()), rawr i-ids);
  }

  pubwic w-wong get_singwe_id(wong wist_no, (˘ω˘) wong offset) {
    wetuwn s-swigfaissjni.vstackinvewtedwists_get_singwe_id(swigcptw, nyaa~~ this, wist_no, UwU offset);
}

  pubwic swigtype_p_unsigned_chaw get_singwe_code(wong wist_no, :3 w-wong offset) {
    wong cptw = swigfaissjni.vstackinvewtedwists_get_singwe_code(swigcptw, this, (⑅˘꒳˘) w-wist_no, (///ˬ///✿) offset);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew swigtype_p_unsigned_chaw(cptw, ^^;; fawse);
  }

  p-pubwic void pwefetch_wists(wongvectow w-wist_nos, >_< int nywist) {
    s-swigfaissjni.vstackinvewtedwists_pwefetch_wists(swigcptw, rawr x3 this, swigtype_p_wong_wong.getcptw(wist_nos.data()), /(^•ω•^) wist_nos, :3 nywist);
  }

}
