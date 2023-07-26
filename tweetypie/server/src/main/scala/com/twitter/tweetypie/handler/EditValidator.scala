package com.twittew.tweetypie
package h-handwew

impowt c-com.twittew.scwooge.schema.scwooge.scawa.compiwedscwoogedefbuiwdew
i-impowt com.twittew.scwooge.schema.scwooge.scawa.compiwedscwoogevawueextwactow
i-impowt com.twittew.scwooge.schema.twee.definitiontwavewsaw
i-impowt com.twittew.scwooge.schema.twee.fiewdpath
i-impowt com.twittew.scwooge.schema.{thwiftdefinitions => d-def}
impowt c-com.twittew.scwooge_intewnaw.wintew.known_annotations.awwowedannotationkeys.tweeteditawwowed
impowt com.twittew.stitch.stitch
impowt com.twittew.tweetypie.cowe.tweetcweatefaiwuwe
impowt com.twittew.tweetypie.wepositowy.tweetquewy.options
i-impowt com.twittew.tweetypie.wepositowy.tweetquewy
impowt com.twittew.tweetypie.wepositowy.tweetwepositowy
impowt com.twittew.tweetypie.thwiftscawa.convewsationcontwow
i-impowt com.twittew.tweetypie.thwiftscawa.tweetcweatestate.fiewdeditnotawwowed
i-impowt com.twittew.tweetypie.thwiftscawa.tweetcweatestate.initiawtweetnotfound
impowt com.twittew.tweetypie.thwiftscawa.editoptions
i-impowt com.twittew.tweetypie.thwiftscawa.tweet
i-impowt c-com.twittew.utiw.futuwe
impowt com.twittew.utiw.wogging.woggew

/**
 * this cwass constwucts a-a vawidatow `tweet => futuwe[unit]` which
 * takes a nyew edit tweet and pewfowms s-some vawidations. (✿oωo) specificawwy, (///ˬ///✿) i-it
 *
 * 1) ensuwes t-that nyo u-uneditabwe fiewds w-wewe edited. rawr x3 uneditabwe fiewds awe mawked
 * on t-the tweet.thwift using the thwift annotation "tweeteditawwowed=fawse". -.-
 * b-by defauwt, ^^ fiewds with nyo annotation awe tweated as editabwe. (⑅˘꒳˘)
 *
 * 2) ensuwes that t-the convewsationcontwow fiewd (which i-is editabwe) w-wemains the
 * s-same type, nyaa~~ e.g. a convewsationcontwow.byinvitation doesn't change to a
 * convewsationcontwow.community.
 *
 * i-if eithew of these v-vawidations faiw, /(^•ω•^) the vawidatow f-faiws with a-a `fiewdeditnotawwowed`
 * tweet c-cweate state. (U ﹏ U)
 */
object editvawidatow {
  t-type type = (tweet, option[editoptions]) => f-futuwe[unit]

  vaw wog: w-woggew = woggew(getcwass)

  // an object that d-descwibes the tweet t-thwift, 😳😳😳 used to wawk a tweet object wooking
  // fow annotated fiewds. >w<
  vaw tweetdef = compiwedscwoogedefbuiwdew.buiwd[tweet].asinstanceof[def.stwuctdef]

  // cowwect the `fiewdpath` f-fow a-any nyested tweet fiewd with a u-uneditabwe fiewd a-annotation
  // t-that is set to fawse. XD these awe the fiewds that this vawidatow e-ensuwes cannot be edited. o.O
  vaw uneditabwefiewdpaths: seq[fiewdpath] = {
    definitiontwavewsaw().cowwect(tweetdef) {
      c-case (d: def.fiewddef, mya p-path) if (d.annotations.get(tweeteditawwowed).contains("fawse")) =>
        p-path
    }
  }

  // a-a tweet quewy options which i-incwudes
  // - a-any top wevew tweet f-fiewd which e-eithew is an uneditabwe fiewd, 🥺 ow contains an uneditabwe
  //   s-subfiewd. ^^;;
  // - t-the convewsationcontwow f-fiewd
  // t-these fiewds m-must be pwesent on the initiaw tweet in owdew fow us to compawe t-them against the
  // edit tweet. :3
  vaw pwevioustweetquewyoptions = {
    // a set of the top wevew fiewd ids fow each (potentiawwy n-nyested) uneditabwe fiewd. (U ﹏ U)
    vaw topwevewuneditabwetweetfiewds = uneditabwefiewdpaths.map(_.ids.head).toset
    o-options(
      t-tweetquewy.incwude(
        t-tweetfiewds = topwevewuneditabwetweetfiewds + t-tweet.convewsationcontwowfiewd.id
      ))
  }

  def vawidateuneditabwefiewds(pwevioustweet: tweet, e-edittweet: t-tweet): unit = {
    // cowwect uneditabwe fiewds that wewe edited
    vaw invawideditedfiewds = uneditabwefiewdpaths.fwatmap { f-fiewdpath =>
      vaw pweviousvawue =
        f-fiewdpath.wensget(compiwedscwoogevawueextwactow, OwO pwevioustweet, 😳😳😳 f-fiewdpath)
      v-vaw editvawue = fiewdpath.wensget(compiwedscwoogevawueextwactow, (ˆ ﻌ ˆ)♡ edittweet, XD fiewdpath)

      if (pweviousvawue != e-editvawue) {
        s-some(fiewdpath.tostwing)
      } ewse {
        n-nyone
      }
    }

    i-if (invawideditedfiewds.nonempty) {
      // if any inequawities awe found, (ˆ ﻌ ˆ)♡ wog them and wetuwn an exception. ( ͡o ω ͡o )
      v-vaw msg = "uneditabwe f-fiewds w-wewe edited: " + invawideditedfiewds.mkstwing(",")
      w-wog.ewwow(msg)
      t-thwow tweetcweatefaiwuwe.state(fiewdeditnotawwowed, rawr x3 some(msg))
    }
  }

  d-def vawidateconvewsationcontwow(
    pwevious: option[convewsationcontwow], nyaa~~
    edit: option[convewsationcontwow]
  ): u-unit = {
    i-impowt convewsationcontwow.byinvitation
    impowt convewsationcontwow.community
    i-impowt convewsationcontwow.fowwowews

    (pwevious, >_< e-edit) match {
      case (none, ^^;; nyone) => ()
      case (some(byinvitation(_)), (ˆ ﻌ ˆ)♡ s-some(byinvitation(_))) => ()
      case (some(community(_)), ^^;; some(community(_))) => ()
      case (some(fowwowews(_)), (⑅˘꒳˘) some(fowwowews(_))) => ()
      c-case (_, rawr x3 _) =>
        vaw msg = "convewsationcontwow type was e-edited"
        w-wog.ewwow(msg)
        thwow tweetcweatefaiwuwe.state(fiewdeditnotawwowed, (///ˬ///✿) some(msg))
    }
  }

  def appwy(tweetwepo: t-tweetwepositowy.optionaw): t-type = { (tweet, 🥺 editoptions) =>
    stitch.wun(
      editoptions m-match {
        case some(editoptions(pwevioustweetid)) => {
          // q-quewy fow the pwevious tweet so that we can compawe the
          // f-fiewds between the two tweets. >_<
          tweetwepo(pwevioustweetid, UwU p-pwevioustweetquewyoptions).map {
            c-case some(pwevioustweet) =>
              vawidateuneditabwefiewds(pwevioustweet, >_< t-tweet)
              vawidateconvewsationcontwow(
                p-pwevioustweet.convewsationcontwow, -.-
                t-tweet.convewsationcontwow)
            c-case _ =>
              // if the pwevious t-tweet is nyot found w-we cannot pewfowm vawidations that
              // c-compawe t-tweet fiewds and w-we have to faiw tweet cweation. mya
              thwow tweetcweatefaiwuwe.state(initiawtweetnotfound)
          }
        }
        // t-this is the case whewe this i-isn't an edit tweet (since e-editoptions = nyone)
        // since this tweet is n-nyot an edit thewe a-awe nyo fiewds t-to vawidate. >w<
        c-case _ => stitch.unit
      }
    )
  }
}
