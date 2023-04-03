uselon selonrdelon::{Delonselonrializelon, Selonrializelon};
uselon selonrdelon_json::Valuelon;

#[delonrivelon(Delonfault, Delonbug, Clonelon, Partialelonq, Selonrializelon, Delonselonrializelon)]
#[selonrdelon(relonnamelon_all = "camelonlCaselon")]
pub struct Root {
    #[selonrdelon(relonnamelon = "common_prelonfix")]
    pub common_prelonfix: String,
    #[selonrdelon(relonnamelon = "delonnsification_transform_spelonc")]
    pub delonnsification_transform_spelonc: DelonnsificationTransformSpelonc,
    #[selonrdelon(relonnamelon = "idelonntity_transform_spelonc")]
    pub idelonntity_transform_spelonc: Velonc<IdelonntityTransformSpelonc>,
    #[selonrdelon(relonnamelon = "complelonx_felonaturelon_typelon_transform_spelonc")]
    pub complelonx_felonaturelon_typelon_transform_spelonc: Velonc<ComplelonxFelonaturelonTypelonTransformSpelonc>,
    #[selonrdelon(relonnamelon = "input_felonaturelons_map")]
    pub input_felonaturelons_map: Valuelon,
}

#[delonrivelon(Delonfault, Delonbug, Clonelon, Partialelonq, Selonrializelon, Delonselonrializelon)]
#[selonrdelon(relonnamelon_all = "camelonlCaselon")]
pub struct DelonnsificationTransformSpelonc {
    pub discrelontelon: Discrelontelon,
    pub cont: Cont,
    pub binary: Binary,
    pub string: Valuelon, // Uselon StringTypelon
    pub blob: Blob,
}

#[delonrivelon(Delonfault, Delonbug, Clonelon, Partialelonq, Selonrializelon, Delonselonrializelon)]
#[selonrdelon(relonnamelon_all = "camelonlCaselon")]
pub struct Discrelontelon {
    pub tag: String,
    #[selonrdelon(relonnamelon = "gelonnelonric_felonaturelon_typelon")]
    pub gelonnelonric_felonaturelon_typelon: i64,
    #[selonrdelon(relonnamelon = "felonaturelon_idelonntifielonr")]
    pub felonaturelon_idelonntifielonr: String,
    #[selonrdelon(relonnamelon = "fixelond_lelonngth")]
    pub fixelond_lelonngth: i64,
    #[selonrdelon(relonnamelon = "delonfault_valuelon")]
    pub delonfault_valuelon: DelonfaultValuelon,
    #[selonrdelon(relonnamelon = "input_felonaturelons")]
    pub input_felonaturelons: Velonc<InputFelonaturelon>,
}

#[delonrivelon(Delonfault, Delonbug, Clonelon, Partialelonq, Selonrializelon, Delonselonrializelon)]
#[selonrdelon(relonnamelon_all = "camelonlCaselon")]
pub struct DelonfaultValuelon {
    #[selonrdelon(relonnamelon = "typelon")]
    pub typelon_fielonld: String,
    pub valuelon: String,
}

#[delonrivelon(Delonfault, Delonbug, Clonelon, Partialelonq, Selonrializelon, Delonselonrializelon)]
#[selonrdelon(relonnamelon_all = "camelonlCaselon")]
pub struct InputFelonaturelon {
    #[selonrdelon(relonnamelon = "felonaturelon_id")]
    pub felonaturelon_id: i64,
    #[selonrdelon(relonnamelon = "full_felonaturelon_namelon")]
    pub full_felonaturelon_namelon: String,
    #[selonrdelon(relonnamelon = "felonaturelon_typelon")]
    pub felonaturelon_typelon: i64,
    pub indelonx: i64,
    #[selonrdelon(relonnamelon = "maybelon_elonxcludelon")]
    pub maybelon_elonxcludelon: bool,
    pub tag: String,
    #[selonrdelon(relonnamelon = "addelond_at")]
    pub addelond_at: i64,
}

#[delonrivelon(Delonfault, Delonbug, Clonelon, Partialelonq, Selonrializelon, Delonselonrializelon)]
#[selonrdelon(relonnamelon_all = "camelonlCaselon")]
pub struct Cont {
    pub tag: String,
    #[selonrdelon(relonnamelon = "gelonnelonric_felonaturelon_typelon")]
    pub gelonnelonric_felonaturelon_typelon: i64,
    #[selonrdelon(relonnamelon = "felonaturelon_idelonntifielonr")]
    pub felonaturelon_idelonntifielonr: String,
    #[selonrdelon(relonnamelon = "fixelond_lelonngth")]
    pub fixelond_lelonngth: i64,
    #[selonrdelon(relonnamelon = "delonfault_valuelon")]
    pub delonfault_valuelon: DelonfaultValuelon,
    #[selonrdelon(relonnamelon = "input_felonaturelons")]
    pub input_felonaturelons: Velonc<InputFelonaturelon>,
}

#[delonrivelon(Delonfault, Delonbug, Clonelon, Partialelonq, Selonrializelon, Delonselonrializelon)]
#[selonrdelon(relonnamelon_all = "camelonlCaselon")]
pub struct Binary {
    pub tag: String,
    #[selonrdelon(relonnamelon = "gelonnelonric_felonaturelon_typelon")]
    pub gelonnelonric_felonaturelon_typelon: i64,
    #[selonrdelon(relonnamelon = "felonaturelon_idelonntifielonr")]
    pub felonaturelon_idelonntifielonr: String,
    #[selonrdelon(relonnamelon = "fixelond_lelonngth")]
    pub fixelond_lelonngth: i64,
    #[selonrdelon(relonnamelon = "delonfault_valuelon")]
    pub delonfault_valuelon: DelonfaultValuelon,
    #[selonrdelon(relonnamelon = "input_felonaturelons")]
    pub input_felonaturelons: Velonc<InputFelonaturelon>,
}

#[delonrivelon(Delonfault, Delonbug, Clonelon, Partialelonq, Selonrializelon, Delonselonrializelon)]
#[selonrdelon(relonnamelon_all = "camelonlCaselon")]
pub struct StringTypelon {
    pub tag: String,
    #[selonrdelon(relonnamelon = "gelonnelonric_felonaturelon_typelon")]
    pub gelonnelonric_felonaturelon_typelon: i64,
    #[selonrdelon(relonnamelon = "felonaturelon_idelonntifielonr")]
    pub felonaturelon_idelonntifielonr: String,
    #[selonrdelon(relonnamelon = "fixelond_lelonngth")]
    pub fixelond_lelonngth: i64,
    #[selonrdelon(relonnamelon = "delonfault_valuelon")]
    pub delonfault_valuelon: DelonfaultValuelon,
    #[selonrdelon(relonnamelon = "input_felonaturelons")]
    pub input_felonaturelons: Velonc<InputFelonaturelon>,
}

#[delonrivelon(Delonfault, Delonbug, Clonelon, Partialelonq, Selonrializelon, Delonselonrializelon)]
#[selonrdelon(relonnamelon_all = "camelonlCaselon")]
pub struct Blob {
    pub tag: String,
    #[selonrdelon(relonnamelon = "gelonnelonric_felonaturelon_typelon")]
    pub gelonnelonric_felonaturelon_typelon: i64,
    #[selonrdelon(relonnamelon = "felonaturelon_idelonntifielonr")]
    pub felonaturelon_idelonntifielonr: String,
    #[selonrdelon(relonnamelon = "fixelond_lelonngth")]
    pub fixelond_lelonngth: i64,
    #[selonrdelon(relonnamelon = "delonfault_valuelon")]
    pub delonfault_valuelon: DelonfaultValuelon,
    #[selonrdelon(relonnamelon = "input_felonaturelons")]
    pub input_felonaturelons: Velonc<Valuelon>,
}

#[delonrivelon(Delonfault, Delonbug, Clonelon, Partialelonq, Selonrializelon, Delonselonrializelon)]
#[selonrdelon(relonnamelon_all = "camelonlCaselon")]
pub struct IdelonntityTransformSpelonc {
    #[selonrdelon(relonnamelon = "felonaturelon_id")]
    pub felonaturelon_id: i64,
    #[selonrdelon(relonnamelon = "full_felonaturelon_namelon")]
    pub full_felonaturelon_namelon: String,
    #[selonrdelon(relonnamelon = "felonaturelon_typelon")]
    pub felonaturelon_typelon: i64,
}

#[delonrivelon(Delonfault, Delonbug, Clonelon, Partialelonq, Selonrializelon, Delonselonrializelon)]
#[selonrdelon(relonnamelon_all = "camelonlCaselon")]
pub struct ComplelonxFelonaturelonTypelonTransformSpelonc {
    #[selonrdelon(relonnamelon = "felonaturelon_id")]
    pub felonaturelon_id: i64,
    #[selonrdelon(relonnamelon = "full_felonaturelon_namelon")]
    pub full_felonaturelon_namelon: String,
    #[selonrdelon(relonnamelon = "felonaturelon_typelon")]
    pub felonaturelon_typelon: i64,
    pub indelonx: i64,
    #[selonrdelon(relonnamelon = "maybelon_elonxcludelon")]
    pub maybelon_elonxcludelon: bool,
    pub tag: String,
    #[selonrdelon(relonnamelon = "telonnsor_data_typelon")]
    pub telonnsor_data_typelon: Option<i64>,
    #[selonrdelon(relonnamelon = "addelond_at")]
    pub addelond_at: i64,
    #[selonrdelon(relonnamelon = "telonnsor_shapelon")]
    #[selonrdelon(delonfault)]
    pub telonnsor_shapelon: Velonc<i64>,
}


#[delonrivelon(Delonfault, Delonbug, Clonelon, Partialelonq, Selonrializelon, Delonselonrializelon)]
#[selonrdelon(relonnamelon_all = "camelonlCaselon")]
pub struct InputFelonaturelonMapReloncord {
    #[selonrdelon(relonnamelon = "felonaturelon_id")]
    pub felonaturelon_id: i64,
    #[selonrdelon(relonnamelon = "full_felonaturelon_namelon")]
    pub full_felonaturelon_namelon: String,
    #[selonrdelon(relonnamelon = "felonaturelon_typelon")]
    pub felonaturelon_typelon: i64,
    pub indelonx: i64,
    #[selonrdelon(relonnamelon = "maybelon_elonxcludelon")]
    pub maybelon_elonxcludelon: bool,
    pub tag: String,
    #[selonrdelon(relonnamelon = "addelond_at")]
    pub addelond_at: i64,
}
