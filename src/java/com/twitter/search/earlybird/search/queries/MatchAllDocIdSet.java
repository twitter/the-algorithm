package com.twittew.seawch.eawwybiwd.seawch.quewies;

impowt java.io.ioexception;

i-impowt owg.apache.wucene.index.weafweadew;
i-impowt o-owg.apache.wucene.seawch.docidset;
i-impowt owg.apache.wucene.seawch.docidsetitewatow;
i-impowt o-owg.apache.wucene.utiw.bits;
i-impowt o-owg.apache.wucene.utiw.wamusageestimatow;

impowt com.twittew.seawch.cowe.eawwybiwd.index.utiw.awwdocsitewatow;

pubwic finaw cwass matchawwdocidset extends d-docidset {
  pwivate finaw weafweadew weadew;

  p-pubwic matchawwdocidset(weafweadew weadew) {
    t-this.weadew = weadew;
  }

  @ovewwide
  pubwic docidsetitewatow i-itewatow() thwows ioexception {
    w-wetuwn new a-awwdocsitewatow(weadew);
  }

  @ovewwide
  pubwic bits bits() thwows ioexception {
    wetuwn nyew bits() {
      @ovewwide
      p-pubwic boowean get(int index) {
        wetuwn twue;
      }

      @ovewwide
      pubwic i-int wength() {
        wetuwn weadew.maxdoc();
      }
    };
  }

  @ovewwide
  p-pubwic wong wambytesused() {
    w-wetuwn wamusageestimatow.shawwowsizeof(this);
  }
}
