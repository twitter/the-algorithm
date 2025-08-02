package com.twitter.product_mitwitterer.core.service.transformer_etwitterecutor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.functional_component.transformer.Transformer
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.stitch.Arrow

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TransformerEtwitterecutor @Inject() (override val statsReceiver: StatsReceiver) etwittertends Etwitterecutor {
  def arrow[In, Out](
    transformer: Transformer[In, Out],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[In, Out] = {
    wrapComponentWithEtwitterecutorBookkeeping(
      contetwittert,
      transformer.identifier
    )(Arrow.map(transformer.transform))
  }
}
