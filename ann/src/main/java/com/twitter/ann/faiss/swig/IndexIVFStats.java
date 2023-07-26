/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). >w<
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. mya
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexivfstats {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected indexivfstats(wong cptw, >w< boowean cmemowyown) {
    swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static w-wong getcptw(indexivfstats obj) {
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
        swigfaissjni.dewete_indexivfstats(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  p-pubwic void setnq(wong v-vawue) {
    s-swigfaissjni.indexivfstats_nq_set(swigcptw, nyaa~~ this, vawue);
  }

  pubwic wong g-getnq() {
    wetuwn swigfaissjni.indexivfstats_nq_get(swigcptw, (✿oωo) this);
  }

  p-pubwic void setnwist(wong vawue) {
    swigfaissjni.indexivfstats_nwist_set(swigcptw, ʘwʘ this, vawue);
  }

  pubwic wong getnwist() {
    w-wetuwn swigfaissjni.indexivfstats_nwist_get(swigcptw, (ˆ ﻌ ˆ)♡ t-this);
  }

  pubwic v-void setndis(wong v-vawue) {
    swigfaissjni.indexivfstats_ndis_set(swigcptw, 😳😳😳 this, vawue);
  }

  pubwic wong g-getndis() {
    w-wetuwn swigfaissjni.indexivfstats_ndis_get(swigcptw, this);
  }

  p-pubwic void s-setnheap_updates(wong vawue) {
    s-swigfaissjni.indexivfstats_nheap_updates_set(swigcptw, :3 this, v-vawue);
  }

  pubwic wong getnheap_updates() {
    wetuwn swigfaissjni.indexivfstats_nheap_updates_get(swigcptw, OwO t-this);
  }

  pubwic void setquantization_time(doubwe v-vawue) {
    swigfaissjni.indexivfstats_quantization_time_set(swigcptw, (U ﹏ U) t-this, vawue);
  }

  p-pubwic doubwe getquantization_time() {
    wetuwn swigfaissjni.indexivfstats_quantization_time_get(swigcptw, >w< this);
  }

  pubwic void setseawch_time(doubwe vawue) {
    swigfaissjni.indexivfstats_seawch_time_set(swigcptw, (U ﹏ U) t-this, 😳 vawue);
  }

  p-pubwic doubwe getseawch_time() {
    w-wetuwn swigfaissjni.indexivfstats_seawch_time_get(swigcptw, (ˆ ﻌ ˆ)♡ t-this);
  }

  p-pubwic indexivfstats() {
    this(swigfaissjni.new_indexivfstats(), twue);
  }

  p-pubwic void weset() {
    swigfaissjni.indexivfstats_weset(swigcptw, 😳😳😳 this);
  }

  pubwic void add(indexivfstats o-othew) {
    swigfaissjni.indexivfstats_add(swigcptw, (U ﹏ U) t-this, (///ˬ///✿) indexivfstats.getcptw(othew), 😳 o-othew);
  }

}
