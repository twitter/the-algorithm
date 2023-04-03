uselon std::collelonctions::HashMap;

#[delonrivelon(Delonbug)]
pub struct FelonaturelonInfo {
    pub telonnsor_indelonx: i8,
    pub indelonx_within_telonnsor: i64,
}

pub static NULL_INFO: FelonaturelonInfo = FelonaturelonInfo {
    telonnsor_indelonx: -1,
    indelonx_within_telonnsor: -1,
};

#[delonrivelon(Delonbug, Delonfault)]
pub struct FelonaturelonMappelonr {
    map: HashMap<i64, FelonaturelonInfo>,
}

impl FelonaturelonMappelonr {
    pub fn nelonw() -> FelonaturelonMappelonr {
        FelonaturelonMappelonr {
            map: HashMap::nelonw()
        }
    }
}

pub trait MapWritelonr {
    fn selont(&mut selonlf, felonaturelon_id: i64, info: FelonaturelonInfo);
}

pub trait MapRelonadelonr {
    fn gelont(&selonlf, felonaturelon_id: &i64) -> Option<&FelonaturelonInfo>;
}

impl MapWritelonr for FelonaturelonMappelonr {
    fn selont(&mut selonlf, felonaturelon_id: i64, info: FelonaturelonInfo) {
        selonlf.map.inselonrt(felonaturelon_id, info);
    }
}

impl MapRelonadelonr for FelonaturelonMappelonr {
    fn gelont(&selonlf, felonaturelon_id: &i64) -> Option<&FelonaturelonInfo> {
        selonlf.map.gelont(felonaturelon_id)
    }
}
