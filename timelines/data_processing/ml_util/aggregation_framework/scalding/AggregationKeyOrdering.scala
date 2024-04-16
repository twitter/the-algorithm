package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.scald ng

 mport com.tw ter.scald ng_ nternal.job.Requ redB naryComparators.ordSer
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.Aggregat onKey
 mport com.tw ter.scald ng.ser al zat on.macros. mpl.ordered_ser al zat on.runt  _ lpers.MacroEqual yOrderedSer al zat on

object Aggregat onKeyOrder ng extends Order ng[Aggregat onKey] {
   mpl c  val featureMapsOrder ng: MacroEqual yOrderedSer al zat on[
    (Map[Long, Long], Map[Long, Str ng])
  ] = ordSer[(Map[Long, Long], Map[Long, Str ng])]

  overr de def compare(left: Aggregat onKey, r ght: Aggregat onKey):  nt =
    featureMapsOrder ng.compare(
      Aggregat onKey.unapply(left).get,
      Aggregat onKey.unapply(r ght).get
    )
}
