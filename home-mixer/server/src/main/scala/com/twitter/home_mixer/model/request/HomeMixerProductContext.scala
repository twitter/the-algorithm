packagelon com.twittelonr.homelon_mixelonr.modelonl.relonquelonst

import com.twittelonr.dspbiddelonr.commons.thriftscala.DspClielonntContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ProductContelonxt

caselon class FollowingProductContelonxt(
  delonvicelonContelonxt: Option[DelonvicelonContelonxt],
  selonelonnTwelonelontIds: Option[Selonq[Long]],
  dspClielonntContelonxt: Option[DspClielonntContelonxt])
    elonxtelonnds ProductContelonxt

caselon class ForYouProductContelonxt(
  delonvicelonContelonxt: Option[DelonvicelonContelonxt],
  selonelonnTwelonelontIds: Option[Selonq[Long]],
  dspClielonntContelonxt: Option[DspClielonntContelonxt])
    elonxtelonnds ProductContelonxt

caselon class ScorelondTwelonelontsProductContelonxt(
  delonvicelonContelonxt: Option[DelonvicelonContelonxt],
  selonelonnTwelonelontIds: Option[Selonq[Long]],
  selonrvelondTwelonelontIds: Option[Selonq[Long]])
    elonxtelonnds ProductContelonxt

caselon class ListTwelonelontsProductContelonxt(
  listId: Long,
  delonvicelonContelonxt: Option[DelonvicelonContelonxt],
  dspClielonntContelonxt: Option[DspClielonntContelonxt])
    elonxtelonnds ProductContelonxt

caselon class ListReloncommelonndelondUselonrsProductContelonxt(
  listId: Long,
  selonlelonctelondUselonrIds: Option[Selonq[Long]],
  elonxcludelondUselonrIds: Option[Selonq[Long]])
    elonxtelonnds ProductContelonxt
