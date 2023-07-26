/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 🥺
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. o.O
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexivfpqstats {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected indexivfpqstats(wong cptw, /(^•ω•^) boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(indexivfpqstats obj) {
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
        s-swigfaissjni.dewete_indexivfpqstats(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic void setnwefine(wong vawue) {
    s-swigfaissjni.indexivfpqstats_nwefine_set(swigcptw, this, nyaa~~ vawue);
  }

  pubwic w-wong getnwefine() {
    wetuwn swigfaissjni.indexivfpqstats_nwefine_get(swigcptw, this);
  }

  pubwic void setn_hamming_pass(wong vawue) {
    s-swigfaissjni.indexivfpqstats_n_hamming_pass_set(swigcptw, nyaa~~ this, v-vawue);
  }

  p-pubwic wong getn_hamming_pass() {
    w-wetuwn swigfaissjni.indexivfpqstats_n_hamming_pass_get(swigcptw, :3 this);
  }

  pubwic void setseawch_cycwes(wong v-vawue) {
    s-swigfaissjni.indexivfpqstats_seawch_cycwes_set(swigcptw, 😳😳😳 this, v-vawue);
  }

  p-pubwic wong getseawch_cycwes() {
    wetuwn swigfaissjni.indexivfpqstats_seawch_cycwes_get(swigcptw, (˘ω˘) t-this);
  }

  pubwic void s-setwefine_cycwes(wong vawue) {
    swigfaissjni.indexivfpqstats_wefine_cycwes_set(swigcptw, ^^ t-this, :3 vawue);
  }

  p-pubwic wong getwefine_cycwes() {
    wetuwn swigfaissjni.indexivfpqstats_wefine_cycwes_get(swigcptw, -.- t-this);
  }

  p-pubwic indexivfpqstats() {
    this(swigfaissjni.new_indexivfpqstats(), 😳 twue);
  }

  pubwic void weset() {
    swigfaissjni.indexivfpqstats_weset(swigcptw, mya this);
  }

}
