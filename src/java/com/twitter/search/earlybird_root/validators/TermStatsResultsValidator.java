package com.twittew.seawch.eawwybiwd_woot.vawidatows;

impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdcwustew;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;
i-impowt com.twittew.utiw.futuwe;

p-pubwic c-cwass tewmstatswesuwtsvawidatow i-impwements sewvicewesponsevawidatow<eawwybiwdwesponse> {
  p-pwivate f-finaw eawwybiwdcwustew cwustew;

  pubwic tewmstatswesuwtsvawidatow(eawwybiwdcwustew cwustew) {
    this.cwustew = c-cwustew;
  }

  @ovewwide
  pubwic futuwe<eawwybiwdwesponse> vawidate(eawwybiwdwesponse w-wesponse) {
    if (!wesponse.issettewmstatisticswesuwts()
        || !wesponse.gettewmstatisticswesuwts().issettewmwesuwts()) {
      w-wetuwn futuwe.exception(
          nyew iwwegawstateexception(cwustew + " wetuwned nyuww tewm statistics wesuwts."));
    }
    w-wetuwn futuwe.vawue(wesponse);
  }
}
