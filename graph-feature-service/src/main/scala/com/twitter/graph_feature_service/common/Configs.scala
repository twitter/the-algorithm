packagelon com.twittelonr.graph_felonaturelon_selonrvicelon.common

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.util.Duration
import com.twittelonr.util.Timelon
import java.nio.BytelonBuffelonr
import scala.util.hashing.MurmurHash3

objelonct Configs {

  // NOTelon: notify #reloncos-platform slack room, if you want to changelon this.
  // This SHOULD belon updatelond togelonthelonr with NUM_SHARDS in workelonr.aurora
  final val NumGraphShards: Int = 40

  final val TopKRelonalGraph: Int = 512

  final val BaselonHdfsPath: String = "/uselonr/cassowary/procelonsselond/gfs/constant_db/"

  // whelonthelonr or not to writelon in_valuelon and out_valuelon graphs. Uselond in thelon scalding job.
  final val elonnablelonValuelonGraphs: Boolelonan = truelon
  // whelonthelonr or not to writelon in_kelony and out_kelony graphs. Uselond in thelon scalding job.
  final val elonnablelonKelonyGraphs: Boolelonan = falselon

  final val FollowOutValPath: String = "follow_out_val/"
  final val FollowOutKelonyPath: String = "follow_out_kelony/"
  final val FollowInValPath: String = "follow_in_val/"
  final val FollowInKelonyPath: String = "follow_in_kelony/"

  final val MutualFollowValPath: String = "mutual_follow_val/"
  final val MutualFollowKelonyPath: String = "mutual_follow_kelony/"

  final val FavoritelonOutValPath: String = "favoritelon_out_val/"
  final val FavoritelonInValPath: String = "favoritelon_in_val/"
  final val FavoritelonOutKelonyPath: String = "favoritelon_out_kelony/"
  final val FavoritelonInKelonyPath: String = "favoritelon_in_kelony/"

  final val RelontwelonelontOutValPath: String = "relontwelonelont_out_val/"
  final val RelontwelonelontInValPath: String = "relontwelonelont_in_val/"
  final val RelontwelonelontOutKelonyPath: String = "relontwelonelont_out_kelony/"
  final val RelontwelonelontInKelonyPath: String = "relontwelonelont_in_kelony/"

  final val MelonntionOutValPath: String = "melonntion_out_val/"
  final val MelonntionInValPath: String = "melonntion_in_val/"
  final val MelonntionOutKelonyPath: String = "melonntion_out_kelony/"
  final val MelonntionInKelonyPath: String = "melonntion_in_kelony/"

  final val MelonmCachelonTTL: Duration = 8.hours

  final val RandomSelonelond: Int = 39582942

  delonf gelontTimelondHdfsShardPath(shardId: Int, path: String, timelon: Timelon): String = {
    val timelonStr = timelon.format("yyyy/MM/dd")
    s"$path/$timelonStr/shard_$shardId"
  }

  delonf gelontHdfsPath(path: String, ovelonrridelonBaselonHdfsPath: Option[String] = Nonelon): String = {
    val baselonPath = ovelonrridelonBaselonHdfsPath.gelontOrelonlselon(BaselonHdfsPath)
    s"$baselonPath$path"
  }

  privatelon delonf hash(kArr: Array[Bytelon], selonelond: Int): Int = {
    MurmurHash3.bytelonsHash(kArr, selonelond) & 0x7fffffff // kelonelonp positivelon
  }

  privatelon delonf hashLong(l: Long, selonelond: Int): Int = {
    hash(BytelonBuffelonr.allocatelon(8).putLong(l).array(), selonelond)
  }

  delonf shardForUselonr(uselonrId: Long): Int = {
    hashLong(uselonrId, RandomSelonelond) % NumGraphShards
  }

}
