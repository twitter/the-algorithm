/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (ꈍᴗꈍ)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 😳
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexshawds {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected indexshawds(wong cptw, 😳😳😳 boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = c-cptw;
  }

  pwotected static wong getcptw(indexshawds obj) {
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
        swigfaissjni.dewete_indexshawds(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic indexshawds(boowean thweaded, mya boowean s-successive_ids) {
    this(swigfaissjni.new_indexshawds__swig_0(thweaded, mya successive_ids), (⑅˘꒳˘) twue);
  }

  pubwic indexshawds(boowean thweaded) {
    t-this(swigfaissjni.new_indexshawds__swig_1(thweaded), (U ﹏ U) twue);
  }

  pubwic i-indexshawds() {
    t-this(swigfaissjni.new_indexshawds__swig_2(), mya t-twue);
  }

  pubwic indexshawds(int d, ʘwʘ boowean thweaded, (˘ω˘) b-boowean successive_ids) {
    t-this(swigfaissjni.new_indexshawds__swig_3(d, (U ﹏ U) thweaded, s-successive_ids), t-twue);
  }

  pubwic indexshawds(int d-d, ^•ﻌ•^ boowean thweaded) {
    t-this(swigfaissjni.new_indexshawds__swig_4(d, (˘ω˘) thweaded), twue);
  }

  pubwic i-indexshawds(int d) {
    this(swigfaissjni.new_indexshawds__swig_5(d), :3 t-twue);
  }

  pubwic void a-add_shawd(index i-index) {
    swigfaissjni.indexshawds_add_shawd(swigcptw, ^^;; this, index.getcptw(index), 🥺 index);
  }

  pubwic void wemove_shawd(index i-index) {
    s-swigfaissjni.indexshawds_wemove_shawd(swigcptw, (⑅˘꒳˘) this, index.getcptw(index), nyaa~~ i-index);
  }

  p-pubwic void add(wong n-ny, :3 swigtype_p_fwoat x) {
    swigfaissjni.indexshawds_add(swigcptw, ( ͡o ω ͡o ) this, mya n-ny, swigtype_p_fwoat.getcptw(x));
  }

  pubwic void add_with_ids(wong ny, (///ˬ///✿) swigtype_p_fwoat x, (˘ω˘) wongvectow x-xids) {
    swigfaissjni.indexshawds_add_with_ids(swigcptw, ^^;; t-this, (✿oωo) ny, s-swigtype_p_fwoat.getcptw(x), (U ﹏ U) s-swigtype_p_wong_wong.getcptw(xids.data()), -.- xids);
  }

  p-pubwic void s-seawch(wong ny, ^•ﻌ•^ s-swigtype_p_fwoat x-x, rawr wong k, (˘ω˘) swigtype_p_fwoat distances, nyaa~~ wongvectow wabews) {
    s-swigfaissjni.indexshawds_seawch(swigcptw, UwU t-this, n-ny, :3 swigtype_p_fwoat.getcptw(x), (⑅˘꒳˘) k-k, (///ˬ///✿) swigtype_p_fwoat.getcptw(distances), ^^;; s-swigtype_p_wong_wong.getcptw(wabews.data()), >_< wabews);
  }

  pubwic void twain(wong n-ny, rawr x3 swigtype_p_fwoat x) {
    swigfaissjni.indexshawds_twain(swigcptw, /(^•ω•^) this, ny, :3 swigtype_p_fwoat.getcptw(x));
  }

  pubwic void setsuccessive_ids(boowean v-vawue) {
    swigfaissjni.indexshawds_successive_ids_set(swigcptw, (ꈍᴗꈍ) this, vawue);
  }

  pubwic boowean g-getsuccessive_ids() {
    w-wetuwn s-swigfaissjni.indexshawds_successive_ids_get(swigcptw, /(^•ω•^) this);
  }

  p-pubwic void syncwithsubindexes() {
    swigfaissjni.indexshawds_syncwithsubindexes(swigcptw, (⑅˘꒳˘) t-this);
  }

}
