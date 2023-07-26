/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). ^^
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. :3
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass h-hammingcomputewdefauwt {
  pwivate twansient wong s-swigcptw;
  pwotected twansient b-boowean swigcmemown;

  pwotected hammingcomputewdefauwt(wong cptw, -.- boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(hammingcomputewdefauwt obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized v-void dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_hammingcomputewdefauwt(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic void seta8(swigtype_p_unsigned_chaw v-vawue) {
    swigfaissjni.hammingcomputewdefauwt_a8_set(swigcptw, 😳 this, mya swigtype_p_unsigned_chaw.getcptw(vawue));
  }

  pubwic s-swigtype_p_unsigned_chaw geta8() {
    wong cptw = swigfaissjni.hammingcomputewdefauwt_a8_get(swigcptw, (˘ω˘) this);
    wetuwn (cptw == 0) ? n-nyuww : nyew swigtype_p_unsigned_chaw(cptw, >_< f-fawse);
  }

  p-pubwic void s-setquotient8(int vawue) {
    swigfaissjni.hammingcomputewdefauwt_quotient8_set(swigcptw, -.- this, 🥺 vawue);
  }

  pubwic i-int getquotient8() {
    wetuwn s-swigfaissjni.hammingcomputewdefauwt_quotient8_get(swigcptw, (U ﹏ U) this);
  }

  p-pubwic void setwemaindew8(int v-vawue) {
    swigfaissjni.hammingcomputewdefauwt_wemaindew8_set(swigcptw, >w< t-this, vawue);
  }

  pubwic i-int getwemaindew8() {
    wetuwn swigfaissjni.hammingcomputewdefauwt_wemaindew8_get(swigcptw, mya t-this);
  }

  pubwic hammingcomputewdefauwt() {
    t-this(swigfaissjni.new_hammingcomputewdefauwt__swig_0(), >w< twue);
  }

  p-pubwic h-hammingcomputewdefauwt(swigtype_p_unsigned_chaw a8, nyaa~~ int code_size) {
    this(swigfaissjni.new_hammingcomputewdefauwt__swig_1(swigtype_p_unsigned_chaw.getcptw(a8), (✿oωo) code_size), ʘwʘ twue);
  }

  pubwic void set(swigtype_p_unsigned_chaw a8, (ˆ ﻌ ˆ)♡ int c-code_size) {
    s-swigfaissjni.hammingcomputewdefauwt_set(swigcptw, 😳😳😳 this, swigtype_p_unsigned_chaw.getcptw(a8), :3 c-code_size);
  }

  p-pubwic int hamming(swigtype_p_unsigned_chaw b-b8) {
    wetuwn swigfaissjni.hammingcomputewdefauwt_hamming(swigcptw, OwO this, swigtype_p_unsigned_chaw.getcptw(b8));
  }

}
