package com.twitter.graph_feature_service.worker.modules

import com.twitter.inject.TwitterModule

object WorkerFlagNames {
  final val ServiceRole = "service.role"
  final val ServiceEnv = "service.env"
  final val ShardId = "service.shardId"
  final val NumShards = "service.numShards"
  final val HdfsCluster = "service.hdfsCluster"
  final val HdfsClusterUrl = "service.hdfsClusterUrl"
}

/**
 * Initializes references to the flag values defined in the aurora.deploy file.
 * To check what the flag values are initialized in runtime, search FlagsModule in stdout
 */
object WorkerFlagModule extends TwitterModule {

  import WorkerFlagNames._

  flag[Int](ShardId, "Shard Id")

  flag[Int](NumShards, "Num of Graph Shards")

  flag[String](ServiceRole, "Service Role")

  flag[String](ServiceEnv, "Service Env")

  flag[String](HdfsCluster, "Hdfs cluster to download graph files from")

  flag[String](HdfsClusterUrl, "Hdfs cluster url to download graph files from")
}
