# Signal Viewer 📈

Signal Viewer is a modern, high-performance Android application built with **Jetpack Compose** that fetches, structures, and displays trading/market signals. It connects to a REST API to retrieve post data and processes it into trading intelligence (BUY/SELL signals) within an ultra-premium, dark-themed, glassmorphic interface.

---

## 📱 Features

- **Real-time Signal Classification**: Dynamically interprets data items into actionable trade signals (**BUY** vs. **SELL**) based on message attributes.
- **Dynamic Limit Configuration**: Interactively select the number of signals to load (10, 20, 50, or 100) using a sleek, styled dropdown.
- **Sleek Glassmorphic Design**: Curated dark palette with glowing border gradients, custom transitions, and smooth list animations.
- **Detailed Signal Breakdown**: Tap any signal row to access a dedicated details screen displaying full structural titles and body metadata.
- **State-driven Architecture**: Fully reactive UI responding to loading, error, and success states dynamically.

---

## 🛠️ Tech Stack & Libraries

- **Language**: [Kotlin](https://kotlinlang.org/) (JVM Toolchain 11)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/compose) with Material 3
- **Navigation**: Jetpack [Navigation Compose](https://developer.android.com/develop/ui/compose/navigation)
- **Dependency Injection**: [Koin](https://insert-koin.io/) (`koin-android`, `koin-compose`)
- **Networking & Parsing**: [Retrofit](https://square.github.io/retrofit/) & Gson
- **Async Execution**: [Kotlin Coroutines](https://kotlinlang.org/spec/asynchronous-programming-with-coroutines.html) & StateFlow

---

## 🏛️ Architecture & Project Structure

The project follows clean architecture principles using the **MVVM (Model-View-ViewModel)** architectural pattern.

### Directory Layout

```
SignalViewer/
│
├── app/
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           └── java/com/snehadatta/
│               ├── data/
│               │   ├── model/
│               │   │   └── Post.kt                 # Data classes (signals)
│               │   └── remote/
│               │       ├── ApiService.kt           # Retrofit network interface
│               │       └── MainRepository.kt       # Repository pattern for network requests
│               │
│               └── signalviewer/
│                   ├── MainActivity.kt             # Application Entrypoint
│                   ├── SignalViewerApp.kt          # Application Class (Koin Initialization)
│                   ├── constant/
│                   │   └── NavItem.kt              # Navigation routes and Enums
│                   ├── di/
│                   │   └── AppModule.kt            # Dependency Injection Module definitions
│                   ├── navigation/
│                   │   └── AppNavGraph.kt          # Compose Navigation Graph definition
│                   ├── ui/
│                   │   ├── presentation/
│                   │   │   ├── DetailScreen.kt     # Detail UI Composable
│                   │   │   ├── MainScreen.kt       # Dashboard UI Composable with List/Filters
│                   │   │   ├── MainViewModel.kt    # MVVM ViewModel managing UiState
│                   │   │   └── SignalRow.kt        # Card list-item UI Composable
│                   │   └── theme/
│                   │       ├── Color.kt
│                   │       ├── Theme.kt
│                   │       └── Type.kt
```

### Flow of Data & Control

1. **MainActivity** initiates the Jetpack Compose theme wrapper and loads `AppNavGraph`.
2. **Koin** injects dependencies at startup, initializing the singleton `Retrofit` builder, `ApiService`, `MainRepository`, and scoping the `MainViewModel`.
3. **MainViewModel** exposes `UiState` via a Kotlin `StateFlow`.
4. **MainScreen** collects state reactively and invokes `viewModel.fetchPosts(limit)` on user actions or initial loading.
5. **MainRepository** fetches raw data from the JSONPlaceholder REST endpoint (`/posts`), handles exceptions, and returns a wrapped `Resource` (`Success`, `Error`, or `Loading`).
6. **SignalRow** determines whether the post represents a **BUY** or **SELL** action dynamically.

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** (Ladybug / Meerkat or newer recommended)
- **Android SDK**: Compile & Target SDK 36 (Minimum SDK 24)
- **JDK**: Java Development Kit 11+

### Installation & Run

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/dattasneha/trademav-assessment.git
   cd trademav-assessment
   ```

2. **Open in Android Studio**:
   - Launch Android Studio, click **Open**, and navigate to the cloned directory `SignalViewer`.
   - Let Gradle sync completely.

3. **Build the Application**:
   - Run the following command in the terminal to build the debug APK:
     ```bash
     ./gradlew assembleDebug
     ```

4. **Launch on Emulator/Device**:
   - Connect your Android physical device (with USB debugging enabled) or start a Virtual Device (AVD).
   - Press **Run (Shift + F10)** in Android Studio.
