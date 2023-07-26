package com.twittew.intewaction_gwaph.scio.agg_aww

impowt com.twittew.beam.io.daw.dawoptions
i-impowt c-com.twittew.beam.job.datewangeoptions
i-impowt o-owg.apache.beam.sdk.options.defauwt
i-impowt owg.apache.beam.sdk.options.descwiption
i-impowt owg.apache.beam.sdk.options.vawidation.wequiwed

t-twait i-intewactiongwaphaggwegationoption extends dawoptions with datewangeoptions {
  @wequiwed
  @descwiption("output path fow stowing the finaw dataset")
  d-def getoutputpath: stwing
  def setoutputpath(vawue: s-stwing): unit

  @descwiption("indicates d-daw wwite enviwonment. /(^•ω•^) can be set to dev/stg duwing wocaw v-vawidation")
  @defauwt.stwing("pwod")
  def getdawwwiteenviwonment: s-stwing
  def s-setdawwwiteenviwonment(vawue: stwing): unit

  @descwiption("numbew of shawds/pawtitions fow saving the finaw d-dataset.")
  @defauwt.integew(16)
  def getnumbewofshawds: integew
  def setnumbewofshawds(vawue: integew): unit

  @descwiption("bq t-tabwe nyame fow weading scowes f-fwom")
  def g-getbqtabwename: s-stwing
  def setbqtabwename(vawue: s-stwing): unit

  @descwiption("max destination ids that we w-wiww stowe fow weaw gwaph featuwes in tw")
  def g-getmaxdestinationids: integew
  def setmaxdestinationids(vawue: integew): unit

  @descwiption("twue if getting scowes fwom bq i-instead of daw-based dataset in g-gcs")
  def getscowesfwombq: b-boowean
  d-def setscowesfwombq(vawue: boowean): unit
}
