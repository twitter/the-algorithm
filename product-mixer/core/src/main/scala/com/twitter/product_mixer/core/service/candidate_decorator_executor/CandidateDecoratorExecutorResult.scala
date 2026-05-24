package com.twitter.product_mixer.core.service.candidate_decorator_executor

import com.twitter.product_mixer.core.functional_component.decorator.Decoration
import com.twitter.product_mixer.core.service.ExecutorResult

case class CandidateDecoratorExecutorResult(result: Seq[Decoration]) extends ExecutorResult
