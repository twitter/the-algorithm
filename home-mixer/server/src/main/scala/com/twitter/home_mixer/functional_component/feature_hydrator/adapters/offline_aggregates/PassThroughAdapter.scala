packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator.adaptelonrs.offlinelon_aggrelongatelons

import com.twittelonr.ml.api.DataReloncord
import com.twittelonr.ml.api.IReloncordOnelonToOnelonAdaptelonr

objelonct PassThroughAdaptelonr elonxtelonnds IReloncordOnelonToOnelonAdaptelonr[Selonq[DataReloncord]] {
  ovelonrridelon delonf adaptToDataReloncord(reloncord: Selonq[DataReloncord]): DataReloncord =
    reloncord.helonadOption.gelontOrelonlselon(nelonw DataReloncord)

  // This is not neloncelonssary and should not belon uselond.
  ovelonrridelon delonf gelontFelonaturelonContelonxt = ???
}
