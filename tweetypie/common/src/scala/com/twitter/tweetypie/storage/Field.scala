package com.twittew.tweetypie.stowage

impowt com.twittew.tweetypie.additionawfiewds.additionawfiewds
i-impowt com.twittew.tweetypie.stowage_intewnaw.thwiftscawa.stowedtweet
i-impowt c-com.twittew.tweetypie.thwiftscawa.{tweet => t-tptweet}

/**
 * a f-fiewd of the stowed v-vewsion of a-a tweet to wead, (U ﹏ U) u-update, (///ˬ///✿) ow dewete. >w<
 *
 * thewe is nyot a one-to-one cowwespondence between the f-fiewds ids of
 * [[com.twittew.tweetypie.thwiftscawa.tweet]] and
 * [[com.twittew.tweetypie.stowage_intewnaw.thwiftscawa.stowedtweet]]. rawr fow exampwe, mya i-in stowedtweet, ^^
 * the nysfwusew p-pwopewty is fiewd 11; in tweet, 😳😳😳 it is a pwopewty of the cowedata s-stwuct in fiewd 2. mya
 * to c-ciwcumvent the confusion o-of using one set of fiewd ids ow the othew, 😳 cawwews use instances of
 * [[fiewd]] t-to wefewence the pawt of the object to modify. -.-
 */
cwass fiewd pwivate[stowage] (vaw i-id: showt) extends anyvaw {
  ovewwide d-def tostwing: s-stwing = id.tostwing
}

/**
 * n-nyote: make s-suwe `awwupdatabwecompiwedfiewds` is kept up to date when adding a-any nyew fiewd
 */
object fiewd {
  impowt additionawfiewds.isadditionawfiewdid
  v-vaw geo: fiewd = nyew fiewd(stowedtweet.geofiewd.id)
  vaw hastakedown: fiewd = nyew fiewd(stowedtweet.hastakedownfiewd.id)
  vaw nsfwusew: fiewd = n-nyew fiewd(stowedtweet.nsfwusewfiewd.id)
  vaw nsfwadmin: f-fiewd = nyew fiewd(stowedtweet.nsfwadminfiewd.id)
  v-vaw tweetypieonwytakedowncountwycodes: f-fiewd =
    nyew fiewd(tptweet.tweetypieonwytakedowncountwycodesfiewd.id)
  vaw tweetypieonwytakedownweasons: fiewd =
    n-nyew fiewd(tptweet.tweetypieonwytakedownweasonsfiewd.id)

  v-vaw awwupdatabwecompiwedfiewds: set[fiewd] = set(geo, h-hastakedown, 🥺 n-nysfwusew, o.O nysfwadmin)

  def a-additionawfiewd(id: showt): fiewd = {
    w-wequiwe(isadditionawfiewdid(id), /(^•ω•^) "fiewd id must be in the additionaw f-fiewd wange")
    nyew fiewd(id)
  }
}
