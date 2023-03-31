package com.twitter.graph_feature_service.worker.util

//These classes are to help the GraphContainer choose the right data structure to answer queries
sealed trait GraphType

object FollowGraph extends GraphType

object FavoriteGraph extends GraphType

object RetweetGraph extends GraphType

object ReplyGraph extends GraphType

object MentionGraph extends GraphType

object MutualFollowGraph extends GraphType
