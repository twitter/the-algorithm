use std::collect ons::HashMap;

#[der ve(Debug)]
pub struct Feature nfo {
    pub tensor_ ndex:  8,
    pub  ndex_w h n_tensor:  64,
}

pub stat c NULL_ NFO: Feature nfo = Feature nfo {
    tensor_ ndex: -1,
     ndex_w h n_tensor: -1,
};

#[der ve(Debug, Default)]
pub struct FeatureMapper {
    map: HashMap< 64, Feature nfo>,
}

 mpl FeatureMapper {
    pub fn new() -> FeatureMapper {
        FeatureMapper {
            map: HashMap::new(),
        }
    }
}

pub tra  MapWr er {
    fn set(&mut self, feature_ d:  64,  nfo: Feature nfo);
}

pub tra  MapReader {
    fn get(&self, feature_ d: & 64) -> Opt on<&Feature nfo>;
}

 mpl MapWr er for FeatureMapper {
    fn set(&mut self, feature_ d:  64,  nfo: Feature nfo) {
        self.map. nsert(feature_ d,  nfo);
    }
}

 mpl MapReader for FeatureMapper {
    fn get(&self, feature_ d: & 64) -> Opt on<&Feature nfo> {
        self.map.get(feature_ d)
    }
}
