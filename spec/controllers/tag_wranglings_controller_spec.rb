require "spec_helper"

describe TagWranglingsController do
  include LoginMacros
  include RedirectExpectationHelper

  before do
    fake_login
    controller.current_user.roles << Role.new(name: "tag_wrangler")
  end

  shared_examples "set last wrangling activity" do
    it "sets the last wrangling activity time to now", :frozen do
      user = controller.current_user
      expect(user.last_wrangling_activity.updated_at).to eq(Time.now.utc)
    end
  end

  describe "#wrangle" do
    let(:page_options) { { page: 1, sort_column: "name", sort_direction: "ASC" } }

    it "displays error when there are no fandoms to wrangle to" do
      character = create(:character)
      post :wrangle, params: { fandom_string: "", selected_tags: [character.id] }
      it_redirects_to_with_error(tag_wranglings_path(page_options), "There were no Fandom tags!")
    end

    context "when making tags canonical" do
      let(:tag1) { create(:character) }
      let(:tag2) { create(:character) }

      before do
        post :wrangle, params: { canonicals: [tag1.id, tag2.id] }
      end

      include_examples "set last wrangling activity"
    end

    context "when assigning tags to a medium" do
      let(:fandom1) { create(:fandom, canonical: true) }
      let(:fandom2) { create(:fandom, canonical: true) }
      let(:medium) { create(:media) }

      before do
        post :wrangle, params: { media: medium.name, selected_tags: [fandom1.id, fandom2.id] }
      end

      include_examples "set last wrangling activity"
    end

    context "when adding tags to a fandom" do
      let(:tag1) { create(:character) }
      let(:tag2) { create(:character) }
      let(:fandom) { create(:fandom, canonical: true) }

      before do
        post :wrangle, params: { fandom_string: fandom.name, selected_tags: [tag1.id, tag2.id] }
      end

      include_examples "set last wrangling activity"
    end
  end
end
