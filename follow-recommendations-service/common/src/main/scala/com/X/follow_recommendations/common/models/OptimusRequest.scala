package com.X.follow_recommendations.common.models

import com.X.product_mixer.core.model.marshalling.request.HasClientContext
import com.X.timelines.configapi.HasParams

/**
Convenience trait to group together all traits needed for optimus ranking
 */
trait OptimusRequest
    extends HasParams
    with HasClientContext
    with HasDisplayLocation
    with HasInterestIds
    with HasDebugOptions
    with HasPreviousRecommendationsContext {}
