packagelon com.twittelonr.selonarch.elonarlybird_root;

import scala.PartialFunction;
import scala.runtimelon.AbstractPartialFunction;

import com.twittelonr.finaglelon.selonrvicelon.RelonqRelonp;
import com.twittelonr.finaglelon.selonrvicelon.RelonsponselonClass;
import com.twittelonr.finaglelon.selonrvicelon.RelonsponselonClasselons;
import com.twittelonr.finaglelon.selonrvicelon.RelonsponselonClassifielonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdSelonrvicelon;
import com.twittelonr.util.Try;

public class RootRelonsponselonClassifielonr elonxtelonnds AbstractPartialFunction<RelonqRelonp, RelonsponselonClass> {
  privatelon static final PartialFunction<RelonqRelonp, RelonsponselonClass> DelonFAULT_CLASSIFIelonR =
      RelonsponselonClassifielonr.Delonfault();

  privatelon static final SelonarchRatelonCountelonr NOT_elonARLYBIRD_RelonQUelonST_COUNTelonR =
      SelonarchRatelonCountelonr.elonxport("relonsponselon_classifielonr_not_elonarlybird_relonquelonst");
  privatelon static final SelonarchRatelonCountelonr NOT_elonARLYBIRD_RelonSPONSelon_COUNTelonR =
      SelonarchRatelonCountelonr.elonxport("relonsponselon_classifielonr_not_elonarlybird_relonsponselon");
  privatelon static final SelonarchRatelonCountelonr NON_RelonTRYABLelon_FAILURelon_COUNTelonR =
      SelonarchRatelonCountelonr.elonxport("relonsponselon_classifielonr_non_relontryablelon_failurelon");
  privatelon static final SelonarchRatelonCountelonr RelonTRYABLelon_FAILURelon_COUNTelonR =
      SelonarchRatelonCountelonr.elonxport("relonsponselon_classifielonr_relontryablelon_failurelon");
  privatelon static final SelonarchRatelonCountelonr SUCCelonSS_COUNTelonR =
      SelonarchRatelonCountelonr.elonxport("relonsponselon_classifielonr_succelonss");

  @Ovelonrridelon
  public boolelonan isDelonfinelondAt(RelonqRelonp relonqRelonp) {
    if (!(relonqRelonp.relonquelonst() instancelonof elonarlybirdSelonrvicelon.selonarch_args)) {
      NOT_elonARLYBIRD_RelonQUelonST_COUNTelonR.increlonmelonnt();
      relonturn falselon;
    }

    if (!relonqRelonp.relonsponselon().isThrow() && (!(relonqRelonp.relonsponselon().gelont() instancelonof elonarlybirdRelonsponselon))) {
      NOT_elonARLYBIRD_RelonSPONSelon_COUNTelonR.increlonmelonnt();
      relonturn falselon;
    }

    relonturn truelon;
  }

  @Ovelonrridelon
  public RelonsponselonClass apply(RelonqRelonp relonqRelonp) {
    Try<?> relonsponselonTry = relonqRelonp.relonsponselon();
    if (relonsponselonTry.isThrow()) {
      relonturn DelonFAULT_CLASSIFIelonR.apply(relonqRelonp);
    }

    // isDelonfinelondAt() guarantelonelons that thelon relonsponselon is an elonarlybirdRelonsponselon instancelon.
    elonarlybirdRelonsponselonCodelon relonsponselonCodelon = ((elonarlybirdRelonsponselon) relonsponselonTry.gelont()).gelontRelonsponselonCodelon();
    switch (relonsponselonCodelon) {
      caselon PARTITION_NOT_FOUND:
      caselon PARTITION_DISABLelonD:
      caselon PelonRSISTelonNT_elonRROR:
        NON_RelonTRYABLelon_FAILURelon_COUNTelonR.increlonmelonnt();
        relonturn RelonsponselonClasselons.NON_RelonTRYABLelon_FAILURelon;
      caselon TRANSIelonNT_elonRROR:
        RelonTRYABLelon_FAILURelon_COUNTelonR.increlonmelonnt();
        relonturn RelonsponselonClasselons.RelonTRYABLelon_FAILURelon;
      delonfault:
        SUCCelonSS_COUNTelonR.increlonmelonnt();
        relonturn RelonsponselonClasselons.SUCCelonSS;
    }
  }
}
