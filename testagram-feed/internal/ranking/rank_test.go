package ranking

import (
	"testing"
	"time"
)

func TestRankFiltersUnsafeAndSeenCandidates(t *testing.T) {
	now := time.Date(2026, 11, 6, 12, 0, 0, 0, time.UTC)
	in := Request{
		Now: now,
		SeenIDs: []string{"seen"},
		BlockedAuthorIDs: []string{"blocked"},
		MutedAuthorIDs: []string{"muted"},
		Candidates: []Candidate{
			{ID:"seen", AuthorID:"a", Visible:true, CreatedAt:now},
			{ID:"blocked-post", AuthorID:"blocked", Visible:true, CreatedAt:now},
			{ID:"muted-post", AuthorID:"muted", Visible:true, CreatedAt:now},
			{ID:"hidden", AuthorID:"a", Visible:false, CreatedAt:now},
			{ID:"risky", AuthorID:"a", Visible:true, CreatedAt:now, Features:Features{ReportRisk:.9}},
			{ID:"safe", AuthorID:"b", Visible:true, CreatedAt:now, Features:Features{Quality:.8}},
		},
	}
	got := Rank(in)
	if len(got) != 1 || got[0].ID != "safe" {
		t.Fatalf("unexpected ranked results: %#v", got)
	}
}

func TestRankCapsAuthorDominanceAndIsDeterministic(t *testing.T) {
	now := time.Date(2026, 11, 6, 12, 0, 0, 0, time.UTC)
	in := Request{Now:now, Limit:10, Candidates:[]Candidate{
		{ID:"a1", AuthorID:"author-a", Visible:true, CreatedAt:now, Features:Features{Quality:1}},
		{ID:"a2", AuthorID:"author-a", Visible:true, CreatedAt:now.Add(-time.Minute), Features:Features{Quality:1}},
		{ID:"a3", AuthorID:"author-a", Visible:true, CreatedAt:now.Add(-2*time.Minute), Features:Features{Quality:1}},
		{ID:"b1", AuthorID:"author-b", Visible:true, CreatedAt:now, Features:Features{Quality:1}},
	}}
	first, second := Rank(in), Rank(in)
	if len(first) != 3 { t.Fatalf("expected author diversity cap, got %d", len(first)) }
	if first[0].ID != second[0].ID || first[1].ID != second[1].ID { t.Fatal("ranking must be deterministic") }
	for _, item := range first { if item.AuthorID == "author-a" { } }
}

func TestRankClampsInvalidFeatures(t *testing.T) {
	now := time.Now().UTC()
	got := Rank(Request{Now:now, Candidates:[]Candidate{
		{ID:"x", AuthorID:"u", Visible:true, CreatedAt:now, Features:Features{Quality:1000, InterestAffinity:-4}},
	}})
	if len(got) != 1 || got[0].Score < 0 || got[0].Score > 1 { t.Fatalf("score out of range: %#v", got) }
}
