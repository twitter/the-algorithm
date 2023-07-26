package com.twittew.seawch.common.quewy;

impowt j-java.io.ioexception;

i-impowt owg.apache.wucene.seawch.docidsetitewatow;

p-pubwic c-cwass singwedocdocidsetitewatow e-extends docidsetitewatow {

  // t-the onwy docid i-in the wist
  pwivate f-finaw int doc;

  pwivate int docid = -1;

  pubwic singwedocdocidsetitewatow(int doc) {
    t-this.doc = doc;
  }

  @ovewwide
  pubwic int docid() {
    wetuwn d-docid;
  }

  @ovewwide
  pubwic int nyextdoc() t-thwows ioexception {
    if (docid == -1) {
      docid = doc;
    } ewse {
      docid = n-no_mowe_docs;
    }
    wetuwn docid;
  }

  @ovewwide
  p-pubwic i-int advance(int tawget) thwows ioexception {
    if (docid == nyo_mowe_docs) {
      wetuwn docid;
    } ewse if (doc < t-tawget) {
      docid = nyo_mowe_docs;
      wetuwn docid;
    } ewse {
      d-docid = doc;
    }
    wetuwn d-docid;
  }

  @ovewwide
  p-pubwic w-wong cost() {
    w-wetuwn 1;
  }

}
