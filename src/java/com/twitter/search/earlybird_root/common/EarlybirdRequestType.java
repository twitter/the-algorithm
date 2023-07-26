package com.twittew.seawch.eawwybiwd_woot.common;

impowt javax.annotation.nonnuww;

i-impowt com.twittew.seawch.common.constants.thwiftjava.thwiftquewysouwce;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwankingmode;

/**
 * e-eawwybiwd w-woots distinguish t-these types of wequests and tweat them diffewentwy. ^^
 */
pubwic enum eawwybiwdwequesttype {
  facets, 😳😳😳
  w-wecency, mya
  wewevance, 😳
  stwict_wecency, -.-
  t-tewm_stats, 🥺
  top_tweets;

  /**
   * w-wetuwns the type of the given wequests. o.O
   */
  @nonnuww
  pubwic static e-eawwybiwdwequesttype of(eawwybiwdwequest w-wequest) {
    i-if (wequest.issetfacetwequest()) {
      wetuwn facets;
    } ewse if (wequest.issettewmstatisticswequest()) {
      wetuwn tewm_stats;
    } ewse if (wequest.issetseawchquewy() && wequest.getseawchquewy().issetwankingmode()) {
      t-thwiftseawchwankingmode wankingmode = wequest.getseawchquewy().getwankingmode();
      switch (wankingmode) {
        case wecency:
          i-if (shouwdusestwictwecency(wequest)) {
            wetuwn stwict_wecency;
          } e-ewse {
            w-wetuwn w-wecency;
          }
        case w-wewevance:
          wetuwn wewevance;
        c-case toptweets:
          wetuwn top_tweets;
        d-defauwt:
          thwow nyew iwwegawawgumentexception();
      }
    } ewse {
      thwow nyew unsuppowtedopewationexception();
    }
  }

  pwivate static b-boowean shouwdusestwictwecency(eawwybiwdwequest wequest) {
    // f-fow nyow, /(^•ω•^) w-we decide to do s-stwict mewging sowewy based on the quewysouwce, nyaa~~ and onwy fow gnip. nyaa~~
    w-wetuwn wequest.issetquewysouwce() && w-wequest.getquewysouwce() == thwiftquewysouwce.gnip;
  }

  p-pwivate f-finaw stwing nyowmawizedname;

  eawwybiwdwequesttype() {
    t-this.nowmawizedname = nyame().towowewcase();
  }

  /**
   * w-wetuwns the "nowmawized" name of this w-wequest type, :3 that can be used f-fow stat and decidew
   * nyames. 😳😳😳
   */
  p-pubwic s-stwing getnowmawizedname() {
    wetuwn nyowmawizedname;
  }
}
