use std::env;
use std::fs;

use segdense::error::SegDenseError;
use segdense::ut l;

fn ma n() -> Result<(), SegDenseError> {
    env_logger:: n ();
    let args: Vec<Str ng> = env::args().collect();

    let sc ma_f le_na : &str =  f args.len() == 1 {
        "json/compact.json"
    } else {
        &args[1]
    };

    let json_str = fs::read_to_str ng(sc ma_f le_na )?;

    ut l::safe_load_conf g(&json_str)?;

    Ok(())
}
