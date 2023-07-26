package com.twittew.tweetypie
package h-handwew

impowt c-com.twittew.tweetypie.stowe.updatepossibwysensitivetweet
i-impowt c-com.twittew.tweetypie.thwiftscawa.updatepossibwysensitivetweetwequest
i-impowt c-com.twittew.tweetypie.utiw.tweetwenses

o-object u-updatepossibwysensitivetweethandwew {
  type type = futuweawwow[updatepossibwysensitivetweetwequest, 🥺 unit]

  def appwy(
    tweetgettew: f-futuweawwow[tweetid, >_< tweet],
    usewgettew: futuweawwow[usewid, >_< u-usew], (⑅˘꒳˘)
    updatepossibwysensitivetweetstowe: f-futuweeffect[updatepossibwysensitivetweet.event]
  ): type =
    futuweawwow { wequest =>
      vaw nysfwadminmutation = m-mutation[boowean](_ => wequest.nsfwadmin).checkeq
      v-vaw nysfwusewmutation = m-mutation[boowean](_ => wequest.nsfwusew).checkeq
      vaw tweetmutation =
        tweetwenses.nsfwadmin
          .mutation(nsfwadminmutation)
          .awso(tweetwenses.nsfwusew.mutation(nsfwusewmutation))

      fow {
        o-owiginawtweet <- tweetgettew(wequest.tweetid)
        _ <- tweetmutation(owiginawtweet) match {
          case nyone => f-futuwe.unit
          case some(mutatedtweet) =>
            u-usewgettew(getusewid(owiginawtweet))
              .map { u-usew =>
                u-updatepossibwysensitivetweet.event(
                  t-tweet = mutatedtweet, /(^•ω•^)
                  usew = usew, rawr x3
                  t-timestamp = time.now, (U ﹏ U)
                  byusewid = w-wequest.byusewid, (U ﹏ U)
                  nysfwadminchange = nysfwadminmutation(tweetwenses.nsfwadmin.get(owiginawtweet)), (⑅˘꒳˘)
                  nysfwusewchange = nysfwusewmutation(tweetwenses.nsfwusew.get(owiginawtweet)), òωó
                  nyote = wequest.note, ʘwʘ
                  host = w-wequest.host
                )
              }
              .fwatmap(updatepossibwysensitivetweetstowe)
        }
      } yiewd ()
    }
}
