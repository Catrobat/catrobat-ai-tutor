# Google Summer of Code 2025 – Final Report

**Contributor**: Muhammad Haris Sabil Al Karim  
**Mentor(s)**: Wolfgang Slany, Paul Spiesberger
**Organization**: International Catrobat Association  
**Project**: AI Tutor for Pocket Code Students  
**GitHub Repository**: https://github.com/Catrobat/catrobat-ai-tutor  

---

## 1. Project Overview

This project aims to develop a Kotlin Multiplatform (KMP) library that acts as an AI Tutor facilitator within the Catrobat ecosystem. Instead of embedding AI logic directly, the library bridges between the user and their preferred pre-installed AI applications (e.g., ChatGPT, Gemini, Claude) using Android Intents.

Additionally, an Android Proof of Concept (PoC) application was developed to demonstrate the core functionality of detecting AI apps, constructing prompts, and sending them to the chosen app.

This design empowers users by:
- Leveraging existing AI applications they already use.
- Providing a seamless workflow for asking coding-related questions.
- Delivering high-quality responses by focusing on prompt engineering and contextualization.

---

## 2. Goals of the Project

- Develop a KMP library that can be integrated into Catrobat’s Pocket Code app and other apps.
- Enable detection of installed AI apps on user devices.
- Provide a flexible Prompt Builder for structured AI interactions.
- Build a Proof of Concept Android app showcasing the flow.
- Explore integration into Pocket Code by the end of the project.

---

## 3. Current State

- The KMP library is functional for Android.
- The PoC app demonstrates:
    - Detecting installed AI apps.
    - Constructing prompts.
    - Sending prompts to AI apps via Intents.
    - Copy-paste fallback mechanism.
    - Tutorial/Help UI.
- Initial preparation for publishing the library to Maven Central.

---

## 4. What’s Left to Do

- Finalize Maven Central publishing workflow (CI/CD via GitHub Actions).
- Expand iOS implementations (`iosMain/`) for AI app detection and integration.
- Deep integration into Pocket Code workflows.
- Collect user feedback to improve UX of copy-paste flow.

---

## 5. Code Contributions

All work was pushed to the [Catrobat AI Tutor repository](https://github.com/Catrobat/catrobat-ai-tutor) and the [PoC AI Tutor](https://github.com/Catrobat/catrobat-ai-tutor-ktxpy-editor).  
Pull requests include:
- Add local Maven publishing setup ([PR link](https://github.com/Catrobat/catrobat-ai-tutor/pull/1)).
- AI App Intent Bridge and UI ([PR link](https://github.com/Catrobat/catrobat-ai-tutor/pull/5)).
- Integrate AI Tutor library and add AI assist button ([PR link](https://github.com/Catrobat/catrobat-ai-tutor-ktxpy-editor/pull/1))

---

## 6. Challenges & Learnings

- Pivot in architecture: Originally planned for direct API integration (Gemini), but pivoted to Intent-based approach to leverage installed AI apps.
- Prompt engineering: Learned the importance of structuring prompts for better AI responses.
- GitHub Actions: CI/CD for KMP libraries was a new experience.
- UX challenges: Designing the copy-paste flow required careful consideration to avoid user confusion.

---

## 7. Future Work

- Full Pocket Code integration as the primary consumer of the library.
- Complete Maven Central release with documentation.
- Expand iOS support.
- Community feedback cycles for usability improvements.

---

## 8. Acknowledgements

Thanks to Google Summer of Code for this incredible opportunity to contribute to open source.

Special thanks to [Paul Spiesberger](https://github.com/spipau) and the Catrobat team for their continuous support, guidance, and valuable feedback throughout the program.

---

