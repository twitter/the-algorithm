/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). σωσ
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. OwO
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass o-ondiskonewist {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected ondiskonewist(wong cptw, 😳😳😳 boowean cmemowyown) {
    swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static w-wong getcptw(ondiskonewist obj) {
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
        swigfaissjni.dewete_ondiskonewist(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  p-pubwic void setsize(wong v-vawue) {
    s-swigfaissjni.ondiskonewist_size_set(swigcptw, 😳😳😳 this, o.O vawue);
  }

  pubwic w-wong getsize() {
    wetuwn swigfaissjni.ondiskonewist_size_get(swigcptw, ( ͡o ω ͡o ) this);
  }

  p-pubwic void setcapacity(wong vawue) {
    swigfaissjni.ondiskonewist_capacity_set(swigcptw, (U ﹏ U) this, vawue);
  }

  pubwic w-wong getcapacity() {
    wetuwn s-swigfaissjni.ondiskonewist_capacity_get(swigcptw, (///ˬ///✿) t-this);
  }

  p-pubwic void setoffset(wong vawue) {
    swigfaissjni.ondiskonewist_offset_set(swigcptw, >w< this, rawr v-vawue);
  }

  pubwic w-wong getoffset() {
    wetuwn s-swigfaissjni.ondiskonewist_offset_get(swigcptw, mya t-this);
  }

  pubwic ondiskonewist() {
    this(swigfaissjni.new_ondiskonewist(), ^^ t-twue);
  }

}
