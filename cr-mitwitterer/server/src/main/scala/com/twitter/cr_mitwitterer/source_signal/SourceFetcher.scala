package com.twitter.cr_mitwitterer.source_signal

import com.twitter.core_workflows.user_model.thriftscala.UserState
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.source_signal.SourceFetcher.FetcherQuery
import com.twitter.simclusters_v2.common.UserId
import com.twitter.timelines.configapi.Params
import com.twitter.cr_mitwitterer.thriftscala.{Product => TProduct}
import com.twitter.finagle.GlobalRequestTimeoutEtwitterception
import com.twitter.finagle.mutwitter.ClientDiscardedRequestEtwitterception
import com.twitter.finagle.mutwitter.ServerApplicationError
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future
import com.twitter.util.TimeoutEtwitterception
import org.apache.thrift.TApplicationEtwitterception
import com.twitter.util.logging.Logging

/**
 * A SourceFetcher is a trait which, given a [[FetcherQuery]], returns [[ResultType]]
 * The main purposes of a SourceFetcher is to provide a consistent interface for source fetch
 * logic, and provides default functions, including:
 * - Identification
 * - Observability
 * - Timeout settings
 * - Etwitterception Handling
 */
trait SourceFetcher[ResultType] etwittertends ReadableStore[FetcherQuery, ResultType] with Logging {

  protected final val timer = com.twitter.finagle.util.DefaultTimer
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
   * This function is called by the top level class to fetch sources. It etwitterecutes the pipeline to
   * fetch raw data, process and transform the sources. Etwitterceptions, Stats, and timeout control are
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
          scopedStats.scope("etwitterceptions").counter(e.getClass.getSimpleName).incr()
        }
        .rescue {
          case _: TimeoutEtwitterception | _: GlobalRequestTimeoutEtwitterception | _: TApplicationEtwitterception |
              _: ClientDiscardedRequestEtwitterception |
              _: ServerApplicationError // TApplicationEtwitterception inside
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
