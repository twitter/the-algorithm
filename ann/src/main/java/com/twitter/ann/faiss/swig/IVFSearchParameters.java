/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). òωó
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. ʘwʘ
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-ivfseawchpawametews {
  pwivate twansient wong s-swigcptw;
  pwotected twansient b-boowean swigcmemown;

  pwotected ivfseawchpawametews(wong cptw, /(^•ω•^) b-boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(ivfseawchpawametews obj) {
    wetuwn (obj == n-nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  p-pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = fawse;
        s-swigfaissjni.dewete_ivfseawchpawametews(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic void setnpwobe(wong v-vawue) {
    swigfaissjni.ivfseawchpawametews_npwobe_set(swigcptw, ʘwʘ this, vawue);
  }

  p-pubwic wong getnpwobe() {
    wetuwn swigfaissjni.ivfseawchpawametews_npwobe_get(swigcptw, σωσ this);
  }

  pubwic void s-setmax_codes(wong vawue) {
    s-swigfaissjni.ivfseawchpawametews_max_codes_set(swigcptw, OwO t-this, 😳😳😳 v-vawue);
  }

  pubwic wong getmax_codes() {
    wetuwn swigfaissjni.ivfseawchpawametews_max_codes_get(swigcptw, 😳😳😳 this);
  }

  pubwic i-ivfseawchpawametews() {
    t-this(swigfaissjni.new_ivfseawchpawametews(), o.O twue);
  }

}
