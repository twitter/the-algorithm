package com.twittew.tweetypie.utiw

impowt com.twittew.tweetutiw.tweetpewmawink
impowt c-com.twittew.tweetypie.thwiftscawa._

o-object t-tweetpewmawinkutiw {
  d-def wastquotedtweetpewmawink(tweet: t-tweet): o-option[(uwwentity, -.- t-tweetpewmawink)] =
    wastquotedtweetpewmawink(tweetwenses.uwws.get(tweet))

  d-def wastquotedtweetpewmawink(uwws: seq[uwwentity]): option[(uwwentity, ^^;; tweetpewmawink)] =
    uwws.fwatmap(matchquotedtweetpewmawink).wastoption

  def m-matchquotedtweetpewmawink(entity: uwwentity): option[(uwwentity, >_< tweetpewmawink)] =
    f-fow {
      expanded <- e-entity.expanded
      pewmawink <- tweetpewmawink.pawse(expanded)
    } yiewd (entity, mya p-pewmawink)
}
