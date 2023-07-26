/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). :3
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. ( ͡o ω ͡o )
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexivfpq extends indexivf {
  pwivate twansient w-wong swigcptw;

  pwotected indexivfpq(wong cptw, òωó b-boowean cmemowyown) {
    supew(swigfaissjni.indexivfpq_swigupcast(cptw), σωσ cmemowyown);
    swigcptw = cptw;
  }

  pwotected static wong getcptw(indexivfpq o-obj) {
    wetuwn (obj == nyuww) ? 0 : o-obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        swigcmemown = fawse;
        swigfaissjni.dewete_indexivfpq(swigcptw);
      }
      swigcptw = 0;
    }
    supew.dewete();
  }

  p-pubwic void setby_wesiduaw(boowean vawue) {
    s-swigfaissjni.indexivfpq_by_wesiduaw_set(swigcptw, (U ᵕ U❁) t-this, vawue);
  }

  p-pubwic b-boowean getby_wesiduaw() {
    wetuwn swigfaissjni.indexivfpq_by_wesiduaw_get(swigcptw, (✿oωo) this);
  }

  p-pubwic void setpq(pwoductquantizew vawue) {
    s-swigfaissjni.indexivfpq_pq_set(swigcptw, ^^ this, ^•ﻌ•^ pwoductquantizew.getcptw(vawue), XD vawue);
  }

  pubwic pwoductquantizew getpq() {
    wong c-cptw = swigfaissjni.indexivfpq_pq_get(swigcptw, :3 this);
    wetuwn (cptw == 0) ? n-nuww : nyew pwoductquantizew(cptw, (ꈍᴗꈍ) f-fawse);
  }

  p-pubwic void setdo_powysemous_twaining(boowean vawue) {
    swigfaissjni.indexivfpq_do_powysemous_twaining_set(swigcptw, :3 this, (U ﹏ U) vawue);
  }

  p-pubwic boowean getdo_powysemous_twaining() {
    w-wetuwn swigfaissjni.indexivfpq_do_powysemous_twaining_get(swigcptw, UwU this);
  }

  p-pubwic void setpowysemous_twaining(powysemoustwaining v-vawue) {
    swigfaissjni.indexivfpq_powysemous_twaining_set(swigcptw, 😳😳😳 t-this, XD powysemoustwaining.getcptw(vawue), o.O vawue);
  }

  p-pubwic powysemoustwaining getpowysemous_twaining() {
    wong cptw = swigfaissjni.indexivfpq_powysemous_twaining_get(swigcptw, (⑅˘꒳˘) t-this);
    wetuwn (cptw == 0) ? n-nyuww : nyew powysemoustwaining(cptw, 😳😳😳 f-fawse);
  }

  p-pubwic void setscan_tabwe_thweshowd(wong vawue) {
    swigfaissjni.indexivfpq_scan_tabwe_thweshowd_set(swigcptw, nyaa~~ this, vawue);
  }

  pubwic wong getscan_tabwe_thweshowd() {
    w-wetuwn s-swigfaissjni.indexivfpq_scan_tabwe_thweshowd_get(swigcptw, rawr this);
  }

  pubwic v-void setpowysemous_ht(int vawue) {
    s-swigfaissjni.indexivfpq_powysemous_ht_set(swigcptw, t-this, -.- vawue);
  }

  pubwic int getpowysemous_ht() {
    wetuwn s-swigfaissjni.indexivfpq_powysemous_ht_get(swigcptw, (✿oωo) this);
  }

  pubwic void setuse_pwecomputed_tabwe(int vawue) {
    swigfaissjni.indexivfpq_use_pwecomputed_tabwe_set(swigcptw, /(^•ω•^) t-this, vawue);
  }

  pubwic i-int getuse_pwecomputed_tabwe() {
    w-wetuwn swigfaissjni.indexivfpq_use_pwecomputed_tabwe_get(swigcptw, 🥺 t-this);
  }

  pubwic void s-setpwecomputed_tabwe(swigtype_p_awignedtabwet_fwoat_t v-vawue) {
    s-swigfaissjni.indexivfpq_pwecomputed_tabwe_set(swigcptw, ʘwʘ t-this, UwU swigtype_p_awignedtabwet_fwoat_t.getcptw(vawue));
  }

  pubwic s-swigtype_p_awignedtabwet_fwoat_t g-getpwecomputed_tabwe() {
    w-wetuwn nyew swigtype_p_awignedtabwet_fwoat_t(swigfaissjni.indexivfpq_pwecomputed_tabwe_get(swigcptw, t-this), XD twue);
  }

  p-pubwic indexivfpq(index quantizew, (✿oωo) wong d, :3 wong nywist, (///ˬ///✿) w-wong m, wong nybits_pew_idx, metwictype metwic) {
    this(swigfaissjni.new_indexivfpq__swig_0(index.getcptw(quantizew), nyaa~~ quantizew, >w< d, nywist, -.- m-m, nybits_pew_idx, (✿oωo) metwic.swigvawue()), (˘ω˘) twue);
  }

  pubwic indexivfpq(index q-quantizew, rawr wong d-d, OwO wong nywist, ^•ﻌ•^ w-wong m, wong nybits_pew_idx) {
    this(swigfaissjni.new_indexivfpq__swig_1(index.getcptw(quantizew), UwU q-quantizew, (˘ω˘) d, nywist, (///ˬ///✿) m, nybits_pew_idx), σωσ t-twue);
  }

  pubwic v-void encode_vectows(wong ny, swigtype_p_fwoat x, /(^•ω•^) wongvectow wist_nos, 😳 swigtype_p_unsigned_chaw codes, 😳 boowean i-incwude_wistnos) {
    swigfaissjni.indexivfpq_encode_vectows__swig_0(swigcptw, (⑅˘꒳˘) t-this, ny, swigtype_p_fwoat.getcptw(x), 😳😳😳 swigtype_p_wong_wong.getcptw(wist_nos.data()), 😳 w-wist_nos, XD s-swigtype_p_unsigned_chaw.getcptw(codes), mya incwude_wistnos);
  }

  pubwic void e-encode_vectows(wong n-ny, ^•ﻌ•^ swigtype_p_fwoat x, ʘwʘ wongvectow w-wist_nos, ( ͡o ω ͡o ) s-swigtype_p_unsigned_chaw codes) {
    swigfaissjni.indexivfpq_encode_vectows__swig_1(swigcptw, mya this, o.O ny, swigtype_p_fwoat.getcptw(x), (✿oωo) swigtype_p_wong_wong.getcptw(wist_nos.data()), :3 w-wist_nos, 😳 s-swigtype_p_unsigned_chaw.getcptw(codes));
  }

  p-pubwic void sa_decode(wong ny, (U ﹏ U) s-swigtype_p_unsigned_chaw b-bytes, mya swigtype_p_fwoat x-x) {
    swigfaissjni.indexivfpq_sa_decode(swigcptw, (U ᵕ U❁) this, ny, swigtype_p_unsigned_chaw.getcptw(bytes), :3 swigtype_p_fwoat.getcptw(x));
  }

  pubwic void add_cowe(wong n-ny, mya swigtype_p_fwoat x-x, OwO wongvectow xids, wongvectow pwecomputed_idx) {
    s-swigfaissjni.indexivfpq_add_cowe(swigcptw, (ˆ ﻌ ˆ)♡ this, ʘwʘ n-ny, swigtype_p_fwoat.getcptw(x), o.O swigtype_p_wong_wong.getcptw(xids.data()), UwU xids, swigtype_p_wong_wong.getcptw(pwecomputed_idx.data()), rawr x3 pwecomputed_idx);
  }

  p-pubwic void add_cowe_o(wong ny, 🥺 swigtype_p_fwoat x, :3 wongvectow xids, (ꈍᴗꈍ) swigtype_p_fwoat w-wesiduaws_2, 🥺 wongvectow pwecomputed_idx) {
    s-swigfaissjni.indexivfpq_add_cowe_o__swig_0(swigcptw, (✿oωo) t-this, n, (U ﹏ U) swigtype_p_fwoat.getcptw(x), swigtype_p_wong_wong.getcptw(xids.data()), :3 xids, ^^;; swigtype_p_fwoat.getcptw(wesiduaws_2), rawr swigtype_p_wong_wong.getcptw(pwecomputed_idx.data()), 😳😳😳 p-pwecomputed_idx);
  }

  p-pubwic void add_cowe_o(wong ny, (✿oωo) swigtype_p_fwoat x, OwO w-wongvectow xids, ʘwʘ swigtype_p_fwoat w-wesiduaws_2) {
    swigfaissjni.indexivfpq_add_cowe_o__swig_1(swigcptw, (ˆ ﻌ ˆ)♡ this, (U ﹏ U) ny, swigtype_p_fwoat.getcptw(x), UwU s-swigtype_p_wong_wong.getcptw(xids.data()), XD xids, s-swigtype_p_fwoat.getcptw(wesiduaws_2));
  }

  p-pubwic void twain_wesiduaw(wong ny, ʘwʘ swigtype_p_fwoat x-x) {
    swigfaissjni.indexivfpq_twain_wesiduaw(swigcptw, rawr x3 t-this, ^^;; ny, swigtype_p_fwoat.getcptw(x));
  }

  p-pubwic void twain_wesiduaw_o(wong n-ny, ʘwʘ swigtype_p_fwoat x, (U ﹏ U) swigtype_p_fwoat w-wesiduaws_2) {
    s-swigfaissjni.indexivfpq_twain_wesiduaw_o(swigcptw, (˘ω˘) this, ny, swigtype_p_fwoat.getcptw(x), (ꈍᴗꈍ) swigtype_p_fwoat.getcptw(wesiduaws_2));
  }

  p-pubwic void w-weconstwuct_fwom_offset(wong w-wist_no, /(^•ω•^) wong offset, swigtype_p_fwoat wecons) {
    s-swigfaissjni.indexivfpq_weconstwuct_fwom_offset(swigcptw, >_< this, σωσ wist_no, offset, ^^;; s-swigtype_p_fwoat.getcptw(wecons));
  }

  p-pubwic wong find_dupwicates(wongvectow ids, 😳 swigtype_p_unsigned_wong wims) {
    wetuwn swigfaissjni.indexivfpq_find_dupwicates(swigcptw, >_< t-this, -.- s-swigtype_p_wong_wong.getcptw(ids.data()), UwU i-ids, :3 swigtype_p_unsigned_wong.getcptw(wims));
  }

  pubwic v-void encode(wong key, σωσ swigtype_p_fwoat x-x, >w< swigtype_p_unsigned_chaw code) {
    swigfaissjni.indexivfpq_encode(swigcptw, (ˆ ﻌ ˆ)♡ this, key, ʘwʘ swigtype_p_fwoat.getcptw(x), s-swigtype_p_unsigned_chaw.getcptw(code));
  }

  pubwic void e-encode_muwtipwe(wong ny, :3 wongvectow k-keys, (˘ω˘) swigtype_p_fwoat x, s-swigtype_p_unsigned_chaw codes, 😳😳😳 b-boowean compute_keys) {
    s-swigfaissjni.indexivfpq_encode_muwtipwe__swig_0(swigcptw, rawr x3 t-this, (✿oωo) ny, s-swigtype_p_wong_wong.getcptw(keys.data()), (ˆ ﻌ ˆ)♡ k-keys, swigtype_p_fwoat.getcptw(x), :3 swigtype_p_unsigned_chaw.getcptw(codes), (U ᵕ U❁) compute_keys);
  }

  pubwic void encode_muwtipwe(wong n, ^^;; w-wongvectow keys, s-swigtype_p_fwoat x-x, mya swigtype_p_unsigned_chaw codes) {
    swigfaissjni.indexivfpq_encode_muwtipwe__swig_1(swigcptw, 😳😳😳 t-this, ny, OwO swigtype_p_wong_wong.getcptw(keys.data()), rawr keys, XD swigtype_p_fwoat.getcptw(x), (U ﹏ U) s-swigtype_p_unsigned_chaw.getcptw(codes));
  }

  pubwic v-void decode_muwtipwe(wong ny, (˘ω˘) wongvectow keys, UwU s-swigtype_p_unsigned_chaw xcodes, swigtype_p_fwoat x-x) {
    s-swigfaissjni.indexivfpq_decode_muwtipwe(swigcptw, this, >_< ny, swigtype_p_wong_wong.getcptw(keys.data()), σωσ k-keys, swigtype_p_unsigned_chaw.getcptw(xcodes), 🥺 s-swigtype_p_fwoat.getcptw(x));
  }

  pubwic swigtype_p_faiss__invewtedwistscannew get_invewtedwistscannew(boowean stowe_paiws) {
    w-wong c-cptw = swigfaissjni.indexivfpq_get_invewtedwistscannew(swigcptw, 🥺 t-this, stowe_paiws);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew swigtype_p_faiss__invewtedwistscannew(cptw, ʘwʘ fawse);
  }

  p-pubwic void p-pwecompute_tabwe() {
    swigfaissjni.indexivfpq_pwecompute_tabwe(swigcptw, :3 this);
  }

  p-pubwic i-indexivfpq() {
    this(swigfaissjni.new_indexivfpq__swig_2(), (U ﹏ U) t-twue);
  }

}
