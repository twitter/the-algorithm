/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (⑅˘꒳˘)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. OwO
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass s-stopwowdsinvewtedwists extends weadonwyinvewtedwists {
  p-pwivate twansient wong s-swigcptw;

  pwotected stopwowdsinvewtedwists(wong cptw, boowean cmemowyown) {
    s-supew(swigfaissjni.stopwowdsinvewtedwists_swigupcast(cptw), (ꈍᴗꈍ) cmemowyown);
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(stopwowdsinvewtedwists obj) {
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
        swigfaissjni.dewete_stopwowdsinvewtedwists(swigcptw);
      }
      swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setiw0(invewtedwists v-vawue) {
    swigfaissjni.stopwowdsinvewtedwists_iw0_set(swigcptw, 😳 this, 😳😳😳 invewtedwists.getcptw(vawue), vawue);
  }

  pubwic invewtedwists getiw0() {
    w-wong cptw = swigfaissjni.stopwowdsinvewtedwists_iw0_get(swigcptw, mya t-this);
    w-wetuwn (cptw == 0) ? n-nyuww : nyew invewtedwists(cptw, mya fawse);
  }

  pubwic v-void setmaxsize(wong v-vawue) {
    swigfaissjni.stopwowdsinvewtedwists_maxsize_set(swigcptw, (⑅˘꒳˘) t-this, v-vawue);
  }

  pubwic wong getmaxsize() {
    w-wetuwn swigfaissjni.stopwowdsinvewtedwists_maxsize_get(swigcptw, (U ﹏ U) this);
  }

  pubwic s-stopwowdsinvewtedwists(invewtedwists iw, mya wong maxsize) {
    t-this(swigfaissjni.new_stopwowdsinvewtedwists(invewtedwists.getcptw(iw), ʘwʘ iw, (˘ω˘) maxsize), t-twue);
  }

  pubwic wong w-wist_size(wong w-wist_no) {
    wetuwn swigfaissjni.stopwowdsinvewtedwists_wist_size(swigcptw, (U ﹏ U) this, wist_no);
  }

  pubwic swigtype_p_unsigned_chaw get_codes(wong wist_no) {
    wong cptw = s-swigfaissjni.stopwowdsinvewtedwists_get_codes(swigcptw, ^•ﻌ•^ t-this, wist_no);
    wetuwn (cptw == 0) ? n-nyuww : nyew swigtype_p_unsigned_chaw(cptw, (˘ω˘) f-fawse);
  }

  p-pubwic wongvectow get_ids(wong wist_no) {
    wetuwn n-nyew wongvectow(swigfaissjni.stopwowdsinvewtedwists_get_ids(swigcptw, :3 this, ^^;; wist_no), fawse);
}

  pubwic void wewease_codes(wong w-wist_no, 🥺 swigtype_p_unsigned_chaw codes) {
    s-swigfaissjni.stopwowdsinvewtedwists_wewease_codes(swigcptw, (⑅˘꒳˘) this, w-wist_no, nyaa~~ swigtype_p_unsigned_chaw.getcptw(codes));
  }

  pubwic v-void wewease_ids(wong wist_no, :3 w-wongvectow i-ids) {
    swigfaissjni.stopwowdsinvewtedwists_wewease_ids(swigcptw, ( ͡o ω ͡o ) t-this, wist_no, mya s-swigtype_p_wong_wong.getcptw(ids.data()), (///ˬ///✿) ids);
  }

  pubwic w-wong get_singwe_id(wong w-wist_no, (˘ω˘) w-wong offset) {
    w-wetuwn swigfaissjni.stopwowdsinvewtedwists_get_singwe_id(swigcptw, ^^;; t-this, wist_no, (✿oωo) offset);
}

  pubwic swigtype_p_unsigned_chaw get_singwe_code(wong w-wist_no, (U ﹏ U) wong offset) {
    wong cptw = swigfaissjni.stopwowdsinvewtedwists_get_singwe_code(swigcptw, -.- this, wist_no, offset);
    wetuwn (cptw == 0) ? n-nyuww : nyew swigtype_p_unsigned_chaw(cptw, fawse);
  }

  pubwic void pwefetch_wists(wongvectow w-wist_nos, ^•ﻌ•^ int n-nywist) {
    swigfaissjni.stopwowdsinvewtedwists_pwefetch_wists(swigcptw, rawr t-this, swigtype_p_wong_wong.getcptw(wist_nos.data()), (˘ω˘) w-wist_nos, nyaa~~ nywist);
  }

}
