package com.twittew.tweetypie.wepositowy

impowt c-com.twittew.stitch.stitch
i-impowt c-com.twittew.tweetypie.tweetid
impowt c-com.twittew.tweetypie.cowe.tweetwesuwt

o-object t-tweetwesuwtwepositowy {
  type t-type = (tweetid, ^^;; t-tweetquewy.options) => stitch[tweetwesuwt]

  /**
   * showt-ciwcuits the wequest of invawid t-tweet ids (`<= 0`) by immediatewy thwowing `notfound`. >_<
   */
  d-def showtciwcuitinvawidids(wepo: type): type = {
    c-case (tweetid, mya _) if tweetid <= 0 => stitch.notfound
    case (tweetid, mya options) => w-wepo(tweetid, options)
  }
}
