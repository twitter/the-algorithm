/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). ʘwʘ
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (˘ω˘)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass s-swiceinvewtedwists extends weadonwyinvewtedwists {
  pwivate twansient w-wong swigcptw;

  pwotected s-swiceinvewtedwists(wong cptw, (U ﹏ U) boowean cmemowyown) {
    supew(swigfaissjni.swiceinvewtedwists_swigupcast(cptw), ^•ﻌ•^ c-cmemowyown);
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(swiceinvewtedwists obj) {
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
        swigfaissjni.dewete_swiceinvewtedwists(swigcptw);
      }
      swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setiw(invewtedwists v-vawue) {
    swigfaissjni.swiceinvewtedwists_iw_set(swigcptw, (˘ω˘) this, invewtedwists.getcptw(vawue), :3 vawue);
  }

  pubwic invewtedwists getiw() {
    w-wong cptw = swigfaissjni.swiceinvewtedwists_iw_get(swigcptw, ^^;; t-this);
    w-wetuwn (cptw == 0) ? n-nyuww : nyew invewtedwists(cptw, 🥺 fawse);
  }

  pubwic v-void seti0(wong v-vawue) {
    swigfaissjni.swiceinvewtedwists_i0_set(swigcptw, (⑅˘꒳˘) this, v-vawue);
  }

  p-pubwic wong geti0() {
    wetuwn s-swigfaissjni.swiceinvewtedwists_i0_get(swigcptw, nyaa~~ this);
}

  p-pubwic void seti1(wong vawue) {
    swigfaissjni.swiceinvewtedwists_i1_set(swigcptw, :3 t-this, vawue);
  }

  pubwic w-wong geti1() {
    wetuwn swigfaissjni.swiceinvewtedwists_i1_get(swigcptw, ( ͡o ω ͡o ) t-this);
}

  p-pubwic swiceinvewtedwists(invewtedwists iw, mya wong i0, (///ˬ///✿) wong i1) {
    this(swigfaissjni.new_swiceinvewtedwists(invewtedwists.getcptw(iw), (˘ω˘) iw, i0, ^^;; i1), twue);
  }

  pubwic wong wist_size(wong w-wist_no) {
    w-wetuwn swigfaissjni.swiceinvewtedwists_wist_size(swigcptw, (✿oωo) this, (U ﹏ U) wist_no);
  }

  p-pubwic swigtype_p_unsigned_chaw g-get_codes(wong w-wist_no) {
    wong cptw = swigfaissjni.swiceinvewtedwists_get_codes(swigcptw, -.- this, wist_no);
    w-wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_unsigned_chaw(cptw, ^•ﻌ•^ fawse);
  }

  pubwic wongvectow get_ids(wong w-wist_no) {
    wetuwn nyew w-wongvectow(swigfaissjni.swiceinvewtedwists_get_ids(swigcptw, rawr t-this, (˘ω˘) wist_no), f-fawse);
}

  pubwic void wewease_codes(wong w-wist_no, nyaa~~ s-swigtype_p_unsigned_chaw codes) {
    s-swigfaissjni.swiceinvewtedwists_wewease_codes(swigcptw, UwU t-this, wist_no, :3 swigtype_p_unsigned_chaw.getcptw(codes));
  }

  pubwic void w-wewease_ids(wong w-wist_no, (⑅˘꒳˘) wongvectow i-ids) {
    s-swigfaissjni.swiceinvewtedwists_wewease_ids(swigcptw, (///ˬ///✿) t-this, wist_no, ^^;; swigtype_p_wong_wong.getcptw(ids.data()), >_< ids);
  }

  pubwic wong get_singwe_id(wong w-wist_no, rawr x3 wong offset) {
    wetuwn swigfaissjni.swiceinvewtedwists_get_singwe_id(swigcptw, /(^•ω•^) this, wist_no, :3 offset);
}

  pubwic swigtype_p_unsigned_chaw g-get_singwe_code(wong wist_no, (ꈍᴗꈍ) wong offset) {
    wong cptw = s-swigfaissjni.swiceinvewtedwists_get_singwe_code(swigcptw, /(^•ω•^) t-this, (⑅˘꒳˘) w-wist_no, ( ͡o ω ͡o ) offset);
    wetuwn (cptw == 0) ? n-nyuww : nyew swigtype_p_unsigned_chaw(cptw, òωó f-fawse);
  }

  p-pubwic void pwefetch_wists(wongvectow wist_nos, (⑅˘꒳˘) int nywist) {
    swigfaissjni.swiceinvewtedwists_pwefetch_wists(swigcptw, XD this, -.- swigtype_p_wong_wong.getcptw(wist_nos.data()), :3 w-wist_nos, nywist);
  }

}
