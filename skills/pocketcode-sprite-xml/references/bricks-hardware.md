# Brick reference — hardware categories

Same conventions as `bricks-core.md`: `UPPER_CASE` = formula categories in
`<formulaList>`, `<lowerCase>` = plain child elements, "—" = base fields only
(`brickId` + `commentedOut`).

These bricks appear only in projects using the corresponding hardware.

## Lego NXT

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `LegoNxtMotorMoveBrick` | Move an NXT motor at a speed | `LEGO_NXT_SPEED` + `<motor>` enum (e.g. `MOTOR_A`) |
| `LegoNxtMotorStopBrick` | Stop an NXT motor | `<motor>` enum |
| `LegoNxtMotorTurnAngleBrick` | Turn an NXT motor by an angle | `LEGO_NXT_DEGREES` + `<motor>` enum |
| `LegoNxtPlayToneBrick` | Play a tone on the NXT | `LEGO_NXT_FREQUENCY`, `LEGO_NXT_DURATION_IN_SECONDS` |

## Lego EV3

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `LegoEv3MotorMoveBrick` | Move an EV3 motor at a speed | `LEGO_EV3_SPEED` + `<motor>` enum |
| `LegoEv3MotorStopBrick` | Stop an EV3 motor | `<motor>` enum |
| `LegoEv3MotorTurnAngleBrick` | Turn an EV3 motor by an angle | `LEGO_EV3_DEGREES` + `<motor>` enum |
| `LegoEv3PlayToneBrick` | Play a tone on the EV3 | `LEGO_EV3_FREQUENCY`, `LEGO_EV3_DURATION_IN_SECONDS`, `LEGO_EV3_VOLUME` |
| `LegoEv3SetLedBrick` | Set the EV3 LED status | `<ledStatus>` enum (e.g. `LED_GREEN`) |

## AR.Drone 2.0

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `DroneTakeOffLandBrick` | Take off / land | — |
| `DroneEmergencyBrick` | Emergency stop | — |
| `DroneMoveForwardBrick`, `DroneMoveBackwardBrick`, `DroneMoveUpBrick`, `DroneMoveDownBrick`, `DroneMoveLeftBrick`, `DroneMoveRightBrick` | Move in a direction for a time at a power | each: `DRONE_TIME_TO_FLY_IN_SECONDS`, `DRONE_POWER_IN_PERCENT` |
| `DroneTurnLeftBrick`, `DroneTurnRightBrick` | Turn | `DRONE_TIME_TO_FLY_IN_SECONDS`, `DRONE_POWER_IN_PERCENT` |
| `DroneFlipBrick` | Flip | — |
| `DroneSwitchCameraBrick` | Switch between cameras | — |
| `DronePlayLedAnimationBrick` | Play a flash/LED animation | `<ledAnimationName>` string |

## Jumping Sumo

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `JumpingSumoMoveForwardBrick`, `JumpingSumoMoveBackwardBrick` | Drive for a time at a speed | each: `JUMPING_SUMO_TIME_TO_DRIVE_IN_SECONDS`, `JUMPING_SUMO_SPEED` |
| `JumpingSumoRotateLeftBrick`, `JumpingSumoRotateRightBrick` | Rotate by degrees | `JUMPING_SUMO_ROTATE` |
| `JumpingSumoJumpHighBrick`, `JumpingSumoJumpLongBrick` | Jump high / long | — |
| `JumpingSumoTurnBrick` | Flip (turn over) | — |
| `JumpingSumoAnimationsBrick` | Play an animation | `<animationName>` enum (e.g. `SPIN`) |
| `JumpingSumoSoundBrick` | Play a sound at a volume | `JUMPING_SUMO_VOLUME` + `<soundName>` enum (e.g. `DEFAULT`) |
| `JumpingSumoNoSoundBrick` | Mute sounds | — |
| `JumpingSumoTakingPictureBrick` | Take a picture | — |

## Phiro

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `PhiroMotorMoveForwardBrick`, `PhiroMotorMoveBackwardBrick` | Move a motor at a speed | `PHIRO_SPEED` + `<motor>` enum (e.g. `MOTOR_LEFT`, `MOTOR_BOTH`) |
| `PhiroMotorStopBrick` | Stop a motor | `<motor>` enum |
| `PhiroPlayToneBrick` | Play a tone for a duration | `PHIRO_DURATION_IN_SECONDS` + `<tone>` enum (e.g. `DO`) |
| `PhiroRGBLightBrick` | Set light color (RGB) | `PHIRO_LIGHT_RED`, `PHIRO_LIGHT_GREEN`, `PHIRO_LIGHT_BLUE` + `<eye>` enum (`LEFT`/`RIGHT`/`BOTH`) |
| `PhiroIfLogicBeginBrick` | If a Phiro sensor is activated (if/else composite) | `<sensorSpinnerPosition>` int + `<elseBranchBricks>` / `<ifBranchBricks>` |

## Arduino

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `ArduinoSendDigitalValueBrick` | Set a digital pin to a value | `ARDUINO_DIGITAL_PIN_NUMBER`, `ARDUINO_DIGITAL_PIN_VALUE` |
| `ArduinoSendPWMValueBrick` | Set a PWM~ pin to a value | `ARDUINO_ANALOG_PIN_NUMBER`, `ARDUINO_ANALOG_PIN_VALUE` |

## Raspberry Pi

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `RaspiSendDigitalValueBrick` | Set a GPIO pin to a value | `RASPI_DIGITAL_PIN_NUMBER`, `RASPI_DIGITAL_PIN_VALUE` |
| `RaspiPwmBrick` | Set a PWM pin (frequency, duty cycle) | `RASPI_DIGITAL_PIN_NUMBER`, `RASPI_PWM_FREQUENCY`, `RASPI_PWM_PERCENTAGE` |
| `RaspiIfLogicBeginBrick` | If a pin is high (if/else composite) | `IF_CONDITION` + `<elseBranchBricks>` / `<ifBranchBricks>` |

## NFC

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `SetNfcTagBrick` | Write a message to the next scanned tag | `NFC_NDEF_MESSAGE` + `<nfcTagNdefType>` int |

## Embroidery

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `StitchBrick` | Stitch at the current position | — |
| `RunningStitchBrick` | Start running stitch with a length | `EMBROIDERY_LENGTH` |
| `ZigZagStitchBrick` | Start zigzag stitch with length and width | `ZIGZAG_EMBROIDERY_LENGTH`, `ZIGZAG_EMBROIDERY_WIDTH` |
| `TripleStitchBrick` | Start triple stitch with a length | `EMBROIDERY_LENGTH` |
| `StopRunningStitchBrick` | Stop the current stitch | — |
| `SetThreadColorBrick` | Set thread color | `THREAD_COLOR` |
| `SewUpBrick` | Sew up (knot the thread) | — |
| `WriteEmbroideryToFileBrick` | Write embroidery data to a file | `WRITE_FILENAME` |

## Plotter

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `StartPlotBrick` / `StopPlotBrick` | Start / stop plotting | — |
| `SavePlotBrick` | Save plot as SVG | `WRITE_FILENAME` |
| `SharePlotBrick` | Share plot as SVG | `WRITE_FILENAME` |

## Laser Cutter

| Brick | What it does | Serialized fields / formula categories |
|---|---|---|
| `StartCutBrick` / `StopCutBrick` | Start / stop cutting | — |
| `StartEngraveBrick` / `StopEngraveBrick` | Start / stop engraving | — |
| `SaveLaserBrick` | Save laser data to SVG | `WRITE_FILENAME` |
| `ShareLaserBrick` | Share laser data as SVG | `WRITE_FILENAME` |
