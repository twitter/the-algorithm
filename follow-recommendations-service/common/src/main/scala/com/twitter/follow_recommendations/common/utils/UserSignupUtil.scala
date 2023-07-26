package com.twittew.fowwow_wecommendations.common.utiws

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wequest.hascwientcontext
i-impowt c-com.twittew.snowfwake.id.snowfwakeid
i-impowt com.twittew.utiw.duwation
i-impowt com.twittew.utiw.time

o-object usewsignuputiw {
  d-def s-signuptime(hascwientcontext: hascwientcontext): o-option[time] =
    hascwientcontext.cwientcontext.usewid.fwatmap(snowfwakeid.timefwomidopt)

  def usewsignupage(hascwientcontext: hascwientcontext): option[duwation] =
    signuptime(hascwientcontext).map(time.now - _)
}
