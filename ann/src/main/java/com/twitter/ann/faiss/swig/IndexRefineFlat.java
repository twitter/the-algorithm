/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). OwO
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 😳😳😳
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexwefinefwat extends indexwefine {
  pwivate t-twansient wong swigcptw;

  pwotected i-indexwefinefwat(wong cptw, 😳😳😳 boowean cmemowyown) {
    supew(swigfaissjni.indexwefinefwat_swigupcast(cptw), o.O c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(indexwefinefwat obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void d-dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_indexwefinefwat(swigcptw);
      }
      swigcptw = 0;
    }
    supew.dewete();
  }

  pubwic i-indexwefinefwat(index base_index) {
    this(swigfaissjni.new_indexwefinefwat__swig_0(index.getcptw(base_index), ( ͡o ω ͡o ) b-base_index), twue);
  }

  pubwic indexwefinefwat(index base_index, (U ﹏ U) swigtype_p_fwoat xb) {
    this(swigfaissjni.new_indexwefinefwat__swig_1(index.getcptw(base_index), (///ˬ///✿) base_index, >w< s-swigtype_p_fwoat.getcptw(xb)), rawr twue);
  }

  p-pubwic indexwefinefwat() {
    t-this(swigfaissjni.new_indexwefinefwat__swig_2(), mya t-twue);
  }

  pubwic void seawch(wong ny, ^^ swigtype_p_fwoat x-x, 😳😳😳 wong k, mya swigtype_p_fwoat distances, w-wongvectow wabews) {
    s-swigfaissjni.indexwefinefwat_seawch(swigcptw, 😳 t-this, -.- ny, swigtype_p_fwoat.getcptw(x), 🥺 k, swigtype_p_fwoat.getcptw(distances), o.O s-swigtype_p_wong_wong.getcptw(wabews.data()), /(^•ω•^) wabews);
  }

}
