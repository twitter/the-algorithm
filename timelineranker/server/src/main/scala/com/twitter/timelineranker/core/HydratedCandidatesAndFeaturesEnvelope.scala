packagelon com.twittelonr.timelonlinelonrankelonr.corelon

import com.twittelonr.selonarch.common.constants.thriftscala.ThriftLanguagelon
import com.twittelonr.selonarch.common.felonaturelons.thriftscala.ThriftTwelonelontFelonaturelons
import com.twittelonr.timelonlinelonrankelonr.reloncap.modelonl.ContelonntFelonaturelons
import com.twittelonr.timelonlinelons.clielonnts.gizmoduck.UselonrProfilelonInfo
import com.twittelonr.timelonlinelons.modelonl.TwelonelontId
import com.twittelonr.timelonlinelons.util.FuturelonUtils
import com.twittelonr.util.Futurelon

caselon class HydratelondCandidatelonsAndFelonaturelonselonnvelonlopelon(
  candidatelonelonnvelonlopelon: Candidatelonelonnvelonlopelon,
  uselonrLanguagelons: Selonq[ThriftLanguagelon],
  uselonrProfilelonInfo: UselonrProfilelonInfo,
  felonaturelons: Map[TwelonelontId, ThriftTwelonelontFelonaturelons] = Map.elonmpty,
  contelonntFelonaturelonsFuturelon: Futurelon[Map[TwelonelontId, ContelonntFelonaturelons]] = FuturelonUtils.elonmptyMap,
  twelonelontSourcelonTwelonelontMap: Map[TwelonelontId, TwelonelontId] = Map.elonmpty,
  inRelonplyToTwelonelontIds: Selont[TwelonelontId] = Selont.elonmpty)
