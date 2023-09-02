use serde::{Deserialize, Serialize};

use serde_json::Error;

#[derive(Default, Debug, Clone, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct AllConfig {
    #[serde(rename = "train_data")]
    pub train_data: TrainData,
}

#[derive(Default, Debug, Clone, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct TrainData {
    #[serde(rename = "seg_dense_schema")]
    pub seg_dense_schema: SegDenseSchema,
}

#[derive(Default, Debug, Clone, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct SegDenseSchema {
    #[serde(rename = "renamed_features")]
    pub renamed_features: RenamedFeatures,
}

#[derive(Default, Debug, Clone, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct RenamedFeatures {
    pub continuous: String,
    pub binary: String,
    pub discrete: String,
    #[serde(rename = "author_embedding")]
    pub author_embedding: String,
    #[serde(rename = "user_embedding")]
    pub user_embedding: String,
    #[serde(rename = "user_eng_embedding")]
    pub user_eng_embedding: String,
    #[serde(rename = "meta__author_id")]
    pub meta_author_id: String,
    #[serde(rename = "meta__user_id")]
    pub meta_user_id: String,
    #[serde(rename = "meta__tweet_id")]
    pub meta_tweet_id: String,
}

pub fn parse(json_str: &str) -> Result<AllConfig, Error> {
    let all_config: AllConfig = serde_json::from_str(json_str)?;
    Ok(all_config)
}
