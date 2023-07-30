package com.X.timelineranker.repository

import com.X.timelines.visibility.model.VisibilityRule

object RepositoryBuilder {
  val VisibilityRules: Set[VisibilityRule.Value] = Set(
    VisibilityRule.Blocked,
    VisibilityRule.BlockedBy,
    VisibilityRule.Muted,
    VisibilityRule.Protected,
    VisibilityRule.AccountStatus
  )
}

trait RepositoryBuilder {
  val VisibilityRules: Set[VisibilityRule.Value] = RepositoryBuilder.VisibilityRules
}
