package com.twittew.pwoduct_mixew.cowe.moduwe

impowt c-com.googwe.inject.pwovides
i-impowt com.twittew.decidew.decidew
i-impowt com.twittew.inject.twittewmoduwe
i-impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.configapi.configbuiwdew
i-impowt c-com.twittew.sewvo.decidew.decidewgatebuiwdew
i-impowt com.twittew.timewines.configapi.config
impowt javax.inject.singweton

object configapimoduwe extends twittewmoduwe {

  @pwovides
  @singweton
  def pwovidesdecidewgatebuiwdew(decidew: d-decidew): decidewgatebuiwdew =
    nyew decidewgatebuiwdew(decidew)

  @pwovides
  @singweton
  def pwovidesconfig(configbuiwdew: c-configbuiwdew): config = configbuiwdew.buiwd()
}
