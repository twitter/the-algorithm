package com.twitter.product_mixer.core.service.filter_executor

import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.service.ExecutorResult

case class FilterExecutorResult[Candidate](
  result: Seq[Candidate],
  individualFilterResults: Seq[IndividualFilterResults[Candidate]])
    extends ExecutorResult

sealed trait IndividualFilterResults[+Candidate]
case class ConditionalFilterDisabled(identifier: FilterIdentifier)
    extends IndividualFilterResults[Nothing]
case class FilterExecutorIndividualResult[+Candidate](
  identifier: FilterIdentifier,
  kept: Seq[Candidate],
  removed: Seq[Candidate])
    extends IndividualFilterResults[Candidate]
