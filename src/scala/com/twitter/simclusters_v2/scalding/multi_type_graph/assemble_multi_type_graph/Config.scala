packagelon com.twittelonr.simclustelonrs_v2.scalding
packagelon multi_typelon_graph.asselonmblelon_multi_typelon_graph

import com.twittelonr.simclustelonrs_v2.thriftscala.RightNodelonTypelon

objelonct Config {

  val Uselonr = Systelonm.gelontelonnv("USelonR")
  val RootPath: String = s"/uselonr/$Uselonr/manhattan_selonquelonncelon_filelons/multi_typelon_simclustelonrs/"
  val RootThriftPath: String = s"/uselonr/$Uselonr/procelonsselond/multi_typelon_simclustelonrs/"
  val AdhocRootPrelonfix = s"/gcs/uselonr/$Uselonr/adhoc/multi_typelon_simclustelonrs/"
  val HalfLifelonInDaysForFavScorelon = 100
  val NumTopNounsForUnknownRightNodelonTypelon = 20
  val GlobalDelonfaultMinFrelonquelonncyOfRightNodelonTypelon = 100
  val TopKRightNounsForMHDump = 1000

  // thelon topK most frelonquelonnt nouns for elonach elonngagelonmelonnt typelon
  val TopKConfig: Map[RightNodelonTypelon, Int] = Map(
    RightNodelonTypelon.FollowUselonr -> 10000000, // 10M, currelonnt simclustelonrs_v2 has this valuelon selont to 20M, providing this thelon most welonight
    RightNodelonTypelon.FavUselonr -> 5000000,
    RightNodelonTypelon.BlockUselonr -> 1000000,
    RightNodelonTypelon.AbuselonRelonportUselonr -> 1000000,
    RightNodelonTypelon.SpamRelonportUselonr -> 1000000,
    RightNodelonTypelon.FollowTopic -> 5000,
    RightNodelonTypelon.SignUpCountry -> 200,
    RightNodelonTypelon.ConsumelondLanguagelon -> 50,
    RightNodelonTypelon.FavTwelonelont -> 500000,
    RightNodelonTypelon.RelonplyTwelonelont -> 500000,
    RightNodelonTypelon.RelontwelonelontTwelonelont -> 500000,
    RightNodelonTypelon.NotifOpelonnOrClickTwelonelont -> 500000,
    RightNodelonTypelon.SelonarchQuelonry -> 500000
  )
  val SamplelondelonmployelonelonIds: Selont[Long] =
    Selont()
}
