package com.X.follow_recommendations.common.features

import com.X.core_workflows.user_model.thriftscala.UserState
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.pipeline.PipelineQuery

case object UserStateFeature extends Feature[PipelineQuery, Option[UserState]] {}
