package ranking

import (
	"math"
	"sort"
	"strings"
	"time"
)

type Candidate struct {
	ID        string    `json:"id"`
	AuthorID  string    `json:"authorId"`
	CreatedAt time.Time `json:"createdAt"`
	Visible   bool      `json:"visible"`
	Sensitive bool      `json:"sensitive"`
	Features  Features  `json:"features"`
}

type Features struct {
	Following        bool    `json:"following"`
	MutualAffinity   float64 `json:"mutualAffinity"`
	InterestAffinity float64 `json:"interestAffinity"`
	Quality          float64 `json:"quality"`
	Conversation     float64 `json:"conversation"`
	NegativeFeedback float64 `json:"negativeFeedback"`
	ReportRisk       float64 `json:"reportRisk"`
}

type Request struct {
	Now              time.Time   `json:"now"`
	Candidates       []Candidate `json:"candidates"`
	SeenIDs          []string    `json:"seenIds"`
	BlockedAuthorIDs []string    `json:"blockedAuthorIds"`
	MutedAuthorIDs   []string    `json:"mutedAuthorIds"`
	Limit            int         `json:"limit"`
	AllowSensitive   bool        `json:"allowSensitive"`
}

type Result struct {
	ID       string   `json:"id"`
	AuthorID string   `json:"authorId"`
	Score    float64  `json:"score"`
	Reasons  []string `json:"reasons"`
}

type scored struct {
	result  Result
	created time.Time
}

const (
	maxCandidates = 200
	defaultLimit  = 20
	maxLimit      = 50
)

// Rank applies hard visibility exclusions before relevance scoring. Candidate
// retrieval and authoritative block/follow state remain owned by Testagram Backend.
func Rank(in Request) []Result {
	now := in.Now
	if now.IsZero() {
		now = time.Now().UTC()
	}
	limit := in.Limit
	if limit <= 0 {
		limit = defaultLimit
	}
	if limit > maxLimit {
		limit = maxLimit
	}
	seen := stringSet(in.SeenIDs)
	blocked := stringSet(in.BlockedAuthorIDs)
	muted := stringSet(in.MutedAuthorIDs)

	items := make([]scored, 0, min(len(in.Candidates), maxCandidates))
	for i, c := range in.Candidates {
		if i >= maxCandidates {
			break
		}
		if strings.TrimSpace(c.ID) == "" || strings.TrimSpace(c.AuthorID) == "" || !c.Visible {
			continue
		}
		if _, ok := seen[c.ID]; ok {
			continue
		}
		if _, ok := blocked[c.AuthorID]; ok {
			continue
		}
		if _, ok := muted[c.AuthorID]; ok {
			continue
		}
		if c.Sensitive && !in.AllowSensitive {
			continue
		}
		if c.Features.ReportRisk >= 0.8 {
			continue
		}

		score, reasons := scoreCandidate(c, now)
		items = append(items, scored{result: Result{ID: c.ID, AuthorID: c.AuthorID, Score: score, Reasons: reasons}, created: c.CreatedAt})
	}

	sort.SliceStable(items, func(i, j int) bool {
		if items[i].result.Score != items[j].result.Score {
			return items[i].result.Score > items[j].result.Score
		}
		if !items[i].created.Equal(items[j].created) {
			return items[i].created.After(items[j].created)
		}
		return items[i].result.ID < items[j].result.ID
	})

	// Avoid a single prolific author dominating the first page.
	const maxPerAuthor = 2
	counts := make(map[string]int)
	out := make([]Result, 0, limit)
	for _, item := range items {
		if counts[item.result.AuthorID] >= maxPerAuthor {
			continue
		}
		counts[item.result.AuthorID]++
		out = append(out, item.result)
		if len(out) == limit {
			break
		}
	}
	return out
}

func scoreCandidate(c Candidate, now time.Time) (float64, []string) {
	age := now.Sub(c.CreatedAt)
	if age < 0 {
		age = 0
	}
	// Half-life is approximately 24 hours; future timestamps cannot gain a bonus.
	recency := math.Exp(-math.Ln2 * age.Hours() / 24)
	f := c.Features
	score := 0.30*recency +
		0.18*boolFloat(f.Following) +
		0.10*clamp01(f.MutualAffinity) +
		0.18*clamp01(f.InterestAffinity) +
		0.12*clamp01(f.Quality) +
		0.07*clamp01(f.Conversation) -
		0.20*clamp01(f.NegativeFeedback) -
		0.25*clamp01(f.ReportRisk)
	score = math.Max(0, math.Min(1, score))

	reasons := make([]string, 0, 5)
	if f.Following {
		reasons = append(reasons, "from-followed-account")
	}
	if recency >= 0.5 {
		reasons = append(reasons, "recent")
	}
	if f.InterestAffinity >= 0.6 {
		reasons = append(reasons, "matches-interests")
	}
	if f.Quality >= 0.7 {
		reasons = append(reasons, "quality-signal")
	}
	if f.Conversation >= 0.6 {
		reasons = append(reasons, "constructive-conversation")
	}
	return score, reasons
}

func stringSet(values []string) map[string]struct{} {
	out := make(map[string]struct{}, len(values))
	for _, value := range values {
		if value = strings.TrimSpace(value); value != "" {
			out[value] = struct{}{}
		}
	}
	return out
}

func clamp01(v float64) float64 {
	if math.IsNaN(v) || math.IsInf(v, 0) {
		return 0
	}
	return math.Max(0, math.Min(1, v))
}

func boolFloat(v bool) float64 {
	if v {
		return 1
	}
	return 0
}
