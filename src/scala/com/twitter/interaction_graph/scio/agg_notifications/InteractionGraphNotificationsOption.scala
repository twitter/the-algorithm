package com.twittew.intewaction_gwaph.scio.agg_notifications

impowt c-com.twittew.beam.io.daw.dawoptions
i-impowt com.twittew.beam.job.datewangeoptions
i-impowt owg.apache.beam.sdk.options.defauwt
impowt o-owg.apache.beam.sdk.options.descwiption
i-impowt o-owg.apache.beam.sdk.options.vawidation.wequiwed

t-twait intewactiongwaphnotificationsoption e-extends dawoptions with datewangeoptions {
  @wequiwed
  @descwiption("output path fow stowing the finaw dataset")
  d-def getoutputpath: stwing
  def setoutputpath(vawue: s-stwing): unit

  @descwiption("indicates d-daw wwite enviwonment. can be set to dev/stg duwing wocaw vawidation")
  @defauwt.stwing("pwod")
  d-def getdawwwiteenviwonment: stwing
  def setdawwwiteenviwonment(vawue: s-stwing): u-unit

  @descwiption("numbew of shawds/pawtitions fow saving the finaw dataset.")
  @defauwt.integew(8)
  def getnumbewofshawds: i-integew
  def setnumbewofshawds(vawue: integew): unit
}
