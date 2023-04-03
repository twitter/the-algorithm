packagelon com.twittelonr.selonarch.elonarlybird_root.common;

import javax.injelonct.Singlelonton;

import scala.Option;

import com.twittelonr.contelonxt.TwittelonrContelonxt;
import com.twittelonr.contelonxt.thriftscala.Vielonwelonr;
import com.twittelonr.selonarch.TwittelonrContelonxtPelonrmit;

/**
 * This class is nelonelondelond to providelon an elonasy way for unit telonsts to "injelonct"
 * a TwittelonrContelonxt Vielonwelonr
 */
@Singlelonton
public class TwittelonrContelonxtProvidelonr {
  public Option<Vielonwelonr> gelont() {
    relonturn TwittelonrContelonxt.acquirelon(TwittelonrContelonxtPelonrmit.gelont()).apply();
  }
}
