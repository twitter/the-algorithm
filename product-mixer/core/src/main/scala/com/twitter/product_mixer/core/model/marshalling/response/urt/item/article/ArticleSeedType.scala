package com.twitter.product_mixer.core.model.marshalling.response.urt.item.article

sealed trait ArticleSeedType

/**
 * Seed UTEG with a user's following list (1st degree network)
 */
case object FollowingListSeed extends ArticleSeedType

/**
 * Seed UTEG with a user's friends of friends (follow graph + 1) list
 */
case object FriendsOfFriendsSeed extends ArticleSeedType

/**
 * Seed UTEG with a given lists' members
 */
case object ListIdSeed extends ArticleSeedType
