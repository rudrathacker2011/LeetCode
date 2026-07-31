"""
leetcode_sync.py
Pulls your recent ACCEPTED LeetCode submissions and writes them into a local
git repo, organized by problem, ready to commit/push to GitHub.

SETUP (do this once, locally — never in chat, never committed to git):
  1. Log into leetcode.com in your browser.
  2. DevTools -> Application -> Cookies -> copy:
       - LEETCODE_SESSION
       - csrftoken
  3. Set them as environment variables in your terminal session:

       export LEETCODE_SESSION="paste_here"
       export LEETCODE_CSRF_TOKEN="paste_here"

     (On Windows PowerShell: $env:LEETCODE_SESSION="...")

  4. Never put these values directly in this file or in a chat message.
     If you're automating with GitHub Actions, store them as encrypted
     repo Secrets instead, and this script will pick them up as env vars
     the same way.

  5. Rotate/replace these values whenever you suspect they were exposed
     (e.g. logging out and back in on leetcode.com issues a fresh session
     and invalidates the old one).

USAGE:
  python leetcode_sync.py --repo-dir ./leetcode-solutions --limit 20
"""

import os
import argparse
import subprocess
import requests

LEETCODE_GRAPHQL_URL = "https://leetcode.com/graphql"

EXT_MAP = {
    "python3": "py",
    "python": "py",
    "cpp": "cpp",
    "c": "c",
    "java": "java",
    "javascript": "js",
    "typescript": "ts",
    "golang": "go",
    "rust": "rs",
    "kotlin": "kt",
    "swift": "swift",
    "csharp": "cs",
    "ruby": "rb",
}


def get_headers():
    session = os.environ.get("LEETCODE_SESSION")
    csrf = os.environ.get("LEETCODE_CSRF_TOKEN")
    if not session or not csrf:
        raise SystemExit(
            "Missing credentials. Set LEETCODE_SESSION and LEETCODE_CSRF_TOKEN "
            "as environment variables before running this script."
        )
    return {
        "Cookie": f"LEETCODE_SESSION={session}; csrftoken={csrf}",
        "x-csrftoken": csrf,
        "Content-Type": "application/json",
        "Referer": "https://leetcode.com",
        "User-Agent": "Mozilla/5.0",
    }


def fetch_submission_list(headers, limit=20, offset=0):
    query = """
    query submissionList($offset: Int!, $limit: Int!) {
      submissionList(offset: $offset, limit: $limit) {
        submissions {
          id
          title
          titleSlug
          statusDisplay
          lang
          timestamp
        }
      }
    }
    """
    resp = requests.post(
        LEETCODE_GRAPHQL_URL,
        json={"query": query, "variables": {"offset": offset, "limit": limit}},
        headers=headers,
        timeout=15,
    )
    resp.raise_for_status()
    payload = resp.json()
    if "errors" in payload:
        raise RuntimeError(payload["errors"])
    return payload["data"]["submissionList"]["submissions"]


def fetch_submission_code(headers, submission_id):
    query = """
    query submissionDetails($submissionId: Int!) {
      submissionDetails(submissionId: $submissionId) {
        code
        lang {
          name
        }
      }
    }
    """
    resp = requests.post(
        LEETCODE_GRAPHQL_URL,
        json={"query": query, "variables": {"submissionId": int(submission_id)}},
        headers=headers,
        timeout=15,
    )
    resp.raise_for_status()
    payload = resp.json()
    if "errors" in payload:
        raise RuntimeError(payload["errors"])
    return payload["data"]["submissionDetails"]["code"]


def save_solution(repo_dir, title_slug, lang, code):
    ext = EXT_MAP.get(lang, "txt")
    folder = os.path.join(repo_dir, title_slug)
    os.makedirs(folder, exist_ok=True)
    filepath = os.path.join(folder, f"solution.{ext}")
    with open(filepath, "w", encoding="utf-8") as f:
        f.write(code)
    return filepath


def git_commit_and_push(repo_dir, message="Sync LeetCode solutions"):
    subprocess.run(["git", "-C", repo_dir, "add", "."], check=True)
    result = subprocess.run(
        ["git", "-C", repo_dir, "commit", "-m", message],
        capture_output=True, text=True,
    )
    if result.returncode != 0 and "nothing to commit" not in result.stdout:
        print(result.stdout, result.stderr)
        return
    subprocess.run(["git", "-C", repo_dir, "push", "origin", "main"], check=True)
    print("Pushed to GitHub.")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--repo-dir", default="./leetcode-solutions")
    parser.add_argument("--limit", type=int, default=20)
    parser.add_argument("--no-push", action="store_true", help="Only write files, skip git commit/push")
    args = parser.parse_args()

    headers = get_headers()
    submissions = fetch_submission_list(headers, limit=args.limit)
    accepted = [s for s in submissions if s["statusDisplay"] == "Accepted"]

    seen_slugs = set()
    for sub in accepted:
        slug = sub["titleSlug"]
        if slug in seen_slugs:
            continue  # keep only the most recent accepted submission per problem
        seen_slugs.add(slug)

        code = fetch_submission_code(headers, sub["id"])
        path = save_solution(args.repo_dir, slug, sub["lang"], code)
        print(f"Saved: {path}")

    if not args.no_push:
        git_commit_and_push(args.repo_dir)


if __name__ == "__main__":
    main()