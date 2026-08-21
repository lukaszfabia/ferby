# Kotlin Code Quality

## General Principles

* Prefer simple, readable, and maintainable code over clever or overly abstract solutions.
* Follow existing project conventions before introducing a new pattern.
* Keep responsibilities narrow and explicit.
* Avoid premature abstractions.
* Do not introduce abstractions solely for hypothetical future requirements.
* Keep changes scoped to the problem being solved.
* Do not refactor unrelated code unless explicitly requested.
* Prefer composition and dependency injection over hard-coded dependencies.
* Prefer immutable data and state.
* Make invalid states difficult to represent.

---

## Kotlin Style

Follow standard Kotlin conventions and Google's Kotlin coding style.

Prefer:

* `val` over `var` whenever possible.
* Expression bodies for simple functions.
* Named arguments when they improve readability.
* Trailing commas in multiline declarations and calls.
* Explicit visibility modifiers for declarations where visibility is not obvious.
* Small, focused functions.
* Early returns when they simplify control flow.

Avoid:

* Deeply nested conditionals.
* Large functions with multiple responsibilities.
* Unnecessary temporary variables.
* Reimplementing functionality already provided by the Kotlin standard library.
* Excessive use of scope functions when they make control flow harder to understand.

---

## Naming

Names should describe the responsibility of the symbol.

Prefer explicit names over generic names such as:

* `Manager`
* `Helper`
* `Utils`
* `Handler`
* `Processor`

unless the name accurately describes a well-defined responsibility.

Use consistent naming based on the domain concept.

Examples:

```kotlin
User
UserRepository
UserRepositoryImpl
GetUserUseCase
GetUserUseCaseImpl
UserApi
UserApiImpl
FirestoreUser
```

Avoid inconsistent abbreviations and unnecessary shortened names.

---

## Immutability

Prefer immutable values.

Use `val` by default:

```kotlin
val user = repository.getUser()
```

Use `var` only when mutation is required.

For observable state, keep mutable state private and expose a read-only representation:

```kotlin
private val _state = MutableStateFlow<State>(State.Loading)
val state = _state.asStateFlow()
```

Do not expose mutable collections or mutable reactive state unnecessarily.

Prefer immutable collections and immutable data structures where practical.

---

## Data Classes

Use data classes for value-like models.

Keep their responsibilities focused on representing data.

Do not put business workflows or infrastructure concerns into data classes.

Small derived properties are acceptable when they represent intrinsic characteristics of the model.

For example:

```kotlin
data class User(
    val firstName: String,
    val lastName: String,
) {
    val fullName: String
        get() = "$firstName $lastName"
}
```

Avoid turning domain models into service objects.

---

## Sealed Types

Use `sealed interface` or `sealed class` when a type represents a closed set of possible states or events.

Prefer sealed types for UI state:

```kotlin
sealed interface ShrineSecretsState {
    data object Loading : ShrineSecretsState
    data class Failure(val error: FerbyError) : ShrineSecretsState
    data class Success(
        val shrineSecrets: ShrineSecrets,
    ) : ShrineSecretsState
}
```

Prefer exhaustive `when` expressions.

Do not use generic boolean flags to represent mutually exclusive states.

Avoid nullable state when a sealed type expresses the possible states more clearly.

---

## Nullability

Prefer explicit state modelling over excessive nullable values.

Avoid using `null` to represent multiple different meanings.

If `null` represents a valid domain state, it should be intentional and documented by the surrounding model.

Avoid unnecessary `!!`.

Prefer safe calls, explicit checks, or appropriate Kotlin operators.

A non-null assertion should only be used when the invariant is guaranteed and obvious from the surrounding code.

---

# Coroutines and Flows

## Coroutine Scope

Use lifecycle-aware coroutine scopes.

For ViewModels, use `viewModelScope` for operations tied to the ViewModel lifecycle:

```kotlin
viewModelScope.launch {
    // operation
}
```

Do not create unmanaged coroutine scopes for lifecycle-bound work.

Avoid `GlobalScope`.

---

## Suspend Functions

Use `suspend` for operations that perform asynchronous work.

Do not block threads while waiting for asynchronous operations.

Avoid wrapping already-suspending operations in unnecessary coroutine builders.

---

## Flow

Use `Flow` for streams of values.

Expose read-only flows:

```kotlin
private val _state = MutableStateFlow<State>(State.Loading)
val state = _state.asStateFlow()
```

Do not expose `MutableStateFlow` outside the class that owns the state.

Prefer operators such as `map`, `combine`, `filter`, and `distinctUntilChanged` over manually managing derived state when they make the implementation clearer.

Avoid unnecessary `collect` calls when a declarative transformation is sufficient.

---

# Error Handling

Use the project's existing result/error abstractions consistently.

Do not introduce a new error-handling mechanism for an isolated feature.

Do not silently swallow errors.

Avoid broad exception handling:

```kotlin
catch (e: Exception) {
    // ignore
}
```

Catch exceptions only when the code can meaningfully handle them.

Preserve useful error information when mapping or propagating failures.

---

# ViewModel

ViewModels should coordinate presentation behaviour, not contain business logic that belongs to the domain layer.

Typical responsibilities include:

* invoking use cases,
* managing presentation state,
* handling UI events,
* triggering navigation,
* translating domain results into presentation state.

Dependencies should be provided through the constructor:

```kotlin
class ShrineSecretsViewModel(
    private val getCurrentShrineSecretsUseCase: GetCurrentShrineSecretsUseCase,
    private val navigation: NavigationDelegate,
) : ViewModel()
```

Do not resolve dependencies manually inside the ViewModel.

Do not inject `NavController` directly into the ViewModel.

Use the project's navigation abstraction.

---

# UI State

Represent mutually exclusive UI states explicitly.

A typical state structure is:

```text
Loading
Failure
Success
```

The state should contain everything required by the UI to render the current state.

Do not make the View independently fetch data or determine business state.

The View should render the state supplied by the ViewModel.

---

# Events

Represent user interactions as feature-specific events.

Use a sealed interface when a feature has multiple event types:

```kotlin
sealed interface ShrineSecretsEvent {
    data object OnButtonClick : ShrineSecretsEvent
}
```

Expose a single event entry point from the ViewModel:

```kotlin
fun handleEvent(event: ShrineSecretsEvent) {
    when (event) {
        ShrineSecretsEvent.OnButtonClick -> onButtonClick()
    }
}
```

Keep event-specific implementation in private methods when appropriate.

Do not put business logic inside the event declaration itself.

---

# Event Handlers

Use a typealias for feature event handlers when appropriate:

```kotlin
typealias ShrineSecretsEventHandler = (ShrineSecretsEvent) -> Unit
```

The UI should emit events through the event handler rather than directly accessing the ViewModel.

Example:

```kotlin
ShrineSecretsView(
    state = state,
    eventHandler = viewModel::handleEvent,
)
```

This keeps the UI independent from the ViewModel implementation.

---

# Compose

Composable functions should primarily render state and emit user interactions.

Avoid putting business logic, repository calls, or use-case execution directly inside composables.

Prefer:

```kotlin
when (state) {
    is State.Loading -> LoadingView()
    is State.Failure -> FailureView(state.error)
    is State.Success -> SuccessView(state.data)
}
```

over embedding business decisions throughout the UI.

---

## Composable Decomposition

Split complex UI into smaller composables when it improves readability or isolates a meaningful UI section.

Private composables should be preferred when the component is not intended for reuse outside the file.

For example:

```kotlin
@Composable
private fun ShrineSecretsLoadingView() {
    CircularProgressIndicator()
}
```

Do not create a separate composable for every trivial expression.

---

# Dependency Injection

Use Koin for dependency injection.

Dependencies should be injected through constructors.

Prefer:

```kotlin
class ShrineSecretsViewModel(
    private val useCase: GetCurrentShrineSecretsUseCase,
    private val navigation: NavigationDelegate,
)
```

over retrieving dependencies manually inside the class.

Koin resolution should normally remain in composition/root/registration code rather than being embedded throughout application logic.

Feature-specific Koin registrations should follow the existing project convention.

---

# Functions

Functions should do one thing and should have a clear responsibility.

Prefer:

```kotlin
private fun onButtonClick() {
    viewModelScope.launch {
        navigation.navigate(StartUpRoute)
    }
}
```

over a function that handles unrelated state updates, networking, navigation, and UI concerns simultaneously.

Use expression bodies when they improve readability:

```kotlin
override suspend fun getCurrentShrineSecrets(): FerbyResult<ShrineSecrets> =
    repository.getCurrentShrineSecrets()
```

Do not force expression bodies onto complex functions where a block body is clearer.

---

# Collections

Prefer Kotlin collection operations when they improve clarity:

```kotlin
val perks = data.perks
    .map { it.toDomain() }
    .toSet()
```

Avoid manual loops when a standard collection operator expresses the intent more clearly.

Do not create unnecessary intermediate collections.

Choose the collection type according to semantics:

* `List` for ordered collections.
* `Set` for uniqueness.
* `Map` for key-value lookup.

Do not use `List` simply because it is the default collection type.

---

# Mapping

Keep transformations between data and domain models explicit.

Prefer extension functions for straightforward mappings:

```kotlin
fun ShrineSecretsDto.toDomain(): ShrineSecrets =
    ShrineSecrets(
        perks = data.perks.map { it.toDomain() }.toSet(),
        start = LocalDateTime.parse(data.start),
        end = LocalDateTime.parse(data.end),
        week = data.week,
    )
```

Mapping logic should remain focused on transformation.

Do not put repository or networking logic inside mapper functions.

Do not make domain models aware of data/DTO models.

---

# API and Repository Implementations

API implementations should focus on executing API requests.

Repository implementations should coordinate data access and map data models into domain models.

Avoid placing business rules in API implementations.

Avoid exposing data-layer models outside the data layer.

---

# Comments and KDoc

Comments should explain **why**, not restate **what** the code already clearly expresses.

Avoid:

```kotlin
// Gets the user
fun getUser()
```

Prefer documentation when the public API requires additional context.

Public Kotlin APIs should use KDoc when useful.

Follow the project's Kotlin documentation skill:

[`../skills/kotlin-documentation/SKILL.md`](../../skills/kotlin-documentation/SKILL.md)

Keep KDoc concise and focused on intent and behaviour.

Use `@property` for documented properties when appropriate.

Avoid unnecessary `@param` and `@return` tags when the description can be expressed naturally in the main KDoc text.

---

# Abstractions

Do not create an abstraction without a concrete reason.

Before introducing an interface, ask:

1. Is there more than one meaningful implementation?
2. Is the abstraction required for dependency inversion?
3. Does it define a meaningful boundary between layers?
4. Does the existing architecture already use this abstraction?

Do not introduce interfaces merely because they are theoretically testable.

Existing architectural boundaries such as repositories, APIs, and use cases should follow the project's established conventions.

---

# Duplication

Avoid unnecessary duplication, but do not create premature abstractions to eliminate small amounts of duplication.

Prefer duplication over an abstraction that makes the code harder to understand when the duplicated code has different responsibilities or is likely to evolve independently.

---

# Refactoring

When modifying existing code:

* preserve established architecture,
* avoid unrelated refactors,
* do not rename public APIs without a reason,
* do not change behaviour unintentionally,
* prefer small, reviewable changes.

If a change requires breaking an existing architectural convention, make the deviation explicit.

---

# Testing

Unit tests should primarily cover:

* ViewModels,
* Use Cases,
* important Core components,
* non-trivial business logic,
* complex transformations where appropriate.

Aim for high test coverage, with **100% coverage as the target for code that is expected to be fully unit tested**.

Coverage alone is not sufficient. Tests should verify meaningful behaviour rather than merely execute lines.

Follow the project's unit-testing skill for detailed testing conventions:

[`../skills/unit-testing/SKILL.md`](../../skills/unit-testing/SKILL.md)

---

# Quality Checklist

Before considering Kotlin code complete, verify:

* [ ] Responsibilities are clearly separated.
* [ ] Existing project conventions are followed.
* [ ] Dependencies are injected through constructors.
* [ ] Mutable state is not unnecessarily exposed.
* [ ] ViewModels do not contain domain/business logic.
* [ ] UI does not access repositories or APIs directly.
* [ ] Coroutines are lifecycle-aware.
* [ ] Errors are handled consistently.
* [ ] Data/domain transformations are explicit.
* [ ] Naming clearly communicates responsibility.
* [ ] No unnecessary abstraction was introduced.
* [ ] No unrelated refactoring was performed.
* [ ] Relevant tests have been added or updated.
* [ ] Public APIs have appropriate KDoc.
