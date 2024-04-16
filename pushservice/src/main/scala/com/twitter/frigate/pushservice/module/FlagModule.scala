package com.tw ter.fr gate.pushserv ce.module

 mport com.tw ter.app.Flag
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.convers ons.Durat onOps._

object FlagNa  {
  f nal val shard d = "serv ce.shard"
  f nal val numShards = "serv ce.num_shards"
  f nal val nackWarmupDurat on = "serv ce.nackWarmupDurat on"
  f nal val  s n mCac Off = "serv ce. s n mCac Off"
}

object FlagModule extends Tw terModule {

  val shard d: Flag[ nt] = flag[ nt](
    na  = FlagNa .shard d,
     lp = "Serv ce shard  d"
  )

  val numShards: Flag[ nt] = flag[ nt](
    na  = FlagNa .numShards,
     lp = "Number of shards"
  )

  val mrLogger sTraceAll: Flag[Boolean] = flag[Boolean](
    na  = "serv ce. sTraceAll",
     lp = "atraceflag",
    default = false
  )

  val mrLoggerNthLog: Flag[Boolean] = flag[Boolean](
    na  = "serv ce.nthLog",
     lp = "nthlog",
    default = false
  )

  val  n mCac Off: Flag[Boolean] = flag[Boolean](
    na  = FlagNa . s n mCac Off,
     lp = " s  n mCac  Off (currently only appl es for user_ alth_model_score_store_cac )",
    default = false
  )

  val mrLoggerNthVal: Flag[Long] = flag[Long](
    na  = "serv ce.nthVal",
     lp = "nthlogval",
    default = 0,
  )

  val nackWarmupDurat on: Flag[Durat on] = flag[Durat on](
    na  = FlagNa .nackWarmupDurat on,
     lp = "durat on to nack at startup",
    default = 0.seconds
  )
}
