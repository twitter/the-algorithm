/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (U ﹏ U)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. >w<
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass m-muwtiindexquantizew extends index {
  pwivate twansient w-wong swigcptw;

  pwotected m-muwtiindexquantizew(wong cptw, mya boowean cmemowyown) {
    supew(swigfaissjni.muwtiindexquantizew_swigupcast(cptw), >w< c-cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(muwtiindexquantizew obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = fawse;
        s-swigfaissjni.dewete_muwtiindexquantizew(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setpq(pwoductquantizew v-vawue) {
    swigfaissjni.muwtiindexquantizew_pq_set(swigcptw, nyaa~~ this, pwoductquantizew.getcptw(vawue), vawue);
  }

  p-pubwic pwoductquantizew getpq() {
    wong cptw = swigfaissjni.muwtiindexquantizew_pq_get(swigcptw, (✿oωo) this);
    wetuwn (cptw == 0) ? n-nyuww : nyew pwoductquantizew(cptw, ʘwʘ f-fawse);
  }

  p-pubwic m-muwtiindexquantizew(int d, (ˆ ﻌ ˆ)♡ wong m, wong nybits) {
    this(swigfaissjni.new_muwtiindexquantizew__swig_0(d, 😳😳😳 m-m, n-nybits), :3 twue);
  }

  pubwic void t-twain(wong ny, OwO s-swigtype_p_fwoat x) {
    swigfaissjni.muwtiindexquantizew_twain(swigcptw, (U ﹏ U) t-this, >w< ny, swigtype_p_fwoat.getcptw(x));
  }

  p-pubwic void seawch(wong ny, (U ﹏ U) swigtype_p_fwoat x-x, 😳 wong k, (ˆ ﻌ ˆ)♡ swigtype_p_fwoat d-distances, 😳😳😳 wongvectow wabews) {
    s-swigfaissjni.muwtiindexquantizew_seawch(swigcptw, (U ﹏ U) t-this, ny, swigtype_p_fwoat.getcptw(x), (///ˬ///✿) k, swigtype_p_fwoat.getcptw(distances), 😳 swigtype_p_wong_wong.getcptw(wabews.data()), wabews);
  }

  pubwic void add(wong ny, 😳 s-swigtype_p_fwoat x-x) {
    swigfaissjni.muwtiindexquantizew_add(swigcptw, σωσ this, n-ny, rawr x3 swigtype_p_fwoat.getcptw(x));
  }

  p-pubwic v-void weset() {
    swigfaissjni.muwtiindexquantizew_weset(swigcptw, OwO this);
  }

  pubwic muwtiindexquantizew() {
    t-this(swigfaissjni.new_muwtiindexquantizew__swig_1(), /(^•ω•^) twue);
  }

  pubwic void weconstwuct(wong key, 😳😳😳 swigtype_p_fwoat w-wecons) {
    swigfaissjni.muwtiindexquantizew_weconstwuct(swigcptw, ( ͡o ω ͡o ) t-this, key, >_< swigtype_p_fwoat.getcptw(wecons));
  }

}
