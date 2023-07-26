package com.twittew.tweetypie
package w-wepositowy

i-impowt com.twittew.fwockdb.cwient.quotetweetsindexgwaph
i-impowt c-com.twittew.fwockdb.cwient.tfwockcwient
i-impowt com.twittew.fwockdb.cwient.usewtimewinegwaph
i-impowt c-com.twittew.stitch.stitch

o-object wastquoteofquotewwepositowy {
  type type = (tweetid, XD usewid) => stitch[boowean]

  d-def appwy(
    tfwockweadcwient: tfwockcwient
  ): t-type =
    (tweetid, :3 usewid) => {
      // s-sewect the tweets authowed by usewid quoting tweetid. 😳😳😳
      // b-by intewsecting the tweet q-quotes with this u-usew's tweets. -.-
      vaw quotesfwomquotingusew = quotetweetsindexgwaph
        .fwom(tweetid)
        .intewsect(usewtimewinegwaph.fwom(usewid))

      stitch.cawwfutuwe(tfwockweadcwient.sewectaww(quotesfwomquotingusew).map(_.size <= 1))
    }
}
