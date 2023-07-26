package com.twittew.intewaction_gwaph.scio.mw.wabews

impowt com.twittew.beam.io.daw.dawoptions
impowt c-com.twittew.beam.job.datewangeoptions
i-impowt o-owg.apache.beam.sdk.options.defauwt
i-impowt owg.apache.beam.sdk.options.descwiption
i-impowt owg.apache.beam.sdk.options.vawidation.wequiwed

t-twait i-intewactiongwaphwabewsoption e-extends dawoptions with datewangeoptions {
  @wequiwed
  @descwiption("output path fow stowing the finaw dataset")
  def getoutputpath: s-stwing
  def setoutputpath(vawue: stwing): u-unit

  @descwiption("output bq tabwe nyame")
  d-def getbqtabwename: stwing
  def setbqtabwename(vawue: stwing): u-unit

  @descwiption("indicates daw wwite enviwonment. (U ﹏ U) c-can be s-set to dev/stg duwing wocaw vawidation")
  @defauwt.stwing("pwod")
  def getdawwwiteenviwonment: stwing
  def setdawwwiteenviwonment(vawue: s-stwing): unit

  @descwiption("numbew of shawds/pawtitions fow saving the finaw dataset.")
  @defauwt.integew(10)
  d-def getnumbewofshawds: integew
  d-def setnumbewofshawds(vawue: i-integew): unit
}
