require 'spec_helper'

describe ChallengeSignupsController do
  include LoginMacros
  include RedirectExpectationHelper
  let(:user) { create(:user) }

  let(:closed_challenge) { create(:gift_exchange, :closed) }
  let(:closed_collection) { create(:collection, challenge: closed_challenge) }
  let(:closed_signup) { create(:gift_exchange_signup, collection_id: closed_collection.id) }
  let(:closed_collection_owner) { User.find(closed_collection.all_owners.first.user_id) }
  let(:closed_signup_owner) { Pseud.find(closed_signup.pseud_id).user }

  let(:open_challenge) { create(:gift_exchange, :open) }
  let(:open_collection) { create(:collection, challenge: open_challenge) }
  let(:open_signup) { create(:gift_exchange_signup, collection_id: open_collection.id) }
  let(:open_collection_owner) { User.find(open_collection.all_owners.first.user_id) }
  let(:open_signup_owner) { Pseud.find(open_signup.pseud_id).user }

  describe "new" do
    context "when sign-ups are closed" do
      it "redirects and errors" do
        fake_login
        get :new, params: { collection_id: closed_collection.name }
        it_redirects_to_with_error(collection_path(closed_collection),
                                   "Sign-up is currently closed: please contact a moderator for help.")
      end
    end

    context "when sign-ups are open" do
      it "redirects to edit page with notice if user is already signed up" do
        fake_login_known_user(open_signup_owner)
        get :new, params: { collection_id: open_collection.name, pseud: user.pseuds.first }
        it_redirects_to_with_notice(edit_collection_signup_path(open_collection, open_signup),
                                    "You are already signed up for this challenge. You can edit your sign-up below.")
      end
    end

    context "when collection has no challenge" do
      let(:plain_collection) { create(:collection) }

      it "redirects and errors" do
        fake_login
        get :new, params: { collection_id: plain_collection }
        it_redirects_to_with_error(collection_path(plain_collection),
                                   "What challenge did you want to sign up for?")
      end
    end
  end

  describe "show" do
    # TODO: AO3-5552
    xit "redirects and errors if there is no sign-up with that id" do
      fake_login
      get :show, params: { id: 0, collection_id: closed_collection.name }
      it_redirects_to_with_error(collection_path(closed_collection),
                                 "What sign-up did you want to work on?")
    end

    it "redirects and errors if the user does not own the sign-up or the collection" do
      fake_login
      get :show, params: { id: closed_signup, collection_id: closed_collection.name }
      it_redirects_to_with_error(collection_path(closed_collection),
                                 "Sorry, you're not allowed to do that.")
    end

    context "when the sign-up is from another collection" do
      it "redirects and errors" do
        fake_login_known_user(closed_collection_owner)
        get :show, params: { collection_id: closed_collection.name,
                             id: open_signup.id }
        it_redirects_to_with_error(closed_collection,
                                   "Sorry, that sign-up isn't associated with that collection.")
      end
    end
  end

  describe "index" do
    it "redirects and errors if the current user is not allowed to see the specified user's sign-ups" do
      fake_login
      get :index, params: { user_id: closed_signup_owner }
      it_redirects_to_with_error(root_path,
                                 "You aren't allowed to see that user's sign-ups.")
    end

    it "redirects and errors if the current user is not allowed to see the CSV" do
      fake_login
      get :index, params: { collection_id: closed_collection.name, format: :csv }
      it_redirects_to_with_error(closed_collection,
                                 "You aren't allowed to see the CSV summary.")
    end
  end

  describe "destroy" do
    context "when sign-ups are open" do
      it "deletes the sign-up and redirects with notice" do
        fake_login_known_user(open_signup_owner)
        delete :destroy, params: { id: open_signup, collection_id: open_collection.name }
        it_redirects_to_with_notice(collection_path(open_collection),
                                    "Challenge sign-up was deleted.")
      end
    end
    context "when sign-ups are closed" do
      it "redirects and errors" do
        fake_login_known_user(closed_signup_owner)
        delete :destroy, params: { id: closed_signup, collection_id: closed_collection.name }
        it_redirects_to_with_error(collection_path(closed_collection),
                                   "You cannot delete your sign-up after sign-ups are closed. Please contact a moderator for help.")
      end
    end
  end

  describe "update" do
    context "when sign-ups are open" do
      let(:params) do
        {
          challenge_signup: { pseud_id: open_signup_owner.pseuds.first.id },
          id: open_signup,
          collection_id: open_collection.name
        }
      end

      it "renders edit if update fails" do
        fake_login_known_user(open_signup_owner)
        allow_any_instance_of(ChallengeSignup).to receive(:update).and_return(false)
        put :update, params: params
        allow_any_instance_of(ChallengeSignup).to receive(:update).and_call_original
        expect(response).to render_template :edit
      end

      it "redirects and errors if the current user can't edit the sign-up" do
        fake_login_known_user(user)
        put :update, params: params
        it_redirects_to_with_error(open_collection,
                                   "You can't edit someone else's sign-up!")
      end
    end

    context "when sign-ups are closed" do
      let(:params) do
        {
          challenge_signup: { pseud_id: closed_signup_owner.pseuds.first.id },
          id: closed_signup,
          collection_id: closed_collection.name
        }
      end

      it "redirects and errors without updating the sign-up" do
        fake_login_known_user(closed_signup_owner)
        put :update, params: params
        it_redirects_to_with_error(closed_collection,
                                   "Sign-up is currently closed: please contact a moderator for help.")
      end

      it "redirects and errors if the current user can't edit the sign-up" do
        fake_login_known_user(user)
        put :update, params: params
        it_redirects_to_with_error(closed_collection,
                                   "You can't edit someone else's sign-up!")
      end
    end

    context "when the sign-up is from another collection" do
      let(:params) do
        {
          challenge_signup: { pseud_id: open_signup.pseud_id },
          id: open_signup,
          collection_id: closed_collection.name
        }
      end

      it "redirects and errors" do
        fake_login_known_user(closed_collection_owner)
        put :update, params: params
        it_redirects_to_with_error(closed_collection,
                                   "Sorry, that sign-up isn't associated with that collection.")
      end
    end
  end

  describe "edit" do
    context "when the sign-up is from another collection" do
      it "redirects and errors" do
        fake_login_known_user(closed_collection_owner)
        get :edit, params: { collection_id: closed_collection.name,
                             id: open_signup.id }
        it_redirects_to_with_error(closed_collection,
                                   "Sorry, that sign-up isn't associated with that collection.")
      end
    end
  end

  describe "gift_exchange_to_csv" do
    let(:collection) { create(:collection, challenge: create(:gift_exchange)) }
    let(:signup) { create(:gift_exchange_signup, collection_id: collection.id) }

    before do
      challenge = collection.challenge
      challenge.offer_restriction.update(title_allowed: true)
      challenge.request_restriction.update(title_allowed: true)

      signup_offer = signup.offers.first
      signup_offer.description = ""
      signup_offer.tag_set = create(:tag_set)
      signup_offer.save

      signup_request = signup.requests.first
      signup_request.description = ""
      signup_request.tag_set = create(:tag_set)
      signup_request.save
    end

    it "generates a CSV with all the challenge information" do
      controller.instance_variable_set(:@challenge, collection.challenge)
      controller.instance_variable_set(:@collection, collection)
      expect(controller.send(:gift_exchange_to_csv))
        .to eq([["Pseud", "Email", "Sign-up URL", "Request 1 Tags", "Request 1 Title", "Request 1 Description", "Offer 1 Tags", "Offer 1 Title", "Offer 1 Description"],
                [signup.pseud.name, signup.pseud.user.email, collection_signup_url(collection, signup),
                 signup.requests.first.tag_set.tags.first.name, "", "", signup.offers.first.tag_set.tags.first.name, "", ""]])
    end
  end

  describe "prompt_meme_to_csv" do
    let(:collection) { create(:collection, challenge: create(:prompt_meme)) }
    let(:signup) { create(:prompt_meme_signup, collection_id: collection.id) }
    let(:challenge) { collection.challenge }
    let(:prompt) { signup.prompts.first }

    before do
      prompt.description = ""
      prompt.tag_set = create(:tag_set)
      prompt.save

      controller.instance_variable_set(:@challenge, collection.challenge)
      controller.instance_variable_set(:@collection, collection)
    end

    it "generates a CSV with all the challenge information" do
      expect(controller.send(:prompt_meme_to_csv))
        .to eq([["Pseud", "Sign-up URL", "Tags", "Description"],
                [signup.pseud.name, collection_signup_url(collection, signup),
                 signup.requests.first.tag_set.tags.first.name, ""]])
    end

    context "when title is allowed" do
      before do
        challenge.request_restriction.update(title_allowed: true)
      end

      it "includes title in CSV" do
        expect(controller.send(:prompt_meme_to_csv))
          .to eq([["Pseud", "Sign-up URL", "Tags", "Title", "Description"],
                  [signup.pseud.name, collection_signup_url(collection, signup),
                   signup.requests.first.tag_set.tags.first.name, "", ""]])
      end
    end

    context "when description is not allowed" do
      before do
        challenge.request_restriction.update(description_allowed: false)
      end

      it "omits description from CSV" do
        expect(controller.send(:prompt_meme_to_csv))
          .to eq([["Pseud", "Sign-up URL", "Tags"],
                  [signup.pseud.name, collection_signup_url(collection, signup),
                   signup.requests.first.tag_set.tags.first.name]])
      end
    end

    context "when prompt is anonymous" do
      before do
        prompt.update(anonymous: true)
      end

      it "omits the anonymous prompt's pseud and sign-up URL from CSV" do
        expect(controller.send(:prompt_meme_to_csv))
          .to eq([["Pseud", "Sign-up URL", "Tags", "Description"],
                  ["(Anonymous)", "", signup.requests.first.tag_set.tags.first.name, ""]])
      end
    end
  end
end
