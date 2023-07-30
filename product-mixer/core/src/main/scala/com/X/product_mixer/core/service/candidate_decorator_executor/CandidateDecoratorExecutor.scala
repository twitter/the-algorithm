package com.X.product_mixer.core.service.candidate_decorator_executor

import com.X.finagle.stats.StatsReceiver
import com.X.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.X.product_mixer.core.functional_component.decorator.Decoration
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.service.Executor
import com.X.stitch.Arrow

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CandidateDecoratorExecutor @Inject() (override val statsReceiver: StatsReceiver)
    extends Executor {
  def arrow[Query <: PipelineQuery, Candidate <: UniversalNoun[Any]](
    decoratorOpt: Option[CandidateDecorator[Query, Candidate]],
    context: Executor.Context
  ): Arrow[(Query, Seq[CandidateWithFeatures[Candidate]]), CandidateDecoratorExecutorResult] = {
    val decoratorArrow =
      decoratorOpt match {
        case Some(decorator) =>
          val candidateDecoratorArrow =
            Arrow.flatMap[(Query, Seq[CandidateWithFeatures[Candidate]]), Seq[Decoration]] {
              case (query, candidatesWithFeatures) => decorator.apply(query, candidatesWithFeatures)
            }

          wrapComponentWithExecutorBookkeeping(context, decorator.identifier)(
            candidateDecoratorArrow)

        case _ => Arrow.value(Seq.empty[Decoration])
      }

    decoratorArrow.map(CandidateDecoratorExecutorResult)
  }
}
