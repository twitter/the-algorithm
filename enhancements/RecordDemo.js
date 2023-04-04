/*
 * This is a companion file for the record_demo.inc.c enhancement.
 *
 * You will need the PJ64 javascript API to get this to work, so
 * you should download a nightly build from here (Windows only atm):
 * https://www.pj64-emu.com/nightly-builds
 *
 * Place this .js file into the /Scripts/ folder in the PJ64 directory.
 *
 * In the Scripts window, double click on "RecordDemo" on the list on the left side.
 * When this is done, it should turn green which lets you know that it has started.
 *
 * When your demo has been recorded, it will be dumped to the newly created
 * /SM64_DEMOS/ folder within the PJ64 directory.
 */

var RAM_SIZE = 4 * 1048576 // 4 MB

// Get a copy of the first 4MB of memory.
var RAM = mem.getblock(0x80000000, RAM_SIZE)

// Create SM64_DEMOS Directory if it already doesn't exist.
fs.mkdir("SM64_DEMOS/");

// string "DEMORECVARS"
var pattern = [0x44, 0x45, 0x4D, 0x4F, 0x52, 0x45, 0x43, 0x56, 0x41, 0x52, 0x53, 0x00]

var matches = find_matches_fast(pattern)

if(matches.length > 1) {
    console.log('Error: More than 1 instance of "DEMORECVARS" was found. Abort!')
} else if(matches.length < 1) {
    console.log('Error: No instance of "DEMORECVARS" was found. Abort!')
} else {
    console.clear()
    var demoRecVarsLocation = 0x80000000 + matches[0] + 12

    // Control variables addresses
    var gRecordingStatus_vaddr = demoRecVarsLocation + 0
    var gDoneDelay_vaddr = demoRecVarsLocation + 4
    var gNumOfRecordedInputs_vaddr = demoRecVarsLocation + 8
    var gRecordedInputsPtr_vaddr = demoRecVarsLocation + 12

    console.log('Recording variables were found at address 0x' + demoRecVarsLocation.toString(16))
    console.log('Initialization successful! Press L in-game to ready the demo recording before entering in a level.')

    // This runs every frame that is drawn.
    events.ondraw(function() {
        var gRecordingStatus = mem.u32[gRecordingStatus_vaddr]

        if(gRecordingStatus == 3) { // gRecordingStatus == DEMOREC_STATUS_STOPPING
            var gNumOfRecordedInputs = mem.u32[gNumOfRecordedInputs_vaddr]

            if(gNumOfRecordedInputs < 1) {
                console.log('Error: No inputs could be recorded!')
            } else {
                var gRecordedInputsPtr = mem.u32[gRecordedInputsPtr_vaddr]

                console.log('Recorded ' + gNumOfRecordedInputs + ' demo inputs.')

                // Grab demo data from RAM.
                var demo_data = mem.getblock(gRecordedInputsPtr, (gNumOfRecordedInputs + 1) * 4)

                // Create filename with random id added onto it.
                var filename = 'SM64_DEMOS/demo_' + get_random_int(0, 0xFFFFFFFF).toString(16) + '.bin'

                // Dump demo data to file.
                var file = fs.open(filename, 'wb');
                fs.write(file, demo_data);
                fs.close(file);

                console.log('Dumped data to file ' + filename)
            }

            // Set status to DEMOREC_STATUS_DONE
            mem.u32[gRecordingStatus_vaddr] = 4;

            // Decomp memes
            console.log('OK');
        }
    })
}

function get_random_int(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

/*
 * Finds a byte pattern that is 4-byte aligned.
 *
 * The javascript api is pretty slow when reading memory directly,
 * so I made this to search a copy of RAM to make things a little faster.
 */
function find_matches_fast(pattern) {
    var targetLength = pattern.length
    var targetLengthMinusOne = targetLength - 1
    var matches = []
    var matching = 0

  // Increments by 8 to speed things up.
    for(var i = 0; i < RAM_SIZE; i += 8) {
        if(RAM[i] == pattern[matching])
            matching++
        else
            matching = 0
        if(matching == targetLength) {
            matches.push(i - targetLengthMinusOne)
            matching = 0
        }
    if(matching > 0) {
        if(RAM[i + 1] == pattern[matching])
            matching++
        else
            matching = 0
        if(matching == targetLength) {
            matches.push(i + 1 - targetLengthMinusOne)
            matching = 0
        }
        if(matching > 1) {
            if(RAM[i + 2] == pattern[matching])
                matching++
            else
                matching = 0
            if(matching == targetLength) {
                matches.push(i + 2 - targetLengthMinusOne)
                matching = 0
            }
            if(matching > 2) {
                if(RAM[i + 3] == pattern[matching])
                    matching++
                else
                    matching = 0
                if(matching == targetLength) {
                    matches.push(i + 3 - targetLengthMinusOne)
                    matching = 0
                }
            }
        }
    }

    if(RAM[i + 4] == pattern[matching])
        matching++
    else
        matching = 0
    if(matching == targetLength) {
        matches.push(i + 4 - targetLengthMinusOne)
        matching = 0
    }
    if(matching > 0) {
        if(RAM[i + 5] == pattern[matching])
            matching++
        else
            matching = 0
        if(matching == targetLength) {
            matches.push(i + 5 - targetLengthMinusOne)
            matching = 0
        }
        if(matching > 1) {
            if(RAM[i + 6] == pattern[matching])
                matching++
            else
                matching = 0
            if(matching == targetLength) {
                matches.push(i + 6 - targetLengthMinusOne)
                matching = 0
            }
            if(matching > 2) {
                if(RAM[i + 7] == pattern[matching])
                    matching++
                else
                    matching = 0
                if(matching == targetLength) {
                    matches.push(i + 7 - targetLengthMinusOne)
                    matching = 0
                }
            }
        }
    }
  }

  return matches
}
