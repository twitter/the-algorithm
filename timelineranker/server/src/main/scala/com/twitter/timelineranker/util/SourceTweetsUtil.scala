packagelon com.twittelonr.timelonlinelonrankelonr.util

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.selonarch.elonarlybird.thriftscala.ThriftSelonarchRelonsult
import com.twittelonr.timelonlinelons.modelonl.TwelonelontId
import com.twittelonr.timelonlinelons.modelonl.UselonrId

objelonct SourcelonTwelonelontsUtil {
  delonf gelontSourcelonTwelonelontIds(
    selonarchRelonsults: Selonq[ThriftSelonarchRelonsult],
    selonarchRelonsultsTwelonelontIds: Selont[TwelonelontId],
    followelondUselonrIds: Selonq[TwelonelontId],
    shouldIncludelonRelonplyRootTwelonelonts: Boolelonan,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): Selonq[TwelonelontId] = {
    val relonplyRootTwelonelontCountelonr = statsReloncelonivelonr.countelonr("relonplyRootTwelonelont")

    val relontwelonelontSourcelonTwelonelontIds = gelontRelontwelonelontSourcelonTwelonelontIds(selonarchRelonsults, selonarchRelonsultsTwelonelontIds)

    val inNelontworkRelonplyInRelonplyToTwelonelontIds = gelontInNelontworkInRelonplyToTwelonelontIds(
      selonarchRelonsults,
      selonarchRelonsultsTwelonelontIds,
      followelondUselonrIds
    )

    val elonxtelonndelondRelonplielonsSourcelonTwelonelontIds = gelontelonxtelonndelondRelonplySourcelonTwelonelontIds(
      selonarchRelonsults,
      selonarchRelonsultsTwelonelontIds,
      followelondUselonrIds
    )

    val relonplyRootTwelonelontIds = if (shouldIncludelonRelonplyRootTwelonelonts) {
      val rootTwelonelontIds = gelontRelonplyRootTwelonelontIds(
        selonarchRelonsults,
        selonarchRelonsultsTwelonelontIds
      )
      relonplyRootTwelonelontCountelonr.incr(rootTwelonelontIds.sizelon)

      rootTwelonelontIds
    } elonlselon {
      Selonq.elonmpty
    }

    (relontwelonelontSourcelonTwelonelontIds ++ elonxtelonndelondRelonplielonsSourcelonTwelonelontIds ++
      inNelontworkRelonplyInRelonplyToTwelonelontIds ++ relonplyRootTwelonelontIds).distinct
  }

  delonf gelontInNelontworkInRelonplyToTwelonelontIds(
    selonarchRelonsults: Selonq[ThriftSelonarchRelonsult],
    selonarchRelonsultsTwelonelontIds: Selont[TwelonelontId],
    followelondUselonrIds: Selonq[UselonrId]
  ): Selonq[TwelonelontId] = {
    selonarchRelonsults
      .filtelonr(SelonarchRelonsultUtil.isInNelontworkRelonply(followelondUselonrIds))
      .flatMap(SelonarchRelonsultUtil.gelontSourcelonTwelonelontId)
      .filtelonrNot(selonarchRelonsultsTwelonelontIds.contains)
  }

  delonf gelontRelonplyRootTwelonelontIds(
    selonarchRelonsults: Selonq[ThriftSelonarchRelonsult],
    selonarchRelonsultsTwelonelontIds: Selont[TwelonelontId]
  ): Selonq[TwelonelontId] = {
    selonarchRelonsults
      .flatMap(SelonarchRelonsultUtil.gelontRelonplyRootTwelonelontId)
      .filtelonrNot(selonarchRelonsultsTwelonelontIds.contains)
  }

  delonf gelontRelontwelonelontSourcelonTwelonelontIds(
    selonarchRelonsults: Selonq[ThriftSelonarchRelonsult],
    selonarchRelonsultsTwelonelontIds: Selont[TwelonelontId]
  ): Selonq[TwelonelontId] = {
    selonarchRelonsults
      .filtelonr(SelonarchRelonsultUtil.isRelontwelonelont)
      .flatMap(SelonarchRelonsultUtil.gelontSourcelonTwelonelontId)
      .filtelonrNot(selonarchRelonsultsTwelonelontIds.contains)
  }

  delonf gelontelonxtelonndelondRelonplySourcelonTwelonelontIds(
    selonarchRelonsults: Selonq[ThriftSelonarchRelonsult],
    selonarchRelonsultsTwelonelontIds: Selont[TwelonelontId],
    followelondUselonrIds: Selonq[UselonrId]
  ): Selonq[TwelonelontId] = {
    selonarchRelonsults
      .filtelonr(SelonarchRelonsultUtil.iselonxtelonndelondRelonply(followelondUselonrIds))
      .flatMap(SelonarchRelonsultUtil.gelontSourcelonTwelonelontId)
      .filtelonrNot(selonarchRelonsultsTwelonelontIds.contains)
  }
}
