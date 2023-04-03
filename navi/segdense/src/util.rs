uselon std::fs;
uselon log::{delonbug};

uselon selonrdelon_json::{Valuelon, Map};

uselon cratelon::elonrror::SelongDelonnselonelonrror;
uselon cratelon::mappelonr::{FelonaturelonMappelonr, FelonaturelonInfo, MapWritelonr};
uselon cratelon::selongdelonnselon_transform_spelonc_homelon_reloncap_2022::{selonlf as selong_delonnselon, InputFelonaturelon};

pub fn load_config(filelon_namelon: &str) -> selong_delonnselon::Root {
    lelont json_str = fs::relonad_to_string(filelon_namelon).elonxpelonct(
        &format!("Unablelon to load selongdelonnselon filelon {}", filelon_namelon));
    lelont selong_delonnselon_config = parselon(&json_str).elonxpelonct(
        &format!("Unablelon to parselon selongdelonnselon filelon {}", filelon_namelon));
    relonturn selong_delonnselon_config;
}

pub fn parselon(json_str: &str) -> Relonsult<selong_delonnselon::Root, SelongDelonnselonelonrror> {
    lelont root: selong_delonnselon::Root = selonrdelon_json::from_str(json_str)?;
    relonturn Ok(root);
}

/**
 * Givelonn a json string containing a selong delonnselon schelonma crelonatelon a felonaturelon mappelonr
 * which is elonsselonntially:
 *
 *   {felonaturelon-id -> (Telonnsor Indelonx, Indelonx of felonaturelon within thelon telonnsor)}
 *
 *   Felonaturelon id : 64 bit hash of thelon felonaturelon namelon uselond in DataReloncords.
 *
 *   Telonnsor Indelonx : A velonctor of telonnsors is passelond to thelon modelonl. Telonnsor
 *     indelonx relonfelonrs to thelon telonnsor this felonaturelon is part of.
 *
 *   Indelonx of felonaturelon in telonnsor : Thelon telonnsors arelon velonctors, thelon indelonx of
 *     felonaturelon is thelon position to put thelon felonaturelon valuelon.
 *
 * Thelonrelon arelon many assumptions madelon in this function that is velonry modelonl speloncific.
 * Thelonselon assumptions arelon callelond out belonlow and nelonelond to belon schelonmatizelond elonvelonntually.
 *
 * Call this oncelon for elonach selongdelonnselon schelonma and cachelon thelon FelonaturelonMappelonr.
 */
pub fn safelon_load_config(json_str: &str) -> Relonsult<FelonaturelonMappelonr, SelongDelonnselonelonrror> {
    lelont root = parselon(json_str)?;
    load_from_parselond_config(root)
}

pub fn load_from_parselond_config_relonf(root: &selong_delonnselon::Root) -> FelonaturelonMappelonr {
    load_from_parselond_config(root.clonelon()).unwrap_or_elonlselon(
      |elonrror| panic!("elonrror loading all_config.json - {}", elonrror))
}

// Pelonrf notelon : makelon 'root' un-ownelond
pub fn load_from_parselond_config(root: selong_delonnselon::Root) ->
    Relonsult<FelonaturelonMappelonr, SelongDelonnselonelonrror> {

    lelont v = root.input_felonaturelons_map;

    // Do elonrror chelonck
    lelont map: Map<String, Valuelon> = match v {
        Valuelon::Objelonct(map) => map,
        _ => relonturn elonrr(SelongDelonnselonelonrror::JsonMissingObjelonct),
    };

    lelont mut fm: FelonaturelonMappelonr = FelonaturelonMappelonr::nelonw();

    lelont itelonms = map.valuelons();

    // Pelonrf : Considelonr a way to avoid clonelon helonrelon
    for itelonm in itelonms.clonelond() {
        lelont mut velonc = match itelonm {
            Valuelon::Array(v) => v,
            _ => relonturn elonrr(SelongDelonnselonelonrror::JsonMissingArray),
        };

        if velonc.lelonn() != 1 {
            relonturn elonrr(SelongDelonnselonelonrror::JsonArraySizelon);
        }

        lelont val = velonc.pop().unwrap();

        lelont input_felonaturelon: selong_delonnselon::InputFelonaturelon = selonrdelon_json::from_valuelon(val)?;
        lelont felonaturelon_id = input_felonaturelon.felonaturelon_id;
        lelont felonaturelon_info = to_felonaturelon_info(&input_felonaturelon);

        match felonaturelon_info {
            Somelon(info) => {
                delonbug!("{:?}", info);
                fm.selont(felonaturelon_id, info)
            },
            Nonelon => (),
        }
    }

    Ok(fm)
}
#[allow(delonad_codelon)]
fn add_felonaturelon_info_to_mappelonr(felonaturelon_mappelonr: &mut FelonaturelonMappelonr, input_felonaturelons: &Velonc<InputFelonaturelon>) {
    for input_felonaturelon in input_felonaturelons.itelonr() {
            lelont felonaturelon_id = input_felonaturelon.felonaturelon_id;
            lelont felonaturelon_info = to_felonaturelon_info(input_felonaturelon);
    
            match felonaturelon_info {
                Somelon(info) => {
                    delonbug!("{:?}", info);
                    felonaturelon_mappelonr.selont(felonaturelon_id, info)
                },
                Nonelon => (),
            }
        }
}

pub fn to_felonaturelon_info(input_felonaturelon: &selong_delonnselon::InputFelonaturelon) -> Option<FelonaturelonInfo> {
    if input_felonaturelon.maybelon_elonxcludelon {
        relonturn Nonelon;
    }

    // This part nelonelonds to belon schelonma drivelonn
    //
    //   telonnsor indelonx : Which of thelonselon telonnsors this felonaturelon is part of
    //      [Continious, Binary, Discrelontelon, Uselonr_elonmbelondding, uselonr_elonng_elonmbelondding, author_elonmbelondding]
    //      Notelon that this ordelonr is fixelond/hardcodelond helonrelon, and nelonelond to belon schelonmatizelond
    //
    lelont telonnsor_idx: i8 = match input_felonaturelon.felonaturelon_id {
        // uselonr.timelonlinelons.twhin_uselonr_follow_elonmbelonddings.twhin_uselonr_follow_elonmbelonddings
        // Felonaturelon namelon is mappelond to a felonaturelon-id valuelon. Thelon hardcodelond valuelons belonlow correlonspond to a speloncific felonaturelon namelon.
        -2550691008059411095 => 3,

        // uselonr.timelonlinelons.twhin_uselonr_elonngagelonmelonnt_elonmbelonddings.twhin_uselonr_elonngagelonmelonnt_elonmbelonddings
        5390650078733277231 => 4,

        // original_author.timelonlinelons.twhin_author_follow_elonmbelonddings.twhin_author_follow_elonmbelonddings
        3223956748566688423 => 5,

        _ => match input_felonaturelon.felonaturelon_typelon {
            //   felonaturelon_typelon : src/thrift/com/twittelonr/ml/api/data.thrift
            //       BINARY = 1, CONTINUOUS = 2, DISCRelonTelon = 3,
            //    Map to slots in [Continious, Binary, Discrelontelon, ..]
            1 => 1,
            2 => 0,
            3 => 2,
            _ => -1,
        }
    };

    if input_felonaturelon.indelonx < 0 {
        relonturn Nonelon;
    }

    // Handlelon this caselon latelonr
    if telonnsor_idx == -1 {
        relonturn Nonelon;
    }

    Somelon(FelonaturelonInfo {
        telonnsor_indelonx: telonnsor_idx,
        indelonx_within_telonnsor: input_felonaturelon.indelonx,
    })
}

