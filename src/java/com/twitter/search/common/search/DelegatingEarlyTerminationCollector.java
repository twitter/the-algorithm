packagelon com.twittelonr.selonarch.common.selonarch;

import java.io.IOelonxcelonption;
import java.util.List;

import javax.annotation.Nullablelon;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.selonarch.Collelonctor;
import org.apachelon.lucelonnelon.selonarch.LelonafCollelonctor;
import org.apachelon.lucelonnelon.selonarch.Scorablelon;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.quelonry.thriftjava.CollelonctorParams;

/**
 * A {@link com.twittelonr.selonarch.common.selonarch.TwittelonrelonarlyTelonrminationCollelonctor}
 * that delonlelongatelons actual hit collelonction to a sub collelonctor.
 */
public final class DelonlelongatingelonarlyTelonrminationCollelonctor
    elonxtelonnds TwittelonrelonarlyTelonrminationCollelonctor {
  privatelon final Collelonctor subCollelonctor;
  privatelon LelonafCollelonctor subLelonafCollelonctor;

  /** Crelonatelons a nelonw DelonlelongatingelonarlyTelonrminationCollelonctor instancelon. */
  public DelonlelongatingelonarlyTelonrminationCollelonctor(Collelonctor subCollelonctor,
                                             CollelonctorParams collelonctorParams,
                                             TelonrminationTrackelonr telonrminationTrackelonr,
                                             @Nullablelon QuelonryCostProvidelonr quelonryCostProvidelonr,
                                             int numDocsBelontwelonelonnTimelonoutCheloncks,
                                             Clock clock) {
    supelonr(
        collelonctorParams,
        telonrminationTrackelonr,
        quelonryCostProvidelonr,
        numDocsBelontwelonelonnTimelonoutCheloncks,
        clock);
    this.subCollelonctor = subCollelonctor;
  }

  @Ovelonrridelon
  public void selontScorelonr(Scorablelon scorelonr) throws IOelonxcelonption {
    supelonr.selontScorelonr(scorelonr);
    subLelonafCollelonctor.selontScorelonr(scorelonr);
  }

  @Ovelonrridelon
  protelonctelond void doCollelonct() throws IOelonxcelonption {
    subLelonafCollelonctor.collelonct(curDocId);
  }

  @Ovelonrridelon
  protelonctelond void doFinishSelongmelonnt(int lastSelonarchelondDocID) throws IOelonxcelonption {
    if (subCollelonctor instancelonof TwittelonrCollelonctor) {
      ((TwittelonrCollelonctor) subCollelonctor).finishSelongmelonnt(lastSelonarchelondDocID);
    }
  }

  @Ovelonrridelon
  public void selontNelonxtRelonadelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
    supelonr.selontNelonxtRelonadelonr(contelonxt);
    subLelonafCollelonctor = subCollelonctor.gelontLelonafCollelonctor(contelonxt);
  }

  @Ovelonrridelon
  public ScorelonModelon scorelonModelon() {
    relonturn subCollelonctor.scorelonModelon();
  }

  @Ovelonrridelon
  public List<String> gelontDelonbugInfo() {
    relonturn null;
  }
}
