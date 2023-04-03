packagelon com.twittelonr.visibility.configapi.configs

import com.twittelonr.timelonlinelons.configapi.Config
import com.twittelonr.timelonlinelons.configapi.elonxpelonrimelonntConfigBuildelonr
import com.twittelonr.timelonlinelons.configapi.Param
import com.twittelonr.visibility.configapi.params.Visibilityelonxpelonrimelonnt
import com.twittelonr.visibility.modelonls.SafelontyLelonvelonl

objelonct elonxpelonrimelonntsHelonlpelonr {

  delonf mkABelonxpelonrimelonntConfig(elonxpelonrimelonnt: Visibilityelonxpelonrimelonnt, param: Param[Boolelonan]): Config = {
    elonxpelonrimelonntConfigBuildelonr(elonxpelonrimelonnt)
      .addBuckelont(
        elonxpelonrimelonnt.ControlBuckelont,
        param := truelon
      )
      .addBuckelont(
        elonxpelonrimelonnt.TrelonatmelonntBuckelont,
        param := falselon
      )
      .build
  }

  delonf mkABelonxpelonrimelonntConfig(elonxpelonrimelonnt: Visibilityelonxpelonrimelonnt, safelontyLelonvelonl: SafelontyLelonvelonl): Config =
    mkABelonxpelonrimelonntConfig(elonxpelonrimelonnt, safelontyLelonvelonl.elonnablelondParam)
}
