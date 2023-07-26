/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (U ﹏ U)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (U ﹏ U)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass c-cwustewing1d extends cwustewing {
  pwivate twansient w-wong swigcptw;

  pwotected c-cwustewing1d(wong cptw, (⑅˘꒳˘) boowean cmemowyown) {
    supew(swigfaissjni.cwustewing1d_swigupcast(cptw), òωó c-cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(cwustewing1d obj) {
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
        swigfaissjni.dewete_cwustewing1d(swigcptw);
      }
      swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic cwustewing1d(int k) {
    t-this(swigfaissjni.new_cwustewing1d__swig_0(k), ʘwʘ twue);
  }

  pubwic cwustewing1d(int k, /(^•ω•^) cwustewingpawametews cp) {
    this(swigfaissjni.new_cwustewing1d__swig_1(k, ʘwʘ c-cwustewingpawametews.getcptw(cp), σωσ cp), twue);
  }

  p-pubwic v-void twain_exact(wong n-ny, OwO swigtype_p_fwoat x) {
    swigfaissjni.cwustewing1d_twain_exact(swigcptw, 😳😳😳 this, ny, 😳😳😳 swigtype_p_fwoat.getcptw(x));
  }

}
