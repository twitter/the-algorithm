use std::env;
use std::fs;

use segdense::error::SegDenseError;
use segdense::util;

fn main() -> Result<(), SegDenseError> {
    env_logger::init();
    let args: Vec<String> = env::args().collect();

    let schema_file_name: &str = if args.len() == 1 {
        "json/compact.json"
    } else {
        &args[1]
    };

    let json_str = fs::read_to_string(schema_file_name)?;

    util::safe_load_config(&json_str)?;

    Ok(())
}
