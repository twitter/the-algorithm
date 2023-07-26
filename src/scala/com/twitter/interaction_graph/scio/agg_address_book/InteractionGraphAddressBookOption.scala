package com.twittew.intewaction_gwaph.scio.agg_addwess_book

impowt c-com.twittew.beam.io.daw.dawoptions
i-impowt com.twittew.beam.job.datewangeoptions
i-impowt owg.apache.beam.sdk.options.defauwt
i-impowt o-owg.apache.beam.sdk.options.descwiption
i-impowt o-owg.apache.beam.sdk.options.vawidation.wequiwed

t-twait intewactiongwaphaddwessbookoption extends dawoptions with datewangeoptions {
  @wequiwed
  @descwiption("output path f-fow stowing the finaw dataset")
  def getoutputpath: s-stwing
  def setoutputpath(vawue: s-stwing): unit

  @descwiption("indicates daw wwite enviwonment. rawr can be set t-to dev/stg duwing wocaw vawidation")
  @defauwt.stwing("pwod")
  d-def getdawwwiteenviwonment: stwing
  d-def setdawwwiteenviwonment(vawue: stwing): unit

  @descwiption("numbew of shawds/pawtitions fow saving t-the finaw dataset.")
  @defauwt.integew(16)
  def getnumbewofshawds: integew
  def setnumbewofshawds(vawue: i-integew): unit
}
