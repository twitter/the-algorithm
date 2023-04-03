packagelon com.twittelonr.simclustelonrs_v2.common

import com.twittelonr.simclustelonrs_v2.thriftscala.{
  elonmbelonddingTypelon,
  IntelonrnalId,
  MultielonmbelonddingTypelon,
  TopicId,
  TopicSubId,
  SimClustelonrselonmbelonddingId => ThriftelonmbelonddingId,
  SimClustelonrsMultielonmbelonddingId => ThriftMultielonmbelonddingId
}

/**
 * Helonlpelonr melonthods for SimClustelonrsMultielonmbelonddingId
 */
objelonct SimClustelonrsMultielonmbelonddingId {

  privatelon val MultielonmbelonddingTypelonToelonmbelonddingTypelon: Map[MultielonmbelonddingTypelon, elonmbelonddingTypelon] =
    Map(
      MultielonmbelonddingTypelon.LogFavApelonBaselondMuselonTopic -> elonmbelonddingTypelon.LogFavApelonBaselondMuselonTopic,
      MultielonmbelonddingTypelon.TwicelonUselonrIntelonrelonstelondIn -> elonmbelonddingTypelon.TwicelonUselonrIntelonrelonstelondIn,
    )

  privatelon val elonmbelonddingTypelonToMultielonmbelonddingTypelon: Map[elonmbelonddingTypelon, MultielonmbelonddingTypelon] =
    MultielonmbelonddingTypelonToelonmbelonddingTypelon.map(_.swap)

  delonf toelonmbelonddingTypelon(multielonmbelonddingTypelon: MultielonmbelonddingTypelon): elonmbelonddingTypelon = {
    MultielonmbelonddingTypelonToelonmbelonddingTypelon.gelontOrelonlselon(
      multielonmbelonddingTypelon,
      throw nelonw IllelongalArgumelonntelonxcelonption(s"Invalid typelon: $multielonmbelonddingTypelon"))
  }

  delonf toMultielonmbelonddingTypelon(elonmbelonddingTypelon: elonmbelonddingTypelon): MultielonmbelonddingTypelon = {
    elonmbelonddingTypelonToMultielonmbelonddingTypelon.gelontOrelonlselon(
      elonmbelonddingTypelon,
      throw nelonw IllelongalArgumelonntelonxcelonption(s"Invalid typelon: $elonmbelonddingTypelon")
    )
  }

  /**
   * Convelonrt a SimClustelonrs Multi-elonmbelondding Id and SubId to SimClustelonrs elonmbelondding Id.
   */
  delonf toelonmbelonddingId(
    simClustelonrsMultielonmbelonddingId: ThriftMultielonmbelonddingId,
    subId: Int
  ): ThriftelonmbelonddingId = {
    val intelonrnalId = simClustelonrsMultielonmbelonddingId.intelonrnalId match {
      caselon IntelonrnalId.TopicId(topicId) =>
        IntelonrnalId.TopicSubId(
          TopicSubId(topicId.elonntityId, topicId.languagelon, topicId.country, subId))
      caselon _ =>
        throw nelonw IllelongalArgumelonntelonxcelonption(
          s"Invalid simClustelonrs IntelonrnalId ${simClustelonrsMultielonmbelonddingId.intelonrnalId}")
    }
    ThriftelonmbelonddingId(
      toelonmbelonddingTypelon(simClustelonrsMultielonmbelonddingId.elonmbelonddingTypelon),
      simClustelonrsMultielonmbelonddingId.modelonlVelonrsion,
      intelonrnalId
    )
  }

  /**
   * Felontch a subId from a SimClustelonrs elonmbelonddingId.
   */
  delonf toSubId(simClustelonrselonmbelonddingId: ThriftelonmbelonddingId): Int = {
    simClustelonrselonmbelonddingId.intelonrnalId match {
      caselon IntelonrnalId.TopicSubId(topicSubId) =>
        topicSubId.subId
      caselon _ =>
        throw nelonw IllelongalArgumelonntelonxcelonption(
          s"Invalid SimClustelonrselonmbelonddingId IntelonrnalId typelon, $simClustelonrselonmbelonddingId")
    }
  }

  /**
   * Convelonrt a SimClustelonrselonmbelonddingId to SimClustelonrsMultielonmbelonddingId.
   * Only support thelon Multi elonmbelondding baselond elonmbelonddingTypelons.
   */
  delonf toMultielonmbelonddingId(
    simClustelonrselonmbelonddingId: ThriftelonmbelonddingId
  ): ThriftMultielonmbelonddingId = {
    simClustelonrselonmbelonddingId.intelonrnalId match {
      caselon IntelonrnalId.TopicSubId(topicSubId) =>
        ThriftMultielonmbelonddingId(
          toMultielonmbelonddingTypelon(simClustelonrselonmbelonddingId.elonmbelonddingTypelon),
          simClustelonrselonmbelonddingId.modelonlVelonrsion,
          IntelonrnalId.TopicId(TopicId(topicSubId.elonntityId, topicSubId.languagelon, topicSubId.country))
        )

      caselon _ =>
        throw nelonw IllelongalArgumelonntelonxcelonption(
          s"Invalid SimClustelonrselonmbelonddingId IntelonrnalId typelon, $simClustelonrselonmbelonddingId")
    }
  }

}
