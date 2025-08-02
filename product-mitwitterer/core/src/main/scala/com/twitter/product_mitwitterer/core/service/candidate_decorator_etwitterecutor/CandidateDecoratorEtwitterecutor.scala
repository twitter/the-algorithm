package com.twitter.product_mitwitterer.core.service.candidate_decorator_etwitterecutor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mitwitterer.core.functional_component.decorator.Decoration
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.stitch.Arrow

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CandidateDecoratorEtwitterecutor @Inject() (override val statsReceiver: StatsReceiver)
    etwittertends Etwitterecutor {
  def arrow[Query <: PipelineQuery, Candidate <: UniversalNoun[Any]](
    decoratorOpt: Option[CandidateDecorator[Query, Candidate]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[(Query, Seq[CandidateWithFeatures[Candidate]]), CandidateDecoratorEtwitterecutorResult] = {
    val decoratorArrow =
      decoratorOpt match {
        case Some(decorator) =>
          val candidateDecoratorArrow =
            Arrow.flatMap[(Query, Seq[CandidateWithFeatures[Candidate]]), Seq[Decoration]] {
              case (query, candidatesWithFeatures) => decorator.apply(query, candidatesWithFeatures)
            }

          wrapComponentWithEtwitterecutorBookkeeping(contetwittert, decorator.identifier)(
            candidateDecoratorArrow)

        case _ => Arrow.value(Seq.empty[Decoration])
      }

    decoratorArrow.map(CandidateDecoratorEtwitterecutorResult)
  }
}
