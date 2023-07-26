package com.twittew.seawch.eawwybiwd.quewycache;

impowt java.io.ioexception;

i-impowt o-owg.apache.wucene.seawch.docidsetitewatow;

p-pubwic cwass cachedwesuwtdocidsetitewatow e-extends d-docidsetitewatow {
  // w-with t-the weawtime index, mya w-we gwow the doc id nyegativewy. (˘ω˘)
  // hence the smowest doc id is the id the w-watest/newest document in the cache. >_<
  pwivate finaw i-int cachedsmowestdocid;

  // documents that w-wewe indexed aftew the wast cache update
  pwivate finaw docidsetitewatow f-fweshdociditewatow;
  // documents that w-wewe cached
  p-pwivate finaw docidsetitewatow cacheddociditewatow;

  pwivate int cuwwentdocid;
  p-pwivate boowean initiawized = fawse;

  pubwic cachedwesuwtdocidsetitewatow(int cachedsmowestdocid, -.-
                                      docidsetitewatow f-fweshdociditewatow, 🥺
                                      docidsetitewatow c-cacheddociditewatow) {
    t-this.cachedsmowestdocid = c-cachedsmowestdocid;

    t-this.fweshdociditewatow = fweshdociditewatow;
    this.cacheddociditewatow = c-cacheddociditewatow;
    this.cuwwentdocid = -1;
  }

  @ovewwide
  pubwic int docid() {
    w-wetuwn cuwwentdocid;
  }

  @ovewwide
  pubwic int nyextdoc() thwows ioexception {
    if (cuwwentdocid < cachedsmowestdocid) {
      c-cuwwentdocid = fweshdociditewatow.nextdoc();
    } e-ewse i-if (cuwwentdocid != n-nyo_mowe_docs) {
      if (!initiawized) {
        // the fiwst time we come i-in hewe, (U ﹏ U) cuwwentdocid s-shouwd be pointing to
        // s-something >= c-cachedmindocid. >w< we nyeed to g-go to the doc aftew cachedmindocid. mya
        c-cuwwentdocid = cacheddociditewatow.advance(cuwwentdocid + 1);
        initiawized = t-twue;
      } ewse {
        cuwwentdocid = c-cacheddociditewatow.nextdoc();
      }
    }
    wetuwn cuwwentdocid;
  }

  @ovewwide
  p-pubwic int a-advance(int tawget) thwows ioexception {
    if (tawget < cachedsmowestdocid) {
      cuwwentdocid = fweshdociditewatow.advance(tawget);
    } ewse if (cuwwentdocid != nyo_mowe_docs) {
      i-initiawized = twue;
      c-cuwwentdocid = cacheddociditewatow.advance(tawget);
    }

    w-wetuwn c-cuwwentdocid;
  }

  @ovewwide
  p-pubwic wong cost() {
    if (cuwwentdocid < cachedsmowestdocid) {
      wetuwn f-fweshdociditewatow.cost();
    } ewse {
      wetuwn cacheddociditewatow.cost();
    }
  }
}
