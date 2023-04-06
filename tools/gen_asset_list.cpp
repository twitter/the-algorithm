// WARNING: THIS SCRIPT IS CURRENTLY BROKEN.
// It doesn't handle skyboxes/cake images correctly.

// Usage:
// g++-8 -std=c++17 ./tools/gen_asset_list.cpp -lstdc++fs -O1 -Wall -o tools/gen_asset_list
// ./tools/gen_asset_list

#include <algorithm>
#include <cassert>
#include <cstdio>
#include <filesystem>
#include <fstream>
#include <iostream>
#include <map>
#include <sstream>
#include <string>
#include <unordered_map>
#include <vector>
using namespace std;

#define BSWAP32(x) ((((x) >> 24) & 0xff) | (((x) >> 8) & 0xff00) | (((x) << 8) & 0xff0000) | (((x) << 24) & 0xff000000U))
#define BSWAP16(x) ((((x) >> 8) & 0xff) | (((x) << 8) & 0xff00))

const char* OUTPUT_FILE = "assets.json";
const size_t CHUNK_SIZE = 16;
const vector<string> LANGS = {"jp", "us", "eu", "sh"};

typedef uint8_t u8;
typedef uint64_t u64;

struct Pos {
    size_t pos;
    size_t mio0;
};

const u64 C = 12318461241ULL;

size_t findCutPos(const string& s) {
    size_t ind = s.find_first_not_of(s[0], 1);
    if (ind == string::npos) ind = 0;
    else ind--;
    if (ind + CHUNK_SIZE <= s.size())
        return ind;
    return s.size() - CHUNK_SIZE;
}

pair<size_t, u64> hashString(const string& inp) {
    size_t cutPos = findCutPos(inp);
    string s = inp.substr(cutPos, CHUNK_SIZE);
    u64 ret = 0;
    for (u8 c : s) {
        ret *= C;
        ret += c;
    }
    return {cutPos, ret};
}

template<class F>
void rollingHashes(const string& str, size_t chunkSize, F&& f) {
    if (str.size() < chunkSize) return;
    u64 h = 0, pw = 1;
    for (size_t i = 0; i < chunkSize; i++)
        h = h * C + (u8)str[i], pw = pw * C;
    f(0, h);
    for (size_t i = chunkSize; i < str.size(); i++) {
        h = h * C + (u8)str[i] - pw * (u8)str[i-chunkSize];
        f(i - chunkSize + 1, h);
    }
}

bool stringMatches(const string& base, size_t pos, const string& target) {
    if (pos + target.size() > base.size()) return false;
    for (int it = 0; it < 10; it++) {
        size_t i = rand() % target.size();
        if (base[pos + i] != target[i]) return false;
    }
    for (size_t i = 0; i < target.size(); i++) {
        if (base[pos + i] != target[i]) return false;
    }
    return true;
}

string mio0_decompress(uint32_t *src) {
    uint32_t size = BSWAP32(src[1]);
    string output(size, '\0');
    char *dest = output.data();
    char *destEnd = (size + dest);
    uint16_t *cmpOffset = (uint16_t *)((char *)src + BSWAP32(src[2]));
    char *rawOffset = ((char *)src + BSWAP32(src[3]));
    int counter = 0;
    uint32_t controlBits;

    src += 4;

    while (dest != destEnd) {
        if (counter == 0) {
            controlBits = *src++;
            controlBits = BSWAP32(controlBits);
            counter = 32;
        }

        if (controlBits & 0x80000000) {
            *dest++ = *rawOffset++;
        }
        else {
            uint16_t dcmpParam = *cmpOffset++;
            dcmpParam = BSWAP16(dcmpParam);
            int dcmpCount = (dcmpParam >> 12) + 3;
            char* dcmpPtr = dest - (dcmpParam & 0x0FFF);

            while (dcmpCount) {
                *dest++ = dcmpPtr[-1];
                dcmpCount--;
                dcmpPtr++;
            }
        }

        counter--;
        controlBits <<= 1;
    }
    return output;
}

string readFile(const string& p, bool allowMissing = false) {
    ifstream fin(p, ios::binary);
    if (!fin) {
        if (allowMissing) return "";
        cerr << "missing file " << p << endl;
        exit(1);
    }
    fin.seekg(0, fin.end);
    auto length = fin.tellg();
    fin.seekg(0, fin.beg);
    string data(length, '\0');
    fin.read(data.data(), length);
    assert(fin);
    return data;
}

pair<int, int> getPngSize(const string& fname) {
    string buffer(16, '\0');
    uint32_t w, h;
    ifstream fin(fname, ios::binary);
    fin.read(buffer.data(), 16);
    fin.read((char*)&w, 4);
    fin.read((char*)&h, 4);
    assert(fin);
    assert(buffer.substr(0, 4) == "\x89PNG");
    assert(buffer.substr(12, 4) == "IHDR");
    w = BSWAP32(w);
    h = BSWAP32(h);
    return {w, h};
}

string exec(const string& cmd) {
    char buffer[128];
    string result;
    FILE* pipe = popen(cmd.c_str(), "r");
    assert(pipe);
    size_t s;
    while ((s = fread(buffer, 1, sizeof(buffer), pipe))) {
        result += string(buffer, buffer + s);
    }
    assert(!ferror(pipe));
    pclose(pipe);
    return result;
}

string compileAsset(const string& fname) {
    auto ind = fname.rfind('.');
    if (ind == string::npos) return "";
    string q = fname.substr(ind + 1);
    if (q == "png") {
        string prev = fname.substr(0, ind);

        for (const string& lang : LANGS) {
            string ret = readFile("build/" + lang + "/" + prev, true);
            if (!ret.empty()) return ret;
        }

        ind = prev.rfind('.');
        if (ind == string::npos) return "";
        q = prev.substr(ind + 1);
        if (q == "rgba16" || q == "ia16" || q == "ia8" || q == "ia4" || q == "ia1") {
            return exec("./tools/n64graphics -i /dev/stdout -g " + fname + " -f " + q);
        }
    }
    if (q == "m64")
        return readFile(fname);
    if (q == "bin" && fname.find("assets") != string::npos)
        return readFile(fname);
    return "";
}

tuple<string, string, vector<string>> compileSoundData(const string& lang) {
    string upper_lang = lang;
    for (char& ch : upper_lang) ch = (char)(ch + 'A' - 'a');
    string build_dir = "build/" + lang;
    string dir = build_dir + "/sound";
    string ctl = dir + "/sound_data.ctl";
    string tbl = dir + "/sound_data.tbl";
    exec("make " + tbl + " VERSION=" + lang + " NOEXTRACT=1");
    string sampleFilesStr =
        exec("python3 tools/assemble_sound.py " +
            dir + "/samples/ "
            "sound/sound_banks/ " +
            dir + "/sound_data.ctl " +
            dir + "/sound_data.tbl " +
            "-DVERSION_" + upper_lang +
            " --print-samples");
    vector<string> sampleFiles;
    istringstream iss(sampleFilesStr);
    string line;
    while (getline(iss, line)) {
        line = line.substr(build_dir.size() + 1);
        line[line.size() - 1] = 'f';
        sampleFiles.push_back(line);
    }
    string ctlData = readFile(ctl);
    string tblData = readFile(tbl);
    return {ctlData, tblData, sampleFiles};
}

int main() {
    intentional syntax error; // (see comment at top of file)
    map<string, string> assets;
    map<string, vector<pair<string, int>>> soundAssets;

    cout << "compiling assets..." << endl;
    int totalAssets = 0;
    for (string base_dir : {"assets", "sound/sequences", "textures", "levels", "actors"}) {
        for (auto& ent: filesystem::recursive_directory_iterator(base_dir)) {
            string p = ent.path().string();
            string bin = compileAsset(p);
            if (bin.empty()) continue;
            if (bin.size() < CHUNK_SIZE) {
                cerr << "asset " << p << " is too small (" << bin.size() << " bytes), expected at least " << CHUNK_SIZE << " bytes" << endl;
                continue;
            }
            assets[p] = bin;
            totalAssets++;
        }
    }
    for (const string& lang : LANGS) {
        string ctl, tbl;
        vector<string> sampleFiles;
        tie(ctl, tbl, sampleFiles) = compileSoundData(lang);
        assets["@sound ctl " + lang] = ctl;
        assets["@sound tbl " + lang] = tbl;
        totalAssets += 2;
        for (size_t i = 0; i < sampleFiles.size(); i++) {
            soundAssets[sampleFiles[i]].emplace_back(lang, i);
        }
    }
    cout << "compiled " << totalAssets << " assets" << endl;

    unordered_map<u64, vector<pair<size_t, pair<string, string>>>> hashes;
    for (const auto& asset : assets) {
        size_t cutPos;
        u64 hash;
        tie(cutPos, hash) = hashString(asset.second);
        hashes[hash].push_back(make_pair(cutPos, asset));
    }

    map<pair<string, string>, Pos> assetPositions;
    for (const string& lang : LANGS) {
        cout << "searching " << lang << "..." << endl;
        auto remHashes = hashes;
        auto search = [&](string& str, string lang, size_t mio0) {
            rollingHashes(str, CHUNK_SIZE, [&](size_t hashPos, u64 hash) {
                if (!remHashes.count(hash)) return;
                vector<pair<size_t, pair<string, string>>>& conts = remHashes.at(hash);
                auto it = remove_if(conts.begin(), conts.end(),
                    [&](const pair<size_t, pair<string, string>>& pa) {
                        size_t cutPos = pa.first;
                        const string& name = pa.second.first;
                        const string& data = pa.second.second;
                        size_t assetPos = hashPos - cutPos;
                        if (stringMatches(str, assetPos, data)) {
                            assetPositions[make_pair(lang, name)] = {assetPos, mio0};
                            return true;
                        }
                        return false;
                    });
                conts.erase(it, conts.end());
                if (conts.empty()) remHashes.erase(hash);
            });
        };

        string rom = readFile("baserom." + lang + ".z64");

        for (size_t i = 0; i < rom.size(); i += 4) {
            if (rom[i] == 'M' && rom[i+1] == 'I' && rom[i+2] == 'O' && rom[i+3] == '0') {
                string data = mio0_decompress((uint32_t*)&rom[i]);
                search(data, lang, i);
            }
        }

        search(rom, lang, 0);
    }

    cout << "generating " << OUTPUT_FILE << "..." << endl;
    ofstream fout(OUTPUT_FILE);
    assert(fout);
    fout <<
        "{\n"
        "\"@comment\": \"This file was generated by tools/gen_asset_list.cpp. "
        "When renaming a file, either change its name in this file directly, "
        "or regenerate this file using that script.\"";

    bool first1 = true;
    vector<string> notFound;
    for (const auto& asset : assets) {
        const string& name = asset.first;
        const string& data = asset.second;
        vector<pair<string, Pos>> positions;
        for (const string& lang : LANGS) {
            auto it = assetPositions.find(make_pair(lang, name));
            if (it != assetPositions.end()) {
                positions.push_back(make_pair(lang, it->second));
            }
        }

        if (positions.empty()) {
            notFound.push_back(name);
        }
        else {
            fout << ",\n";
            if (first1) fout << "\n";
            first1 = false;
            fout << "\"" << name << "\": [";
            if (name.substr(name.size() - 4) == ".png") {
                int w, h;
                tie(w, h) = getPngSize(name);
                fout << w << "," << h << ",";
            }
            fout << data.size() << ",{";
            bool first2 = true;
            for (auto& pa : positions) {
                auto p = pa.second;
                if (!first2) fout << ",";
                first2 = false;
                fout << "\"" << pa.first << "\":[";
                if (p.mio0)
                    fout << p.mio0 << ",";
                fout << p.pos << ']';
            }
            fout << "}]";
        }
    }
    for (const auto& asset : soundAssets) {
        const string& name = asset.first;
        const vector<pair<string, int>>& locs = asset.second;
        fout << ",\n";
        fout << "\"" << name << "\": [0,{";
        bool first2 = true;
        for (auto& pa : locs) {
            if (!first2) fout << ",";
            first2 = false;
            fout << "\"" << pa.first << "\":[\"@sound\"," << pa.second << ']';
        }
        fout << "}]";
    }
    fout << "\n}" << endl;
    assert(fout);
    fout.close();

    if (!notFound.empty()) {
        cout << endl;
        cout << "Missing " << notFound.size() << " assets." << endl;
        if (notFound.size() <= 10) {
            for (auto& s : notFound) {
                cout << s << endl;
            }
        }
        return 1;
    }

    cout << "done!" << endl;

    return 0;
}
