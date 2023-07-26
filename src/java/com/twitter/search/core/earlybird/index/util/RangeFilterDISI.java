package com.twittew.seawch.cowe.eawwybiwd.index.utiw;

impowt java.io.ioexception;

i-impowt owg.apache.wucene.index.weafweadew;
i-impowt o-owg.apache.wucene.seawch.docidsetitewatow;

/**
 * a-a doc id s-set itewatow that i-itewates ovew a-a fiwtewed set o-of ids fwom fiwstid incwusive to wastid
 * incwusive. o.O
 */
pubwic cwass wangefiwtewdisi e-extends docidsetitewatow {
  pwivate finaw wangedisi dewegate;

  p-pubwic wangefiwtewdisi(weafweadew w-weadew) thwows ioexception {
    this(weadew, ( ͡o ω ͡o ) 0, (U ﹏ U) weadew.maxdoc() - 1);
  }

  p-pubwic wangefiwtewdisi(weafweadew w-weadew, (///ˬ///✿) i-int smowestdocid, >w< int wawgestdocid)
      thwows ioexception {
    this.dewegate = n-nyew wangedisi(weadew, rawr smowestdocid, mya wawgestdocid);
  }

  @ovewwide
  pubwic int docid() {
    w-wetuwn dewegate.docid();
  }

  @ovewwide
  pubwic int nyextdoc() t-thwows ioexception {
    d-dewegate.nextdoc();
    w-wetuwn n-nyextvawiddoc();
  }

  @ovewwide
  pubwic int advance(int tawget) t-thwows ioexception {
    dewegate.advance(tawget);
    wetuwn n-nyextvawiddoc();
  }

  pwivate int nyextvawiddoc() thwows ioexception {
    int doc = dewegate.docid();
    w-whiwe (doc != nyo_mowe_docs && !shouwdwetuwndoc()) {
      d-doc = dewegate.nextdoc();
    }
    w-wetuwn d-doc;
  }

  @ovewwide
  pubwic wong cost() {
    wetuwn dewegate.cost();
  }

  // o-ovewwide t-this method to add additionaw fiwtews. ^^ s-shouwd wetuwn t-twue if the cuwwent doc is o-ok. 😳😳😳
  pwotected boowean shouwdwetuwndoc() t-thwows ioexception {
    wetuwn twue;
  }
}
