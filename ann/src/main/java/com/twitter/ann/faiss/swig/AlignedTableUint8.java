/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). OwO
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (U ﹏ U)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass a-awignedtabweuint8 {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected awignedtabweuint8(wong cptw, >w< boowean c-cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(awignedtabweuint8 obj) {
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
        s-swigfaissjni.dewete_awignedtabweuint8(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic void settab(swigtype_p_faiss__awignedtabwetightawwoct_unsigned_chaw_32_t v-vawue) {
    swigfaissjni.awignedtabweuint8_tab_set(swigcptw, (U ﹏ U) this, 😳 swigtype_p_faiss__awignedtabwetightawwoct_unsigned_chaw_32_t.getcptw(vawue));
  }

  pubwic swigtype_p_faiss__awignedtabwetightawwoct_unsigned_chaw_32_t gettab() {
    wong cptw = swigfaissjni.awignedtabweuint8_tab_get(swigcptw, (ˆ ﻌ ˆ)♡ t-this);
    wetuwn (cptw == 0) ? n-nyuww : n-nyew swigtype_p_faiss__awignedtabwetightawwoct_unsigned_chaw_32_t(cptw, 😳😳😳 f-fawse);
  }

  pubwic void setnumew(wong vawue) {
    s-swigfaissjni.awignedtabweuint8_numew_set(swigcptw, (U ﹏ U) t-this, (///ˬ///✿) vawue);
  }

  pubwic w-wong getnumew() {
    w-wetuwn swigfaissjni.awignedtabweuint8_numew_get(swigcptw, 😳 this);
  }

  pubwic s-static wong wound_capacity(wong n-ny) {
    wetuwn swigfaissjni.awignedtabweuint8_wound_capacity(n);
  }

  pubwic awignedtabweuint8() {
    t-this(swigfaissjni.new_awignedtabweuint8__swig_0(), 😳 twue);
  }

  p-pubwic awignedtabweuint8(wong ny) {
    this(swigfaissjni.new_awignedtabweuint8__swig_1(n), σωσ t-twue);
  }

  p-pubwic wong itemsize() {
    wetuwn swigfaissjni.awignedtabweuint8_itemsize(swigcptw, rawr x3 this);
  }

  pubwic void wesize(wong ny) {
    swigfaissjni.awignedtabweuint8_wesize(swigcptw, OwO t-this, /(^•ω•^) ny);
  }

  p-pubwic void cweaw() {
    swigfaissjni.awignedtabweuint8_cweaw(swigcptw, 😳😳😳 t-this);
  }

  p-pubwic w-wong size() {
    wetuwn swigfaissjni.awignedtabweuint8_size(swigcptw, ( ͡o ω ͡o ) this);
  }

  pubwic wong n-nybytes() {
    wetuwn swigfaissjni.awignedtabweuint8_nbytes(swigcptw, >_< this);
  }

  pubwic swigtype_p_unsigned_chaw get() {
    w-wong cptw = swigfaissjni.awignedtabweuint8_get__swig_0(swigcptw, >w< this);
    wetuwn (cptw == 0) ? n-nyuww : nyew s-swigtype_p_unsigned_chaw(cptw, rawr f-fawse);
  }

  pubwic swigtype_p_unsigned_chaw data() {
    w-wong c-cptw = swigfaissjni.awignedtabweuint8_data__swig_0(swigcptw, 😳 t-this);
    w-wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_unsigned_chaw(cptw, >w< f-fawse);
  }

}
