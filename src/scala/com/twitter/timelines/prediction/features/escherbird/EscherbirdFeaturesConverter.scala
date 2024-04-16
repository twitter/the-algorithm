package com.tw ter.t  l nes.pred ct on.features.esc rb rd

 mport com.tw ter.t etyp e.thr ftscala.T et
 mport scala.collect on.JavaConverters._

object Esc rb rdFeaturesConverter {
  val DeprecatedOrTestDoma ns = Set(1L, 5L, 7L, 9L, 14L, 19L, 20L, 31L)

  def fromT et(t et: T et): Opt on[Esc rb rdFeatures] = t et.esc rb rdEnt yAnnotat ons.map {
    esc rb rdEnt yAnnotat ons =>
      val annotat ons = esc rb rdEnt yAnnotat ons.ent yAnnotat ons
        .f lterNot(annotat on => DeprecatedOrTestDoma ns.conta ns(annotat on.doma n d))
      val t etGroup ds = annotat ons.map(_.group d.toStr ng).toSet.asJava
      val t etDoma n ds = annotat ons.map(_.doma n d.toStr ng).toSet.asJava
      // An ent y  s only un que w h n a g ven doma n
      val t etEnt y ds = annotat ons.map(a => s"${a.doma n d}.${a.ent y d}").toSet.asJava
      Esc rb rdFeatures(t et. d, t etGroup ds, t etDoma n ds, t etEnt y ds)
  }
}
