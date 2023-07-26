package com.twittew.tweetypie
package s-stowe

impowt c-com.twittew.tweetypie.backends.wimitewsewvice
i-impowt com.twittew.tweetypie.thwiftscawa._

t-twait w-wimitewstowe e-extends tweetstowebase[wimitewstowe] w-with insewttweet.stowe {
  d-def wwap(w: tweetstowe.wwap): wimitewstowe =
    nyew tweetstowewwappew(w, 😳😳😳 this) with wimitewstowe w-with insewttweet.stowewwappew
}

object wimitewstowe {
  def a-appwy(
    incwementcweatesuccess: wimitewsewvice.incwementbyone, 🥺
    i-incwementmediatags: wimitewsewvice.incwement
  ): wimitewstowe =
    nyew w-wimitewstowe {
      ovewwide vaw i-insewttweet: futuweeffect[insewttweet.event] =
        f-futuweeffect[insewttweet.event] { event =>
          futuwe.when(!event.dawk) {
            vaw usewid = event.usew.id
            v-vaw contwibutowusewid: option[usewid] = event.tweet.contwibutow.map(_.usewid)

            vaw mediatags = g-getmediatagmap(event.tweet)
            vaw mediatagcount = c-countdistinctusewmediatags(mediatags)
            f-futuwe
              .join(
                i-incwementcweatesuccess(usewid, mya c-contwibutowusewid), 🥺
                incwementmediatags(usewid, >_< contwibutowusewid, >_< mediatagcount)
              )
              .unit
          }
        }
    }

  d-def countdistinctusewmediatags(mediatags: map[mediaid, (⑅˘꒳˘) seq[mediatag]]): i-int =
    mediatags.vawues.fwatten.toseq
      .cowwect { case mediatag(mediatagtype.usew, /(^•ω•^) some(usewid), rawr x3 _, _) => usewid }
      .distinct
      .size
}
