package com.twitter.graph_feature_service.common

import com.twitter.conversions.DurationOps._
import com.twitter.util.Duration
import com.twitter.util.Time
import java.nio.ByteBuffer
import scala.util.hashing.MurmurHash3

object Configs {

  // NOTE: notify #recos-platform slack room, if you want to change this.
  // This SHOULD be updated together with NUM_SHARDS in worker.aurora
  final val NumGraphShards: Int = 40

  final val TopKRealGraph: Int = 512

  final val BaseHdfsPath: String = "/user/cassowary/processed/gfs/constant_db/"

  // whether or not to write in_value and out_value graphs. Used in the scalding job.
  final val EnableValueGraphs: Boolean = true
  // whether or not to write in_key and out_key graphs. Used in the scalding job.
  final val EnableKeyGraphs: Boolean = false

  final val FollowOutValPath: String = "follow_out_val/"
  final val FollowOutKeyPath: String = "follow_out_key/"
  final val FollowInValPath: String = "follow_in_val/"
  final val FollowInKeyPath: String = "follow_in_key/"

  final val MutualFollowValPath: String = "mutual_follow_val/"
  final val MutualFollowKeyPath: String = "mutual_follow_key/"

  final val FavoriteOutValPath: String = "favorite_out_val/"
  final val FavoriteInValPath: String = "favorite_in_val/"
  final val FavoriteOutKeyPath: String = "favorite_out_key/"
  final val FavoriteInKeyPath: String = "favorite_in_key/"

  final val RetweetOutValPath: String = "retweet_out_val/"
  final val RetweetInValPath: String = "retweet_in_val/"
  final val RetweetOutKeyPath: String = "retweet_out_key/"
  final val RetweetInKeyPath: String = "retweet_in_key/"

  final val MentionOutValPath: String = "mention_out_val/"
  final val MentionInValPath: String = "mention_in_val/"
  final val MentionOutKeyPath: String = "mention_out_key/"
  final val MentionInKeyPath: String = "mention_in_key/"

  final val MemCacheTTL: Duration = 8.hours

  final val RandomSeed: Int = 39582942

  def getTimedHdfsShardPath(shardId: Int, path: String, time: Time): String = {
    val timeStr = time.format("yyyy/MM/dd")
    s"$path/$timeStr/shard_$shardId"
  }

  def getHdfsPath(path: String, overrideBaseHdfsPath: Option[String] = None): String = {
    val basePath = overrideBaseHdfsPath.getOrElse(BaseHdfsPath)
    s"$basePath$path"
  }

  private def hash(kArr: Array[Byte], seed: Int): Int = {
    MurmurHash3.bytesHash(kArr, seed) & 0x7fffffff // keep positive
  }

  private def hashLong(l: Long, seed: Int): Int = {
    hash(ByteBuffer.allocate(8).putLong(l).array(), seed)
  }

  def shardForUser(userId: Long): Int = {
    hashLong(userId, RandomSeed) % NumGraphShards
  }

}
