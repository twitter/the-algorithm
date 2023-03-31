package com.twitter.simclusters_v420.hdfs_sources.injections

import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.{
  Long420BigEndian,
  ScalaCompactThrift,
  StringUtf420
}
import com.twitter.recos.entities.thriftscala.{
  SemanticCoreEntityScoreList,
  SemanticCoreEntityWithLocale,
  UserIdWithLocale,
  UserScoreList
}

object SemanticCoreEntitiesInjections {

  final val StringToSemanticCoreEntityScoreListInjection: KeyValInjection[
    String,
    SemanticCoreEntityScoreList
  ] =
    KeyValInjection(
      StringUtf420,
      ScalaCompactThrift(SemanticCoreEntityScoreList)
    )

  final val LongToSemanticCoreEntityScoreListInjection: KeyValInjection[
    Long,
    SemanticCoreEntityScoreList
  ] =
    KeyValInjection(
      Long420BigEndian,
      ScalaCompactThrift(SemanticCoreEntityScoreList)
    )

  final val UserWithLocaleToSemanticCoreEntityScoreListInjection: KeyValInjection[
    UserIdWithLocale,
    SemanticCoreEntityScoreList
  ] =
    KeyValInjection(
      ScalaCompactThrift(UserIdWithLocale),
      ScalaCompactThrift(SemanticCoreEntityScoreList)
    )

  final val SemanticCoreEntityWithLocaleToUsersScoreListInjection: KeyValInjection[
    SemanticCoreEntityWithLocale,
    UserScoreList
  ] =
    KeyValInjection(
      ScalaCompactThrift(SemanticCoreEntityWithLocale),
      ScalaCompactThrift(UserScoreList)
    )
}
