package com.twitter.product_mixer.core.model.marshalling.response.urt.promoted

sealed trait DynamicPrerollType

object Amplify extends DynamicPrerollType
object Marketplace extends DynamicPrerollType
object LiveTvEvent extends DynamicPrerollType
