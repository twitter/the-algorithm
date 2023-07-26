/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 😳
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 😳
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass s-simuwatedanneawingpawametews {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected simuwatedanneawingpawametews(wong cptw, σωσ boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = c-cptw;
  }

  pwotected static wong getcptw(simuwatedanneawingpawametews obj) {
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
        swigfaissjni.dewete_simuwatedanneawingpawametews(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic void setinit_tempewatuwe(doubwe v-vawue) {
    swigfaissjni.simuwatedanneawingpawametews_init_tempewatuwe_set(swigcptw, rawr x3 this, vawue);
  }

  pubwic doubwe getinit_tempewatuwe() {
    wetuwn s-swigfaissjni.simuwatedanneawingpawametews_init_tempewatuwe_get(swigcptw, OwO this);
  }

  p-pubwic void s-settempewatuwe_decay(doubwe v-vawue) {
    swigfaissjni.simuwatedanneawingpawametews_tempewatuwe_decay_set(swigcptw, /(^•ω•^) this, 😳😳😳 vawue);
  }

  pubwic doubwe gettempewatuwe_decay() {
    w-wetuwn swigfaissjni.simuwatedanneawingpawametews_tempewatuwe_decay_get(swigcptw, ( ͡o ω ͡o ) t-this);
  }

  pubwic void s-setn_itew(int v-vawue) {
    swigfaissjni.simuwatedanneawingpawametews_n_itew_set(swigcptw, >_< this, v-vawue);
  }

  pubwic int getn_itew() {
    w-wetuwn swigfaissjni.simuwatedanneawingpawametews_n_itew_get(swigcptw, >w< this);
  }

  p-pubwic void setn_wedo(int vawue) {
    s-swigfaissjni.simuwatedanneawingpawametews_n_wedo_set(swigcptw, rawr this, 😳 vawue);
  }

  p-pubwic i-int getn_wedo() {
    wetuwn swigfaissjni.simuwatedanneawingpawametews_n_wedo_get(swigcptw, >w< this);
  }

  pubwic void setseed(int vawue) {
    swigfaissjni.simuwatedanneawingpawametews_seed_set(swigcptw, (⑅˘꒳˘) t-this, vawue);
  }

  p-pubwic int getseed() {
    w-wetuwn swigfaissjni.simuwatedanneawingpawametews_seed_get(swigcptw, OwO t-this);
  }

  p-pubwic void setvewbose(int vawue) {
    swigfaissjni.simuwatedanneawingpawametews_vewbose_set(swigcptw, (ꈍᴗꈍ) this, 😳 v-vawue);
  }

  pubwic int getvewbose() {
    wetuwn swigfaissjni.simuwatedanneawingpawametews_vewbose_get(swigcptw, 😳😳😳 this);
  }

  p-pubwic void setonwy_bit_fwips(boowean vawue) {
    s-swigfaissjni.simuwatedanneawingpawametews_onwy_bit_fwips_set(swigcptw, mya t-this, v-vawue);
  }

  pubwic boowean g-getonwy_bit_fwips() {
    w-wetuwn s-swigfaissjni.simuwatedanneawingpawametews_onwy_bit_fwips_get(swigcptw, mya t-this);
  }

  pubwic void setinit_wandom(boowean v-vawue) {
    s-swigfaissjni.simuwatedanneawingpawametews_init_wandom_set(swigcptw, (⑅˘꒳˘) t-this, v-vawue);
  }

  pubwic b-boowean getinit_wandom() {
    wetuwn swigfaissjni.simuwatedanneawingpawametews_init_wandom_get(swigcptw, (U ﹏ U) this);
  }

  pubwic simuwatedanneawingpawametews() {
    t-this(swigfaissjni.new_simuwatedanneawingpawametews(), twue);
  }

}
