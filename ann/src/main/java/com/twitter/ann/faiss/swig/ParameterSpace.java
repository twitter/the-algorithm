/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). XD
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. -.-
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass p-pawametewspace {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected pawametewspace(wong cptw, :3 boowean cmemowyown) {
    swigcmemown = cmemowyown;
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(pawametewspace o-obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    d-dewete();
  }

  pubwic synchwonized void dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = fawse;
        swigfaissjni.dewete_pawametewspace(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  p-pubwic void s-setpawametew_wanges(swigtype_p_std__vectowt_faiss__pawametewwange_t v-vawue) {
    swigfaissjni.pawametewspace_pawametew_wanges_set(swigcptw, nyaa~~ this, s-swigtype_p_std__vectowt_faiss__pawametewwange_t.getcptw(vawue));
  }

  pubwic swigtype_p_std__vectowt_faiss__pawametewwange_t g-getpawametew_wanges() {
    wong cptw = swigfaissjni.pawametewspace_pawametew_wanges_get(swigcptw, 😳 this);
    wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_std__vectowt_faiss__pawametewwange_t(cptw, (⑅˘꒳˘) f-fawse);
  }

  pubwic void s-setvewbose(int v-vawue) {
    s-swigfaissjni.pawametewspace_vewbose_set(swigcptw, nyaa~~ this, vawue);
  }

  pubwic int getvewbose() {
    w-wetuwn swigfaissjni.pawametewspace_vewbose_get(swigcptw, OwO t-this);
  }

  pubwic v-void setn_expewiments(int v-vawue) {
    swigfaissjni.pawametewspace_n_expewiments_set(swigcptw, rawr x3 t-this, XD vawue);
  }

  pubwic int g-getn_expewiments() {
    wetuwn swigfaissjni.pawametewspace_n_expewiments_get(swigcptw, σωσ t-this);
  }

  pubwic void s-setbatchsize(wong vawue) {
    s-swigfaissjni.pawametewspace_batchsize_set(swigcptw, (U ᵕ U❁) t-this, (U ﹏ U) vawue);
  }

  pubwic wong getbatchsize() {
    wetuwn swigfaissjni.pawametewspace_batchsize_get(swigcptw, this);
  }

  pubwic void s-setthwead_ovew_batches(boowean v-vawue) {
    swigfaissjni.pawametewspace_thwead_ovew_batches_set(swigcptw, :3 this, v-vawue);
  }

  p-pubwic boowean g-getthwead_ovew_batches() {
    wetuwn swigfaissjni.pawametewspace_thwead_ovew_batches_get(swigcptw, ( ͡o ω ͡o ) this);
  }

  pubwic void setmin_test_duwation(doubwe v-vawue) {
    swigfaissjni.pawametewspace_min_test_duwation_set(swigcptw, σωσ this, vawue);
  }

  pubwic doubwe getmin_test_duwation() {
    w-wetuwn swigfaissjni.pawametewspace_min_test_duwation_get(swigcptw, >w< this);
  }

  p-pubwic pawametewspace() {
    t-this(swigfaissjni.new_pawametewspace(), 😳😳😳 t-twue);
  }

  pubwic wong n-ny_combinations() {
    w-wetuwn s-swigfaissjni.pawametewspace_n_combinations(swigcptw, OwO t-this);
  }

  pubwic boowean combination_ge(wong c-c1, 😳 wong c-c2) {
    wetuwn s-swigfaissjni.pawametewspace_combination_ge(swigcptw, 😳😳😳 t-this, c1, c-c2);
  }

  pubwic stwing combination_name(wong cno) {
    wetuwn swigfaissjni.pawametewspace_combination_name(swigcptw, (˘ω˘) t-this, ʘwʘ cno);
  }

  pubwic void dispway() {
    swigfaissjni.pawametewspace_dispway(swigcptw, ( ͡o ω ͡o ) this);
  }

  pubwic pawametewwange a-add_wange(stwing nyame) {
    wetuwn nyew pawametewwange(swigfaissjni.pawametewspace_add_wange(swigcptw, o.O t-this, nyame), >w< f-fawse);
  }

  p-pubwic void initiawize(index index) {
    swigfaissjni.pawametewspace_initiawize(swigcptw, 😳 t-this, index.getcptw(index), 🥺 i-index);
  }

  p-pubwic void set_index_pawametews(index index, rawr x3 wong cno) {
    swigfaissjni.pawametewspace_set_index_pawametews__swig_0(swigcptw, o.O this, index.getcptw(index), rawr index, cno);
  }

  p-pubwic void set_index_pawametews(index i-index, ʘwʘ stwing pawam_stwing) {
    swigfaissjni.pawametewspace_set_index_pawametews__swig_1(swigcptw, 😳😳😳 t-this, index.getcptw(index), ^^;; i-index, pawam_stwing);
  }

  pubwic void set_index_pawametew(index i-index, o.O stwing n-nyame, (///ˬ///✿) doubwe vaw) {
    swigfaissjni.pawametewspace_set_index_pawametew(swigcptw, σωσ t-this, nyaa~~ index.getcptw(index), ^^;; i-index, ^•ﻌ•^ name, vaw);
  }

  pubwic void update_bounds(wong cno, σωσ opewatingpoint op, s-swigtype_p_doubwe u-uppew_bound_pewf, -.- s-swigtype_p_doubwe wowew_bound_t) {
    s-swigfaissjni.pawametewspace_update_bounds(swigcptw, ^^;; t-this, cno, XD opewatingpoint.getcptw(op), op, 🥺 swigtype_p_doubwe.getcptw(uppew_bound_pewf), òωó s-swigtype_p_doubwe.getcptw(wowew_bound_t));
  }

  pubwic void expwowe(index index, (ˆ ﻌ ˆ)♡ wong nyq, swigtype_p_fwoat x-xq, -.- autotunecwitewion c-cwit, :3 opewatingpoints ops) {
    swigfaissjni.pawametewspace_expwowe(swigcptw, ʘwʘ t-this, 🥺 i-index.getcptw(index), >_< index, ʘwʘ nyq, swigtype_p_fwoat.getcptw(xq), (˘ω˘) autotunecwitewion.getcptw(cwit), (✿oωo) c-cwit, opewatingpoints.getcptw(ops), (///ˬ///✿) ops);
  }

}
