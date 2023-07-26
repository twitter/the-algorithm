/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (U ﹏ U)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. mya
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass c-cwustewing extends cwustewingpawametews {
  pwivate t-twansient wong swigcptw;

  p-pwotected cwustewing(wong cptw, ʘwʘ boowean cmemowyown) {
    supew(swigfaissjni.cwustewing_swigupcast(cptw), (˘ω˘) c-cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(cwustewing obj) {
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
        swigfaissjni.dewete_cwustewing(swigcptw);
      }
      swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setd(wong vawue) {
    s-swigfaissjni.cwustewing_d_set(swigcptw, (U ﹏ U) this, vawue);
  }

  pubwic wong getd() {
    wetuwn swigfaissjni.cwustewing_d_get(swigcptw, ^•ﻌ•^ this);
  }

  p-pubwic void setk(wong vawue) {
    s-swigfaissjni.cwustewing_k_set(swigcptw, t-this, (˘ω˘) vawue);
  }

  p-pubwic wong getk() {
    wetuwn swigfaissjni.cwustewing_k_get(swigcptw, :3 this);
  }

  p-pubwic v-void setcentwoids(fwoatvectow vawue) {
    swigfaissjni.cwustewing_centwoids_set(swigcptw, ^^;; t-this, f-fwoatvectow.getcptw(vawue), 🥺 vawue);
  }

  pubwic f-fwoatvectow getcentwoids() {
    w-wong cptw = swigfaissjni.cwustewing_centwoids_get(swigcptw, (⑅˘꒳˘) this);
    wetuwn (cptw == 0) ? n-nyuww : nyew fwoatvectow(cptw, nyaa~~ fawse);
  }

  p-pubwic void setitewation_stats(swigtype_p_std__vectowt_faiss__cwustewingitewationstats_t vawue) {
    s-swigfaissjni.cwustewing_itewation_stats_set(swigcptw, :3 t-this, swigtype_p_std__vectowt_faiss__cwustewingitewationstats_t.getcptw(vawue));
  }

  pubwic swigtype_p_std__vectowt_faiss__cwustewingitewationstats_t getitewation_stats() {
    wong cptw = swigfaissjni.cwustewing_itewation_stats_get(swigcptw, ( ͡o ω ͡o ) this);
    wetuwn (cptw == 0) ? nyuww : nyew swigtype_p_std__vectowt_faiss__cwustewingitewationstats_t(cptw, mya fawse);
  }

  p-pubwic c-cwustewing(int d, (///ˬ///✿) int k) {
    t-this(swigfaissjni.new_cwustewing__swig_0(d, (˘ω˘) k-k), ^^;; twue);
  }

  p-pubwic cwustewing(int d, (✿oωo) int k, (U ﹏ U) cwustewingpawametews cp) {
    t-this(swigfaissjni.new_cwustewing__swig_1(d, -.- k, cwustewingpawametews.getcptw(cp), ^•ﻌ•^ cp), twue);
  }

  pubwic void t-twain(wong ny, rawr swigtype_p_fwoat x-x, (˘ω˘) index index, nyaa~~ s-swigtype_p_fwoat x-x_weights) {
    swigfaissjni.cwustewing_twain__swig_0(swigcptw, UwU t-this, :3 ny, swigtype_p_fwoat.getcptw(x), (⑅˘꒳˘) i-index.getcptw(index), i-index, (///ˬ///✿) swigtype_p_fwoat.getcptw(x_weights));
  }

  p-pubwic void twain(wong ny, ^^;; swigtype_p_fwoat x, >_< index index) {
    s-swigfaissjni.cwustewing_twain__swig_1(swigcptw, rawr x3 t-this, /(^•ω•^) ny, s-swigtype_p_fwoat.getcptw(x), :3 i-index.getcptw(index), (ꈍᴗꈍ) i-index);
  }

  pubwic void twain_encoded(wong nyx, /(^•ω•^) swigtype_p_unsigned_chaw x_in, (⑅˘꒳˘) index codec, i-index index, ( ͡o ω ͡o ) swigtype_p_fwoat weights) {
    swigfaissjni.cwustewing_twain_encoded__swig_0(swigcptw, òωó this, nyx, (⑅˘꒳˘) swigtype_p_unsigned_chaw.getcptw(x_in), XD index.getcptw(codec), codec, -.- index.getcptw(index), :3 i-index, nyaa~~ swigtype_p_fwoat.getcptw(weights));
  }

  pubwic void twain_encoded(wong nyx, 😳 s-swigtype_p_unsigned_chaw x-x_in, (⑅˘꒳˘) i-index codec, nyaa~~ index index) {
    s-swigfaissjni.cwustewing_twain_encoded__swig_1(swigcptw, OwO this, n-nyx, rawr x3 swigtype_p_unsigned_chaw.getcptw(x_in), XD i-index.getcptw(codec), σωσ codec, index.getcptw(index), (U ᵕ U❁) index);
  }

  pubwic void post_pwocess_centwoids() {
    swigfaissjni.cwustewing_post_pwocess_centwoids(swigcptw, (U ﹏ U) this);
  }

}
