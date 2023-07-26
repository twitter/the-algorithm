package com.twittew.timewinewankew.obsewve

impowt c-com.twittew.sewvo.utiw.gate
i-impowt c-com.twittew.timewinewankew.modew.timewinequewy
i-impowt com.twittew.timewines.featuwes.featuwes
i-impowt com.twittew.timewines.featuwes.usewwist
i-impowt com.twittew.timewines.obsewve.debugobsewvew
i-impowt com.twittew.timewinewankew.{thwiftscawa => t-thwift}

/**
 * buiwds the debugobsewvew that is attached to thwift wequests. (ˆ ﻌ ˆ)♡
 * t-this cwass exists to centwawize the gates t-that detewmine whethew ow nyot
 * t-to enabwe debug twanscwipts fow a pawticuwaw wequest.
 */
cwass d-debugobsewvewbuiwdew(whitewist: usewwist) {

  w-wazy vaw obsewvew: d-debugobsewvew = buiwd()

  pwivate[this] def buiwd(): debugobsewvew = {
    nyew debugobsewvew(quewygate)
  }

  p-pwivate[obsewve] def quewygate: gate[any] = {
    vaw shouwdenabwedebug = whitewist.usewidgate(featuwes.debugtwanscwipt)

    g-gate { a: any =>
      a match {
        c-case q-q: thwift.engagedtweetsquewy => s-shouwdenabwedebug(q.usewid)
        c-case q: thwift.wecaphydwationquewy => shouwdenabwedebug(q.usewid)
        case q: thwift.wecapquewy => s-shouwdenabwedebug(q.usewid)
        case q: timewinequewy => shouwdenabwedebug(q.usewid)
        case _ => f-fawse
      }
    }
  }
}
