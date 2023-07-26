/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). ʘwʘ
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (ˆ ﻌ ˆ)♡
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass a-awignedtabweuint16 {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected awignedtabweuint16(wong cptw, 😳😳😳 boowean c-cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(awignedtabweuint16 obj) {
    wetuwn (obj == n-nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_awignedtabweuint16(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic void settab(swigtype_p_faiss__awignedtabwetightawwoct_uint16_t_32_t v-vawue) {
    swigfaissjni.awignedtabweuint16_tab_set(swigcptw, :3 this, swigtype_p_faiss__awignedtabwetightawwoct_uint16_t_32_t.getcptw(vawue));
  }

  pubwic swigtype_p_faiss__awignedtabwetightawwoct_uint16_t_32_t g-gettab() {
    wong cptw = swigfaissjni.awignedtabweuint16_tab_get(swigcptw, OwO this);
    wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_faiss__awignedtabwetightawwoct_uint16_t_32_t(cptw, (U ﹏ U) fawse);
  }

  p-pubwic void setnumew(wong v-vawue) {
    s-swigfaissjni.awignedtabweuint16_numew_set(swigcptw, >w< t-this, (U ﹏ U) vawue);
  }

  pubwic wong getnumew() {
    wetuwn s-swigfaissjni.awignedtabweuint16_numew_get(swigcptw, 😳 t-this);
  }

  pubwic static w-wong wound_capacity(wong n-ny) {
    wetuwn swigfaissjni.awignedtabweuint16_wound_capacity(n);
  }

  p-pubwic awignedtabweuint16() {
    this(swigfaissjni.new_awignedtabweuint16__swig_0(), (ˆ ﻌ ˆ)♡ t-twue);
  }

  pubwic awignedtabweuint16(wong ny) {
    t-this(swigfaissjni.new_awignedtabweuint16__swig_1(n), 😳😳😳 twue);
  }

  p-pubwic wong itemsize() {
    w-wetuwn swigfaissjni.awignedtabweuint16_itemsize(swigcptw, (U ﹏ U) t-this);
  }

  pubwic void wesize(wong ny) {
    swigfaissjni.awignedtabweuint16_wesize(swigcptw, (///ˬ///✿) this, ny);
  }

  pubwic void cweaw() {
    s-swigfaissjni.awignedtabweuint16_cweaw(swigcptw, 😳 t-this);
  }

  pubwic wong s-size() {
    wetuwn s-swigfaissjni.awignedtabweuint16_size(swigcptw, 😳 t-this);
  }

  pubwic wong nybytes() {
    wetuwn swigfaissjni.awignedtabweuint16_nbytes(swigcptw, σωσ this);
  }

  p-pubwic swigtype_p_uint16_t get() {
    wong cptw = swigfaissjni.awignedtabweuint16_get__swig_0(swigcptw, rawr x3 this);
    wetuwn (cptw == 0) ? n-nyuww : nyew swigtype_p_uint16_t(cptw, OwO f-fawse);
  }

  p-pubwic swigtype_p_uint16_t data() {
    w-wong cptw = swigfaissjni.awignedtabweuint16_data__swig_0(swigcptw, /(^•ω•^) this);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew swigtype_p_uint16_t(cptw, 😳😳😳 f-fawse);
  }

}
