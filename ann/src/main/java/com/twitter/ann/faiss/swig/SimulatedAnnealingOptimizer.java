/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 😳
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. σωσ
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass s-simuwatedanneawingoptimizew extends simuwatedanneawingpawametews {
  p-pwivate twansient wong swigcptw;

  p-pwotected simuwatedanneawingoptimizew(wong cptw, rawr x3 boowean cmemowyown) {
    s-supew(swigfaissjni.simuwatedanneawingoptimizew_swigupcast(cptw), OwO cmemowyown);
    s-swigcptw = c-cptw;
  }

  pwotected static wong getcptw(simuwatedanneawingoptimizew obj) {
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
        swigfaissjni.dewete_simuwatedanneawingoptimizew(swigcptw);
      }
      s-swigcptw = 0;
    }
    supew.dewete();
  }

  pubwic v-void setobj(pewmutationobjective vawue) {
    swigfaissjni.simuwatedanneawingoptimizew_obj_set(swigcptw, /(^•ω•^) this, pewmutationobjective.getcptw(vawue), 😳😳😳 vawue);
  }

  p-pubwic pewmutationobjective getobj() {
    w-wong cptw = swigfaissjni.simuwatedanneawingoptimizew_obj_get(swigcptw, ( ͡o ω ͡o ) t-this);
    w-wetuwn (cptw == 0) ? nyuww : nyew pewmutationobjective(cptw, >_< fawse);
  }

  pubwic v-void setn(int v-vawue) {
    swigfaissjni.simuwatedanneawingoptimizew_n_set(swigcptw, >w< t-this, v-vawue);
  }

  pubwic int getn() {
    w-wetuwn swigfaissjni.simuwatedanneawingoptimizew_n_get(swigcptw, rawr this);
  }

  p-pubwic void setwogfiwe(swigtype_p_fiwe vawue) {
    s-swigfaissjni.simuwatedanneawingoptimizew_wogfiwe_set(swigcptw, 😳 this, swigtype_p_fiwe.getcptw(vawue));
  }

  p-pubwic swigtype_p_fiwe getwogfiwe() {
    w-wong cptw = swigfaissjni.simuwatedanneawingoptimizew_wogfiwe_get(swigcptw, >w< t-this);
    wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_fiwe(cptw, (⑅˘꒳˘) fawse);
  }

  pubwic simuwatedanneawingoptimizew(pewmutationobjective o-obj, OwO simuwatedanneawingpawametews p-p) {
    this(swigfaissjni.new_simuwatedanneawingoptimizew(pewmutationobjective.getcptw(obj), (ꈍᴗꈍ) o-obj, simuwatedanneawingpawametews.getcptw(p), 😳 p-p), twue);
  }

  p-pubwic void setwnd(swigtype_p_faiss__wandomgenewatow vawue) {
    swigfaissjni.simuwatedanneawingoptimizew_wnd_set(swigcptw, 😳😳😳 t-this, swigtype_p_faiss__wandomgenewatow.getcptw(vawue));
  }

  pubwic swigtype_p_faiss__wandomgenewatow getwnd() {
    wong cptw = swigfaissjni.simuwatedanneawingoptimizew_wnd_get(swigcptw, mya t-this);
    wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_faiss__wandomgenewatow(cptw, mya f-fawse);
  }

  p-pubwic void s-setinit_cost(doubwe vawue) {
    s-swigfaissjni.simuwatedanneawingoptimizew_init_cost_set(swigcptw, (⑅˘꒳˘) t-this, vawue);
  }

  p-pubwic doubwe g-getinit_cost() {
    wetuwn swigfaissjni.simuwatedanneawingoptimizew_init_cost_get(swigcptw, (U ﹏ U) t-this);
  }

  p-pubwic doubwe optimize(swigtype_p_int p-pewm) {
    w-wetuwn swigfaissjni.simuwatedanneawingoptimizew_optimize(swigcptw, mya t-this, swigtype_p_int.getcptw(pewm));
  }

  pubwic doubwe wun_optimization(swigtype_p_int best_pewm) {
    w-wetuwn swigfaissjni.simuwatedanneawingoptimizew_wun_optimization(swigcptw, ʘwʘ this, (˘ω˘) swigtype_p_int.getcptw(best_pewm));
  }

}
