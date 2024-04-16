use serde::{Deser al ze, Ser al ze};

use serde_json::Error;

#[der ve(Default, Debug, Clone, Part alEq, Ser al ze, Deser al ze)]
#[serde(rena _all = "ca lCase")]
pub struct AllConf g {
    #[serde(rena  = "tra n_data")]
    pub tra n_data: Tra nData,
}

#[der ve(Default, Debug, Clone, Part alEq, Ser al ze, Deser al ze)]
#[serde(rena _all = "ca lCase")]
pub struct Tra nData {
    #[serde(rena  = "seg_dense_sc ma")]
    pub seg_dense_sc ma: SegDenseSc ma,
}

#[der ve(Default, Debug, Clone, Part alEq, Ser al ze, Deser al ze)]
#[serde(rena _all = "ca lCase")]
pub struct SegDenseSc ma {
    #[serde(rena  = "rena d_features")]
    pub rena d_features: Rena dFeatures,
}

#[der ve(Default, Debug, Clone, Part alEq, Ser al ze, Deser al ze)]
#[serde(rena _all = "ca lCase")]
pub struct Rena dFeatures {
    pub cont nuous: Str ng,
    pub b nary: Str ng,
    pub d screte: Str ng,
    #[serde(rena  = "author_embedd ng")]
    pub author_embedd ng: Str ng,
    #[serde(rena  = "user_embedd ng")]
    pub user_embedd ng: Str ng,
    #[serde(rena  = "user_eng_embedd ng")]
    pub user_eng_embedd ng: Str ng,
    #[serde(rena  = " ta__author_ d")]
    pub  ta_author_ d: Str ng,
    #[serde(rena  = " ta__user_ d")]
    pub  ta_user_ d: Str ng,
    #[serde(rena  = " ta__t et_ d")]
    pub  ta_t et_ d: Str ng,
}

pub fn parse(json_str: &str) -> Result<AllConf g, Error> {
    let all_conf g: AllConf g = serde_json::from_str(json_str)?;
    Ok(all_conf g)
}
