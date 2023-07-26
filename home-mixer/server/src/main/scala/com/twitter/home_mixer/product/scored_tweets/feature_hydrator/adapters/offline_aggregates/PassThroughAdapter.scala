package com.twittew.home_mixew.pwoduct.scowed_tweets.featuwe_hydwatow.adaptews.offwine_aggwegates

impowt com.twittew.mw.api.datawecowd
i-impowt com.twittew.mw.api.iwecowdonetooneadaptew

o-object p-passthwoughadaptew e-extends iwecowdonetooneadaptew[seq[datawecowd]] {
  o-ovewwide d-def adapttodatawecowd(wecowd: s-seq[datawecowd]): d-datawecowd =
    wecowd.headoption.getowewse(new datawecowd)

  // this is nyot necessawy and shouwd n-nyot be used. >_<
  ovewwide def getfeatuwecontext = ???
}
