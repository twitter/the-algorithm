# frozen_string_literal: true
require 'spec_helper'

describe FilterCount do
  context "enqueuing filters" do
    let(:tag) { create(:canonical_fandom) }

    it "should place small filters in the small queue" do
      tag.build_filter_count.update(
        public_works_count: 5,
        unhidden_works_count: 4
      )

      FilterCount.enqueue_filter(tag)

      expect(FilterCount::REDIS.smembers(FilterCount::QUEUE_KEY_SMALL)).
        to eq([tag.id.to_s])
      expect(FilterCount::REDIS.smembers(FilterCount::QUEUE_KEY_LARGE)).
        to eq([])
    end

    it "should place large filters in the large queue" do
      tag.build_filter_count.update(
        public_works_count: 5000,
        unhidden_works_count: 4000
      )

      FilterCount.enqueue_filter(tag)

      expect(FilterCount::REDIS.smembers(FilterCount::QUEUE_KEY_SMALL)).
        to eq([])
      expect(FilterCount::REDIS.smembers(FilterCount::QUEUE_KEY_LARGE)).
        to eq([tag.id.to_s])
    end
  end

  context "processing the queue" do
    let(:tag_small) { create(:canonical_fandom) }
    let(:tag_large) { create(:canonical_fandom) }

    before do
      # Set incorrect values, so that we know when they've been recalculated.
      tag_small.build_filter_count.update(public_works_count: 13)
      tag_large.build_filter_count.update(public_works_count: 13)

      FilterCount::REDIS.sadd(FilterCount::QUEUE_KEY_SMALL, tag_small.id)
      FilterCount::REDIS.sadd(FilterCount::QUEUE_KEY_LARGE, tag_large.id)
    end

    context "for small tags" do
      before { FilterCount.update_counts_for_small_queue }

      it "should fix the filter count for tags in the small queue" do
        expect(tag_small.filter_count.reload.public_works_count).to eq 0
      end

      it "should not fix the filter count for tags in the large queue" do
        expect(tag_large.filter_count.reload.public_works_count).to eq 13
      end
    end

    context "for large tags" do
      before { FilterCount.update_counts_for_large_queue }

      it "should fix the filter count for tags in the large queue" do
        expect(tag_large.filter_count.reload.public_works_count).to eq 0
      end

      it "should not fix the filter count for tags in the small queue" do
        expect(tag_small.filter_count.reload.public_works_count).to eq 13
      end
    end
  end
end
