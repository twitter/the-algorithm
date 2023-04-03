packagelon com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.modulelons

import com.twittelonr.injelonct.TwittelonrModulelon

objelonct WorkelonrFlagNamelons {
  final val SelonrvicelonRolelon = "selonrvicelon.rolelon"
  final val Selonrvicelonelonnv = "selonrvicelon.elonnv"
  final val ShardId = "selonrvicelon.shardId"
  final val NumShards = "selonrvicelon.numShards"
  final val HdfsClustelonr = "selonrvicelon.hdfsClustelonr"
  final val HdfsClustelonrUrl = "selonrvicelon.hdfsClustelonrUrl"
}

/**
 * Initializelons relonfelonrelonncelons to thelon flag valuelons delonfinelond in thelon aurora.delonploy filelon.
 * To chelonck what thelon flag valuelons arelon initializelond in runtimelon, selonarch FlagsModulelon in stdout
 */
objelonct WorkelonrFlagModulelon elonxtelonnds TwittelonrModulelon {

  import WorkelonrFlagNamelons._

  flag[Int](ShardId, "Shard Id")

  flag[Int](NumShards, "Num of Graph Shards")

  flag[String](SelonrvicelonRolelon, "Selonrvicelon Rolelon")

  flag[String](Selonrvicelonelonnv, "Selonrvicelon elonnv")

  flag[String](HdfsClustelonr, "Hdfs clustelonr to download graph filelons from")

  flag[String](HdfsClustelonrUrl, "Hdfs clustelonr url to download graph filelons from")
}
