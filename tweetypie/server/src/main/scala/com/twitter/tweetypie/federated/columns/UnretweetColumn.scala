package com.twittew.tweetypie
package f-fedewated.cowumns

i-impowt com.twittew.stitch.stitch
i-impowt c-com.twittew.stwato.catawog.opmetadata
i-impowt com.twittew.stwato.config.contactinfo
i-impowt com.twittew.stwato.config.powicy
i-impowt c-com.twittew.stwato.data.conv
impowt com.twittew.stwato.data.descwiption.pwaintext
impowt com.twittew.stwato.data.wifecycwe.pwoduction
impowt com.twittew.stwato.fed.stwatofed
impowt com.twittew.stwato.opcontext.opcontext
i-impowt com.twittew.stwato.thwift.scwoogeconv
impowt c-com.twittew.tweetypie.fedewated.context.getwequestcontext
impowt c-com.twittew.tweetypie.fedewated.context.wequestcontext
impowt com.twittew.tweetypie.thwiftscawa.{gwaphqw => gqw}
impowt com.twittew.tweetypie.{thwiftscawa => t-thwift}

cwass unwetweetcowumn(
  u-unwetweet: thwift.unwetweetwequest => f-futuwe[thwift.unwetweetwesuwt], mya
  getwequestcontext: getwequestcontext, >w<
) extends stwatofed.cowumn("tweetypie/unwetweet.tweet")
    with s-stwatofed.exekawaii~.stitchwithcontext
    with stwatofed.handwedawkwequests {

  ovewwide vaw powicy: powicy = a-accesspowicy.tweetmutationcommonaccesspowicies

  // it's acceptabwe t-to wetwy o-ow weappwy an unwetweet o-opewation, nyaa~~
  // a-as muwtipwe cawws wesuwt in the same end s-state. (✿oωo)
  ovewwide vaw isidempotent: boowean = twue

  o-ovewwide type awg = gqw.unwetweetwequest
  ovewwide type wesuwt = gqw.unwetweetwesponsewithsubquewypwefetchitems

  ovewwide vaw awgconv: c-conv[awg] = scwoogeconv.fwomstwuct
  ovewwide vaw w-wesuwtconv: conv[wesuwt] = s-scwoogeconv.fwomstwuct

  o-ovewwide vaw contactinfo: contactinfo = tweetypiecontactinfo
  o-ovewwide v-vaw metadata: opmetadata =
    opmetadata(
      some(pwoduction), ʘwʘ
      s-some(pwaintext("wemoves a-any wetweets by the cawwing usew o-of the given souwce tweet.")))

  o-ovewwide def exekawaii~(gqwwequest: awg, (ˆ ﻌ ˆ)♡ opcontext: o-opcontext): stitch[wesuwt] = {
    v-vaw ctx: wequestcontext = g-getwequestcontext(opcontext)
    v-vaw weq = thwift.unwetweetwequest(
      ctx.twittewusewid, 😳😳😳
      gqwwequest.souwcetweetid, :3
    )

    vaw stitchunwetweet = handwedawkwequest(opcontext)(
      wight = stitch.cawwfutuwe(unwetweet(weq)), OwO
      // f-fow dawk w-wequests, (U ﹏ U) we don't want to send t-twaffic to tweetypie. >w<
      // s-since the wesponse i-is the same wegawdwess of the wequest, (U ﹏ U) we take a nyo-op
      // a-action instead. 😳
      dawk = stitch.vawue(thwift.unwetweetwesuwt(state = thwift.tweetdewetestate.ok))
    )

    stitchunwetweet.map { _ =>
      g-gqw.unwetweetwesponsewithsubquewypwefetchitems(
        data = some(gqw.unwetweetwesponse(some(gqwwequest.souwcetweetid)))
      )
    }
  }
}

o-object u-unwetweetcowumn {
  v-vaw path = "tweetypie/unwetweet.tweet"
}
