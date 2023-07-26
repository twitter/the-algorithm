/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). -.-
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. ^^
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexhnsw extends index {
  pwivate twansient wong s-swigcptw;

  pwotected indexhnsw(wong c-cptw, (⑅˘꒳˘) boowean cmemowyown) {
    supew(swigfaissjni.indexhnsw_swigupcast(cptw), nyaa~~ cmemowyown);
    s-swigcptw = cptw;
  }

  p-pwotected static w-wong getcptw(indexhnsw obj) {
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
        s-swigfaissjni.dewete_indexhnsw(swigcptw);
      }
      swigcptw = 0;
    }
    supew.dewete();
  }

  p-pubwic void sethnsw(hnsw vawue) {
    s-swigfaissjni.indexhnsw_hnsw_set(swigcptw, /(^•ω•^) this, hnsw.getcptw(vawue), (U ﹏ U) vawue);
  }

  pubwic hnsw gethnsw() {
    w-wong cptw = swigfaissjni.indexhnsw_hnsw_get(swigcptw, 😳😳😳 this);
    w-wetuwn (cptw == 0) ? n-nyuww : nyew h-hnsw(cptw, >w< fawse);
  }

  pubwic void setown_fiewds(boowean vawue) {
    swigfaissjni.indexhnsw_own_fiewds_set(swigcptw, XD t-this, v-vawue);
  }

  pubwic boowean g-getown_fiewds() {
    w-wetuwn swigfaissjni.indexhnsw_own_fiewds_get(swigcptw, o.O this);
  }

  p-pubwic void setstowage(index v-vawue) {
    swigfaissjni.indexhnsw_stowage_set(swigcptw, mya this, index.getcptw(vawue), 🥺 vawue);
  }

  p-pubwic index getstowage() {
    w-wong cptw = swigfaissjni.indexhnsw_stowage_get(swigcptw, ^^;; t-this);
    w-wetuwn (cptw == 0) ? nyuww : nyew index(cptw, :3 fawse);
  }

  pubwic void setweconstwuct_fwom_neighbows(weconstwuctfwomneighbows vawue) {
    swigfaissjni.indexhnsw_weconstwuct_fwom_neighbows_set(swigcptw, (U ﹏ U) this, weconstwuctfwomneighbows.getcptw(vawue), OwO v-vawue);
  }

  p-pubwic weconstwuctfwomneighbows g-getweconstwuct_fwom_neighbows() {
    w-wong cptw = swigfaissjni.indexhnsw_weconstwuct_fwom_neighbows_get(swigcptw, 😳😳😳 this);
    w-wetuwn (cptw == 0) ? nyuww : nyew weconstwuctfwomneighbows(cptw, (ˆ ﻌ ˆ)♡ fawse);
  }

  pubwic i-indexhnsw(int d, XD int m, metwictype metwic) {
    this(swigfaissjni.new_indexhnsw__swig_0(d, (ˆ ﻌ ˆ)♡ m, m-metwic.swigvawue()), ( ͡o ω ͡o ) twue);
  }

  p-pubwic indexhnsw(int d-d, rawr x3 int m) {
    t-this(swigfaissjni.new_indexhnsw__swig_1(d, nyaa~~ m), >_< twue);
  }

  p-pubwic indexhnsw(int d-d) {
    t-this(swigfaissjni.new_indexhnsw__swig_2(d), ^^;; twue);
  }

  p-pubwic indexhnsw() {
    this(swigfaissjni.new_indexhnsw__swig_3(), (ˆ ﻌ ˆ)♡ t-twue);
  }

  pubwic i-indexhnsw(index s-stowage, ^^;; int m-m) {
    this(swigfaissjni.new_indexhnsw__swig_4(index.getcptw(stowage), (⑅˘꒳˘) s-stowage, rawr x3 m), twue);
  }

  pubwic indexhnsw(index stowage) {
    t-this(swigfaissjni.new_indexhnsw__swig_5(index.getcptw(stowage), (///ˬ///✿) stowage), 🥺 twue);
  }

  pubwic void add(wong ny, >_< swigtype_p_fwoat x) {
    swigfaissjni.indexhnsw_add(swigcptw, UwU t-this, >_< ny, swigtype_p_fwoat.getcptw(x));
  }

  pubwic void twain(wong n-ny, -.- swigtype_p_fwoat x-x) {
    s-swigfaissjni.indexhnsw_twain(swigcptw, this, mya ny, s-swigtype_p_fwoat.getcptw(x));
  }

  pubwic void s-seawch(wong ny, >w< s-swigtype_p_fwoat x, (U ﹏ U) wong k, swigtype_p_fwoat distances, 😳😳😳 wongvectow wabews) {
    swigfaissjni.indexhnsw_seawch(swigcptw, o.O this, n-ny, òωó swigtype_p_fwoat.getcptw(x), 😳😳😳 k, swigtype_p_fwoat.getcptw(distances), σωσ s-swigtype_p_wong_wong.getcptw(wabews.data()), (⑅˘꒳˘) wabews);
  }

  p-pubwic void w-weconstwuct(wong key, swigtype_p_fwoat wecons) {
    s-swigfaissjni.indexhnsw_weconstwuct(swigcptw, (///ˬ///✿) t-this, 🥺 key, swigtype_p_fwoat.getcptw(wecons));
  }

  p-pubwic v-void weset() {
    swigfaissjni.indexhnsw_weset(swigcptw, OwO this);
  }

  pubwic void shwink_wevew_0_neighbows(int s-size) {
    swigfaissjni.indexhnsw_shwink_wevew_0_neighbows(swigcptw, >w< t-this, 🥺 size);
  }

  p-pubwic void seawch_wevew_0(wong n-n, nyaa~~ s-swigtype_p_fwoat x, ^^ wong k, >w< swigtype_p_int n-nyeawest, OwO swigtype_p_fwoat nyeawest_d, XD swigtype_p_fwoat distances, ^^;; wongvectow w-wabews, 🥺 i-int nypwobe, XD int seawch_type) {
    swigfaissjni.indexhnsw_seawch_wevew_0__swig_0(swigcptw, (U ᵕ U❁) t-this, :3 n-ny, swigtype_p_fwoat.getcptw(x), ( ͡o ω ͡o ) k, swigtype_p_int.getcptw(neawest), òωó swigtype_p_fwoat.getcptw(neawest_d), σωσ swigtype_p_fwoat.getcptw(distances), (U ᵕ U❁) s-swigtype_p_wong_wong.getcptw(wabews.data()), (✿oωo) wabews, nypwobe, ^^ seawch_type);
  }

  pubwic void seawch_wevew_0(wong n-ny, ^•ﻌ•^ swigtype_p_fwoat x, XD wong k, swigtype_p_int n-nyeawest, :3 swigtype_p_fwoat nyeawest_d, (ꈍᴗꈍ) s-swigtype_p_fwoat distances, :3 wongvectow wabews, (U ﹏ U) int nypwobe) {
    s-swigfaissjni.indexhnsw_seawch_wevew_0__swig_1(swigcptw, UwU t-this, ny, swigtype_p_fwoat.getcptw(x), 😳😳😳 k, swigtype_p_int.getcptw(neawest), XD swigtype_p_fwoat.getcptw(neawest_d), o.O swigtype_p_fwoat.getcptw(distances), (⑅˘꒳˘) s-swigtype_p_wong_wong.getcptw(wabews.data()), 😳😳😳 wabews, nypwobe);
  }

  p-pubwic void seawch_wevew_0(wong ny, nyaa~~ swigtype_p_fwoat x, rawr wong k, -.- swigtype_p_int nyeawest, (✿oωo) s-swigtype_p_fwoat neawest_d, /(^•ω•^) s-swigtype_p_fwoat d-distances, 🥺 wongvectow wabews) {
    s-swigfaissjni.indexhnsw_seawch_wevew_0__swig_2(swigcptw, ʘwʘ this, UwU ny, swigtype_p_fwoat.getcptw(x), XD k-k, swigtype_p_int.getcptw(neawest), (✿oωo) s-swigtype_p_fwoat.getcptw(neawest_d), s-swigtype_p_fwoat.getcptw(distances), :3 swigtype_p_wong_wong.getcptw(wabews.data()), (///ˬ///✿) w-wabews);
  }

  p-pubwic void init_wevew_0_fwom_knngwaph(int k, nyaa~~ swigtype_p_fwoat d-d, >w< wongvectow i-i) {
    swigfaissjni.indexhnsw_init_wevew_0_fwom_knngwaph(swigcptw, -.- t-this, (✿oωo) k, swigtype_p_fwoat.getcptw(d), (˘ω˘) swigtype_p_wong_wong.getcptw(i.data()), rawr i);
  }

  pubwic v-void init_wevew_0_fwom_entwy_points(int nypt, s-swigtype_p_int p-points, OwO swigtype_p_int nyeawests) {
    swigfaissjni.indexhnsw_init_wevew_0_fwom_entwy_points(swigcptw, ^•ﻌ•^ this, UwU n-nypt, swigtype_p_int.getcptw(points), (˘ω˘) s-swigtype_p_int.getcptw(neawests));
  }

  p-pubwic void weowdew_winks() {
    s-swigfaissjni.indexhnsw_weowdew_winks(swigcptw, (///ˬ///✿) this);
  }

  p-pubwic void wink_singwetons() {
    swigfaissjni.indexhnsw_wink_singwetons(swigcptw, σωσ this);
  }

}
