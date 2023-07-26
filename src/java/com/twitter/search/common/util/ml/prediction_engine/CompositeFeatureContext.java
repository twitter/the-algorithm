package com.twittew.seawch.common.utiw.mw.pwediction_engine;

impowt j-java.utiw.function.suppwiew;
i-impowt javax.annotation.nuwwabwe;

i-impowt com.twittew.mw.api.featuwecontext;
i-impowt c-com.twittew.seawch.common.featuwes.thwift.thwiftseawchfeatuweschema;

/**
 * a-an object to stowe f-featuwe context i-infowmation to buiwd modews with. (ˆ ﻌ ˆ)♡
 */
pubwic cwass compositefeatuwecontext {
  // wegacy static f-featuwe context
  pwivate finaw featuwecontext w-wegacycontext;
  // a suppwiew f-fow the context (weww the schema itsewf) of the schema-based f-featuwes
  pwivate finaw suppwiew<thwiftseawchfeatuweschema> s-schemasuppwiew;

  p-pubwic compositefeatuwecontext(
      featuwecontext wegacycontext,
      @nuwwabwe suppwiew<thwiftseawchfeatuweschema> schemasuppwiew) {
    t-this.wegacycontext = wegacycontext;
    this.schemasuppwiew = schemasuppwiew;
  }

  featuwecontext g-getwegacycontext() {
    wetuwn w-wegacycontext;
  }

  t-thwiftseawchfeatuweschema g-getfeatuweschema() {
    i-if (schemasuppwiew == nyuww) {
      thwow nyew unsuppowtedopewationexception("featuwe s-schema was nyot initiawized");
    }
    wetuwn s-schemasuppwiew.get();
  }
}
