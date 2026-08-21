# Architecture

## Overview

Ferby is a Kotlin Multiplatform (KMP) application.

The project consists of:

* `sharedLogic` — shared, platform-independent application logic.
* `androidApp` — native Android presentation built with Jetpack Compose.
* `iosApp` — native iOS presentation built with SwiftUI.

The project follows **Clean Architecture**.

The architecture is organized on two scales:

* **Macro architecture** — shared concepts and infrastructure used across multiple features.
* **Micro architecture** — logic specific to an individual feature.

The same architectural principles apply at both scales.

---

## Project Structure

The high-level structure of the shared logic is:

```text
sharedLogic/
└── commonMain/
    ├── common/
    ├── core/
    ├── data/
    ├── di/
    ├── domain/
    └── feature/
```

Platform presentation is kept outside `sharedLogic`:

```text
androidApp/
    └── presentation

iosApp/
    └── presentation
```

The exact presentation structure is platform-specific.

---

# Macro Architecture

Macro architecture contains concepts shared between multiple features.

Examples include:

* shared domain models,
* shared domain types,
* common contracts,
* networking infrastructure,
* persistence infrastructure,
* caching infrastructure,
* platform abstractions,
* dependency injection configuration.

A concept should be placed at the macro level when it represents a genuinely shared application or infrastructure concern.

Feature-specific logic should remain inside the corresponding feature.

---

# Micro Architecture

Micro architecture contains logic belonging to a single feature.

A feature may contain its own:

```text
feature/
└── <feature>/
    ├── domain/
    └── data/
```

The feature-level `domain` and `data` layers follow the same architectural responsibilities as the corresponding macro-level layers.

For example:

```text
feature/
└── shrinesecrets/
    ├── domain/
    │   ├── model/
    │   ├── repository/
    │   └── usecase/
    └── data/
        ├── api/
        ├── model/
        └── repository/
```

Feature-specific concepts should not be promoted to global layers merely to make them accessible from other parts of the project.

---

# Domain

The domain layer contains application and business concepts.

It represents what the application does rather than how external systems provide the required data.

The domain may contain:

* domain models,
* use cases,
* repository contracts,
* domain types,
* enums and other domain-level literals.

The domain must remain independent of concrete infrastructure.

It must not depend on:

* API implementations,
* HTTP clients,
* database implementations,
* persistence models,
* API DTOs,
* platform-specific presentation frameworks.

---

## Domain Models

Domain models represent concepts meaningful to the application.

Examples include:

```text
User
Perk
Entity
ShrineSecrets
```

Domain models should represent the application's view of the data rather than the representation used by an external system.

For example:

```text
API response
    ↓
data model
    ↓
domain model
```

A domain model should not be shaped around an API response simply because the structures happen to be similar.

---

## Use Cases

Use cases represent application operations.

A use case can:

* perform a simple domain operation,
* access a repository,
* coordinate multiple repositories,
* coordinate multiple use cases,
* perform application-level business logic.

The use case layer is the primary entry point from presentation into shared application logic.

Conceptually:

```text
Presentation
     ↓
Use Case
     ↓
Domain
```

Use cases depend on abstractions rather than concrete infrastructure.

---

## Repository Contracts

Repository interfaces belong to the domain layer.

They define how the domain accesses required data without knowing where that data comes from.

For example:

```text
domain
└── repository
    └── ShrineSecretsRepository
```

The domain depends on the repository contract.

The implementation belongs to the data layer.

---

# Data

The data layer is responsible for obtaining and transforming data from external or persistence sources.

It may contain:

* API interfaces and implementations,
* repository implementations,
* data models,
* mappers,
* data-source-specific logic.

The data layer translates external representations into representations understood by the domain.

Conceptually:

```text
External Source
      ↓
Data Model
      ↓
Repository
      ↓
Domain Model
```

---

## API

Feature-specific APIs belong to the data layer.

An API abstraction describes the operations required to obtain data from an external source.

For example:

```text
data/
└── api/
    ├── ShrineSecretsApi
    └── ShrineSecretsApiImpl
```

API implementations use infrastructure provided by `core`.

API code is responsible for data retrieval.

Business logic should not be placed in API implementations.

---

## Repository Implementations

Repository implementations belong to the data layer.

They connect domain repository contracts with concrete data sources.

For example:

```text
Domain
└── ShrineSecretsRepository
            ↑
Data
└── ShrineSecretsRepositoryImpl
            ↓
     ShrineSecretsApi
```

Repositories are responsible for translating data-layer representations into domain representations.

External models must not leak through repository boundaries into the domain.

---

## Data Models

Data models represent external or persistence-specific data.

Examples include:

* API DTOs,
* database entities,
* cache representations.

They may contain details required by the underlying data source, such as serialization annotations or API-specific fields.

These details should remain inside the data layer.

---

## Mapping

Mapping between data models and domain models belongs to the data layer.

Conceptually:

```text
Data Model
    ↓
Mapper
    ↓
Domain Model
```

Mappers are responsible for representation conversion.

They should not perform network operations, access repositories, or execute application workflows.

---

# Core

`core` contains reusable technical infrastructure.

It provides capabilities required by multiple parts of the application.

Examples include:

* networking,
* HTTP clients,
* database infrastructure,
* cache infrastructure,
* remote data infrastructure,
* shared technical services.

For example:

```text
core/
└── networking/
    └── ApiClient
```

`core` should contain infrastructure, not feature-specific business logic.

A feature-specific API belongs inside the feature:

```text
feature/shrinesecrets/data/api/
```

The generic HTTP client used by that API belongs in:

```text
core/networking/
```

---

# Common

`common` contains shared abstractions and utilities that do not belong to a specific feature or infrastructure subsystem.

One important responsibility is defining contracts for platform-specific capabilities required by shared logic.

Conceptually:

```text
shared logic
      ↓
common contract
      ↓
platform-specific implementation
```

`common` should not become a general-purpose dumping ground.

If something represents reusable technical infrastructure, it should generally belong in `core`.

If something represents business logic, it should belong in `domain`.

---

# Dependency Injection

`di` is responsible for composing the application's dependency graph.

It connects abstractions to their concrete implementations.

For example:

```text
UseCase
    ↓
Repository
    ↓
RepositoryImpl
    ↓
API
    ↓
API Implementation
    ↓
Core Infrastructure
```

The concrete implementations are wired together by dependency injection rather than being constructed by the consumers themselves.

Dependency injection is an infrastructure concern and should not change the architectural responsibilities of the components being registered.

Feature-specific dependencies may be registered by feature-level DI modules and subsequently aggregated into the application's dependency graph.

---

# Dependency Direction

The main dependency direction is:

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
      ↓
External System
```

Data flows back toward presentation through the appropriate abstractions and transformations:

```text
External System
      ↓
  Data Model
      ↓
   Mapper
      ↓
 Domain Model
      ↓
   Use Case
      ↓
 Presentation
```

The important architectural rule is that **higher-level application concepts must not depend directly on lower-level implementation details**.

In particular:

```text
Domain ─X→ API implementation
Domain ─X→ Database implementation
Domain ─X→ HTTP client
Domain ─X→ Android
Domain ─X→ iOS
```

Instead, dependencies should point toward abstractions.

---

# Feature Boundaries

A feature owns the logic that is specific to that feature.

For example:

```text
feature/shrinesecrets/
├── domain/
└── data/
```

A feature may depend on:

* shared domain concepts,
* shared core infrastructure,
* shared common contracts.

A feature should not reach into the private implementation details of another feature.

If two features require the same concept, determine whether that concept is genuinely shared.

If it is, it should be moved to an appropriate macro-level location.

---

# Presentation Boundary

Presentation is deliberately **not part of `sharedLogic`**.

Android and iOS implement their presentation layers natively.

```text
                    sharedLogic
                         │
              ┌──────────┴──────────┐
              ↓                     ↓
         androidApp              iosApp
        Jetpack Compose          SwiftUI
```

Shared logic provides application behaviour and domain concepts.

Platform applications are responsible for presenting that behaviour to the user.

This separation allows Android and iOS to use platform-native UI frameworks without introducing presentation-framework dependencies into shared business logic.

---

# Platform Boundary

Platform-specific implementation belongs to the corresponding platform application.

Shared code may define an abstraction when it requires a platform capability.

The platform then provides the concrete implementation.

Conceptually:

```text
commonMain
    │
    │ abstraction
    ↓
Platform Application
    │
    │ implementation
    ↓
Android / iOS
```

The shared layer should not contain Android- or iOS-specific presentation dependencies.

---

# Architectural Example

The Shrine Secrets feature follows this flow:

```text
Android / iOS Presentation
          ↓
GetCurrentShrineSecretsUseCase
          ↓
ShrineSecretsRepository
          ↓
ShrineSecretsRepositoryImpl
          ↓
ShrineSecretsApi
          ↓
ShrineSecretsApiImpl
          ↓
ApiClient
          ↓
Remote API
```

The returned data follows the reverse transformation:

```text
Remote API
    ↓
ShrineSecretsDto
    ↓
toDomain()
    ↓
ShrineSecrets
    ↓
Use Case
    ↓
Platform Presentation
```

The same architecture applies to other features, regardless of whether their data source is a remote API, local database, cache, or another source.

---

# Architectural Rules

The following rules define the architectural boundaries of the project:

* Domain contains business and application concepts.
* Domain depends on abstractions, not concrete infrastructure.
* Repository contracts belong to Domain.
* Repository implementations belong to Data.
* API abstractions and implementations belong to Data.
* Data models remain inside Data.
* Mapping between external/data representations and domain representations belongs to Data.
* Core contains reusable technical infrastructure.
* Common contains shared abstractions and utilities that do not belong to a specific feature or infrastructure subsystem.
* DI composes the dependency graph.
* Feature-specific logic remains inside the corresponding feature.
* Shared concepts should only be promoted to macro-level architecture when they are genuinely shared.
* Presentation is implemented natively in Android and iOS.
* Shared business logic must not depend on Android or iOS presentation frameworks.
* Higher-level layers must not depend directly on lower-level implementation details.
* Platform-specific concerns must remain behind appropriate abstractions when they are required by shared logic.
