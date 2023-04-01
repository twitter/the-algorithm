Given "the user {string} has blocked the user {string}" do |blocker, blocked|
  blocker = ensure_user(blocker)
  blocked = ensure_user(blocked)
  Block.create!(blocker: blocker, blocked: blocked)
end

Given "there are {int} blocked users per page" do |amount|
  allow(Block).to receive(:per_page).and_return(amount)
end

Given "the maximum number of accounts users can block is {int}" do |count| 
  allow(ArchiveConfig).to receive(:MAX_BLOCKED_USERS).and_return(count) 
end

Then "the user {string} should have a block for {string}" do |blocker, blocked|
  blocker = User.find_by(login: blocker)
  blocked = User.find_by(login: blocked)
  expect(Block.find_by(blocker: blocker, blocked: blocked)).to be_present
end

Then "the user {string} should not have a block for {string}" do |blocker, blocked|
  blocker = User.find_by(login: blocker)
  blocked = User.find_by(login: blocked)
  expect(Block.find_by(blocker: blocker, blocked: blocked)).to be_blank
end
