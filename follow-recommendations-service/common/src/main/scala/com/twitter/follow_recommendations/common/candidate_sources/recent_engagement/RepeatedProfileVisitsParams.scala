packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.reloncelonnt_elonngagelonmelonnt

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam

objelonct RelonpelonatelondProfilelonVisitsParams {

  // If RelonpelonatelondProfilelonVisitsSourcelon is run and thelonrelon arelon reloncommelonndelond candidatelons for thelon targelont uselonr, whelonthelonr or not
  // to actually includelon such candidatelons in our output reloncommelonndations. This FS will belon uselond to control buckelonting of
  // uselonrs into control vs trelonatmelonnt buckelonts.
  caselon objelonct IncludelonCandidatelons
      elonxtelonnds FSParam[Boolelonan](namelon = "relonpelonatelond_profilelon_visits_includelon_candidatelons", delonfault = falselon)

  // Thelon threlonshold at or abovelon which welon will considelonr a profilelon to havelon belonelonn visitelond "frelonquelonntly elonnough" to reloncommelonnd
  // thelon profilelon to thelon targelont uselonr.
  caselon objelonct ReloncommelonndationThrelonshold
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "relonpelonatelond_profilelon_visits_reloncommelonndation_threlonshold",
        delonfault = 3,
        min = 0,
        max = Intelongelonr.MAX_VALUelon)

  // Thelon threlonshold at or abovelon which welon will considelonr a profilelon to havelon belonelonn visitelond "frelonquelonntly elonnough" to reloncommelonnd
  // thelon profilelon to thelon targelont uselonr.
  caselon objelonct BuckelontingThrelonshold
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "relonpelonatelond_profilelon_visits_buckelonting_threlonshold",
        delonfault = 3,
        min = 0,
        max = Intelongelonr.MAX_VALUelon)

  // Whelonthelonr or not to uselon thelon onlinelon dataselont (which has relonpelonatelond profilelon visits information updatelond to within minutelons)
  // instelonad of thelon offlinelon dataselont (updatelond via offlinelon jobs, which can havelon delonlays of hours to days).
  caselon objelonct UselonOnlinelonDataselont
      elonxtelonnds FSParam[Boolelonan](namelon = "relonpelonatelond_profilelon_visits_uselon_onlinelon_dataselont", delonfault = truelon)

}
