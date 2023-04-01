require 'spec_helper'

describe ChallengeAssignment do

  describe "a challenge assignment" do
    before do
      @assignment = create(:challenge_assignment)
      @collection = @assignment.collection
    end

    it "should initially be unposted and unfulfilled and undefaulted" do
      expect(@assignment.posted?).to be_falsey
      expect(@assignment.fulfilled?).to be_falsey
      expect(@assignment.defaulted?).to be_falsey
      expect(@collection.assignments.unstarted).to include(@assignment)
      expect(@collection.assignments.unposted).to include(@assignment)
      expect(@collection.assignments.unfulfilled).to include(@assignment)
    end

    it "should be unsent" do
      expect(@collection.assignments.sent).not_to include(@assignment)
    end

    describe "after being sent" do
      before do
        @assignment.send_out
      end
      it "should be sent" do
        expect(@collection.assignments.sent).to include(@assignment)
      end
    end

    describe "when it has an unposted creation" do
      before do
        @assignment.send_out
        @author = @assignment.offer_signup.pseud

        # Setting challenge_assignment_ids only works if the authors are saved,
        # or the current user owns the assignment in question.
        User.current_user = @author.user
        @work = create(:draft, authors: [@author], collection_names: @collection.name, challenge_assignment_ids: [@assignment.id])

        @assignment.reload
      end

      it "should be started but not posted fulfilled or defaulted" do
        expect(@assignment.started?).to be_truthy
        expect(@assignment.posted?).to be_falsey
        expect(@assignment.fulfilled?).to be_falsey
        expect(@assignment.defaulted?).to be_falsey
      end

      describe "that gets posted" do
        before do
          @work.posted = true
          @work.save
          @assignment.reload
        end

        it "should be posted and fulfilled and undefaulted" do
          # note: if this collection is moderated then fulfilled shouldn't be true
          # until the item is approved
          expect(@assignment.posted?).to be_truthy
          expect(@assignment.fulfilled?).to be_truthy
          expect(@assignment.defaulted?).to be_falsey
        end

        describe "that is destroyed" do
          before do
            @work.destroy
            @assignment.reload
          end

          it "should be unposted and unfulfilled again" do
            expect(@assignment.posted?).to be_falsey
            expect(@assignment.fulfilled?).to be_falsey
            expect(@assignment.defaulted?).to be_falsey
          end
        end

      end

    end
  end

end
