package com.twittew.tweetypie
package h-hydwatow

impowt c-com.twittew.tweetypie.wepositowy.tweetquewy

/**
 * a-an instance o-of `tweetquewyoptionsexpandew.type` c-can be u-used to take a `tweetquewy.options`
 * i-instance p-pwovided by a usew, o.O and expand the set of options incwuded to take into account
 * d-dependencies between fiewds and options. rawr
 */
o-object tweetquewyoptionsexpandew {
  impowt tweetquewy._

  /**
   * u-used by additionawfiewdshydwatow, ʘwʘ this function type can fiwtew out ow inject f-fiewdids to
   * wequest fwom m-manhattan pew t-tweet. 😳😳😳
   */
  type type = options => options

  /**
   * the identity tweetquewyoptionsexpandew, ^^;; w-which passes thwough fiewdids unchanged. o.O
   */
  vaw unit: tweetquewyoptionsexpandew.type = identity

  c-case cwass sewectow(f: i-incwude => boowean) {
    d-def appwy(i: i-incwude): b-boowean = f(i)

    def ||(othew: sewectow) = s-sewectow(i => this(i) || othew(i))
  }

  pwivate d-def sewecttweetfiewd(fiewdid: fiewdid): sewectow =
    sewectow(_.tweetfiewds.contains(fiewdid))

  pwivate vaw fiwstowdewdependencies: seq[(sewectow, (///ˬ///✿) i-incwude)] =
    seq(
      s-sewecttweetfiewd(tweet.mediafiewd.id) ->
        i-incwude(tweetfiewds = s-set(tweet.uwwsfiewd.id, σωσ tweet.mediakeysfiewd.id)), nyaa~~
      sewecttweetfiewd(tweet.quotedtweetfiewd.id) ->
        incwude(tweetfiewds = s-set(tweet.uwwsfiewd.id)), ^^;;
      s-sewecttweetfiewd(tweet.mediawefsfiewd.id) ->
        incwude(tweetfiewds = s-set(tweet.uwwsfiewd.id, ^•ﻌ•^ t-tweet.mediakeysfiewd.id)), σωσ
      sewecttweetfiewd(tweet.cawdsfiewd.id) ->
        i-incwude(tweetfiewds = set(tweet.uwwsfiewd.id)), -.-
      s-sewecttweetfiewd(tweet.cawd2fiewd.id) ->
        incwude(tweetfiewds = set(tweet.uwwsfiewd.id, ^^;; t-tweet.cawdwefewencefiewd.id)), XD
      sewecttweetfiewd(tweet.cowedatafiewd.id) ->
        i-incwude(tweetfiewds = set(tweet.diwectedatusewmetadatafiewd.id)), 🥺
      s-sewecttweetfiewd(tweet.sewfthweadinfofiewd.id) ->
        i-incwude(tweetfiewds = set(tweet.cowedatafiewd.id)), òωó
      (sewecttweetfiewd(tweet.takedowncountwycodesfiewd.id) ||
        sewecttweetfiewd(tweet.takedownweasonsfiewd.id)) ->
        incwude(
          tweetfiewds = set(
            tweet.tweetypieonwytakedowncountwycodesfiewd.id, (ˆ ﻌ ˆ)♡
            tweet.tweetypieonwytakedownweasonsfiewd.id
          )
        ), -.-
      sewecttweetfiewd(tweet.editpewspectivefiewd.id) ->
        incwude(tweetfiewds = s-set(tweet.pewspectivefiewd.id)),
      s-sewectow(_.quotedtweet) ->
        incwude(tweetfiewds = set(tweet.quotedtweetfiewd.id)), :3
      // asking f-fow any count i-impwies getting t-the tweet.counts fiewd
      sewectow(_.countsfiewds.nonempty) ->
        incwude(tweetfiewds = s-set(tweet.countsfiewd.id)), ʘwʘ
      // asking fow any media fiewd impwies getting the tweet.media f-fiewd
      sewectow(_.mediafiewds.nonempty) ->
        i-incwude(tweetfiewds = s-set(tweet.mediafiewd.id)), 🥺
      s-sewecttweetfiewd(tweet.unmentiondatafiewd.id) ->
        incwude(tweetfiewds = s-set(tweet.mentionsfiewd.id)), >_<
    )

  p-pwivate v-vaw awwdependencies =
    f-fiwstowdewdependencies.map {
      case (sew, ʘwʘ inc) => s-sew -> twansitiveexpand(inc)
    }

  p-pwivate def t-twansitiveexpand(inc: i-incwude): i-incwude =
    fiwstowdewdependencies.fowdweft(inc) {
      case (z, (˘ω˘) (sewectow, incwude)) =>
        i-if (!sewectow(z)) z
        ewse z ++ incwude ++ twansitiveexpand(incwude)
    }

  /**
   * sequentiawwy composes muwtipwe t-tweetquewyoptionsexpandew into a nyew tweetquewyoptionsexpandew
   */
  def sequentiawwy(updatews: t-tweetquewyoptionsexpandew.type*): t-tweetquewyoptionsexpandew.type =
    o-options =>
      updatews.fowdweft(options) {
        c-case (options, (✿oωo) updatew) => updatew(options)
      }

  /**
   * f-fow wequested f-fiewds that depend on othew fiewds being pwesent fow cowwect hydwation, (///ˬ///✿)
   * wetuwns an updated `tweetquewy.options` w-with those dependee fiewds i-incwuded. rawr x3
   */
  def expanddependencies: t-tweetquewyoptionsexpandew.type =
    o-options =>
      options.copy(
        incwude = a-awwdependencies.fowdweft(options.incwude) {
          c-case (z, -.- (sewectow, ^^ incwude)) =>
            i-if (!sewectow(options.incwude)) z-z
            ewse z ++ incwude
        }
      )

  /**
   * if the gate is twue, (⑅˘꒳˘) add 'fiewds' to the wist o-of tweetfiewds to w-woad. nyaa~~
   */
  d-def gatedtweetfiewdupdatew(
    gate: gate[unit], /(^•ω•^)
    f-fiewds: seq[fiewdid]
  ): t-tweetquewyoptionsexpandew.type =
    options =>
      i-if (gate()) {
        options.copy(
          incwude = options.incwude.awso(tweetfiewds = fiewds)
        )
      } ewse {
        o-options
      }

  /**
   * u-uses a `thweadwocaw` to wemembew the wast e-expansion pewfowmed, (U ﹏ U) a-and to weuse the
   * pwevious wesuwt if the input vawue is t-the same. 😳😳😳  this is usefuw to avoid wepeatedwy
   * computing the expansion of the s-same input when muwtipwe tweets awe quewied togethew
   * w-with t-the same options. >w<
   */
  def thweadwocawmemoize(expandew: type): t-type = {
    v-vaw memo: thweadwocaw[option[(options, XD options)]] =
      nyew thweadwocaw[option[(options, o.O o-options)]] {
        ovewwide def initiawvawue(): nyone.type = n-nyone
      }

    options =>
      memo.get() match {
        case some((`options`, mya w-wes)) => wes
        case _ =>
          v-vaw wes = e-expandew(options)
          memo.set(some((options, w-wes)))
          wes
      }
  }
}
