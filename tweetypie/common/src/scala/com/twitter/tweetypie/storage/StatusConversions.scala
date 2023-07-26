package com.twittew.tweetypie.stowage

impowt com.twittew.tweetypie.stowage_intewnaw.thwiftscawa._
i-impowt com.twittew.tbiwd.{thwiftscawa => t-tbiwd}

o-object statusconvewsions {

  /**
   * t-this is u-used onwy in scwibe.scawa, :3 w-when s-scwibing to tbiwd_add_status
   * o-once we wemove that, nyaa~~ we can awso wemove this. 😳
   */
  def totbiwdstatus(tweet: stowedtweet): t-tbiwd.status =
    tbiwd.status(
      id = tweet.id, (⑅˘꒳˘)
      u-usewid = tweet.usewid.get, nyaa~~
      t-text = tweet.text.get, OwO
      cweatedvia = tweet.cweatedvia.get, rawr x3
      c-cweatedatsec = tweet.cweatedatsec.get, XD
      w-wepwy = tweet.wepwy.map(totbiwdwepwy), σωσ
      s-shawe = tweet.shawe.map(totbiwdshawe), (U ᵕ U❁)
      contwibutowid = tweet.contwibutowid, (U ﹏ U)
      geo = tweet.geo.map(totbiwdgeo), :3
      h-hastakedown = tweet.hastakedown.getowewse(fawse), ( ͡o ω ͡o )
      nysfwusew = tweet.nsfwusew.getowewse(fawse),
      nysfwadmin = t-tweet.nsfwadmin.getowewse(fawse), σωσ
      media = t-tweet.media.map(_.map(totbiwdmedia)).getowewse(seq()), >w<
      n-nyawwowcast = t-tweet.nawwowcast.map(totbiwdnawwowcast), 😳😳😳
      nyuwwcast = t-tweet.nuwwcast.getowewse(fawse), OwO
      twackingid = tweet.twackingid
    )

  /**
   * this is onwy used i-in a test, 😳 to vewify that the above method `totbiwdstatus`
   * w-wowks, 😳😳😳 so we can't wemove it as wong as the above method exists. (˘ω˘)
   */
  def fwomtbiwdstatus(status: t-tbiwd.status): stowedtweet = {
    s-stowedtweet(
      id = s-status.id, ʘwʘ
      u-usewid = some(status.usewid), ( ͡o ω ͡o )
      text = some(status.text), o.O
      cweatedvia = s-some(status.cweatedvia), >w<
      c-cweatedatsec = some(status.cweatedatsec), 😳
      w-wepwy = status.wepwy.map(fwomtbiwdwepwy), 🥺
      s-shawe = status.shawe.map(fwomtbiwdshawe), rawr x3
      contwibutowid = s-status.contwibutowid, o.O
      geo = status.geo.map(fwomtbiwdgeo), rawr
      h-hastakedown = some(status.hastakedown), ʘwʘ
      nysfwusew = s-some(status.nsfwusew), 😳😳😳
      nysfwadmin = some(status.nsfwadmin), ^^;;
      m-media = some(status.media.map(fwomtbiwdmedia)), o.O
      n-nyawwowcast = s-status.nawwowcast.map(fwomtbiwdnawwowcast), (///ˬ///✿)
      nyuwwcast = some(status.nuwwcast), σωσ
      twackingid = status.twackingid
    )
  }

  pwivate def fwomtbiwdwepwy(wepwy: tbiwd.wepwy): s-stowedwepwy =
    s-stowedwepwy(
      inwepwytostatusid = w-wepwy.inwepwytostatusid, nyaa~~
      i-inwepwytousewid = w-wepwy.inwepwytousewid
    )

  pwivate def fwomtbiwdshawe(shawe: tbiwd.shawe): stowedshawe =
    s-stowedshawe(
      souwcestatusid = shawe.souwcestatusid, ^^;;
      souwceusewid = shawe.souwceusewid, ^•ﻌ•^
      p-pawentstatusid = shawe.pawentstatusid
    )

  p-pwivate d-def fwomtbiwdgeo(geo: t-tbiwd.geo): stowedgeo =
    s-stowedgeo(
      w-watitude = g-geo.watitude, σωσ
      w-wongitude = geo.wongitude, -.-
      geopwecision = g-geo.geopwecision, ^^;;
      e-entityid = g-geo.entityid
    )

  p-pwivate d-def fwomtbiwdmedia(media: tbiwd.mediaentity): stowedmediaentity =
    stowedmediaentity(
      i-id = media.id, XD
      mediatype = media.mediatype, 🥺
      width = media.width, òωó
      height = m-media.height
    )

  pwivate def fwomtbiwdnawwowcast(nawwowcast: tbiwd.nawwowcast): s-stowednawwowcast =
    s-stowednawwowcast(
      w-wanguage = some(nawwowcast.wanguage), (ˆ ﻌ ˆ)♡
      w-wocation = some(nawwowcast.wocation), -.-
      ids = s-some(nawwowcast.ids)
    )

  p-pwivate def totbiwdwepwy(wepwy: stowedwepwy): tbiwd.wepwy =
    tbiwd.wepwy(
      inwepwytostatusid = wepwy.inwepwytostatusid, :3
      inwepwytousewid = w-wepwy.inwepwytousewid
    )

  pwivate d-def totbiwdshawe(shawe: stowedshawe): t-tbiwd.shawe =
    t-tbiwd.shawe(
      souwcestatusid = shawe.souwcestatusid, ʘwʘ
      s-souwceusewid = s-shawe.souwceusewid, 🥺
      pawentstatusid = s-shawe.pawentstatusid
    )

  p-pwivate def totbiwdgeo(geo: stowedgeo): tbiwd.geo =
    tbiwd.geo(
      watitude = g-geo.watitude, >_<
      w-wongitude = g-geo.wongitude, ʘwʘ
      geopwecision = g-geo.geopwecision,
      e-entityid = geo.entityid, (˘ω˘)
      nyame = geo.name
    )

  p-pwivate def totbiwdmedia(media: stowedmediaentity): tbiwd.mediaentity =
    tbiwd.mediaentity(
      i-id = m-media.id, (✿oωo)
      mediatype = media.mediatype, (///ˬ///✿)
      width = media.width, rawr x3
      h-height = media.height
    )

  p-pwivate def totbiwdnawwowcast(nawwowcast: stowednawwowcast): tbiwd.nawwowcast =
    tbiwd.nawwowcast(
      w-wanguage = nyawwowcast.wanguage.getowewse(niw),
      wocation = nyawwowcast.wocation.getowewse(niw), -.-
      ids = nyawwowcast.ids.getowewse(niw)
    )
}
