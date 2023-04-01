# frozen_string_literal: true
require "spec_helper"

describe CollectionParticipantsController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:user) { create(:user) }
  let(:collection) { create(:collection) }

  describe "join" do
    context "where user isn't logged in" do
      it "redirects to new user session with error" do
        get :join, params: { collection_id: collection.name }
        it_redirects_to_with_error(new_user_session_path,
                                   "Sorry, you don't have permission to access the page"\
                                   " you were trying to reach. Please log in.")
      end
    end

    context "where the user is logged in" do
      before do
        fake_login_known_user(user)
      end

      context "where there is no collection" do
        it "raises a RecordNotFound error" do
          expect do
            get :join, params: { collection_id: 0 }
          end.to raise_exception(ActiveRecord::RecordNotFound)
        end
      end

      context "where there is a collection" do
        let(:current_role) { CollectionParticipant::NONE }

        context "where the user is already a participant" do
          let!(:participant) do
            create(
              :collection_participant,
              collection: collection,
              pseud: user.default_pseud,
              participant_role: current_role
            )
          end

          context "where the user has been invited" do
            let(:current_role) { CollectionParticipant::INVITED }

            it "approves the participant and redirects to the index" do
              get :join, params: { collection_id: collection.name }
              expect(CollectionParticipant.find(participant.id).participant_role).to eq CollectionParticipant::MEMBER
              it_redirects_to_with_notice(root_path,
                                          "You are now a member of #{collection.title}.")
            end
          end

          context "where the user is not currently invited" do
            it "redirects to the index and displays a notice that the user has already joined or applied" do
              get :join, params: { collection_id: collection.name }
              it_redirects_to_with_notice(root_path,
                                          "You have already joined (or applied to) this collection.")
            end
          end
        end

        context "where user isn't a participant yet" do
          it "creates a participant for the user, redirects to index and notifies them that they have applied" do
            get :join, params: { collection_id: collection.name }
            participant = CollectionParticipant.find_by(pseud_id: user.default_pseud.id)
            expect(participant).to be_present
            expect(participant.participant_role).to eq CollectionParticipant::NONE
            it_redirects_to_with_notice(root_path,
                                        "You have applied to join #{collection.title}.")
          end
        end
      end
    end
  end

  describe "index" do
    let(:current_role) { CollectionParticipant::NONE }

    context "user is not logged in" do
      it "redirects to the index and displays an access denied message" do
        get :index, params: { collection_id: collection.name }
        it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
      end
    end

    context "user is logged in" do
      let!(:participant) do
        create(
          :collection_participant,
          collection: collection,
          pseud: user.default_pseud,
          participant_role: current_role
        )
      end
      let(:params) { { collection_id: collection.name } }

      before do
        fake_login_known_user(user)
      end

      context "where the user is not a maintainer" do
        it "redirects to the index" do
          get :index, params: params
          it_redirects_to_with_error(user_path(user), "Sorry, you don't have permission to access the page you were trying to reach.")
        end
      end

      context "where the user is a maintainer", work_search: true, bookmark_search: true do
        render_views
        let(:current_role) { CollectionParticipant::MODERATOR }

        context "where the collection has several participants" do
          let!(:users) do
            Array.new(3) do
              user = create(:user)
              create(
                :collection_participant,
                collection: collection,
                pseud: user.default_pseud
              )
              user
            end
          end

          it "displays the participants" do
            get :index, params: params
            expect(response).to have_http_status(:success)
            users.each do |user|
              expect(response.body).to include user.default_pseud.name
            end
          end
        end
      end
    end
  end

  describe "update" do
    let(:user_role) { CollectionParticipant::NONE }
    let!(:user_participant) do
      create(
        :collection_participant,
        pseud: user.default_pseud,
        collection: collection,
        participant_role: user_role
      )
    end
    let(:id_to_update) { "" }
    let(:params) do
      {
        id: id_to_update,
        collection_id: collection.name,
        collection_participant: {
          participant_role: CollectionParticipant::MEMBER
        }
      }
    end

    before do
      fake_login_known_user(user)
    end

    context "where there is no participant" do
      it "raises a RecordNotFound error" do
        expect do
          put :update, params: params
        end.to raise_exception(ActiveRecord::RecordNotFound)
      end
    end

    context "where there is a participant" do
      let(:participant) do
        create(
          :collection_participant,
          collection: user_participant.collection,
          participant_role: CollectionParticipant::NONE
        )
      end
      let(:id_to_update) { participant.id }

      context "where the user is not a collection maintainer" do
        it "redirects to the collection page and displays an error" do
          put :update, params: params
          it_redirects_to_with_error(collection_path(collection), "Sorry, you're not allowed to do that.")
        end
      end

      context "when the participant is from another collection" do
        let(:id_to_update) { create(:collection_participant).id }

        it "raises a RecordNotFound error" do
          expect do
            put :update, params: params
          end.to raise_exception(ActiveRecord::RecordNotFound)
        end
      end

      context "where the user is a collection maintainer" do
        let(:user_role) { CollectionParticipant::MODERATOR }
        context "where the participant is updated successfully" do
          it "successfully updates and redirects to collection participants" do
            put :update, params: params
            it_redirects_to_with_notice(collection_participants_path(collection), "Updated #{participant.pseud.name}.")
          end
        end

        context "where the participant is not updated successfully" do
          before do
            allow_any_instance_of(CollectionParticipant).to receive(:update).and_return(false)
          end

          it "displays an error notice and and redirects to collection participants" do
            put :update, params: params
            it_redirects_to_with_error(collection_participants_path(collection), "Couldn't update #{participant.pseud.name}.")
          end
        end
      end
    end
  end

  describe "destroy" do
    let(:pseud_name) { user.default_pseud.name }
    let(:user_participant_role) { CollectionParticipant::MEMBER }
    let!(:user_participant) do
      create(
        :collection_participant,
        pseud: user.default_pseud,
        collection: collection,
        participant_role: user_participant_role
      )
    end
    let(:params) do
      { id: user_participant.id, collection_id: collection.name }
    end

    before do
      fake_login_known_user(user)
    end

    context "where there is no participant" do
      let(:params) { { id: 0, collection_id: collection.name } }

      it "raises a RecordNotFound error" do
        expect do
          delete :destroy, params: params
        end.to raise_exception(ActiveRecord::RecordNotFound)
      end
    end

    context "where there is a participant found" do
      context "where user is not a maintainer" do
        context "where the user is destroying their own participant" do
          it "destroys the participant successfully and redirects to index" do
            delete :destroy, params: params
            it_redirects_to_with_notice(root_path, "Removed #{pseud_name} from collection.")
            expect(CollectionParticipant.find_by(pseud_id: user.default_pseud.id)).to_not be_present
          end
        end

        context "when the participant is from another collection" do
          it "raises a RecordNotFound error" do
            expect do
              put :update, params: { id: create(:collection_participant).id, collection_id: collection.name }
            end.to raise_exception(ActiveRecord::RecordNotFound)
          end
        end

        context "where the user is trying to destroy another participant" do
          let(:other_participant) do
            create(
              :collection_participant,
              collection: collection,
              participant_role: CollectionParticipant::MEMBER
            )
          end
          let(:pseud_name) { other_participant.pseud.name }
          let(:params) { { id: other_participant.id, collection_id: collection.name } }

          it "doesn't allow the destroy and redirects to the collection page" do
            delete :destroy, params: params
            it_redirects_to_with_error(collection_path(collection), "Sorry, you're not allowed to do that.")
            expect(CollectionParticipant.find(other_participant.id)).to be_present
          end
        end
      end

      context "where user is a maintainer" do
        let(:user_participant_role) { CollectionParticipant::MODERATOR }
        let(:other_participant) do
          create(
            :collection_participant,
            collection: collection,
            participant_role: CollectionParticipant::MEMBER
          )
        end
        let(:pseud_name) { other_participant.pseud.name }
        let(:params) do
          { id: other_participant.id, collection_id: collection.name }
        end

        context "where participant to be destroyed is not an owner" do
          it "destroys the participant successfully and redirects to index" do
            delete :destroy, params: params
            it_redirects_to_with_notice(root_path, "Removed #{pseud_name} from collection.")
            expect(CollectionParticipant.find_by(pseud_id: other_participant.pseud_id)).to_not be_present
          end
        end

        context "where participant to be destroyed is an owner" do
          let(:delete_participant_id) { CollectionParticipant.find_by(pseud_id: collection.owners.first.id) }
          let(:params) { { id: delete_participant_id, collection_id: collection.name } }

          context "where there are no other owners" do
            it "displays an error and redirects to the collection participants path" do
              delete :destroy, params: params
              it_redirects_to_with_error(collection_participants_path(collection), "You can't remove the only owner!")
              expect(CollectionParticipant.find(delete_participant_id.id)).to be_present
            end
          end

          context "where there are other owners" do
            let!(:pseud_name) { CollectionParticipant.find(delete_participant_id.id).pseud.name }
            let!(:other_owner) do
              create(
                :collection_participant,
                collection: collection,
                participant_role: CollectionParticipant::OWNER
              )
            end

            it "destroys the participant successfully and redirects to index" do
              delete :destroy, params: params
              it_redirects_to_with_notice(root_path, "Removed #{pseud_name} from collection.")
              expect(CollectionParticipant.where(id: delete_participant_id)).to be_empty
            end
          end
        end
      end
    end
  end

  describe "add" do
    let(:participants_to_invite) { "" }
    let!(:params) do
      {
        collection_id: collection.name,
        participants_to_invite: participants_to_invite
      }
    end
    let(:user_participant_role) { CollectionParticipant::MEMBER }
    let!(:user_participant) do
      create(
        :collection_participant,
        pseud: user.default_pseud,
        collection: collection,
        participant_role: user_participant_role
      )
    end

    before do
      fake_login_known_user(user)
    end

    context "where the user is not a maintainer" do
      it "redirects and shows an error message" do
        get :add, params: params
        it_redirects_to_with_error(user_path(user), "Sorry, you don't have permission to access the page you were trying to reach.")
      end
    end

    context "where the user is a maintainer" do
      let(:user_participant_role) { CollectionParticipant:: MODERATOR }
      let(:banned) { false }
      let(:users) { Array.new(3) { create(:user, banned: banned) } }
      let(:participants_to_invite) do
        users.map(&:default_pseud).map(&:byline).map(&:to_s).join(",")
      end

      context "where users to be added have already applied to the collection" do
        let!(:participants) do
          users.each do |user|
            create(
              :collection_participant,
              collection: collection,
              pseud: user.default_pseud,
              participant_role: CollectionParticipant::NONE
            )
          end
        end

        it "approves those users, redirects to the collection participants page and displays a notification" do
          get :add, params: params
          it_redirects_to_simple(collection_participants_path(collection))
          expect(flash[:notice]).to include "Members added:"
          users.each do |user|
            expect(flash[:notice]).to include user.default_pseud.byline
            participant = CollectionParticipant.find_by(pseud_id: user.default_pseud.id)
            expect(participant.participant_role).to eq CollectionParticipant::MEMBER
          end
        end
      end

      context "where the users to be added haven't yet applied to the collection" do
        it "creates new participants with the member role and redirects" do
          get :add, params: params
          it_redirects_to_simple(collection_participants_path(collection))
          expect(flash[:notice]).to include "New members invited:"
          users.each do |user|
            expect(flash[:notice]).to include user.default_pseud.byline
            participant = CollectionParticipant.find_by(pseud_id: user.default_pseud.id)
            expect(participant.participant_role).to eq CollectionParticipant::MEMBER
          end
        end
      end

      context "where users to be added are banned users" do
        let(:banned) { true }

        it "doesn't approve the member, displays an error and redirects" do
          get :add, params: params
          it_redirects_to_with_error(collection_participants_path(collection),
                                     "#{users.map(&:default_pseud).map(&:byline).to_sentence} is currently banned and cannot participate in challenges.")
          users.each do |user|
            expect(CollectionParticipant.where(pseud_id: user.default_pseud.id)).to be_empty
          end
        end
      end

      context "where users to be added can't be found" do
        let!(:pseud_ids) { users.map(&:default_pseud).map(&:id) }
        it "displays an error and redirects" do
          users.each do |user|
            user.default_pseud.destroy
          end

          get :add, params: params
          it_redirects_to_with_error(collection_participants_path(collection), "We couldn't find anyone new by that name to add.")
          pseud_ids.each do |pseud_id|
            expect(CollectionParticipant.where(pseud_id: pseud_id)).to be_empty
          end
        end
      end
    end
  end
end
