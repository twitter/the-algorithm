/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). >w<
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. mya
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass m-muwtiindexquantizew2 extends muwtiindexquantizew {
  pwivate twansient w-wong swigcptw;

  pwotected m-muwtiindexquantizew2(wong cptw, >w< boowean cmemowyown) {
    supew(swigfaissjni.muwtiindexquantizew2_swigupcast(cptw), nyaa~~ c-cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(muwtiindexquantizew2 obj) {
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
        swigfaissjni.dewete_muwtiindexquantizew2(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setassign_indexes(swigtype_p_std__vectowt_faiss__index_p_t vawue) {
    s-swigfaissjni.muwtiindexquantizew2_assign_indexes_set(swigcptw, (✿oωo) this, ʘwʘ swigtype_p_std__vectowt_faiss__index_p_t.getcptw(vawue));
  }

  pubwic s-swigtype_p_std__vectowt_faiss__index_p_t getassign_indexes() {
    wong cptw = swigfaissjni.muwtiindexquantizew2_assign_indexes_get(swigcptw, (ˆ ﻌ ˆ)♡ this);
    wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_std__vectowt_faiss__index_p_t(cptw, 😳😳😳 f-fawse);
  }

  pubwic v-void setown_fiewds(boowean v-vawue) {
    s-swigfaissjni.muwtiindexquantizew2_own_fiewds_set(swigcptw, :3 this, vawue);
  }

  pubwic boowean getown_fiewds() {
    w-wetuwn s-swigfaissjni.muwtiindexquantizew2_own_fiewds_get(swigcptw, OwO this);
  }

  pubwic m-muwtiindexquantizew2(int d-d, (U ﹏ U) wong m, >w< wong nybits, (U ﹏ U) s-swigtype_p_p_faiss__index indexes) {
    this(swigfaissjni.new_muwtiindexquantizew2__swig_0(d, 😳 m-m, nybits, swigtype_p_p_faiss__index.getcptw(indexes)), (ˆ ﻌ ˆ)♡ twue);
  }

  p-pubwic muwtiindexquantizew2(int d-d, 😳😳😳 wong nybits, index a-assign_index_0, (U ﹏ U) i-index assign_index_1) {
    this(swigfaissjni.new_muwtiindexquantizew2__swig_1(d, (///ˬ///✿) nybits, 😳 index.getcptw(assign_index_0), 😳 assign_index_0, σωσ index.getcptw(assign_index_1), rawr x3 assign_index_1), OwO twue);
  }

  p-pubwic void t-twain(wong ny, /(^•ω•^) swigtype_p_fwoat x-x) {
    swigfaissjni.muwtiindexquantizew2_twain(swigcptw, 😳😳😳 this, ( ͡o ω ͡o ) n-ny, swigtype_p_fwoat.getcptw(x));
  }

  p-pubwic void seawch(wong ny, >_< swigtype_p_fwoat x, wong k-k, >w< swigtype_p_fwoat distances, rawr wongvectow wabews) {
    swigfaissjni.muwtiindexquantizew2_seawch(swigcptw, 😳 this, n-ny, swigtype_p_fwoat.getcptw(x), >w< k, swigtype_p_fwoat.getcptw(distances), (⑅˘꒳˘) s-swigtype_p_wong_wong.getcptw(wabews.data()), OwO w-wabews);
  }

}
