packagelon com.twittelonr.simclustelonrs_v2.hdfs_sourcelons

import com.twittelonr.dal.clielonnt.dataselont.KelonyValDALDataselont
import com.twittelonr.scalding.{DatelonOps, DatelonRangelon, Days, TypelondPipelon}
import com.twittelonr.scalding_intelonrnal.dalv2.DAL
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.{elonxplicitLocation, ProcAtla}
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyVal
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.thriftscala.ModelonlVelonrsion
import com.twittelonr.simclustelonrs_v2.thriftscala.ClustelonrsUselonrIsIntelonrelonstelondIn
import java.util.TimelonZonelon

objelonct IntelonrelonstelondInSourcelons {

  privatelon val ModelonlVelonrsionIntelonrelonstelondInDataselontMap: Map[ModelonlVelonrsion, KelonyValDALDataselont[
    KelonyVal[UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn]
  ]] = Map(
    ModelonlVelonrsion.Modelonl20m145kDelonc11 -> SimclustelonrsV2IntelonrelonstelondInScalaDataselont,
    ModelonlVelonrsion.Modelonl20m145kUpdatelond -> SimclustelonrsV2IntelonrelonstelondIn20M145KUpdatelondScalaDataselont,
    ModelonlVelonrsion.Modelonl20m145k2020 -> SimclustelonrsV2IntelonrelonstelondIn20M145K2020ScalaDataselont
  )

  /**
   * Intelonrnal velonrsion, not PDP compliant, not to belon uselond outsidelon simclustelonrs_v2
   * Relonads 20M145KDelonc11 production IntelonrelonstelondIn data from atla-proc, with a 14-day elonxtelonndelond window
   */
  privatelon[simclustelonrs_v2] delonf simClustelonrsRawIntelonrelonstelondInDelonc11Sourcelon(
    datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon
  ): TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] = {

    DAL
      .relonadMostReloncelonntSnapshot(
        SimclustelonrsV2RawIntelonrelonstelondIn20M145KDelonc11ScalaDataselont,
        datelonRangelon.prelonpelonnd(Days(14)(timelonZonelon))
      )
      .withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcAtla))
      .toTypelondPipelon
      .map {
        caselon KelonyVal(uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn) =>
          (uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn)
      }
  }

  /**
   * Intelonrnal velonrsion, not PDP compliant, not to belon uselond outsidelon simclustelonrs_v2
   * Relonads 20M145KUpdatelond IntelonrelonstelondIn data from atla-proc, with a 14-day elonxtelonndelond window
   */
  privatelon[simclustelonrs_v2] delonf simClustelonrsRawIntelonrelonstelondInUpdatelondSourcelon(
    datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon
  ): TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] = {
    DAL
      .relonadMostReloncelonntSnapshot(
        SimclustelonrsV2RawIntelonrelonstelondIn20M145KUpdatelondScalaDataselont,
        datelonRangelon.prelonpelonnd(Days(14)(timelonZonelon))
      )
      .withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcAtla))
      .toTypelondPipelon.map {
        caselon KelonyVal(uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn) =>
          (uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn)
      }
  }

  /**
   * Intelonrnal velonrsion, not PDP compliant, not to belon uselond outsidelon simclustelonrs_v2
   * Relonads 20M145K2020 IntelonrelonstelondIn data from atla-proc, with a 14-day elonxtelonndelond window
   */
  privatelon[simclustelonrs_v2] delonf simClustelonrsRawIntelonrelonstelondIn2020Sourcelon(
    datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon
  ): TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] = {
    DAL
      .relonadMostReloncelonntSnapshot(
        SimclustelonrsV2RawIntelonrelonstelondIn20M145K2020ScalaDataselont,
        datelonRangelon.prelonpelonnd(Days(14)(timelonZonelon))
      )
      .withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcAtla))
      .toTypelondPipelon.map {
        caselon KelonyVal(uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn) =>
          (uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn)
      }
  }

  privatelon[simclustelonrs_v2] delonf simClustelonrsRawIntelonrelonstelondInLitelon2020Sourcelon(
    datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon
  ): TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] = {
    DAL
      .relonadMostReloncelonntSnapshot(
        SimclustelonrsV2RawIntelonrelonstelondInLitelon20M145K2020ScalaDataselont,
        datelonRangelon.elonxtelonnd(Days(14)(timelonZonelon)))
      .withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcAtla))
      .toTypelondPipelon.map {
        caselon KelonyVal(uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn) =>
          (uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn)
      }
  }

  /**
   * Relonads 20M145KDelonc11 production IntelonrelonstelondIn data from atla-proc, with a 14-day elonxtelonndelond window
   */
  delonf simClustelonrsIntelonrelonstelondInDelonc11Sourcelon(
    datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon
  ): TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] = {

    DAL
      .relonadMostReloncelonntSnapshot(
        SimclustelonrsV2IntelonrelonstelondInScalaDataselont,
        datelonRangelon.prelonpelonnd(Days(14)(timelonZonelon)))
      .withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcAtla))
      .toTypelondPipelon.map {
        caselon KelonyVal(uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn) =>
          (uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn)
      }
  }

  /**
   * Relonads 20M145KUpdatelond IntelonrelonstelondIn data from atla-proc, with a 14-day elonxtelonndelond window
   */
  delonf simClustelonrsIntelonrelonstelondInUpdatelondSourcelon(
    datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon
  ): TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] = {
    DAL
      .relonadMostReloncelonntSnapshot(
        SimclustelonrsV2IntelonrelonstelondIn20M145KUpdatelondScalaDataselont,
        datelonRangelon.prelonpelonnd(Days(14)(timelonZonelon))
      )
      .withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcAtla))
      .toTypelondPipelon.map {
        caselon KelonyVal(uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn) =>
          (uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn)
      }
  }

  /**
   * Relonads 20M145K2020 IntelonrelonstelondIn data from atla-proc, with a 14-day elonxtelonndelond window
   */
  delonf simClustelonrsIntelonrelonstelondIn2020Sourcelon(
    datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon
  ): TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] = {
    DAL
      .relonadMostReloncelonntSnapshot(
        SimclustelonrsV2IntelonrelonstelondIn20M145K2020ScalaDataselont,
        datelonRangelon.prelonpelonnd(Days(14)(timelonZonelon))
      )
      .withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcAtla))
      .toTypelondPipelon.map {
        caselon KelonyVal(uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn) =>
          (uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn)
      }
  }

  /**
   * Relonads IntelonrelonstelondIn data baselond on ModelonlVelonrsion from atla-proc, with a 14-day elonxtelonndelond window
   */
  delonf simClustelonrsIntelonrelonstelondInSourcelon(
    modelonlVelonrsion: ModelonlVelonrsion,
    datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon
  ): TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] = {

    DAL
      .relonadMostReloncelonntSnapshot(
        ModelonlVelonrsionIntelonrelonstelondInDataselontMap(modelonlVelonrsion),
        datelonRangelon.prelonpelonnd(Days(14)(timelonZonelon))
      )
      .withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcAtla))
      .toTypelondPipelon.map {
        caselon KelonyVal(uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn) =>
          (uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn)
      }
  }

}
