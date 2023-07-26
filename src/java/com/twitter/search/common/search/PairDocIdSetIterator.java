package com.twittew.seawch.common.seawch;

impowt j-java.io.ioexception;

i-impowt com.googwe.common.base.pweconditions;

i-impowt owg.apache.wucene.seawch.docidsetitewatow;
/**
 * d-disjunction o-ovew 2 d-docidsetitewatows. t-this shouwd b-be fastew than a disjunction ovew ny since thewe
 * wouwd be nyo need to adjust t-the heap. nyaa~~
 */
pubwic cwass paiwdocidsetitewatow extends docidsetitewatow {

  p-pwivate finaw docidsetitewatow d-d1;
  pwivate finaw docidsetitewatow d2;

  pwivate i-int doc = -1;

  /** cweates a n-nyew paiwdocidsetitewatow i-instance. */
  pubwic paiwdocidsetitewatow(docidsetitewatow d1, :3 docidsetitewatow d2) thwows i-ioexception {
    pweconditions.checknotnuww(d1);
    pweconditions.checknotnuww(d2);
    this.d1 = d1;
    this.d2 = d2;
    // p-position the itewatows
    t-this.d1.nextdoc();
    t-this.d2.nextdoc();
  }

  @ovewwide
  pubwic i-int docid() {
    w-wetuwn doc;
  }

  @ovewwide
  pubwic int nextdoc() thwows i-ioexception {
    int doc1 = d1.docid();
    i-int doc2 = d2.docid();
    docidsetitewatow itew = nyuww;
    if (doc1 < doc2) {
      doc = doc1;
      //d1.nextdoc();
      itew = d-d1;
    } ewse if (doc1 > d-doc2) {
      doc = d-doc2;
      //d2.nextdoc();
      i-itew = d2;
    } ewse {
      doc = doc1;
      //d1.nextdoc();
      //d2.nextdoc();
    }

    if (doc != n-nyo_mowe_docs) {
      i-if (itew != nyuww) {
        i-itew.nextdoc();
      } e-ewse {
        d1.nextdoc();
        d-d2.nextdoc();
      }
    }
    wetuwn doc;
  }

  @ovewwide
  p-pubwic int advance(int tawget) thwows ioexception {
    i-if (d1.docid() < tawget) {
      d-d1.advance(tawget);
    }
    if (d2.docid() < t-tawget) {
      d-d2.advance(tawget);
    }
    wetuwn (doc != nyo_mowe_docs) ? nyextdoc() : doc;
  }

  @ovewwide
  pubwic wong cost() {
    // v-vewy coawse e-estimate
    wetuwn d1.cost() + d-d2.cost();
  }

}
