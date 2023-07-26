/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). ( ͡o ω ͡o )
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. >_<
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass o-opqmatwix extends wineawtwansfowm {
  pwivate twansient w-wong swigcptw;

  pwotected o-opqmatwix(wong cptw, boowean cmemowyown) {
    supew(swigfaissjni.opqmatwix_swigupcast(cptw), >w< c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(opqmatwix obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        swigfaissjni.dewete_opqmatwix(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setm(int vawue) {
    s-swigfaissjni.opqmatwix_m_set(swigcptw, rawr this, vawue);
  }

  p-pubwic int getm() {
    wetuwn swigfaissjni.opqmatwix_m_get(swigcptw, 😳 this);
  }

  pubwic void setnitew(int v-vawue) {
    swigfaissjni.opqmatwix_nitew_set(swigcptw, >w< this, vawue);
  }

  p-pubwic i-int getnitew() {
    w-wetuwn swigfaissjni.opqmatwix_nitew_get(swigcptw, (⑅˘꒳˘) this);
  }

  pubwic v-void setnitew_pq(int v-vawue) {
    swigfaissjni.opqmatwix_nitew_pq_set(swigcptw, OwO t-this, vawue);
  }

  p-pubwic int getnitew_pq() {
    w-wetuwn swigfaissjni.opqmatwix_nitew_pq_get(swigcptw, (ꈍᴗꈍ) this);
  }

  p-pubwic void setnitew_pq_0(int vawue) {
    s-swigfaissjni.opqmatwix_nitew_pq_0_set(swigcptw, 😳 this, vawue);
  }

  p-pubwic int getnitew_pq_0() {
    w-wetuwn swigfaissjni.opqmatwix_nitew_pq_0_get(swigcptw, 😳😳😳 this);
  }

  p-pubwic void setmax_twain_points(wong vawue) {
    swigfaissjni.opqmatwix_max_twain_points_set(swigcptw, mya this, mya vawue);
  }

  pubwic wong getmax_twain_points() {
    wetuwn swigfaissjni.opqmatwix_max_twain_points_get(swigcptw, (⑅˘꒳˘) this);
  }

  p-pubwic v-void setvewbose(boowean vawue) {
    s-swigfaissjni.opqmatwix_vewbose_set(swigcptw, (U ﹏ U) t-this, vawue);
  }

  p-pubwic boowean getvewbose() {
    wetuwn swigfaissjni.opqmatwix_vewbose_get(swigcptw, mya t-this);
  }

  pubwic void setpq(pwoductquantizew vawue) {
    swigfaissjni.opqmatwix_pq_set(swigcptw, ʘwʘ this, (˘ω˘) pwoductquantizew.getcptw(vawue), (U ﹏ U) vawue);
  }

  p-pubwic pwoductquantizew g-getpq() {
    w-wong cptw = swigfaissjni.opqmatwix_pq_get(swigcptw, ^•ﻌ•^ t-this);
    wetuwn (cptw == 0) ? n-nyuww : nyew p-pwoductquantizew(cptw, (˘ω˘) f-fawse);
  }

  p-pubwic opqmatwix(int d, :3 int m, int d2) {
    t-this(swigfaissjni.new_opqmatwix__swig_0(d, ^^;; m-m, d2), 🥺 twue);
  }

  p-pubwic opqmatwix(int d-d, (⑅˘꒳˘) i-int m) {
    this(swigfaissjni.new_opqmatwix__swig_1(d, nyaa~~ m), twue);
  }

  pubwic opqmatwix(int d) {
    t-this(swigfaissjni.new_opqmatwix__swig_2(d), :3 twue);
  }

  pubwic opqmatwix() {
    this(swigfaissjni.new_opqmatwix__swig_3(), ( ͡o ω ͡o ) twue);
  }

  pubwic void t-twain(wong ny, mya swigtype_p_fwoat x) {
    swigfaissjni.opqmatwix_twain(swigcptw, (///ˬ///✿) this, ny, swigtype_p_fwoat.getcptw(x));
  }

}
