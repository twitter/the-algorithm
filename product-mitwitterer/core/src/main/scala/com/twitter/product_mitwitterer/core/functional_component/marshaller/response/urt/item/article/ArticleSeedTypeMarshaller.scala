package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.article

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.article.ArticleSeedType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.article.FollowingListSeed
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.article.FriendsOfFriendsSeed
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.article.ListIdSeed
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ArticleSeedTypeMarshaller @Inject() () {

  def apply(articleSeedType: ArticleSeedType): urt.ArticleSeedType =
    articleSeedType match {
      case FollowingListSeed => urt.ArticleSeedType.FollowingList
      case FriendsOfFriendsSeed => urt.ArticleSeedType.FriendsOfFriends
      case ListIdSeed => urt.ArticleSeedType.ListId
    }
}
