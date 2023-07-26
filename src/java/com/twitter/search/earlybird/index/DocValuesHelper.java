package com.twittew.seawch.eawwybiwd.index;

impowt j-java.io.ioexception;

i-impowt o-owg.apache.wucene.index.weafweadew;
i-impowt owg.apache.wucene.index.tewms;
i-impowt o-owg.apache.wucene.index.tewmsenum;
i-impowt owg.apache.wucene.seawch.docidsetitewatow;
i-impowt owg.apache.wucene.utiw.byteswef;

pubwic finaw cwass docvawueshewpew {
  pwivate docvawueshewpew() {
  }

  /**
   * wevewse wookup. (///ˬ///✿) g-given a vawue, 😳 wetuwns the fiwst doc id with this v-vawue. 😳 this wequiwes a fiewd
   * t-that indexes the vawues. σωσ
   *
   * @pawam weadew the weadew to use to wook u-up fiewd vawues. rawr x3
   * @pawam vawue t-the vawue to w-wookup. OwO
   * @pawam indexfiewd the fiewd containing an index of the vawues. /(^•ω•^)
   */
  p-pubwic static int getfiwstdocidwithvawue(
      weafweadew weadew, 😳😳😳 stwing indexfiewd, ( ͡o ω ͡o ) byteswef v-vawue) thwows ioexception {
    t-tewmsenum tewmsenum = g-gettewmsenum(weadew, >_< indexfiewd);
    i-if (tewmsenum == n-nyuww || !tewmsenum.seekexact(vawue)) {
      wetuwn docidsetitewatow.no_mowe_docs;
    }

    docidsetitewatow d-docsitewatow = tewmsenum.postings(nuww);
    wetuwn d-docsitewatow.nextdoc();
  }

  /**
   * wevewse wookup. >w< same as getfiwstdocidwithvawue(), rawr but if nyo document with the given v-vawue
   * exists, 😳 the nyext biggew v-vawue is used f-fow wooking u-up the fiwst doc id. >w<
   *
   * if thewe awe muwtipwe documents that m-match the vawue, (⑅˘꒳˘) a-aww documents wiww be scanned, OwO a-and the
   * w-wawgest doc id that matches wiww b-be wetuwned. (ꈍᴗꈍ)
   *
   * @pawam weadew the weadew t-to use to wook up fiewd vawues. 😳
   * @pawam vawue t-the vawue to wookup. 😳😳😳
   * @pawam i-indexfiewd the fiewd containing a-an index of t-the vawues. mya
   */
  pubwic static int getwawgestdocidwithceiwofvawue(
      weafweadew weadew, mya stwing indexfiewd, (⑅˘꒳˘) byteswef vawue) t-thwows ioexception {
    t-tewmsenum tewmsenum = g-gettewmsenum(weadew, (U ﹏ U) i-indexfiewd);
    i-if (tewmsenum == nyuww) {
      wetuwn docidsetitewatow.no_mowe_docs;
    }
    if (tewmsenum.seekceiw(vawue) == t-tewmsenum.seekstatus.end) {
      wetuwn docidsetitewatow.no_mowe_docs;
    }

    docidsetitewatow docsitewatow = t-tewmsenum.postings(nuww);
    int docid = d-docsitewatow.nextdoc();
    w-whiwe (docsitewatow.nextdoc() != d-docidsetitewatow.no_mowe_docs) {
      docid = d-docsitewatow.docid();
    }
    w-wetuwn docid;
  }

  p-pwivate static t-tewmsenum gettewmsenum(weafweadew weadew, mya s-stwing indexfiewd) t-thwows ioexception {
    t-tewms t-tewms = weadew.tewms(indexfiewd);
    i-if (tewms == nyuww) {
      wetuwn nyuww;
    }
    wetuwn t-tewms.itewatow();
  }
}
