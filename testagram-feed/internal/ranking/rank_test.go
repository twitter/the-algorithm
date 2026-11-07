package ranking

import (
	"testing"
	"time"
)

func TestRankFiltersUnsafeAndSeenCandidates(t *testing.T) {
	now := time.Date(2026, 11, 6, 12, 0, 0, 0, time.UTC)
	in := Request{
		Now: now, SeenIDs: []string{"seen"},
		BlockedAuthorIDs: []string{"blocked"}, MutedAuthorIDs: []string{"muted"},
		Candidates: []Candidate{
			{ID: "seen", AuthorID: "a", Visible: true, CreatedAt: now},
			{ID: "blocked-post", AuthorID: "blocked", Visible: true, CreatedAt: now},
			{ID: "muted-post", AuthorID: "muted", Visible: true, CreatedAt: now},
			{ID: "hidden", AuthorID: "a", Visible: false, CreatedAt: now},
			{ID: "risky", AuthorID: "a", Visible: true, CreatedAt: now, Features: Features{ReportRisk: .9}},
			{ID: "safe", AuthorID: "b", Visible: true, CreatedAt: now, Features: Features{Quality: .8}},
		},
	}
	got := Rank(in)
	if len(got) != 1 || got[0].ID != "safe" {
		t.Fatalf("unexpected ranked results: %#v", got)
	}
}

func TestRankCapsAuthorDominanceAndIsDeterministic(t *testing.T) {
	now := time.Date(2026, 11, 6, 12, 0, 0, 0, time.UTC)
	in := Request{Now: now, Limit: 10, Candidates: []Candidate{
		{ID: "a1", AuthorID: "author-a", Visible: true, CreatedAt: now, Features: Features{Quality: 1}},
		{ID: "a2", AuthorID: "author-a", Visible: true, CreatedAt: now.Add(-time.Minute), Features: Features{Quality: 1}},
		{ID: "a3", AuthorID: "author-a", Visible: true, CreatedAt: now.Add(-2 * time.Minute), Features: Features{Quality: 1}},
		{ID: "b1", AuthorID: "author-b", Visible: true, CreatedAt: now, Features: Features{Quality: 1}},
	}}
	first, second := Rank(in), Rank(in)
	if len(first) != 3 {
		t.Fatalf("expected author diversity cap, got %d", len(first))
	}
	if first[0].ID != second[0].ID || first[1].ID != second[1].ID {
		t.Fatal("ranking must be deterministic")
	}
	authorACount := 0
	for _, item := range first {
		if item.AuthorID == "author-a" {
			authorACount++
		}
	}
	if authorACount != 2 {
		t.Fatalf("expected exactly two posts from author-a, got %d", authorACount)
	}
}

func TestRankClampsInvalidFeatures(t *testing.T) {
	now := time.Now().UTC()
	got := Rank(Request{Now: now, Candidates: []Candidate{
		{ID: "x", AuthorID: "u", Visible: true, CreatedAt: now, Features: Features{Quality: 1000, InterestAffinity: -4}},
	}})
	if len(got) != 1 || got[0].Score < 0 || got[0].Score > 1 {
		t.Fatalf("score out of range: %#v", got)
	}
}

func TestRankRejectsMalformedAndDuplicateCandidateIDs(t *testing.T) {
	now := time.Date(2026, 11, 6, 12, 0, 0, 0, time.UTC)
	got := Rank(Request{Now: now, Limit: 10, Candidates: []Candidate{
		{ID: "post-1", AuthorID: "author-1", Visible: true, CreatedAt: now},
		{ID: "post-1", AuthorID: "author-2", Visible: true, CreatedAt: now},
		{ID: " post-2", AuthorID: "author-2", Visible: true, CreatedAt: now},
		{ID: "post-3 ", AuthorID: "author-3", Visible: true, CreatedAt: now},
		{ID: "post-4", AuthorID: " author-4", Visible: true, CreatedAt: now},
		{ID: "post-5", AuthorID: "author-5 ", Visible: true, CreatedAt: now},
		{ID: string(make([]byte, 129)), AuthorID: "author-6", Visible: true, CreatedAt: now},
	}})
	if len(got) != 1 || got[0].ID != "post-1" || got[0].AuthorID != "author-1" {
		t.Fatalf("expected one canonical candidate, got %#v", got)
	}
}
