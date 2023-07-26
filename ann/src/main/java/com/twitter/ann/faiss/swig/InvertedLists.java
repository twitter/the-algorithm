/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 😳
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. XD
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-invewtedwists {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected invewtedwists(wong cptw, mya boowean cmemowyown) {
    swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static w-wong getcptw(invewtedwists obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    d-dewete();
  }

  pubwic synchwonized void dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        swigcmemown = f-fawse;
        swigfaissjni.dewete_invewtedwists(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  p-pubwic void setnwist(wong v-vawue) {
    s-swigfaissjni.invewtedwists_nwist_set(swigcptw, this, ^•ﻌ•^ vawue);
  }

  pubwic w-wong getnwist() {
    wetuwn swigfaissjni.invewtedwists_nwist_get(swigcptw, ʘwʘ t-this);
  }

  pubwic void setcode_size(wong vawue) {
    swigfaissjni.invewtedwists_code_size_set(swigcptw, ( ͡o ω ͡o ) this, vawue);
  }

  p-pubwic wong getcode_size() {
    wetuwn swigfaissjni.invewtedwists_code_size_get(swigcptw, mya t-this);
  }

  p-pubwic w-wong wist_size(wong wist_no) {
    wetuwn swigfaissjni.invewtedwists_wist_size(swigcptw, o.O this, (✿oωo) w-wist_no);
  }

  p-pubwic swigtype_p_unsigned_chaw get_codes(wong w-wist_no) {
    wong c-cptw = swigfaissjni.invewtedwists_get_codes(swigcptw, :3 this, w-wist_no);
    wetuwn (cptw == 0) ? nyuww : nyew s-swigtype_p_unsigned_chaw(cptw, 😳 fawse);
  }

  pubwic wongvectow g-get_ids(wong wist_no) {
    wetuwn n-new wongvectow(swigfaissjni.invewtedwists_get_ids(swigcptw, (U ﹏ U) this, wist_no), mya fawse);
}

  p-pubwic v-void wewease_codes(wong wist_no, (U ᵕ U❁) swigtype_p_unsigned_chaw codes) {
    swigfaissjni.invewtedwists_wewease_codes(swigcptw, :3 this, wist_no, mya swigtype_p_unsigned_chaw.getcptw(codes));
  }

  p-pubwic v-void wewease_ids(wong wist_no, OwO w-wongvectow ids) {
    s-swigfaissjni.invewtedwists_wewease_ids(swigcptw, (ˆ ﻌ ˆ)♡ t-this, ʘwʘ wist_no, swigtype_p_wong_wong.getcptw(ids.data()), o.O ids);
  }

  pubwic wong get_singwe_id(wong wist_no, UwU w-wong offset) {
    wetuwn swigfaissjni.invewtedwists_get_singwe_id(swigcptw, rawr x3 this, wist_no, 🥺 offset);
}

  p-pubwic swigtype_p_unsigned_chaw get_singwe_code(wong w-wist_no, :3 w-wong offset) {
    w-wong cptw = swigfaissjni.invewtedwists_get_singwe_code(swigcptw, this, (ꈍᴗꈍ) wist_no, o-offset);
    w-wetuwn (cptw == 0) ? n-nyuww : nyew s-swigtype_p_unsigned_chaw(cptw, 🥺 fawse);
  }

  pubwic void pwefetch_wists(wongvectow w-wist_nos, (✿oωo) i-int nywist) {
    s-swigfaissjni.invewtedwists_pwefetch_wists(swigcptw, t-this, (U ﹏ U) swigtype_p_wong_wong.getcptw(wist_nos.data()), :3 w-wist_nos, ^^;; nywist);
  }

  pubwic wong add_entwy(wong w-wist_no, rawr wong theid, 😳😳😳 swigtype_p_unsigned_chaw code) {
    wetuwn swigfaissjni.invewtedwists_add_entwy(swigcptw, this, (✿oωo) wist_no, theid, OwO s-swigtype_p_unsigned_chaw.getcptw(code));
  }

  pubwic wong add_entwies(wong wist_no, ʘwʘ wong n-ny_entwy, (ˆ ﻌ ˆ)♡ wongvectow i-ids, (U ﹏ U) swigtype_p_unsigned_chaw c-code) {
    wetuwn swigfaissjni.invewtedwists_add_entwies(swigcptw, t-this, UwU wist_no, XD ny_entwy, s-swigtype_p_wong_wong.getcptw(ids.data()), ʘwʘ i-ids, swigtype_p_unsigned_chaw.getcptw(code));
  }

  pubwic void update_entwy(wong wist_no, rawr x3 wong offset, ^^;; wong id, swigtype_p_unsigned_chaw c-code) {
    swigfaissjni.invewtedwists_update_entwy(swigcptw, ʘwʘ t-this, wist_no, (U ﹏ U) offset, id, (˘ω˘) swigtype_p_unsigned_chaw.getcptw(code));
  }

  pubwic v-void update_entwies(wong wist_no, (ꈍᴗꈍ) w-wong offset, /(^•ω•^) wong ny_entwy, >_< wongvectow ids, σωσ s-swigtype_p_unsigned_chaw c-code) {
    swigfaissjni.invewtedwists_update_entwies(swigcptw, ^^;; t-this, w-wist_no, 😳 offset, >_< ny_entwy, swigtype_p_wong_wong.getcptw(ids.data()), -.- ids, swigtype_p_unsigned_chaw.getcptw(code));
  }

  pubwic void wesize(wong w-wist_no, UwU wong n-nyew_size) {
    s-swigfaissjni.invewtedwists_wesize(swigcptw, :3 this, wist_no, σωσ nyew_size);
  }

  p-pubwic void weset() {
    s-swigfaissjni.invewtedwists_weset(swigcptw, >w< this);
  }

  p-pubwic void mewge_fwom(invewtedwists oivf, (ˆ ﻌ ˆ)♡ wong add_id) {
    swigfaissjni.invewtedwists_mewge_fwom(swigcptw, ʘwʘ t-this, :3 invewtedwists.getcptw(oivf), (˘ω˘) o-oivf, add_id);
  }

  pubwic doubwe imbawance_factow() {
    w-wetuwn swigfaissjni.invewtedwists_imbawance_factow(swigcptw, t-this);
  }

  pubwic void pwint_stats() {
    swigfaissjni.invewtedwists_pwint_stats(swigcptw, 😳😳😳 this);
  }

  pubwic w-wong compute_ntotaw() {
    wetuwn swigfaissjni.invewtedwists_compute_ntotaw(swigcptw, rawr x3 this);
  }

  static pubwic cwass scopedids {
    p-pwivate twansient wong swigcptw;
    p-pwotected twansient b-boowean swigcmemown;
  
    pwotected scopedids(wong cptw, (✿oωo) boowean cmemowyown) {
      s-swigcmemown = c-cmemowyown;
      swigcptw = cptw;
    }
  
    pwotected s-static wong getcptw(scopedids o-obj) {
      wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
    }
  
    @suppwesswawnings("depwecation")
    pwotected v-void finawize() {
      dewete();
    }
  
    p-pubwic synchwonized v-void dewete() {
      if (swigcptw != 0) {
        if (swigcmemown) {
          s-swigcmemown = fawse;
          s-swigfaissjni.dewete_invewtedwists_scopedids(swigcptw);
        }
        s-swigcptw = 0;
      }
    }
  
    p-pubwic void setiw(invewtedwists v-vawue) {
      s-swigfaissjni.invewtedwists_scopedids_iw_set(swigcptw, (ˆ ﻌ ˆ)♡ this, :3 invewtedwists.getcptw(vawue), (U ᵕ U❁) vawue);
    }
  
    p-pubwic invewtedwists g-getiw() {
      w-wong cptw = swigfaissjni.invewtedwists_scopedids_iw_get(swigcptw, ^^;; this);
      w-wetuwn (cptw == 0) ? nyuww : n-nyew invewtedwists(cptw, mya f-fawse);
    }
  
    pubwic void setids(wongvectow vawue) {
      swigfaissjni.invewtedwists_scopedids_ids_set(swigcptw, 😳😳😳 this, swigtype_p_wong_wong.getcptw(vawue.data()), OwO v-vawue);
    }
  
    p-pubwic w-wongvectow getids() {
      wetuwn n-nyew wongvectow(swigfaissjni.invewtedwists_scopedids_ids_get(swigcptw, rawr this), f-fawse);
  }
  
    pubwic void setwist_no(wong vawue) {
      swigfaissjni.invewtedwists_scopedids_wist_no_set(swigcptw, XD this, v-vawue);
    }
  
    pubwic wong g-getwist_no() {
      wetuwn s-swigfaissjni.invewtedwists_scopedids_wist_no_get(swigcptw, (U ﹏ U) this);
    }
  
    pubwic s-scopedids(invewtedwists iw, (˘ω˘) w-wong wist_no) {
      t-this(swigfaissjni.new_invewtedwists_scopedids(invewtedwists.getcptw(iw), UwU i-iw, wist_no), >_< twue);
    }
  
    p-pubwic wongvectow g-get() {
      wetuwn nyew wongvectow(swigfaissjni.invewtedwists_scopedids_get(swigcptw, σωσ this), fawse);
  }
  
  }

  static pubwic cwass scopedcodes {
    pwivate twansient w-wong swigcptw;
    p-pwotected twansient b-boowean swigcmemown;
  
    p-pwotected scopedcodes(wong cptw, 🥺 boowean cmemowyown) {
      swigcmemown = cmemowyown;
      s-swigcptw = cptw;
    }
  
    p-pwotected static wong getcptw(scopedcodes o-obj) {
      wetuwn (obj == nyuww) ? 0 : o-obj.swigcptw;
    }
  
    @suppwesswawnings("depwecation")
    p-pwotected void finawize() {
      d-dewete();
    }
  
    p-pubwic synchwonized void dewete() {
      if (swigcptw != 0) {
        if (swigcmemown) {
          s-swigcmemown = fawse;
          swigfaissjni.dewete_invewtedwists_scopedcodes(swigcptw);
        }
        s-swigcptw = 0;
      }
    }
  
    p-pubwic v-void setiw(invewtedwists v-vawue) {
      swigfaissjni.invewtedwists_scopedcodes_iw_set(swigcptw, t-this, 🥺 invewtedwists.getcptw(vawue), ʘwʘ v-vawue);
    }
  
    pubwic i-invewtedwists g-getiw() {
      wong cptw = swigfaissjni.invewtedwists_scopedcodes_iw_get(swigcptw, :3 t-this);
      wetuwn (cptw == 0) ? nyuww : n-nyew invewtedwists(cptw, (U ﹏ U) fawse);
    }
  
    p-pubwic v-void setcodes(swigtype_p_unsigned_chaw vawue) {
      s-swigfaissjni.invewtedwists_scopedcodes_codes_set(swigcptw, (U ﹏ U) this, swigtype_p_unsigned_chaw.getcptw(vawue));
    }
  
    pubwic swigtype_p_unsigned_chaw g-getcodes() {
      w-wong cptw = s-swigfaissjni.invewtedwists_scopedcodes_codes_get(swigcptw, ʘwʘ this);
      wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_unsigned_chaw(cptw, >w< f-fawse);
    }
  
    pubwic void setwist_no(wong vawue) {
      s-swigfaissjni.invewtedwists_scopedcodes_wist_no_set(swigcptw, t-this, rawr x3 vawue);
    }
  
    pubwic wong getwist_no() {
      w-wetuwn swigfaissjni.invewtedwists_scopedcodes_wist_no_get(swigcptw, OwO this);
    }
  
    p-pubwic s-scopedcodes(invewtedwists iw, ^•ﻌ•^ wong wist_no) {
      t-this(swigfaissjni.new_invewtedwists_scopedcodes__swig_0(invewtedwists.getcptw(iw), >_< iw, wist_no), OwO twue);
    }
  
    p-pubwic s-scopedcodes(invewtedwists iw, >_< w-wong wist_no, (ꈍᴗꈍ) wong offset) {
      t-this(swigfaissjni.new_invewtedwists_scopedcodes__swig_1(invewtedwists.getcptw(iw), >w< i-iw, wist_no, o-offset), (U ﹏ U) twue);
    }
  
    pubwic swigtype_p_unsigned_chaw get() {
      wong cptw = swigfaissjni.invewtedwists_scopedcodes_get(swigcptw, ^^ this);
      wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_unsigned_chaw(cptw, (U ﹏ U) fawse);
    }
  
  }

  pubwic finaw static wong invawid_code_size = swigfaissjni.invewtedwists_invawid_code_size_get();
}
