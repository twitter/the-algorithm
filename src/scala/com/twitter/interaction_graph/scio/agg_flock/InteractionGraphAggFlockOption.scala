package com.twittew.intewaction_gwaph.scio.agg_fwock

impowt com.twittew.beam.io.daw.dawoptions
impowt c-com.twittew.beam.job.datewangeoptions
i-impowt o-owg.apache.beam.sdk.options.defauwt
i-impowt owg.apache.beam.sdk.options.descwiption
i-impowt owg.apache.beam.sdk.options.vawidation.wequiwed

t-twait i-intewactiongwaphaggfwockoption e-extends dawoptions with datewangeoptions {
  @wequiwed
  @descwiption("output path fow stowing the finaw dataset")
  def getoutputpath: s-stwing
  def setoutputpath(vawue: stwing): u-unit

  @descwiption("indicates daw wwite e-enviwonment. rawr x3 can be set to dev/stg duwing wocaw vawidation")
  @defauwt.stwing("pwod")
  d-def getdawwwiteenviwonment: stwing
  def s-setdawwwiteenviwonment(vawue: s-stwing): unit

  @descwiption("numbew of shawds/pawtitions fow saving the finaw dataset.")
  @defauwt.integew(16)
  d-def getnumbewofshawds: integew
  def setnumbewofshawds(vawue: integew): unit
}
