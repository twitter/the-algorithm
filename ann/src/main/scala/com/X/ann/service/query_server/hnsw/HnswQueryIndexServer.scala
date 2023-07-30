package com.X.ann.service.query_server.hnsw

import com.X.ann.common.Distance
import com.X.ann.common._
import com.X.ann.common.thriftscala.{RuntimeParams => ServiceRuntimeParams}
import com.X.ann.hnsw.HnswCommon
import com.X.ann.hnsw.HnswParams
import com.X.ann.hnsw.TypedHnswIndex
import com.X.ann.service.query_server.common.QueryableProvider
import com.X.ann.service.query_server.common.RefreshableQueryable
import com.X.ann.service.query_server.common.UnsafeQueryIndexServer
import com.X.ann.service.query_server.common.ValidatedIndexPathProvider
import com.X.ann.service.query_server.common.warmup.Warmup
import com.X.bijection.Injection
import com.X.conversions.DurationOps.richDurationFromInt
import com.X.search.common.file.AbstractFile
import com.X.search.common.file.FileUtils
import com.X.util.Duration
import com.X.util.FuturePool

// Creating a separate hnsw query server object, since unit test require non singleton server.
object HnswQueryIndexServer extends HnswQueryableServer

class HnswQueryableServer extends UnsafeQueryIndexServer[HnswParams] {
  private val IndexGroupPrefix = "group_"

  // given a directory, how to load it as a queryable index
  def queryableProvider[T, D <: Distance[D]]: QueryableProvider[T, HnswParams, D] =
    new QueryableProvider[T, HnswParams, D] {
      override def provideQueryable(
        dir: AbstractFile
      ): Queryable[T, HnswParams, D] = {
        TypedHnswIndex.loadIndex[T, D](
          dimension(),
          unsafeMetric.asInstanceOf[Metric[D]],
          idInjection[T](),
          ReadWriteFuturePool(FuturePool.interruptible(executor)),
          dir
        )
      }
    }

  private def buildQueryable[T, D <: Distance[D]](
    dir: AbstractFile,
    grouped: Boolean
  ): Queryable[T, HnswParams, D] = {
    val queryable = if (refreshable()) {
      logger.info(s"build refreshable queryable")
      val updatableQueryable = new RefreshableQueryable(
        grouped,
        dir,
        queryableProvider.asInstanceOf[QueryableProvider[T, HnswParams, D]],
        ValidatedIndexPathProvider(
          minIndexSizeBytes(),
          maxIndexSizeBytes(),
          statsReceiver.scope("validated_index_provider")
        ),
        statsReceiver.scope("refreshable_queryable"),
        updateInterval = refreshableInterval().minutes
      )
      // init first load of index and also schedule the following reloads
      updatableQueryable.start()
      updatableQueryable.asInstanceOf[QueryableGrouped[T, HnswParams, D]]
    } else {
      logger.info(s"build non-refreshable queryable")
      queryableProvider.provideQueryable(dir).asInstanceOf[Queryable[T, HnswParams, D]]
    }

    logger.info("Hnsw queryable created....")
    queryable
  }

  override def unsafeQueryableMap[T, D <: Distance[D]]: Queryable[T, HnswParams, D] = {
    val dir = FileUtils.getFileHandle(indexDirectory())
    buildQueryable(dir, grouped())
  }

  override val runtimeInjection: Injection[HnswParams, ServiceRuntimeParams] =
    HnswCommon.RuntimeParamsInjection

  protected override def warmup(): Unit =
    if (warmup_enabled()) new HNSWWarmup(unsafeQueryableMap, dimension()).warmup()
}

class HNSWWarmup(hnsw: Queryable[_, HnswParams, _], dimension: Int) extends Warmup {
  protected def minSuccessfulTries: Int = 100
  protected def maxTries: Int = 1000
  protected def timeout: Duration = 50.milliseconds
  protected def randomQueryDimension: Int = dimension

  def warmup(): Unit = {
    run(
      name = "queryWithDistance",
      f = hnsw
        .queryWithDistance(randomQuery(), 100, HnswParams(ef = 800))
    )
  }
}
