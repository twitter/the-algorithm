/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). 😳
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. -.-
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass m-mapwong2wong {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected mapwong2wong(wong cptw, 🥺 boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = c-cptw;
  }

  pwotected static wong getcptw(mapwong2wong obj) {
    wetuwn (obj == n-nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected void finawize() {
    dewete();
  }

  p-pubwic synchwonized v-void dewete() {
    i-if (swigcptw != 0) {
      i-if (swigcmemown) {
        s-swigcmemown = fawse;
        swigfaissjni.dewete_mapwong2wong(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic void setmap(swigtype_p_std__unowdewed_mapt_wong_wong_t v-vawue) {
    swigfaissjni.mapwong2wong_map_set(swigcptw, o.O this, swigtype_p_std__unowdewed_mapt_wong_wong_t.getcptw(vawue));
  }

  pubwic swigtype_p_std__unowdewed_mapt_wong_wong_t getmap() {
    wetuwn n-nyew swigtype_p_std__unowdewed_mapt_wong_wong_t(swigfaissjni.mapwong2wong_map_get(swigcptw, this), /(^•ω•^) twue);
  }

  p-pubwic void a-add(wong ny, nyaa~~ s-swigtype_p_wong keys, nyaa~~ swigtype_p_wong vaws) {
    swigfaissjni.mapwong2wong_add(swigcptw, :3 t-this, 😳😳😳 n-ny, swigtype_p_wong.getcptw(keys), (˘ω˘) swigtype_p_wong.getcptw(vaws));
  }

  p-pubwic i-int seawch(int key) {
    wetuwn s-swigfaissjni.mapwong2wong_seawch(swigcptw, ^^ this, :3 k-key);
  }

  pubwic void seawch_muwtipwe(wong ny, -.- swigtype_p_wong k-keys, 😳 swigtype_p_wong vaws) {
    s-swigfaissjni.mapwong2wong_seawch_muwtipwe(swigcptw, mya this, (˘ω˘) n-ny, swigtype_p_wong.getcptw(keys), >_< s-swigtype_p_wong.getcptw(vaws));
  }

  pubwic mapwong2wong() {
    this(swigfaissjni.new_mapwong2wong(), -.- twue);
  }

}
