/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). >w<
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 😳😳😳
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexbinawy {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected indexbinawy(wong cptw, OwO boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = c-cptw;
  }

  pwotected static wong getcptw(indexbinawy obj) {
    wetuwn (obj == n-nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  p-pubwic synchwonized void d-dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = fawse;
        swigfaissjni.dewete_indexbinawy(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic void setd(int vawue) {
    swigfaissjni.indexbinawy_d_set(swigcptw, 😳 t-this, 😳😳😳 vawue);
  }

  pubwic int getd() {
    wetuwn swigfaissjni.indexbinawy_d_get(swigcptw, (˘ω˘) this);
  }

  pubwic void setcode_size(int v-vawue) {
    swigfaissjni.indexbinawy_code_size_set(swigcptw, ʘwʘ t-this, ( ͡o ω ͡o ) v-vawue);
  }

  p-pubwic int getcode_size() {
    wetuwn swigfaissjni.indexbinawy_code_size_get(swigcptw, o.O this);
  }

  pubwic void s-setntotaw(wong v-vawue) {
    swigfaissjni.indexbinawy_ntotaw_set(swigcptw, >w< this, v-vawue);
  }

  p-pubwic wong getntotaw() {
    wetuwn swigfaissjni.indexbinawy_ntotaw_get(swigcptw, t-this);
}

  pubwic void setvewbose(boowean vawue) {
    s-swigfaissjni.indexbinawy_vewbose_set(swigcptw, 😳 this, 🥺 vawue);
  }

  p-pubwic boowean getvewbose() {
    wetuwn swigfaissjni.indexbinawy_vewbose_get(swigcptw, rawr x3 t-this);
  }

  pubwic void s-setis_twained(boowean v-vawue) {
    swigfaissjni.indexbinawy_is_twained_set(swigcptw, o.O this, rawr vawue);
  }

  pubwic boowean getis_twained() {
    wetuwn swigfaissjni.indexbinawy_is_twained_get(swigcptw, ʘwʘ this);
  }

  p-pubwic void s-setmetwic_type(metwictype vawue) {
    s-swigfaissjni.indexbinawy_metwic_type_set(swigcptw, 😳😳😳 t-this, ^^;; v-vawue.swigvawue());
  }

  pubwic metwictype getmetwic_type() {
    wetuwn metwictype.swigtoenum(swigfaissjni.indexbinawy_metwic_type_get(swigcptw, o.O t-this));
  }

  pubwic void twain(wong ny, (///ˬ///✿) swigtype_p_unsigned_chaw x) {
    s-swigfaissjni.indexbinawy_twain(swigcptw, σωσ this, n-ny, swigtype_p_unsigned_chaw.getcptw(x));
  }

  p-pubwic void a-add(wong ny, nyaa~~ swigtype_p_unsigned_chaw x) {
    swigfaissjni.indexbinawy_add(swigcptw, ^^;; t-this, ny, s-swigtype_p_unsigned_chaw.getcptw(x));
  }

  p-pubwic v-void add_with_ids(wong ny, ^•ﻌ•^ swigtype_p_unsigned_chaw x, σωσ wongvectow x-xids) {
    s-swigfaissjni.indexbinawy_add_with_ids(swigcptw, -.- t-this, ^^;; ny, swigtype_p_unsigned_chaw.getcptw(x), XD s-swigtype_p_wong_wong.getcptw(xids.data()), 🥺 x-xids);
  }

  pubwic void seawch(wong ny, òωó swigtype_p_unsigned_chaw x, w-wong k, (ˆ ﻌ ˆ)♡ swigtype_p_int distances, -.- wongvectow wabews) {
    swigfaissjni.indexbinawy_seawch(swigcptw, :3 this, ny, ʘwʘ swigtype_p_unsigned_chaw.getcptw(x), 🥺 k-k, swigtype_p_int.getcptw(distances), >_< swigtype_p_wong_wong.getcptw(wabews.data()), ʘwʘ wabews);
  }

  pubwic v-void wange_seawch(wong n-ny, (˘ω˘) swigtype_p_unsigned_chaw x-x, (✿oωo) int wadius, (///ˬ///✿) wangeseawchwesuwt w-wesuwt) {
    swigfaissjni.indexbinawy_wange_seawch(swigcptw, rawr x3 t-this, -.- ny, swigtype_p_unsigned_chaw.getcptw(x), ^^ w-wadius, (⑅˘꒳˘) wangeseawchwesuwt.getcptw(wesuwt), nyaa~~ wesuwt);
  }

  pubwic void assign(wong ny, /(^•ω•^) swigtype_p_unsigned_chaw x, (U ﹏ U) wongvectow w-wabews, 😳😳😳 wong k) {
    swigfaissjni.indexbinawy_assign__swig_0(swigcptw, >w< t-this, ny, XD swigtype_p_unsigned_chaw.getcptw(x), o.O s-swigtype_p_wong_wong.getcptw(wabews.data()), mya w-wabews, 🥺 k);
  }

  pubwic void assign(wong ny, ^^;; s-swigtype_p_unsigned_chaw x-x, :3 wongvectow wabews) {
    s-swigfaissjni.indexbinawy_assign__swig_1(swigcptw, (U ﹏ U) t-this, ny, OwO swigtype_p_unsigned_chaw.getcptw(x), 😳😳😳 swigtype_p_wong_wong.getcptw(wabews.data()), (ˆ ﻌ ˆ)♡ wabews);
  }

  pubwic void w-weset() {
    s-swigfaissjni.indexbinawy_weset(swigcptw, XD t-this);
  }

  pubwic wong w-wemove_ids(idsewectow s-sew) {
    wetuwn swigfaissjni.indexbinawy_wemove_ids(swigcptw, (ˆ ﻌ ˆ)♡ t-this, idsewectow.getcptw(sew), ( ͡o ω ͡o ) sew);
  }

  pubwic void weconstwuct(wong key, rawr x3 swigtype_p_unsigned_chaw w-wecons) {
    swigfaissjni.indexbinawy_weconstwuct(swigcptw, nyaa~~ t-this, >_< key, swigtype_p_unsigned_chaw.getcptw(wecons));
  }

  pubwic v-void weconstwuct_n(wong i-i0, ^^;; wong nyi, (ˆ ﻌ ˆ)♡ swigtype_p_unsigned_chaw wecons) {
    swigfaissjni.indexbinawy_weconstwuct_n(swigcptw, ^^;; this, i0, nyi, swigtype_p_unsigned_chaw.getcptw(wecons));
  }

  p-pubwic void seawch_and_weconstwuct(wong ny, swigtype_p_unsigned_chaw x, (⑅˘꒳˘) wong k, rawr x3 swigtype_p_int distances, (///ˬ///✿) wongvectow w-wabews, 🥺 swigtype_p_unsigned_chaw wecons) {
    swigfaissjni.indexbinawy_seawch_and_weconstwuct(swigcptw, >_< t-this, n-ny, UwU swigtype_p_unsigned_chaw.getcptw(x), >_< k, swigtype_p_int.getcptw(distances), -.- swigtype_p_wong_wong.getcptw(wabews.data()), mya w-wabews, >w< swigtype_p_unsigned_chaw.getcptw(wecons));
  }

  p-pubwic void dispway() {
    swigfaissjni.indexbinawy_dispway(swigcptw, (U ﹏ U) this);
  }

}
