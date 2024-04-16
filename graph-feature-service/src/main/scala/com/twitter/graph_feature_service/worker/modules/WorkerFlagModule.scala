package com.tw ter.graph_feature_serv ce.worker.modules

 mport com.tw ter. nject.Tw terModule

object WorkerFlagNa s {
  f nal val Serv ceRole = "serv ce.role"
  f nal val Serv ceEnv = "serv ce.env"
  f nal val Shard d = "serv ce.shard d"
  f nal val NumShards = "serv ce.numShards"
  f nal val HdfsCluster = "serv ce.hdfsCluster"
  f nal val HdfsClusterUrl = "serv ce.hdfsClusterUrl"
}

/**
 *  n  al zes references to t  flag values def ned  n t  aurora.deploy f le.
 * To c ck what t  flag values are  n  al zed  n runt  , search FlagsModule  n stdout
 */
object WorkerFlagModule extends Tw terModule {

   mport WorkerFlagNa s._

  flag[ nt](Shard d, "Shard  d")

  flag[ nt](NumShards, "Num of Graph Shards")

  flag[Str ng](Serv ceRole, "Serv ce Role")

  flag[Str ng](Serv ceEnv, "Serv ce Env")

  flag[Str ng](HdfsCluster, "Hdfs cluster to download graph f les from")

  flag[Str ng](HdfsClusterUrl, "Hdfs cluster url to download graph f les from")
}
