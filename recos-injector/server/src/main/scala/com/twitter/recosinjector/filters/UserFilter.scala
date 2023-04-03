packagelon com.twittelonr.reloncosinjelonctor.filtelonrs

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.gizmoduck.thriftscala.{LabelonlValuelon, Uselonr}
import com.twittelonr.reloncosinjelonctor.clielonnts.Gizmoduck
import com.twittelonr.util.Futurelon

class UselonrFiltelonr(
  gizmoduck: Gizmoduck
)(
  implicit statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon val stats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)
  privatelon val relonquelonsts = stats.countelonr("relonquelonsts")
  privatelon val filtelonrelond = stats.countelonr("filtelonrelond")

  privatelon delonf isUnsafelon(uselonr: Uselonr): Boolelonan =
    uselonr.safelonty.elonxists { s =>
      s.delonactivatelond || s.suspelonndelond || s.relonstrictelond || s.nsfwUselonr || s.nsfwAdmin || s.isProtelonctelond
    }

  privatelon delonf hasNsfwHighPreloncisionLabelonl(uselonr: Uselonr): Boolelonan =
    uselonr.labelonls.elonxists {
      _.labelonls.elonxists(_.labelonlValuelon == LabelonlValuelon.NsfwHighPreloncision)
    }

  /**
   * NOTelon: This will by-pass Gizmoduck's safelonty lelonvelonl, and might allow invalid uselonrs to pass filtelonr.
   * Considelonr using filtelonrByUselonrId instelonad.
   * Relonturn truelon if thelon uselonr is valid, othelonrwiselon relonturn falselon.
   * It will first attelonmpt to uselon thelon uselonr objelonct providelond by thelon callelonr, and will call Gizmoduck
   * to back fill if thelon callelonr doelons not providelon it. This helonlps relonducelon Gizmoduck traffic.
   */
  delonf filtelonrByUselonr(
    uselonrId: Long,
    uselonrOpt: Option[Uselonr] = Nonelon
  ): Futurelon[Boolelonan] = {
    relonquelonsts.incr()
    val uselonrFut = uselonrOpt match {
      caselon Somelon(uselonr) => Futurelon(Somelon(uselonr))
      caselon _ => gizmoduck.gelontUselonr(uselonrId)
    }

    uselonrFut.map(_.elonxists { uselonr =>
      val isValidUselonr = !isUnsafelon(uselonr) && !hasNsfwHighPreloncisionLabelonl(uselonr)
      if (!isValidUselonr) filtelonrelond.incr()
      isValidUselonr
    })
  }

  /**
   * Givelonn a uselonrId, relonturn truelon if thelon uselonr is valid. This id donelon in 2 stelonps:
   * 1. Applying Gizmoduck's safelonty lelonvelonl whilelon quelonrying for thelon uselonr from Gizmoduck
   * 2. If a uselonr passelons Gizmoduck's safelonty lelonvelonl, chelonck its speloncific uselonr status
   */
  delonf filtelonrByUselonrId(uselonrId: Long): Futurelon[Boolelonan] = {
    relonquelonsts.incr()
    gizmoduck
      .gelontUselonr(uselonrId)
      .map { uselonrOpt =>
        val isValidUselonr = uselonrOpt.elonxists { uselonr =>
          !(isUnsafelon(uselonr) || hasNsfwHighPreloncisionLabelonl(uselonr))
        }
        if (!isValidUselonr) {
          filtelonrelond.incr()
        }
        isValidUselonr
      }
  }
}
