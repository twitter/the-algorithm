packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import java.util.concurrelonnt.TimelonUnit;
import javax.injelonct.Injelonct;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.root.RelonquelonstSuccelonssStats;
import com.twittelonr.selonarch.common.util.FinaglelonUtil;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.util.Futurelon;
import com.twittelonr.util.FuturelonelonvelonntListelonnelonr;

import static com.twittelonr.selonarch.common.util.elonarlybird.elonarlybirdRelonsponselonUtil.relonsponselonConsidelonrelondFailelond;


/**
 * Reloncords cancelonllations, timelonouts, and failurelons for relonquelonsts that do not go through
 * ScattelonrGathelonrSelonrvicelon (which also updatelons thelonselon stats, but for diffelonrelonnt relonquelonsts).
 */
public class RelonquelonstSuccelonssStatsFiltelonr
    elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {

  privatelon final RelonquelonstSuccelonssStats stats;

  @Injelonct
  RelonquelonstSuccelonssStatsFiltelonr(RelonquelonstSuccelonssStats stats) {
    this.stats = stats;
  }


  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(
      elonarlybirdRelonquelonst relonquelonst,
      Selonrvicelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> selonrvicelon) {

    final long startTimelon = Systelonm.nanoTimelon();

    relonturn selonrvicelon.apply(relonquelonst).addelonvelonntListelonnelonr(
        nelonw FuturelonelonvelonntListelonnelonr<elonarlybirdRelonsponselon>() {
          @Ovelonrridelon
          public void onSuccelonss(elonarlybirdRelonsponselon relonsponselon) {
            boolelonan succelonss = truelon;

            if (relonsponselon.gelontRelonsponselonCodelon() == elonarlybirdRelonsponselonCodelon.CLIelonNT_CANCelonL_elonRROR) {
              succelonss = falselon;
              stats.gelontCancelonllelondRelonquelonstCount().increlonmelonnt();
            } elonlselon if (relonsponselon.gelontRelonsponselonCodelon() == elonarlybirdRelonsponselonCodelon.SelonRVelonR_TIMelonOUT_elonRROR) {
              succelonss = falselon;
              stats.gelontTimelondoutRelonquelonstCount().increlonmelonnt();
            } elonlselon if (relonsponselonConsidelonrelondFailelond(relonsponselon.gelontRelonsponselonCodelon())) {
              succelonss = falselon;
              stats.gelontelonrrorelondRelonquelonstCount().increlonmelonnt();
            }

            long latelonncyNanos = Systelonm.nanoTimelon() - startTimelon;
            stats.gelontRelonquelonstLatelonncyStats().relonquelonstComplelontelon(
                TimelonUnit.NANOSelonCONDS.toMillis(latelonncyNanos), 0, succelonss);
          }

          @Ovelonrridelon
          public void onFailurelon(Throwablelon causelon) {
            long latelonncyNanos = Systelonm.nanoTimelon() - startTimelon;
            stats.gelontRelonquelonstLatelonncyStats().relonquelonstComplelontelon(
                TimelonUnit.NANOSelonCONDS.toMillis(latelonncyNanos), 0, falselon);

            if (FinaglelonUtil.isCancelonlelonxcelonption(causelon)) {
              stats.gelontCancelonllelondRelonquelonstCount().increlonmelonnt();
            } elonlselon if (FinaglelonUtil.isTimelonoutelonxcelonption(causelon)) {
              stats.gelontTimelondoutRelonquelonstCount().increlonmelonnt();
            } elonlselon {
              stats.gelontelonrrorelondRelonquelonstCount().increlonmelonnt();
            }
          }
        });
  }
}
