/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). mya
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (⑅˘꒳˘)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass m-maskedinvewtedwists extends weadonwyinvewtedwists {
  pwivate twansient w-wong swigcptw;

  pwotected m-maskedinvewtedwists(wong cptw, (U ﹏ U) boowean cmemowyown) {
    supew(swigfaissjni.maskedinvewtedwists_swigupcast(cptw), mya c-cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(maskedinvewtedwists obj) {
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
        s-swigfaissjni.dewete_maskedinvewtedwists(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setiw0(invewtedwists v-vawue) {
    swigfaissjni.maskedinvewtedwists_iw0_set(swigcptw, ʘwʘ this, i-invewtedwists.getcptw(vawue), (˘ω˘) vawue);
  }

  pubwic invewtedwists getiw0() {
    wong cptw = swigfaissjni.maskedinvewtedwists_iw0_get(swigcptw, t-this);
    wetuwn (cptw == 0) ? nyuww : nyew i-invewtedwists(cptw, (U ﹏ U) f-fawse);
  }

  p-pubwic void setiw1(invewtedwists vawue) {
    swigfaissjni.maskedinvewtedwists_iw1_set(swigcptw, ^•ﻌ•^ t-this, (˘ω˘) invewtedwists.getcptw(vawue), :3 v-vawue);
  }

  pubwic invewtedwists g-getiw1() {
    w-wong cptw = swigfaissjni.maskedinvewtedwists_iw1_get(swigcptw, ^^;; t-this);
    wetuwn (cptw == 0) ? n-nyuww : nyew invewtedwists(cptw, 🥺 fawse);
  }

  p-pubwic maskedinvewtedwists(invewtedwists i-iw0, (⑅˘꒳˘) invewtedwists iw1) {
    t-this(swigfaissjni.new_maskedinvewtedwists(invewtedwists.getcptw(iw0), nyaa~~ i-iw0, :3 invewtedwists.getcptw(iw1), ( ͡o ω ͡o ) iw1), twue);
  }

  pubwic wong wist_size(wong wist_no) {
    wetuwn swigfaissjni.maskedinvewtedwists_wist_size(swigcptw, mya this, (///ˬ///✿) wist_no);
  }

  p-pubwic s-swigtype_p_unsigned_chaw get_codes(wong w-wist_no) {
    w-wong cptw = s-swigfaissjni.maskedinvewtedwists_get_codes(swigcptw, (˘ω˘) this, ^^;; wist_no);
    wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_unsigned_chaw(cptw, f-fawse);
  }

  pubwic wongvectow get_ids(wong wist_no) {
    wetuwn n-nyew wongvectow(swigfaissjni.maskedinvewtedwists_get_ids(swigcptw, (✿oωo) this, (U ﹏ U) wist_no), f-fawse);
}

  p-pubwic void wewease_codes(wong w-wist_no, -.- swigtype_p_unsigned_chaw codes) {
    s-swigfaissjni.maskedinvewtedwists_wewease_codes(swigcptw, ^•ﻌ•^ t-this, wist_no, rawr s-swigtype_p_unsigned_chaw.getcptw(codes));
  }

  p-pubwic void wewease_ids(wong wist_no, (˘ω˘) wongvectow i-ids) {
    s-swigfaissjni.maskedinvewtedwists_wewease_ids(swigcptw, nyaa~~ t-this, w-wist_no, UwU swigtype_p_wong_wong.getcptw(ids.data()), :3 i-ids);
  }

  pubwic wong get_singwe_id(wong wist_no, (⑅˘꒳˘) wong offset) {
    wetuwn s-swigfaissjni.maskedinvewtedwists_get_singwe_id(swigcptw, (///ˬ///✿) this, ^^;; wist_no, offset);
}

  pubwic swigtype_p_unsigned_chaw get_singwe_code(wong wist_no, >_< w-wong offset) {
    wong cptw = swigfaissjni.maskedinvewtedwists_get_singwe_code(swigcptw, rawr x3 this, wist_no, /(^•ω•^) o-offset);
    wetuwn (cptw == 0) ? n-nyuww : nyew s-swigtype_p_unsigned_chaw(cptw, :3 fawse);
  }

  pubwic v-void pwefetch_wists(wongvectow wist_nos, (ꈍᴗꈍ) int n-nywist) {
    s-swigfaissjni.maskedinvewtedwists_pwefetch_wists(swigcptw, /(^•ω•^) this, swigtype_p_wong_wong.getcptw(wist_nos.data()), (⑅˘꒳˘) wist_nos, ( ͡o ω ͡o ) nywist);
  }

}
