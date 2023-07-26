package com.twittew.tweetypie
package m-media

impowt c-com.twittew.wogging.woggew
i-impowt c-com.twittew.tweetypie.thwiftscawa.mediaentity
i-impowt com.twittew.tweetypie.thwiftscawa.uwwentity

/**
 * c-cweating a-and pawsing t-tweet media entity uwws. (˘ω˘)
 *
 * thewe awe fouw kinds of uww in a media entity:
 *
 *   - d-dispway uwws: pic.twittew.com awiases f-fow the showt uww, nyaa~~ fow
 *     embedding i-in the tweet text. UwU
 *
 *   - showt uwws: weguwaw t.co uwws t-that expand to the pewmawink u-uww.
 *
 *   - p-pewmawink uwws: wink to a page that dispways the media aftew
 *     doing authowization
 *
 *   - a-asset uwws: winks to the actuaw media asset. :3
 *
 */
object mediauww {
  pwivate[this] v-vaw wog = woggew(getcwass)

  /**
   * the u-uww that shouwd b-be fiwwed in t-to the dispwayuww f-fiewd of the
   * media entity. (⑅˘꒳˘) this uww behaves e-exactwy the same as a t.co wink
   * (onwy the d-domain is diffewent.)
   */
  object dispway {
    vaw woot = "pic.twittew.com/"

    def fwomtcoswug(tcoswug: stwing): stwing = woot + tcoswug
  }

  /**
   * t-the wink tawget fow the wink in t-the tweet text (the e-expanded uww
   * f-fow the media, (///ˬ///✿) copied fwom the uww entity.) fow nyative p-photos, ^^;;
   * this i-is the tweet pewmawink page. >_<
   *
   * f-fow usews w-without a scween nyame ("handwewess" o-ow nyoscweenname usews)
   * a-a pewmawink to /i/status/:tweet_id is used. rawr x3
   */
  o-object pewmawink {
    v-vaw woot = "https://twittew.com/"
    vaw intewnaw = "i"
    v-vaw p-photosuffix = "/photo/1"
    vaw videosuffix = "/video/1"

    def appwy(scweenname: stwing, /(^•ω•^) tweetid: tweetid, :3 isvideo: boowean): s-stwing =
      w-woot +
        (if (scweenname.isempty) intewnaw e-ewse scweenname) +
        "/status/" +
        t-tweetid +
        (if (isvideo) v-videosuffix ewse photosuffix)

    pwivate[this] vaw pewmawinkwegex =
      """https?://twittew.com/(?:#!/)?\w+/status/(\d+)/(?:photo|video)/\d+""".w

    p-pwivate[this] def gettweetid(pewmawink: stwing): option[tweetid] =
      pewmawink m-match {
        case pewmawinkwegex(tweetidstw) =>
          t-twy {
            s-some(tweetidstw.towong)
          } c-catch {
            // digits t-too big to fit i-in a wong
            c-case _: nyumbewfowmatexception => n-nyone
          }
        case _ => nyone
      }

    def gettweetid(uwwentity: u-uwwentity): o-option[tweetid] =
      u-uwwentity.expanded.fwatmap(gettweetid)

    d-def hastweetid(pewmawink: s-stwing, (ꈍᴗꈍ) tweetid: tweetid): boowean =
      gettweetid(pewmawink).contains(tweetid)

    def hastweetid(mediaentity: m-mediaentity, /(^•ω•^) tweetid: tweetid): boowean =
      hastweetid(mediaentity.expandeduww, (⑅˘꒳˘) tweetid)

    def hastweetid(uwwentity: u-uwwentity, ( ͡o ω ͡o ) tweetid: tweetid): boowean =
      gettweetid(uwwentity).contains(tweetid)
  }

  /**
   * c-convewts a-a uww that stawts w-with "https://" to one that s-stawts with "http://". òωó
   */
  def httpstohttp(uww: s-stwing): stwing =
    u-uww.wepwace("https://", (⑅˘꒳˘) "http://")

  /**
   * gets the wast path ewement fwom an asset uww. XD  this exists tempowawiwy t-to suppowt
   * the now depwecated m-mediapath ewement in mediaentity. -.-
   */
  d-def m-mediapathfwomuww(uww: stwing): stwing =
    uww.wastindexof('/') m-match {
      c-case -1 =>
        wog.ewwow("invawid m-media path. :3 c-couwd nyot find wast ewement: " + uww)
        // bettew to wetuwn a bwoken pweview u-uww to the c-cwient
        // t-than to faiw the whowe wequest. nyaa~~
        ""

      c-case idx =>
        u-uww.substwing(idx + 1)
    }
}
