package com.twitter.product_mitwitterer.core.service.gate_etwitterecutor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.functional_component.gate.BaseGate
import com.twitter.product_mitwitterer.core.functional_component.gate.GateResult
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.stitch.Arrow
import com.twitter.stitch.Arrow.Iso
import com.twitter.util.Return
import com.twitter.util.Throw

import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import scala.collection.immutable.Queue

/**
 * A GateEtwitterecutor takes a Seq[Gate], etwitterecutes them all sequentially, and
 * determines a final Continue or Stop decision.
 */
@Singleton
class GateEtwitterecutor @Inject() (override val statsReceiver: StatsReceiver) etwittertends Etwitterecutor {

  private val Continue = "continue"
  private val Skipped = "skipped"
  private val Stop = "stop"

  def arrow[Query <: PipelineQuery](
    gates: Seq[BaseGate[Query]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[Query, GateEtwitterecutorResult] = {

    val gateArrows = gates.map(getIsoArrowForGate(_, contetwittert))
    val combinedArrow = isoArrowsSequentially(gateArrows)

    Arrow
      .map { query: Query => (query, GateEtwitterecutorResult(Queue.empty)) }
      .andThen(combinedArrow)
      .map {
        case (_, gateEtwitterecutorResult) =>
          // materialize the Queue into a List for faster future iterations
          GateEtwitterecutorResult(gateEtwitterecutorResult.individualGateResults.toList)
      }
  }

  /**
   * Each gate is transformed into a Iso Arrow over (Quest, List[GatewayResult]).
   *
   * This arrow:
   * - Adapts the input and output types of the underlying Gate arrow (an [[Iso[(Query, QueryResult)]])
   * - throws a [[StoppedGateEtwitterception]] if [[GateResult.continue]] is false
   * - if its not false, prepends the current results to the [[GateEtwitterecutorResult.individualGateResults]] list
   */
  private def getIsoArrowForGate[Query <: PipelineQuery](
    gate: BaseGate[Query],
    contetwittert: Etwitterecutor.Contetwittert
  ): Iso[(Query, GateEtwitterecutorResult)] = {
    val broadcastStatsReceiver =
      Etwitterecutor.broadcastStatsReceiver(contetwittert, gate.identifier, statsReceiver)

    val continueCounter = broadcastStatsReceiver.counter(Continue)
    val skippedCounter = broadcastStatsReceiver.counter(Skipped)
    val stopCounter = broadcastStatsReceiver.counter(Stop)

    val observedArrow = wrapComponentWithEtwitterecutorBookkeeping(
      contetwittert,
      gate.identifier,
      onSuccess = { gateResult: GateResult =>
        gateResult match {
          case GateResult.Continue => continueCounter.incr()
          case GateResult.Skipped => skippedCounter.incr()
          case GateResult.Stop => stopCounter.incr()
        }
      }
    )(gate.arrow)

    val inputAdapted: Arrow[(Query, GateEtwitterecutorResult), GateResult] =
      Arrow
        .map[(Query, GateEtwitterecutorResult), Query] { case (query, _) => query }
        .andThen(observedArrow)

    val zipped = Arrow.zipWithArg(inputAdapted)

    // at each step, the current `GateEtwitterecutorResult.continue` value is correct for all already run gates
    val withStoppedGatesAsEtwitterceptions = zipped.map {
      case ((query, previousResults), currentResult) if currentResult.continue =>
        Return(
          (
            query,
            GateEtwitterecutorResult(
              previousResults.individualGateResults :+ EtwitterecutedGateResult(
                gate.identifier,
                currentResult))
          ))
      case _ => Throw(StoppedGateEtwitterception(gate.identifier))
    }.lowerFromTry

    /**
     * we gather stats before converting closed gates to etwitterceptions because a closed gate
     * isn't a failure for the gate, its a normal behavior
     * but we do want to remap the the [[StoppedGateEtwitterception]] created because the [[BaseGate]] is closed
     * to the correct [[com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure]],
     * so we remap with [[wrapWithErrorHandling]]
     */
    wrapWithErrorHandling(contetwittert, gate.identifier)(withStoppedGatesAsEtwitterceptions)
  }
}
