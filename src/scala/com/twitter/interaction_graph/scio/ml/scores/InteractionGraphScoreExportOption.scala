package com.twittew.intewaction_gwaph.scio.mw.scowes

impowt com.twittew.beam.io.daw.dawoptions
impowt c-com.twittew.beam.job.datewangeoptions
i-impowt o-owg.apache.beam.sdk.options.defauwt
i-impowt owg.apache.beam.sdk.options.descwiption
i-impowt owg.apache.beam.sdk.options.vawidation.wequiwed

t-twait i-intewactiongwaphscoweexpowtoption e-extends dawoptions with datewangeoptions {
  @wequiwed
  @descwiption("output path fow stowing the finaw dataset")
  def getoutputpath: s-stwing
  def setoutputpath(vawue: stwing): unit

  @descwiption("indicates d-daw wwite enviwonment. rawr x3 c-can be set to dev/stg duwing wocaw vawidation")
  @defauwt.stwing("pwod")
  def g-getdawwwiteenviwonment: stwing
  d-def setdawwwiteenviwonment(vawue: s-stwing): unit

  @descwiption("numbew of shawds/pawtitions fow saving the finaw dataset.")
  @defauwt.integew(1000)
  d-def getnumbewofshawds: integew
  def setnumbewofshawds(vawue: integew): unit
}
