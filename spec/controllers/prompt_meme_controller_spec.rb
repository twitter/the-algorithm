require 'spec_helper'

describe Challenge::PromptMemeController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:challenge) { PromptMeme.new }
  let(:collection) { create(:collection, challenge: challenge) }
  let(:owner) { collection.owners.first.user }

  before { fake_login_known_user(owner) }

  describe "new" do
    context "when the collection already has a challenge" do
      before do
        post :new, params: { collection_id: collection.name }
      end

      it "redirects to edit meme collection path with notice" do
        it_redirects_to_with_notice(edit_collection_prompt_meme_path(collection), "There is already a challenge set up for this collection.")
      end
    end
  end

  describe "create" do
    let(:challenge) { nil }

    it "parses dates in the specified time zone, but doesn't change Time.zone" do
      # Use travel_to so that we don't get any daylight savings time issues:
      travel_to Time.utc(2021, 6, 24) do
        expect do
          post :create, params: {
            collection_id: collection.name,
            prompt_meme: {
              signup_open: false,
              time_zone: "Sydney",
              signups_open_at_string: "2021-07-01 6:00 AM"
            }
          }
        end.not_to change { Time.zone }

        # Sydney is at +10, so we expect the UTC time to be 10 hours earlier:
        expect(collection.reload.challenge.signups_open_at).to eq(Time.utc(2021, 6, 30, 20))
      end
    end
  end

  describe "edit" do
    let(:challenge) do
      PromptMeme.new(time_zone: "Sydney",
                     signup_open: false,
                     signups_open_at: Time.utc(2021, 6, 30, 20))
    end

    it "displays dates in the challenge time zone, but doesn't change Time.zone" do
      # Use travel_to so that we don't get any daylight savings time issues:
      travel_to Time.utc(2021, 6, 24) do
        expect do
          get :edit, params: { collection_id: collection.name }
        end.not_to change { Time.zone }

        # Sydney is at +10, so the time in the form should be 10 hours after the UTC time:
        expect(assigns[:challenge].signups_open_at_string).to eq("2021-07-01 06:00AM")
      end
    end
  end

  describe "update" do
    context "when it fails to update parameters" do
      before do
        post :update, params: { collection_id: collection.name, prompt_meme: { requests_num_required: -1 } }
      end

      it "renders edit page" do
        expect(response).to render_template "edit"
      end
    end

    it "parses dates in the specified time zone, but doesn't change Time.zone" do
      # Use travel_to so that we don't get any daylight savings time issues:
      travel_to Time.utc(2021, 6, 24) do
        expect do
          put :update, params: {
            collection_id: collection.name,
            prompt_meme: {
              signup_open: false,
              time_zone: "Sydney",
              signups_open_at_string: "2021-07-01 6:00 AM"
            }
          }
        end.not_to change { Time.zone }

        # Sydney is at +10, so we expect the UTC time to be 10 hours earlier:
        expect(collection.reload.challenge.signups_open_at).to eq(Time.utc(2021, 6, 30, 20))
      end
    end
  end

  describe "destroy" do
    before do
      delete :destroy, params: { collection_id: collection.name }
    end

    it "remove challenge variables on Collection" do
      expect(collection.reload.challenge_id).to eq(nil)
      expect(collection.reload.challenge_type).to eq(nil)
    end

    it "sets a flash message" do
      expect(flash[:notice]).to eq("Challenge settings were deleted.")
    end

    it "redirects to the collection's main page" do
      expect(response).to redirect_to(collection)
    end
  end
end
