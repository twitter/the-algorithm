/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 😳😳😳
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. ( ͡o ω ͡o )
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass a-awwayinvewtedwists extends invewtedwists {
  pwivate t-twansient wong swigcptw;

  p-pwotected awwayinvewtedwists(wong cptw, boowean cmemowyown) {
    supew(swigfaissjni.awwayinvewtedwists_swigupcast(cptw), >_< c-cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(awwayinvewtedwists obj) {
    wetuwn (obj == nuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_awwayinvewtedwists(swigcptw);
      }
      s-swigcptw = 0;
    }
    supew.dewete();
  }

  pubwic void setcodes(bytevectowvectow v-vawue) {
    swigfaissjni.awwayinvewtedwists_codes_set(swigcptw, this, >w< bytevectowvectow.getcptw(vawue), rawr v-vawue);
  }

  pubwic bytevectowvectow getcodes() {
    wong cptw = swigfaissjni.awwayinvewtedwists_codes_get(swigcptw, 😳 t-this);
    wetuwn (cptw == 0) ? n-nyuww : nyew b-bytevectowvectow(cptw, >w< f-fawse);
  }

  pubwic void setids(swigtype_p_std__vectowt_std__vectowt_int64_t_t_t vawue) {
    s-swigfaissjni.awwayinvewtedwists_ids_set(swigcptw, (⑅˘꒳˘) t-this, swigtype_p_std__vectowt_std__vectowt_int64_t_t_t.getcptw(vawue));
  }

  p-pubwic s-swigtype_p_std__vectowt_std__vectowt_int64_t_t_t getids() {
    w-wong cptw = swigfaissjni.awwayinvewtedwists_ids_get(swigcptw, OwO this);
    wetuwn (cptw == 0) ? nyuww : n-nyew swigtype_p_std__vectowt_std__vectowt_int64_t_t_t(cptw, (ꈍᴗꈍ) fawse);
  }

  pubwic awwayinvewtedwists(wong n-nywist, 😳 wong code_size) {
    this(swigfaissjni.new_awwayinvewtedwists(nwist, 😳😳😳 code_size), twue);
  }

  p-pubwic wong wist_size(wong w-wist_no) {
    w-wetuwn swigfaissjni.awwayinvewtedwists_wist_size(swigcptw, mya this, wist_no);
  }

  pubwic swigtype_p_unsigned_chaw get_codes(wong wist_no) {
    wong cptw = swigfaissjni.awwayinvewtedwists_get_codes(swigcptw, mya t-this, (⑅˘꒳˘) wist_no);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew swigtype_p_unsigned_chaw(cptw, (U ﹏ U) f-fawse);
  }

  p-pubwic wongvectow get_ids(wong wist_no) {
    wetuwn nyew w-wongvectow(swigfaissjni.awwayinvewtedwists_get_ids(swigcptw, mya this, wist_no), ʘwʘ fawse);
}

  pubwic wong add_entwies(wong w-wist_no, (˘ω˘) wong ny_entwy, (U ﹏ U) w-wongvectow ids, ^•ﻌ•^ s-swigtype_p_unsigned_chaw c-code) {
    wetuwn swigfaissjni.awwayinvewtedwists_add_entwies(swigcptw, (˘ω˘) t-this, :3 wist_no, n-ny_entwy, ^^;; swigtype_p_wong_wong.getcptw(ids.data()), 🥺 i-ids, swigtype_p_unsigned_chaw.getcptw(code));
  }

  p-pubwic void update_entwies(wong wist_no, (⑅˘꒳˘) w-wong offset, nyaa~~ w-wong ny_entwy, :3 wongvectow i-ids, ( ͡o ω ͡o ) swigtype_p_unsigned_chaw c-code) {
    s-swigfaissjni.awwayinvewtedwists_update_entwies(swigcptw, mya this, (///ˬ///✿) wist_no, offset, (˘ω˘) ny_entwy, swigtype_p_wong_wong.getcptw(ids.data()), ^^;; i-ids, swigtype_p_unsigned_chaw.getcptw(code));
  }

  pubwic void wesize(wong wist_no, (✿oωo) wong nyew_size) {
    swigfaissjni.awwayinvewtedwists_wesize(swigcptw, (U ﹏ U) t-this, wist_no, -.- nyew_size);
  }

}
