package com.twittew.seawch.eawwybiwd.seawch;

impowt j-java.io.ioexception;

i-impowt c-com.twittew.common.utiw.cwock;
i-impowt com.twittew.seawch.common.schema.base.immutabweschemaintewface;
i-impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdcwustew;
i-impowt c-com.twittew.seawch.eawwybiwd.common.usewupdates.usewtabwe;
i-impowt com.twittew.seawch.eawwybiwd.stats.eawwybiwdseawchewstats;

/**
 * cweated with intewwij idea. (U ﹏ U)
 * date: 6/20/12
 * t-time: 12:06 pm
 * to change this tempwate u-use fiwe | settings | fiwe tempwates. (U ﹏ U)
 */
p-pubwic cwass sociawseawchwesuwtscowwectow extends seawchwesuwtscowwectow {

  pwivate f-finaw sociawfiwtew sociawfiwtew;

  p-pubwic sociawseawchwesuwtscowwectow(
      i-immutabweschemaintewface schema, (⑅˘꒳˘)
      seawchwequestinfo seawchwequestinfo, òωó
      sociawfiwtew sociawfiwtew, ʘwʘ
      e-eawwybiwdseawchewstats seawchewstats, /(^•ω•^)
      eawwybiwdcwustew cwustew, ʘwʘ
      usewtabwe usewtabwe, σωσ
      int wequestdebugmode) {
    s-supew(schema, OwO seawchwequestinfo, 😳😳😳 c-cwock.system_cwock, 😳😳😳 s-seawchewstats, o.O c-cwustew, ( ͡o ω ͡o ) u-usewtabwe, (U ﹏ U)
        wequestdebugmode);
    this.sociawfiwtew = s-sociawfiwtew;
  }

  @ovewwide
  pubwic finaw void docowwect(wong t-tweetid) thwows ioexception {
    if (sociawfiwtew == nyuww || sociawfiwtew.accept(cuwdocid)) {
      wesuwts.add(new h-hit(cuwwtimeswiceid, (///ˬ///✿) tweetid));
    }
  }

  @ovewwide
  p-pubwic void stawtsegment() t-thwows i-ioexception {
    if (sociawfiwtew != nyuww) {
      sociawfiwtew.stawtsegment(cuwwtwittewweadew);
    }
  }
}
