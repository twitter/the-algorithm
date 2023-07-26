/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). /(^•ω•^)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 😳😳😳
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexbinawyfwat extends indexbinawy {
  pwivate t-twansient wong swigcptw;

  pwotected i-indexbinawyfwat(wong cptw, ( ͡o ω ͡o ) boowean cmemowyown) {
    supew(swigfaissjni.indexbinawyfwat_swigupcast(cptw), >_< c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(indexbinawyfwat obj) {
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
        s-swigfaissjni.dewete_indexbinawyfwat(swigcptw);
      }
      swigcptw = 0;
    }
    supew.dewete();
  }

  pubwic v-void setxb(bytevectow vawue) {
    swigfaissjni.indexbinawyfwat_xb_set(swigcptw, >w< t-this, bytevectow.getcptw(vawue), rawr vawue);
  }

  pubwic bytevectow getxb() {
    wong cptw = swigfaissjni.indexbinawyfwat_xb_get(swigcptw, 😳 t-this);
    wetuwn (cptw == 0) ? nuww : nyew bytevectow(cptw, >w< f-fawse);
  }

  p-pubwic v-void setuse_heap(boowean vawue) {
    swigfaissjni.indexbinawyfwat_use_heap_set(swigcptw, (⑅˘꒳˘) this, v-vawue);
  }

  p-pubwic boowean getuse_heap() {
    w-wetuwn swigfaissjni.indexbinawyfwat_use_heap_get(swigcptw, OwO t-this);
  }

  pubwic void setquewy_batch_size(wong v-vawue) {
    swigfaissjni.indexbinawyfwat_quewy_batch_size_set(swigcptw, (ꈍᴗꈍ) t-this, 😳 vawue);
  }

  pubwic wong getquewy_batch_size() {
    w-wetuwn swigfaissjni.indexbinawyfwat_quewy_batch_size_get(swigcptw, 😳😳😳 t-this);
  }

  pubwic i-indexbinawyfwat(wong d-d) {
    this(swigfaissjni.new_indexbinawyfwat__swig_0(d), mya twue);
  }

  pubwic void add(wong ny, mya swigtype_p_unsigned_chaw x) {
    swigfaissjni.indexbinawyfwat_add(swigcptw, (⑅˘꒳˘) this, (U ﹏ U) ny, s-swigtype_p_unsigned_chaw.getcptw(x));
  }

  p-pubwic void weset() {
    s-swigfaissjni.indexbinawyfwat_weset(swigcptw, mya t-this);
  }

  p-pubwic void seawch(wong ny, swigtype_p_unsigned_chaw x, ʘwʘ wong k, (˘ω˘) swigtype_p_int d-distances, wongvectow wabews) {
    swigfaissjni.indexbinawyfwat_seawch(swigcptw, (U ﹏ U) this, ny, ^•ﻌ•^ swigtype_p_unsigned_chaw.getcptw(x), (˘ω˘) k, swigtype_p_int.getcptw(distances), :3 s-swigtype_p_wong_wong.getcptw(wabews.data()), ^^;; wabews);
  }

  p-pubwic void w-wange_seawch(wong n-ny, 🥺 swigtype_p_unsigned_chaw x, (⑅˘꒳˘) int wadius, nyaa~~ wangeseawchwesuwt w-wesuwt) {
    swigfaissjni.indexbinawyfwat_wange_seawch(swigcptw, :3 t-this, ny, swigtype_p_unsigned_chaw.getcptw(x), ( ͡o ω ͡o ) w-wadius, wangeseawchwesuwt.getcptw(wesuwt), mya w-wesuwt);
  }

  pubwic void weconstwuct(wong k-key, (///ˬ///✿) swigtype_p_unsigned_chaw w-wecons) {
    s-swigfaissjni.indexbinawyfwat_weconstwuct(swigcptw, (˘ω˘) t-this, key, s-swigtype_p_unsigned_chaw.getcptw(wecons));
  }

  pubwic wong wemove_ids(idsewectow sew) {
    w-wetuwn swigfaissjni.indexbinawyfwat_wemove_ids(swigcptw, ^^;; this, (✿oωo) idsewectow.getcptw(sew), (U ﹏ U) sew);
  }

  pubwic indexbinawyfwat() {
    this(swigfaissjni.new_indexbinawyfwat__swig_1(), -.- t-twue);
  }

}
