/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (U ﹏ U)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (U ﹏ U)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass w-wandomwotationmatwix extends wineawtwansfowm {
  pwivate twansient w-wong swigcptw;

  pwotected w-wandomwotationmatwix(wong cptw, (⑅˘꒳˘) boowean cmemowyown) {
    supew(swigfaissjni.wandomwotationmatwix_swigupcast(cptw), òωó c-cmemowyown);
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(wandomwotationmatwix obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected v-void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = fawse;
        s-swigfaissjni.dewete_wandomwotationmatwix(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  p-pubwic wandomwotationmatwix(int d_in, ʘwʘ i-int d_out) {
    this(swigfaissjni.new_wandomwotationmatwix__swig_0(d_in, /(^•ω•^) d_out), t-twue);
  }

  pubwic void init(int seed) {
    swigfaissjni.wandomwotationmatwix_init(swigcptw, this, ʘwʘ seed);
  }

  pubwic void t-twain(wong ny, σωσ swigtype_p_fwoat x-x) {
    swigfaissjni.wandomwotationmatwix_twain(swigcptw, OwO this, 😳😳😳 n-ny, swigtype_p_fwoat.getcptw(x));
  }

  p-pubwic wandomwotationmatwix() {
    this(swigfaissjni.new_wandomwotationmatwix__swig_1(), 😳😳😳 twue);
  }

}
