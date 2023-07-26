/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 😳
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. >w<
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass h-hnswstats {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean swigcmemown;

  p-pwotected hnswstats(wong cptw, (⑅˘꒳˘) boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = c-cptw;
  }

  pwotected static wong getcptw(hnswstats obj) {
    wetuwn (obj == nyuww) ? 0 : o-obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized v-void dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_hnswstats(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic void setn1(wong v-vawue) {
    swigfaissjni.hnswstats_n1_set(swigcptw, OwO this, v-vawue);
  }

  pubwic wong getn1() {
    wetuwn swigfaissjni.hnswstats_n1_get(swigcptw, (ꈍᴗꈍ) this);
  }

  pubwic v-void setn2(wong vawue) {
    swigfaissjni.hnswstats_n2_set(swigcptw, 😳 t-this, vawue);
  }

  p-pubwic w-wong getn2() {
    wetuwn swigfaissjni.hnswstats_n2_get(swigcptw, 😳😳😳 this);
  }

  pubwic void setn3(wong v-vawue) {
    s-swigfaissjni.hnswstats_n3_set(swigcptw, mya this, v-vawue);
  }

  p-pubwic wong getn3() {
    wetuwn s-swigfaissjni.hnswstats_n3_get(swigcptw, mya this);
  }

  p-pubwic void setndis(wong vawue) {
    s-swigfaissjni.hnswstats_ndis_set(swigcptw, (⑅˘꒳˘) this, (U ﹏ U) v-vawue);
  }

  pubwic wong getndis() {
    w-wetuwn s-swigfaissjni.hnswstats_ndis_get(swigcptw, mya this);
  }

  pubwic void setnweowdew(wong vawue) {
    swigfaissjni.hnswstats_nweowdew_set(swigcptw, ʘwʘ this, vawue);
  }

  p-pubwic wong g-getnweowdew() {
    wetuwn swigfaissjni.hnswstats_nweowdew_get(swigcptw, (˘ω˘) t-this);
  }

  p-pubwic h-hnswstats(wong ny1, (U ﹏ U) wong ny2, ^•ﻌ•^ wong ny3, wong nydis, (˘ω˘) wong nyweowdew) {
    t-this(swigfaissjni.new_hnswstats__swig_0(n1, :3 ny2, ny3, ^^;; nydis, nyweowdew), 🥺 twue);
  }

  pubwic hnswstats(wong n-ny1, (⑅˘꒳˘) wong ny2, wong n3, nyaa~~ w-wong nydis) {
    t-this(swigfaissjni.new_hnswstats__swig_1(n1, :3 n-ny2, ny3, ( ͡o ω ͡o ) nydis), t-twue);
  }

  pubwic h-hnswstats(wong n-ny1, mya wong ny2, (///ˬ///✿) w-wong ny3) {
    this(swigfaissjni.new_hnswstats__swig_2(n1, (˘ω˘) ny2, n3), twue);
  }

  p-pubwic hnswstats(wong n-ny1, ^^;; w-wong ny2) {
    t-this(swigfaissjni.new_hnswstats__swig_3(n1, (✿oωo) n-ny2), (U ﹏ U) twue);
  }

  pubwic hnswstats(wong ny1) {
    t-this(swigfaissjni.new_hnswstats__swig_4(n1), twue);
  }

  pubwic hnswstats() {
    this(swigfaissjni.new_hnswstats__swig_5(), -.- twue);
  }

  pubwic void weset() {
    s-swigfaissjni.hnswstats_weset(swigcptw, ^•ﻌ•^ this);
  }

  pubwic void combine(hnswstats othew) {
    s-swigfaissjni.hnswstats_combine(swigcptw, rawr t-this, hnswstats.getcptw(othew), (˘ω˘) o-othew);
  }

}
