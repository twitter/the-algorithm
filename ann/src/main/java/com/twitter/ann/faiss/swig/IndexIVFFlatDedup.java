/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). nyaa~~
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 😳
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexivffwatdedup extends indexivffwat {
  pwivate t-twansient wong swigcptw;

  p-pwotected indexivffwatdedup(wong cptw, (⑅˘꒳˘) boowean cmemowyown) {
    supew(swigfaissjni.indexivffwatdedup_swigupcast(cptw), nyaa~~ cmemowyown);
    s-swigcptw = cptw;
  }

  p-pwotected static w-wong getcptw(indexivffwatdedup obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    d-dewete();
  }

  pubwic synchwonized void dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        swigcmemown = f-fawse;
        swigfaissjni.dewete_indexivffwatdedup(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  p-pubwic v-void setinstances(swigtype_p_std__unowdewed_muwtimapt_int64_t_int64_t_t vawue) {
    swigfaissjni.indexivffwatdedup_instances_set(swigcptw, OwO t-this, swigtype_p_std__unowdewed_muwtimapt_int64_t_int64_t_t.getcptw(vawue));
  }

  pubwic swigtype_p_std__unowdewed_muwtimapt_int64_t_int64_t_t g-getinstances() {
    wetuwn nyew swigtype_p_std__unowdewed_muwtimapt_int64_t_int64_t_t(swigfaissjni.indexivffwatdedup_instances_get(swigcptw, rawr x3 this), twue);
  }

  pubwic indexivffwatdedup(index quantizew, XD wong d-d, σωσ wong nywist_, metwictype awg3) {
    t-this(swigfaissjni.new_indexivffwatdedup__swig_0(index.getcptw(quantizew), (U ᵕ U❁) q-quantizew, d, (U ﹏ U) n-nywist_, awg3.swigvawue()), twue);
  }

  pubwic indexivffwatdedup(index q-quantizew, :3 w-wong d, ( ͡o ω ͡o ) wong nywist_) {
    t-this(swigfaissjni.new_indexivffwatdedup__swig_1(index.getcptw(quantizew), σωσ q-quantizew, >w< d, nywist_), t-twue);
  }

  pubwic void twain(wong n-ny, 😳😳😳 swigtype_p_fwoat x) {
    swigfaissjni.indexivffwatdedup_twain(swigcptw, OwO t-this, ny, swigtype_p_fwoat.getcptw(x));
  }

  pubwic void add_with_ids(wong n-ny, 😳 swigtype_p_fwoat x, 😳😳😳 wongvectow x-xids) {
    s-swigfaissjni.indexivffwatdedup_add_with_ids(swigcptw, (˘ω˘) this, ʘwʘ ny, swigtype_p_fwoat.getcptw(x), ( ͡o ω ͡o ) swigtype_p_wong_wong.getcptw(xids.data()), xids);
  }

  pubwic void seawch_pweassigned(wong n-n, o.O swigtype_p_fwoat x-x, >w< wong k, 😳 wongvectow a-assign, 🥺 swigtype_p_fwoat c-centwoid_dis, rawr x3 s-swigtype_p_fwoat distances, o.O wongvectow wabews, rawr boowean s-stowe_paiws, ʘwʘ ivfseawchpawametews pawams, 😳😳😳 indexivfstats stats) {
    swigfaissjni.indexivffwatdedup_seawch_pweassigned__swig_0(swigcptw, ^^;; this, o.O n-ny, swigtype_p_fwoat.getcptw(x), (///ˬ///✿) k, swigtype_p_wong_wong.getcptw(assign.data()), σωσ a-assign, nyaa~~ swigtype_p_fwoat.getcptw(centwoid_dis), ^^;; s-swigtype_p_fwoat.getcptw(distances), s-swigtype_p_wong_wong.getcptw(wabews.data()), ^•ﻌ•^ wabews, σωσ stowe_paiws, -.- i-ivfseawchpawametews.getcptw(pawams), ^^;; p-pawams, XD i-indexivfstats.getcptw(stats), 🥺 s-stats);
  }

  pubwic void seawch_pweassigned(wong ny, òωó swigtype_p_fwoat x-x, (ˆ ﻌ ˆ)♡ wong k-k, wongvectow a-assign, -.- swigtype_p_fwoat c-centwoid_dis, :3 s-swigtype_p_fwoat distances, ʘwʘ wongvectow wabews, 🥺 boowean stowe_paiws, >_< i-ivfseawchpawametews pawams) {
    swigfaissjni.indexivffwatdedup_seawch_pweassigned__swig_1(swigcptw, ʘwʘ this, ny, swigtype_p_fwoat.getcptw(x), (˘ω˘) k, swigtype_p_wong_wong.getcptw(assign.data()), (✿oωo) assign, (///ˬ///✿) swigtype_p_fwoat.getcptw(centwoid_dis), rawr x3 s-swigtype_p_fwoat.getcptw(distances), swigtype_p_wong_wong.getcptw(wabews.data()), -.- wabews, ^^ stowe_paiws, (⑅˘꒳˘) i-ivfseawchpawametews.getcptw(pawams), nyaa~~ p-pawams);
  }

  p-pubwic void seawch_pweassigned(wong n-ny, /(^•ω•^) swigtype_p_fwoat x, (U ﹏ U) w-wong k, 😳😳😳 wongvectow a-assign, >w< swigtype_p_fwoat centwoid_dis, XD swigtype_p_fwoat distances, o.O wongvectow wabews, mya boowean s-stowe_paiws) {
    swigfaissjni.indexivffwatdedup_seawch_pweassigned__swig_2(swigcptw, 🥺 t-this, ^^;; ny, swigtype_p_fwoat.getcptw(x), :3 k-k, swigtype_p_wong_wong.getcptw(assign.data()), (U ﹏ U) a-assign, swigtype_p_fwoat.getcptw(centwoid_dis), OwO swigtype_p_fwoat.getcptw(distances), 😳😳😳 swigtype_p_wong_wong.getcptw(wabews.data()), (ˆ ﻌ ˆ)♡ w-wabews, XD stowe_paiws);
  }

  pubwic w-wong wemove_ids(idsewectow sew) {
    wetuwn s-swigfaissjni.indexivffwatdedup_wemove_ids(swigcptw, (ˆ ﻌ ˆ)♡ t-this, idsewectow.getcptw(sew), ( ͡o ω ͡o ) sew);
  }

  pubwic void wange_seawch(wong ny, rawr x3 swigtype_p_fwoat x, nyaa~~ fwoat wadius, >_< w-wangeseawchwesuwt w-wesuwt) {
    s-swigfaissjni.indexivffwatdedup_wange_seawch(swigcptw, ^^;; this, n-ny, swigtype_p_fwoat.getcptw(x), (ˆ ﻌ ˆ)♡ w-wadius, ^^;; wangeseawchwesuwt.getcptw(wesuwt), (⑅˘꒳˘) wesuwt);
  }

  pubwic void update_vectows(int n-nyv, rawr x3 wongvectow idx, (///ˬ///✿) swigtype_p_fwoat v) {
    swigfaissjni.indexivffwatdedup_update_vectows(swigcptw, 🥺 this, >_< nyv, s-swigtype_p_wong_wong.getcptw(idx.data()), UwU i-idx, swigtype_p_fwoat.getcptw(v));
  }

  pubwic void weconstwuct_fwom_offset(wong w-wist_no, >_< w-wong offset, -.- swigtype_p_fwoat wecons) {
    swigfaissjni.indexivffwatdedup_weconstwuct_fwom_offset(swigcptw, mya t-this, wist_no, >w< offset, swigtype_p_fwoat.getcptw(wecons));
  }

  pubwic indexivffwatdedup() {
    this(swigfaissjni.new_indexivffwatdedup__swig_2(), (U ﹏ U) twue);
  }

}
