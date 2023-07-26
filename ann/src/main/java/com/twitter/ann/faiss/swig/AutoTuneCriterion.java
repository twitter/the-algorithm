/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (U ﹏ U)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (///ˬ///✿)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass a-autotunecwitewion {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected autotunecwitewion(wong cptw, 😳 boowean c-cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(autotunecwitewion obj) {
    w-wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    d-dewete();
  }

  pubwic s-synchwonized v-void dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        swigcmemown = fawse;
        s-swigfaissjni.dewete_autotunecwitewion(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic void setnq(wong v-vawue) {
    swigfaissjni.autotunecwitewion_nq_set(swigcptw, 😳 this, vawue);
  }

  pubwic wong getnq() {
    wetuwn swigfaissjni.autotunecwitewion_nq_get(swigcptw, σωσ t-this);
}

  pubwic void s-setnnn(wong v-vawue) {
    swigfaissjni.autotunecwitewion_nnn_set(swigcptw, t-this, rawr x3 vawue);
  }

  pubwic wong getnnn() {
    wetuwn s-swigfaissjni.autotunecwitewion_nnn_get(swigcptw, OwO t-this);
}

  pubwic void setgt_nnn(wong v-vawue) {
    s-swigfaissjni.autotunecwitewion_gt_nnn_set(swigcptw, /(^•ω•^) this, v-vawue);
  }

  pubwic wong getgt_nnn() {
    w-wetuwn swigfaissjni.autotunecwitewion_gt_nnn_get(swigcptw, 😳😳😳 this);
}

  pubwic void s-setgt_d(fwoatvectow vawue) {
    s-swigfaissjni.autotunecwitewion_gt_d_set(swigcptw, ( ͡o ω ͡o ) this, >_< fwoatvectow.getcptw(vawue), >w< v-vawue);
  }

  p-pubwic fwoatvectow getgt_d() {
    wong cptw = swigfaissjni.autotunecwitewion_gt_d_get(swigcptw, rawr this);
    wetuwn (cptw == 0) ? nyuww : n-nyew fwoatvectow(cptw, 😳 f-fawse);
  }

  pubwic void s-setgt_i(swigtype_p_std__vectowt_int64_t_t v-vawue) {
    s-swigfaissjni.autotunecwitewion_gt_i_set(swigcptw, >w< this, (⑅˘꒳˘) swigtype_p_std__vectowt_int64_t_t.getcptw(vawue));
  }

  pubwic s-swigtype_p_std__vectowt_int64_t_t getgt_i() {
    wong cptw = swigfaissjni.autotunecwitewion_gt_i_get(swigcptw, OwO this);
    wetuwn (cptw == 0) ? n-nuww : nyew swigtype_p_std__vectowt_int64_t_t(cptw, (ꈍᴗꈍ) fawse);
  }

  p-pubwic void s-set_gwoundtwuth(int g-gt_nnn, 😳 swigtype_p_fwoat gt_d_in, 😳😳😳 wongvectow g-gt_i_in) {
    s-swigfaissjni.autotunecwitewion_set_gwoundtwuth(swigcptw, mya t-this, mya g-gt_nnn, swigtype_p_fwoat.getcptw(gt_d_in), swigtype_p_wong_wong.getcptw(gt_i_in.data()), (⑅˘꒳˘) gt_i_in);
  }

  p-pubwic d-doubwe evawuate(swigtype_p_fwoat d-d, wongvectow i-i) {
    wetuwn s-swigfaissjni.autotunecwitewion_evawuate(swigcptw, (U ﹏ U) this, swigtype_p_fwoat.getcptw(d), mya swigtype_p_wong_wong.getcptw(i.data()), ʘwʘ i);
  }

}
