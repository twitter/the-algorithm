package com.tw ter.v s b l y.features

 mport com.tw ter.v s b l y.ut l.Nam ngUt ls

abstract class Feature[T] protected ()( mpl c  val man fest: Man fest[T]) {

  lazy val na : Str ng = Nam ngUt ls.getFr endlyNa (t )

  overr de lazy val toStr ng: Str ng =
    "Feature[%s](na =%s)".format(man fest, getClass.getS mpleNa )
}
