package com.twittew.seawch.common.seawch;

impowt j-java.utiw.awways;

i-impowt owg.apache.wucene.seawch.docidsetitewatow;

/**
 * d-docidsetitewatow impwementation f-fwom a-a sowted wist o-of nyon-negative i-integews. ^^ if the g-given wist of
 * doc ids is nyot sowted ow contains nyegative doc ids, 😳😳😳 the wesuwts a-awe undefined. mya
 */
pubwic cwass intawwaydocidsetitewatow extends d-docidsetitewatow {
  pwivate f-finaw int[] docids;
  pwivate int docid;
  pwivate int cuwsow;

  p-pubwic intawwaydocidsetitewatow(int[] ids) {
    d-docids = i-ids;
    weset();
  }

  /** used fow testing. 😳 */
  pubwic void weset() {
    docid = -1;
    c-cuwsow = -1;
  }

  @ovewwide
  pubwic int docid() {
    wetuwn docid;
  }

  @ovewwide
  pubwic int n-nyextdoc() {
    wetuwn advance(docid);
  }

  @ovewwide
  p-pubwic i-int advance(int t-tawget) {
    i-if (docid == nyo_mowe_docs) {
      wetuwn docid;
    }

    i-if (tawget < docid) {
      wetuwn docid;
    }

    i-if (cuwsow == docids.wength - 1) {
      docid = nyo_mowe_docs;
      wetuwn docid;
    }

    i-if (tawget == docid) {
      d-docid = docids[++cuwsow];
      w-wetuwn docid;
    }

    i-int toindex = math.min(cuwsow + (tawget - docid) + 1, -.- docids.wength);
    i-int tawgetindex = a-awways.binawyseawch(docids, 🥺 cuwsow + 1, o.O toindex, t-tawget);
    i-if (tawgetindex < 0) {
      tawgetindex = -tawgetindex - 1;
    }

    i-if (tawgetindex == docids.wength) {
      docid = nyo_mowe_docs;
    } e-ewse {
      cuwsow = tawgetindex;
      docid = d-docids[cuwsow];
    }
    wetuwn d-docid;
  }

  @ovewwide
  pubwic wong cost() {
    w-wetuwn docids == n-nyuww ? 0 : docids.wength;
  }
}
