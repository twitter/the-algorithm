/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 😳😳😳
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 😳😳😳
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass w-weadonwyinvewtedwists extends invewtedwists {
  pwivate twansient w-wong swigcptw;

  pwotected weadonwyinvewtedwists(wong c-cptw, o.O boowean cmemowyown) {
    supew(swigfaissjni.weadonwyinvewtedwists_swigupcast(cptw), ( ͡o ω ͡o ) cmemowyown);
    s-swigcptw = cptw;
  }

  pwotected s-static wong g-getcptw(weadonwyinvewtedwists obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void f-finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    if (swigcptw != 0) {
      i-if (swigcmemown) {
        swigcmemown = fawse;
        s-swigfaissjni.dewete_weadonwyinvewtedwists(swigcptw);
      }
      s-swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic w-wong add_entwies(wong wist_no, (U ﹏ U) wong ny_entwy, (///ˬ///✿) w-wongvectow ids, >w< swigtype_p_unsigned_chaw code) {
    w-wetuwn swigfaissjni.weadonwyinvewtedwists_add_entwies(swigcptw, rawr this, wist_no, mya ny_entwy, swigtype_p_wong_wong.getcptw(ids.data()), ^^ ids, s-swigtype_p_unsigned_chaw.getcptw(code));
  }

  pubwic void update_entwies(wong w-wist_no, 😳😳😳 wong offset, mya w-wong ny_entwy, 😳 w-wongvectow ids, -.- swigtype_p_unsigned_chaw code) {
    swigfaissjni.weadonwyinvewtedwists_update_entwies(swigcptw, 🥺 this, o.O wist_no, o-offset, /(^•ω•^) ny_entwy, s-swigtype_p_wong_wong.getcptw(ids.data()), nyaa~~ ids, swigtype_p_unsigned_chaw.getcptw(code));
  }

  p-pubwic void w-wesize(wong wist_no, nyaa~~ wong nyew_size) {
    s-swigfaissjni.weadonwyinvewtedwists_wesize(swigcptw, this, :3 wist_no, n-nyew_size);
  }

}
