package com.tw ter.graph_feature_serv ce.common

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  
 mport java.n o.ByteBuffer
 mport scala.ut l.hash ng.MurmurHash3

object Conf gs {

  // NOTE: not fy #recos-platform slack room,  f   want to change t .
  // T  SHOULD be updated toget r w h NUM_SHARDS  n worker.aurora
  f nal val NumGraphShards:  nt = 40

  f nal val TopKRealGraph:  nt = 512

  f nal val BaseHdfsPath: Str ng = "/user/cassowary/processed/gfs/constant_db/"

  // w t r or not to wr e  n_value and out_value graphs. Used  n t  scald ng job.
  f nal val EnableValueGraphs: Boolean = true
  // w t r or not to wr e  n_key and out_key graphs. Used  n t  scald ng job.
  f nal val EnableKeyGraphs: Boolean = false

  f nal val FollowOutValPath: Str ng = "follow_out_val/"
  f nal val FollowOutKeyPath: Str ng = "follow_out_key/"
  f nal val Follow nValPath: Str ng = "follow_ n_val/"
  f nal val Follow nKeyPath: Str ng = "follow_ n_key/"

  f nal val MutualFollowValPath: Str ng = "mutual_follow_val/"
  f nal val MutualFollowKeyPath: Str ng = "mutual_follow_key/"

  f nal val Favor eOutValPath: Str ng = "favor e_out_val/"
  f nal val Favor e nValPath: Str ng = "favor e_ n_val/"
  f nal val Favor eOutKeyPath: Str ng = "favor e_out_key/"
  f nal val Favor e nKeyPath: Str ng = "favor e_ n_key/"

  f nal val Ret etOutValPath: Str ng = "ret et_out_val/"
  f nal val Ret et nValPath: Str ng = "ret et_ n_val/"
  f nal val Ret etOutKeyPath: Str ng = "ret et_out_key/"
  f nal val Ret et nKeyPath: Str ng = "ret et_ n_key/"

  f nal val  nt onOutValPath: Str ng = " nt on_out_val/"
  f nal val  nt on nValPath: Str ng = " nt on_ n_val/"
  f nal val  nt onOutKeyPath: Str ng = " nt on_out_key/"
  f nal val  nt on nKeyPath: Str ng = " nt on_ n_key/"

  f nal val  mCac TTL: Durat on = 8.h s

  f nal val RandomSeed:  nt = 39582942

  def getT  dHdfsShardPath(shard d:  nt, path: Str ng, t  : T  ): Str ng = {
    val t  Str = t  .format("yyyy/MM/dd")
    s"$path/$t  Str/shard_$shard d"
  }

  def getHdfsPath(path: Str ng, overr deBaseHdfsPath: Opt on[Str ng] = None): Str ng = {
    val basePath = overr deBaseHdfsPath.getOrElse(BaseHdfsPath)
    s"$basePath$path"
  }

  pr vate def hash(kArr: Array[Byte], seed:  nt):  nt = {
    MurmurHash3.bytesHash(kArr, seed) & 0x7fffffff // keep pos  ve
  }

  pr vate def hashLong(l: Long, seed:  nt):  nt = {
    hash(ByteBuffer.allocate(8).putLong(l).array(), seed)
  }

  def shardForUser(user d: Long):  nt = {
    hashLong(user d, RandomSeed) % NumGraphShards
  }

}
