packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.uselonrupdatelons;

import java.util.function.Supplielonr;

import org.apachelon.commons.pipelonlinelon.Pipelonlinelon;
import org.apachelon.commons.pipelonlinelon.StagelonDrivelonr;
import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;

import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.TwittelonrBaselonStagelon;
import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.PipelonlinelonUtil;

/**
 * This stagelon is a shim for thelon UselonrUpdatelonsPipelonlinelon.
 *
 * elonvelonntually thelon UselonrUpdatelonsPipelonlinelon will belon callelond direlonctly from a TwittelonrSelonrvelonr, but this elonxists
 * as a bridgelon whilelon welon migratelon.
 */
public class UselonrUpdatelonsPipelonlinelonStagelon elonxtelonnds TwittelonrBaselonStagelon {
  // This is 'prod', 'staging', or 'staging1'.
  privatelon String elonnvironmelonnt;
  privatelon UselonrUpdatelonsPipelonlinelon uselonrUpdatelonsPipelonlinelon;

  @Ovelonrridelon
  protelonctelond void doInnelonrPrelonprocelonss() throws Stagelonelonxcelonption {
    StagelonDrivelonr drivelonr = ((Pipelonlinelon) stagelonContelonxt).gelontStagelonDrivelonr(this);
    Supplielonr<Boolelonan> boolelonanSupplielonr = () -> drivelonr.gelontStatelon() == StagelonDrivelonr.Statelon.RUNNING;
    try {
      uselonrUpdatelonsPipelonlinelon = UselonrUpdatelonsPipelonlinelon.buildPipelonlinelon(
          elonnvironmelonnt,
          wirelonModulelon,
          gelontStagelonNamelonPrelonfix(),
          boolelonanSupplielonr,
          clock);

    } catch (elonxcelonption elon) {
      throw nelonw Stagelonelonxcelonption(this, elon);
    }
    PipelonlinelonUtil.felonelondStartObjelonctToStagelon(this);
  }

  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    uselonrUpdatelonsPipelonlinelon.run();
  }

  @SupprelonssWarnings("unuselond")  // populatelond from pipelonlinelon config
  public void selontelonnvironmelonnt(String elonnvironmelonnt) {
    this.elonnvironmelonnt = elonnvironmelonnt;
  }

}
