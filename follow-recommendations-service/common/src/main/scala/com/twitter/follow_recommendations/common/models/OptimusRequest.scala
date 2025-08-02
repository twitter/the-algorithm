package com.twitter.follow_recommendations.common.models

import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.timelines.configapi.HasParams

/**
Convenience trait to group together all traits needed for optimus ranking
 */
trait OptimusRequest
    etwittertends HasParams
    with HasClientContetwittert
    with HasDisplayLocation
    with HasInterestIds
    with HasDebugOptions
    with HasPreviousRecommendationsContetwittert {}
