/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (⑅˘꒳˘)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. /(^•ω•^)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexhnswfwat extends indexhnsw {
  pwivate twansient w-wong swigcptw;

  pwotected i-indexhnswfwat(wong cptw, rawr x3 boowean cmemowyown) {
    supew(swigfaissjni.indexhnswfwat_swigupcast(cptw), (U ﹏ U) c-cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(indexhnswfwat obj) {
    wetuwn (obj == nyuww) ? 0 : o-obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  pubwic s-synchwonized void dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = f-fawse;
        swigfaissjni.dewete_indexhnswfwat(swigcptw);
      }
      swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic indexhnswfwat() {
    this(swigfaissjni.new_indexhnswfwat__swig_0(), (U ﹏ U) twue);
  }

  p-pubwic indexhnswfwat(int d, (⑅˘꒳˘) int m, metwictype metwic) {
    this(swigfaissjni.new_indexhnswfwat__swig_1(d, òωó m, metwic.swigvawue()), ʘwʘ t-twue);
  }

  pubwic indexhnswfwat(int d-d, /(^•ω•^) int m) {
    t-this(swigfaissjni.new_indexhnswfwat__swig_2(d, ʘwʘ m-m), twue);
  }

}
