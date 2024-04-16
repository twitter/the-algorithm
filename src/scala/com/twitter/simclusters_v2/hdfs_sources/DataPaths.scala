package com.tw ter.s mclusters_v2.hdfs_s ces

object DataPaths {

  val  nterested n2020Path =
    "/user/cassowary/manhattan_sequence_f les/s mclusters_v2_ nterested_ n_20M_145K_2020"

  val  nterested n2020Thr ftPath =
    "/user/cassowary/manhattan_sequence_f les/s mclusters_v2_ nterested_ n_20M_145K_2020_thr ft"

  val  nterested nL e2020Path =
    "/user/cassowary/manhattan_sequence_f les/s mclusters_v2_ nterested_ n_l e_20M_145K_2020"

  val  nterested nL e2020Thr ftPath =
    "/user/cassowary/manhattan_sequence_f les/s mclusters_v2_ nterested_ n_l e_20M_145K_2020_thr ft"

  val KnownFor2020Path =
    "/user/cassowary/manhattan_sequence_f les/s mclusters_v2_known_for_20M_145K_2020"

  // keep t   ns de /user/cassowary/manhattan_sequence_f les/ to use t  latest 3 retent on pol cy
  val KnownFor2020Thr ftDatasetPath =
    "/user/cassowary/manhattan_sequence_f les/s mclusters_v2_known_for_20M_145K_2020_thr ft"

  val Offl neClusterTop d aT ets2020DatasetPath =
    "/user/cassowary/manhattan_sequence_f les/cluster_top_ d a_t ets_20M_145K_2020"
}

/**
 * T se should only be accessed from s mclusters_v2 data p pel ne for  nter d ate data, t se
 * are not opt-out compl ant and shouldn't be exposed externally.
 */
object  nternalDataPaths {
  //  nternal vers ons, not to be read or wr ten outs de of s mcluster_v2

  pr vate[s mclusters_v2] val Raw nterested n2020Path =
    "/user/cassowary/manhattan_sequence_f les/s mclusters_v2_raw_ nterested_ n_20M_145K_2020"

  pr vate[s mclusters_v2] val Raw nterested nL e2020Path =
    "/user/cassowary/manhattan_sequence_f les/s mclusters_v2_raw_ nterested_ n_l e_20M_145K_2020"

  pr vate[s mclusters_v2] val RawKnownForDec11Path =
    "/user/cassowary/manhattan_sequence_f les/s mclusters_v2_raw_known_for_20M_145K_dec11"

  pr vate[s mclusters_v2] val RawKnownForUpdatedPath =
    "/user/cassowary/manhattan_sequence_f les/s mclusters_v2_raw_known_for_20M_145K_updated"

  pr vate[s mclusters_v2] val RawKnownFor2020Path =
    "/user/cassowary/manhattan_sequence_f les/s mclusters_v2_raw_known_for_20M_145K_2020"
}
