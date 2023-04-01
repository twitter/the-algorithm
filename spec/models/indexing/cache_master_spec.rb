require 'spec_helper'

describe CacheMaster do
  let(:cache_master) { CacheMaster.new(123_456) }

  it "should have a key" do
    expect(cache_master.key).to eq("works:123456:assocs")
  end

  it "should record deleted associations" do
    cache_master.record('tag', 5)
    expect(cache_master.get_hash).to eq("tag" => "5")
  end

  it "should combine multiple deleted associations" do
    cache_master.record('tag', 5)
    cache_master.record('tag', 6)
    cache_master.record('pseud', 7)
    expect(cache_master.get_hash).to eq({ "tag" => "5,6", "pseud" => "7" })
  end

  it "should expire caches" do
    cache_master.record('tag', 5)
    cache_master.record('tag', 6)
    cache_master.record('pseud', 7)
    expect(Tag).to receive(:expire_ids).with(['5', '6'])
    cache_master.expire
  end

  it "should not retain data after expiring caches" do
    cache_master.record('tag', 5)
    cache_master.record('tag', 6)
    cache_master.record('pseud', 7)
    cache_master.expire
    expect(cache_master.get_hash).to eq({})
  end
end
