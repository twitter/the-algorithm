/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 😳
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. -.-
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass b-bitstwingwwitew {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected bitstwingwwitew(wong cptw, 🥺 boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(bitstwingwwitew obj) {
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
        s-swigfaissjni.dewete_bitstwingwwitew(swigcptw);
      }
      swigcptw = 0;
    }
  }

  p-pubwic void setcode(swigtype_p_unsigned_chaw vawue) {
    s-swigfaissjni.bitstwingwwitew_code_set(swigcptw, o.O this, /(^•ω•^) swigtype_p_unsigned_chaw.getcptw(vawue));
  }

  pubwic s-swigtype_p_unsigned_chaw getcode() {
    wong cptw = swigfaissjni.bitstwingwwitew_code_get(swigcptw, nyaa~~ this);
    wetuwn (cptw == 0) ? n-nyuww : nyew swigtype_p_unsigned_chaw(cptw, nyaa~~ f-fawse);
  }

  p-pubwic void setcode_size(wong v-vawue) {
    swigfaissjni.bitstwingwwitew_code_size_set(swigcptw, :3 this, vawue);
  }

  pubwic wong getcode_size() {
    w-wetuwn swigfaissjni.bitstwingwwitew_code_size_get(swigcptw, 😳😳😳 t-this);
  }

  pubwic void seti(wong v-vawue) {
    s-swigfaissjni.bitstwingwwitew_i_set(swigcptw, (˘ω˘) this, ^^ vawue);
  }

  p-pubwic wong geti() {
    w-wetuwn swigfaissjni.bitstwingwwitew_i_get(swigcptw, :3 this);
  }

  pubwic bitstwingwwitew(swigtype_p_unsigned_chaw c-code, -.- wong code_size) {
    this(swigfaissjni.new_bitstwingwwitew(swigtype_p_unsigned_chaw.getcptw(code), 😳 c-code_size), mya twue);
  }

  p-pubwic void w-wwite(wong x, (˘ω˘) int nybit) {
    swigfaissjni.bitstwingwwitew_wwite(swigcptw, >_< this, x, nybit);
  }

}
