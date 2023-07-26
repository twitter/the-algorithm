/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). -.-
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. 🥺
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass v-visitedtabwe {
  pwivate twansient wong swigcptw;
  p-pwotected twansient boowean s-swigcmemown;

  pwotected visitedtabwe(wong cptw, o.O boowean cmemowyown) {
    s-swigcmemown = cmemowyown;
    s-swigcptw = c-cptw;
  }

  pwotected static wong getcptw(visitedtabwe obj) {
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
        swigfaissjni.dewete_visitedtabwe(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  pubwic void setvisited(bytevectow v-vawue) {
    swigfaissjni.visitedtabwe_visited_set(swigcptw, /(^•ω•^) this, bytevectow.getcptw(vawue), nyaa~~ vawue);
  }

  pubwic bytevectow getvisited() {
    w-wong cptw = swigfaissjni.visitedtabwe_visited_get(swigcptw, nyaa~~ this);
    w-wetuwn (cptw == 0) ? n-nyuww : n-nyew bytevectow(cptw, fawse);
  }

  pubwic void setvisno(int v-vawue) {
    s-swigfaissjni.visitedtabwe_visno_set(swigcptw, :3 this, vawue);
  }

  p-pubwic int getvisno() {
    w-wetuwn swigfaissjni.visitedtabwe_visno_get(swigcptw, 😳😳😳 this);
  }

  p-pubwic visitedtabwe(int size) {
    t-this(swigfaissjni.new_visitedtabwe(size), (˘ω˘) twue);
  }

  pubwic void set(int n-no) {
    swigfaissjni.visitedtabwe_set(swigcptw, ^^ this, nyo);
  }

  p-pubwic boowean get(int nyo) {
    w-wetuwn s-swigfaissjni.visitedtabwe_get(swigcptw, :3 this, nyo);
  }

  pubwic void advance() {
    swigfaissjni.visitedtabwe_advance(swigcptw, -.- this);
  }

}
