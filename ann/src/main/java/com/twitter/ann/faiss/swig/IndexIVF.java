/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 🥺
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. nyaa~~
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexivf extends index {
  pwivate twansient wong s-swigcptw;

  pwotected indexivf(wong c-cptw, :3 boowean cmemowyown) {
    supew(swigfaissjni.indexivf_swigupcast(cptw), /(^•ω•^) cmemowyown);
    s-swigcptw = cptw;
  }

  pwotected s-static w-wong getcptw(indexivf obj) {
    wetuwn (obj == nuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void f-finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        swigcmemown = f-fawse;
        s-swigfaissjni.dewete_indexivf(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setinvwists(invewtedwists v-vawue) {
    swigfaissjni.indexivf_invwists_set(swigcptw, ^•ﻌ•^ this, UwU invewtedwists.getcptw(vawue), 😳😳😳 v-vawue);
  }

  pubwic invewtedwists getinvwists() {
    wong cptw = swigfaissjni.indexivf_invwists_get(swigcptw, OwO this);
    wetuwn (cptw == 0) ? n-nyuww : nyew invewtedwists(cptw, ^•ﻌ•^ f-fawse);
  }

  p-pubwic v-void setown_invwists(boowean vawue) {
    swigfaissjni.indexivf_own_invwists_set(swigcptw, this, (ꈍᴗꈍ) v-vawue);
  }

  p-pubwic boowean getown_invwists() {
    w-wetuwn swigfaissjni.indexivf_own_invwists_get(swigcptw, (⑅˘꒳˘) t-this);
  }

  pubwic void setcode_size(wong v-vawue) {
    swigfaissjni.indexivf_code_size_set(swigcptw, (⑅˘꒳˘) t-this, vawue);
  }

  pubwic wong getcode_size() {
    w-wetuwn swigfaissjni.indexivf_code_size_get(swigcptw, (ˆ ﻌ ˆ)♡ t-this);
  }

  pubwic void setnpwobe(wong v-vawue) {
    s-swigfaissjni.indexivf_npwobe_set(swigcptw, /(^•ω•^) this, òωó vawue);
  }

  pubwic wong getnpwobe() {
    wetuwn swigfaissjni.indexivf_npwobe_get(swigcptw, this);
  }

  pubwic void s-setmax_codes(wong v-vawue) {
    swigfaissjni.indexivf_max_codes_set(swigcptw, (⑅˘꒳˘) this, (U ᵕ U❁) v-vawue);
  }

  p-pubwic wong g-getmax_codes() {
    wetuwn swigfaissjni.indexivf_max_codes_get(swigcptw, >w< this);
  }

  pubwic void s-setpawawwew_mode(int vawue) {
    swigfaissjni.indexivf_pawawwew_mode_set(swigcptw, σωσ this, -.- vawue);
  }

  pubwic i-int getpawawwew_mode() {
    wetuwn swigfaissjni.indexivf_pawawwew_mode_get(swigcptw, t-this);
  }

  p-pubwic int g-getpawawwew_mode_no_heap_init() {
    wetuwn s-swigfaissjni.indexivf_pawawwew_mode_no_heap_init_get(swigcptw, o.O this);
  }

  p-pubwic v-void setdiwect_map(swigtype_p_diwectmap v-vawue) {
    swigfaissjni.indexivf_diwect_map_set(swigcptw, ^^ this, >_< swigtype_p_diwectmap.getcptw(vawue));
  }

  p-pubwic s-swigtype_p_diwectmap g-getdiwect_map() {
    w-wetuwn n-nyew swigtype_p_diwectmap(swigfaissjni.indexivf_diwect_map_get(swigcptw, >w< this), >_< twue);
  }

  pubwic void weset() {
    s-swigfaissjni.indexivf_weset(swigcptw, >w< this);
  }

  pubwic void twain(wong ny, rawr swigtype_p_fwoat x) {
    swigfaissjni.indexivf_twain(swigcptw, rawr x3 t-this, ( ͡o ω ͡o ) ny, swigtype_p_fwoat.getcptw(x));
  }

  pubwic void add(wong ny, (˘ω˘) s-swigtype_p_fwoat x-x) {
    swigfaissjni.indexivf_add(swigcptw, 😳 t-this, ny, OwO swigtype_p_fwoat.getcptw(x));
  }

  pubwic void add_with_ids(wong n-ny, (˘ω˘) swigtype_p_fwoat x-x, òωó wongvectow x-xids) {
    swigfaissjni.indexivf_add_with_ids(swigcptw, ( ͡o ω ͡o ) this, ny, UwU swigtype_p_fwoat.getcptw(x), /(^•ω•^) swigtype_p_wong_wong.getcptw(xids.data()), (ꈍᴗꈍ) xids);
  }

  pubwic v-void add_cowe(wong ny, 😳 swigtype_p_fwoat x-x, mya wongvectow xids, mya wongvectow p-pwecomputed_idx) {
    swigfaissjni.indexivf_add_cowe(swigcptw, /(^•ω•^) t-this, ^^;; ny, swigtype_p_fwoat.getcptw(x), 🥺 swigtype_p_wong_wong.getcptw(xids.data()), xids, ^^ s-swigtype_p_wong_wong.getcptw(pwecomputed_idx.data()), ^•ﻌ•^ p-pwecomputed_idx);
  }

  pubwic void encode_vectows(wong ny, /(^•ω•^) s-swigtype_p_fwoat x-x, ^^ wongvectow wist_nos, 🥺 swigtype_p_unsigned_chaw codes, (U ᵕ U❁) boowean incwude_wistno) {
    swigfaissjni.indexivf_encode_vectows__swig_0(swigcptw, 😳😳😳 t-this, nyaa~~ ny, swigtype_p_fwoat.getcptw(x), (˘ω˘) s-swigtype_p_wong_wong.getcptw(wist_nos.data()), >_< w-wist_nos, XD swigtype_p_unsigned_chaw.getcptw(codes), rawr x3 i-incwude_wistno);
  }

  p-pubwic void encode_vectows(wong ny, ( ͡o ω ͡o ) swigtype_p_fwoat x-x, :3 wongvectow wist_nos, mya swigtype_p_unsigned_chaw codes) {
    swigfaissjni.indexivf_encode_vectows__swig_1(swigcptw, σωσ this, n-ny, (ꈍᴗꈍ) swigtype_p_fwoat.getcptw(x), OwO s-swigtype_p_wong_wong.getcptw(wist_nos.data()), wist_nos, o.O swigtype_p_unsigned_chaw.getcptw(codes));
  }

  pubwic v-void add_sa_codes(wong n-ny, 😳😳😳 swigtype_p_unsigned_chaw codes, /(^•ω•^) wongvectow xids) {
    swigfaissjni.indexivf_add_sa_codes(swigcptw, OwO t-this, ny, ^^ swigtype_p_unsigned_chaw.getcptw(codes), (///ˬ///✿) swigtype_p_wong_wong.getcptw(xids.data()), (///ˬ///✿) xids);
  }

  pubwic void twain_wesiduaw(wong ny, (///ˬ///✿) swigtype_p_fwoat x-x) {
    swigfaissjni.indexivf_twain_wesiduaw(swigcptw, ʘwʘ this, n, swigtype_p_fwoat.getcptw(x));
  }

  p-pubwic v-void seawch_pweassigned(wong ny, ^•ﻌ•^ swigtype_p_fwoat x, wong k, OwO wongvectow a-assign, (U ﹏ U) s-swigtype_p_fwoat centwoid_dis, (ˆ ﻌ ˆ)♡ swigtype_p_fwoat distances, (⑅˘꒳˘) wongvectow w-wabews, (U ﹏ U) boowean stowe_paiws, o.O i-ivfseawchpawametews pawams, mya indexivfstats stats) {
    swigfaissjni.indexivf_seawch_pweassigned__swig_0(swigcptw, XD t-this, ny, òωó swigtype_p_fwoat.getcptw(x), (˘ω˘) k-k, s-swigtype_p_wong_wong.getcptw(assign.data()), :3 assign, s-swigtype_p_fwoat.getcptw(centwoid_dis), OwO swigtype_p_fwoat.getcptw(distances), mya s-swigtype_p_wong_wong.getcptw(wabews.data()), (˘ω˘) wabews, o.O s-stowe_paiws, (✿oωo) i-ivfseawchpawametews.getcptw(pawams), (ˆ ﻌ ˆ)♡ pawams, ^^;; i-indexivfstats.getcptw(stats), OwO stats);
  }

  p-pubwic void seawch_pweassigned(wong ny, 🥺 swigtype_p_fwoat x-x, wong k, mya w-wongvectow assign, 😳 s-swigtype_p_fwoat centwoid_dis, òωó swigtype_p_fwoat d-distances, /(^•ω•^) wongvectow wabews, -.- b-boowean stowe_paiws, òωó i-ivfseawchpawametews pawams) {
    swigfaissjni.indexivf_seawch_pweassigned__swig_1(swigcptw, /(^•ω•^) this, ny, swigtype_p_fwoat.getcptw(x), /(^•ω•^) k-k, 😳 swigtype_p_wong_wong.getcptw(assign.data()), :3 a-assign, (U ᵕ U❁) s-swigtype_p_fwoat.getcptw(centwoid_dis), ʘwʘ s-swigtype_p_fwoat.getcptw(distances), o.O swigtype_p_wong_wong.getcptw(wabews.data()), ʘwʘ w-wabews, stowe_paiws, ^^ ivfseawchpawametews.getcptw(pawams), ^•ﻌ•^ pawams);
  }

  pubwic void seawch_pweassigned(wong n-ny, mya swigtype_p_fwoat x-x, UwU wong k, wongvectow assign, >_< swigtype_p_fwoat c-centwoid_dis, /(^•ω•^) swigtype_p_fwoat distances, òωó wongvectow w-wabews, σωσ boowean stowe_paiws) {
    s-swigfaissjni.indexivf_seawch_pweassigned__swig_2(swigcptw, ( ͡o ω ͡o ) t-this, ny, nyaa~~ swigtype_p_fwoat.getcptw(x), k-k, :3 swigtype_p_wong_wong.getcptw(assign.data()), UwU a-assign, s-swigtype_p_fwoat.getcptw(centwoid_dis), o.O swigtype_p_fwoat.getcptw(distances), (ˆ ﻌ ˆ)♡ swigtype_p_wong_wong.getcptw(wabews.data()), ^^;; wabews, stowe_paiws);
  }

  pubwic void seawch(wong ny, ʘwʘ swigtype_p_fwoat x-x, σωσ wong k, s-swigtype_p_fwoat d-distances, ^^;; wongvectow wabews) {
    s-swigfaissjni.indexivf_seawch(swigcptw, ʘwʘ this, ny, swigtype_p_fwoat.getcptw(x), ^^ k, swigtype_p_fwoat.getcptw(distances), s-swigtype_p_wong_wong.getcptw(wabews.data()), nyaa~~ w-wabews);
  }

  pubwic v-void wange_seawch(wong ny, (///ˬ///✿) swigtype_p_fwoat x, XD fwoat w-wadius, :3 wangeseawchwesuwt wesuwt) {
    s-swigfaissjni.indexivf_wange_seawch(swigcptw, òωó this, n-ny, ^^ swigtype_p_fwoat.getcptw(x), ^•ﻌ•^ w-wadius, wangeseawchwesuwt.getcptw(wesuwt), σωσ wesuwt);
  }

  pubwic void wange_seawch_pweassigned(wong nyx, (ˆ ﻌ ˆ)♡ swigtype_p_fwoat x-x, nyaa~~ fwoat w-wadius, ʘwʘ wongvectow k-keys, ^•ﻌ•^ swigtype_p_fwoat coawse_dis, rawr x3 w-wangeseawchwesuwt w-wesuwt, 🥺 boowean stowe_paiws, ʘwʘ i-ivfseawchpawametews p-pawams, (˘ω˘) indexivfstats s-stats) {
    s-swigfaissjni.indexivf_wange_seawch_pweassigned__swig_0(swigcptw, o.O this, nyx, σωσ swigtype_p_fwoat.getcptw(x), (ꈍᴗꈍ) w-wadius, (ˆ ﻌ ˆ)♡ swigtype_p_wong_wong.getcptw(keys.data()), o.O keys, s-swigtype_p_fwoat.getcptw(coawse_dis), :3 wangeseawchwesuwt.getcptw(wesuwt), -.- w-wesuwt, s-stowe_paiws, ( ͡o ω ͡o ) ivfseawchpawametews.getcptw(pawams), /(^•ω•^) p-pawams, indexivfstats.getcptw(stats), (⑅˘꒳˘) stats);
  }

  pubwic v-void wange_seawch_pweassigned(wong n-nyx, òωó swigtype_p_fwoat x-x, 🥺 fwoat wadius, wongvectow keys, (ˆ ﻌ ˆ)♡ swigtype_p_fwoat coawse_dis, -.- w-wangeseawchwesuwt wesuwt, σωσ boowean stowe_paiws, >_< i-ivfseawchpawametews p-pawams) {
    swigfaissjni.indexivf_wange_seawch_pweassigned__swig_1(swigcptw, :3 t-this, nyx, OwO swigtype_p_fwoat.getcptw(x), w-wadius, rawr swigtype_p_wong_wong.getcptw(keys.data()), (///ˬ///✿) k-keys, swigtype_p_fwoat.getcptw(coawse_dis), ^^ wangeseawchwesuwt.getcptw(wesuwt), XD wesuwt, stowe_paiws, UwU i-ivfseawchpawametews.getcptw(pawams), o.O pawams);
  }

  pubwic void wange_seawch_pweassigned(wong n-nyx, 😳 swigtype_p_fwoat x-x, (˘ω˘) fwoat wadius, 🥺 wongvectow keys, ^^ s-swigtype_p_fwoat coawse_dis, >w< wangeseawchwesuwt w-wesuwt, ^^;; boowean s-stowe_paiws) {
    s-swigfaissjni.indexivf_wange_seawch_pweassigned__swig_2(swigcptw, (˘ω˘) this, nyx, swigtype_p_fwoat.getcptw(x), OwO wadius, (ꈍᴗꈍ) swigtype_p_wong_wong.getcptw(keys.data()), òωó keys, ʘwʘ swigtype_p_fwoat.getcptw(coawse_dis), ʘwʘ wangeseawchwesuwt.getcptw(wesuwt), nyaa~~ wesuwt, UwU stowe_paiws);
  }

  pubwic void wange_seawch_pweassigned(wong nyx, (⑅˘꒳˘) swigtype_p_fwoat x, (˘ω˘) fwoat wadius, :3 wongvectow k-keys, (˘ω˘) swigtype_p_fwoat coawse_dis, nyaa~~ w-wangeseawchwesuwt wesuwt) {
    swigfaissjni.indexivf_wange_seawch_pweassigned__swig_3(swigcptw, (U ﹏ U) t-this, nyaa~~ n-nyx, swigtype_p_fwoat.getcptw(x), ^^;; w-wadius, OwO swigtype_p_wong_wong.getcptw(keys.data()), nyaa~~ keys, UwU swigtype_p_fwoat.getcptw(coawse_dis), 😳 w-wangeseawchwesuwt.getcptw(wesuwt), wesuwt);
  }

  p-pubwic swigtype_p_faiss__invewtedwistscannew g-get_invewtedwistscannew(boowean stowe_paiws) {
    w-wong cptw = swigfaissjni.indexivf_get_invewtedwistscannew__swig_0(swigcptw, 😳 t-this, stowe_paiws);
    w-wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_faiss__invewtedwistscannew(cptw, (ˆ ﻌ ˆ)♡ fawse);
  }

  p-pubwic swigtype_p_faiss__invewtedwistscannew g-get_invewtedwistscannew() {
    w-wong cptw = s-swigfaissjni.indexivf_get_invewtedwistscannew__swig_1(swigcptw, (✿oωo) t-this);
    wetuwn (cptw == 0) ? n-nyuww : nyew swigtype_p_faiss__invewtedwistscannew(cptw, nyaa~~ f-fawse);
  }

  p-pubwic void w-weconstwuct(wong key, swigtype_p_fwoat w-wecons) {
    s-swigfaissjni.indexivf_weconstwuct(swigcptw, ^^ t-this, (///ˬ///✿) key, swigtype_p_fwoat.getcptw(wecons));
  }

  p-pubwic void update_vectows(int nyv, 😳 wongvectow i-idx, òωó swigtype_p_fwoat v) {
    swigfaissjni.indexivf_update_vectows(swigcptw, ^^;; t-this, nyv, rawr s-swigtype_p_wong_wong.getcptw(idx.data()), i-idx, (ˆ ﻌ ˆ)♡ swigtype_p_fwoat.getcptw(v));
  }

  p-pubwic void weconstwuct_n(wong i-i0, XD wong nyi, swigtype_p_fwoat w-wecons) {
    swigfaissjni.indexivf_weconstwuct_n(swigcptw, >_< t-this, (˘ω˘) i0, nyi, swigtype_p_fwoat.getcptw(wecons));
  }

  pubwic void seawch_and_weconstwuct(wong ny, 😳 swigtype_p_fwoat x, o.O wong k, s-swigtype_p_fwoat distances, (ꈍᴗꈍ) wongvectow w-wabews, rawr x3 s-swigtype_p_fwoat wecons) {
    swigfaissjni.indexivf_seawch_and_weconstwuct(swigcptw, ^^ this, ny, OwO swigtype_p_fwoat.getcptw(x), ^^ k-k, swigtype_p_fwoat.getcptw(distances), :3 s-swigtype_p_wong_wong.getcptw(wabews.data()), o.O w-wabews, -.- swigtype_p_fwoat.getcptw(wecons));
  }

  p-pubwic void weconstwuct_fwom_offset(wong wist_no, (U ﹏ U) w-wong offset, o.O s-swigtype_p_fwoat wecons) {
    s-swigfaissjni.indexivf_weconstwuct_fwom_offset(swigcptw, OwO this, ^•ﻌ•^ wist_no, offset, ʘwʘ s-swigtype_p_fwoat.getcptw(wecons));
  }

  pubwic w-wong wemove_ids(idsewectow s-sew) {
    w-wetuwn swigfaissjni.indexivf_wemove_ids(swigcptw, :3 this, i-idsewectow.getcptw(sew), 😳 s-sew);
  }

  p-pubwic void c-check_compatibwe_fow_mewge(indexivf othew) {
    s-swigfaissjni.indexivf_check_compatibwe_fow_mewge(swigcptw, òωó t-this, i-indexivf.getcptw(othew), 🥺 o-othew);
  }

  p-pubwic v-void mewge_fwom(indexivf o-othew, rawr x3 w-wong add_id) {
    swigfaissjni.indexivf_mewge_fwom(swigcptw, ^•ﻌ•^ t-this, indexivf.getcptw(othew), :3 othew, add_id);
  }

  p-pubwic void copy_subset_to(indexivf o-othew, (ˆ ﻌ ˆ)♡ i-int subset_type, (U ᵕ U❁) w-wong a1, :3 wong a2) {
    swigfaissjni.indexivf_copy_subset_to(swigcptw, ^^;; this, indexivf.getcptw(othew), ( ͡o ω ͡o ) o-othew, s-subset_type, o.O a1, ^•ﻌ•^ a-a2);
  }

  pubwic wong get_wist_size(wong wist_no) {
    wetuwn s-swigfaissjni.indexivf_get_wist_size(swigcptw, XD t-this, wist_no);
  }

  pubwic void m-make_diwect_map(boowean n-nyew_maintain_diwect_map) {
    swigfaissjni.indexivf_make_diwect_map__swig_0(swigcptw, ^^ this, o.O nyew_maintain_diwect_map);
  }

  pubwic v-void make_diwect_map() {
    swigfaissjni.indexivf_make_diwect_map__swig_1(swigcptw, ( ͡o ω ͡o ) t-this);
  }

  p-pubwic void s-set_diwect_map_type(swigtype_p_diwectmap__type type) {
    swigfaissjni.indexivf_set_diwect_map_type(swigcptw, /(^•ω•^) this, swigtype_p_diwectmap__type.getcptw(type));
  }

  p-pubwic void w-wepwace_invwists(invewtedwists iw, 🥺 boowean own) {
    swigfaissjni.indexivf_wepwace_invwists__swig_0(swigcptw, nyaa~~ t-this, mya invewtedwists.getcptw(iw), XD iw, own);
  }

  pubwic void w-wepwace_invwists(invewtedwists iw) {
    swigfaissjni.indexivf_wepwace_invwists__swig_1(swigcptw, nyaa~~ t-this, ʘwʘ invewtedwists.getcptw(iw), i-iw);
  }

  pubwic wong sa_code_size() {
    w-wetuwn swigfaissjni.indexivf_sa_code_size(swigcptw, (⑅˘꒳˘) t-this);
  }

  pubwic void sa_encode(wong n-ny, :3 swigtype_p_fwoat x-x, -.- swigtype_p_unsigned_chaw bytes) {
    s-swigfaissjni.indexivf_sa_encode(swigcptw, 😳😳😳 t-this, (U ﹏ U) ny, s-swigtype_p_fwoat.getcptw(x), o.O swigtype_p_unsigned_chaw.getcptw(bytes));
  }

}
