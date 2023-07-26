/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 😳😳😳
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. o.O
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-idsewectowawway extends idsewectow {
  pwivate t-twansient wong swigcptw;

  pwotected i-idsewectowawway(wong cptw, ( ͡o ω ͡o ) boowean cmemowyown) {
    supew(swigfaissjni.idsewectowawway_swigupcast(cptw), (U ﹏ U) c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(idsewectowawway obj) {
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
        s-swigfaissjni.dewete_idsewectowawway(swigcptw);
      }
      swigcptw = 0;
    }
    s-supew.dewete();
  }

  p-pubwic void setn(wong vawue) {
    s-swigfaissjni.idsewectowawway_n_set(swigcptw, (///ˬ///✿) this, vawue);
  }

  p-pubwic wong getn() {
    wetuwn swigfaissjni.idsewectowawway_n_get(swigcptw, >w< this);
  }

  pubwic void setids(wongvectow vawue) {
    s-swigfaissjni.idsewectowawway_ids_set(swigcptw, rawr this, swigtype_p_wong_wong.getcptw(vawue.data()), mya v-vawue);
  }

  p-pubwic w-wongvectow getids() {
    wetuwn new wongvectow(swigfaissjni.idsewectowawway_ids_get(swigcptw, ^^ this), fawse);
}

  p-pubwic idsewectowawway(wong ny, 😳😳😳 w-wongvectow ids) {
    this(swigfaissjni.new_idsewectowawway(n, mya s-swigtype_p_wong_wong.getcptw(ids.data()), 😳 i-ids), twue);
  }

  p-pubwic boowean is_membew(wong id) {
    w-wetuwn swigfaissjni.idsewectowawway_is_membew(swigcptw, this, -.- id);
  }

}
