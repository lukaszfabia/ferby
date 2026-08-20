---
name: unit-testing

description: Write comprehensive Kotlin unit tests for the provided files following the project's existing testing conventions. Use when the user asks to add, improve, or review unit tests for ViewModels, Use Cases, or other Kotlin classes.
---

# Unit Testing

## Goal

Create comprehensive unit tests for the specified Kotlin files with the goal of achieving 100% meaningful line and branch coverage.

Tests must verify actual behavior rather than merely executing code for the sake of increasing coverage.

Prioritize correctness, meaningful assertions, relevant edge cases, and consistency with the existing test suite.

## Input

The input is the name or path of one or more Kotlin files for which tests should be created or improved.

Example:

```text
Create unit tests for:
- ShrineSecretsViewModel.kt
- GetCurrentShrineSecretsUseCaseImpl.kt
```

Only create or modify tests for the files explicitly provided by the user.

## Workflow

1. Inspect the target Kotlin file or files.

2. Inspect existing tests for the target classes and related classes to understand:

   * testing framework
   * mocking or fake implementation conventions
   * coroutine testing conventions
   * naming conventions
   * assertion style
   * test structure
   * test fixtures

3. Determine the corresponding production source path and mirror its directory and package structure in the test source set.

4. If a test file for the requested production class already exists, extend the existing test suite instead of creating a duplicate test class.

5. Identify all observable behaviors and logical branches in the target code.

6. Identify:

   * successful execution paths
   * failure paths
   * conditional branches
   * empty collections
   * null values
   * boundary values
   * invalid input
   * exceptional cases
   * coroutine-related behavior
   * state transitions
   * interactions with dependencies

7. Consider relevant edge cases based on the actual implementation and domain.

8. Before creating mocks, fakes, stubs, or fixtures, search the existing test source set for reusable implementations.

9. Create or update tests covering all meaningful branches.

10. Aim for 100% meaningful line and branch coverage of the tested production code.

11. Ensure every test contains meaningful assertions about the expected behavior.

12. Do not add tests that exist only to execute code for the purpose of increasing coverage.

13. Run the relevant test suite after implementing the tests.

14. If coverage tooling is available in the project, inspect the existing coverage configuration and run the appropriate coverage task.

15. If tests fail, determine whether the problem is in the test or the production code.

16. Fix incorrect tests without modifying production behavior.

17. Re-run the tests after every meaningful correction.

18. Inspect coverage results and identify meaningful uncovered branches.

19. Add missing tests for uncovered meaningful behavior.

20. Re-run the complete relevant test suite before finishing.

21. If required test infrastructure does not exist, create the necessary directories and supporting files in the appropriate test source set.

## Test Directory Structure

Mirror the production source directory structure in the corresponding test source set.

The path to a test file should match the path of the production file, with the source set changed from `main` to `test`.

For example:

```text
commonMain/
└── kotlin/
    └── com/
        └── lukaszfabia/
            └── ferby/
                └── feature/
                    └── shrinesecrets/
                        └── domain/
                            └── model/
                                └── ShrineSecrets.kt

commonTest/
└── kotlin/
    └── com/
        └── lukaszfabia/
            └── ferby/
                └── feature/
                    └── shrinesecrets/
                        └── domain/
                            └── model/
                                └── ShrineSecretsTest.kt
```

Keep the package declaration identical between the production file and its corresponding test file.

Do not place tests in an unrelated package or directory when a corresponding production path exists.

When a new test requires additional supporting files, create the necessary directories and files while preserving the same project structure.

For example:

```text
commonTest/
└── kotlin/
    └── com/
        └── lukaszfabia/
            └── ferby/
                └── feature/
                    └── shrinesecrets/
                        └── domain/
                            └── repository/
                                └── FakeShrineSecretsRepository.kt
```

Generate the required supporting code when necessary rather than placing unrelated test helpers in arbitrary locations.

Follow the project's existing naming and organization conventions for test support files.

## Test Structure

Follow the project's existing testing style.

When the project uses Given/When/Then, follow that structure.

Example:

```kotlin
@Test
fun invoke_onSuccess_returnsSuccess() =
    runTest {
        // Given
        repository.result = expectedResult

        // When
        val result = useCase()

        // Then
        assertEquals(expectedResult, result)
    }
```

Tests should have descriptive names following:

```text
<method>_<condition>_<expectedBehavior>
```

Examples:

```kotlin
fun invoke_onSuccess_returnsSuccess()

fun invoke_onFailure_returnsFailure()

fun init_onSuccess_setsStateToSuccess()

fun init_onFailure_setsStateToFailure()
```

Avoid:

```kotlin
fun test1()

fun successTest()

fun testViewModel()
```

## Use Case Testing

For Use Cases:

1. Test every meaningful result returned by the Use Case.

2. Test successful execution with representative valid data.

3. Test failure results for every meaningful failure path.

4. Test empty collections when supported by the domain.

5. Test boundary values when the Use Case contains boundary-dependent logic.

6. Test transformations and mappings rather than only checking that the Use Case executes.

7. Test the exact returned value when it is part of the Use Case's behavior.

8. Verify meaningful dependency interactions when they are part of the Use Case's behavior.

9. Use fakes or mocks according to the project's existing conventions.

Example:

```kotlin
@Test
fun invoke_onSuccess_returnsSuccess() =
    runTest {
        // Given
        repository.result = expectedResult

        // When
        val result = useCase()

        // Then
        assertEquals(expectedResult, result)
    }
```

## ViewModel Testing

For ViewModels:

1. Test the initial state.

2. Test every meaningful state transition.

3. Test successful Use Case results.

4. Test failure results.

5. Test loading states when applicable.

6. Test empty or missing data when it affects the state.

7. Test coroutine-based operations using the project's existing coroutine test utilities.

8. Control coroutine execution explicitly when necessary.

9. Use `advanceUntilIdle()` when asynchronous work must complete before asserting the result.

10. Verify observable state, events, and effects rather than private implementation details.

Example:

```kotlin
@Test
fun init_onSuccess_setsStateToSuccess() =
    runTest {
        // Given
        val useCase = FakeGetCurrentShrineSecretsUseCase(
            FerbyResult.Success(shrineSecrets)
        )

        // When
        val viewModel = ShrineSecretsViewModel(useCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(
            ShrineSecretsState.Success(shrineSecrets),
            viewModel.state.value
        )
    }
```

## Mocking and Test Doubles

Use the project's existing mocking and test-double conventions.

Before creating a mock, fake, stub, or test fixture:

1. Inspect the project for existing test doubles for the required dependency.

2. Reuse an existing fake, mock, stub, factory, or fixture when appropriate.

3. Prefer the project's existing mocking library if one is already configured.

4. Do not introduce a new mocking library unless explicitly requested.

5. Do not create mocks for simple value objects or dependencies that can be represented more clearly with a small fake.

6. Mock only dependencies whose behavior needs to be controlled or verified.

7. Do not mock the class under test.

8. Do not over-mock implementation details.

9. Verify dependency interactions only when they are part of the behavior being tested.

When creating a new test double, place it according to the project's existing test structure and naming conventions.

If a fake implementation is more appropriate than a mock, prefer a fake.

Example:

```kotlin
private class FakeGetCurrentShrineSecretsUseCase(
    private val result: FerbyResult<ShrineSecrets>,
) : GetCurrentShrineSecretsUseCase {
    override suspend fun invoke(): FerbyResult<ShrineSecrets> = result
}
```

## Branch Coverage

For every conditional branch, verify all meaningful execution paths.

Explicitly inspect:

* `if` / `else`
* `when` branches
* Elvis operators (`?:`)
* nullable branches
* early returns
* boolean expressions with multiple conditions
* exception handling
* collection empty/non-empty branches
* state transitions

Do not assume that line coverage means all behavior is covered.

For example, for:

```kotlin
when (status) {
    Status.Loading -> ...
    Status.Success -> ...
    Status.Error -> ...
}
```

create tests that exercise every meaningful branch.

## Edge Cases

Always consider relevant edge cases before deciding that the test suite is complete.

Depending on the code, consider:

* empty collections
* empty strings
* blank strings
* null values
* zero
* negative values
* minimum and maximum values
* duplicate values
* single-item collections
* large collections
* invalid states
* unexpected enum values where applicable
* repository failures
* network failures
* exceptions
* repeated calls
* concurrent calls
* cancellation
* coroutine failures
* state changes before and after asynchronous operations

Do not create irrelevant edge-case tests.

Only test edge cases that are possible and meaningful for the actual implementation and domain.

## Coroutine Testing

Use `runTest` for suspend functions and coroutine-based tests.

Use the project's existing test dispatcher and coroutine test utilities.

Control coroutine execution explicitly when necessary.

Use `advanceUntilIdle()` when testing asynchronous work that must complete before asserting the result.

Do not add unnecessary dispatcher manipulation to synchronous tests.

Verify loading or intermediate states when the implementation exposes them and they are observable.

## Behavior Over Implementation

Prefer assertions on observable behavior and outputs.

Do not test private implementation details.

Do not assert internal implementation choices unless they are part of the public contract.

For ViewModels, prefer asserting exposed state, events, and effects.

For Use Cases, prefer asserting returned domain results and meaningful dependency interactions.

Do not introduce tests that become coupled to implementation details without a clear behavioral reason.

## Test Data

Use realistic but minimal test data.

Prefer small fixtures that clearly communicate the behavior being tested.

Reuse existing test fixtures, factories, builders, and fake implementations when they already exist in the project.

Do not introduce large or duplicated test fixtures unnecessarily.

When testing domain models, use representative values rather than arbitrary values that make the test difficult to understand.

## Dependencies

Follow the project's existing dependency-testing conventions.

Prefer:

* existing fake implementations
* existing test fixtures
* existing factories
* existing mocking libraries

Do not introduce a new testing library when an existing project dependency can solve the problem.

Do not create unnecessary abstractions solely for testing.

## Coverage Verification

Before running coverage, inspect the project's existing coverage configuration and Gradle tasks.

Use the existing project coverage tooling whenever possible.

Do not introduce a new coverage framework or Gradle plugin unless explicitly requested.

Use the project's configured coverage exclusions and understand which files are included in the coverage report.

Do not claim 100% coverage unless coverage was actually measured.

If coverage cannot be measured because the project does not provide suitable coverage tooling, state that coverage could not be verified and ensure all identifiable meaningful branches are covered by inspection.

## Coverage Scope

100% coverage applies to meaningful production logic.

Do not add meaningless tests solely to execute:

* generated code
* trivial getters/setters
* compiler-generated methods
* framework-generated code
* unreachable defensive branches
* code excluded by the project's coverage configuration

If a branch is intentionally not testable or should be excluded, identify it rather than creating a meaningless test.

## Existing Tests

If a test file for the requested production class already exists:

* extend the existing test suite
* preserve useful existing tests
* avoid creating duplicate test classes
* avoid duplicating existing test cases
* improve existing tests when they are incomplete or fragile

Do not remove existing tests unless they are demonstrably incorrect or redundant.

## Important Rules

* Write tests only for the explicitly requested files.

* Inspect existing tests before creating new ones.

* Follow the project's existing testing conventions.

* Mirror the production source directory structure in the test source set.

* Keep test package declarations identical to the corresponding production package.

* Test behavior, not implementation details.

* Aim for 100% meaningful line and branch coverage.

* Consider relevant edge cases.

* Every test must contain meaningful assertions.

* Do not create tests solely to increase coverage.

* Do not modify production code unless explicitly requested.

* Do not weaken assertions to make tests pass.

* Do not introduce unnecessary testing dependencies.

* Do not change production behavior.

* Do not duplicate existing tests.

* Do not claim coverage that was not measured.

* Prefer extending existing test files over creating duplicate test classes.

* Preserve existing tests that are correct.

* Improve existing tests when they are incomplete, fragile, or fail to cover meaningful behavior.

* Do not test private implementation details.

* Do not add meaningless tests for unreachable or generated code.

* Reuse existing mocks, fakes, fixtures, and test utilities whenever possible.

* Do not introduce a new mocking library unless explicitly requested.

* Prefer fakes over mocks when a fake provides clearer and more maintainable tests.

* Create new test support files only when necessary.

* Place new test support files in the appropriate mirrored test directory.

## Output

When asked to add unit tests, modify or create only the test files required for the requested Kotlin files.

If additional test infrastructure is required, create the necessary directories and supporting test files while preserving the project's existing structure.

Run the relevant tests after making changes.

If coverage tooling is available, run it and inspect the resulting coverage.

If coverage is below 100%, continue improving the tests until all meaningful branches are covered or explain why a specific path cannot be covered.

When asked to review existing tests without making changes, report only:

* missing test cases
* uncovered behaviors
* relevant edge cases
* assertion problems
* unnecessary or redundant tests

Do not add explanations unrelated to the testing task.
