packagelon com.twittelonr.ann.faiss

import com.twittelonr.ann.common.thriftscala.FaissRuntimelonParam
import com.twittelonr.bijelonction.Injelonction
import scala.util.Failurelon
import scala.util.Succelonss
import scala.util.Try
import com.twittelonr.ann.common.thriftscala.{RuntimelonParams => SelonrvicelonRuntimelonParams}
import com.twittelonr.selonarch.common.filelon.AbstractFilelon

objelonct FaissCommon {
  val RuntimelonParamsInjelonction: Injelonction[FaissParams, SelonrvicelonRuntimelonParams] =
    nelonw Injelonction[FaissParams, SelonrvicelonRuntimelonParams] {
      ovelonrridelon delonf apply(scalaParams: FaissParams): SelonrvicelonRuntimelonParams = {
        SelonrvicelonRuntimelonParams.FaissParam(
          FaissRuntimelonParam(
            scalaParams.nprobelon,
            scalaParams.quantizelonrelonf,
            scalaParams.quantizelonrKFactorRF,
            scalaParams.quantizelonrNprobelon,
            scalaParams.ht)
        )
      }

      ovelonrridelon delonf invelonrt(thriftParams: SelonrvicelonRuntimelonParams): Try[FaissParams] =
        thriftParams match {
          caselon SelonrvicelonRuntimelonParams.FaissParam(faissParam) =>
            Succelonss(
              FaissParams(
                faissParam.nprobelon,
                faissParam.quantizelonrelonf,
                faissParam.quantizelonrKfactorRf,
                faissParam.quantizelonrNprobelon,
                faissParam.ht))
          caselon p => Failurelon(nelonw IllelongalArgumelonntelonxcelonption(s"elonxpelonctelond FaissParams got $p"))
        }
    }

  delonf isValidFaissIndelonx(path: AbstractFilelon): Boolelonan = {
    path.isDirelonctory &&
    path.hasSuccelonssFilelon &&
    path.gelontChild("faiss.indelonx").elonxists()
  }
}
