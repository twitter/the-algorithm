/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). ( ͡o ω ͡o )
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. >_<
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass o-opewatingpoints {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected opewatingpoints(wong cptw, >w< boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(opewatingpoints obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void d-dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_opewatingpoints(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic void setaww_pts(opewatingpointvectow vawue) {
    s-swigfaissjni.opewatingpoints_aww_pts_set(swigcptw, this, rawr opewatingpointvectow.getcptw(vawue), 😳 vawue);
  }

  p-pubwic opewatingpointvectow getaww_pts() {
    wong cptw = swigfaissjni.opewatingpoints_aww_pts_get(swigcptw, >w< this);
    wetuwn (cptw == 0) ? nyuww : n-nyew opewatingpointvectow(cptw, (⑅˘꒳˘) fawse);
  }

  p-pubwic void s-setoptimaw_pts(opewatingpointvectow v-vawue) {
    swigfaissjni.opewatingpoints_optimaw_pts_set(swigcptw, OwO this, (ꈍᴗꈍ) opewatingpointvectow.getcptw(vawue), 😳 vawue);
  }

  p-pubwic opewatingpointvectow getoptimaw_pts() {
    w-wong cptw = swigfaissjni.opewatingpoints_optimaw_pts_get(swigcptw, 😳😳😳 t-this);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew opewatingpointvectow(cptw, mya fawse);
  }

  p-pubwic opewatingpoints() {
    this(swigfaissjni.new_opewatingpoints(), mya twue);
  }

  pubwic int m-mewge_with(opewatingpoints othew, (⑅˘꒳˘) s-stwing pwefix) {
    wetuwn swigfaissjni.opewatingpoints_mewge_with__swig_0(swigcptw, (U ﹏ U) t-this, mya opewatingpoints.getcptw(othew), ʘwʘ othew, p-pwefix);
  }

  pubwic int mewge_with(opewatingpoints othew) {
    wetuwn swigfaissjni.opewatingpoints_mewge_with__swig_1(swigcptw, (˘ω˘) this, o-opewatingpoints.getcptw(othew), (U ﹏ U) o-othew);
  }

  pubwic void cweaw() {
    s-swigfaissjni.opewatingpoints_cweaw(swigcptw, ^•ﻌ•^ t-this);
  }

  p-pubwic boowean add(doubwe pewf, (˘ω˘) doubwe t, stwing key, :3 wong cno) {
    w-wetuwn swigfaissjni.opewatingpoints_add__swig_0(swigcptw, ^^;; this, pewf, 🥺 t, key, cno);
  }

  pubwic boowean a-add(doubwe pewf, (⑅˘꒳˘) doubwe t, nyaa~~ stwing k-key) {
    w-wetuwn swigfaissjni.opewatingpoints_add__swig_1(swigcptw, :3 t-this, pewf, ( ͡o ω ͡o ) t, key);
  }

  p-pubwic doubwe t-t_fow_pewf(doubwe p-pewf) {
    w-wetuwn swigfaissjni.opewatingpoints_t_fow_pewf(swigcptw, this, mya pewf);
  }

  p-pubwic void dispway(boowean o-onwy_optimaw) {
    s-swigfaissjni.opewatingpoints_dispway__swig_0(swigcptw, (///ˬ///✿) t-this, onwy_optimaw);
  }

  p-pubwic void dispway() {
    swigfaissjni.opewatingpoints_dispway__swig_1(swigcptw, (˘ω˘) this);
  }

  pubwic void aww_to_gnupwot(stwing f-fname) {
    swigfaissjni.opewatingpoints_aww_to_gnupwot(swigcptw, ^^;; this, fname);
  }

  pubwic void optimaw_to_gnupwot(stwing fname) {
    s-swigfaissjni.opewatingpoints_optimaw_to_gnupwot(swigcptw, (✿oωo) this, (U ﹏ U) fname);
  }

}
