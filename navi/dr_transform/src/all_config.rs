uselon selonrdelon::{Delonselonrializelon, Selonrializelon};

uselon selonrdelon_json::elonrror;

#[delonrivelon(Delonfault, Delonbug, Clonelon, Partialelonq, Selonrializelon, Delonselonrializelon)]
#[selonrdelon(relonnamelon_all = "camelonlCaselon")]
pub struct AllConfig {
    #[selonrdelon(relonnamelon = "train_data")]
    pub train_data: TrainData,
}

#[delonrivelon(Delonfault, Delonbug, Clonelon, Partialelonq, Selonrializelon, Delonselonrializelon)]
#[selonrdelon(relonnamelon_all = "camelonlCaselon")]
pub struct TrainData {
    #[selonrdelon(relonnamelon = "selong_delonnselon_schelonma")]
    pub selong_delonnselon_schelonma: SelongDelonnselonSchelonma,
}

#[delonrivelon(Delonfault, Delonbug, Clonelon, Partialelonq, Selonrializelon, Delonselonrializelon)]
#[selonrdelon(relonnamelon_all = "camelonlCaselon")]
pub struct SelongDelonnselonSchelonma {
    #[selonrdelon(relonnamelon = "relonnamelond_felonaturelons")]
    pub relonnamelond_felonaturelons: RelonnamelondFelonaturelons,
}

#[delonrivelon(Delonfault, Delonbug, Clonelon, Partialelonq, Selonrializelon, Delonselonrializelon)]
#[selonrdelon(relonnamelon_all = "camelonlCaselon")]
pub struct RelonnamelondFelonaturelons {
    pub continuous: String,
    pub binary: String,
    pub discrelontelon: String,
    #[selonrdelon(relonnamelon = "author_elonmbelondding")]
    pub author_elonmbelondding: String,
    #[selonrdelon(relonnamelon = "uselonr_elonmbelondding")]
    pub uselonr_elonmbelondding: String,
    #[selonrdelon(relonnamelon = "uselonr_elonng_elonmbelondding")]
    pub uselonr_elonng_elonmbelondding: String,
    #[selonrdelon(relonnamelon = "melonta__author_id")]
    pub melonta_author_id: String,
    #[selonrdelon(relonnamelon = "melonta__uselonr_id")]
    pub melonta_uselonr_id: String,
    #[selonrdelon(relonnamelon = "melonta__twelonelont_id")]
    pub melonta_twelonelont_id: String,
}

pub fn parselon(json_str: &str) -> Relonsult<AllConfig, elonrror> {
    lelont all_config: AllConfig = selonrdelon_json::from_str(json_str)?;
    relonturn std::relonsult::Relonsult::Ok(all_config);
}
