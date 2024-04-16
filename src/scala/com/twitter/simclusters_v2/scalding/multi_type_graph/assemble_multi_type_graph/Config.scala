package com.tw ter.s mclusters_v2.scald ng
package mult _type_graph.assemble_mult _type_graph

 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNodeType

object Conf g {

  val User = System.getenv("USER")
  val RootPath: Str ng = s"/user/$User/manhattan_sequence_f les/mult _type_s mclusters/"
  val RootThr ftPath: Str ng = s"/user/$User/processed/mult _type_s mclusters/"
  val AdhocRootPref x = s"/gcs/user/$User/adhoc/mult _type_s mclusters/"
  val HalfL fe nDaysForFavScore = 100
  val NumTopNounsForUnknownR ghtNodeType = 20
  val GlobalDefaultM nFrequencyOfR ghtNodeType = 100
  val TopKR ghtNounsForMHDump = 1000

  // t  topK most frequent nouns for each engage nt type
  val TopKConf g: Map[R ghtNodeType,  nt] = Map(
    R ghtNodeType.FollowUser -> 10000000, // 10M, current s mclusters_v2 has t  value set to 20M, prov d ng t  t  most   ght
    R ghtNodeType.FavUser -> 5000000,
    R ghtNodeType.BlockUser -> 1000000,
    R ghtNodeType.AbuseReportUser -> 1000000,
    R ghtNodeType.SpamReportUser -> 1000000,
    R ghtNodeType.FollowTop c -> 5000,
    R ghtNodeType.S gnUpCountry -> 200,
    R ghtNodeType.Consu dLanguage -> 50,
    R ghtNodeType.FavT et -> 500000,
    R ghtNodeType.ReplyT et -> 500000,
    R ghtNodeType.Ret etT et -> 500000,
    R ghtNodeType.Not fOpenOrCl ckT et -> 500000,
    R ghtNodeType.SearchQuery -> 500000
  )
  val SampledEmployee ds: Set[Long] =
    Set()
}
