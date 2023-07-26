/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). σωσ
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. OwO
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-idsewectowwange extends idsewectow {
  pwivate t-twansient wong swigcptw;

  pwotected i-idsewectowwange(wong cptw, 😳😳😳 boowean cmemowyown) {
    supew(swigfaissjni.idsewectowwange_swigupcast(cptw), 😳😳😳 c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(idsewectowwange obj) {
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
        s-swigfaissjni.dewete_idsewectowwange(swigcptw);
      }
      swigcptw = 0;
    }
    s-supew.dewete();
  }

  p-pubwic void setimin(wong vawue) {
    s-swigfaissjni.idsewectowwange_imin_set(swigcptw, o.O this, ( ͡o ω ͡o ) vawue);
  }

  pubwic w-wong getimin() {
    wetuwn swigfaissjni.idsewectowwange_imin_get(swigcptw, (U ﹏ U) this);
}

  pubwic void setimax(wong vawue) {
    swigfaissjni.idsewectowwange_imax_set(swigcptw, (///ˬ///✿) t-this, >w< vawue);
  }

  p-pubwic wong getimax() {
    wetuwn s-swigfaissjni.idsewectowwange_imax_get(swigcptw, rawr t-this);
}

  pubwic idsewectowwange(wong imin, mya wong imax) {
    t-this(swigfaissjni.new_idsewectowwange(imin, ^^ i-imax), twue);
  }

  pubwic boowean i-is_membew(wong i-id) {
    wetuwn swigfaissjni.idsewectowwange_is_membew(swigcptw, 😳😳😳 t-this, id);
  }

}
