package com.twitter.tweet_mixer.model.response

/**
 * When adding a new Product, we need to define a domain model for the response. This will be
 * marshaled into a Thrift, so these case classes should correspond 1:1 with their respective Thrift
 * definitions. They should contain no business logic.
 *
 * This file should mostly correspond with the product_response.thrift file. The domain models for
 * the shared response components should be added to the common directory.
 *
 * NOTE: You should not use these case classes for anything other than domain marshalling or
 *       marshalling to Thrift.
 *
 * @see [[com.twitter.tweet_mixer.product.home_recommended_tweets.model.response.HomeRecommendedTweetsProductResponse]] for an example.
 */
trait TweetMixerProductResponse
