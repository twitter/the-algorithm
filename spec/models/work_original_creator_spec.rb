# frozen_string_literal: true

require "spec_helper"

describe WorkOriginalCreator do
  describe "#display" do
    let(:original_creator) { create(:work_original_creator) }
    let(:user) { original_creator.user }

    context "when the associated user exists" do
      it "returns both the id and username" do
        expect(original_creator.display).to eq("#{user.id} (#{user.login})")
      end
    end

    context "when the associated user has been deleted" do
      before { user.destroy! }

      it "returns only the id" do
        expect(original_creator.reload.display).to eq(original_creator.user_id.to_s)
      end
    end
  end

  describe ".cleanup" do
    it "removes creators past the configured TTL" do
      travel_to ArchiveConfig.ORIGINAL_CREATOR_TTL_HOURS.hours.ago do
        create(:work_original_creator)
      end
      expect(described_class.cleanup).to eq(1)
      expect(described_class.all).to be_empty
    end

    it "does not remove creators past the TTL" do
      original_creator = create(:work_original_creator)
      expect(described_class.cleanup).to eq(0)
      expect(described_class.exists?(id: original_creator.id)).to be_truthy
    end
  end
end
