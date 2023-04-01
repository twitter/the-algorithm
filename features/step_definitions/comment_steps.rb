# GIVEN

Given /^I have the default comment notifications setup$/ do
end

Given /^I have the receive all comment notifications setup$/ do
  step %{I set my preferences to turn on copies of my own comments}
end

Given /^I have the receive no comment notifications setup$/ do
  user = User.current_user
  user.preference.comment_emails_off = true
  user.preference.kudos_emails_off = true
  user.preference.save
end

Given "a guest comment on the work {string}" do |title|
  work = Work.find_by(title: title)
  FactoryBot.create(:comment, :by_guest, commentable: work.first_chapter)
end

ParameterType(
  name: "commentable",
  regexp: /the (work|admin post|tag) "([^"]*)"/,
  type: ActsAsCommentable::Commentable,
  transformer: lambda { |type, title|
    case type
    when "work"
      Work.find_by(title: title)
    when "admin post"
      AdminPost.find_by(title: title)
    when "tag"
      Tag.find_by(name: title)
    end
  }
)

Given "a comment {string} by {string} on {commentable}" do |text, user, commentable|
  user = ensure_user(user)
  commentable = Comment.commentable_object(commentable)
  FactoryBot.create(:comment,
                    pseud: user.default_pseud,
                    commentable: commentable,
                    comment_content: text)
end

Given "a reply {string} by {string} on {commentable}" do |text, user, commentable|
  user = ensure_user(user)
  comment = commentable.comments.first
  FactoryBot.create(:comment,
                    pseud: user.default_pseud,
                    commentable: comment,
                    comment_content: text)
end

# THEN

Then /^the comment's posted date should be nowish$/ do
  nowish = Time.zone.now.strftime('%a %d %b %Y %I:%M%p')
  step %{I should see "#{nowish}" within ".posted.datetime"}
end

Then /^I should see Last Edited nowish$/ do
  nowish = Time.zone.now.strftime('%a %d %b %Y %I:%M%p')
  step "I should see \"Last Edited #{nowish}\""
end

Then /^I should see the comment form$/ do
  step %{I should see "New comment on"}
end

Then /^I should see the reply to comment form$/ do
  step %{I should see "Comment as" within ".odd"}
end

Then /^I should see Last Edited in the right timezone$/ do
  zone = Time.current.in_time_zone(Time.zone).zone
  step %{I should see "#{zone}" within ".comment .posted"}
  step %{I should see "Last Edited"}
end

# WHEN

When /^I set up the comment "([^"]*)" on the work "([^"]*)"$/ do |comment_text, work|
  work = Work.find_by(title: work)
  visit work_path(work)
  fill_in("comment[comment_content]", with: comment_text)
end

When /^I attempt to comment on "([^"]*)" with a pseud that is not mine$/ do |work|
  step %{I am logged in as "commenter"}
  step %{I set up the comment "This is a test" on the work "#{work}"}
  work_id = Work.find_by(title: work).id
  pseud_id = User.first.pseuds.first.id
  find("#comment_pseud_id_for_#{work_id}", visible: false).set(pseud_id)
  click_button "Comment"
end

When /^I attempt to update a comment on "([^"]*)" with a pseud that is not mine$/ do |work|
  step %{I am logged in as "commenter"}
  step %{I post the comment "blah blah blah" on the work "#{work}"}
  step %{I follow "Edit"}
  pseud_id = User.first.pseuds.first.id
  find(:xpath, "//input[@name='comment[pseud_id]']", visible: false).set(pseud_id)
  click_button "Update"
end

When /^I post the comment "([^"]*)" on the work "([^"]*)"$/ do |comment_text, work|
  step "I set up the comment \"#{comment_text}\" on the work \"#{work}\""
  click_button("Comment")
end

When /^I post the comment "([^"]*)" on the work "([^"]*)" as a guest(?: with email "([^"]*)")?$/ do |comment_text, work, email|
  step "I start a new session"
  step "I set up the comment \"#{comment_text}\" on the work \"#{work}\""
  fill_in("Guest name", with: "guest")
  fill_in("Guest email", with: (email || "guest@foo.com"))
  click_button "Comment"
end

When /^I edit a comment$/ do
  step %{I follow "Edit"}
  fill_in("comment[comment_content]", with: "Edited comment")
  click_button "Update"
end

# this step assumes we are on a page with a comment form
When /^I post a comment "([^"]*)"$/ do |comment_text|
  fill_in("comment[comment_content]", with: comment_text)
  click_button("Comment")
end

# this step assumes that the reply-to-comment form can be opened
When /^I reply to a comment with "([^"]*)"$/ do |comment_text|
  step %{I follow "Reply"}
  step %{I should see the reply to comment form}
  with_scope(".odd") do
    fill_in("comment[comment_content]", with: comment_text)
    click_button("Comment")
  end
end

When /^I visit the new comment page for the work "([^"]+)"$/ do |work|
  work = Work.find_by(title: work)
  visit new_work_comment_path(work, only_path: false)
end

When /^I comment on an admin post$/ do
  step "I go to the admin-posts page"
  step %{I follow "Default Admin Post"}
  step %{I fill in "comment[comment_content]" with "Excellent, my dear!"}
  step %{I press "Comment"}
end

When /^I post a spam comment$/ do
  fill_in("comment[name]", with: "spammer")
  fill_in("comment[email]", with: "spammer@example.org")
  fill_in("comment[comment_content]", with: "Buy my product! http://spam.org")
  click_button("Comment")
  step %{I should see "Comment created!"}
end

When /^I post a guest comment$/ do
  fill_in("comment[name]", with: "guest")
  fill_in("comment[email]", with: "guest@example.org")
  fill_in("comment[comment_content]", with: "This was really lovely!")
  click_button("Comment")
  step %{I should see "Comment created!"}
end

When /^all comments by "([^"]*)" are marked as spam$/ do |name|
  Comment.where(name: name).find_each(&:mark_as_spam!)
end

When /^I compose an invalid comment(?: within "([^"]*)")?$/ do |selector|
  with_scope(selector) do
    fill_in("Comment", with: "Now, we can devour the gods, together! " * 270)
  end
end

When /^I delete the comment$/ do
  step %{I follow "Delete" within ".odd"}
  step %{I follow "Yes, delete!"}
end

When /^I delete the reply comment$/ do
  step %{I follow "Delete" within ".even"}
  step %{I follow "Yes, delete!"}
end

When /^I view the latest comment$/ do
  visit comment_path(Comment.last)
end

Given(/^the moderated work "([^\"]*?)" by "([^\"]*?)"$/) do |work, user|
  step %{I am logged in as "#{user}"}
  step %{I set up the draft "#{work}"}
  check("work_moderated_commenting_enabled")
  step %{I post the work without preview}
end

Then /^comment moderation should be enabled on "([^\"]*?)"/ do |work|
  w = Work.find_by(title: work)
  assert w.moderated_commenting_enabled?
end

Then /^comment moderation should not be enabled on "([^\"]*?)"/ do |work|
  w = Work.find_by(title: work)
  assert !w.moderated_commenting_enabled?
end

Then /^the comment on "([^\"]*?)" should be marked as unreviewed/ do |work|
  w = Work.find_by(title: work)
  assert w.comments.first.unreviewed?
end

Then /^the comment on "([^\"]*?)" should not be marked as unreviewed/ do |work|
  w = Work.find_by(title: work)
  assert !w.comments.first.unreviewed?
end

When "I view {commentable} with comments" do |commentable|
  if commentable.is_a?(Tag)
    visit tag_comments_path(commentable)
  else
    visit polymorphic_path(commentable, show_comments: true)
  end
end

When /^I view the unreviewed comments page for "([^\"]*?)"/ do |work|
  w = Work.find_by(title: work)
  visit unreviewed_work_comments_path(w)
end

When /^I visit the thread for the comment on "([^\"]*?)"/ do |work|
  w = Work.find_by(title: work)
  visit comment_path(w.comments.first)
end

Then /^there should be (\d+) comments on "([^\"]*?)"/ do |num, work|
  w = Work.find_by(title: work)
  assert w.find_all_comments.count == num.to_i
end

Given /^the moderated work "([^\"]*)" by "([^\"]*)" with the approved comment "([^\"]*)" by "([^\"]*)"/ do |work, author, comment, commenter|
  step %{the moderated work "#{work}" by "#{author}"}
  step %{I am logged in as "#{commenter}"}
  step %{I post the comment "#{comment}" on the work "#{work}"}
  step %{I am logged in as "#{author}"}
  step %{I view the unreviewed comments page for "#{work}"}
  step %{I press "Approve"}
end

When /^I reload the comments on "([^\"]*?)"/ do |work|
  w = Work.find_by(title: work)
  w.find_all_comments.each { |c| c.reload }
end

When /^I post a deeply nested comment thread on "([^\"]*?)"$/ do |work|
  work = Work.find_by(title: work)
  chapter = work.chapters[0]
  user = User.current_user

  commentable = chapter

  count = ArchiveConfig.COMMENT_THREAD_MAX_DEPTH + 1

  count.times do |i|
    commentable = Comment.create(
      commentable: commentable,
      parent: chapter,
      comment_content: "This is a comment at depth #{i}.",
      pseud: user.default_pseud
    )
  end

  # As long as there's only one child comment, it'll keep displaying the child.
  # So we need two comments at the final depth:
  2.times do |i|
    ordinal = i.zero? ? "first" : "second"
    Comment.create(
      commentable: commentable,
      parent: chapter,
      comment_content: "This is the #{ordinal} hidden comment.",
      pseud: user.default_pseud
    )
  end
end

Then /^I (should|should not) see the deeply nested comments$/ do |should_or_should_not|
  step %{I #{should_or_should_not} see "This is the first hidden comment."}
  step %{I #{should_or_should_not} see "This is the second hidden comment."}
end

When /^I delete all visible comments on "([^\"]*?)"$/ do |work|
  work = Work.find_by(title: work)

  loop do
    visit work_url(work, show_comments: true)
    break unless page.has_content? "Delete"
    click_link("Delete")
    click_link("Yes, delete!") # TODO: Fix along with comment deletion.
  end
end
