package com.ExTwitter.cr_mixer.source_signal

import com.ExTwitter.core_workflows.user_model.thriftscala.UserState
import com.ExTwitter.cr_mixer.config.TimeoutConfig
import com.ExTwitter.cr_mixer.source_signal.SourceFetcher.FetcherQuery
import com.ExTwitter.simclusters_v2.common.UserId
import com.ExTwitter.timelines.configapi.Params
import com.ExTwitter.cr_mixer.thriftscala.{Product => TProduct}
import com.ExTwitter.finagle.GlobalRequestTimeoutException
import com.ExTwitter.finagle.mux.ClientDiscardedRequestException
import com.ExTwitter.finagle.mux.ServerApplicationError
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.util.Future
import com.ExTwitter.util.TimeoutException
import org.apache.thrift.TApplicationException
import com.ExTwitter.util.logging.Logging

/**
 * A SourceFetcher is a trait which, given a [[FetcherQuery]], returns [[ResultType]]
 * The main purposes of a SourceFetcher is to provide a consistent interface for source fetch
 * logic, and provides default functions, including:
 * - Identification
 * - Observability
 * - Timeout settings
 * - Exception Handling
 */
trait SourceFetcher[ResultType] extends ReadableStore[FetcherQuery, ResultType] with Logging {

  protected final val timer = com.ExTwitter.finagle.util.DefaultTimer
  protected final def identifier: String = this.getClass.getSimpleName
  protected def stats: StatsReceiver
  protected def timeoutConfig: TimeoutConfig

  /***
   * Use FeatureSwitch to decide if a specific source is enabled.
   */
  def isEnabled(query: FetcherQuery): Boolean

  /***
   * This function fetches the raw sources and process them.
   * Custom stats tracking can be added depending on the type of ResultType
   */
  def fetchAndProcess(
    query: FetcherQuery,
  ): Future[Option[ResultType]]

  /***
   * Side-effect function to track stats for signal fetching and processing.
   */
  def trackStats(
    query: FetcherQuery
  )(
    func: => Future[Option[ResultType]]
  ): Future[Option[ResultType]]

  /***
   * This function is called by the top level class to fetch sources. It executes the pipeline to
   * fetch raw data, process and transform the sources. Exceptions, Stats, and timeout control are
   * handled here.
   */
  override def get(
    query: FetcherQuery
  ): Future[Option[ResultType]] = {
    val scopedStats = stats.scope(query.product.originalName)
    if (isEnabled(query)) {
      scopedStats.counter("gate_enabled").incr()
      trackStats(query)(fetchAndProcess(query))
        .raiseWithin(timeoutConfig.signalFetchTimeout)(timer)
        .onFailure { e =>
          scopedStats.scope("exceptions").counter(e.getClass.getSimpleName).incr()
        }
        .rescue {
          case _: TimeoutException | _: GlobalRequestTimeoutException | _: TApplicationException |
              _: ClientDiscardedRequestException |
              _: ServerApplicationError // TApplicationException inside
              =>
            Future.None
          case e =>
            logger.info(e)
            Future.None
        }
    } else {
      scopedStats.counter("gate_disabled").incr()
      Future.None
    }
  }
}

object SourceFetcher {

  /***
   * Every SourceFetcher all share the same input: FetcherQuery
   */
  case class FetcherQuery(
    userId: UserId,
    product: TProduct,
    userState: UserState,
    params: Params)

}
