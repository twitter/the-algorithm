/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (U ﹏ U)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. mya
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexbinawyhnsw extends indexbinawy {
  pwivate t-twansient wong swigcptw;

  pwotected i-indexbinawyhnsw(wong cptw, ʘwʘ boowean cmemowyown) {
    supew(swigfaissjni.indexbinawyhnsw_swigupcast(cptw), (˘ω˘) c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(indexbinawyhnsw obj) {
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
        s-swigfaissjni.dewete_indexbinawyhnsw(swigcptw);
      }
      swigcptw = 0;
    }
    supew.dewete();
  }

  pubwic v-void sethnsw(hnsw vawue) {
    swigfaissjni.indexbinawyhnsw_hnsw_set(swigcptw, (U ﹏ U) t-this, ^•ﻌ•^ hnsw.getcptw(vawue), (˘ω˘) vawue);
  }

  pubwic hnsw gethnsw() {
    wong cptw = swigfaissjni.indexbinawyhnsw_hnsw_get(swigcptw, :3 this);
    w-wetuwn (cptw == 0) ? nyuww : nyew h-hnsw(cptw, ^^;; fawse);
  }

  p-pubwic v-void setown_fiewds(boowean vawue) {
    swigfaissjni.indexbinawyhnsw_own_fiewds_set(swigcptw, 🥺 this, vawue);
  }

  pubwic boowean g-getown_fiewds() {
    w-wetuwn swigfaissjni.indexbinawyhnsw_own_fiewds_get(swigcptw, (⑅˘꒳˘) t-this);
  }

  p-pubwic void setstowage(indexbinawy v-vawue) {
    swigfaissjni.indexbinawyhnsw_stowage_set(swigcptw, nyaa~~ t-this, :3 indexbinawy.getcptw(vawue), ( ͡o ω ͡o ) vawue);
  }

  p-pubwic indexbinawy getstowage() {
    w-wong cptw = swigfaissjni.indexbinawyhnsw_stowage_get(swigcptw, mya this);
    wetuwn (cptw == 0) ? n-nyuww : nyew indexbinawy(cptw, (///ˬ///✿) f-fawse);
  }

  pubwic indexbinawyhnsw() {
    this(swigfaissjni.new_indexbinawyhnsw__swig_0(), (˘ω˘) twue);
  }

  pubwic indexbinawyhnsw(int d, ^^;; int m) {
    this(swigfaissjni.new_indexbinawyhnsw__swig_1(d, (✿oωo) m-m), twue);
  }

  p-pubwic indexbinawyhnsw(int d-d) {
    this(swigfaissjni.new_indexbinawyhnsw__swig_2(d), (U ﹏ U) t-twue);
  }

  pubwic i-indexbinawyhnsw(indexbinawy stowage, -.- int m) {
    this(swigfaissjni.new_indexbinawyhnsw__swig_3(indexbinawy.getcptw(stowage), ^•ﻌ•^ stowage, m), rawr t-twue);
  }

  pubwic indexbinawyhnsw(indexbinawy stowage) {
    this(swigfaissjni.new_indexbinawyhnsw__swig_4(indexbinawy.getcptw(stowage), (˘ω˘) stowage), t-twue);
  }

  pubwic distancecomputew g-get_distance_computew() {
    w-wong c-cptw = swigfaissjni.indexbinawyhnsw_get_distance_computew(swigcptw, this);
    wetuwn (cptw == 0) ? n-nyuww : nyew d-distancecomputew(cptw, nyaa~~ f-fawse);
  }

  p-pubwic void add(wong ny, UwU swigtype_p_unsigned_chaw x-x) {
    s-swigfaissjni.indexbinawyhnsw_add(swigcptw, :3 t-this, n-ny, (⑅˘꒳˘) swigtype_p_unsigned_chaw.getcptw(x));
  }

  p-pubwic void twain(wong ny, (///ˬ///✿) swigtype_p_unsigned_chaw x) {
    swigfaissjni.indexbinawyhnsw_twain(swigcptw, ^^;; t-this, >_< ny, swigtype_p_unsigned_chaw.getcptw(x));
  }

  pubwic void seawch(wong ny, rawr x3 swigtype_p_unsigned_chaw x, /(^•ω•^) wong k-k, :3 swigtype_p_int distances, (ꈍᴗꈍ) wongvectow wabews) {
    swigfaissjni.indexbinawyhnsw_seawch(swigcptw, /(^•ω•^) t-this, n, (⑅˘꒳˘) swigtype_p_unsigned_chaw.getcptw(x), ( ͡o ω ͡o ) k-k, swigtype_p_int.getcptw(distances), s-swigtype_p_wong_wong.getcptw(wabews.data()), òωó wabews);
  }

  p-pubwic void weconstwuct(wong k-key, (⑅˘꒳˘) swigtype_p_unsigned_chaw w-wecons) {
    swigfaissjni.indexbinawyhnsw_weconstwuct(swigcptw, XD this, key, swigtype_p_unsigned_chaw.getcptw(wecons));
  }

  pubwic void weset() {
    swigfaissjni.indexbinawyhnsw_weset(swigcptw, -.- this);
  }

}
