use std::collections::HashMap;

#[derive(Debug)]
pub struct FeatureInfo {
    pub tensor_index: i8,
    pub index_within_tensor: i64,
}

pub static NULL_INFO: FeatureInfo = FeatureInfo {
    tensor_index: -1,
    index_within_tensor: -1,
};

#[derive(Debug, Default)]
pub struct FeatureMapper {
    map: HashMap<i64, FeatureInfo>,
}

impl FeatureMapper {
    pub fn new() -> FeatureMapper {
        FeatureMapper {
            map: HashMap::new(),
        }
    }
}

pub trait MapWriter {
    fn set(&mut self, feature_id: i64, info: FeatureInfo);
}

pub trait MapReader {
    fn get(&self, feature_id: &i64) -> Option<&FeatureInfo>;
}

impl MapWriter for FeatureMapper {
    fn set(&mut self, feature_id: i64, info: FeatureInfo) {
        self.map.insert(feature_id, info);
    }
}

impl MapReader for FeatureMapper {
    fn get(&self, feature_id: &i64) -> Option<&FeatureInfo> {
        self.map.get(feature_id)
    }
}
