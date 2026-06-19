![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![iOS](https://img.shields.io/badge/iOS-000000?style=for-the-badge&logo=ios&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Swift](https://img.shields.io/badge/swift-F54A2A?style=for-the-badge&logo=swift&logoColor=white)

# Ferby

The **first application that enhances your [Dead by Daylight](https://deadbydaylight.com/) gameplay**, helping you build personalized perk sets.

## Features

- Shrine Secrets weekly promotion information inside the app with notifications
- Generating perk sets using a chat-based interface
- Perk set management
- List of all perks and characters in the game
- Marking perks as favorites, which can influence perk set generation

## Technical overview

The **Ferby** project uses [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html). We can distinguish several layers:

- **Data** – data sources, usually including:
  - API
  - models (optional)
  - mappers (optional)
  - repository implementations

- **Domain** – the core layer, defining:
  - models
  - repository contracts
  - use cases

- **Presentation (iOS / Android)** – UI layer implemented separately for iOS and Android:
  - state
  - view models
  - events (optional)
  - views

This directory structure is consistent on both micro and macro levels.

To simplify usage of use cases and other components, we use [Koin](https://insert-koin.io/).