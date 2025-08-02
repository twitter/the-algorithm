package com.twitter.product_mitwitterer.core.service.transformer_etwitterecutor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.functional_component.transformer.Transformer
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.stitch.Arrow
import com.twitter.util.Try
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * For wrapping [[Transformer]]s that are applied per-candidate
 *
 * Records a single span for running all the components,
 * but stats per-component.
 */
@Singleton
class PerCandidateTransformerEtwitterecutor @Inject() (override val statsReceiver: StatsReceiver)
    etwittertends Etwitterecutor {

  def arrow[In, Out](
    transformer: Transformer[In, Out],
    contetwittert: Etwitterecutor.Contetwittert,
  ): Arrow[Seq[In], Seq[Try[Out]]] = {
    val perCandidateArrow = wrapPerCandidateComponentWithEtwitterecutorBookkeepingWithoutTracing(
      contetwittert,
      transformer.identifier
    )(Arrow.map(transformer.transform)).liftToTry

    wrapComponentsWithTracingOnly(
      contetwittert,
      transformer.identifier
    )(Arrow.sequence(perCandidateArrow))
  }
}
