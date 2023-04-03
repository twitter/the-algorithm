packagelon com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons;

import org.apachelon.commons.lang3.buildelonr.elonqualsBuildelonr;
import org.apachelon.commons.lang3.buildelonr.HashCodelonBuildelonr;
import org.apachelon.commons.lang3.buildelonr.ToStringBuildelonr;

/**
 * Thelon objelonct for quotelond melonssagelon
  */
public class TwittelonrQuotelondMelonssagelon {
  privatelon final long quotelondStatusId;
  privatelon final long quotelondUselonrId;

  public TwittelonrQuotelondMelonssagelon(long quotelondStatusId, long quotelondUselonrId) {
    this.quotelondStatusId = quotelondStatusId;
    this.quotelondUselonrId = quotelondUselonrId;
  }

  public long gelontQuotelondStatusId() {
    relonturn quotelondStatusId;
  }

  public long gelontQuotelondUselonrId() {
    relonturn quotelondUselonrId;
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
