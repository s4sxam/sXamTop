# sXamTop
📰 A premium AMOLED-dark native Android news reader built with Kotlin + Jetpack Compose — live RSS, bookmarks, user posts &amp; background refresh.
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

No images in cards. Initials-based avatars only. Clean, fast, minimal.

---

## 🏗️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material3 |
| Architecture | MVVM + StateFlow |
| Networking | Retrofit2 + OkHttp3 |
| Local DB | Room Database |
| Preferences | DataStore |
| Background | WorkManager |
| Image Loading | Coil (avatars only) |
| Navigation | Navigation Compose |

---

## 📁 Project Structure
app/src/main/java/com/sxam/sxamtop/
├── MainActivity.kt
├── navigation/
│   ├── AppNavigation.kt
│   └── BottomNavBar.kt
├── ui/
│   ├── home/
│   ├── search/
│   ├── bookmarks/
│   ├── post/
│   ├── settings/
│   ├── detail/
│   └── components/
├── data/
│   ├── model/
│   ├── remote/
│   ├── local/
│   └── repository/
├── worker/
└── datastore/
---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- JDK 17
- Android device / emulator running API 26+

### Build & Run

```bash
# Clone the repo
git clone https://github.com/s4sxam/sXamTop.git
cd sXamTop

# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug
Optional: NewsAPI Key
Get a free key at newsapi.org
Open the app → Settings → Sources → paste your key → Save
📦 Download APK
Every push to main triggers a GitHub Actions build.
Go to the Actions tab
Click the latest Build APK run
Download the sXamTop-debug artifact
Install on your Android phone
📸 Screens
Home
Search
Bookmarks
Live feed + category filters
Real-time article search
Saved articles offline
Post News
Settings
Detail
Write your own posts
Theme, API key, data
Full article view
🛠️ Configuration
Setting
Default
Description
Theme
Dark
Dark / Light / System
AMOLED Black
On
Pure #000000 background
NewsAPI Key
(empty)
Optional for extra sources
📄 License
MIT License

Copyright (c) 2026 s4sxam

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
Built with ♥ by s4sxam
---

### `app/src/main/play/listings/en-US/full-description.txt`
*(This is the Google Play Store description — also usable anywhere you need a plain text blurb)*
sXamTop — News, Redefined.
Stay ahead of the world with sXamTop, a lightning-fast native Android news reader
built for people who want information without the noise.
━━━━━━━━━━━━━━━━━━━━━━
LIVE NEWS, ALWAYS FRESH
━━━━━━━━━━━━━━━━━━━━━━
sXamTop pulls the latest headlines from Google News RSS and NewsAPI in real time.
News refreshes automatically in the background every 5 minutes so you never miss
a story. Pull down to refresh anytime.
━━━━━━━━━━━━━━━━━━━━━━
YOUR NEWS, YOUR WORDS
━━━━━━━━━━━━━━━━━━━━━━
Have something to say? Post your own news directly inside the app. Write a title,
description, pick a category, and publish. Your posts live alongside the headlines.
━━━━━━━━━━━━━━━━━━━━━━
CLEAN. DARK. FAST.
━━━━━━━━━━━━━━━━━━━━━━
Pure AMOLED black UI. No ads. No images cluttering your feed. Just clean cards,
sharp typography with JetBrains Mono metadata, and a teal accent that pops.
Designed to be read, not scrolled past.
━━━━━━━━━━━━━━━━━━━━━━
FEATURES
━━━━━━━━━━━━━━━━━━━━━━
• Live feed from Google News RSS
• Optional NewsAPI integration (set your own key)
• Category filters: All, Top, World, Tech, Sports, Finance, User Posts
• Real-time search across titles and sources
• Bookmark articles for offline reading
• Post and share your own news
• AMOLED black + dark + light themes
• Background auto-refresh with notifications
• Smooth shimmer skeleton loading
• Share any article in one tap
• Open original articles in browser
━━━━━━━━━━━━━━━━━━━━━━
PRIVACY
━━━━━━━━━━━━━━━━━━━━━━
sXamTop does not collect any personal data. Bookmarks and user posts are stored
entirely on your device. Your NewsAPI key never leaves your phone.
━━━━━━━━━━━━━━━━━━━━━━
OPEN SOURCE
━━━━━━━━━━━━━━━━━━━━━━
sXamTop is fully open source. Star it, fork it, build on it.
github.com/s4sxam/sXamTop
Built with Kotlin + Jetpack Compose. Minimum Android 8.0 (API 26).
