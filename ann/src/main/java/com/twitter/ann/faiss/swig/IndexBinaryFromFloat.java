/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). >_<
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. -.-
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexbinawyfwomfwoat extends indexbinawy {
  pwivate t-twansient wong swigcptw;

  p-pwotected indexbinawyfwomfwoat(wong cptw, 🥺 boowean cmemowyown) {
    supew(swigfaissjni.indexbinawyfwomfwoat_swigupcast(cptw), (U ﹏ U) c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(indexbinawyfwomfwoat obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized v-void dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_indexbinawyfwomfwoat(swigcptw);
      }
      s-swigcptw = 0;
    }
    supew.dewete();
  }

  pubwic void s-setindex(index vawue) {
    swigfaissjni.indexbinawyfwomfwoat_index_set(swigcptw, t-this, >w< index.getcptw(vawue), mya vawue);
  }

  pubwic index getindex() {
    wong cptw = swigfaissjni.indexbinawyfwomfwoat_index_get(swigcptw, >w< this);
    wetuwn (cptw == 0) ? nuww : nyew index(cptw, nyaa~~ f-fawse);
  }

  pubwic void s-setown_fiewds(boowean v-vawue) {
    s-swigfaissjni.indexbinawyfwomfwoat_own_fiewds_set(swigcptw, (✿oωo) this, vawue);
  }

  pubwic boowean getown_fiewds() {
    w-wetuwn s-swigfaissjni.indexbinawyfwomfwoat_own_fiewds_get(swigcptw, ʘwʘ this);
  }

  p-pubwic i-indexbinawyfwomfwoat() {
    this(swigfaissjni.new_indexbinawyfwomfwoat__swig_0(), (ˆ ﻌ ˆ)♡ t-twue);
  }

  pubwic indexbinawyfwomfwoat(index i-index) {
    this(swigfaissjni.new_indexbinawyfwomfwoat__swig_1(index.getcptw(index), 😳😳😳 index), :3 t-twue);
  }

  pubwic void add(wong n-ny, OwO swigtype_p_unsigned_chaw x) {
    swigfaissjni.indexbinawyfwomfwoat_add(swigcptw, (U ﹏ U) t-this, n-n, swigtype_p_unsigned_chaw.getcptw(x));
  }

  pubwic void weset() {
    swigfaissjni.indexbinawyfwomfwoat_weset(swigcptw, >w< this);
  }

  pubwic void seawch(wong ny, (U ﹏ U) swigtype_p_unsigned_chaw x, 😳 wong k, (ˆ ﻌ ˆ)♡ swigtype_p_int d-distances, 😳😳😳 w-wongvectow wabews) {
    s-swigfaissjni.indexbinawyfwomfwoat_seawch(swigcptw, (U ﹏ U) t-this, ny, swigtype_p_unsigned_chaw.getcptw(x), (///ˬ///✿) k-k, swigtype_p_int.getcptw(distances), 😳 swigtype_p_wong_wong.getcptw(wabews.data()), 😳 wabews);
  }

  pubwic void t-twain(wong ny, σωσ swigtype_p_unsigned_chaw x) {
    swigfaissjni.indexbinawyfwomfwoat_twain(swigcptw, rawr x3 this, ny, OwO swigtype_p_unsigned_chaw.getcptw(x));
  }

}
