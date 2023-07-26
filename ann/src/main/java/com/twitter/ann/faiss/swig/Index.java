/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). mya
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 🥺
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-index {
  pwivate twansient wong swigcptw;
  pwotected t-twansient boowean swigcmemown;

  p-pwotected index(wong cptw, ^^;; boowean cmemowyown) {
    swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static w-wong getcptw(index obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void d-dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_index(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic void setd(int v-vawue) {
    swigfaissjni.index_d_set(swigcptw, :3 this, vawue);
  }

  p-pubwic int getd() {
    wetuwn swigfaissjni.index_d_get(swigcptw, (U ﹏ U) this);
  }

  pubwic void setntotaw(wong v-vawue) {
    swigfaissjni.index_ntotaw_set(swigcptw, OwO t-this, 😳😳😳 v-vawue);
  }

  p-pubwic wong getntotaw() {
    wetuwn swigfaissjni.index_ntotaw_get(swigcptw, (ˆ ﻌ ˆ)♡ this);
}

  pubwic v-void setvewbose(boowean v-vawue) {
    swigfaissjni.index_vewbose_set(swigcptw, XD this, v-vawue);
  }

  p-pubwic boowean getvewbose() {
    w-wetuwn swigfaissjni.index_vewbose_get(swigcptw, (ˆ ﻌ ˆ)♡ this);
  }

  p-pubwic void setis_twained(boowean vawue) {
    s-swigfaissjni.index_is_twained_set(swigcptw, ( ͡o ω ͡o ) this, vawue);
  }

  p-pubwic boowean getis_twained() {
    w-wetuwn s-swigfaissjni.index_is_twained_get(swigcptw, rawr x3 this);
  }

  pubwic void setmetwic_type(metwictype vawue) {
    swigfaissjni.index_metwic_type_set(swigcptw, nyaa~~ this, vawue.swigvawue());
  }

  p-pubwic m-metwictype getmetwic_type() {
    wetuwn metwictype.swigtoenum(swigfaissjni.index_metwic_type_get(swigcptw, >_< t-this));
  }

  p-pubwic v-void setmetwic_awg(fwoat vawue) {
    swigfaissjni.index_metwic_awg_set(swigcptw, ^^;; this, vawue);
  }

  p-pubwic fwoat getmetwic_awg() {
    wetuwn swigfaissjni.index_metwic_awg_get(swigcptw, (ˆ ﻌ ˆ)♡ this);
  }

  pubwic v-void twain(wong ny, swigtype_p_fwoat x-x) {
    s-swigfaissjni.index_twain(swigcptw, ^^;; t-this, ny, swigtype_p_fwoat.getcptw(x));
  }

  p-pubwic void a-add(wong ny, (⑅˘꒳˘) swigtype_p_fwoat x-x) {
    swigfaissjni.index_add(swigcptw, rawr x3 t-this, (///ˬ///✿) ny, swigtype_p_fwoat.getcptw(x));
  }

  pubwic v-void add_with_ids(wong n-ny, 🥺 swigtype_p_fwoat x-x, >_< wongvectow x-xids) {
    s-swigfaissjni.index_add_with_ids(swigcptw, UwU this, >_< n, swigtype_p_fwoat.getcptw(x), -.- swigtype_p_wong_wong.getcptw(xids.data()), mya xids);
  }

  pubwic v-void seawch(wong ny, >w< swigtype_p_fwoat x, (U ﹏ U) wong k, 😳😳😳 swigtype_p_fwoat distances, o.O wongvectow wabews) {
    s-swigfaissjni.index_seawch(swigcptw, òωó this, ny, swigtype_p_fwoat.getcptw(x), 😳😳😳 k, swigtype_p_fwoat.getcptw(distances), σωσ swigtype_p_wong_wong.getcptw(wabews.data()), (⑅˘꒳˘) wabews);
  }

  p-pubwic v-void wange_seawch(wong n-ny, (///ˬ///✿) swigtype_p_fwoat x, 🥺 fwoat wadius, OwO w-wangeseawchwesuwt wesuwt) {
    s-swigfaissjni.index_wange_seawch(swigcptw, >w< t-this, ny, 🥺 swigtype_p_fwoat.getcptw(x), nyaa~~ wadius, ^^ wangeseawchwesuwt.getcptw(wesuwt), >w< wesuwt);
  }

  pubwic void assign(wong n-ny, OwO swigtype_p_fwoat x, XD wongvectow w-wabews, ^^;; wong k) {
    swigfaissjni.index_assign__swig_0(swigcptw, 🥺 t-this, XD ny, s-swigtype_p_fwoat.getcptw(x), (U ᵕ U❁) swigtype_p_wong_wong.getcptw(wabews.data()), :3 wabews, k-k);
  }

  p-pubwic void assign(wong ny, ( ͡o ω ͡o ) swigtype_p_fwoat x-x, òωó w-wongvectow wabews) {
    swigfaissjni.index_assign__swig_1(swigcptw, σωσ this, ny, swigtype_p_fwoat.getcptw(x), (U ᵕ U❁) swigtype_p_wong_wong.getcptw(wabews.data()), (✿oωo) wabews);
  }

  p-pubwic v-void weset() {
    s-swigfaissjni.index_weset(swigcptw, ^^ this);
  }

  p-pubwic wong w-wemove_ids(idsewectow sew) {
    w-wetuwn swigfaissjni.index_wemove_ids(swigcptw, ^•ﻌ•^ this, idsewectow.getcptw(sew), XD sew);
  }

  pubwic void weconstwuct(wong key, :3 swigtype_p_fwoat wecons) {
    s-swigfaissjni.index_weconstwuct(swigcptw, (ꈍᴗꈍ) t-this, key, swigtype_p_fwoat.getcptw(wecons));
  }

  pubwic v-void weconstwuct_n(wong i-i0, wong nyi, :3 swigtype_p_fwoat wecons) {
    swigfaissjni.index_weconstwuct_n(swigcptw, (U ﹏ U) t-this, i0, nyi, UwU swigtype_p_fwoat.getcptw(wecons));
  }

  pubwic void seawch_and_weconstwuct(wong ny, 😳😳😳 swigtype_p_fwoat x-x, XD wong k, swigtype_p_fwoat distances, o.O wongvectow w-wabews, (⑅˘꒳˘) s-swigtype_p_fwoat wecons) {
    swigfaissjni.index_seawch_and_weconstwuct(swigcptw, 😳😳😳 this, nyaa~~ ny, swigtype_p_fwoat.getcptw(x), rawr k-k, swigtype_p_fwoat.getcptw(distances), s-swigtype_p_wong_wong.getcptw(wabews.data()), -.- wabews, (✿oωo) swigtype_p_fwoat.getcptw(wecons));
  }

  pubwic void compute_wesiduaw(swigtype_p_fwoat x, /(^•ω•^) swigtype_p_fwoat w-wesiduaw, 🥺 wong key) {
    swigfaissjni.index_compute_wesiduaw(swigcptw, ʘwʘ t-this, UwU swigtype_p_fwoat.getcptw(x), XD swigtype_p_fwoat.getcptw(wesiduaw), (✿oωo) key);
  }

  p-pubwic void compute_wesiduaw_n(wong ny, :3 swigtype_p_fwoat x-xs, (///ˬ///✿) swigtype_p_fwoat wesiduaws, w-wongvectow keys) {
    s-swigfaissjni.index_compute_wesiduaw_n(swigcptw, nyaa~~ this, >w< ny, swigtype_p_fwoat.getcptw(xs), -.- s-swigtype_p_fwoat.getcptw(wesiduaws), (✿oωo) s-swigtype_p_wong_wong.getcptw(keys.data()), (˘ω˘) k-keys);
  }

  pubwic distancecomputew get_distance_computew() {
    w-wong c-cptw = swigfaissjni.index_get_distance_computew(swigcptw, this);
    wetuwn (cptw == 0) ? n-nyuww : n-nyew distancecomputew(cptw, rawr f-fawse);
  }

  pubwic wong sa_code_size() {
    wetuwn swigfaissjni.index_sa_code_size(swigcptw, OwO t-this);
  }

  pubwic void sa_encode(wong n-ny, ^•ﻌ•^ swigtype_p_fwoat x, s-swigtype_p_unsigned_chaw bytes) {
    swigfaissjni.index_sa_encode(swigcptw, UwU this, (˘ω˘) ny, swigtype_p_fwoat.getcptw(x), (///ˬ///✿) s-swigtype_p_unsigned_chaw.getcptw(bytes));
  }

  p-pubwic void s-sa_decode(wong n-ny, σωσ swigtype_p_unsigned_chaw bytes, /(^•ω•^) swigtype_p_fwoat x-x) {
    swigfaissjni.index_sa_decode(swigcptw, 😳 this, ny, swigtype_p_unsigned_chaw.getcptw(bytes), 😳 swigtype_p_fwoat.getcptw(x));
  }

  pubwic indexivf toivf() {
    w-wong cptw = swigfaissjni.index_toivf(swigcptw, (⑅˘꒳˘) t-this);
    wetuwn (cptw == 0) ? n-nyuww : nyew indexivf(cptw, 😳😳😳 f-fawse);
  }

}
