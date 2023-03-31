package com.twitter.follow_recommendations.common.features

import com.twitter.core_workflows.user_model.thriftscala.UserState
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.pipeline.PipelineQuery

case object UserStateFeature extends Feature[PipelineQuery, Option[UserState]] {}
