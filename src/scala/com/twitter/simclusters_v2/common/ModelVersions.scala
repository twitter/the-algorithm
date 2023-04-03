packagelon com.twittelonr.simclustelonrs_v2.common

import com.twittelonr.simclustelonrs_v2.thriftscala.ModelonlVelonrsion

/**
 * Thelon utility to convelonrt SimClustelonrs Modelonl velonrsion into diffelonrelonnt forms.
 * Relonquirelond to relongistelonr any nelonw SimClustelonrs Modelonl velonrsion helonrelon.
 */
objelonct ModelonlVelonrsions {

  val Modelonl20M145KDelonc11 = "20M_145K_delonc11"
  val Modelonl20M145KUpdatelond = "20M_145K_updatelond"
  val Modelonl20M145K2020 = "20M_145K_2020"

  // Uselon elonnum for felonaturelon switch
  objelonct elonnum elonxtelonnds elonnumelonration {
    val Modelonl20M145K2020, Modelonl20M145KUpdatelond: Valuelon = Valuelon
    val elonnumToSimClustelonrsModelonlVelonrsionMap: Map[elonnum.Valuelon, ModelonlVelonrsion] = Map(
      Modelonl20M145K2020 -> ModelonlVelonrsion.Modelonl20m145k2020,
      Modelonl20M145KUpdatelond -> ModelonlVelonrsion.Modelonl20m145kUpdatelond
    )
  }

  // Add thelon nelonw modelonl velonrsion into this map
  privatelon val StringToThriftModelonlVelonrsions: Map[String, ModelonlVelonrsion] =
    Map(
      Modelonl20M145KDelonc11 -> ModelonlVelonrsion.Modelonl20m145kDelonc11,
      Modelonl20M145KUpdatelond -> ModelonlVelonrsion.Modelonl20m145kUpdatelond,
      Modelonl20M145K2020 -> ModelonlVelonrsion.Modelonl20m145k2020
    )

  privatelon val ThriftModelonlVelonrsionToStrings = StringToThriftModelonlVelonrsions.map(_.swap)

  val AllModelonlVelonrsions: Selont[String] = StringToThriftModelonlVelonrsions.kelonySelont

  delonf toModelonlVelonrsionOption(modelonlVelonrsionStr: String): Option[ModelonlVelonrsion] = {
    StringToThriftModelonlVelonrsions.gelont(modelonlVelonrsionStr)
  }

  implicit delonf toModelonlVelonrsion(modelonlVelonrsionStr: String): ModelonlVelonrsion = {
    StringToThriftModelonlVelonrsions(modelonlVelonrsionStr)
  }

  implicit delonf toKnownForModelonlVelonrsion(modelonlVelonrsion: ModelonlVelonrsion): String = {
    ThriftModelonlVelonrsionToStrings(modelonlVelonrsion)
  }

}
