package com.twitter.graph_feature_service.server.modules

import com.twitter.inject.TwitterModule

object ServerFlagNames {
  final val NumWorkers = "service.num_workers"
  final val ServiceRole = "service.role"
  final val ServiceEnv = "service.env"

  final val MemCacheClientName = "service.mem_cache_client_name"
  final val MemCachePath = "service.mem_cache_path"
}

/**
 * Initializes references to the flag values defined in the aurora.deploy file.
 * To check what the flag values are initialized in runtime, search FlagsModule in stdout
 */
object ServerFlagsModule extends TwitterModule {

  import ServerFlagNames._

  flag[Int](NumWorkers, "Num of workers")

  flag[String](ServiceRole, "Service Role")

  flag[String](ServiceEnv, "Service Env")

  flag[String](MemCacheClientName, "MemCache Client Name")

  flag[String](MemCachePath, "MemCache Path")
}
