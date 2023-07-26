/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). /(^•ω•^)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. ʘwʘ
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass p-pwogwessivedimcwustewingpawametews extends cwustewingpawametews {
  pwivate twansient w-wong swigcptw;

  pwotected p-pwogwessivedimcwustewingpawametews(wong cptw, σωσ boowean cmemowyown) {
    supew(swigfaissjni.pwogwessivedimcwustewingpawametews_swigupcast(cptw), OwO c-cmemowyown);
    swigcptw = cptw;
  }

  p-pwotected s-static wong getcptw(pwogwessivedimcwustewingpawametews obj) {
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
        swigfaissjni.dewete_pwogwessivedimcwustewingpawametews(swigcptw);
      }
      swigcptw = 0;
    }
    s-supew.dewete();
  }

  pubwic void setpwogwessive_dim_steps(int v-vawue) {
    swigfaissjni.pwogwessivedimcwustewingpawametews_pwogwessive_dim_steps_set(swigcptw, 😳😳😳 this, 😳😳😳 vawue);
  }

  pubwic int getpwogwessive_dim_steps() {
    wetuwn swigfaissjni.pwogwessivedimcwustewingpawametews_pwogwessive_dim_steps_get(swigcptw, o.O this);
  }

  p-pubwic void setappwy_pca(boowean v-vawue) {
    s-swigfaissjni.pwogwessivedimcwustewingpawametews_appwy_pca_set(swigcptw, ( ͡o ω ͡o ) t-this, vawue);
  }

  pubwic boowean getappwy_pca() {
    wetuwn s-swigfaissjni.pwogwessivedimcwustewingpawametews_appwy_pca_get(swigcptw, (U ﹏ U) t-this);
  }

  pubwic pwogwessivedimcwustewingpawametews() {
    t-this(swigfaissjni.new_pwogwessivedimcwustewingpawametews(), (///ˬ///✿) t-twue);
  }

}
