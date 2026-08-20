---
name: commit-message
description: Generate a Conventional Commit message from staged changes. Use when the user asks to create or suggest a commit message.
---

# Commit Message

## Goal

Generate a clear, concise Conventional Commit message based only on the staged changes.

## Workflow

1. Run `git diff --staged`.
2. Analyze all staged changes.
3. Determine the primary purpose of the changes.
4. Select the appropriate Conventional Commit type.
5. Determine a scope when it provides useful context.
6. Write a concise commit subject.
7. Add a short bullet-point body only when the changes contain multiple meaningful parts.
8. Do not modify any files.
9. Do not run `git commit`.
10. Do not mention changes that are not present in the staged diff.
11. Always generate exactly one commit message.
12. Never generate multiple commit messages, alternatives, or separate commit suggestions.
13. Treat all staged changes as one commit, even when they contain multiple unrelated changes.
14. If the staged changes contain multiple unrelated changes, summarize them in one message using the primary purpose and a concise body.

## Conventional Commit Format

Use:

```text
<type>(<scope>): <description>

- <description>
- <description>
- <description>
```

The body is optional. Do not add it when the change can be clearly described by the subject alone.

## Commit Types

Use the following types:

* `feat` — add new user-facing functionality
* `fix` — fix incorrect or broken behavior
* `refactor` — change code structure without changing behavior
* `test` — add or modify tests
* `docs` — add or modify documentation
* `style` — formatting or code style changes without behavioral changes
* `perf` — improve performance
* `build` — change build system or dependencies
* `ci` — change CI/CD configuration
* `chore` — maintenance changes that do not fit another category

Choose the most specific applicable type.

Do not use `feat` for developer tooling, configuration, agents, skills, references, or repository maintenance.

Use `chore` for repository tooling, configuration, agents, skills, references, and maintenance changes that do not affect application behavior.

## Scope

Use a scope when it clearly identifies the affected area of the project.

Examples:

```text
feat(auth): add token refresh

fix(networking): handle HTTP errors

refactor(di): simplify Koin modules

test(player): add build validation tests

docs(networking): add KDoc documentation

chore(opencode): add code review agents
```

Do not force a scope when there is no meaningful one.

## Subject Rules

The subject must:

* be concise and specific
* describe the actual change
* start with a lowercase verb
* use imperative mood
* not end with a period
* avoid unnecessary details
* not exceed 72 characters when reasonably possible

Good:

```text
feat(builds): add build sharing

fix(perks): handle missing perk data

refactor(repository): simplify data mapping

docs(networking): add KDoc documentation

chore(opencode): add code review agents
```

Bad:

```text
feat: Made some changes

fix: Fixed a bug

feat: Added a really cool new feature for the application
```

## Body Rules

Add a body only when it provides useful additional information.

Each bullet should:

* describe a meaningful change
* be concise
* contain only information supported by the diff
* avoid repeating the subject
* summarize multiple meaningful parts of the staged changes when necessary

Example:

```text
feat(di): add Koin dependency injection

- Add Koin dependencies to the project.
- Create dependency injection modules.
- Register Koin in `MainActivity.kt`.
```

When multiple independent changes are staged, do not create separate commit messages. Use one subject describing the dominant purpose and use the body to summarize the other meaningful changes.

Example:

```text
chore(opencode): add development agents and skills

- Add architecture, code review, bug investigation, and test agents.
- Add commit message, Kotlin documentation, and unit testing skills.
- Add shared architecture and code quality references.
```

## Important Rules

* Inspect the actual staged diff before generating the message.
* Never guess the purpose of a change without evidence from the diff.
* Do not include unstaged changes.
* Do not include changes that are not present in the staged diff.
* Do not modify files.
* Do not execute `git commit`.
* Always return exactly one commit message.
* Never return multiple commit messages.
* Never return alternative commit messages.
* Never split the staged changes into separate commit messages.
* If multiple types of changes are staged, choose the type based on the primary purpose of the overall change.
* Use the commit body to summarize secondary meaningful changes when necessary.
* Do not use `feat` for OpenCode configuration, agents, skills, references, or other developer tooling.
* Prefer `chore` for OpenCode configuration, agents, skills, references, and repository tooling.
* Prefer one clear commit message over multiple alternatives.

## Output

Return only the proposed commit message.

Do not add explanations.

Do not add commentary.

Do not wrap the message in Markdown code fences.
