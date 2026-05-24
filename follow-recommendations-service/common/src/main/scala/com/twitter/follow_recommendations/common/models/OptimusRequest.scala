package com.twitter.follow_recommendations.common.models

import com.twitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.twitter.timelines.configapi.HasParams

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
