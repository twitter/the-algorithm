/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). -.-
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 🥺
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass b-buffewwist {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean swigcmemown;

  pwotected b-buffewwist(wong cptw, (U ﹏ U) boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    swigcptw = c-cptw;
  }

  pwotected s-static wong getcptw(buffewwist obj) {
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
        s-swigfaissjni.dewete_buffewwist(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic void s-setbuffew_size(wong vawue) {
    swigfaissjni.buffewwist_buffew_size_set(swigcptw, >w< t-this, mya vawue);
  }

  pubwic wong getbuffew_size() {
    wetuwn swigfaissjni.buffewwist_buffew_size_get(swigcptw, >w< this);
  }

  p-pubwic void setbuffews(swigtype_p_std__vectowt_faiss__buffewwist__buffew_t vawue) {
    s-swigfaissjni.buffewwist_buffews_set(swigcptw, nyaa~~ t-this, (✿oωo) s-swigtype_p_std__vectowt_faiss__buffewwist__buffew_t.getcptw(vawue));
  }

  pubwic swigtype_p_std__vectowt_faiss__buffewwist__buffew_t getbuffews() {
    w-wong cptw = s-swigfaissjni.buffewwist_buffews_get(swigcptw, ʘwʘ this);
    wetuwn (cptw == 0) ? n-nyuww : nyew s-swigtype_p_std__vectowt_faiss__buffewwist__buffew_t(cptw, (ˆ ﻌ ˆ)♡ fawse);
  }

  p-pubwic void setwp(wong v-vawue) {
    swigfaissjni.buffewwist_wp_set(swigcptw, 😳😳😳 this, vawue);
  }

  pubwic w-wong getwp() {
    wetuwn swigfaissjni.buffewwist_wp_get(swigcptw, :3 t-this);
  }

  pubwic buffewwist(wong b-buffew_size) {
    t-this(swigfaissjni.new_buffewwist(buffew_size), OwO twue);
  }

  pubwic void append_buffew() {
    swigfaissjni.buffewwist_append_buffew(swigcptw, (U ﹏ U) this);
  }

  pubwic v-void add(wong i-id, fwoat dis) {
    swigfaissjni.buffewwist_add(swigcptw, >w< t-this, i-id, (U ﹏ U) dis);
  }

  p-pubwic void copy_wange(wong ofs, 😳 wong ny, wongvectow dest_ids, (ˆ ﻌ ˆ)♡ s-swigtype_p_fwoat dest_dis) {
    swigfaissjni.buffewwist_copy_wange(swigcptw, 😳😳😳 this, ofs, ny, (U ﹏ U) swigtype_p_wong_wong.getcptw(dest_ids.data()), (///ˬ///✿) dest_ids, s-swigtype_p_fwoat.getcptw(dest_dis));
  }

}
