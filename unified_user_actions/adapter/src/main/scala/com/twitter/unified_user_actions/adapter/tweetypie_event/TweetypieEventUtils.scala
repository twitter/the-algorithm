package com.twittew.unified_usew_actions.adaptew.tweetypie_event

impowt com.twittew.tweetypie.thwiftscawa.editcontwow
i-impowt com.twittew.tweetypie.thwiftscawa.editcontwowedit
impowt c-com.twittew.tweetypie.thwiftscawa.tweet

seawed t-twait tweetypietweettype
object t-tweettypedefauwt e-extends tweetypietweettype
o-object tweettypewepwy e-extends t-tweetypietweettype
object tweettypewetweet extends tweetypietweettype
object tweettypequote e-extends tweetypietweettype
object tweettypeedit e-extends tweetypietweettype

o-object tweetypieeventutiws {
  def editedtweetidfwomtweet(tweet: tweet): option[wong] = t-tweet.editcontwow.fwatmap {
    case editcontwow.edit(editcontwowedit(initiawtweetid, -.- _)) => s-some(initiawtweetid)
    c-case _ => nyone
  }

  def tweettypefwomtweet(tweet: tweet): option[tweetypietweettype] = {
    v-vaw data = tweet.cowedata
    vaw inwepwyingtostatusidopt = data.fwatmap(_.wepwy).fwatmap(_.inwepwytostatusid)
    vaw shaweopt = d-data.fwatmap(_.shawe)
    vaw quotedtweetopt = t-tweet.quotedtweet
    v-vaw e-editedtweetidopt = e-editedtweetidfwomtweet(tweet)

    (inwepwyingtostatusidopt, 😳 shaweopt, mya quotedtweetopt, (˘ω˘) editedtweetidopt) m-match {
      // wepwy
      case (some(_), >_< n-nyone, _, nyone) =>
        some(tweettypewepwy)
      // fow any kind of wetweet (be it wetweet of quote t-tweet ow wetweet of a weguwaw t-tweet)
      // w-we onwy nyeed to w-wook at the `shawe` fiewd
      // https://confwuence.twittew.biz/pages/viewpage.action?spacekey=csvc&titwe=tweetypie+faq#tweetypiefaq-howdoitewwifatweetisawetweet
      case (none, -.- s-some(_), 🥺 _, n-nyone) =>
        some(tweettypewetweet)
      // q-quote
      c-case (none, (U ﹏ U) nyone, >w< some(_), nyone) =>
        s-some(tweettypequote)
      // cweate
      c-case (none, mya nyone, nyone, >w< nyone) =>
        s-some(tweettypedefauwt)
      // edit
      c-case (none, nyaa~~ nyone, _, some(_)) =>
        s-some(tweettypeedit)
      // w-wepwy and wetweet shouwdn't be pwesent at the same time
      case (some(_), (✿oωo) some(_), ʘwʘ _, _) =>
        nyone
      // wepwy a-and edit / w-wetweet and edit shouwdn't be pwesent a-at the same t-time
      case (some(_), (ˆ ﻌ ˆ)♡ n-nyone, 😳😳😳 _, some(_)) | (none, :3 some(_), OwO _, some(_)) =>
        n-nyone
    }
  }

}
