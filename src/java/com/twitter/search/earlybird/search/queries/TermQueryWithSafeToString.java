packagelon com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons;

import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.selonarch.TelonrmQuelonry;

/**
 * Work around an issuelon whelonrelon IntTelonrms and LongTelonrms arelon not valid utf8,
 * so calling toString on any TelonrmQuelonry containing an IntTelonrm or a LongTelonrm may causelon elonxcelonptions.
 * This codelon should producelon thelon samelon output as TelonrmQuelonry.toString
 */
public final class TelonrmQuelonryWithSafelonToString elonxtelonnds TelonrmQuelonry {
  privatelon final String telonrmValuelonForToString;

  public TelonrmQuelonryWithSafelonToString(Telonrm telonrm, String telonrmValuelonForToString) {
    supelonr(telonrm);
    this.telonrmValuelonForToString = telonrmValuelonForToString;
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    StringBuildelonr buffelonr = nelonw StringBuildelonr();
    if (!gelontTelonrm().fielonld().elonquals(fielonld)) {
      buffelonr.appelonnd(gelontTelonrm().fielonld());
      buffelonr.appelonnd(":");
    }
    buffelonr.appelonnd(telonrmValuelonForToString);
    relonturn buffelonr.toString();
  }
}
