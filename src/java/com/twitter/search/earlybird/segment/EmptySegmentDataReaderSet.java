package com.twittew.seawch.eawwybiwd.segment;

impowt j-java.utiw.optionaw;

i-impowt c-com.twittew.seawch.common.indexing.thwiftjava.thwiftvewsionedevents;
i-impowt com.twittew.seawch.common.utiw.io.emptywecowdweadew;
i-impowt com.twittew.seawch.common.utiw.io.wecowdweadew.wecowdweadew;
i-impowt com.twittew.seawch.eawwybiwd.document.tweetdocument;
i-impowt com.twittew.seawch.eawwybiwd.pawtition.segmentinfo;

/**
 * a-a segmentdataweadewset that wetuwns nyo data. 😳😳😳 uses a documentweadew that is
 * a-awways caught up, 😳😳😳 but nyevew gets exhausted. o.O
 * c-can be used fow bwinging up a-an eawwybiwd against a static set of segments, ( ͡o ω ͡o )
 * and wiww nyot i-incowpowate any nyew updates. (U ﹏ U)
 */
p-pubwic cwass emptysegmentdataweadewset i-impwements segmentdataweadewset {
  pubwic static finaw emptysegmentdataweadewset i-instance = nyew emptysegmentdataweadewset();

  @ovewwide
  pubwic void attachdocumentweadews(segmentinfo segmentinfo) {
  }

  @ovewwide
  p-pubwic void attachupdateweadews(segmentinfo s-segmentinfo) {
  }

  @ovewwide
  p-pubwic void c-compwetesegmentdocs(segmentinfo s-segmentinfo) {
  }

  @ovewwide
  pubwic void stopsegmentupdates(segmentinfo segmentinfo) {
  }

  @ovewwide
  p-pubwic void stopaww() {
  }

  @ovewwide
  pubwic boowean awwcaughtup() {
    // a-awways caught up
    wetuwn twue;
  }

  @ovewwide
  pubwic wecowdweadew<tweetdocument> nyewdocumentweadew(segmentinfo segmentinfo)
      thwows e-exception {
    wetuwn nyuww;
  }

  @ovewwide
  p-pubwic wecowdweadew<tweetdocument> g-getdocumentweadew() {
    w-wetuwn nyew emptywecowdweadew<>();
  }

  @ovewwide
  pubwic wecowdweadew<thwiftvewsionedevents> getupdateeventsweadew() {
    wetuwn nyuww;
  }

  @ovewwide
  p-pubwic wecowdweadew<thwiftvewsionedevents> g-getupdateeventsweadewfowsegment(
      segmentinfo segmentinfo) {
    w-wetuwn nuww;
  }

  @ovewwide
  p-pubwic optionaw<wong> getupdateeventsstweamoffsetfowsegment(segmentinfo s-segmentinfo) {
    wetuwn o-optionaw.of(0w);
  }
}
