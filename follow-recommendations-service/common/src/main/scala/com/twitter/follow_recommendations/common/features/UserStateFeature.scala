package com.twitter.follow_recommendations.common.features

import com.twitter.core_workflows.user_model.thriftscala.UserState
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case object UserStateFeature etwittertends Feature[PipelineQuery, Option[UserState]] {}
