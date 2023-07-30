package com.X.product_mixer.core.service.candidate_decorator_executor

import com.X.product_mixer.core.functional_component.decorator.Decoration
import com.X.product_mixer.core.service.ExecutorResult

case class CandidateDecoratorExecutorResult(result: Seq[Decoration]) extends ExecutorResult
