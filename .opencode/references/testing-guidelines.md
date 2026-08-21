# Testing Guidelines

## Purpose

Tests should verify application behaviour, protect architectural boundaries, and prevent regressions.

Testing should prioritize meaningful behaviour over maximizing the number of tests.

The primary testing targets are:

* Use Cases,
* ViewModels,
* non-trivial Core components,
* non-trivial domain logic,
* important data transformations.

The detailed implementation and framework-specific testing conventions are defined in:

[`../skills/unit-testing/SKILL.md`](../skills/unit-testing/SKILL.md)

---

# Testing Strategy

The project primarily uses unit tests for isolated application logic.

The preferred dependency flow for testing is:

```text
Test
 ↓
System Under Test
 ↓
Mock / Fake dependency
```

Tests should isolate the component being tested from external systems.

For example:

```text
GetCurrentShrineSecretsUseCase
        ↓
Mock ShrineSecretsRepository
```

rather than:

```text
GetCurrentShrineSecretsUseCase
        ↓
Real Repository
        ↓
Real API
```

Integration tests may be used when testing the interaction between components is itself important.

---

# Test Priorities

Prioritize tests in this order:

1. Business behaviour
2. State transitions
3. Error handling
4. Edge cases
5. Data transformations
6. Important infrastructure behaviour
7. Implementation details

Tests should describe observable behaviour rather than internal implementation.

---

# Use Case Testing

Use Cases are one of the primary testing targets.

Test:

* successful execution,
* failure propagation,
* business logic,
* orchestration of multiple dependencies,
* edge cases,
* correct interaction with dependencies.

For a simple use case that only delegates to a repository, verify that the result is correctly returned.

For a complex use case, test the actual orchestration and business rules.

Example scenarios:

```text
Given repository returns success
    → use case returns success

Given repository returns failure
    → use case returns failure

Given multiple dependencies return values
    → use case produces the expected domain result
```

Do not test private implementation details of the use case.

---

# ViewModel Testing

ViewModels are a primary testing target because they coordinate presentation behaviour.

Test:

* initial state,
* successful state transitions,
* failure state transitions,
* event handling,
* use-case invocation,
* navigation requests,
* relevant side effects.

A ViewModel test should verify behaviour from the perspective of the UI.

For example:

```text
Initial
  ↓
Loading
  ↓
UseCase succeeds
  ↓
Success
```

or:

```text
Initial
  ↓
Loading
  ↓
UseCase fails
  ↓
Failure
```

For events:

```text
UI Event
  ↓
ViewModel
  ↓
Expected action
```

Navigation should be tested through the navigation abstraction rather than a concrete platform navigation controller.

---

# State Testing

When a feature uses explicit UI states, test meaningful state transitions.

For example:

```text
Loading
Success
Failure
```

Verify that the correct state is produced for each relevant outcome.

Do not test trivial property access or generated data-class behaviour.

Focus on whether the state presented to the UI is correct.

---

# Event Testing

Test feature events when they trigger meaningful behaviour.

For example:

```text
OnButtonClick
    ↓
ViewModel
    ↓
NavigationDelegate.navigate(...)
```

Verify that the event produces the expected effect.

Do not create tests solely to verify that a `when` statement contains every enum/sealed-interface case if the behaviour itself is already covered.

---

# Navigation Testing

Navigation should be tested through its abstraction.

For example, if a ViewModel receives:

```text
OnButtonClick
```

and should navigate to:

```text
StartUpRoute
```

the test should verify that the navigation delegate receives the expected route.

Do not instantiate or test the real platform navigation controller in a ViewModel unit test.

---

# Repository Testing

Repository implementations should be tested when they contain meaningful behaviour.

Test:

* correct interaction with data sources,
* successful data mapping,
* failure propagation,
* data-source fallback,
* caching behaviour,
* relevant edge cases.

A repository that only delegates to a data source may require limited testing if its behaviour is already fully covered elsewhere.

Repository tests should verify the boundary between Data and Domain.

---

# API Testing

API implementations generally require testing when they contain meaningful request configuration or response handling.

Test:

* correct request configuration,
* response handling,
* error handling,
* relevant serialization behaviour.

Do not test an HTTP client's internal implementation in every feature.

Generic networking behaviour belongs in tests for the Core networking layer.

---

# Mapper Testing

Simple, mechanical mappers do not necessarily require exhaustive tests.

Test mappers when they contain meaningful transformation logic such as:

* nullable field handling,
* enum conversion,
* date/time conversion,
* nested object transformation,
* default values,
* filtering,
* aggregation,
* non-trivial calculations.

For example:

```text
ShrineSecretsDto
    ↓
toDomain()
    ↓
ShrineSecrets
```

should be tested when the mapping contains non-trivial transformation logic.

---

# Core Testing

Core components should be tested when they contain application-critical or non-trivial behaviour.

Examples include:

* networking infrastructure,
* caching,
* persistence,
* serialization infrastructure,
* shared services,
* retry logic,
* request execution,
* error conversion.

Because Core is shared by multiple features, regressions can have a wide impact.

---

# Error Testing

Every meaningful failure path should be considered during testing.

Test relevant failures such as:

* network errors,
* API errors,
* parsing errors,
* repository failures,
* domain failures,
* invalid input,
* missing data.

Verify that failures are propagated or transformed correctly.

Avoid tests that only verify that an exception was thrown if the application's actual contract is to return an explicit result type.

---

# Edge Cases

Tests should cover meaningful edge cases.

Depending on the component, consider:

* empty collections,
* missing optional values,
* invalid data,
* boundary dates,
* duplicate values,
* unexpected API responses,
* concurrent operations,
* repeated events,
* stale cache data.

Do not create edge-case tests that have no realistic impact on application behaviour.

---

# Mocking and Fakes

External dependencies should generally be replaced with mocks or fakes in unit tests.

Examples:

```text
Repository
API
NavigationDelegate
UseCase
Clock
Cache
```

The dependency should be controlled by the test.

Prefer the simplest test double that clearly expresses the scenario.

Use a fake when simple deterministic behaviour is easier to express with a small implementation.

Use a mock when verifying interactions is important.

Avoid mocking the System Under Test.

---

# Test Isolation

Each test should be independent.

Tests should not depend on:

* execution order,
* global mutable state,
* previous test results,
* external network availability,
* a real production database,
* the developer's local environment.

Tests should be deterministic and repeatable.

---

# Coroutine Testing

Coroutine-based code must be tested using controlled test dispatchers and scopes.

Tests should not depend on real timing or arbitrary delays.

Avoid:

```kotlin
delay(1000)
```

as a mechanism for waiting for asynchronous work to complete.

Tests should explicitly control coroutine execution.

Follow the coroutine testing conventions defined in the unit-testing skill.

---

# Flow Testing

When testing `StateFlow` or other Kotlin Flows, verify emitted behaviour rather than implementation details.

For stateful components, test meaningful sequences:

```text
Loading
→ Success
```

or:

```text
Loading
→ Failure
```

When multiple emissions matter, verify the relevant order and values.

Avoid asserting unnecessary intermediate emissions that are not part of the component's observable contract.

---

# Coverage

The project aims for **100% test coverage of meaningful application logic**.

Coverage should be treated as a signal, not as the sole definition of test quality.

A high coverage percentage does not compensate for tests that do not verify meaningful behaviour.

Prioritize coverage of:

* Use Cases,
* ViewModels,
* domain logic,
* important Core components,
* non-trivial transformations,
* error paths.

Do not add meaningless tests solely to execute trivial generated code or simple accessors.

---

# Regression Tests

When fixing a bug, add a regression test whenever practical.

The test should reproduce the problematic behaviour before the fix and verify the expected behaviour after the fix.

Preferred pattern:

```text
Bug
 ↓
Regression test reproduces bug
 ↓
Implementation fix
 ↓
Test passes
```

This prevents the same bug from returning later.

---

# Test Naming

Test names should describe the behaviour being verified.

Prefer names that communicate:

```text
given / when / then
```

or an equivalent clear structure.

For example:

```text
givenRepositoryFailure_whenGetShrineSecrets_thenReturnsFailure
```

The exact naming style should follow the conventions defined in the unit-testing skill.

Avoid names such as:

```text
test1
works
testRepository
shouldWork
```

---

# Test Structure

Tests should generally follow:

```text
Arrange
Act
Assert
```

or:

```text
Given
When
Then
```

Keep each test focused on one meaningful behaviour.

Avoid tests that verify many unrelated behaviours at once.

---

# What Not to Test

Do not write tests solely for:

* generated data-class methods,
* trivial getters/setters,
* simple constructors,
* framework behaviour,
* third-party library behaviour,
* implementation details that are not part of the component's contract.

Do not duplicate tests across layers when the same behaviour is already guaranteed by a lower-level abstraction.

---

# Testing Architectural Boundaries

Tests should help ensure that architectural boundaries remain intact.

When appropriate, verify that:

* ViewModels use Use Cases rather than repositories,
* repositories use data sources,
* Data maps external models into Domain models,
* navigation goes through the project's navigation abstraction,
* platform-specific concerns do not leak into shared business logic.

Architectural tests should be introduced when a boundary is important enough that accidental violations are likely.

---

# Test Review Checklist

Before considering a change adequately tested:

* [ ] Important behaviour is covered.
* [ ] Success paths are covered.
* [ ] Relevant failure paths are covered.
* [ ] Meaningful edge cases are covered.
* [ ] ViewModel state transitions are tested where applicable.
* [ ] Feature events are tested where they cause meaningful behaviour.
* [ ] Navigation effects are tested through the navigation abstraction.
* [ ] Use Case behaviour is tested.
* [ ] Non-trivial mapping logic is tested.
* [ ] Important Core behaviour is tested.
* [ ] Coroutines are tested deterministically.
* [ ] Tests are isolated and repeatable.
* [ ] Regression tests are added for bug fixes.
* [ ] Tests verify behaviour rather than implementation details.
* [ ] Meaningful application logic aims for 100% coverage.
