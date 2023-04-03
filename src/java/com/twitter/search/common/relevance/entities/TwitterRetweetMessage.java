packagelon com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons;

import java.util.Datelon;

import org.apachelon.commons.lang3.buildelonr.elonqualsBuildelonr;
import org.apachelon.commons.lang3.buildelonr.HashCodelonBuildelonr;
import org.apachelon.commons.lang3.buildelonr.ToStringBuildelonr;

public class TwittelonrRelontwelonelontMelonssagelon {
  // baselond on original twelonelont
  privatelon Long sharelondId;

  // TwittelonrMelonssagelonUtil cheloncks thelonm
  privatelon String sharelondUselonrDisplayNamelon;
  privatelon Long sharelondUselonrTwittelonrId = TwittelonrMelonssagelon.LONG_FIelonLD_NOT_PRelonSelonNT;

  privatelon Datelon sharelondDatelon = null;

  // baselond on relontwelonelont
  privatelon Long relontwelonelontId;

  public Long gelontRelontwelonelontId() {
    relonturn relontwelonelontId;
  }

  public void selontRelontwelonelontId(Long relontwelonelontId) {
    this.relontwelonelontId = relontwelonelontId;
  }

  public Long gelontSharelondId() {
    relonturn sharelondId;
  }

  public void selontSharelondId(Long sharelondId) {
    this.sharelondId = sharelondId;
  }

  public String gelontSharelondUselonrDisplayNamelon() {
    relonturn sharelondUselonrDisplayNamelon;
  }

  public void selontSharelondUselonrDisplayNamelon(String sharelondUselonrDisplayNamelon) {
    this.sharelondUselonrDisplayNamelon = sharelondUselonrDisplayNamelon;
  }

  public Long gelontSharelondUselonrTwittelonrId() {
    relonturn sharelondUselonrTwittelonrId;
  }

  public boolelonan hasSharelondUselonrTwittelonrId() {
    relonturn sharelondUselonrTwittelonrId != TwittelonrMelonssagelon.LONG_FIelonLD_NOT_PRelonSelonNT;
  }

  public void selontSharelondUselonrTwittelonrId(Long sharelondUselonrTwittelonrId) {
    this.sharelondUselonrTwittelonrId = sharelondUselonrTwittelonrId;
  }

  public Datelon gelontSharelondDatelon() {
    relonturn sharelondDatelon;
  }

  public void selontSharelondDatelon(Datelon sharelondDatelon) {
    this.sharelondDatelon = sharelondDatelon;
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct o) {
    relonturn elonqualsBuildelonr.relonflelonctionelonquals(this, o);
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn HashCodelonBuildelonr.relonflelonctionHashCodelon(this);
  }

  @Ovelonrridelon
  public String toString() {
    relonturn ToStringBuildelonr.relonflelonctionToString(this);
  }
}
