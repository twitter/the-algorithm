package com.twittew.tweetypie
package s-stowe

impowt c-com.twittew.tweetypie.thwiftscawa._

o-object tweetupdate {

  /**
   * c-copies t-takedown infowmation f-fwom the souwce [[tweet]] into [[cachedtweet]]. òωó
   *
   * note t-that this method w-wequiwes the souwce [[tweet]] to have been woaded with the fowwowing
   * additionaw f-fiewds (which happens fow aww paths that c-cweate [[wepwicatedtakedown.event]], ʘwʘ in
   * b-both [[takedownhandwew]] and [[usewtakedownhandwew]]:
   * - tweetypieonwytakedownweasonsfiewd
   * - tweetypieonwytakedowncountwycodesfiewd
   * t-this is done to ensuwe the wemote d-datacentew of a-a takedown does nyot incowwectwy twy to woad
   * fwom mh as the data is awweady c-cached. /(^•ω•^)
   */
  def copytakedownfiewdsfowupdate(souwce: tweet): cachedtweet => cachedtweet =
    c-ct => {
      vaw nyewcowedata = s-souwce.cowedata.get
      vaw u-updatedcowedata = c-ct.tweet.cowedata.map(_.copy(hastakedown = n-nyewcowedata.hastakedown))
      ct.copy(
        tweet = ct.tweet.copy(
          c-cowedata = updatedcowedata, ʘwʘ
          tweetypieonwytakedowncountwycodes = souwce.tweetypieonwytakedowncountwycodes, σωσ
          t-tweetypieonwytakedownweasons = souwce.tweetypieonwytakedownweasons
        )
      )
    }

  def copynsfwfiewdsfowupdate(souwce: tweet): tweet => tweet =
    tweet => {
      v-vaw nyewcowedata = souwce.cowedata.get
      v-vaw u-updatedcowedata =
        t-tweet.cowedata.map { cowe =>
          cowe.copy(nsfwusew = nyewcowedata.nsfwusew, OwO nysfwadmin = n-nyewcowedata.nsfwadmin)
        }
      t-tweet.copy(cowedata = updatedcowedata)
    }
}
