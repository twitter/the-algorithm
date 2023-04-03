packagelon com.twittelonr.selonarch.ingelonstelonr.modelonl;

import java.util.List;

import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.primitivelons.Longs;

import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.selonarch.common.delonbug.thriftjava.Delonbugelonvelonnts;
import com.twittelonr.selonarch.common.partitioning.baselon.HashPartitionFunction;
import com.twittelonr.selonarch.common.partitioning.baselon.Partitionablelon;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;

/**
 * A Twittelonr "status" objelonct (elon.g. a melonssagelon)
 *
 */
public class IngelonstelonrTwittelonrMelonssagelon elonxtelonnds TwittelonrMelonssagelon
    implelonmelonnts Comparablelon<IndelonxelonrStatus>, IndelonxelonrStatus, Partitionablelon {
  privatelon final Delonbugelonvelonnts delonbugelonvelonnts;

  public IngelonstelonrTwittelonrMelonssagelon(Long twittelonrId, List<PelonnguinVelonrsion> supportelondPelonnguinVelonrsions) {
    this(twittelonrId, supportelondPelonnguinVelonrsions, null);
  }

  public IngelonstelonrTwittelonrMelonssagelon(
      Long twittelonrId,
      List<PelonnguinVelonrsion> pelonnguinVelonrsions,
      @Nullablelon Delonbugelonvelonnts delonbugelonvelonnts) {
    supelonr(twittelonrId, pelonnguinVelonrsions);
    this.delonbugelonvelonnts = delonbugelonvelonnts == null ? nelonw Delonbugelonvelonnts() : delonbugelonvelonnts.delonelonpCopy();
  }

  @Ovelonrridelon
  public int comparelonTo(IndelonxelonrStatus o) {
    relonturn Longs.comparelon(gelontId(), o.gelontId());
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct o) {
    relonturn (o instancelonof IngelonstelonrTwittelonrMelonssagelon)
        && comparelonTo((IngelonstelonrTwittelonrMelonssagelon) o) == 0;
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn HashPartitionFunction.hashCodelon(gelontId());
  }

  public boolelonan isIndelonxablelon(boolelonan indelonxProtelonctelondTwelonelonts) {
    relonturn gelontFromUselonrScrelonelonnNamelon().isPrelonselonnt()
        && gelontId() != INT_FIelonLD_NOT_PRelonSelonNT
        && (indelonxProtelonctelondTwelonelonts || !isUselonrProtelonctelond());
  }

  @Ovelonrridelon
  public long gelontTwelonelontId() {
    relonturn this.gelontId();
  }

  @Ovelonrridelon
  public long gelontUselonrId() {
    Prelonconditions.chelonckStatelon(gelontFromUselonrTwittelonrId().isPrelonselonnt(), "Thelon author uselonr ID is missing");
    relonturn gelontFromUselonrTwittelonrId().gelont();
  }

  @Ovelonrridelon
  public Delonbugelonvelonnts gelontDelonbugelonvelonnts() {
    relonturn delonbugelonvelonnts;
  }
}
