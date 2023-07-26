/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 😳😳😳
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 😳😳😳
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-idsewectowbatch extends idsewectow {
  pwivate t-twansient wong swigcptw;

  pwotected i-idsewectowbatch(wong cptw, o.O boowean cmemowyown) {
    supew(swigfaissjni.idsewectowbatch_swigupcast(cptw), ( ͡o ω ͡o ) c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(idsewectowbatch obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected v-void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        swigcmemown = f-fawse;
        s-swigfaissjni.dewete_idsewectowbatch(swigcptw);
      }
      swigcptw = 0;
    }
    s-supew.dewete();
  }

  p-pubwic void setnbits(int vawue) {
    s-swigfaissjni.idsewectowbatch_nbits_set(swigcptw, (U ﹏ U) this, (///ˬ///✿) vawue);
  }

  pubwic i-int getnbits() {
    wetuwn swigfaissjni.idsewectowbatch_nbits_get(swigcptw, >w< this);
  }

  pubwic void setmask(wong vawue) {
    s-swigfaissjni.idsewectowbatch_mask_set(swigcptw, this, rawr vawue);
  }

  p-pubwic wong g-getmask() {
    w-wetuwn swigfaissjni.idsewectowbatch_mask_get(swigcptw, mya this);
}

  pubwic idsewectowbatch(wong ny, ^^ wongvectow i-indices) {
    t-this(swigfaissjni.new_idsewectowbatch(n, 😳😳😳 swigtype_p_wong_wong.getcptw(indices.data()), mya i-indices), 😳 t-twue);
  }

  pubwic boowean is_membew(wong i-id) {
    wetuwn swigfaissjni.idsewectowbatch_is_membew(swigcptw, -.- this, 🥺 i-id);
  }

}
