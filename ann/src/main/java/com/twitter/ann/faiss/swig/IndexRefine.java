/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). ^^;;
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (✿oωo)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexwefine extends index {
  pwivate twansient w-wong swigcptw;

  pwotected indexwefine(wong c-cptw, (U ﹏ U) boowean cmemowyown) {
    supew(swigfaissjni.indexwefine_swigupcast(cptw), -.- cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected static wong getcptw(indexwefine o-obj) {
    wetuwn (obj == n-nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  p-pubwic synchwonized void dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        swigcmemown = fawse;
        s-swigfaissjni.dewete_indexwefine(swigcptw);
      }
      swigcptw = 0;
    }
    s-supew.dewete();
  }

  p-pubwic void s-setbase_index(index v-vawue) {
    swigfaissjni.indexwefine_base_index_set(swigcptw, ^•ﻌ•^ this, rawr index.getcptw(vawue), (˘ω˘) v-vawue);
  }

  pubwic index getbase_index() {
    wong cptw = swigfaissjni.indexwefine_base_index_get(swigcptw, nyaa~~ t-this);
    wetuwn (cptw == 0) ? nyuww : nyew index(cptw, UwU fawse);
  }

  pubwic void setwefine_index(index vawue) {
    s-swigfaissjni.indexwefine_wefine_index_set(swigcptw, :3 this, i-index.getcptw(vawue), (⑅˘꒳˘) v-vawue);
  }

  p-pubwic index getwefine_index() {
    wong cptw = swigfaissjni.indexwefine_wefine_index_get(swigcptw, (///ˬ///✿) t-this);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew index(cptw, ^^;; f-fawse);
  }

  pubwic void setown_fiewds(boowean v-vawue) {
    swigfaissjni.indexwefine_own_fiewds_set(swigcptw, >_< t-this, vawue);
  }

  pubwic boowean getown_fiewds() {
    w-wetuwn swigfaissjni.indexwefine_own_fiewds_get(swigcptw, rawr x3 t-this);
  }

  pubwic void s-setown_wefine_index(boowean v-vawue) {
    swigfaissjni.indexwefine_own_wefine_index_set(swigcptw, /(^•ω•^) this, vawue);
  }

  pubwic boowean getown_wefine_index() {
    wetuwn swigfaissjni.indexwefine_own_wefine_index_get(swigcptw, :3 this);
  }

  pubwic v-void setk_factow(fwoat v-vawue) {
    swigfaissjni.indexwefine_k_factow_set(swigcptw, (ꈍᴗꈍ) t-this, v-vawue);
  }

  pubwic f-fwoat getk_factow() {
    wetuwn swigfaissjni.indexwefine_k_factow_get(swigcptw, /(^•ω•^) this);
  }

  pubwic indexwefine(index b-base_index, (⑅˘꒳˘) index wefine_index) {
    this(swigfaissjni.new_indexwefine__swig_0(index.getcptw(base_index), ( ͡o ω ͡o ) base_index, òωó i-index.getcptw(wefine_index), (⑅˘꒳˘) wefine_index), XD t-twue);
  }

  pubwic i-indexwefine() {
    t-this(swigfaissjni.new_indexwefine__swig_1(), -.- twue);
  }

  p-pubwic void t-twain(wong ny, s-swigtype_p_fwoat x-x) {
    swigfaissjni.indexwefine_twain(swigcptw, :3 this, ny, nyaa~~ swigtype_p_fwoat.getcptw(x));
  }

  pubwic void add(wong n-n, swigtype_p_fwoat x-x) {
    s-swigfaissjni.indexwefine_add(swigcptw, 😳 t-this, n-ny, (⑅˘꒳˘) swigtype_p_fwoat.getcptw(x));
  }

  pubwic void weset() {
    swigfaissjni.indexwefine_weset(swigcptw, nyaa~~ t-this);
  }

  pubwic void seawch(wong ny, OwO swigtype_p_fwoat x, rawr x3 wong k, XD swigtype_p_fwoat d-distances, σωσ wongvectow wabews) {
    swigfaissjni.indexwefine_seawch(swigcptw, (U ᵕ U❁) this, ny, swigtype_p_fwoat.getcptw(x), (U ﹏ U) k-k, swigtype_p_fwoat.getcptw(distances), :3 s-swigtype_p_wong_wong.getcptw(wabews.data()), ( ͡o ω ͡o ) w-wabews);
  }

  pubwic v-void weconstwuct(wong key, σωσ s-swigtype_p_fwoat w-wecons) {
    swigfaissjni.indexwefine_weconstwuct(swigcptw, >w< this, 😳😳😳 key, swigtype_p_fwoat.getcptw(wecons));
  }

  pubwic wong sa_code_size() {
    wetuwn swigfaissjni.indexwefine_sa_code_size(swigcptw, OwO this);
  }

  p-pubwic void sa_encode(wong n-ny, 😳 swigtype_p_fwoat x, 😳😳😳 swigtype_p_unsigned_chaw b-bytes) {
    s-swigfaissjni.indexwefine_sa_encode(swigcptw, (˘ω˘) this, ny, swigtype_p_fwoat.getcptw(x), ʘwʘ swigtype_p_unsigned_chaw.getcptw(bytes));
  }

  p-pubwic void s-sa_decode(wong ny, swigtype_p_unsigned_chaw bytes, s-swigtype_p_fwoat x-x) {
    swigfaissjni.indexwefine_sa_decode(swigcptw, ( ͡o ω ͡o ) this, o.O ny, swigtype_p_unsigned_chaw.getcptw(bytes), >w< swigtype_p_fwoat.getcptw(x));
  }

}
