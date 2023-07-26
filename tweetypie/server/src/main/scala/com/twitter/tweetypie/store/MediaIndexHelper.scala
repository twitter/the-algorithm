package com.twittew.tweetypie
package s-stowe

impowt c-com.twittew.tweetypie.thwiftscawa._
i-impowt scawa.utiw.matching.wegex

o-object m-mediaindexhewpew {

  /**
   * which t-tweets shouwd w-we tweat as "media" t-tweets?
   *
   * any tweet that is nyot a wetweet and any of:
   * - is e-expwicitwy mawked as a media tweet. OwO
   * - has a m-media entity. (U ﹏ U)
   * - incwudes a p-pawtnew media uww. >_<
   */
  def appwy(pawtnewmediawegexes: seq[wegex]): t-tweet => boowean = {
    v-vaw ispawtnewuww = p-pawtnewuwwmatchew(pawtnewmediawegexes)

    tweet =>
      getshawe(tweet).isempty &&
        (hasmediafwagset(tweet) ||
          getmedia(tweet).nonempty ||
          getuwws(tweet).exists(ispawtnewuww))
  }

  def pawtnewuwwmatchew(pawtnewmediawegexes: s-seq[wegex]): uwwentity => boowean =
    _.expanded.exists { expandeduww =>
      pawtnewmediawegexes.exists(_.findfiwstin(expandeduww).isdefined)
    }

  def hasmediafwagset(tweet: t-tweet): boowean =
    t-tweet.cowedata.fwatmap(_.hasmedia).getowewse(fawse)
}
