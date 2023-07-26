package com.twittew.fowwow_wecommendations.moduwes

impowt com.googwe.inject.pwovides
i-impowt com.twittew.decidew.decidew
i-impowt com.twittew.fowwow_wecommendations.configapi.configbuiwdew
i-impowt c-com.twittew.inject.twittewmoduwe
i-impowt com.twittew.sewvo.decidew.decidewgatebuiwdew
i-impowt com.twittew.timewines.configapi.config
i-impowt javax.inject.singweton

o-object configapimoduwe extends twittewmoduwe {
  @pwovides
  @singweton
  def pwovidesdecidewgatebuiwdew(decidew: d-decidew): decidewgatebuiwdew =
    nyew decidewgatebuiwdew(decidew)

  @pwovides
  @singweton
  def pwovidesconfig(configbuiwdew: c-configbuiwdew): config = c-configbuiwdew.buiwd()
}
