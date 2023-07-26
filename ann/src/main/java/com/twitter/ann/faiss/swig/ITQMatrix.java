/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). nyaa~~
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. nyaa~~
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-itqmatwix extends wineawtwansfowm {
  pwivate twansient w-wong swigcptw;

  pwotected i-itqmatwix(wong cptw, boowean cmemowyown) {
    supew(swigfaissjni.itqmatwix_swigupcast(cptw), :3 c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(itqmatwix obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        swigfaissjni.dewete_itqmatwix(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setmax_itew(int v-vawue) {
    swigfaissjni.itqmatwix_max_itew_set(swigcptw, 😳😳😳 this, vawue);
  }

  p-pubwic int getmax_itew() {
    wetuwn swigfaissjni.itqmatwix_max_itew_get(swigcptw, (˘ω˘) this);
  }

  pubwic void setseed(int vawue) {
    swigfaissjni.itqmatwix_seed_set(swigcptw, ^^ t-this, :3 vawue);
  }

  pubwic int g-getseed() {
    w-wetuwn swigfaissjni.itqmatwix_seed_get(swigcptw, -.- t-this);
  }

  pubwic void setinit_wotation(doubwevectow vawue) {
    swigfaissjni.itqmatwix_init_wotation_set(swigcptw, 😳 t-this, mya d-doubwevectow.getcptw(vawue), (˘ω˘) vawue);
  }

  p-pubwic d-doubwevectow getinit_wotation() {
    w-wong cptw = swigfaissjni.itqmatwix_init_wotation_get(swigcptw, >_< t-this);
    wetuwn (cptw == 0) ? nyuww : n-nyew doubwevectow(cptw, -.- fawse);
  }

  p-pubwic itqmatwix(int d) {
    t-this(swigfaissjni.new_itqmatwix__swig_0(d), 🥺 t-twue);
  }

  pubwic itqmatwix() {
    this(swigfaissjni.new_itqmatwix__swig_1(), (U ﹏ U) twue);
  }

  pubwic void twain(wong ny, >w< swigtype_p_fwoat x) {
    s-swigfaissjni.itqmatwix_twain(swigcptw, mya t-this, n, >w< swigtype_p_fwoat.getcptw(x));
  }

}
