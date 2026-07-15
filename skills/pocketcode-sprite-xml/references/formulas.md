# Formulas: trees, operators, functions, sensors

Formula expression trees and the complete value sets: 15 operators,
53 functions, 165 sensors.

## Structure

Bricks store formulas in `<formulaList>`; each `<formula>`'s `category`
attribute is the `BrickField` slot it fills (one brick can have several).
A formula is a binary expression tree; each node:

```xml
<formula category="CATEGORY_NAME">
  <additionalChildren/>
  <type>ElementType</type>
  <value>value</value>
  <!-- optional leftChild / rightChild subtrees, same node shape -->
</formula>
```

### Element types

| `type` | Meaning | Example `value` |
|---|---|---|
| `NUMBER` | Literal number | `50`, `3.14` |
| `STRING` | Literal string | `hello` |
| `OPERATOR` | Math/logic operator | `PLUS` |
| `FUNCTION` | Built-in function | `SQRT` |
| `SENSOR` | Device/sprite sensor | `OBJECT_X` |
| `USER_VARIABLE` | User variable | the variable's name |
| `USER_LIST` | User list | the list's name |
| `USER_DEFINED_BRICK_INPUT` | Custom-brick parameter | the parameter name |
| `BRACKET` | Grouping parentheses | — |
| `COLLISION_FORMULA` | Collision with another sprite | the *other sprite's name* |

### Tree examples

Leaf number `50`:

```xml
<formula category="SIZE">
  <additionalChildren/>
  <type>NUMBER</type>
  <value>50</value>
</formula>
```

`x + 10`:

```xml
<formula category="X_POSITION_CHANGE">
  <additionalChildren/>
  <leftChild>
    <additionalChildren/>
    <type>SENSOR</type>
    <value>OBJECT_X</value>
  </leftChild>
  <rightChild>
    <additionalChildren/>
    <type>NUMBER</type>
    <value>10</value>
  </rightChild>
  <type>OPERATOR</type>
  <value>PLUS</value>
</formula>
```

Unary negation `-500` = `MINUS` with no `<leftChild>`. `LOGICAL_NOT` is also unary.

### Three-argument functions use `additionalChildren`

`<additionalChildren>` is present in every node and empty (`<additionalChildren/>`)
**except** for `JOIN3` and `IF_THEN_ELSE`, where it wraps the third argument in
fully-qualified `<org.catrobat.catroid.formulaeditor.FormulaElement>` elements:

```xml
<formula category="X_POSITION">
  <additionalChildren>
    <org.catrobat.catroid.formulaeditor.FormulaElement>
      <additionalChildren/>
      <type>STRING</type>
      <value>c</value>
    </org.catrobat.catroid.formulaeditor.FormulaElement>
  </additionalChildren>
  <leftChild>
    <additionalChildren/>
    <type>STRING</type>
    <value>a</value>
  </leftChild>
  <rightChild>
    <additionalChildren/>
    <type>STRING</type>
    <value>b</value>
  </rightChild>
  <type>FUNCTION</type>
  <value>JOIN3</value>
</formula>
```

For `IF_THEN_ELSE`: `leftChild` = condition, `rightChild` = then-value,
`additionalChildren[0]` = else-value. Note `<additionalChildren>` comes first
(alphabetical field order).

## Operators (15)

| Operator | Priority | Type | Meaning |
|---|---|---|---|
| `LOGICAL_OR` | 1 | logical | A or B |
| `LOGICAL_AND` | 2 | logical | A and B |
| `EQUAL` | 3 | logical | A = B |
| `NOT_EQUAL` | 4 | logical | A ≠ B |
| `SMALLER_OR_EQUAL` | 4 | logical | A ≤ B |
| `GREATER_OR_EQUAL` | 4 | logical | A ≥ B |
| `SMALLER_THAN` | 4 | logical | A < B |
| `GREATER_THAN` | 4 | logical | A > B |
| `LOGICAL_NOT` | 4 | logical | not A (unary) |
| `PLUS` | 5 | arithmetic | A + B |
| `MINUS` | 5 | arithmetic | A − B (or unary negation) |
| `MULT` | 6 | arithmetic | A × B |
| `DIVIDE` | 6 | arithmetic | A ÷ B |
| `MOD` | 6 | arithmetic | A mod B |
| `POW` | 7 | arithmetic | A ^ B |

## Functions (53)

Math: `SIN COS TAN ARCSIN ARCCOS ARCTAN ARCTAN2 LN LOG EXP POWER SQRT ABS
ROUND FLOOR CEIL MOD MAX MIN RAND PI`

Boolean: `TRUE FALSE IF_THEN_ELSE` (ternary — see additionalChildren above)

String: `LENGTH LETTER SUBTEXT JOIN JOIN3 REGEX CONTAINS`

List: `LIST_ITEM INDEX_OF_ITEM NUMBER_OF_ITEMS FLATTEN`

Touch: `MULTI_FINGER_X MULTI_FINGER_Y MULTI_FINGER_TOUCHED INDEX_CURRENT_TOUCH`

Color/vision: `COLLIDES_WITH_COLOR COLOR_TOUCHES_COLOR COLOR_AT_XY COLOR_EQUALS_COLOR`

Text recognition (ML): `TEXT_BLOCK_X TEXT_BLOCK_Y TEXT_BLOCK_SIZE
TEXT_BLOCK_FROM_CAMERA TEXT_BLOCK_LANGUAGE_FROM_CAMERA`

Object detection (ML): `ID_OF_DETECTED_OBJECT OBJECT_WITH_ID_VISIBLE`

Hardware: `ARDUINOANALOG ARDUINODIGITAL RASPIDIGITAL`

## Sensors (165)

Sprite/object (apply to the current sprite): `OBJECT_X OBJECT_Y OBJECT_SIZE
OBJECT_TRANSPARENCY OBJECT_BRIGHTNESS OBJECT_COLOR MOTION_DIRECTION
LOOK_DIRECTION OBJECT_LAYER OBJECT_DISTANCE_TO OBJECT_LOOK_NUMBER
OBJECT_LOOK_NAME OBJECT_NUMBER_OF_LOOKS OBJECT_BACKGROUND_NUMBER
OBJECT_BACKGROUND_NAME OBJECT_X_VELOCITY OBJECT_Y_VELOCITY
OBJECT_ANGULAR_VELOCITY COLLIDES_WITH_EDGE COLLIDES_WITH_FINGER NFC_TAG_ID
NFC_TAG_MESSAGE`

Device motion: `X_ACCELERATION Y_ACCELERATION Z_ACCELERATION COMPASS_DIRECTION
X_INCLINATION Y_INCLINATION LOUDNESS`

Location: `LATITUDE LONGITUDE LOCATION_ACCURACY ALTITUDE`

Time/date: `TIMER DATE_YEAR DATE_MONTH DATE_DAY DATE_WEEKDAY TIME_HOUR
TIME_MINUTE TIME_SECOND`

Stage: `STAGE_WIDTH STAGE_HEIGHT USER_LANGUAGE`

Touch: `FINGER_X FINGER_Y FINGER_TOUCHED LAST_FINGER_INDEX NUMBER_CURRENT_TOUCHES`

Face detection: `FACE_DETECTED FACE_SIZE FACE_X FACE_Y SECOND_FACE_DETECTED
SECOND_FACE_SIZE SECOND_FACE_X SECOND_FACE_Y`

Pose detection — each landmark has `_X` and `_Y` sensors (70 total):
`HEAD_TOP NECK NOSE LEFT_EYE_INNER LEFT_EYE_CENTER LEFT_EYE_OUTER
RIGHT_EYE_INNER RIGHT_EYE_CENTER RIGHT_EYE_OUTER LEFT_EAR RIGHT_EAR
MOUTH_LEFT_CORNER MOUTH_RIGHT_CORNER LEFT_SHOULDER RIGHT_SHOULDER LEFT_ELBOW
RIGHT_ELBOW LEFT_WRIST RIGHT_WRIST LEFT_PINKY RIGHT_PINKY LEFT_INDEX
RIGHT_INDEX LEFT_THUMB RIGHT_THUMB LEFT_HIP RIGHT_HIP LEFT_KNEE RIGHT_KNEE
LEFT_ANKLE RIGHT_ANKLE LEFT_HEEL RIGHT_HEEL LEFT_FOOT_INDEX RIGHT_FOOT_INDEX`
(e.g. `LEFT_WRIST_X`, `LEFT_WRIST_Y`)

Text recognition: `TEXT_FROM_CAMERA TEXT_BLOCKS_NUMBER TEXT_BLOCK_X
TEXT_BLOCK_Y TEXT_BLOCK_SIZE TEXT_BLOCK_FROM_CAMERA
TEXT_BLOCK_LANGUAGE_FROM_CAMERA SPEECH_RECOGNITION_LANGUAGE`

Gamepad: `GAMEPAD_A_PRESSED GAMEPAD_B_PRESSED GAMEPAD_UP_PRESSED
GAMEPAD_DOWN_PRESSED GAMEPAD_LEFT_PRESSED GAMEPAD_RIGHT_PRESSED`

Hardware robots: `NXT_SENSOR_1..4`, `EV3_SENSOR_1..4`,
`PHIRO_FRONT_LEFT/RIGHT PHIRO_SIDE_LEFT/RIGHT PHIRO_BOTTOM_LEFT/RIGHT`,
`DRONE_BATTERY_STATUS DRONE_EMERGENCY_STATE DRONE_FLYING DRONE_INITIALIZED
DRONE_USB_ACTIVE DRONE_USB_REMAINING_TIME DRONE_CAMERA_READY DRONE_RECORD_READY
DRONE_RECORDING DRONE_NUM_FRAMES`

## BrickField categories

The full set of legal `category` names is the `BrickField` enum (≈130 values
like `X_POSITION`, `IF_CONDITION`, `TIMES_TO_REPEAT`, `PHIRO_LIGHT_RED`…).
Per-brick categories are listed in `references/bricks-core.md` and
`references/bricks-hardware.md` — use only those listed for the brick at hand.
