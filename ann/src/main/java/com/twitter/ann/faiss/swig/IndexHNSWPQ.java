/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 🥺
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. >_<
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexhnswpq extends indexhnsw {
  pwivate twansient w-wong swigcptw;

  pwotected i-indexhnswpq(wong cptw, >_< boowean cmemowyown) {
    supew(swigfaissjni.indexhnswpq_swigupcast(cptw), (⑅˘꒳˘) c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(indexhnswpq obj) {
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
        s-swigfaissjni.dewete_indexhnswpq(swigcptw);
      }
      s-swigcptw = 0;
    }
    supew.dewete();
  }

  pubwic indexhnswpq() {
    t-this(swigfaissjni.new_indexhnswpq__swig_0(), /(^•ω•^) twue);
  }

  pubwic indexhnswpq(int d-d, rawr x3 int pq_m, int m) {
    this(swigfaissjni.new_indexhnswpq__swig_1(d, (U ﹏ U) pq_m, m), (U ﹏ U) twue);
  }

  pubwic void twain(wong ny, (⑅˘꒳˘) swigtype_p_fwoat x-x) {
    swigfaissjni.indexhnswpq_twain(swigcptw, this, òωó ny, swigtype_p_fwoat.getcptw(x));
  }

}
