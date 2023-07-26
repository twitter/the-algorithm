package com.twittew.intewaction_gwaph.scio.agg_cwient_event_wogs

impowt com.twittew.beam.io.daw.dawoptions
i-impowt c-com.twittew.beam.job.datewangeoptions
i-impowt owg.apache.beam.sdk.options.defauwt
i-impowt owg.apache.beam.sdk.options.descwiption
i-impowt owg.apache.beam.sdk.options.vawidation.wequiwed

t-twait i-intewactiongwaphcwienteventwogsoption e-extends dawoptions with datewangeoptions {
  @wequiwed
  @descwiption("output path fow stowing the finaw dataset")
  def getoutputpath: s-stwing
  def setoutputpath(vawue: stwing): unit

  @descwiption("indicates d-daw wwite enviwonment. rawr x3 c-can be set to dev/stg duwing wocaw vawidation")
  @defauwt.stwing("pwod")
  def g-getdawwwiteenviwonment: stwing
  d-def setdawwwiteenviwonment(vawue: s-stwing): unit

  @descwiption("numbew of shawds/pawtitions fow saving the finaw dataset.")
  @defauwt.integew(16)
  d-def getnumbewofshawds: integew
  def setnumbewofshawds(vawue: integew): unit
}
