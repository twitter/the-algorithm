/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). >_<
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. >_<
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass d-distancecomputew {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected distancecomputew(wong cptw, (⑅˘꒳˘) boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = cptw;
  }

  p-pwotected static wong getcptw(distancecomputew obj) {
    wetuwn (obj == n-nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  p-pubwic synchwonized void d-dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = fawse;
        swigfaissjni.dewete_distancecomputew(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic void set_quewy(swigtype_p_fwoat x-x) {
    swigfaissjni.distancecomputew_set_quewy(swigcptw, /(^•ω•^) this, rawr x3 swigtype_p_fwoat.getcptw(x));
  }

  pubwic fwoat symmetwic_dis(wong i, (U ﹏ U) wong j) {
    wetuwn s-swigfaissjni.distancecomputew_symmetwic_dis(swigcptw, (U ﹏ U) this, i, j-j);
  }

}
