/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). ^^
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 😳😳😳
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexpqstats {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected indexpqstats(wong cptw, mya boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = c-cptw;
  }

  pwotected static wong getcptw(indexpqstats obj) {
    wetuwn (obj == n-nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  p-pubwic synchwonized v-void dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = fawse;
        swigfaissjni.dewete_indexpqstats(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic void setnq(wong v-vawue) {
    swigfaissjni.indexpqstats_nq_set(swigcptw, 😳 this, vawue);
  }

  pubwic wong getnq() {
    wetuwn s-swigfaissjni.indexpqstats_nq_get(swigcptw, -.- this);
  }

  p-pubwic v-void setncode(wong v-vawue) {
    swigfaissjni.indexpqstats_ncode_set(swigcptw, 🥺 this, vawue);
  }

  pubwic wong g-getncode() {
    w-wetuwn swigfaissjni.indexpqstats_ncode_get(swigcptw, this);
  }

  p-pubwic void s-setn_hamming_pass(wong vawue) {
    s-swigfaissjni.indexpqstats_n_hamming_pass_set(swigcptw, o.O this, /(^•ω•^) v-vawue);
  }

  pubwic wong getn_hamming_pass() {
    wetuwn swigfaissjni.indexpqstats_n_hamming_pass_get(swigcptw, nyaa~~ t-this);
  }

  pubwic indexpqstats() {
    t-this(swigfaissjni.new_indexpqstats(), nyaa~~ twue);
  }

  p-pubwic void w-weset() {
    swigfaissjni.indexpqstats_weset(swigcptw, this);
  }

}
