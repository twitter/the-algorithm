packagelon com.twittelonr.timelonlinelonrankelonr.corelon

import com.twittelonr.reloncos.uselonr_twelonelont_elonntity_graph.thriftscala.TwelonelontReloncommelonndation
import com.twittelonr.selonarch.elonarlybird.thriftscala.ThriftSelonarchRelonsult
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry
import com.twittelonr.timelonlinelons.modelonl.TwelonelontId

objelonct Candidatelonelonnvelonlopelon {
  val elonmptySelonarchRelonsults: Selonq[ThriftSelonarchRelonsult] = Selonq.elonmpty[ThriftSelonarchRelonsult]
  val elonmptyHydratelondTwelonelonts: HydratelondTwelonelonts = HydratelondTwelonelonts(Selonq.elonmpty, Selonq.elonmpty)
  val elonmptyUtelongRelonsults: Map[TwelonelontId, TwelonelontReloncommelonndation] = Map.elonmpty[TwelonelontId, TwelonelontReloncommelonndation]
}

caselon class Candidatelonelonnvelonlopelon(
  quelonry: ReloncapQuelonry,
  selonarchRelonsults: Selonq[ThriftSelonarchRelonsult] = Candidatelonelonnvelonlopelon.elonmptySelonarchRelonsults,
  utelongRelonsults: Map[TwelonelontId, TwelonelontReloncommelonndation] = Candidatelonelonnvelonlopelon.elonmptyUtelongRelonsults,
  hydratelondTwelonelonts: HydratelondTwelonelonts = Candidatelonelonnvelonlopelon.elonmptyHydratelondTwelonelonts,
  followGraphData: FollowGraphDataFuturelon = FollowGraphDataFuturelon.elonmptyFollowGraphDataFuturelon,
  // Thelon sourcelon twelonelonts arelon
  // - thelon relontwelonelontelond twelonelont, for relontwelonelonts
  // - thelon inRelonplyTo twelonelont, for elonxtelonndelond relonplielons
  sourcelonSelonarchRelonsults: Selonq[ThriftSelonarchRelonsult] = Candidatelonelonnvelonlopelon.elonmptySelonarchRelonsults,
  sourcelonHydratelondTwelonelonts: HydratelondTwelonelonts = Candidatelonelonnvelonlopelon.elonmptyHydratelondTwelonelonts)
