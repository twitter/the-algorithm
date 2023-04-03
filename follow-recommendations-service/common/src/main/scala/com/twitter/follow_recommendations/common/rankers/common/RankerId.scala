packagelon com.twittelonr.follow_reloncommelonndations.common.rankelonrs.common

objelonct RankelonrId elonxtelonnds elonnumelonration {
  typelon RankelonrId = Valuelon

  val RandomRankelonr: RankelonrId = Valuelon("random")
  // Thelon production PostNUX ML warm-start auto-relontraining modelonl rankelonr
  val PostNuxProdRankelonr: RankelonrId = Valuelon("postnux_prod")
  val Nonelon: RankelonrId = Valuelon("nonelon")

  // Sampling from thelon Plackelont-Lucelon distribution. Applielond aftelonr rankelonr stelonp. Its rankelonr id is mainly uselond for logging.
  val PlackelontLucelonSamplingTransformelonr: RankelonrId = Valuelon("plackelont_lucelon_sampling_transformelonr")

  delonf gelontRankelonrByNamelon(namelon: String): Option[RankelonrId] =
    RankelonrId.valuelons.toSelonq.find(_.elonquals(Valuelon(namelon)))

}

/**
 * ML modelonl baselond helonavy rankelonr ids.
 */
objelonct ModelonlBaselondHelonavyRankelonrId {
  import RankelonrId._
  val HelonavyRankelonrIds: Selont[String] = Selont(
    PostNuxProdRankelonr.toString,
  )
}
