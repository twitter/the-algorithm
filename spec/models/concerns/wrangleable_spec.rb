require "spec_helper"

shared_examples "a wrangleable" do
  shared_examples "no wrangling activity recorded" do
    it "does not set a last wrangling time" do
      expect(wrangleable.save).to be_truthy
      expect(LastWranglingActivity.all).to be_empty
    end
  end

  describe "#update_last_wrangling_activity" do
    context "as a tag wrangler" do
      before { User.current_user = create(:tag_wrangler) }

      context "a wrangling activity has happened" do
        before { User.should_update_wrangling_activity = true }

        it "sets a last wrangling time" do
          freeze_time do
            expect(wrangleable.save).to be_truthy
            expect(User.current_user.last_wrangling_activity.updated_at).to eq(Time.now.utc)
          end
        end
      end

      context "no wrangling activity has happened" do
        before { User.should_update_wrangling_activity = false }

        include_examples "no wrangling activity recorded"
      end
    end
  end

  [
    ["regular user", FactoryBot.create(:user)],
    ["admin", FactoryBot.create(:admin)],
    ["nil", nil]
  ].each do |role, user|
    context "as #{role}" do
      before { User.current_user = user }

      context "a wrangling activity has happened" do
        before { User.should_update_wrangling_activity = true }

        include_examples "no wrangling activity recorded"
      end

      context "no wrangling activity has happened" do
        before { User.should_update_wrangling_activity = false }

        include_examples "no wrangling activity recorded"
      end
    end
  end
end

describe CommonTagging do
  let(:wrangleable) { build(:common_tagging) }

  it_behaves_like "a wrangleable"
end

describe MetaTagging do
  let(:wrangleable) { build(:meta_tagging) }

  it_behaves_like "a wrangleable"
end

describe Tag do
  let(:wrangleable) { build(:character) }

  it_behaves_like "a wrangleable"
end
