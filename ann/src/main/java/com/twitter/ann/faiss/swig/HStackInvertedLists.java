/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). rawr x3
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. OwO
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass h-hstackinvewtedwists extends weadonwyinvewtedwists {
  pwivate twansient w-wong swigcptw;

  pwotected h-hstackinvewtedwists(wong cptw, /(^•ω•^) boowean cmemowyown) {
    supew(swigfaissjni.hstackinvewtedwists_swigupcast(cptw), 😳😳😳 c-cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(hstackinvewtedwists obj) {
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
        s-swigfaissjni.dewete_hstackinvewtedwists(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setiws(swigtype_p_std__vectowt_faiss__invewtedwists_const_p_t v-vawue) {
    swigfaissjni.hstackinvewtedwists_iws_set(swigcptw, ( ͡o ω ͡o ) this, swigtype_p_std__vectowt_faiss__invewtedwists_const_p_t.getcptw(vawue));
  }

  p-pubwic swigtype_p_std__vectowt_faiss__invewtedwists_const_p_t getiws() {
    wong cptw = swigfaissjni.hstackinvewtedwists_iws_get(swigcptw, >_< this);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew swigtype_p_std__vectowt_faiss__invewtedwists_const_p_t(cptw, >w< f-fawse);
  }

  p-pubwic hstackinvewtedwists(int nyiw, rawr swigtype_p_p_faiss__invewtedwists iws) {
    this(swigfaissjni.new_hstackinvewtedwists(niw, 😳 s-swigtype_p_p_faiss__invewtedwists.getcptw(iws)), >w< t-twue);
  }

  pubwic wong wist_size(wong w-wist_no) {
    w-wetuwn swigfaissjni.hstackinvewtedwists_wist_size(swigcptw, (⑅˘꒳˘) t-this, wist_no);
  }

  pubwic s-swigtype_p_unsigned_chaw get_codes(wong wist_no) {
    w-wong cptw = swigfaissjni.hstackinvewtedwists_get_codes(swigcptw, OwO t-this, (ꈍᴗꈍ) wist_no);
    w-wetuwn (cptw == 0) ? n-nyuww : nyew swigtype_p_unsigned_chaw(cptw, 😳 fawse);
  }

  pubwic wongvectow get_ids(wong wist_no) {
    wetuwn new wongvectow(swigfaissjni.hstackinvewtedwists_get_ids(swigcptw, t-this, 😳😳😳 wist_no), mya f-fawse);
}

  pubwic void p-pwefetch_wists(wongvectow w-wist_nos, mya i-int nywist) {
    swigfaissjni.hstackinvewtedwists_pwefetch_wists(swigcptw, (⑅˘꒳˘) this, swigtype_p_wong_wong.getcptw(wist_nos.data()), (U ﹏ U) wist_nos, mya n-nywist);
  }

  pubwic void wewease_codes(wong wist_no, ʘwʘ swigtype_p_unsigned_chaw codes) {
    swigfaissjni.hstackinvewtedwists_wewease_codes(swigcptw, (˘ω˘) this, (U ﹏ U) wist_no, s-swigtype_p_unsigned_chaw.getcptw(codes));
  }

  pubwic void w-wewease_ids(wong w-wist_no, ^•ﻌ•^ wongvectow i-ids) {
    swigfaissjni.hstackinvewtedwists_wewease_ids(swigcptw, (˘ω˘) t-this, w-wist_no, :3 swigtype_p_wong_wong.getcptw(ids.data()), ^^;; i-ids);
  }

  p-pubwic wong get_singwe_id(wong wist_no, 🥺 wong offset) {
    wetuwn s-swigfaissjni.hstackinvewtedwists_get_singwe_id(swigcptw, (⑅˘꒳˘) t-this, w-wist_no, nyaa~~ offset);
}

  p-pubwic swigtype_p_unsigned_chaw g-get_singwe_code(wong wist_no, :3 wong offset) {
    wong cptw = s-swigfaissjni.hstackinvewtedwists_get_singwe_code(swigcptw, this, ( ͡o ω ͡o ) wist_no, offset);
    wetuwn (cptw == 0) ? nyuww : new swigtype_p_unsigned_chaw(cptw, mya fawse);
  }

}
