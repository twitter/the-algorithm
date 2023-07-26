/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 😳😳😳
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 😳😳😳
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexhnsw2wevew extends indexhnsw {
  pwivate twansient w-wong swigcptw;

  pwotected i-indexhnsw2wevew(wong cptw, o.O boowean cmemowyown) {
    supew(swigfaissjni.indexhnsw2wevew_swigupcast(cptw), ( ͡o ω ͡o ) cmemowyown);
    s-swigcptw = cptw;
  }

  pwotected s-static wong getcptw(indexhnsw2wevew o-obj) {
    wetuwn (obj == nuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    d-dewete();
  }

  pubwic synchwonized void dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = fawse;
        s-swigfaissjni.dewete_indexhnsw2wevew(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  p-pubwic indexhnsw2wevew() {
    this(swigfaissjni.new_indexhnsw2wevew__swig_0(), (U ﹏ U) t-twue);
  }

  pubwic indexhnsw2wevew(index q-quantizew, (///ˬ///✿) wong nywist, >w< int m_pq, rawr int m) {
    this(swigfaissjni.new_indexhnsw2wevew__swig_1(index.getcptw(quantizew), mya quantizew, ^^ nywist, m-m_pq, 😳😳😳 m), twue);
  }

  pubwic v-void fwip_to_ivf() {
    s-swigfaissjni.indexhnsw2wevew_fwip_to_ivf(swigcptw, mya t-this);
  }

  pubwic void seawch(wong n, 😳 swigtype_p_fwoat x-x, -.- wong k, 🥺 s-swigtype_p_fwoat distances, o.O wongvectow w-wabews) {
    s-swigfaissjni.indexhnsw2wevew_seawch(swigcptw, this, /(^•ω•^) ny, swigtype_p_fwoat.getcptw(x), nyaa~~ k-k, swigtype_p_fwoat.getcptw(distances), nyaa~~ swigtype_p_wong_wong.getcptw(wabews.data()), :3 w-wabews);
  }

}
