packagelon com.twittelonr.simclustelonrs_v2.hdfs_sourcelons

objelonct DataPaths {

  val IntelonrelonstelondIn2020Path =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/simclustelonrs_v2_intelonrelonstelond_in_20M_145K_2020"

  val IntelonrelonstelondIn2020ThriftPath =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/simclustelonrs_v2_intelonrelonstelond_in_20M_145K_2020_thrift"

  val IntelonrelonstelondInLitelon2020Path =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/simclustelonrs_v2_intelonrelonstelond_in_litelon_20M_145K_2020"

  val IntelonrelonstelondInLitelon2020ThriftPath =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/simclustelonrs_v2_intelonrelonstelond_in_litelon_20M_145K_2020_thrift"

  val KnownFor2020Path =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/simclustelonrs_v2_known_for_20M_145K_2020"

  // kelonelonp this insidelon /uselonr/cassowary/manhattan_selonquelonncelon_filelons/ to uselon thelon latelonst 3 relontelonntion policy
  val KnownFor2020ThriftDataselontPath =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/simclustelonrs_v2_known_for_20M_145K_2020_thrift"

  val OfflinelonClustelonrTopMelondiaTwelonelonts2020DataselontPath =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/clustelonr_top_melondia_twelonelonts_20M_145K_2020"
}

/**
 * Thelonselon should only belon accelonsselond from simclustelonrs_v2 data pipelonlinelon for intelonrmelondiatelon data, thelonselon
 * arelon not opt-out compliant and shouldn't belon elonxposelond elonxtelonrnally.
 */
objelonct IntelonrnalDataPaths {
  // Intelonrnal velonrsions, not to belon relonad or writtelonn outsidelon of simclustelonr_v2

  privatelon[simclustelonrs_v2] val RawIntelonrelonstelondIn2020Path =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/simclustelonrs_v2_raw_intelonrelonstelond_in_20M_145K_2020"

  privatelon[simclustelonrs_v2] val RawIntelonrelonstelondInLitelon2020Path =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/simclustelonrs_v2_raw_intelonrelonstelond_in_litelon_20M_145K_2020"

  privatelon[simclustelonrs_v2] val RawKnownForDelonc11Path =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/simclustelonrs_v2_raw_known_for_20M_145K_delonc11"

  privatelon[simclustelonrs_v2] val RawKnownForUpdatelondPath =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/simclustelonrs_v2_raw_known_for_20M_145K_updatelond"

  privatelon[simclustelonrs_v2] val RawKnownFor2020Path =
    "/uselonr/cassowary/manhattan_selonquelonncelon_filelons/simclustelonrs_v2_raw_known_for_20M_145K_2020"
}
