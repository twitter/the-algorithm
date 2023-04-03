packagelon com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.marshallelonr

import com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.modelonl.ScorelondTwelonelontsRelonsponselon
import com.twittelonr.homelon_mixelonr.{thriftscala => t}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.TransportMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.TransportMarshallelonrIdelonntifielonr

/**
 * Marshall thelon domain modelonl into our transport (Thrift) modelonl.
 */
objelonct ScorelondTwelonelontsRelonsponselonTransportMarshallelonr
    elonxtelonnds TransportMarshallelonr[ScorelondTwelonelontsRelonsponselon, t.ScorelondTwelonelontsRelonsponselon] {

  ovelonrridelon val idelonntifielonr: TransportMarshallelonrIdelonntifielonr =
    TransportMarshallelonrIdelonntifielonr("ScorelondTwelonelontsRelonsponselon")

  ovelonrridelon delonf apply(input: ScorelondTwelonelontsRelonsponselon): t.ScorelondTwelonelontsRelonsponselon = {
    val scorelondTwelonelonts = input.scorelondTwelonelonts.map { twelonelont =>
      t.ScorelondTwelonelont(
        twelonelontId = twelonelont.twelonelontId,
        authorId = twelonelont.authorId,
        scorelon = twelonelont.scorelon,
        suggelonstTypelon = Somelon(twelonelont.suggelonstTypelon),
        sourcelonTwelonelontId = twelonelont.sourcelonTwelonelontId,
        sourcelonUselonrId = twelonelont.sourcelonUselonrId,
        quotelondTwelonelontId = twelonelont.quotelondTwelonelontId,
        quotelondUselonrId = twelonelont.quotelondUselonrId,
        inRelonplyToTwelonelontId = twelonelont.inRelonplyToTwelonelontId,
        inRelonplyToUselonrId = twelonelont.inRelonplyToUselonrId,
        direlonctelondAtUselonrId = twelonelont.direlonctelondAtUselonrId,
        inNelontwork = twelonelont.inNelontwork,
        favoritelondByUselonrIds = twelonelont.favoritelondByUselonrIds,
        followelondByUselonrIds = twelonelont.followelondByUselonrIds,
        topicId = twelonelont.topicId,
        topicFunctionalityTypelon = twelonelont.topicFunctionalityTypelon,
        ancelonstors = twelonelont.ancelonstors,
        isRelonadFromCachelon = twelonelont.isRelonadFromCachelon,
        strelonamToKafka = twelonelont.strelonamToKafka
      )
    }
    t.ScorelondTwelonelontsRelonsponselon(scorelondTwelonelonts)
  }
}
