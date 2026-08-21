# Review Guidelines

## Purpose

Review changes for correctness, architectural consistency, maintainability, and project conventions.

A review should focus on meaningful problems introduced by the change.

Do not report stylistic preferences as issues when they do not violate project conventions or affect maintainability.

---

# Review Priorities

Review issues in the following order:

1. Correctness
2. Architectural violations
3. Behaviour and edge cases
4. Error handling
5. Concurrency and lifecycle
6. Test coverage
7. Maintainability
8. Code quality
9. Minor style issues

Do not let minor formatting issues obscure more important problems.

---

# Correctness

Verify that the implementation does what the feature requires.

Check for:

* incorrect business logic,
* incorrect state transitions,
* incorrect data transformations,
* missing edge cases,
* incorrect API behaviour,
* incorrect navigation behaviour,
* incorrect error handling,
* regressions in existing behaviour.

Do not assume that code is correct simply because it compiles.

---

# Architecture

Verify that the change respects the project's architectural boundaries.

Check that:

* Domain does not depend on concrete Data implementations.
* Domain does not depend on platform frameworks.
* Repository contracts remain in Domain.
* Repository implementations remain in Data.
* API implementations remain in Data.
* Infrastructure remains in Core.
* Feature-specific logic remains inside its feature.
* Platform presentation remains in the platform application.
* Shared logic does not introduce Android or iOS presentation dependencies.
* Dependencies flow through the intended abstractions.
* Existing architectural patterns are reused instead of introducing unnecessary alternatives.

If a change bypasses an architectural layer, determine whether there is a legitimate reason before reporting it.

---

# Dependency Boundaries

Pay particular attention to dependency direction.

The expected flow is:

```text
Presentation
    ↓
Use Case
    ↓
Repository Contract
    ↓
Repository Implementation
    ↓
API
    ↓
Core Infrastructure
```

Flag cases where:

```text
ViewModel → API
ViewModel → Repository
View → UseCase
Domain → API
Domain → ApiClient
Domain → Database
Feature A → Feature B internal implementation
```

unless the project explicitly defines such a dependency as part of its architecture.

---

# Shared vs Platform Code

For KMP changes, verify whether code belongs in shared logic or a platform-specific application.

Ask:

* Is this business/application logic?
* Is it reusable between Android and iOS?
* Does it depend on a platform framework?
* Does it represent presentation?
* Could it be expressed as a platform-independent abstraction?

Do not move code into `commonMain` simply to reduce duplication if doing so introduces platform-specific concerns.

Do not duplicate business logic between Android and iOS when it can reasonably live in shared logic.

---

# Feature Boundaries

When reviewing feature changes, verify that feature-specific logic remains localized.

Check for:

* unrelated logic leaking into the feature,
* direct access to another feature's implementation,
* duplicated shared domain concepts,
* inappropriate additions to global `domain`,
* inappropriate additions to `core`,
* `common` being used as a generic dumping ground.

If functionality is shared by multiple features, consider whether it belongs in a macro-level shared layer.

---

# Domain Review

When reviewing Domain code, verify that it describes application behaviour rather than infrastructure details.

Check:

* domain models represent application concepts,
* use cases contain application-level orchestration,
* repository contracts describe required capabilities,
* domain code does not know how data is retrieved,
* API/serialization/database details do not leak into Domain.

Domain code should remain usable independently of Android, iOS, HTTP, and database implementations.

---

# Data Review

When reviewing Data code, verify that it correctly translates external representations into domain representations.

Check:

* API models are kept separate from domain models,
* repository implementations map data models to domain models,
* API implementations are responsible for data retrieval,
* mapping logic is deterministic and easy to reason about,
* external API changes do not unnecessarily leak into Domain or Presentation.

Avoid putting business workflows inside API implementations.

---

# Mapping Review

Pay particular attention to mapper correctness.

Check:

* all required fields are mapped,
* nullable values are handled correctly,
* enums and types are mapped safely,
* date/time conversions are correct,
* identifiers are not accidentally changed,
* default values do not hide invalid data,
* nested objects are mapped correctly.

A mapper should not silently discard meaningful information unless this is intentional.

---

# State and Presentation Review

For presentation changes, verify that UI state represents the states the screen can actually be in.

Check for:

* missing loading states,
* missing failure states,
* invalid state transitions,
* stale state,
* duplicated state,
* unnecessary state stored in the ViewModel,
* UI logic leaking into shared business logic.

Presentation should react to state rather than reconstructing business state independently.

---

# Event Handling

For event-driven presentation, verify that user interactions are represented consistently.

Check that:

* UI emits events,
* ViewModel handles events,
* event handling triggers the appropriate application operation,
* business logic is not implemented directly inside the View,
* events do not contain unnecessary implementation details.

Avoid creating events for trivial internal UI implementation details unless they need to cross the View boundary.

---

# Navigation Review

Verify that navigation respects the project's navigation abstraction.

Check that:

* feature routes remain associated with their feature,
* ViewModels do not become coupled to concrete navigation controllers,
* navigation requests use the project's navigation abstraction,
* navigation behaviour remains testable,
* platform navigation details stay outside shared business logic.

Avoid introducing a second navigation mechanism when the existing navigation architecture already supports the required behaviour.

---

# Dependency Injection Review

Check that dependencies are provided through DI rather than manually constructed inside consumers.

Look for:

* manually instantiated repositories,
* manually instantiated APIs,
* manually instantiated use cases,
* duplicated dependency graphs,
* incorrect scopes,
* missing registrations,
* feature modules that are not included in the application graph.

When adding a new implementation, verify that its abstraction-to-implementation binding is registered.

---

# Error Handling

Review whether failures are represented and propagated appropriately.

Check:

* API failures,
* network failures,
* parsing failures,
* database failures,
* domain failures,
* UI-visible failures.

Avoid:

* swallowing exceptions,
* replacing meaningful errors with generic ones,
* logging and ignoring failures,
* using exceptions for normal domain control flow when the project uses explicit result types.

Follow existing project error-handling conventions.

---

# Concurrency and Lifecycle

Pay special attention to asynchronous code.

Check for:

* unnecessary coroutine scopes,
* incorrect coroutine lifecycle,
* work continuing after the owning component is destroyed,
* race conditions,
* concurrent state updates,
* unsafe shared mutable state,
* blocking operations on the main thread.

For Android presentation, ViewModel-owned asynchronous work should respect the ViewModel lifecycle.

For shared logic, platform-independent coroutine behaviour should remain lifecycle-safe for its consumer.

---

# Caching and Persistence

When reviewing caching or persistence changes, verify that the chosen storage mechanism matches the requirement.

Distinguish between:

* in-memory cache,
* persistent local storage,
* remote data,
* temporary UI state.

Do not introduce persistent storage when an in-memory cache is sufficient.

Do not introduce a complex database abstraction for a simple temporary cache.

Review:

* cache invalidation,
* stale data,
* refresh behaviour,
* concurrent access,
* failure fallback,
* memory lifetime.

---

# API and Data Source Changes

When an API or external data source changes, verify that the change does not unnecessarily propagate through the architecture.

The expected boundary is:

```text
External API
    ↓
Data Model
    ↓
Mapper
    ↓
Domain Model
```

A change to an API response should normally be isolated primarily to the Data layer.

If API-specific details appear in Domain or Presentation, investigate whether the architectural boundary has been violated.

---

# Testing

Tests should focus on behaviour and architectural boundaries.

Prioritize testing:

* Use Cases,
* ViewModels,
* non-trivial domain logic,
* important Core infrastructure,
* complex data transformations,
* important error paths.

When behaviour changes, verify that corresponding tests are added or updated.

The project's detailed testing conventions are defined in:

[`../skills/unit-testing/SKILL.md`](../skills/unit-testing/SKILL.md)

Do not demand tests for trivial code solely to increase the number of tests.

---

# Regression Review

Consider whether the change can affect existing functionality.

Look for:

* changed shared models,
* changed repository contracts,
* changed API contracts,
* changed DI registrations,
* changed navigation,
* changed shared infrastructure,
* changes affecting multiple features.

Shared code should receive more careful review than isolated feature code because a small change may affect multiple consumers.

---

# Maintainability

Prefer solutions that are consistent with the existing architecture.

Be cautious about:

* unnecessary abstractions,
* premature generalization,
* duplicate implementations,
* new patterns introduced for a single use case,
* overly generic utilities,
* unnecessary indirection.

A solution should be as simple as possible while preserving the project's architectural boundaries.

Do not introduce complexity merely because it is technically possible.

---

# Existing Patterns

Before suggesting a new abstraction or pattern, inspect the existing project.

If the project already has an established mechanism for:

* navigation,
* dependency injection,
* networking,
* error handling,
* state management,
* mapping,
* caching,

prefer extending or reusing it.

Do not introduce a competing pattern without a clear reason.

---

# Review Comments

Review comments should be:

* specific,
* actionable,
* technically justified,
* proportional to the severity of the issue.

Explain:

1. What is wrong.
2. Why it matters.
3. What direction would fix it.

Avoid comments based solely on personal preference.

Good review:

> `ShrineSecretsViewModel` directly depends on `ShrineSecretsRepository`, bypassing the use-case boundary used by the rest of the application. This couples presentation to the data layer. Expose the operation through a use case instead.

Bad review:

> I don't like this dependency. Use a use case.

---

# Severity

Use the following severity levels:

### Critical

The change introduces a serious defect, security issue, data loss, or architectural failure that makes the implementation unsafe to merge.

### High

The change is likely to cause incorrect behaviour, significant regression, or a major architectural violation.

### Medium

The change introduces a meaningful maintainability, correctness, or testing problem that should be addressed.

### Low

The change has a minor issue that is worth addressing but does not materially affect correctness.

Do not report subjective style preferences as review findings.

---

## Avoid Nitpicking and False Positives

Only report an issue when there is a concrete and demonstrable problem.

Do not create findings merely because:
- an alternative implementation exists,
- information is not preserved when it has no value to higher layers,
- code could theoretically fail without a realistic failure path,
- a different design could be preferred,
- a generic best practice is not followed without a project-specific reason.

Do not search for problems simply to increase the number of findings.

If the implementation is correct, report that no actionable issues were found.

### Evidence Requirement

Every finding must explain the concrete execution path, architectural violation, or maintainability problem that makes it an issue.

Avoid speculative findings based on words such as:
- "could"
- "might"
- "potentially"
- "should consider"

unless the relevant failure scenario is realistic and supported by the code.

### Severity

Do not inflate severity.

Use:
- Critical only for severe correctness, security, data-loss, or system-breaking issues.
- High for significant bugs or architectural violations.
- Medium for meaningful but non-critical problems.
- Low for minor actionable issues.

Do not report an issue solely because information is discarded. First determine whether that information has semantic value to the application.

# Final Review Checklist

Before approving a change, verify:

* [ ] Behaviour is correct.
* [ ] Architectural boundaries are preserved.
* [ ] Dependency direction is correct.
* [ ] Shared and platform-specific responsibilities are separated.
* [ ] Feature boundaries are respected.
* [ ] Error paths are handled.
* [ ] Asynchronous work is lifecycle-safe.
* [ ] Data is mapped correctly between layers.
* [ ] DI registrations are complete.
* [ ] Navigation follows the existing architecture.
* [ ] Relevant tests exist or were updated.
* [ ] No unnecessary abstraction or complexity was introduced.
* [ ] Existing project patterns were reused where appropriate.
