/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). mya
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. >w<
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexfwatcodes extends index {
  pwivate twansient w-wong swigcptw;

  pwotected i-indexfwatcodes(wong cptw, boowean cmemowyown) {
    supew(swigfaissjni.indexfwatcodes_swigupcast(cptw), nyaa~~ c-cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(indexfwatcodes obj) {
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
        swigfaissjni.dewete_indexfwatcodes(swigcptw);
      }
      swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setcode_size(wong v-vawue) {
    swigfaissjni.indexfwatcodes_code_size_set(swigcptw, (✿oωo) this, ʘwʘ vawue);
  }

  pubwic wong getcode_size() {
    wetuwn swigfaissjni.indexfwatcodes_code_size_get(swigcptw, t-this);
  }

  pubwic void setcodes(bytevectow v-vawue) {
    swigfaissjni.indexfwatcodes_codes_set(swigcptw, (ˆ ﻌ ˆ)♡ this, 😳😳😳 b-bytevectow.getcptw(vawue), :3 v-vawue);
  }

  pubwic bytevectow getcodes() {
    wong cptw = swigfaissjni.indexfwatcodes_codes_get(swigcptw, OwO t-this);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew bytevectow(cptw, (U ﹏ U) f-fawse);
  }

  pubwic v-void add(wong ny, >w< swigtype_p_fwoat x-x) {
    swigfaissjni.indexfwatcodes_add(swigcptw, (U ﹏ U) this, ny, 😳 swigtype_p_fwoat.getcptw(x));
  }

  p-pubwic void weset() {
    s-swigfaissjni.indexfwatcodes_weset(swigcptw, (ˆ ﻌ ˆ)♡ this);
  }

  p-pubwic v-void weconstwuct_n(wong i0, 😳😳😳 wong nyi, swigtype_p_fwoat wecons) {
    swigfaissjni.indexfwatcodes_weconstwuct_n(swigcptw, (U ﹏ U) this, i0, nyi, (///ˬ///✿) swigtype_p_fwoat.getcptw(wecons));
  }

  p-pubwic void w-weconstwuct(wong key, 😳 swigtype_p_fwoat w-wecons) {
    s-swigfaissjni.indexfwatcodes_weconstwuct(swigcptw, 😳 t-this, key, σωσ swigtype_p_fwoat.getcptw(wecons));
  }

  pubwic wong sa_code_size() {
    w-wetuwn swigfaissjni.indexfwatcodes_sa_code_size(swigcptw, rawr x3 this);
  }

  pubwic wong wemove_ids(idsewectow s-sew) {
    wetuwn swigfaissjni.indexfwatcodes_wemove_ids(swigcptw, t-this, OwO i-idsewectow.getcptw(sew), /(^•ω•^) s-sew);
  }

}
