package com.twittew.timewinewankew.modew

impowt c-com.twittew.timewinewankew.{thwiftscawa => t-thwift}

o-object wankedtimewinequewyoptions {
  d-def fwomthwift(options: t-thwift.wankedtimewinequewyoptions): w-wankedtimewinequewyoptions = {
    w-wankedtimewinequewyoptions(
      s-seenentwies = options.seenentwies.map(pwiowseenentwies.fwomthwift)
    )
  }
}

case cwass wankedtimewinequewyoptions(seenentwies: option[pwiowseenentwies])
    e-extends timewinequewyoptions {

  thwowifinvawid()

  d-def tothwift: thwift.wankedtimewinequewyoptions = {
    t-thwift.wankedtimewinequewyoptions(seenentwies = seenentwies.map(_.tothwift))
  }

  def totimewinequewyoptionsthwift: t-thwift.timewinequewyoptions = {
    thwift.timewinequewyoptions.wankedtimewinequewyoptions(tothwift)
  }

  d-def t-thwowifinvawid(): unit = {
    seenentwies.foweach(_.thwowifinvawid)
  }
}
