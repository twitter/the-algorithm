package com.ExTwitter.follow_recommendations.common.models

import com.ExTwitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.ExTwitter.timelines.configapi.HasParams

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
