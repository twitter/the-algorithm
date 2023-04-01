require "spec_helper"

describe ChallengeAssignmentsController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:gift_exchange) { create(:gift_exchange) }
  let(:collection) { create(:collection, challenge: gift_exchange) }

  let(:assignment) do
    create(:challenge_assignment,
           collection_id: collection.id,
           sent_at: gift_exchange.assignments_sent_at)
  end

  let(:assignment_owner) { assignment.offering_user }
  let(:collection_owner) { collection.owners.first.user }
  let(:other_user) { create(:user) }

  before { fake_login_known_user(user) }

  describe "default" do
    let(:gift_exchange) { create(:gift_exchange, assignments_sent_at: Faker::Time.backward) }

    context "when logged in as the owner of the collection" do
      let(:user) { collection_owner }

      it "doesn't default the assignment, and redirects with an error" do
        get :default, params: { collection_id: collection.name, id: assignment.id }
        it_redirects_to_with_error(root_path, "You aren't the owner of that assignment.")
        expect(assignment.reload.defaulted_at).to be_nil
      end
    end

    context "when logged in as the owner of the assignment" do
      let(:user) { assignment_owner }

      it "defaults and redirects to the current user's assignments path" do
        get :default, params: { collection_id: collection.name, id: assignment.id }
        it_redirects_to_with_notice(user_assignments_path(user),
                                    "We have notified the collection maintainers that you had to default on your assignment.")
        expect(assignment.reload.defaulted_at).not_to be_nil
      end

      context "when assignments have not been sent" do
        let(:gift_exchange) { create(:gift_exchange, assignments_sent_at: nil) }

        it "doesn't default the assignment, and redirects with an error" do
          get :default, params: { collection_id: collection.name, id: assignment.id }
          it_redirects_to_with_error(collection_path(collection),
                                     "Assignments have not been sent! You might want matching instead.")
          expect(assignment.reload.defaulted_at).to be_nil
        end
      end

      context "when the assignment doesn't exist" do
        it "causes a 404 error" do
          expect do
            get :default, params: { collection_id: collection.name, id: -1 }
          end.to raise_exception(ActiveRecord::RecordNotFound)
        end
      end

      context "when the assignment is from another collection" do
        let(:other_assignment) { create(:challenge_assignment) }

        it "causes a 404 error" do
          expect do
            get :default, params: { collection_id: collection.name, id: other_assignment.id }
          end.to raise_exception(ActiveRecord::RecordNotFound)
        end
      end

      context "when the collection doesn't exist" do
        it "redirects with an error" do
          get :default, params: { collection_id: "--MISSING--", id: assignment.id }
          it_redirects_to_with_error(root_path,
                                     "What challenge did you want to work with?")
        end
      end

      context "when the collection is not a challenge" do
        let(:collection) { create(:collection) }

        it "redirects with an error" do
          get :default, params: { collection_id: collection.name, id: assignment.id }
          it_redirects_to_with_error(collection_path(collection),
                                     "What challenge did you want to work with?")
        end
      end
    end

    context "when logged in as a random user" do
      let(:user) { other_user }

      it "doesn't default the assignment, and redirects with an error" do
        get :default, params: { collection_id: collection.name, id: assignment.id }
        it_redirects_to_with_error(root_path, "You aren't the owner of that assignment.")
        expect(assignment.reload.defaulted_at).to be_nil
      end
    end
  end

  describe "set" do
    let(:signup1) { create(:challenge_signup, collection: assignment.collection) }
    let(:signup2) { create(:challenge_signup, collection: assignment.collection) }

    let(:params) do
      {
        collection_id: collection.name,
        challenge_assignments: {
          assignment.id => {
            request_signup_pseud: signup1.pseud.byline,
            offer_signup_pseud: signup2.pseud.byline
          }
        }
      }
    end

    context "when logged in as the collection maintainer" do
      let(:user) { collection_owner }

      it "successfully updates the assignment" do
        PotentialMatch.create(request_signup: signup1,
                              offer_signup: signup2,
                              collection: collection)

        put :set, params: params
        expect { assignment.reload }.to \
          change { assignment.request_signup_id }.to(signup1.id).and \
            change { assignment.offer_signup_id }.to(signup2.id)

        it_redirects_to_with_notice(collection_potential_matches_path(collection),
                                    "Assignments updated")
      end

      context "when there is no potential match" do
        it "doesn't update the assignment, and renders the potential matches index" do
          put :set, params: params
          expect { assignment.reload }.not_to \
            change { [assignment.request_signup_id, assignment.offer_signup_id] }

          expect(flash[:error]).to eq("These assignments could not be saved because the two participants do not match. Did you mean to write in a giver?")
          expect(assigns[:assignments]).to include(assignment)
          expect(response).to render_template("potential_matches/index")
        end
      end

      context "when assignments have been sent" do
        let(:gift_exchange) { create(:gift_exchange, assignments_sent_at: Faker::Time.backward) }

        it "doesn't update the assignment, and redirects with an error" do
          put :set, params: params
          expect { assignment.reload }.not_to \
            change { [assignment.request_signup_id, assignment.offer_signup_id] }

          it_redirects_to_with_error(collection_assignments_path(collection),
                                     "Assignments have already been sent! If necessary, you can purge them.")
        end
      end

      context "when the assignment is from another collection" do
        let(:other_collection) { create(:collection, challenge: create(:gift_exchange)) }

        let(:assignment) do
          create(:challenge_assignment,
                 collection: other_collection,
                 sent_at: Faker::Time.backward)
        end

        it "silently ignores the assignment" do
          put :set, params: params
          expect { assignment.reload }.not_to \
            change { [assignment.request_signup_id, assignment.offer_signup_id] }

          it_redirects_to_with_notice(collection_potential_matches_path(collection),
                                      "Assignments updated")
        end
      end
    end

    context "when logged in as a random user" do
      let(:user) { other_user }

      it "doesn't update the assignment, and redirects with an error" do
        put :set, params: params
        expect { assignment.reload }.not_to \
          change { [assignment.request_signup_id, assignment.offer_signup_id] }

        it_redirects_to_with_error(user_path(user),
                                   "Sorry, you don't have permission to access the page you were trying to reach.")
      end
    end
  end

  describe "update_multiple" do
    let(:gift_exchange) { create(:gift_exchange, assignments_sent_at: Faker::Time.backward) }

    context "when logged in as the collection maintainer" do
      let(:user) { collection_owner }

      context "defaulting" do
        let(:params) do
          {
            collection_id: collection.name,
            "default_#{assignment.id}" => true
          }
        end

        it "marks the assignment as defaulted" do
          put :update_multiple, params: params
          it_redirects_to_with_notice(collection_assignments_path(collection),
                                      "Assignment updates complete!")

          assignment.reload
          expect(assignment.defaulted_at).not_to be_nil
        end

        context "when the assignment is from another collection" do
          let(:other_collection) { create(:collection, challenge: create(:gift_exchange)) }
          let(:assignment) do
            create(:challenge_assignment,
                   collection: other_collection,
                   sent_at: Faker::Time.backward)
          end

          it "doesn't mark the assignment as defaulted, and redirects with an error" do
            put :update_multiple, params: params
            it_redirects_to_with_error(collection_assignments_path(collection),
                                       ["Couldn't find assignment with id #{assignment.id}!"])

            assignment.reload
            expect(assignment.defaulted_at).to be_nil
          end
        end
      end

      context "undefaulting" do
        let(:params) do
          {
            collection_id: collection.name,
            "undefault_#{assignment.id}" => true
          }
        end

        before { assignment.update!(defaulted_at: Faker::Time.backward) }

        it "marks the assignment as undefaulted" do
          put :update_multiple, params: params
          it_redirects_to_with_notice(collection_assignments_path(collection),
                                      "Assignment updates complete!")

          assignment.reload
          expect(assignment.defaulted_at).to be_nil
        end
      end

      context "approving" do
        let(:params) do
          {
            collection_id: collection.name,
            "approve_#{assignment.id}" => true
          }
        end

        let(:work) do
          create(:work, challenge_assignments: [assignment],
                        collections: [collection])
        end

        let(:collection_item) { work.collection_items.find_by(collection: collection) }

        before do
          collection_item.update(collection_approval_status: CollectionItem::NEUTRAL)
        end

        it "marks the collection item as approved", pending: "AO3-6106" do
          put :update_multiple, params: params
          it_redirects_to_with_notice(collection_assignments_path(collection),
                                      "Assignment updates complete!")

          collection_item.reload
          expect(collection_item.collection_approval_status).to eq(CollectionItem::APPROVED)
        end
      end

      context "covering" do
        let(:pinch_hitter) { create(:pseud) }

        let(:params) do
          {
            collection_id: collection.name,
            "cover_#{assignment.id}" => pinch_hitter.byline
          }
        end

        before { assignment.update!(defaulted_at: Faker::Time.backward) }

        it "marks the assignment as undefaulted" do
          expect do
            put :update_multiple, params: params
          end.to change { ChallengeAssignment.count }.by(1)

          it_redirects_to_with_notice(collection_assignments_path(collection),
                                      "Assignment updates complete!")

          assignment.reload
          expect(assignment.covered_at).not_to be_nil
          expect(collection.assignments.find_by(pinch_hitter: pinch_hitter)).not_to be_nil
        end
      end
    end
  end

  describe "show" do
    shared_examples "allowed to see assignment" do
      it "shows the assignment" do
        get :show, params: { id: assignment.id, collection_id: collection.name }
        expect(response).to have_http_status(:success)
        expect(response).to render_template :show
      end

      context "when the assignment is defaulted" do
        before { assignment.update_attribute(:defaulted_at, Faker::Time.backward) }

        it "shows a message" do
          get :show, params: { id: assignment.id, collection_id: collection.name }
          expect(flash[:notice]).to eq("This assignment has been defaulted-on.")
        end
      end

      context "when the assignment is from a different collection" do
        let(:assignment) { create(:challenge_assignment) }

        it "causes a 404 error" do
          expect do
            get :show, params: { id: assignment.id, collection_id: collection.name }
          end.to raise_exception(ActiveRecord::RecordNotFound)
        end
      end
    end

    context "when logged in as the owner of the assignment" do
      let(:user) { assignment_owner }

      include_examples "allowed to see assignment"
    end

    context "when logged in as a moderator of the collection" do
      let(:user) { collection_owner }

      include_examples "allowed to see assignment"
    end

    context "when logged in as a random user" do
      let(:user) { other_user }

      it "doesn't show the assignment" do
        get :show, params: { id: assignment.id, collection_id: collection.name }
        it_redirects_to_with_error(root_path, "You aren't allowed to see that assignment!")
      end
    end
  end

  describe "index" do
    let(:gift_exchange) { create(:gift_exchange, assignments_sent_at: Faker::Time.backward) }
    let!(:open_assignment) do
      create(:challenge_assignment,
             collection_id: collection.id,
             sent_at: gift_exchange.assignments_sent_at)
    end
    let!(:defaulted_assignment) do
      create(:challenge_assignment,
             collection_id: collection.id,
             sent_at: gift_exchange.assignments_sent_at,
             defaulted_at: Faker::Time.backward)
    end

    context "when logged in as a maintainer of the collection", work_search: true, bookmark_search: true do
      render_views

      let(:user) { collection_owner }

      it "shows defaulted assignments within a collection" do
        get :index, params: { collection_id: collection.name }
        expect(response).to have_http_status(:success)
        expect(response.body).to include "Assignments for"
        expect(response.body).to include collection.title
        expect(response.body).to include defaulted_assignment.request_byline
        expect(response.body).not_to include "No assignments to review!"
      end

      it "shows unfulfilled assignments within a collection" do
        get :index, params: { collection_id: collection.name, unfulfilled: true }
        expect(response).to have_http_status(:success)
        expect(response.body).to include "Assignments for"
        expect(response.body).to include collection.title
        expect(response.body).to include open_assignment.request_byline
        expect(response.body).not_to include "No assignments to review!"
      end
    end

    context "when logged in as the owner of an open assignment" do
      let(:user) { open_assignment.offering_user }

      it "lists the assignment" do
        get :index, params: { collection_id: collection.name, user_id: user.login }
        expect(response).to have_http_status(:success)
        expect(assigns[:challenge_assignments]).to include(open_assignment)
        expect(assigns[:challenge_assignments]).not_to include(defaulted_assignment)
        expect(response).to render_template(:index)
      end
    end

    context "when logged in as the owner of a defaulted assignment" do
      let(:user) { defaulted_assignment.offering_user }

      it "doesn't list the assignment" do
        get :index, params: { collection_id: collection.name, user_id: user.login }
        expect(response).to have_http_status(:success)
        expect(assigns[:challenge_assignments]).not_to include(defaulted_assignment)
        expect(assigns[:challenge_assignments]).not_to include(open_assignment)
        expect(response).to render_template(:index)
      end
    end

    context "when logged in as a random user" do
      let(:user) { other_user }

      it "doesn't show any assignments" do
        get :index, params: { collection_id: collection.name, user_id: user.login }
        expect(response).to have_http_status(:success)
        expect(assigns[:challenge_assignments]).not_to include(defaulted_assignment)
        expect(assigns[:challenge_assignments]).not_to include(open_assignment)
        expect(response).to render_template(:index)
      end

      context "when trying to access another user's assignments" do
        it "redirects with an error" do
          get :index, params: { collection_id: collection.name, user_id: open_assignment.offering_user.login }
          it_redirects_to_with_error(root_path,
                                     "You aren't allowed to see that user's assignments.")
        end
      end

      context "when trying to access a non-existent user's assignments" do
        it "redirects with an error" do
          get :index, params: { collection_id: collection.name, user_id: "--MISSING--" }
          it_redirects_to_with_error(user_path(user),
                                     "Sorry, you don't have permission to access the page you were trying to reach.")
        end
      end
    end
  end
end
