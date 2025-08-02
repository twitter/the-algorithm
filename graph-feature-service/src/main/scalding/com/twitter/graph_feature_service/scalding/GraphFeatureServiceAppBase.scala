package com.twitter.graph_feature_service.scalding

import com.twitter.scalding._
import com.twitter.scalding_internal.job.TwitterEtwitterecutionApp
import com.twitter.scalding_internal.job.analytics_batch.{
  AnalyticsBatchEtwitterecution,
  AnalyticsBatchEtwitterecutionArgs,
  BatchDescription,
  BatchFirstTime,
  BatchIncrement,
  TwitterScheduledEtwitterecutionApp
}
import java.util.TimeZone

/**
 * Each job only needs to implement this runOnDateRange() function. It makes it easier for testing.
 */
trait GraphFeatureServiceBaseJob {
  implicit val timeZone: TimeZone = DateOps.UTC
  implicit val dateParser: DateParser = DateParser.default

  def runOnDateRange(
    enableValueGraphs: Option[Boolean] = None,
    enableKeyGraphs: Option[Boolean] = None
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit]

  /**
   * Print customized counters in the log
   */
  def printerCounters[T](etwitterecution: Etwitterecution[T]): Etwitterecution[Unit] = {
    etwitterecution.getCounters
      .flatMap {
        case (_, counters) =>
          counters.toMap.toSeq
            .sortBy(e => (e._1.group, e._1.counter))
            .foreach {
              case (statKey, value) =>
                println(s"${statKey.group}\t${statKey.counter}\t$value")
            }
          Etwitterecution.unit
      }
  }
}

/**
 * Trait that wraps things about adhoc jobs.
 */
trait GraphFeatureServiceAdhocBaseApp etwittertends TwitterEtwitterecutionApp with GraphFeatureServiceBaseJob {
  override def job: Etwitterecution[Unit] = Etwitterecution.withId { implicit uniqueId =>
    Etwitterecution.getArgs.flatMap { args: Args =>
      implicit val dateRange: DateRange = DateRange.parse(args.list("date"))(timeZone, dateParser)
      printerCounters(runOnDateRange())
    }
  }
}

/**
 * Trait that wraps things about scheduled jobs.
 *
 * A new daily app only needs to declare the starting date.
 */
trait GraphFeatureServiceScheduledBaseApp
    etwittertends TwitterScheduledEtwitterecutionApp
    with GraphFeatureServiceBaseJob {

  def firstTime: RichDate // for etwitterample: RichDate("2018-02-21")

  def batchIncrement: Duration = Days(1)

  override def scheduledJob: Etwitterecution[Unit] = Etwitterecution.withId { implicit uniqueId =>
    val analyticsArgs = AnalyticsBatchEtwitterecutionArgs(
      batchDesc = BatchDescription(getClass.getName),
      firstTime = BatchFirstTime(firstTime),
      batchIncrement = BatchIncrement(batchIncrement)
    )

    AnalyticsBatchEtwitterecution(analyticsArgs) { implicit dateRange =>
      printerCounters(runOnDateRange())
    }
  }
}
