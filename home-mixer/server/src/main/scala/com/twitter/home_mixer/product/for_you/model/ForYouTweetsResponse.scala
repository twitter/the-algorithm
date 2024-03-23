package com.ExTwitter.home_mixer.product.for_you.model

import com.ExTwitter.product_mixer.core.model.marshalling.HasMarshalling

case class ForYouTweetsResponse(tweetCandidates: Seq[Long]) extends HasMarshalling
