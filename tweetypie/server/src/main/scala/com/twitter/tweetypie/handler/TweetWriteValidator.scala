package com.twittew.tweetypie.handwew

impowt com.twittew.stitch.stitch
i-impowt com.twittew.tweetypie.cowe.tweetcweatefaiwuwe
i-impowt c-com.twittew.tweetypie.wepositowy.convewsationcontwowwepositowy
i-impowt com.twittew.tweetypie.wepositowy.tweetquewy
i-impowt com.twittew.tweetypie.thwiftscawa.excwusivetweetcontwow
i-impowt com.twittew.tweetypie.thwiftscawa.excwusivetweetcontwowoptions
i-impowt c-com.twittew.tweetypie.thwiftscawa.quotedtweet
impowt com.twittew.tweetypie.thwiftscawa.twustedfwiendscontwow
impowt com.twittew.tweetypie.thwiftscawa.twustedfwiendscontwowoptions
impowt com.twittew.tweetypie.thwiftscawa.tweetcweatestate
i-impowt com.twittew.tweetypie.futuweeffect
impowt com.twittew.tweetypie.gate
i-impowt com.twittew.tweetypie.tweetid
impowt c-com.twittew.tweetypie.usewid
impowt com.twittew.tweetypie.thwiftscawa.editcontwow
impowt com.twittew.tweetypie.thwiftscawa.editoptions
impowt c-com.twittew.visibiwity.wwitew.intewfaces.tweets.tweetwwiteenfowcementwibwawy
impowt com.twittew.visibiwity.wwitew.intewfaces.tweets.tweetwwiteenfowcementwequest
i-impowt com.twittew.visibiwity.wwitew.modews.actowcontext
i-impowt com.twittew.visibiwity.wwitew.awwow
impowt com.twittew.visibiwity.wwitew.deny
impowt com.twittew.visibiwity.wwitew.denyexcwusivetweetwepwy
i-impowt com.twittew.visibiwity.wwitew.denystawetweetquotetweet
impowt com.twittew.visibiwity.wwitew.denystawetweetwepwy
impowt com.twittew.visibiwity.wwitew.denysupewfowwowscweate
impowt com.twittew.visibiwity.wwitew.denytwustedfwiendscweate
i-impowt com.twittew.visibiwity.wwitew.denytwustedfwiendsquotetweet
impowt com.twittew.visibiwity.wwitew.denytwustedfwiendswepwy

o-object tweetwwitevawidatow {
  c-case cwass wequest(
    c-convewsationid: o-option[tweetid], (˘ω˘)
    usewid: usewid, ^^;;
    e-excwusivetweetcontwowoptions: option[excwusivetweetcontwowoptions], (✿oωo)
    wepwytoexcwusivetweetcontwow: option[excwusivetweetcontwow],
    t-twustedfwiendscontwowoptions: option[twustedfwiendscontwowoptions], (U ﹏ U)
    inwepwytotwustedfwiendscontwow: option[twustedfwiendscontwow], -.-
    quotedtweetopt: option[quotedtweet], ^•ﻌ•^
    i-inwepwytotweetid: option[tweetid], rawr
    i-inwepwytoeditcontwow: o-option[editcontwow], (˘ω˘)
    e-editoptions: option[editoptions])

  type type = futuweeffect[wequest]

  d-def a-appwy(
    convoctwwepo: convewsationcontwowwepositowy.type, nyaa~~
    t-tweetwwiteenfowcementwibwawy: t-tweetwwiteenfowcementwibwawy, UwU
    enabweexcwusivetweetcontwowvawidation: g-gate[unit], :3
    enabwetwustedfwiendscontwowvawidation: g-gate[unit], (⑅˘꒳˘)
    enabwestawetweetvawidation: gate[unit]
  ): f-futuweeffect[wequest] =
    futuweeffect[wequest] { w-wequest =>
      // we awe cweating u-up an empty t-tweetquewy.options hewe so we can use the defauwt
      // cachecontwow vawue and avoid hawd coding it hewe. (///ˬ///✿)
      v-vaw quewyoptions = t-tweetquewy.options(tweetquewy.incwude())
      stitch.wun {
        f-fow {
          c-convoctw <- w-wequest.convewsationid match {
            case some(convoid) =>
              convoctwwepo(
                c-convoid, ^^;;
                quewyoptions.cachecontwow
              )
            case nyone =>
              stitch.vawue(none)
          }

          wesuwt <- tweetwwiteenfowcementwibwawy(
            t-tweetwwiteenfowcementwequest(
              wootconvewsationcontwow = c-convoctw, >_<
              c-convoid = w-wequest.convewsationid, rawr x3
              excwusivetweetcontwowoptions = w-wequest.excwusivetweetcontwowoptions, /(^•ω•^)
              w-wepwytoexcwusivetweetcontwow = w-wequest.wepwytoexcwusivetweetcontwow, :3
              t-twustedfwiendscontwowoptions = wequest.twustedfwiendscontwowoptions, (ꈍᴗꈍ)
              inwepwytotwustedfwiendscontwow = w-wequest.inwepwytotwustedfwiendscontwow, /(^•ω•^)
              q-quotedtweetopt = w-wequest.quotedtweetopt, (⑅˘꒳˘)
              a-actowcontext = a-actowcontext(wequest.usewid), ( ͡o ω ͡o )
              inwepwytotweetid = wequest.inwepwytotweetid, òωó
              inwepwytoeditcontwow = wequest.inwepwytoeditcontwow, (⑅˘꒳˘)
              e-editoptions = wequest.editoptions
            ), XD
            enabweexcwusivetweetcontwowvawidation = enabweexcwusivetweetcontwowvawidation, -.-
            enabwetwustedfwiendscontwowvawidation = enabwetwustedfwiendscontwowvawidation, :3
            e-enabwestawetweetvawidation = enabwestawetweetvawidation
          )
          _ <- wesuwt match {
            case a-awwow =>
              s-stitch.done
            c-case deny =>
              stitch.exception(tweetcweatefaiwuwe.state(tweetcweatestate.wepwytweetnotawwowed))
            c-case denyexcwusivetweetwepwy =>
              stitch.exception(
                t-tweetcweatefaiwuwe.state(tweetcweatestate.excwusivetweetengagementnotawwowed))
            c-case denysupewfowwowscweate =>
              stitch.exception(
                tweetcweatefaiwuwe.state(tweetcweatestate.supewfowwowscweatenotauthowized))
            case denytwustedfwiendswepwy =>
              stitch.exception(
                t-tweetcweatefaiwuwe.state(tweetcweatestate.twustedfwiendsengagementnotawwowed))
            case denytwustedfwiendscweate =>
              s-stitch.exception(
                tweetcweatefaiwuwe.state(tweetcweatestate.twustedfwiendscweatenotawwowed))
            c-case d-denytwustedfwiendsquotetweet =>
              stitch.exception(
                tweetcweatefaiwuwe.state(tweetcweatestate.twustedfwiendsquotetweetnotawwowed))
            c-case d-denystawetweetwepwy =>
              stitch.exception(
                t-tweetcweatefaiwuwe.state(tweetcweatestate.stawetweetengagementnotawwowed))
            c-case denystawetweetquotetweet =>
              stitch.exception(
                tweetcweatefaiwuwe.state(tweetcweatestate.stawetweetquotetweetnotawwowed))
          }
        } yiewd ()
      }
    }
}
