require "spec_helper"

describe RedisHitCounter do
  let(:work_id) { 42 }
  let(:ip_address) { Faker::Internet.ip_v4_address }

  describe ".current_timestamp" do
    it "returns the previous date at 2:59 AM UTC" do
      Delorean.time_travel_to "2020/01/30 2:59 UTC" do
        expect(RedisHitCounter.current_timestamp).to eq("20200129")
      end
    end

    it "returns the current date at 3:00 AM UTC" do
      Delorean.time_travel_to "2020/01/30 3:00 UTC" do
        expect(RedisHitCounter.current_timestamp).to eq("20200130")
      end
    end

    it "returns the current date at 3:01 AM UTC" do
      Delorean.time_travel_to "2020/01/30 3:01 UTC" do
        expect(RedisHitCounter.current_timestamp).to eq("20200130")
      end
    end
  end

  describe ".add" do
    context "when the IP address hasn't visited" do
      it "records the IP address and increments the count" do
        Delorean.time_travel_to "2020/01/30 3:05 UTC" do
          RedisHitCounter.add(work_id, ip_address)
        end

        expect(RedisHitCounter.redis.smembers("visits:20200130")).to \
          eq(["#{work_id}:#{ip_address}"])
        expect(RedisHitCounter.redis.hgetall("recent_counts")).to \
          eq(work_id.to_s => "1")
      end
    end

    context "when the IP address has already visited after 3 AM" do
      before do
        Delorean.time_travel_to "2020/01/30 3:01 UTC" do
          RedisHitCounter.add(work_id, ip_address)
        end

        RedisHitCounter.redis.del("recent_counts")
      end

      it "doesn't increment the count" do
        Delorean.time_travel_to "2020/01/30 3:02 UTC" do
          RedisHitCounter.add(work_id, ip_address)
        end

        expect(RedisHitCounter.redis.hgetall("recent_counts")).to \
          eq({})
      end
    end

    context "when the IP address has already visited before 3 AM" do
      before do
        Delorean.time_travel_to "2020/01/30 2:59 UTC" do
          RedisHitCounter.add(work_id, ip_address)
        end

        RedisHitCounter.redis.del("recent_counts")
      end

      it "increments the count" do
        Delorean.time_travel_to "2020/01/30 3:02 UTC" do
          RedisHitCounter.add(work_id, ip_address)
        end

        expect(RedisHitCounter.redis.hgetall("recent_counts")).to \
          eq(work_id.to_s => "1")
      end
    end
  end

  describe ".save_recent_counts" do
    let(:work_id) { work.id }
    let(:stat_counter) { work.stat_counter }

    before do
      stat_counter.update(hit_count: 3)
      RedisHitCounter.redis.hset("recent_counts", work_id, 10)
    end

    shared_examples "clears the recent counts hash" do
      it "clears the recent counts hash" do
        RedisHitCounter.save_recent_counts

        expect(RedisHitCounter.redis.hgetall("recent_counts")).to \
          eq({})
      end
    end

    context "when the work is visible" do
      let(:work) { create(:work) }

      it "updates the stat counters from redis" do
        RedisHitCounter.save_recent_counts

        expect(stat_counter.reload.hit_count).to eq(13)
      end

      include_examples "clears the recent counts hash"
    end

    shared_examples "doesn't add the hits" do
      it "doesn't add the hits" do
        RedisHitCounter.save_recent_counts

        expect(stat_counter.reload.hit_count).to eq(3)
      end
    end

    context "when the work is a draft" do
      let(:work) { create(:draft) }

      include_examples "doesn't add the hits"
      include_examples "clears the recent counts hash"
    end

    context "when the work is hidden by an admin" do
      let(:work) { create(:work, hidden_by_admin: true) }

      include_examples "doesn't add the hits"
      include_examples "clears the recent counts hash"
    end

    context "when the work is in an unrevealed collection" do
      let(:collection) { create(:unrevealed_collection) }
      let(:work) { create(:work, collections: [collection]) }

      include_examples "doesn't add the hits"
      include_examples "clears the recent counts hash"
    end

    context "when the work doesn't exist" do
      let(:work_id) { 42 }
      let(:stat_counter) { StatCounter.create(work_id: work_id) }

      include_examples "doesn't add the hits"
      include_examples "clears the recent counts hash"
    end
  end

  describe ".remove_old_visits" do
    it "removes information from previous days" do
      Delorean.time_travel_to "2020/01/30 2:59 UTC" do
        RedisHitCounter.add(work_id, ip_address)

        expect(RedisHitCounter.redis.exists("visits:20200129")).to be_truthy
      end

      Delorean.time_travel_to "2020/01/30 3:01 UTC" do
        RedisHitCounter.remove_old_visits

        expect(RedisHitCounter.redis.exists("visits:20200129")).to be_falsey
      end
    end

    it "doesn't remove information from the current day" do
      Delorean.time_travel_to "2020/01/30 2:59 UTC" do
        RedisHitCounter.add(work_id, ip_address)
      end

      Delorean.time_travel_to "2020/01/30 3:01 UTC" do
        RedisHitCounter.add(work_id, ip_address)
      end

      Delorean.time_travel_to "2020/01/30 3:02 UTC" do
        RedisHitCounter.remove_old_visits

        expect(RedisHitCounter.redis.exists("visits:20200129")).to be_falsey
        expect(RedisHitCounter.redis.exists("visits:20200130")).to be_truthy
      end
    end

    it "doesn't modify recent_counts" do
      Delorean.time_travel_to "2020/01/30 2:59 UTC" do
        RedisHitCounter.add(work_id, ip_address)
      end

      Delorean.time_travel_to "2020/01/30 3:01 UTC" do
        RedisHitCounter.add(work_id, ip_address)
      end

      Delorean.time_travel_to "2020/01/30 3:02 UTC" do
        expect do
          RedisHitCounter.remove_old_visits
        end.not_to(change { RedisHitCounter.redis.hgetall("recent_counts") })
      end
    end
  end
end
