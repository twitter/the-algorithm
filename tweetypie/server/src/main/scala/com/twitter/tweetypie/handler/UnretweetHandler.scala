package com.twittew.tweetypie
package h-handwew

impowt c-com.twittew.stitch.stitch
impowt c-com.twittew.tweetypie.futuwe
i-impowt com.twittew.tweetypie.cowe.fiwtewedstate
i-impowt com.twittew.tweetypie.wepositowy.tweetquewy
i-impowt com.twittew.tweetypie.wepositowy.tweetwepositowy
i-impowt c-com.twittew.tweetypie.thwiftscawa._
impowt com.twittew.timewinesewvice.{thwiftscawa => tws}
impowt com.twittew.tweetypie.backends.timewinesewvice.getpewspectives

o-object unwetweethandwew {

  type type = unwetweetwequest => f-futuwe[unwetweetwesuwt]

  def appwy(
    dewetetweets: t-tweetdewetepathhandwew.dewetetweets, -.-
    getpewspectives: getpewspectives, 🥺
    unwetweetedits: t-tweetdewetepathhandwew.unwetweetedits, o.O
    tweetwepo: t-tweetwepositowy.type, /(^•ω•^)
  ): t-type = { wequest: unwetweetwequest =>
    vaw handweedits = getsouwcetweet(wequest.souwcetweetid, nyaa~~ tweetwepo).wifttotwy.fwatmap {
      case wetuwn(souwcetweet) =>
        // i-if we'we abwe to fetch the souwce tweet, nyaa~~ unwetweet aww its othew vewsions
        u-unwetweetedits(souwcetweet.editcontwow, :3 wequest.souwcetweetid, 😳😳😳 w-wequest.usewid)
      c-case thwow(_) => f-futuwe.done
    }

    h-handweedits.fwatmap(_ => unwetweetsouwcetweet(wequest, (˘ω˘) dewetetweets, ^^ getpewspectives))
  }

  d-def unwetweetsouwcetweet(
    wequest: unwetweetwequest, :3
    dewetetweets: t-tweetdewetepathhandwew.dewetetweets, -.-
    getpewspectives: getpewspectives, 😳
  ): futuwe[unwetweetwesuwt] =
    getpewspectives(
      seq(tws.pewspectivequewy(wequest.usewid, mya s-seq(wequest.souwcetweetid)))
    ).map { wesuwts => w-wesuwts.head.pewspectives.headoption.fwatmap(_.wetweetid) }
      .fwatmap {
        c-case some(id) =>
          d-dewetetweets(
            dewetetweetswequest(tweetids = seq(id), (˘ω˘) byusewid = some(wequest.usewid)), >_<
            f-fawse
          ).map(_.head).map { d-dewetetweetwesuwt =>
            unwetweetwesuwt(some(dewetetweetwesuwt.tweetid), -.- d-dewetetweetwesuwt.state)
          }
        c-case nyone => futuwe.vawue(unwetweetwesuwt(none, 🥺 t-tweetdewetestate.ok))
      }

  def getsouwcetweet(
    s-souwcetweetid: tweetid, (U ﹏ U)
    tweetwepo: tweetwepositowy.type
  ): f-futuwe[tweet] = {
    vaw options: t-tweetquewy.options = tweetquewy
      .options(incwude = tweetquewy.incwude(tweetfiewds = s-set(tweet.editcontwowfiewd.id)))

    s-stitch.wun {
      tweetwepo(souwcetweetid, >w< options).wescue {
        case _: fiwtewedstate => stitch.notfound
      }
    }
  }
}
