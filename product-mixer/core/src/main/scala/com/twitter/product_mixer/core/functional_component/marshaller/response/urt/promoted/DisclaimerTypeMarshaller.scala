package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted

import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.DisclaimerIssue
import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.DisclaimerPolitical
import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.DisclaimerType
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DisclaimerTypeMarshaller @Inject() () {

  def apply(disclaimerType: DisclaimerType): urt.DisclaimerType = disclaimerType match {
    case DisclaimerPolitical => urt.DisclaimerType.Political
    case DisclaimerIssue => urt.DisclaimerType.Issue
  }
}
