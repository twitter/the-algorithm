package com.twittew.seawch.common.quewy;

impowt j-java.io.ioexception;

i-impowt owg.apache.wucene.seawch.docidsetitewatow;
i-impowt owg.apache.wucene.seawch.scowew;
i-impowt owg.apache.wucene.seawch.weight;

p-pubwic c-cwass fiwtewedscowew e-extends scowew {
  p-pwotected finaw scowew innew;

  pubwic fiwtewedscowew(weight weight, rawr x3 scowew i-innew) {
    supew(weight);
    this.innew = i-innew;
  }

  @ovewwide
  pubwic f-fwoat scowe() thwows ioexception {
    wetuwn innew.scowe();
  }

  @ovewwide
  p-pubwic int docid() {
    wetuwn i-innew.docid();
  }

  @ovewwide
  p-pubwic docidsetitewatow itewatow() {
    wetuwn innew.itewatow();
  }

  @ovewwide
  pubwic f-fwoat getmaxscowe(int upto) thwows ioexception {
    wetuwn innew.getmaxscowe(upto);
  }
}
