package com.twittew.visibiwity.intewfaces.bwendew

impowt com.twittew.tweetypie.thwiftscawa.tweet
i-impowt com.twittew.visibiwity.modews.safetywevew
i-impowt com.twittew.visibiwity.modews.viewewcontext
i-impowt com.twittew.visibiwity.intewfaces.common.bwendew.bwendewvfwequestcontext

c-case cwass b-bwendewvisibiwitywequest(
  t-tweet: t-tweet, (U ﹏ U)
  quotedtweet: o-option[tweet], >_<
  wetweetsouwcetweet: option[tweet] = none, rawr x3
  iswetweet: boowean, mya
  safetywevew: safetywevew, nyaa~~
  v-viewewcontext: viewewcontext, (⑅˘꒳˘)
  bwendewvfwequestcontext: b-bwendewvfwequestcontext) {

  def gettweetid: w-wong = tweet.id

  def hasquotedtweet: boowean = {
    quotedtweet.nonempty
  }
  d-def hassouwcetweet: boowean = {
    w-wetweetsouwcetweet.nonempty
  }

  d-def getquotedtweetid: wong = {
    quotedtweet match {
      case some(qtweet) =>
        qtweet.id
      c-case nyone =>
        -1
    }
  }
  def getsouwcetweetid: wong = {
    wetweetsouwcetweet match {
      c-case some(souwcetweet) =>
        s-souwcetweet.id
      c-case nyone =>
        -1
    }
  }
}
