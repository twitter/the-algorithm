require "spec_helper"

describe ShareHelper do
  before do
    # The admin check is defined in ApplicationController
    # and is unavailable for helper specs.
    allow(helper).to receive(:logged_in_as_admin?).and_return(false)

    # Stub a Devise helper for creator checks
    allow(helper).to receive(:current_user)
  end

  describe "#get_tumblr_embed_link_title" do
    context "on anonymous works" do
      let(:work) { build_stubbed(:work, in_anon_collection: true) }

      it "does not link to a user's profile" do
        expect(helper.get_tumblr_embed_link_title(work)).to include("by Anonymous")
      end
    end
  end

  describe "#get_tweet_text" do
    context "on unrevealed works" do
      let(:work) { build_stubbed(:work, in_unrevealed_collection: true) }

      it "returns 'Mystery Work'" do
        expect(helper.get_tweet_text(work)).to eq("Mystery Work")
      end
    end

    context "on anonymous works" do
      let(:work) { build_stubbed(:work, in_anon_collection: true) }

      it "lists the creator as 'Anonymous'" do
        expect(helper.get_tweet_text(work)).to include "by Anonymous"
      end
    end

    context "when work has three or more fandoms" do
      let(:work) { create(:work, fandom_string: "saiki k, mob psycho 100, spy x family") }

      it "lists the fandom as 'Multifandom'" do
        expect(helper.get_tweet_text(work)).to include " - Multifandom"
        expect(helper.get_tweet_text(work)).not_to include "saiki k"
      end
    end

    context "when work is revealed, non-anonymous, and has one fandom" do
      let(:work) { create(:work, title: "the coffee shop at the end of the universe") }

      it "includes all info" do
        text = "the coffee shop at the end of the universe by #{work.pseuds.first.byline} - Testing"
        expect(helper.get_tweet_text(work)).to eq(text)
      end
    end
  end

  describe "#get_tweet_text_for_bookmark" do
    context "on bookmarked works" do
      let(:work) { create(:work, title: "MAMA 2020", fandom_string: "K/DA") }
      let(:bookmark) { build_stubbed(:bookmark, bookmarkable: work) }

      it "returns a formatted tweet" do
        text = "Bookmark of MAMA 2020 by #{work.pseuds.first.byline} - K/DA".truncate(83)
        expect(helper.get_tweet_text_for_bookmark(bookmark)).to eq(text)
      end
    end
  end
end
