/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). /(^•ω•^)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. nyaa~~
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass w-wangequewywesuwt {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected wangequewywesuwt(wong cptw, nyaa~~ boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(wangequewywesuwt obj) {
    wetuwn (obj == n-nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  p-pubwic synchwonized void d-dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = fawse;
        swigfaissjni.dewete_wangequewywesuwt(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic void setqno(wong v-vawue) {
    swigfaissjni.wangequewywesuwt_qno_set(swigcptw, :3 this, 😳😳😳 vawue);
  }

  pubwic wong getqno() {
    wetuwn swigfaissjni.wangequewywesuwt_qno_get(swigcptw, (˘ω˘) t-this);
}

  pubwic void s-setnwes(wong vawue) {
    s-swigfaissjni.wangequewywesuwt_nwes_set(swigcptw, ^^ t-this, :3 vawue);
  }

  pubwic wong getnwes() {
    wetuwn s-swigfaissjni.wangequewywesuwt_nwes_get(swigcptw, -.- t-this);
  }

  pubwic void s-setpwes(wangeseawchpawtiawwesuwt v-vawue) {
    swigfaissjni.wangequewywesuwt_pwes_set(swigcptw, 😳 this, mya wangeseawchpawtiawwesuwt.getcptw(vawue), (˘ω˘) v-vawue);
  }

  pubwic w-wangeseawchpawtiawwesuwt getpwes() {
    wong c-cptw = swigfaissjni.wangequewywesuwt_pwes_get(swigcptw, >_< this);
    w-wetuwn (cptw == 0) ? nyuww : n-new wangeseawchpawtiawwesuwt(cptw, -.- f-fawse);
  }

  pubwic void add(fwoat dis, 🥺 wong id) {
    swigfaissjni.wangequewywesuwt_add(swigcptw, (U ﹏ U) this, dis, id);
  }

  pubwic wangequewywesuwt() {
    t-this(swigfaissjni.new_wangequewywesuwt(), >w< t-twue);
  }

}
