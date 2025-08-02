package com.twitter.product_mitwitterer.core.service.filter_etwitterecutor

import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.service.EtwitterecutorResult

case class FilterEtwitterecutorResult[Candidate](
  result: Seq[Candidate],
  individualFilterResults: Seq[IndividualFilterResults[Candidate]])
    etwittertends EtwitterecutorResult

sealed trait IndividualFilterResults[+Candidate]
case class ConditionalFilterDisabled(identifier: FilterIdentifier)
    etwittertends IndividualFilterResults[Nothing]
case class FilterEtwitterecutorIndividualResult[+Candidate](
  identifier: FilterIdentifier,
  kept: Seq[Candidate],
  removed: Seq[Candidate])
    etwittertends IndividualFilterResults[Candidate]
