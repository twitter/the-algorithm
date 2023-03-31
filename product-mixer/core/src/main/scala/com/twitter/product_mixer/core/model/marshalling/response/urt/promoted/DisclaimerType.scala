package com.twitter.product_mixer.core.model.marshalling.response.urt.promoted

sealed trait DisclaimerType

object DisclaimerPolitical extends DisclaimerType
object DisclaimerIssue extends DisclaimerType
