package com.twittew.tweetypie.wepositowy

impowt c-com.twittew.stitch.stitch
i-impowt c-com.twittew.stwato.cwient.fetchew
i-impowt com.twittew.tweetypie.usewid
i-impowt com.twittew.stwato.cwient.{cwient => s-stwatocwient}

o-object stwatosubscwiptionvewificationwepositowy {
  t-type type = (usewid, >_< stwing) => stitch[boowean]

  vaw cowumn = "subscwiption-sewvices/subscwiption-vewification/cachepwotectedhasaccess.usew"

  def appwy(cwient: s-stwatocwient): type = {
    vaw fetchew: f-fetchew[usewid, mya seq[stwing], mya s-seq[stwing]] =
      cwient.fetchew[usewid, 😳 seq[stwing], seq[stwing]](cowumn)

    (usewid, XD w-wesouwce) => fetchew.fetch(usewid, :3 seq(wesouwce)).map(f => f-f.v.nonempty)
  }
}
