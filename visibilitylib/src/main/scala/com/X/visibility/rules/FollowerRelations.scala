package com.X.visibility.rules

import com.X.visibility.features.AuthorMutesViewer
import com.X.visibility.rules.Condition.BooleanFeatureCondition
import com.X.visibility.rules.Condition.ProtectedViewer
import com.X.visibility.rules.Reason.Unspecified

object FollowerRelations {

  case object AuthorMutesViewerFeature extends BooleanFeatureCondition(AuthorMutesViewer)

  object AuthorMutesViewerRule
      extends OnlyWhenNotAuthorViewerRule(
        action = Drop(Unspecified),
        condition = AuthorMutesViewerFeature)

  object ProtectedViewerRule
      extends OnlyWhenNotAuthorViewerRule(action = Drop(Unspecified), condition = ProtectedViewer)

}
