---
name: kotlin-documentation

description: Write concise KDoc for Kotlin code following Google's Kotlin readability recommendations. Use when the user asks to add, improve, or review KDoc documentation.
---

# Kotlin Documentation

## Goal

Add concise, useful KDoc to Kotlin code following Google's Kotlin readability recommendations.

Documentation should explain the purpose and behavior of the code without repeating information that is already obvious from the implementation.

## Workflow

1. Inspect the relevant Kotlin code before writing documentation.
2. Identify public classes, interfaces, objects, data classes, properties, methods, and functions that require documentation.
3. Add KDoc only where it provides useful information.
4. For data models, describe the purpose of the model and document its properties using `@property`.
5. For methods and functions, describe what the method does or what it returns.
6. Do not add KDoc to private methods unless they contain complex or non-obvious logic.
7. Use Markdown when it improves readability.
8. Use KDoc links (`[Symbol]`) when referring to classes, interfaces, functions, properties, or other code symbols that are available and resolvable in the project.
9. Do not modify the implementation or behavior of the code.
10. Do not add unnecessary or redundant documentation.
11. Keep documentation concise. Prefer one short sentence when it fully explains the code.

## KDoc Format

### Data classes and data models

Use a short description followed by `@property` tags.

Example:

```kotlin
/**
 * Represents the result of loading a player's build.
 *
 * @property build The player's selected build.
 * @property createdAt The time when the build was created.
 */
data class PlayerBuild(
    val build: Build,
    val createdAt: Instant,
)
```

### Classes and interfaces

Use a concise description explaining the responsibility or purpose of the type.

Example:

```kotlin
/**
 * Provides access to the player's saved builds.
 */
interface BuildRepository
```

### Methods and functions

Use a simple sentence describing what the method does or what it returns.

Example:

```kotlin
/**
 * Returns all builds saved by the player.
 */
suspend fun getBuilds(): List<Build>
```

When appropriate, describe the returned value directly:

```kotlin
/**
 * Returns the player's current build.
 */
fun getCurrentBuild(): Build
```

When a method accepts parameters, describe their purpose naturally in the main KDoc description. Never use `@param`.

Example:

```kotlin
/**
 * Returns the build saved by the player with the given ID.
 */
suspend fun getBuild(id: String): Build?
```

For multiple parameters, describe them naturally when they are relevant to understanding the method.

Example:

```kotlin
/**
 * Creates a new build for the player using the provided name and perks.
 */
suspend fun createBuild(
    name: String,
    perks: List<Perk>,
): Build
```

Never use `@return`. Describe the returned value directly in the main KDoc sentence.

## KDoc Links

When referring to Kotlin classes, interfaces, functions, properties, or other project symbols, use KDoc links with `[Symbol]`.

Example:

```kotlin
/**
 * Executes an [ApiRequest] and returns the [HttpResponse].
 */
suspend fun HttpClient.execute(request: ApiRequest): HttpResponse
```

Prefer:

```kotlin
/**
 * Loads data from the [BuildRepository].
 */
```

over:

```kotlin
/**
 * Loads data from the BuildRepository.
 */
```

Use links only when the referenced symbol is actually available and resolvable in the project.

Do not use links for ordinary words, concepts, or symbols that cannot be resolved.

## Subject Rules

The KDoc subject should:

* be concise and specific
* describe the purpose or behavior of the code
* use a simple sentence
* avoid repeating the function, class, or property name unnecessarily
* describe behavior rather than implementation details
* describe return values directly when relevant
* use KDoc links for resolvable project symbols
* avoid `@return`
* avoid `@param`

Good:

```kotlin
/**
 * Returns the player's current build.
 */
```

Bad:

```kotlin
/**
 * @return The current build.
 */
```

Good:

```kotlin
/**
 * Represents a player's saved build.
 */
```

Bad:

```kotlin
/**
 * PlayerBuild is a class that represents a player's saved build.
 */
```

## Body Rules

Keep KDoc to a single sentence whenever possible.

Use additional paragraphs only when they provide information that cannot be understood from the code itself, such as important behavior, constraints, side effects, or non-obvious logic.

Example:

```kotlin
/**
 * Loads the player's build from the repository.
 *
 * If no build exists, the method returns an empty result.
 */
```

Do not document every branch of a simple `when`, `if`, or conditional expression.

Do not enumerate implementation details that are already obvious from the code.

## Important Rules

* Add KDoc to public classes, interfaces, objects, structures, methods, and functions when documentation is useful.
* For data models, provide a basic description and use `@property` for their properties.
* For methods and functions, describe what they do or what they return.
* Private methods do not require KDoc by default.
* Add KDoc to private methods only when their logic is complex or non-obvious.
* Never use `@return` under any circumstances.
* Never add `@return`, even when the function returns a value.
* Describe returned values in the main KDoc sentence instead.
* Never use `@param`.
* Use `@property` for documenting properties of data models.
* Use KDoc links (`[Symbol]`) when referring to resolvable project symbols.
* Do not use KDoc links when the referenced symbol is not available or cannot be resolved.
* Do not document private implementation details unnecessarily.
* Do not add redundant KDoc that simply repeats the code.
* Do not modify code behavior or implementation.
* Preserve existing KDoc when it is already correct and useful.
* Improve existing KDoc when it is inaccurate, incomplete, or misleading.
* Keep KDoc concise.
* Prefer a single sentence over a multi-line explanation when the behavior is obvious.
* Do not enumerate every branch of a simple conditional or `when` expression unless the behavior is non-obvious or requires additional context.
* Do not invent information that cannot be established from the code.
* Do not use `@return` even if existing documentation uses it; replace it with a description in the main KDoc body when modifying that documentation.

## Output

When asked to add KDoc, modify only the Kotlin files explicitly provided or attached to the prompt.

If the required Kotlin files are not provided or attached, ask the user to provide them.

When asked to review KDoc without making changes, report only the documentation issues found and suggest improvements.

Do not add explanations unrelated to the documentation task.
