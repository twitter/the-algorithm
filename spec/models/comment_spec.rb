# frozen_string_literal: true

require "spec_helper"

describe Comment do

  context "with an existing comment from the same user" do
    let(:first_comment) { create(:comment) }

    let(:second_comment) do
      attributes = %w[pseud_id commentable_id commentable_type comment_content name email]
      Comment.new(first_comment.attributes.slice(*attributes))
    end

    it "should be invalid if exactly duplicated" do
      expect(second_comment.valid?).to be_falsy
      expect(second_comment.errors.keys).to include(:comment_content)
    end

    it "should not be invalid if in the process of being deleted" do
      second_comment.is_deleted = true
      expect(second_comment.valid?).to be_truthy
    end
  end

  context "with blocking" do
    let(:blocked) { create(:user) }

    let(:comment) do
      Comment.new(commentable: commentable,
                  pseud: blocked.default_pseud,
                  comment_content: "Hmm.")
    end

    before { Block.create(blocker: blocker, blocked: blocked) }

    shared_examples "creating and editing comments is allowed" do
      describe "save" do
        it "allows new comments" do
          expect(comment.save).to be_truthy
          expect(comment.errors.full_messages).to be_blank
        end
      end

      describe "update" do
        before { comment.save(validate: false) }

        it "changes the comment" do
          expect(comment.update(comment_content: "Why did you block me?")).to be_truthy
          expect(comment.errors.full_messages).to be_blank
          expect(comment.reload.comment_content).to eq("Why did you block me?")
        end
      end
    end

    shared_examples "creating and editing comments is not allowed" do |message:|
      describe "save" do
        it "doesn't allow new comments" do
          expect(comment.save).to be_falsey
          expect(comment.errors.full_messages).to include(message)
        end
      end

      describe "update" do
        before { comment.save(validate: false) }

        it "doesn't change the comment" do
          expect(comment.update(comment_content: "Why did you block me?")).to be_falsey
          expect(comment.errors.full_messages).to include(message)
          expect(comment.reload.comment_content).to eq("Hmm.")
        end
      end
    end

    shared_examples "deleting comments is allowed" do
      describe "destroy_or_mark_deleted" do
        before { comment.save(validate: false) }

        it "allows deleting comments" do
          expect(comment.destroy_or_mark_deleted).to be_truthy
          expect { comment.reload }.to raise_exception ActiveRecord::RecordNotFound
        end

        it "allows deleting comments with replies" do
          create(:comment, commentable: comment)
          expect(comment.destroy_or_mark_deleted).to be_truthy
          expect { comment.reload }.not_to raise_exception
          expect(comment.is_deleted).to be_truthy
          expect(comment.comment_content).to eq("deleted comment")
        end
      end
    end

    context "when the commenter is blocked by the work's owner" do
      let(:work) { create(:work) }
      let(:blocker) { work.users.first }

      context "when commenting directly on the work" do
        let(:commentable) { work.first_chapter }

        it_behaves_like "creating and editing comments is not allowed",
                        message: "Sorry, you have been blocked by one or more of this work's creators."
        it_behaves_like "deleting comments is allowed"
      end

      context "when replying to someone else's comment on the work" do
        let(:commentable) { create(:comment, commentable: work.first_chapter) }

        it_behaves_like "creating and editing comments is not allowed",
                        message: "Sorry, you have been blocked by one or more of this work's creators."
        it_behaves_like "deleting comments is allowed"
      end
    end

    context "when the commenter shares the work with their blocker" do
      let(:blocker) { create(:user) }
      let(:work) { create(:work, authors: [blocker.default_pseud, blocked.default_pseud]) }

      context "when commenting directly on the work" do
        let(:commentable) { work.first_chapter }

        it_behaves_like "creating and editing comments is allowed"
        it_behaves_like "deleting comments is allowed"
      end

      context "when replying to someone else's comment on the work" do
        let(:commentable) { create(:comment, commentable: work.first_chapter) }

        it_behaves_like "creating and editing comments is allowed"
        it_behaves_like "deleting comments is allowed"
      end

      context "when replying to their blocker on their shared work" do
        let(:commentable) { create(:comment, pseud: blocker.default_pseud, commentable: work.first_chapter) }

        it_behaves_like "creating and editing comments is not allowed",
                        message: "Sorry, you have been blocked by that user."
        it_behaves_like "deleting comments is allowed"
      end
    end

    context "when the commenter is blocked by the person they're replying to" do
      let(:blocker) { commentable.user }

      context "on a work" do
        let(:commentable) { create(:comment) }

        it_behaves_like "creating and editing comments is not allowed",
                        message: "Sorry, you have been blocked by that user."
        it_behaves_like "deleting comments is allowed"
      end

      context "on an admin post" do
        let(:commentable) { create(:comment, :on_admin_post) }

        it_behaves_like "creating and editing comments is not allowed",
                        message: "Sorry, you have been blocked by that user."
        it_behaves_like "deleting comments is allowed"
      end

      context "on a tag" do
        let(:commentable) { create(:comment, :on_tag) }

        it_behaves_like "creating and editing comments is allowed"
        it_behaves_like "deleting comments is allowed"
      end
    end
  end

  describe "#create" do
    context "as a tag wrangler" do
      let(:tag_wrangler) { create(:tag_wrangler) }

      shared_examples "updates last wrangling activity" do
        it "tracks last wrangling activity", :frozen do
          expect(tag_wrangler.last_wrangling_activity.updated_at).to eq(Time.now.utc)
        end
      end

      context "direct parent is a tag" do
        before { create(:comment, :on_tag, pseud: tag_wrangler.default_pseud) }

        include_examples "updates last wrangling activity"
      end

      context "ultimate parent is indirectly a tag" do
        let(:parent_comment) { create(:comment, :on_tag) }

        before { create(:comment, commentable: parent_comment, pseud: tag_wrangler.default_pseud) }

        include_examples "updates last wrangling activity"
      end

      shared_examples "does not update last wrangling activity" do
        it "does not track last wrangling activity" do
          expect(tag_wrangler.last_wrangling_activity).to be_nil
        end
      end

      context "parent is a work" do
        before { create(:comment, pseud: tag_wrangler.default_pseud) }

        include_examples "does not update last wrangling activity"
      end

      context "parent is an admin comment" do
        before { create(:comment, :on_admin_post, pseud: tag_wrangler.default_pseud) }

        include_examples "does not update last wrangling activity"
      end
    end

    context "as a non-tag wrangler" do
      let(:user) { create(:archivist) }

      context "parent is a tag" do
        before { create(:comment, :on_tag, pseud: user.default_pseud) }

        it "does not update last wrangling activity" do
          expect(user.last_wrangling_activity).to be_nil
        end
      end
    end

    context "as non-user" do
      context "parent is a tag" do
        before { create(:comment, :by_guest, :on_tag) }

        it "does not update last wrangling activity" do
          expect(LastWranglingActivity.all).to be_empty
        end
      end
    end
  end

  describe "#update" do
    context "as a tag wrangler" do
      let(:tag_wrangler) { create(:tag_wrangler) }

      context "direct parent is a tag" do
        let!(:comment) { create(:comment, :on_tag, pseud: tag_wrangler.default_pseud) }

        it "does not update last wrangling activity" do
          expect do
            comment.update(comment_content: Faker::Lorem.sentence(word_count: 25))
          end.not_to change { tag_wrangler.reload.last_wrangling_activity.updated_at }
        end
      end
    end
  end

  describe "#destroy" do
    context "as a tag wrangler" do
      let(:tag_wrangler) { create(:tag_wrangler) }

      context "direct parent is a tag" do
        let!(:comment) { create(:comment, :on_tag, pseud: tag_wrangler.default_pseud) }

        it "does not update last wrangling activity" do
          expect do
            comment.destroy
          end.not_to change { tag_wrangler.reload.last_wrangling_activity.updated_at }
        end
      end
    end
  end
end
