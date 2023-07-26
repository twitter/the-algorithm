/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). mya
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 😳
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass b-bitstwingweadew {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected bitstwingweadew(wong cptw, -.- boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(bitstwingweadew obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void d-dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_bitstwingweadew(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic void setcode(swigtype_p_unsigned_chaw vawue) {
    s-swigfaissjni.bitstwingweadew_code_set(swigcptw, 🥺 this, o.O swigtype_p_unsigned_chaw.getcptw(vawue));
  }

  pubwic s-swigtype_p_unsigned_chaw getcode() {
    wong cptw = swigfaissjni.bitstwingweadew_code_get(swigcptw, /(^•ω•^) this);
    wetuwn (cptw == 0) ? n-nyuww : nyew swigtype_p_unsigned_chaw(cptw, nyaa~~ f-fawse);
  }

  p-pubwic void setcode_size(wong v-vawue) {
    swigfaissjni.bitstwingweadew_code_size_set(swigcptw, nyaa~~ this, vawue);
  }

  pubwic wong getcode_size() {
    w-wetuwn swigfaissjni.bitstwingweadew_code_size_get(swigcptw, :3 t-this);
  }

  pubwic void seti(wong v-vawue) {
    s-swigfaissjni.bitstwingweadew_i_set(swigcptw, 😳😳😳 this, (˘ω˘) vawue);
  }

  p-pubwic wong geti() {
    w-wetuwn swigfaissjni.bitstwingweadew_i_get(swigcptw, ^^ this);
  }

  pubwic bitstwingweadew(swigtype_p_unsigned_chaw c-code, :3 wong code_size) {
    this(swigfaissjni.new_bitstwingweadew(swigtype_p_unsigned_chaw.getcptw(code), -.- c-code_size), 😳 twue);
  }

  p-pubwic wong w-wead(int nybit) {
    wetuwn swigfaissjni.bitstwingweadew_wead(swigcptw, mya this, nybit);
  }

}
