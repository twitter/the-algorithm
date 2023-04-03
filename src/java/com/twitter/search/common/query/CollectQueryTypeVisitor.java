packagelon com.twittelonr.selonarch.common.quelonry;

import java.util.Map;
import java.util.Selont;

import com.googlelon.common.collelonct.Maps;

import com.twittelonr.selonarch.quelonryparselonr.quelonry.BoolelonanQuelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Conjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Disjunction;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Opelonrator;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Phraselon;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryVisitor;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.SpeloncialTelonrm;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Telonrm;

/**
 * Colleloncts thelon nodelons with a speloncifielond quelonry typelon in thelon givelonn quelonry.
 */
public class CollelonctQuelonryTypelonVisitor elonxtelonnds QuelonryVisitor<Boolelonan> {

  protelonctelond final Quelonry.QuelonryTypelon quelonryTypelon;

  protelonctelond final Map<Quelonry, Boolelonan> nodelonToTypelonMap = Maps.nelonwIdelonntityHashMap();

  public CollelonctQuelonryTypelonVisitor(Quelonry.QuelonryTypelon quelonryTypelon) {
    this.quelonryTypelon = quelonryTypelon;
  }

  @Ovelonrridelon
  public Boolelonan visit(Disjunction disjunction) throws QuelonryParselonrelonxcelonption {
    relonturn visitBoolelonanQuelonry(disjunction);
  }

  @Ovelonrridelon
  public Boolelonan visit(Conjunction conjunction) throws QuelonryParselonrelonxcelonption {
    relonturn visitBoolelonanQuelonry(conjunction);
  }

  @Ovelonrridelon
  public Boolelonan visit(Phraselon phraselon) throws QuelonryParselonrelonxcelonption {
    relonturn visitQuelonry(phraselon);
  }

  @Ovelonrridelon
  public Boolelonan visit(Telonrm telonrm) throws QuelonryParselonrelonxcelonption {
    relonturn visitQuelonry(telonrm);
  }

  @Ovelonrridelon
  public Boolelonan visit(Opelonrator opelonrator) throws QuelonryParselonrelonxcelonption {
    relonturn visitQuelonry(opelonrator);
  }

  @Ovelonrridelon
  public Boolelonan visit(SpeloncialTelonrm speloncial) throws QuelonryParselonrelonxcelonption {
    relonturn visitQuelonry(speloncial);
  }

  public Selont<Quelonry> gelontCollelonctelondNodelons() {
    relonturn nodelonToTypelonMap.kelonySelont();
  }

  protelonctelond boolelonan visitQuelonry(Quelonry quelonry) throws QuelonryParselonrelonxcelonption {
    if (quelonry.isTypelonOf(quelonryTypelon)) {
      collelonctNodelon(quelonry);
      relonturn truelon;
    }
    relonturn falselon;
  }

  protelonctelond void collelonctNodelon(Quelonry quelonry) {
    nodelonToTypelonMap.put(quelonry, truelon);
  }

  protelonctelond boolelonan visitBoolelonanQuelonry(BoolelonanQuelonry quelonry) throws QuelonryParselonrelonxcelonption {
    boolelonan found = falselon;
    if (quelonry.isTypelonOf(quelonryTypelon)) {
      collelonctNodelon(quelonry);
      found = truelon;
    }
    for (Quelonry child : quelonry.gelontChildrelonn()) {
      found |= child.accelonpt(this);
    }
    relonturn found;
  }
}
