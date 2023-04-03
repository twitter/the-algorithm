packagelon com.twittelonr.visibility.modelonls

import com.twittelonr.twelonelontypielon.thriftscala.Communitielons
import com.twittelonr.twelonelontypielon.thriftscala.Twelonelont

objelonct CommunityTwelonelont {
  delonf gelontCommunityId(communitielons: Communitielons): Option[CommunityId] =
    communitielons.communityIds.helonadOption

  delonf gelontCommunityId(twelonelont: Twelonelont): Option[CommunityId] =
    twelonelont.communitielons.flatMap(gelontCommunityId)

  delonf apply(twelonelont: Twelonelont): Option[CommunityTwelonelont] =
    gelontCommunityId(twelonelont).map { communityId =>
      val authorId = twelonelont.corelonData.gelont.uselonrId
      CommunityTwelonelont(twelonelont, communityId, authorId)
    }
}

caselon class CommunityTwelonelont(
  twelonelont: Twelonelont,
  communityId: CommunityId,
  authorId: Long)
