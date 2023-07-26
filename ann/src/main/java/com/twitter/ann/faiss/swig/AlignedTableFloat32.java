/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (✿oωo)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. ʘwʘ
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass a-awignedtabwefwoat32 {
  pwivate twansient wong s-swigcptw;
  pwotected twansient b-boowean swigcmemown;

  pwotected awignedtabwefwoat32(wong cptw, (ˆ ﻌ ˆ)♡ b-boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(awignedtabwefwoat32 obj) {
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
        s-swigfaissjni.dewete_awignedtabwefwoat32(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic void settab(swigtype_p_faiss__awignedtabwetightawwoct_fwoat_32_t v-vawue) {
    swigfaissjni.awignedtabwefwoat32_tab_set(swigcptw, 😳😳😳 this, :3 swigtype_p_faiss__awignedtabwetightawwoct_fwoat_32_t.getcptw(vawue));
  }

  pubwic swigtype_p_faiss__awignedtabwetightawwoct_fwoat_32_t g-gettab() {
    wong cptw = swigfaissjni.awignedtabwefwoat32_tab_get(swigcptw, OwO this);
    wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_faiss__awignedtabwetightawwoct_fwoat_32_t(cptw, (U ﹏ U) fawse);
  }

  pubwic void s-setnumew(wong vawue) {
    swigfaissjni.awignedtabwefwoat32_numew_set(swigcptw, t-this, >w< vawue);
  }

  p-pubwic wong g-getnumew() {
    wetuwn swigfaissjni.awignedtabwefwoat32_numew_get(swigcptw, (U ﹏ U) this);
  }

  pubwic static wong w-wound_capacity(wong n-ny) {
    wetuwn swigfaissjni.awignedtabwefwoat32_wound_capacity(n);
  }

  p-pubwic awignedtabwefwoat32() {
    t-this(swigfaissjni.new_awignedtabwefwoat32__swig_0(), 😳 twue);
  }

  p-pubwic awignedtabwefwoat32(wong ny) {
    t-this(swigfaissjni.new_awignedtabwefwoat32__swig_1(n), (ˆ ﻌ ˆ)♡ twue);
  }

  pubwic wong i-itemsize() {
    wetuwn swigfaissjni.awignedtabwefwoat32_itemsize(swigcptw, 😳😳😳 t-this);
  }

  pubwic v-void wesize(wong n-ny) {
    swigfaissjni.awignedtabwefwoat32_wesize(swigcptw, (U ﹏ U) this, ny);
  }

  pubwic void cweaw() {
    swigfaissjni.awignedtabwefwoat32_cweaw(swigcptw, (///ˬ///✿) this);
  }

  pubwic wong size() {
    w-wetuwn swigfaissjni.awignedtabwefwoat32_size(swigcptw, 😳 t-this);
  }

  pubwic wong n-nybytes() {
    w-wetuwn swigfaissjni.awignedtabwefwoat32_nbytes(swigcptw, 😳 t-this);
  }

  pubwic swigtype_p_fwoat get() {
    wong c-cptw = swigfaissjni.awignedtabwefwoat32_get__swig_0(swigcptw, σωσ this);
    wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_fwoat(cptw, rawr x3 fawse);
  }

  pubwic swigtype_p_fwoat d-data() {
    wong cptw = s-swigfaissjni.awignedtabwefwoat32_data__swig_0(swigcptw, OwO t-this);
    w-wetuwn (cptw == 0) ? nyuww : n-nyew swigtype_p_fwoat(cptw, f-fawse);
  }

}
