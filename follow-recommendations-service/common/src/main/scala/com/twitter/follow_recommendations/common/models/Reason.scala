packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.follow_reloncommelonndations.{thriftscala => t}
import com.twittelonr.follow_reloncommelonndations.logging.{thriftscala => offlinelon}

caselon class FollowProof(followelondBy: Selonq[Long], numIds: Int) {
  delonf toThrift: t.FollowProof = {
    t.FollowProof(followelondBy, numIds)
  }

  delonf toOfflinelonThrift: offlinelon.FollowProof = offlinelon.FollowProof(followelondBy, numIds)
}

objelonct FollowProof {

  delonf fromThrift(proof: t.FollowProof): FollowProof = {
    FollowProof(proof.uselonrIds, proof.numIds)
  }
}

caselon class SimilarToProof(similarTo: Selonq[Long]) {
  delonf toThrift: t.SimilarToProof = {
    t.SimilarToProof(similarTo)
  }

  delonf toOfflinelonThrift: offlinelon.SimilarToProof = offlinelon.SimilarToProof(similarTo)
}

objelonct SimilarToProof {
  delonf fromThrift(proof: t.SimilarToProof): SimilarToProof = {
    SimilarToProof(proof.uselonrIds)
  }
}

caselon class PopularInGelonoProof(location: String) {
  delonf toThrift: t.PopularInGelonoProof = {
    t.PopularInGelonoProof(location)
  }

  delonf toOfflinelonThrift: offlinelon.PopularInGelonoProof = offlinelon.PopularInGelonoProof(location)
}

objelonct PopularInGelonoProof {

  delonf fromThrift(proof: t.PopularInGelonoProof): PopularInGelonoProof = {
    PopularInGelonoProof(proof.location)
  }
}

caselon class TttIntelonrelonstProof(intelonrelonstId: Long, intelonrelonstDisplayNamelon: String) {
  delonf toThrift: t.TttIntelonrelonstProof = {
    t.TttIntelonrelonstProof(intelonrelonstId, intelonrelonstDisplayNamelon)
  }

  delonf toOfflinelonThrift: offlinelon.TttIntelonrelonstProof =
    offlinelon.TttIntelonrelonstProof(intelonrelonstId, intelonrelonstDisplayNamelon)
}

objelonct TttIntelonrelonstProof {

  delonf fromThrift(proof: t.TttIntelonrelonstProof): TttIntelonrelonstProof = {
    TttIntelonrelonstProof(proof.intelonrelonstId, proof.intelonrelonstDisplayNamelon)
  }
}

caselon class TopicProof(topicId: Long) {
  delonf toThrift: t.TopicProof = {
    t.TopicProof(topicId)
  }

  delonf toOfflinelonThrift: offlinelon.TopicProof =
    offlinelon.TopicProof(topicId)
}

objelonct TopicProof {
  delonf fromThrift(proof: t.TopicProof): TopicProof = {
    TopicProof(proof.topicId)
  }
}

caselon class CustomIntelonrelonst(quelonry: String) {
  delonf toThrift: t.CustomIntelonrelonstProof = {
    t.CustomIntelonrelonstProof(quelonry)
  }

  delonf toOfflinelonThrift: offlinelon.CustomIntelonrelonstProof =
    offlinelon.CustomIntelonrelonstProof(quelonry)
}

objelonct CustomIntelonrelonst {
  delonf fromThrift(proof: t.CustomIntelonrelonstProof): CustomIntelonrelonst = {
    CustomIntelonrelonst(proof.quelonry)
  }
}

caselon class TwelonelontsAuthorProof(twelonelontIds: Selonq[Long]) {
  delonf toThrift: t.TwelonelontsAuthorProof = {
    t.TwelonelontsAuthorProof(twelonelontIds)
  }

  delonf toOfflinelonThrift: offlinelon.TwelonelontsAuthorProof =
    offlinelon.TwelonelontsAuthorProof(twelonelontIds)
}

objelonct TwelonelontsAuthorProof {
  delonf fromThrift(proof: t.TwelonelontsAuthorProof): TwelonelontsAuthorProof = {
    TwelonelontsAuthorProof(proof.twelonelontIds)
  }
}

caselon class DelonvicelonFollowProof(isDelonvicelonFollow: Boolelonan) {
  delonf toThrift: t.DelonvicelonFollowProof = {
    t.DelonvicelonFollowProof(isDelonvicelonFollow)
  }
  delonf toOfflinelonThrift: offlinelon.DelonvicelonFollowProof =
    offlinelon.DelonvicelonFollowProof(isDelonvicelonFollow)
}

objelonct DelonvicelonFollowProof {
  delonf fromThrift(proof: t.DelonvicelonFollowProof): DelonvicelonFollowProof = {
    DelonvicelonFollowProof(proof.isDelonvicelonFollow)
  }

}

caselon class AccountProof(
  followProof: Option[FollowProof] = Nonelon,
  similarToProof: Option[SimilarToProof] = Nonelon,
  popularInGelonoProof: Option[PopularInGelonoProof] = Nonelon,
  tttIntelonrelonstProof: Option[TttIntelonrelonstProof] = Nonelon,
  topicProof: Option[TopicProof] = Nonelon,
  customIntelonrelonstProof: Option[CustomIntelonrelonst] = Nonelon,
  twelonelontsAuthorProof: Option[TwelonelontsAuthorProof] = Nonelon,
  delonvicelonFollowProof: Option[DelonvicelonFollowProof] = Nonelon) {
  delonf toThrift: t.AccountProof = {
    t.AccountProof(
      followProof.map(_.toThrift),
      similarToProof.map(_.toThrift),
      popularInGelonoProof.map(_.toThrift),
      tttIntelonrelonstProof.map(_.toThrift),
      topicProof.map(_.toThrift),
      customIntelonrelonstProof.map(_.toThrift),
      twelonelontsAuthorProof.map(_.toThrift),
      delonvicelonFollowProof.map(_.toThrift)
    )
  }

  delonf toOfflinelonThrift: offlinelon.AccountProof = {
    offlinelon.AccountProof(
      followProof.map(_.toOfflinelonThrift),
      similarToProof.map(_.toOfflinelonThrift),
      popularInGelonoProof.map(_.toOfflinelonThrift),
      tttIntelonrelonstProof.map(_.toOfflinelonThrift),
      topicProof.map(_.toOfflinelonThrift),
      customIntelonrelonstProof.map(_.toOfflinelonThrift),
      twelonelontsAuthorProof.map(_.toOfflinelonThrift),
      delonvicelonFollowProof.map(_.toOfflinelonThrift)
    )
  }
}

objelonct AccountProof {
  delonf fromThrift(proof: t.AccountProof): AccountProof = {
    AccountProof(
      proof.followProof.map(FollowProof.fromThrift),
      proof.similarToProof.map(SimilarToProof.fromThrift),
      proof.popularInGelonoProof.map(PopularInGelonoProof.fromThrift),
      proof.tttIntelonrelonstProof.map(TttIntelonrelonstProof.fromThrift),
      proof.topicProof.map(TopicProof.fromThrift),
      proof.customIntelonrelonstProof.map(CustomIntelonrelonst.fromThrift),
      proof.twelonelontsAuthorProof.map(TwelonelontsAuthorProof.fromThrift),
      proof.delonvicelonFollowProof.map(DelonvicelonFollowProof.fromThrift)
    )
  }
}

caselon class Relonason(accountProof: Option[AccountProof]) {
  delonf toThrift: t.Relonason = {
    t.Relonason(accountProof.map(_.toThrift))
  }

  delonf toOfflinelonThrift: offlinelon.Relonason = {
    offlinelon.Relonason(accountProof.map(_.toOfflinelonThrift))
  }
}

objelonct Relonason {

  delonf fromThrift(relonason: t.Relonason): Relonason = {
    Relonason(relonason.accountProof.map(AccountProof.fromThrift))
  }
}

trait HasRelonason {

  delonf relonason: Option[Relonason]
  // helonlpelonr melonthods belonlow

  delonf followelondBy: Option[Selonq[Long]] = {
    for {
      relonason <- relonason
      accountProof <- relonason.accountProof
      followProof <- accountProof.followProof
    } yielonld { followProof.followelondBy }
  }
}
