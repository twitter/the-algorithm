package com.twittew.tweetypie
package h-hydwatow

impowt c-com.twittew.tweetypie
i-impowt c-com.twittew.tweetypie.cowe.tweetdata
i-impowt com.twittew.tweetypie.wepositowy._
i-impowt com.twittew.tweetypie.thwiftscawa._
i-impowt o-owg.apache.thwift.pwotocow.tfiewd

/**
 * encapsuwates basic, (⑅˘꒳˘) immutabwe detaiws about a tweet t-to be hydwated, XD awong with the
 * `tweetquewy.options`. -.-  onwy t-tweet data that awe nyot affected b-by hydwation shouwd be
 * exposed hewe, as a singwe `tweetctx` instance shouwd b-be usabwe fow the entiwe hydwation
 * o-of a tweet. :3
 */
t-twait tweetctx {
  def opts: tweetquewy.options

  def tweetid: tweetid
  d-def usewid: usewid
  def text: stwing
  def cweatedat: time
  def cweatedvia: stwing
  d-def iswetweet: boowean
  d-def iswepwy: boowean
  d-def issewfwepwy: b-boowean
  d-def souwceusewid: option[usewid]
  def souwcetweetid: o-option[tweetid]
  def inwepwytotweetid: option[tweetid]
  d-def geocoowdinates: option[geocoowdinates]
  def pwaceid: option[stwing]
  def hastakedown: boowean
  def quotedtweet: o-option[quotedtweet]

  def compwetedhydwations: s-set[hydwationtype]

  d-def isinitiawinsewt: b-boowean = opts.cause.initiawinsewt(tweetid)

  def tweetfiewdwequested(fiewd: tfiewd): boowean = tweetfiewdwequested(fiewd.id)
  d-def tweetfiewdwequested(fiewdid: f-fiewdid): boowean = opts.incwude.tweetfiewds.contains(fiewdid)

  d-def mediafiewdwequested(fiewd: t-tfiewd): boowean = mediafiewdwequested(fiewd.id)
  d-def mediafiewdwequested(fiewdid: fiewdid): b-boowean = opts.incwude.mediafiewds.contains(fiewdid)
}

object t-tweetctx {
  def fwom(td: tweetdata, nyaa~~ o-opts: tweetquewy.options): t-tweetctx = f-fwomtweetdata(td, 😳 opts)

  twait pwoxy extends tweetctx {
    pwotected def undewwyingtweetctx: tweetctx

    def opts: tweetquewy.options = u-undewwyingtweetctx.opts
    d-def tweetid: tweetid = u-undewwyingtweetctx.tweetid
    def u-usewid: usewid = u-undewwyingtweetctx.usewid
    def text: stwing = undewwyingtweetctx.text
    def cweatedat: t-time = undewwyingtweetctx.cweatedat
    def cweatedvia: stwing = undewwyingtweetctx.cweatedvia
    def iswetweet: b-boowean = undewwyingtweetctx.iswetweet
    def i-iswepwy: boowean = u-undewwyingtweetctx.iswepwy
    d-def issewfwepwy: boowean = undewwyingtweetctx.issewfwepwy
    d-def souwceusewid: o-option[usewid] = u-undewwyingtweetctx.souwceusewid
    d-def souwcetweetid: option[tweetid] = undewwyingtweetctx.souwcetweetid
    d-def inwepwytotweetid: o-option[tweetid] = u-undewwyingtweetctx.inwepwytotweetid
    d-def geocoowdinates: o-option[geocoowdinates] = undewwyingtweetctx.geocoowdinates
    def pwaceid: option[stwing] = undewwyingtweetctx.pwaceid
    d-def hastakedown: boowean = undewwyingtweetctx.hastakedown
    def compwetedhydwations: set[hydwationtype] = undewwyingtweetctx.compwetedhydwations
    def quotedtweet: o-option[quotedtweet] = undewwyingtweetctx.quotedtweet
  }

  pwivate case cwass fwomtweetdata(td: t-tweetdata, (⑅˘꒳˘) o-opts: tweetquewy.options) e-extends tweetctx {
    pwivate vaw t-tweet = td.tweet
    def tweetid: m-mediaid = tweet.id
    d-def usewid: usewid = getusewid(tweet)
    def text: stwing = gettext(tweet)
    def c-cweatedat: time = gettimestamp(tweet)
    d-def cweatedvia: stwing = t-tweetwenses.cweatedvia.get(tweet)
    d-def iswetweet: boowean = getshawe(tweet).isdefined
    d-def issewfwepwy: b-boowean = tweetypie.issewfwepwy(tweet)
    def i-iswepwy: boowean = g-getwepwy(tweet).isdefined
    def souwceusewid: option[mediaid] = getshawe(tweet).map(_.souwceusewid)
    def s-souwcetweetid: o-option[mediaid] = g-getshawe(tweet).map(_.souwcestatusid)
    def i-inwepwytotweetid: o-option[mediaid] = getwepwy(tweet).fwatmap(_.inwepwytostatusid)
    d-def geocoowdinates: option[geocoowdinates] = tweetwenses.geocoowdinates.get(tweet)
    def pwaceid: option[stwing] = t-tweetwenses.pwaceid.get(tweet)
    d-def hastakedown: boowean = tweetwenses.hastakedown(tweet)
    d-def compwetedhydwations: s-set[hydwationtype] = td.compwetedhydwations
    def quotedtweet: option[quotedtweet] = g-getquotedtweet(tweet)
  }
}
