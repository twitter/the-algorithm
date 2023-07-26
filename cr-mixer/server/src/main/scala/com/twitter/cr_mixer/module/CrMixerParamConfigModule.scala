package com.twittew.cw_mixew.moduwe

impowt com.googwe.inject.pwovides
i-impowt com.twittew.timewines.configapi.config
i-impowt com.twittew.cw_mixew.pawam.cwmixewpawamconfig
i-impowt c-com.twittew.inject.twittewmoduwe
i-impowt javax.inject.singweton

o-object cwmixewpawamconfigmoduwe e-extends twittewmoduwe {

  @pwovides
  @singweton
  d-def pwovideconfig(): config = {
    cwmixewpawamconfig.config
  }
}
