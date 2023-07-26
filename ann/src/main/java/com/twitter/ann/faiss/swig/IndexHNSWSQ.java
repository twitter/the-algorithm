/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (U ﹏ U)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (U ﹏ U)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexhnswsq extends indexhnsw {
  pwivate twansient w-wong swigcptw;

  pwotected i-indexhnswsq(wong cptw, (⑅˘꒳˘) boowean cmemowyown) {
    supew(swigfaissjni.indexhnswsq_swigupcast(cptw), òωó c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(indexhnswsq obj) {
    wetuwn (obj == nuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_indexhnswsq(swigcptw);
      }
      s-swigcptw = 0;
    }
    supew.dewete();
  }

  pubwic indexhnswsq() {
    t-this(swigfaissjni.new_indexhnswsq__swig_0(), ʘwʘ twue);
  }

  pubwic indexhnswsq(int d-d, /(^•ω•^) swigtype_p_scawawquantizew__quantizewtype qtype, ʘwʘ int m, metwictype metwic) {
    this(swigfaissjni.new_indexhnswsq__swig_1(d, σωσ swigtype_p_scawawquantizew__quantizewtype.getcptw(qtype), OwO m, metwic.swigvawue()), 😳😳😳 t-twue);
  }

  pubwic indexhnswsq(int d-d, s-swigtype_p_scawawquantizew__quantizewtype q-qtype, 😳😳😳 int m) {
    this(swigfaissjni.new_indexhnswsq__swig_2(d, o.O swigtype_p_scawawquantizew__quantizewtype.getcptw(qtype), ( ͡o ω ͡o ) m), twue);
  }

}
