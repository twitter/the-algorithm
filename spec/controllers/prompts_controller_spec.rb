require "spec_helper"

describe PromptsController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:collection) { create(:collection) }
  let(:open_signup) do
    signups = create(:challenge_signup)
    signups.collection.challenge.signup_open = true
    signups.collection.challenge.save
    signups
  end
  let(:signup) { create(:challenge_signup) }

  let(:user) { create(:user) }
  before do
    fake_login_known_user(user)
  end

  describe "show" do
    shared_examples "displays the prompt" do
      # expects let variables :collection, :prompt
      it "displays the prompt" do
        get :show, params: { collection_id: collection.name, id: prompt.id }
        expect(flash[:error]).blank?
        expect(response).to have_http_status(200)
        expect(response).to render_template("prompts/show")
        expect(assigns(:prompt)).to eq(prompt)
      end
    end

    context "in a prompt meme" do
      let(:signup) { create(:prompt_meme_signup) }
      let(:collection) { signup.collection }
      let(:prompt) { signup.requests.first }

      context "when logged in as a random user" do
        let(:user) { create(:user) }
        include_examples "displays the prompt"
      end
    end

    context "in a gift exchange" do
      let(:signup) { create(:gift_exchange_signup) }
      let(:collection) { signup.collection }
      let(:prompt) { signup.requests.first }

      context "when logged in as the prompt owner" do
        let(:user) { prompt.pseud.user }
        include_examples "displays the prompt"
      end

      context "when logged in as the collection owner" do
        let(:user) { collection.owners.first.user }
        include_examples "displays the prompt"
      end

      context "when logged in as a random user" do
        let(:user) { create(:user) }

        it "redirects and shows an error" do
          get :show, params: { collection_id: collection.name, id: prompt.id }
          it_redirects_to_with_error(collection, "Sorry, you don't have permission to access the page you were trying to reach.")
        end
      end
    end

    context "when the prompt is in another collection" do
      let(:target_signup) { create(:gift_exchange_signup) }
      let(:target_prompt) { target_signup.requests.first }

      let(:other_collection) { create(:collection, challenge: create(:gift_exchange)) }
      let(:user) { other_collection.owners.first.user }

      it "redirects and shows an error" do
        get :show, params: { collection_id: other_collection.name,
                             id: target_prompt.id }
        it_redirects_to_with_error(other_collection, "Sorry, that prompt isn't associated with that collection.")
      end
    end
  end

  describe "no_challenge" do
    it "should show an error and redirect" do
      get :show, params: { id: 0, collection_id: collection.name }
      it_redirects_to_with_error(collection_path(collection), "What challenge did you want to sign up for?")
    end
  end

  describe "no_signup" do
    it "should show an error and redirect" do
      post :create, params: { collection_id: signup.collection.name }
      it_redirects_to_with_error(collection_signups_path(signup.collection) + "/new",\
                                 "Please submit a basic sign-up with the required fields first.")
    end
  end

  describe "signups_closed" do
    let(:user) { Pseud.find(ChallengeSignup.in_collection(signup.collection).first.pseud_id).user }
    it "should show an error and redirect" do
      post :create, params: { collection_id: signup.collection.name }
      it_redirects_to_with_error(signup.collection, "Signup is currently closed: please contact a moderator for help.")
    end
  end

  describe "not_signup_owner" do
    it "should show an error and redirect" do
      prompt = signup.collection.prompts.first
      post :edit, params: { id: prompt.id, collection_id: signup.collection.name }
      it_redirects_to_with_error(signup.collection, "You can't edit someone else's sign-up!")
    end
  end

  describe "new" do
    context "when prompt_type is offer" do
      let(:user) { Pseud.find(ChallengeSignup.in_collection(open_signup.collection).first.pseud_id).user }
      it "should have no errors" do
        post :new, params: { collection_id: open_signup.collection.name, prompt_type: "offer" }
        expect(response).to have_http_status(200)
        expect(flash[:error]).blank?
        expect(assigns(:index)).to eq(open_signup.offers.count)
      end
    end
  end

  describe "create" do
    let(:user) { Pseud.find(ChallengeSignup.in_collection(open_signup.collection).first.pseud_id).user }
    it "should have no errors and redirect to the edit page" do
      post :create, params: { collection_id: open_signup.collection.name, prompt_type: "offer", prompt: { description: "This is a description." } }
      it_redirects_to_simple("#{collection_signups_path(open_signup.collection)}/"\
                      "#{open_signup.collection.prompts.first.challenge_signup_id}/edit")
    end
  end

  describe "update" do
    context "when prompt is valid" do
      let(:user) { Pseud.find(ChallengeSignup.in_collection(open_signup.collection).first.pseud_id).user }
      it "should save the prompt and redirect with a success message" do
        parameters = {
          collection_id: open_signup.collection.name,
          prompt_type: "offer",
          prompt: {
            description: "This is a description"
          },
          id: open_signup.collection.prompts.first.id
        }

        put :update, params: parameters
        it_redirects_to_with_notice("#{collection_signups_path(open_signup.collection)}/#{open_signup.id}",
                                    "Prompt was successfully updated.")
        new_prompt = open_signup.collection.prompts.first
        expect(new_prompt.description).to eq("<p>This is a description</p>")
      end
    end
  end

  describe "destroy" do
    let(:user) { Pseud.find(ChallengeSignup.in_collection(signup.collection).first.pseud_id).user }
    it "redirects with an error when sign-ups are closed" do
      prompt = signup.collection.prompts.first
      delete :destroy, params: { collection_id: signup.collection.name, id: prompt.id }
      it_redirects_to_with_error("#{collection_signups_path(signup.collection)}/#{signup.id}",
                                 "You cannot delete a prompt after sign-ups are closed."\
                                  " Please contact a moderator for help.")
    end

    context "where current_user is signup owner" do
      let(:user) { Pseud.find(ChallengeSignup.in_collection(open_signup.collection).first.pseud_id).user }
      it "redirects with an error when it would make a sign-up invalid" do
        parameters = {
          collection_id: open_signup.collection.name,
          id: open_signup.collection.prompts.first.id
        }

        delete :destroy, params: parameters
        it_redirects_to_with_error("#{collection_signups_path(open_signup.collection)}/#{open_signup.id}",
                                   "That would make your sign-up invalid, sorry! Please edit instead.")
      end

      it "deletes the prompt and redirects with a success message" do
        prompt = open_signup.offers.build(pseud_id: ChallengeSignup.in_collection(open_signup.collection).first.pseud_id,\
                                          collection_id: open_signup.collection.id)
        prompt.save
        delete :destroy, params: { collection_id: open_signup.collection.name, id: prompt.id }
        it_redirects_to_with_notice("#{collection_signups_path(open_signup.collection)}/#{open_signup.id}",
                                    "Prompt was deleted.")
      end
    end
  end

  describe "edit" do
    context "no prompt" do
      it "should show an error and redirect" do
        post :edit, params: { id: 0, collection_id: signup.collection.name }
        it_redirects_to_with_error(collection_path(signup.collection), "What prompt did you want to work on?")
      end
    end
  end
end
