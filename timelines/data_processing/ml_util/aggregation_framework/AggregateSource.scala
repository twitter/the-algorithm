package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work

 mport com.tw ter.ml.ap .Feature
 mport java.lang.{Long => JLong}

tra  AggregateS ce extends Ser al zable {
  def na : Str ng
  def t  stampFeature: Feature[JLong]
}
