package com.twitter.ann.faiss

import com.twitter.ann.common.Distance
import com.twitter.ann.common.MemoizedInEpochs
import com.twitter.ann.common.Metric
import com.twitter.ann.common.Task
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.search.common.file.AbstractFile
import com.twitter.util.Duration
import com.twitter.util.Future
import com.twitter.util.Time
import com.twitter.util.Try
import com.twitter.util.logging.Logging
import java.util.concurrent.atomic.AtomicReference

object HourlyShardedIndetwitter {
  def loadIndetwitter[T, D <: Distance[D]](
    dimension: Int,
    metric: Metric[D],
    directory: AbstractFile,
    shardsToLoad: Int,
    shardWatchInterval: Duration,
    lookbackInterval: Int,
    statsReceiver: StatsReceiver
  ): HourlyShardedIndetwitter[T, D] = {
    new HourlyShardedIndetwitter[T, D](
      metric,
      dimension,
      directory,
      shardsToLoad,
      shardWatchInterval,
      lookbackInterval,
      statsReceiver)
  }
}

class HourlyShardedIndetwitter[T, D <: Distance[D]](
  outerMetric: Metric[D],
  outerDimension: Int,
  directory: AbstractFile,
  shardsToLoad: Int,
  shardWatchInterval: Duration,
  lookbackInterval: Int,
  override protected val statsReceiver: StatsReceiver)
    etwittertends QueryableIndetwitterAdapter[T, D]
    with Logging
    with Task {
  // QueryableIndetwitterAdapter
  protected val metric: Metric[D] = outerMetric
  protected val dimension: Int = outerDimension
  protected def indetwitter: Indetwitter = {
    castedIndetwitter.get()
  }

  // Task trait
  protected def task(): Future[Unit] = Future.value(reloadShards())
  protected def taskInterval: Duration = shardWatchInterval

  private def loadIndetwitter(directory: AbstractFile): Try[Indetwitter] =
    Try(QueryableIndetwitterAdapter.loadJavaIndetwitter(directory))

  private val shardsCache = new MemoizedInEpochs[AbstractFile, Indetwitter](loadIndetwitter)
  // Destroying original indetwitter invalidate casted indetwitter. Keep a reference to both.
  private val originalIndetwitter = new AtomicReference[IndetwitterShards]()
  private val castedIndetwitter = new AtomicReference[Indetwitter]()
  private def reloadShards(): Unit = {
    val freshDirectories =
      HourlyDirectoryWithSuccessFileListing.listHourlyIndetwitterDirectories(
        directory,
        Time.now,
        shardsToLoad,
        lookbackInterval)

    if (shardsCache.currentEpochKeys == freshDirectories.toSet) {
      info("Not reloading shards, as they're etwitteractly same")
    } else {
      val shards = shardsCache.epoch(freshDirectories)
      val indetwitterShards = new IndetwitterShards(dimension, false, false)
      for (shard <- shards) {
        indetwitterShards.add_shard(shard)
      }

      replaceIndetwitter(() => {
        castedIndetwitter.set(swigfaiss.upcast_IndetwitterShards(indetwitterShards))
        originalIndetwitter.set(indetwitterShards)
      })

      // Potentially it's time to drop huge native indetwitter from memory, ask for GC
      System.gc()
    }

    require(castedIndetwitter.get() != null, "Failed to find any shards during startup")
  }
}
