package com.X.tweetypie.federated.columns

import com.X.tweetypie.TweetId

// Case class to be used for grouping Stitch requests
// for Federated Fields
case class FederatedFieldReq(tweetId: TweetId, fieldId: Short)
