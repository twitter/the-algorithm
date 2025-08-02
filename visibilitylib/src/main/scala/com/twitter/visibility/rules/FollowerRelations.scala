package com.twitter.visibility.rules

import com.twitter.visibility.features.AuthorMutesViewer
import com.twitter.visibility.rules.Condition.BooleanFeatureCondition
import com.twitter.visibility.rules.Condition.ProtectedViewer
import com.twitter.visibility.rules.Reason.Unspecified

object FollowerRelations {

  case object AuthorMutesViewerFeature etwittertends BooleanFeatureCondition(AuthorMutesViewer)

  object AuthorMutesViewerRule
      etwittertends OnlyWhenNotAuthorViewerRule(
        action = Drop(Unspecified),
        condition = AuthorMutesViewerFeature)

  object ProtectedViewerRule
      etwittertends OnlyWhenNotAuthorViewerRule(action = Drop(Unspecified), condition = ProtectedViewer)

}
