package com.twitter.graph_feature_service.worker.util

//These classes are to help the GraphContainer choose the right data structure to answer queries
sealed trait GraphType

object FollowGraph etwittertends GraphType

object FavoriteGraph etwittertends GraphType

object RetweetGraph etwittertends GraphType

object ReplyGraph etwittertends GraphType

object MentionGraph etwittertends GraphType

object MutualFollowGraph etwittertends GraphType
