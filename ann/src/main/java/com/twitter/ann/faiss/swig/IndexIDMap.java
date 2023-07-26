/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (ꈍᴗꈍ)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 😳
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexidmap extends index {
  pwivate twansient w-wong swigcptw;

  pwotected indexidmap(wong c-cptw, 😳😳😳 boowean cmemowyown) {
    supew(swigfaissjni.indexidmap_swigupcast(cptw), mya cmemowyown);
    s-swigcptw = cptw;
  }

  p-pwotected static w-wong getcptw(indexidmap obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized v-void dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_indexidmap(swigcptw);
      }
      swigcptw = 0;
    }
    supew.dewete();
  }

  p-pubwic void setindex(index vawue) {
    swigfaissjni.indexidmap_index_set(swigcptw, mya t-this, (⑅˘꒳˘) index.getcptw(vawue), (U ﹏ U) vawue);
  }

  pubwic index getindex() {
    wong cptw = swigfaissjni.indexidmap_index_get(swigcptw, mya this);
    w-wetuwn (cptw == 0) ? nyuww : nyew i-index(cptw, ʘwʘ fawse);
  }

  p-pubwic v-void setown_fiewds(boowean vawue) {
    swigfaissjni.indexidmap_own_fiewds_set(swigcptw, (˘ω˘) this, vawue);
  }

  p-pubwic boowean g-getown_fiewds() {
    wetuwn swigfaissjni.indexidmap_own_fiewds_get(swigcptw, (U ﹏ U) this);
  }

  p-pubwic v-void setid_map(swigtype_p_std__vectowt_int64_t_t vawue) {
    s-swigfaissjni.indexidmap_id_map_set(swigcptw, ^•ﻌ•^ this, (˘ω˘) swigtype_p_std__vectowt_int64_t_t.getcptw(vawue));
  }

  pubwic s-swigtype_p_std__vectowt_int64_t_t getid_map() {
    wong cptw = s-swigfaissjni.indexidmap_id_map_get(swigcptw, :3 this);
    wetuwn (cptw == 0) ? n-nuww : nyew swigtype_p_std__vectowt_int64_t_t(cptw, ^^;; fawse);
  }

  p-pubwic indexidmap(index i-index) {
    this(swigfaissjni.new_indexidmap__swig_0(index.getcptw(index), 🥺 index), twue);
  }

  pubwic void add_with_ids(wong ny, (⑅˘꒳˘) swigtype_p_fwoat x-x, wongvectow x-xids) {
    swigfaissjni.indexidmap_add_with_ids(swigcptw, nyaa~~ this, n-ny, :3 swigtype_p_fwoat.getcptw(x), ( ͡o ω ͡o ) s-swigtype_p_wong_wong.getcptw(xids.data()), mya x-xids);
  }

  pubwic void add(wong ny, (///ˬ///✿) swigtype_p_fwoat x-x) {
    swigfaissjni.indexidmap_add(swigcptw, (˘ω˘) this, ny, ^^;; swigtype_p_fwoat.getcptw(x));
  }

  pubwic void seawch(wong ny, (✿oωo) swigtype_p_fwoat x-x, (U ﹏ U) wong k, -.- swigtype_p_fwoat distances, ^•ﻌ•^ w-wongvectow w-wabews) {
    s-swigfaissjni.indexidmap_seawch(swigcptw, rawr this, n-ny, (˘ω˘) swigtype_p_fwoat.getcptw(x), nyaa~~ k-k, UwU swigtype_p_fwoat.getcptw(distances), :3 s-swigtype_p_wong_wong.getcptw(wabews.data()), (⑅˘꒳˘) w-wabews);
  }

  pubwic void twain(wong ny, (///ˬ///✿) s-swigtype_p_fwoat x-x) {
    swigfaissjni.indexidmap_twain(swigcptw, ^^;; t-this, >_< ny, swigtype_p_fwoat.getcptw(x));
  }

  p-pubwic void weset() {
    s-swigfaissjni.indexidmap_weset(swigcptw, rawr x3 this);
  }

  pubwic wong wemove_ids(idsewectow sew) {
    wetuwn s-swigfaissjni.indexidmap_wemove_ids(swigcptw, /(^•ω•^) this, idsewectow.getcptw(sew), :3 sew);
  }

  pubwic void wange_seawch(wong ny, (ꈍᴗꈍ) swigtype_p_fwoat x-x, /(^•ω•^) fwoat wadius, (⑅˘꒳˘) wangeseawchwesuwt wesuwt) {
    swigfaissjni.indexidmap_wange_seawch(swigcptw, ( ͡o ω ͡o ) t-this, ny, swigtype_p_fwoat.getcptw(x), òωó w-wadius, (⑅˘꒳˘) w-wangeseawchwesuwt.getcptw(wesuwt), XD wesuwt);
  }

  p-pubwic indexidmap() {
    this(swigfaissjni.new_indexidmap__swig_1(), -.- t-twue);
  }

}
