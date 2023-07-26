/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (⑅˘꒳˘)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. òωó
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-ivfpqseawchpawametews extends ivfseawchpawametews {
  pwivate twansient w-wong swigcptw;

  pwotected i-ivfpqseawchpawametews(wong cptw, ʘwʘ boowean cmemowyown) {
    supew(swigfaissjni.ivfpqseawchpawametews_swigupcast(cptw), /(^•ω•^) cmemowyown);
    s-swigcptw = cptw;
  }

  p-pwotected static w-wong getcptw(ivfpqseawchpawametews obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected v-void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_ivfpqseawchpawametews(swigcptw);
      }
      s-swigcptw = 0;
    }
    supew.dewete();
  }

  pubwic void s-setscan_tabwe_thweshowd(wong vawue) {
    swigfaissjni.ivfpqseawchpawametews_scan_tabwe_thweshowd_set(swigcptw, ʘwʘ t-this, σωσ vawue);
  }

  pubwic wong getscan_tabwe_thweshowd() {
    wetuwn swigfaissjni.ivfpqseawchpawametews_scan_tabwe_thweshowd_get(swigcptw, OwO this);
  }

  pubwic void setpowysemous_ht(int v-vawue) {
    swigfaissjni.ivfpqseawchpawametews_powysemous_ht_set(swigcptw, 😳😳😳 t-this, v-vawue);
  }

  pubwic i-int getpowysemous_ht() {
    wetuwn swigfaissjni.ivfpqseawchpawametews_powysemous_ht_get(swigcptw, 😳😳😳 this);
  }

  pubwic ivfpqseawchpawametews() {
    t-this(swigfaissjni.new_ivfpqseawchpawametews(), o.O t-twue);
  }

}
