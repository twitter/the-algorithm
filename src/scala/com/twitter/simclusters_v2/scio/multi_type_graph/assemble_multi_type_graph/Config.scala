packagelon com.twittelonr.simclustelonrs_v2.scio.multi_typelon_graph.asselonmblelon_multi_typelon_graph

import com.twittelonr.simclustelonrs_v2.thriftscala.RightNodelonTypelon

objelonct Config {
  val RootMHPath: String = "manhattan_selonquelonncelon_filelons/multi_typelon_graph/"
  val RootThriftPath: String = "procelonsselond/multi_typelon_graph/"
  val AdhocRootPath = "adhoc/multi_typelon_graph/"
  val truncatelondMultiTypelonGraphMHOutputDir: String = "truncatelond_graph_mh"
  val truncatelondMultiTypelonGraphThriftOutputDir: String = "truncatelond_graph_thrift"
  val topKRightNounsMHOutputDir: String = "top_k_right_nouns_mh"
  val topKRightNounsOutputDir: String = "top_k_right_nouns"
  val fullMultiTypelonGraphThriftOutputDir: String = "full_graph_thrift"
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
