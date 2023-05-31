use serde::{Deserialize, Serialize};
use serde_json::Value;

#[derive(Default, Debug, Clone, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct Root {
    #[serde(rename = "common_prefix")]
    pub common_prefix: String,
    #[serde(rename = "densification_transform_spec")]
    pub densification_transform_spec: DensificationTransformSpec,
    #[serde(rename = "identity_transform_spec")]
    pub identity_transform_spec: Vec<IdentityTransformSpec>,
    #[serde(rename = "complex_feature_type_transform_spec")]
    pub complex_feature_type_transform_spec: Vec<ComplexFeatureTypeTransformSpec>,
    #[serde(rename = "input_features_map")]
    pub input_features_map: Value,
}

#[derive(Default, Debug, Clone, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct DensificationTransformSpec {
    pub discrete: Discrete,
    pub cont: Cont,
    pub binary: Binary,
    pub string: Value, // Use StringType
    pub blob: Blob,
}

#[derive(Default, Debug, Clone, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct Discrete {
    pub tag: String,
    #[serde(rename = "generic_feature_type")]
    pub generic_feature_type: i64,
    #[serde(rename = "feature_identifier")]
    pub feature_identifier: String,
    #[serde(rename = "fixed_length")]
    pub fixed_length: i64,
    #[serde(rename = "default_value")]
    pub default_value: DefaultValue,
    #[serde(rename = "input_features")]
    pub input_features: Vec<InputFeature>,
}

#[derive(Default, Debug, Clone, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct DefaultValue {
    #[serde(rename = "type")]
    pub type_field: String,
    pub value: String,
}

#[derive(Default, Debug, Clone, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct InputFeature {
    #[serde(rename = "feature_id")]
    pub feature_id: i64,
    #[serde(rename = "full_feature_name")]
    pub full_feature_name: String,
    #[serde(rename = "feature_type")]
    pub feature_type: i64,
    pub index: i64,
    #[serde(rename = "maybe_exclude")]
    pub maybe_exclude: bool,
    pub tag: String,
    #[serde(rename = "added_at")]
    pub added_at: i64,
}

#[derive(Default, Debug, Clone, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct Cont {
    pub tag: String,
    #[serde(rename = "generic_feature_type")]
    pub generic_feature_type: i64,
    #[serde(rename = "feature_identifier")]
    pub feature_identifier: String,
    #[serde(rename = "fixed_length")]
    pub fixed_length: i64,
    #[serde(rename = "default_value")]
    pub default_value: DefaultValue,
    #[serde(rename = "input_features")]
    pub input_features: Vec<InputFeature>,
}

#[derive(Default, Debug, Clone, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct Binary {
    pub tag: String,
    #[serde(rename = "generic_feature_type")]
    pub generic_feature_type: i64,
    #[serde(rename = "feature_identifier")]
    pub feature_identifier: String,
    #[serde(rename = "fixed_length")]
    pub fixed_length: i64,
    #[serde(rename = "default_value")]
    pub default_value: DefaultValue,
    #[serde(rename = "input_features")]
    pub input_features: Vec<InputFeature>,
}

#[derive(Default, Debug, Clone, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct StringType {
    pub tag: String,
    #[serde(rename = "generic_feature_type")]
    pub generic_feature_type: i64,
    #[serde(rename = "feature_identifier")]
    pub feature_identifier: String,
    #[serde(rename = "fixed_length")]
    pub fixed_length: i64,
    #[serde(rename = "default_value")]
    pub default_value: DefaultValue,
    #[serde(rename = "input_features")]
    pub input_features: Vec<InputFeature>,
}

#[derive(Default, Debug, Clone, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct Blob {
    pub tag: String,
    #[serde(rename = "generic_feature_type")]
    pub generic_feature_type: i64,
    #[serde(rename = "feature_identifier")]
    pub feature_identifier: String,
    #[serde(rename = "fixed_length")]
    pub fixed_length: i64,
    #[serde(rename = "default_value")]
    pub default_value: DefaultValue,
    #[serde(rename = "input_features")]
    pub input_features: Vec<Value>,
}

#[derive(Default, Debug, Clone, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct IdentityTransformSpec {
    #[serde(rename = "feature_id")]
    pub feature_id: i64,
    #[serde(rename = "full_feature_name")]
    pub full_feature_name: String,
    #[serde(rename = "feature_type")]
    pub feature_type: i64,
}

#[derive(Default, Debug, Clone, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct ComplexFeatureTypeTransformSpec {
    #[serde(rename = "feature_id")]
    pub feature_id: i64,
    #[serde(rename = "full_feature_name")]
    pub full_feature_name: String,
    #[serde(rename = "feature_type")]
    pub feature_type: i64,
    pub index: i64,
    #[serde(rename = "maybe_exclude")]
    pub maybe_exclude: bool,
    pub tag: String,
    #[serde(rename = "tensor_data_type")]
    pub tensor_data_type: Option<i64>,
    #[serde(rename = "added_at")]
    pub added_at: i64,
    #[serde(rename = "tensor_shape")]
    #[serde(default)]
    pub tensor_shape: Vec<i64>,
}

#[derive(Default, Debug, Clone, PartialEq, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct InputFeatureMapRecord {
    #[serde(rename = "feature_id")]
    pub feature_id: i64,
    #[serde(rename = "full_feature_name")]
    pub full_feature_name: String,
    #[serde(rename = "feature_type")]
    pub feature_type: i64,
    pub index: i64,
    #[serde(rename = "maybe_exclude")]
    pub maybe_exclude: bool,
    pub tag: String,
    #[serde(rename = "added_at")]
    pub added_at: i64,
}
