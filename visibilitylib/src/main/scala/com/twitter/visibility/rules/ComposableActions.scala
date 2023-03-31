package com.twitter.visibility.rules

object ComposableActions {

  object ComposableActionsWithConversationSectionAbusiveQuality {
    def unapply(
      composableActions: TweetInterstitial
    ): Option[ConversationSectionAbusiveQuality.type] = {
      composableActions.abusiveQuality
    }
  }

  object ComposableActionsWithSoftIntervention {
    def unapply(composableActions: TweetInterstitial): Option[SoftIntervention] = {
      composableActions.softIntervention match {
        case Some(si: SoftIntervention) => Some(si)
        case _ => None
      }
    }
  }

  object ComposableActionsWithInterstitialLimitedEngagements {
    def unapply(composableActions: TweetInterstitial): Option[InterstitialLimitedEngagements] = {
      composableActions.interstitial match {
        case Some(ile: InterstitialLimitedEngagements) => Some(ile)
        case _ => None
      }
    }
  }

  object ComposableActionsWithInterstitial {
    def unapply(composableActions: TweetInterstitial): Option[Interstitial] = {
      composableActions.interstitial match {
        case Some(i: Interstitial) => Some(i)
        case _ => None
      }
    }
  }

  object ComposableActionsWithAppealable {
    def unapply(composableActions: TweetInterstitial): Option[Appealable] = {
      composableActions.appealable
    }
  }
}
