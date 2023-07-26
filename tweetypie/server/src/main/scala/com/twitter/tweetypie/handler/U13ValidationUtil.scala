package com.twittew.tweetypie.handwew

impowt com.twittew.compwiance.usewconsent.compwiance.biwthdate.gwobawbiwthdateutiw
i-impowt c-com.twittew.gizmoduck.thwiftscawa.usew
i-impowt com.twittew.tweetypie.thwiftscawa.dewetedtweet
i-impowt o-owg.joda.time.datetime

/*
 * a-as pawt of gdpw u-u13 wowk, XD we want t-to bwock tweets cweated fwom when a usew
 * was < 13 fwom being westowed. :3
 */

p-pwivate[handwew] object u13vawidationutiw {
  def wastweetcweatedbefoweusewtuwned13(usew: u-usew, 😳😳😳 dewetedtweet: d-dewetedtweet): boowean =
    dewetedtweet.cweatedatsecs match {
      case nyone =>
        t-thwow nyocweatedattimeexception
      c-case some(cweatedatsecs) =>
        g-gwobawbiwthdateutiw.isundewsomeage(13, -.- nyew datetime(cweatedatsecs * 1000w), usew)
    }
}
