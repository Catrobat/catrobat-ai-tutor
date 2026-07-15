# Brick reference — core categories

Every brick always has `<brickId>` (UUID) and `<commentedOut>`; the fields
column lists only what comes in addition. `UPPER_CASE` names are `<formula
category="...">` entries inside `<formulaList>`; `<lowerCase>` names are plain
child elements. "—" = base fields only. Optional object fields (`look`,
`sound`, `userVariable`, sprite refs) are omitted while null/unset.

Hardware bricks (Lego, Drone, Sumo, Phiro, Arduino, Raspi, NFC, embroidery,
plotter, laser) are in `references/bricks-hardware.md`.

## Script-wrapper bricks — never in `<brickList>`

`WhenStartedBrick`, `WhenBrick`, `WhenTouchDownBrick`, `WhenClonedBrick`,
`WhenConditionBrick`, `WhenBackgroundChangesBrick`, `WhenBounceOffBrick`,
`WhenGamepadButtonBrick`, `WhenNfcBrick`, `WhenRaspiPinChangedBrick`,
`BroadcastReceiverBrick`, `UserDefinedReceiverBrick`, `EmptyEventBrick`
are editor headers for scripts — never generate them inside a `brickList`.

## Legacy marker bricks — parse-only, never emit

`IfLogicElseBrick`, `IfLogicEndBrick`, `IfThenLogicEndBrick`, `LoopEndBrick`,
`LoopEndlessBrick`. (`ParameterizedEndBrick` appears only nested in
`ParameterizedBrick`'s `<endBrick>`.)

## Motion

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `PlaceAtBrick` | Place at — teleport to X/Y coordinates | `X_POSITION`, `Y_POSITION` |
| `SetXBrick` | Set x to — set horizontal position | `X_POSITION` |
| `SetYBrick` | Set y to — set vertical position | `Y_POSITION` |
| `ChangeXByNBrick` | Change x by — move horizontally by offset | `X_POSITION_CHANGE` |
| `ChangeYByNBrick` | Change y by — move vertically by offset | `Y_POSITION_CHANGE` |
| `GoToBrick` | Go to — jump to touch position, random position, or another sprite | `<spinnerSelection>` int: 80 = touch, 81 = random, 82 = other sprite; `<destinationSprite>` inline/reference when 82 |
| `MoveNStepsBrick` | Move N steps in the current direction | `STEPS` |
| `TurnLeftBrick` | Turn left by degrees | `TURN_LEFT_DEGREES` |
| `TurnRightBrick` | Turn right by degrees | `TURN_RIGHT_DEGREES` |
| `PointInDirectionBrick` | Point in direction (degrees, 90 = right) | `DEGREES` |
| `PointToBrick` | Point towards another sprite | `<pointedObject>` inline sprite or reference |
| `IfOnEdgeBounceBrick` | If on edge, bounce off the stage border | — |
| `SetRotationStyleBrick` | Set rotation style (left-right / all-around / don't rotate) | `<selection>` int spinner index |
| `GlideToBrick` | Glide smoothly to X/Y over a duration | `X_DESTINATION`, `Y_DESTINATION`, `DURATION_IN_SECONDS` |
| `GoNStepsBackBrick` | Go back N layers (drawing order) | `STEPS` |
| `ComeToFrontBrick` | Go to front — top drawing layer | — |
| `VibrationBrick` | Vibrate the device for N seconds | `VIBRATE_DURATION_IN_SECONDS` |
| `ArcBrick` | Go in arc — move along an arc with given radius and angle | `SIZE` (radius), `DEGREES` + `<direction>` enum (`LEFT`/`RIGHT`) |
| `GoThroughBrick` | Go through X/Y then to a target X/Y | `X_POSITION`, `Y_POSITION`, `X_DESTINATION`, `Y_DESTINATION` + `<startCoordinates>` boolean |

## Physics

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `SetPhysicsObjectTypeBrick` | Set motion type (gravity/collision behavior) | `<type>` enum: `DYNAMIC`, `FIXED`, `NONE` |
| `SetVelocityBrick` | Set velocity to X/Y steps/second | `PHYSICS_VELOCITY_X`, `PHYSICS_VELOCITY_Y` |
| `TurnLeftSpeedBrick` | Spin left at degrees/second | `PHYSICS_TURN_LEFT_SPEED` |
| `TurnRightSpeedBrick` | Spin right at degrees/second | `PHYSICS_TURN_RIGHT_SPEED` |
| `SetGravityBrick` | Set gravity for all actors/objects to X/Y | `PHYSICS_GRAVITY_X`, `PHYSICS_GRAVITY_Y` |
| `SetMassBrick` | Set mass to N kilograms | `PHYSICS_MASS` |
| `SetBounceBrick` | Set bounce factor (%) | `PHYSICS_BOUNCE_FACTOR` |
| `SetFrictionBrick` | Set friction (%) | `PHYSICS_FRICTION` |

## Looks

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `SetLookBrick` | Switch to a specific look | `<look>` reference/inline |
| `SetLookByIndexBrick` | Switch to look with number | `LOOK_INDEX` |
| `NextLookBrick` | Switch to the next look in the list | — |
| `PreviousLookBrick` | Switch to the previous look | — |
| `SetSizeToBrick` | Set size to N percent | `SIZE` |
| `ChangeSizeByNBrick` | Change size by N percent | `SIZE_CHANGE` |
| `ShowBrick` | Make the sprite visible | — |
| `HideBrick` | Make the sprite invisible | — |
| `SetTransparencyBrick` | Set transparency percentage | `TRANSPARENCY` |
| `ChangeTransparencyByNBrick` | Change transparency by offset | `TRANSPARENCY_CHANGE` |
| `SetBrightnessBrick` | Set brightness percentage | `BRIGHTNESS` |
| `ChangeBrightnessByNBrick` | Change brightness by offset | `BRIGHTNESS_CHANGE` |
| `SetColorBrick` | Set color effect (0–200) | `COLOR` |
| `ChangeColorByNBrick` | Change color effect by offset | `COLOR_CHANGE` |
| `ClearGraphicEffectBrick` | Clear all graphic effects | — |
| `SayBubbleBrick` | Say text in a speech bubble | `STRING` |
| `SayForBubbleBrick` | Say text for N seconds | `STRING`, `DURATION_IN_SECONDS` |
| `ThinkBubbleBrick` | Think text in a thought bubble | `STRING` |
| `ThinkForBubbleBrick` | Think text for N seconds | `STRING`, `DURATION_IN_SECONDS` |
| `AskBrick` | Ask a question, store written answer in a variable | `ASK_QUESTION` + `<userVariable>` |
| `PaintNewLookBrick` | Paint a new look in Pocket Paint and switch to it | `LOOK_NEW` (name) |
| `EditLookBrick` | Edit the current look in Pocket Paint | — |
| `CopyLookBrick` | Copy the current look and name the copy | `LOOK_COPY` (name) |
| `DeleteLookBrick` | Delete the current look | — |
| `CameraBrick` | Turn camera preview on/off | `<spinnerSelectionON>` boolean |
| `ChooseCameraBrick` | Use front or rear camera | `<spinnerSelectionFRONT>` boolean |
| `FlashBrick` | Turn the flashlight on/off | `<spinnerSelectionID>` int spinner index |
| `SetCameraFocusPointBrick` | Become camera focus point | `HORIZONTAL_FLEXIBILITY`, `VERTICAL_FLEXIBILITY` |
| `FadeParticleEffectBrick` | Fade the particle effect in/out | `<fadeSpinnerSelectionId>` int (no formulaList) |
| `ParticleEffectAdditivityBrick` | Set particle additivity on/off | `<fadeSpinnerSelectionId>` int (no formulaList) |
| `SetParticleColorBrick` | Set the particle effect color | `COLOR` |

## Background

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `SetBackgroundBrick` | Set background to a specific look | `<look>` reference/inline |
| `SetBackgroundByIndexBrick` | Set background to number | `BACKGROUND_INDEX` |
| `SetBackgroundAndWaitBrick` | Set background and wait for its scripts | `<look>` reference/inline |
| `SetBackgroundByIndexAndWaitBrick` | Set background to number and wait | `BACKGROUND_WAIT_INDEX` |
| `LookRequestBrick` | Get image from URL, use as current look | `LOOK_REQUEST` |
| `BackgroundRequestBrick` | Get image from URL, use as background | `BACKGROUND_REQUEST` |

## Sound

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `PlaySoundBrick` | Start a sound (non-blocking) | `<sound>` reference/inline |
| `PlaySoundAndWaitBrick` | Start a sound and wait until done | `<sound>` reference/inline |
| `PlaySoundAtBrick` | Start a sound at an offset (seconds) | `PLAY_SOUND_AT` + `<sound>` |
| `StopSoundBrick` | Stop a specific sound | `<sound>` reference/inline |
| `StopAllSoundsBrick` | Stop all playing sounds | — |
| `SetVolumeToBrick` | Set volume to N percent | `VOLUME` |
| `ChangeVolumeByNBrick` | Change volume by offset | `VOLUME_CHANGE` |
| `SpeakBrick` | Speak text via TTS (non-blocking) | `SPEAK` |
| `SpeakAndWaitBrick` | Speak text and wait until done | `SPEAK` |
| `AskSpeechBrick` | Ask, store spoken answer in a variable | `ASK_SPEECH_QUESTION` + `<userVariable>` |
| `StartListeningBrick` | Listen to voice, store words in a variable | `<userVariable>` |
| `SetListeningLanguageBrick` | Set speech-recognition language | `<languageObject>` |
| `PlayNoteForBeatsBrick` | Play a MIDI note for N beats | `NOTE_TO_PLAY`, `BEATS_TO_PLAY_NOTE` |
| `PlayDrumForBeatsBrick` | Play a drum for N beats | `PLAY_DRUM` + `<drumSelection>` enum (e.g. `SNARE_DRUM`) |
| `SetTempoBrick` | Set tempo (BPM) | `TEMPO` |
| `ChangeTempoByNBrick` | Change tempo by offset | `TEMPO_CHANGE` |
| `PauseForBeatsBrick` | Pause for N beats | `BEATS_TO_PAUSE` |
| `SetInstrumentBrick` | Choose the MIDI instrument | `<instrumentSelection>` enum (e.g. `PIANO`) |

## Control

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `WaitBrick` | Wait N seconds | `TIME_TO_WAIT_IN_SECONDS` |
| `WaitUntilBrick` | Wait until a condition is true | `IF_CONDITION` |
| `NoteBrick` | Comment, not executed | `NOTE` |
| `ForeverBrick` | Forever loop | body in `<loopBricks>` |
| `RepeatBrick` | Repeat N times | `TIMES_TO_REPEAT` — body in `<loopBricks>` |
| `RepeatUntilBrick` | Repeat until a condition is true | `REPEAT_UNTIL_CONDITION` — body in `<loopBricks>` |
| `ForVariableFromToBrick` | For values from A to B (counter variable) | `FOR_LOOP_FROM`, `FOR_LOOP_TO` + `<userVariable>` — body in `<loopBricks>` |
| `ForItemInUserListBrick` | For each value in a list | `<userDataList>` (`FOR_ITEM_IN_USERLIST_VARIABLE` + `FOR_ITEM_IN_USERLIST_LIST`) — body in `<loopBricks>` |
| `IfLogicBeginBrick` | If / else | `IF_CONDITION` — `<elseBranchBricks>` + `<ifBranchBricks>` |
| `IfThenLogicBeginBrick` | If (no else) | `IF_CONDITION` — `<ifBranchBricks>` only |
| `StopScriptBrick` | Stop this / all / other scripts | `<spinnerSelection>` int: 0 = this, 1 = all, 2 = other |
| `SceneTransitionBrick` | Continue another scene (resume) | `<sceneForTransition>` scene name |
| `SceneStartBrick` | Start another scene from the beginning | `<sceneToStart>` scene name |
| `CloneBrick` | Create clone of self or another sprite | `<objectToClone>` inline/reference; omitted = self |
| `DeleteThisCloneBrick` | Delete this clone | — |
| `ExitStageBrick` | Exit the stage | — |
| `OpenUrlBrick` | Open a URL in the browser | `OPEN_URL` |
| `ResetTimerBrick` | Reset the timer sensor to 0 | — |

## Broadcast

| Brick | What it does | Serialized fields |
|---|---|---|
| `BroadcastBrick` | Broadcast a message (non-blocking) | `<broadcastMessage>` string |
| `BroadcastWaitBrick` | Broadcast and wait for receivers | `<broadcastMessage>` string |

## Variables / Data

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `SetVariableBrick` | Set a variable to a value | `VARIABLE` + `<userVariable>` |
| `ChangeVariableBrick` | Change a variable by an offset | `VARIABLE_CHANGE` + `<userVariable>` |
| `ShowTextBrick` | Show variable on stage at X/Y | `X_POSITION`, `Y_POSITION` + `<userVariable>` |
| `ShowTextColorSizeAlignmentBrick` | Show variable with size/color/alignment | `X_POSITION`, `Y_POSITION`, `SIZE`, `COLOR` + `<alignmentSelection>` int + `<userVariable>` |
| `HideTextBrick` | Hide a shown variable | `<userVariable>` only (no formulaList) |
| `WriteVariableOnDeviceBrick` | Persist a variable on the device | `<userVariable>` |
| `ReadVariableFromDeviceBrick` | Load a persisted variable | `<userVariable>` |
| `WriteVariableToFileBrick` | Write a variable to a file | `WRITE_FILENAME` + `<userVariable>` |
| `ReadVariableFromFileBrick` | Read a variable from a file | `READ_FILENAME` + `<spinnerSelectionID>` int + `<userVariable>` |
| `WriteListOnDeviceBrick` | Persist a list on the device | `<userList>` |
| `ReadListFromDeviceBrick` | Load a persisted list | `<userList>` |
| `AddItemToUserListBrick` | Add an item to a list | `LIST_ADD_ITEM` + `<userList>` |
| `DeleteItemOfUserListBrick` | Delete the item at a position | `LIST_DELETE_ITEM` + `<userList>` |
| `ClearUserListBrick` | Delete all items from a list | `<userList>` |
| `InsertItemIntoUserListBrick` | Insert an item at a position | `INSERT_ITEM_INTO_USERLIST_VALUE`, `INSERT_ITEM_INTO_USERLIST_INDEX` + `<userList>` |
| `ReplaceItemInUserListBrick` | Replace the item at a position | `REPLACE_ITEM_IN_USERLIST_VALUE`, `REPLACE_ITEM_IN_USERLIST_INDEX` + `<userList>` |
| `StoreCSVIntoUserListBrick` | Store a CSV column into a list | `STORE_CSV_INTO_USERLIST_CSV`, `STORE_CSV_INTO_USERLIST_COLUMN` + `<userList>` |
| `WebRequestBrick` | Send a web request, store answer in a variable | `WEB_REQUEST` + `<userVariable>` |
| `UserDefinedBrick` | Call (or define) a custom brick | see `references/composite-and-userdefined.md` |

## Pen

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `PenDownBrick` | Start drawing a line while moving | — |
| `PenUpBrick` | Stop drawing | — |
| `SetPenSizeBrick` | Set pen size | `PEN_SIZE` |
| `SetPenColorBrick` | Set pen color (RGB) | `PEN_COLOR_RED`, `PEN_COLOR_GREEN`, `PEN_COLOR_BLUE` |
| `StampBrick` | Stamp the sprite's look onto the background | — |
| `ClearBackgroundBrick` | Clear pen lines and stamps | — |

## Testing / Automation

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `AssertEqualsBrick` | Assert actual equals expected | `ASSERT_EQUALS_ACTUAL`, `ASSERT_EQUALS_EXPECTED` |
| `AssertUserListsBrick` | Assert two lists are equal | `<userDataList>` (`ASSERT_LISTS_ACTUAL` + `ASSERT_LISTS_EXPECTED`) |
| `ParameterizedBrick` | Run assertions per item of selected lists | `<userLists>` + nested `<endBrick>` + `<loopBricks>` |
| `FinishStageBrick` | Finish stage / end test run | — |
| `ReportBrick` | Report a value (test output) | `REPORT_BRICK` |
| `TapAtBrick` | Simulate a single tap at X/Y | `X_POSITION`, `Y_POSITION` |
| `TapForBrick` | Simulate touching at X/Y for a duration | `X_POSITION`, `Y_POSITION`, `DURATION_IN_SECONDS` |
| `TouchAndSlideBrick` | Simulate touch-and-slide | `X_POSITION`, `Y_POSITION`, `X_POSITION_CHANGE`, `Y_POSITION_CHANGE`, `DURATION_IN_SECONDS` + `<startCoordinates>` boolean |
| `WaitTillIdleBrick` | Wait until all other scripts stopped | — |

## Misc / Legacy

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `SetTextBrick` | Legacy "set text at X/Y" overlay | `X_DESTINATION`, `Y_DESTINATION`, `STRING` |
