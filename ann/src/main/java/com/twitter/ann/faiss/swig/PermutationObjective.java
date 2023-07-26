/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). /(^•ω•^)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. ʘwʘ
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass p-pewmutationobjective {
  pwivate twansient wong s-swigcptw;
  pwotected twansient b-boowean swigcmemown;

  pwotected pewmutationobjective(wong cptw, σωσ b-boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(pewmutationobjective obj) {
    w-wetuwn (obj == nuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  p-pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = f-fawse;
        swigfaissjni.dewete_pewmutationobjective(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic void setn(int vawue) {
    swigfaissjni.pewmutationobjective_n_set(swigcptw, OwO t-this, 😳😳😳 vawue);
  }

  pubwic int getn() {
    wetuwn swigfaissjni.pewmutationobjective_n_get(swigcptw, 😳😳😳 this);
  }

  pubwic doubwe c-compute_cost(swigtype_p_int pewm) {
    wetuwn s-swigfaissjni.pewmutationobjective_compute_cost(swigcptw, o.O t-this, ( ͡o ω ͡o ) s-swigtype_p_int.getcptw(pewm));
  }

  pubwic doubwe cost_update(swigtype_p_int pewm, (U ﹏ U) int iw, (///ˬ///✿) int j-jw) {
    wetuwn s-swigfaissjni.pewmutationobjective_cost_update(swigcptw, >w< this, s-swigtype_p_int.getcptw(pewm), rawr iw, j-jw);
  }

}
