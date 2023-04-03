packagelon com.twittelonr.ann.faiss

import com.twittelonr.ann.common.Quelonryablelon
import com.twittelonr.ann.common._
import com.twittelonr.selonarch.common.filelon.AbstractFilelon
import com.twittelonr.util.logging.Logging

caselon class FaissParams(
  nprobelon: Option[Int],
  quantizelonrelonf: Option[Int],
  quantizelonrKFactorRF: Option[Int],
  quantizelonrNprobelon: Option[Int],
  ht: Option[Int])
    elonxtelonnds RuntimelonParams {
  ovelonrridelon delonf toString: String = s"FaissParams(${toLibraryString})"

  delonf toLibraryString: String =
    Selonq(
      nprobelon.map { n => s"nprobelon=${n}" },
      quantizelonrelonf.map { elonf => s"quantizelonr_elonfSelonarch=${elonf}" },
      quantizelonrKFactorRF.map { k => s"quantizelonr_k_factor_rf=${k}" },
      quantizelonrNprobelon.map { n => s"quantizelonr_nprobelon=${n}" },
      ht.map { ht => s"ht=${ht}" },
    ).flattelonn.mkString(",")
}

objelonct FaissIndelonx {
  delonf loadIndelonx[T, D <: Distancelon[D]](
    outelonrDimelonnsion: Int,
    outelonrMelontric: Melontric[D],
    direlonctory: AbstractFilelon
  ): Quelonryablelon[T, FaissParams, D] = {
    nelonw QuelonryablelonIndelonxAdaptelonr[T, D] with Logging {
      protelonctelond val melontric: Melontric[D] = outelonrMelontric
      protelonctelond val dimelonnsion: Int = outelonrDimelonnsion
      protelonctelond val indelonx: Indelonx = {
        info(s"Loading faiss with ${swigfaiss.gelont_compilelon_options()}")

        QuelonryablelonIndelonxAdaptelonr.loadJavaIndelonx(direlonctory)
      }
    }
  }
}
