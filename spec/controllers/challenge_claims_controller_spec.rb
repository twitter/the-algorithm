# frozen_string_literal: true
require 'spec_helper'

describe ChallengeClaimsController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:user) { create(:user) }
  let(:signup) { create(:prompt_meme_signup) }
  let(:collection) { signup.collection }
  let(:claim) { create(:challenge_claim, collection: collection) }

  describe 'index' do
    it 'assigns claims and gives a notice if the collection is closed and the user is not the maintainer' do
      fake_login_known_user(user)
      allow_any_instance_of(Collection).to receive(:closed?) { true }
      get :index, params: { id: claim.id, collection_id: collection.name, for_user: true }
      expect(flash[:notice]).to include("This challenge is currently closed to new posts.")
      expect(assigns(:claims))
    end

    it 'will not allow you to see someone elses claims' do
      second_user = create(:user)
      fake_login_known_user(user)
      get :index, params: { user_id: second_user.login }
      it_redirects_to_with_error(root_path, \
                                 "You aren't allowed to see that user's claims.")
    end

    context "when multiple users have claims" do
      let!(:claim_by_other_user) { create(:challenge_claim, collection: collection, claiming_user: create(:user)) }

      it "does not allow a logged in user to access the page with everyone's claims" do
        fake_login_known_user(user)

        get :index, params: { collection_id: collection.name }

        it_redirects_to_with_error(collection, "Sorry, you're not allowed to do that.")
      end

      it "does not allow a claiming user to access the page with everyone's claims" do
        claim.update!(claiming_user: user)
        fake_login_known_user(user)

        get :index, params: { collection_id: collection.name }

        it_redirects_to_with_error(collection, "Sorry, you're not allowed to do that.")
      end

      it "allows a claiming user to see their own claims" do
        claim.update!(claiming_user: user)
        fake_login_known_user(user)

        get :index, params: { collection_id: collection.name, for_user: true }

        claims = assigns(:claims)
        expect(claims).to include(claim)
        expect(claims).not_to include(claim_by_other_user)
      end

      context "for a maintainer" do
        let(:other_signup) { create(:prompt_meme_signup) }
        let!(:claim_in_other_collection) { create(:challenge_claim, collection: other_signup.collection) }

        it "allows them to see everyone's claims in the collection" do
          fake_login_known_user(collection.all_owners.first.user)

          get :index, params: { collection_id: collection.name }

          claims = assigns(:claims)
          expect(claims).to include(claim)
          expect(claims).to include(claim_by_other_user)
          expect(claims).not_to include(claim_in_other_collection)
        end
      end
    end
  end

  describe 'show' do
    it 'redirects logged in user to the prompt' do
      request_prompt = create(:request, collection_id: collection.id, challenge_signup_id: signup.id)
      claim_with_prompt = create(:challenge_claim, collection: collection, request_prompt_id: request_prompt.id)
      fake_login_known_user(user)
      get :show, params: { id: claim_with_prompt.id, collection_id: collection.name }
      it_redirects_to(collection_prompt_path(collection, claim_with_prompt.request_prompt))
    end

    xit 'needs a collection' do
      fake_login_known_user(user)
      get :show
      it_redirects_to_with_error(root_path, \
                                 "What challenge did you want to work with?")
    end
  end

  describe 'create' do
    let(:prompt) { signup.requests.first }

    it 'sets a notice and redirects' do
      fake_login_known_user(user)
      post :create, params: { collection_id: collection.name, prompt_id: prompt.id }
      it_redirects_to_with_notice(collection_claims_path(collection, for_user: true), \
                                  "New claim made.")
    end

    it 'on an exception gives an error and redirects' do
      fake_login_known_user(user)
      allow_any_instance_of(ChallengeClaim).to receive(:save) { false }
      post :create, params: { collection_id: collection.name, prompt_id: prompt.id }
      it_redirects_to_with_error(collection_claims_path(collection, for_user: true), \
                                 "We couldn't save the new claim.")
    end
  end

  describe "destroy" do
    context "when a user deletes their own claim" do
      before do
        claim.update!(claiming_user: user)
      end

      it "redirects them to their claims in collection page" do
        fake_login_known_user(user)

        delete :destroy, params: { id: claim.id, collection_id: collection.name }

        it_redirects_to_with_notice(collection_claims_path(collection, for_user: true),
                                    "Your claim was deleted.")
      end
    end

    context "when a maintainer deletes someone else's claim" do
      it "redirects them to the collection claims page" do
        fake_login_known_user(collection.all_owners.first.user)

        delete :destroy, params: { id: claim.id, collection_id: collection.name }

        it_redirects_to_with_notice(collection_claims_path(collection),
                                    "The claim was deleted.")
      end
    end

    context "when an exception occurs" do
      before do
        allow_any_instance_of(ChallengeClaim).to receive(:destroy) { raise ActiveRecord::RecordNotDestroyed }
      end

      it "gives an error and redirects" do
        fake_login_known_user(collection.all_owners.first.user)

        delete :destroy, params: { id: claim.id, collection_id: collection.name }

        it_redirects_to_with_error(collection_claims_path(collection), \
                                   "We couldn't delete that right now, sorry! Please try again later.")
      end
    end
  end
end
