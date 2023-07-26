/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (U ﹏ U)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. ^•ﻌ•^
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass w-wepwoducedistancesobjective extends pewmutationobjective {
  p-pwivate twansient w-wong swigcptw;

  pwotected wepwoducedistancesobjective(wong cptw, (˘ω˘) boowean cmemowyown) {
    s-supew(swigfaissjni.wepwoducedistancesobjective_swigupcast(cptw), :3 cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(wepwoducedistancesobjective obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        swigcmemown = fawse;
        s-swigfaissjni.dewete_wepwoducedistancesobjective(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  p-pubwic void setdis_weight_factow(doubwe vawue) {
    s-swigfaissjni.wepwoducedistancesobjective_dis_weight_factow_set(swigcptw, ^^;; this, 🥺 vawue);
  }

  pubwic doubwe g-getdis_weight_factow() {
    wetuwn swigfaissjni.wepwoducedistancesobjective_dis_weight_factow_get(swigcptw, (⑅˘꒳˘) this);
  }

  pubwic static doubwe sqw(doubwe x) {
    wetuwn s-swigfaissjni.wepwoducedistancesobjective_sqw(x);
  }

  pubwic doubwe d-dis_weight(doubwe x-x) {
    w-wetuwn swigfaissjni.wepwoducedistancesobjective_dis_weight(swigcptw, nyaa~~ this, x);
  }

  pubwic void setsouwce_dis(doubwevectow v-vawue) {
    s-swigfaissjni.wepwoducedistancesobjective_souwce_dis_set(swigcptw, :3 this, d-doubwevectow.getcptw(vawue), ( ͡o ω ͡o ) v-vawue);
  }

  pubwic doubwevectow g-getsouwce_dis() {
    wong cptw = s-swigfaissjni.wepwoducedistancesobjective_souwce_dis_get(swigcptw, mya this);
    wetuwn (cptw == 0) ? n-nyuww : nyew doubwevectow(cptw, (///ˬ///✿) f-fawse);
  }

  pubwic void s-settawget_dis(swigtype_p_doubwe v-vawue) {
    swigfaissjni.wepwoducedistancesobjective_tawget_dis_set(swigcptw, (˘ω˘) this, swigtype_p_doubwe.getcptw(vawue));
  }

  pubwic swigtype_p_doubwe gettawget_dis() {
    wong cptw = swigfaissjni.wepwoducedistancesobjective_tawget_dis_get(swigcptw, ^^;; this);
    wetuwn (cptw == 0) ? n-nyuww : n-nyew swigtype_p_doubwe(cptw, (✿oωo) fawse);
  }

  p-pubwic void setweights(doubwevectow v-vawue) {
    s-swigfaissjni.wepwoducedistancesobjective_weights_set(swigcptw, (U ﹏ U) this, doubwevectow.getcptw(vawue), -.- vawue);
  }

  pubwic doubwevectow g-getweights() {
    wong cptw = swigfaissjni.wepwoducedistancesobjective_weights_get(swigcptw, ^•ﻌ•^ this);
    wetuwn (cptw == 0) ? n-nyuww : nyew doubwevectow(cptw, rawr f-fawse);
  }

  p-pubwic doubwe g-get_souwce_dis(int i, (˘ω˘) int j) {
    w-wetuwn swigfaissjni.wepwoducedistancesobjective_get_souwce_dis(swigcptw, nyaa~~ this, UwU i-i, j);
  }

  p-pubwic doubwe c-compute_cost(swigtype_p_int pewm) {
    wetuwn s-swigfaissjni.wepwoducedistancesobjective_compute_cost(swigcptw, :3 t-this, swigtype_p_int.getcptw(pewm));
  }

  p-pubwic d-doubwe cost_update(swigtype_p_int p-pewm, (⑅˘꒳˘) int iw, (///ˬ///✿) int jw) {
    wetuwn swigfaissjni.wepwoducedistancesobjective_cost_update(swigcptw, ^^;; this, swigtype_p_int.getcptw(pewm), >_< i-iw, jw);
  }

  pubwic wepwoducedistancesobjective(int ny, rawr x3 swigtype_p_doubwe souwce_dis_in, /(^•ω•^) swigtype_p_doubwe t-tawget_dis_in, :3 doubwe dis_weight_factow) {
    this(swigfaissjni.new_wepwoducedistancesobjective(n, (ꈍᴗꈍ) swigtype_p_doubwe.getcptw(souwce_dis_in), /(^•ω•^) s-swigtype_p_doubwe.getcptw(tawget_dis_in), (⑅˘꒳˘) d-dis_weight_factow), ( ͡o ω ͡o ) t-twue);
  }

  pubwic static v-void compute_mean_stdev(swigtype_p_doubwe tab, òωó w-wong ny2, (⑅˘꒳˘) swigtype_p_doubwe m-mean_out, XD swigtype_p_doubwe stddev_out) {
    swigfaissjni.wepwoducedistancesobjective_compute_mean_stdev(swigtype_p_doubwe.getcptw(tab), -.- ny2, swigtype_p_doubwe.getcptw(mean_out), :3 swigtype_p_doubwe.getcptw(stddev_out));
  }

  pubwic v-void set_affine_tawget_dis(swigtype_p_doubwe souwce_dis_in) {
    s-swigfaissjni.wepwoducedistancesobjective_set_affine_tawget_dis(swigcptw, nyaa~~ this, swigtype_p_doubwe.getcptw(souwce_dis_in));
  }

}
