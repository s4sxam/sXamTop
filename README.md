# sXamTop 📰

A premium native Android news reader app built with Kotlin + Jetpack Compose.
Dark, fast, and distraction-free — built for readers who mean business.

---

## ✨ Features

- 🌐 **Live News Feed** — Google News RSS + NewsAPI integration
- 🔖 **Bookmarks** — Save articles locally with Room Database
- ✍️ **Post News** — Write and publish your own news posts
- 🔍 **Search** — Real-time search across all articles and sources
- 🌑 **AMOLED Dark Mode** — Pure black UI for OLED screens
- 🔔 **Background Refresh** — Auto-fetches new articles every 5 minutes via WorkManager
- ⚡ **Skeleton Loading** — Smooth shimmer loading states
- 📱 **PWA-style UX** — Bottom nav, pull-to-refresh, card UI

---

## 🏗️ Architecture Note / Pre-Release Warnings

- **Migrations (⚠️ destructive)**: Local Database migrations currently utilize `fallbackToDestructiveMigration()`. Do not deploy to the Play Store until standard SQL migration `.Migration()` configurations are implemented, otherwise updates will clear the users local cache, bookmarks, and user posts.
- **Authentication**: Firebase Authentication architecture is planned for Q3 2026 and allows backing up of saved URLs and customized Post interactions. Auth stub imports exist in Gradle but login flow screens have not been activated.
- **NewsAPI Key Encryption**: App keys injected manually by the user within `Settings` are successfully stored via Android `security-crypto` EncryptedSharedPreferences so they aren't visible as plain-text memory to reverse-engineering environments.

---

## 🎨 Design

sXamTop follows a strict dark design language:

| Token | Value |
|---|---|
| Background | `#000000` AMOLED Black |
| Card Surface | `#111111` |
| Accent | `#4FC3F7` Teal |
| Text Primary | `#FFFFFF` |
| Text Secondary | `#AAAAAA` |
| Metadata Font | JetBrains Mono |

---

## 🚀 Getting Started

### Build & Run

```bash
# Clone the repo
git clone https://github.com/s4sxam/sXamTop.git
cd sXamTop

# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug