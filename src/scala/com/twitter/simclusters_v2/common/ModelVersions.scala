package com.tw ter.s mclusters_v2.common

 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on

/**
 * T  ut l y to convert S mClusters Model vers on  nto d fferent forms.
 * Requ red to reg ster any new S mClusters Model vers on  re.
 */
object ModelVers ons {

  val Model20M145KDec11 = "20M_145K_dec11"
  val Model20M145KUpdated = "20M_145K_updated"
  val Model20M145K2020 = "20M_145K_2020"

  // Use Enum for feature sw ch
  object Enum extends Enu rat on {
    val Model20M145K2020, Model20M145KUpdated: Value = Value
    val enumToS mClustersModelVers onMap: Map[Enum.Value, ModelVers on] = Map(
      Model20M145K2020 -> ModelVers on.Model20m145k2020,
      Model20M145KUpdated -> ModelVers on.Model20m145kUpdated
    )
  }

  // Add t  new model vers on  nto t  map
  pr vate val Str ngToThr ftModelVers ons: Map[Str ng, ModelVers on] =
    Map(
      Model20M145KDec11 -> ModelVers on.Model20m145kDec11,
      Model20M145KUpdated -> ModelVers on.Model20m145kUpdated,
      Model20M145K2020 -> ModelVers on.Model20m145k2020
    )

  pr vate val Thr ftModelVers onToStr ngs = Str ngToThr ftModelVers ons.map(_.swap)

  val AllModelVers ons: Set[Str ng] = Str ngToThr ftModelVers ons.keySet

  def toModelVers onOpt on(modelVers onStr: Str ng): Opt on[ModelVers on] = {
    Str ngToThr ftModelVers ons.get(modelVers onStr)
  }

   mpl c  def toModelVers on(modelVers onStr: Str ng): ModelVers on = {
    Str ngToThr ftModelVers ons(modelVers onStr)
  }

   mpl c  def toKnownForModelVers on(modelVers on: ModelVers on): Str ng = {
    Thr ftModelVers onToStr ngs(modelVers on)
  }

}
