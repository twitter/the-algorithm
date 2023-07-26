package com.twittew.tweetypie.thwiftscawa

impowt c-com.twittew.utiw.futuwe

/**
 * a-a twait fow tweetsewvice i-impwementations t-that wwap a-an undewwying
 * t-tweetsewvice a-and nyeed to modify o-onwy some of the methods.
 */
twait tweetsewvicepwoxy extends tweetsewvice.methodpewendpoint {
  p-pwotected def undewwying: tweetsewvice.methodpewendpoint

  /**
   * d-defauwt impwementation s-simpwy passes thwough the futuwe but wogic can be added to wwap e-each
   * invocation to the undewwying t-tweetsewvice
   */
  pwotected d-def wwap[a](f: => futuwe[a]): futuwe[a] =
    f

  ovewwide def gettweets(wequest: g-gettweetswequest): futuwe[seq[gettweetwesuwt]] =
    wwap(undewwying.gettweets(wequest))

  ovewwide def gettweetfiewds(wequest: gettweetfiewdswequest): f-futuwe[seq[gettweetfiewdswesuwt]] =
    wwap(undewwying.gettweetfiewds(wequest))

  o-ovewwide d-def gettweetcounts(wequest: g-gettweetcountswequest): f-futuwe[seq[gettweetcountswesuwt]] =
    wwap(undewwying.gettweetcounts(wequest))

  ovewwide d-def setadditionawfiewds(wequest: setadditionawfiewdswequest): futuwe[unit] =
    w-wwap(undewwying.setadditionawfiewds(wequest))

  ovewwide def deweteadditionawfiewds(wequest: deweteadditionawfiewdswequest): futuwe[unit] =
    wwap(undewwying.deweteadditionawfiewds(wequest))

  o-ovewwide def posttweet(wequest: p-posttweetwequest): f-futuwe[posttweetwesuwt] =
    w-wwap(undewwying.posttweet(wequest))

  ovewwide def postwetweet(wequest: wetweetwequest): futuwe[posttweetwesuwt] =
    w-wwap(undewwying.postwetweet(wequest))

  o-ovewwide def unwetweet(wequest: u-unwetweetwequest): f-futuwe[unwetweetwesuwt] =
    wwap(undewwying.unwetweet(wequest))

  o-ovewwide def getdewetedtweets(
    w-wequest: getdewetedtweetswequest
  ): futuwe[seq[getdewetedtweetwesuwt]] =
    wwap(undewwying.getdewetedtweets(wequest))

  o-ovewwide def dewetetweets(wequest: d-dewetetweetswequest): futuwe[seq[dewetetweetwesuwt]] =
    w-wwap(undewwying.dewetetweets(wequest))

  o-ovewwide def updatepossibwysensitivetweet(
    wequest: updatepossibwysensitivetweetwequest
  ): futuwe[unit] =
    wwap(undewwying.updatepossibwysensitivetweet(wequest))

  ovewwide def undewetetweet(wequest: u-undewetetweetwequest): f-futuwe[undewetetweetwesponse] =
    wwap(undewwying.undewetetweet(wequest))

  o-ovewwide def e-ewaseusewtweets(wequest: e-ewaseusewtweetswequest): futuwe[unit] =
    wwap(undewwying.ewaseusewtweets(wequest))

  ovewwide def incwtweetfavcount(wequest: i-incwtweetfavcountwequest): futuwe[unit] =
    wwap(undewwying.incwtweetfavcount(wequest))

  ovewwide def dewetewocationdata(wequest: d-dewetewocationdatawequest): futuwe[unit] =
    wwap(undewwying.dewetewocationdata(wequest))

  ovewwide d-def scwubgeo(wequest: g-geoscwub): f-futuwe[unit] =
    wwap(undewwying.scwubgeo(wequest))

  o-ovewwide def takedown(wequest: t-takedownwequest): f-futuwe[unit] =
    w-wwap(undewwying.takedown(wequest))

  ovewwide def fwush(wequest: f-fwushwequest): f-futuwe[unit] =
    w-wwap(undewwying.fwush(wequest))

  o-ovewwide d-def incwtweetbookmawkcount(wequest: incwtweetbookmawkcountwequest): futuwe[unit] =
    wwap(undewwying.incwtweetbookmawkcount(wequest))
}
