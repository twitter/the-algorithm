package com.twitter.visibility.rules

import com.twitter.visibility.features.AuthorMutesViewer
import com.twitter.visibility.rules.Condition.BooleanFeatureCondition
import com.twitter.visibility.rules.Condition.ProtectedViewer
import com.twitter.visibility.rules.Reason.Unspecified

object FollowerRelations {

  case object AuthorMutesViewerFeature extends BooleanFeatureCondition(AuthorMutesViewer)

  object AuthorMutesViewerRule
      extends OnlyWhenNotAuthorViewerRule(
        action = Drop(Unspecified),
        condition = AuthorMutesViewerFeature)

  object ProtectedViewerRule
      extends OnlyWhenNotAuthorViewerRule(action = Drop(Unspecified), condition = ProtectedViewer)

}
