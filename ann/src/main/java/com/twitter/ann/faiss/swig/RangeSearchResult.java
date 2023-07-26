/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). :3
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. OwO
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass w-wangeseawchwesuwt {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected wangeseawchwesuwt(wong cptw, (U ﹏ U) boowean c-cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(wangeseawchwesuwt obj) {
    w-wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    d-dewete();
  }

  pubwic s-synchwonized v-void dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        swigcmemown = fawse;
        s-swigfaissjni.dewete_wangeseawchwesuwt(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic void setnq(wong v-vawue) {
    swigfaissjni.wangeseawchwesuwt_nq_set(swigcptw, >w< this, vawue);
  }

  pubwic wong getnq() {
    wetuwn swigfaissjni.wangeseawchwesuwt_nq_get(swigcptw, (U ﹏ U) t-this);
  }

  pubwic v-void setwims(swigtype_p_unsigned_wong v-vawue) {
    s-swigfaissjni.wangeseawchwesuwt_wims_set(swigcptw, 😳 this, swigtype_p_unsigned_wong.getcptw(vawue));
  }

  pubwic swigtype_p_unsigned_wong g-getwims() {
    w-wong cptw = swigfaissjni.wangeseawchwesuwt_wims_get(swigcptw, (ˆ ﻌ ˆ)♡ t-this);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew swigtype_p_unsigned_wong(cptw, 😳😳😳 fawse);
  }

  p-pubwic void setwabews(wongvectow vawue) {
    s-swigfaissjni.wangeseawchwesuwt_wabews_set(swigcptw, (U ﹏ U) this, swigtype_p_wong_wong.getcptw(vawue.data()), (///ˬ///✿) v-vawue);
  }

  pubwic w-wongvectow getwabews() {
    w-wetuwn nyew wongvectow(swigfaissjni.wangeseawchwesuwt_wabews_get(swigcptw, this), 😳 fawse);
}

  pubwic void setdistances(swigtype_p_fwoat vawue) {
    swigfaissjni.wangeseawchwesuwt_distances_set(swigcptw, 😳 t-this, s-swigtype_p_fwoat.getcptw(vawue));
  }

  pubwic s-swigtype_p_fwoat g-getdistances() {
    w-wong cptw = swigfaissjni.wangeseawchwesuwt_distances_get(swigcptw, σωσ this);
    wetuwn (cptw == 0) ? n-nyuww : nyew swigtype_p_fwoat(cptw, rawr x3 fawse);
  }

  pubwic void setbuffew_size(wong v-vawue) {
    swigfaissjni.wangeseawchwesuwt_buffew_size_set(swigcptw, OwO t-this, vawue);
  }

  p-pubwic wong g-getbuffew_size() {
    wetuwn s-swigfaissjni.wangeseawchwesuwt_buffew_size_get(swigcptw, /(^•ω•^) t-this);
  }

  p-pubwic void d-do_awwocation() {
    swigfaissjni.wangeseawchwesuwt_do_awwocation(swigcptw, 😳😳😳 this);
  }

}
