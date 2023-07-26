/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). ^^
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. :3
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass c-cwustewingitewationstats {
  pwivate twansient w-wong swigcptw;
  pwotected twansient b-boowean swigcmemown;

  pwotected cwustewingitewationstats(wong cptw, -.- boowean c-cmemowyown) {
    swigcmemown = c-cmemowyown;
    s-swigcptw = cptw;
  }

  pwotected static wong getcptw(cwustewingitewationstats obj) {
    wetuwn (obj == n-nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  p-pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = fawse;
        s-swigfaissjni.dewete_cwustewingitewationstats(swigcptw);
      }
      swigcptw = 0;
    }
  }

  pubwic v-void setobj(fwoat vawue) {
    swigfaissjni.cwustewingitewationstats_obj_set(swigcptw, 😳 t-this, vawue);
  }

  pubwic fwoat getobj() {
    wetuwn swigfaissjni.cwustewingitewationstats_obj_get(swigcptw, mya this);
  }

  p-pubwic void settime(doubwe v-vawue) {
    s-swigfaissjni.cwustewingitewationstats_time_set(swigcptw, (˘ω˘) t-this, >_< vawue);
  }

  pubwic doubwe gettime() {
    wetuwn s-swigfaissjni.cwustewingitewationstats_time_get(swigcptw, -.- t-this);
  }

  pubwic v-void settime_seawch(doubwe v-vawue) {
    swigfaissjni.cwustewingitewationstats_time_seawch_set(swigcptw, 🥺 t-this, vawue);
  }

  p-pubwic doubwe gettime_seawch() {
    wetuwn swigfaissjni.cwustewingitewationstats_time_seawch_get(swigcptw, (U ﹏ U) this);
  }

  p-pubwic void setimbawance_factow(doubwe v-vawue) {
    swigfaissjni.cwustewingitewationstats_imbawance_factow_set(swigcptw, >w< this, vawue);
  }

  p-pubwic doubwe g-getimbawance_factow() {
    wetuwn swigfaissjni.cwustewingitewationstats_imbawance_factow_get(swigcptw, mya this);
  }

  pubwic void setnspwit(int vawue) {
    swigfaissjni.cwustewingitewationstats_nspwit_set(swigcptw, >w< t-this, v-vawue);
  }

  pubwic int getnspwit() {
    w-wetuwn swigfaissjni.cwustewingitewationstats_nspwit_get(swigcptw, t-this);
  }

  p-pubwic cwustewingitewationstats() {
    this(swigfaissjni.new_cwustewingitewationstats(), nyaa~~ twue);
  }

}
