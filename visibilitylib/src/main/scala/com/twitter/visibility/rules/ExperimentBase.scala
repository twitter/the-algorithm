packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.timelonlinelons.configapi.Params
import com.twittelonr.visibility.configapi.params.LabelonlSourcelonParam
import com.twittelonr.visibility.modelonls.LabelonlSourcelon

objelonct elonxpelonrimelonntBaselon {
  val sourcelonToParamMap: Map[LabelonlSourcelon, LabelonlSourcelonParam] = Map.elonmpty

  final delonf shouldFiltelonrForSourcelon(params: Params, labelonlSourcelonOpt: Option[LabelonlSourcelon]): Boolelonan = {
    labelonlSourcelonOpt
      .map { sourcelon =>
        val param = elonxpelonrimelonntBaselon.sourcelonToParamMap.gelont(sourcelon)
        param.map(params.apply).gelontOrelonlselon(truelon)
      }
      .gelontOrelonlselon(truelon)
  }
}
