# Composite bricks and custom (user-defined) bricks

Nesting rules for if/else and loops, the `ParameterizedBrick` exception,
`userDataList`, and the three linked parts of a custom brick.

## Composite bricks: bodies nest, end bricks don't exist

In the editor, IF/ELSE and loops look like several bricks (begin, else, end).
**In XML they are one brick** whose body bricks nest in dedicated list elements.
This is the single most common cause of crashes in generated XML.

All 10 composite types:

| Composite brick | Body nests in | Extra serialized fields |
|---|---|---|
| `IfLogicBeginBrick` | `<elseBranchBricks>` + `<ifBranchBricks>` — else **first** | — |
| `RaspiIfLogicBeginBrick` | same (subclass of `IfLogicBeginBrick`) | — |
| `PhiroIfLogicBeginBrick` | same branch lists | `<sensorSpinnerPosition>` int |
| `IfThenLogicBeginBrick` | `<ifBranchBricks>` only | — |
| `ForeverBrick` | `<loopBricks>` | — |
| `RepeatBrick` | `<loopBricks>` | `TIMES_TO_REPEAT` formula |
| `RepeatUntilBrick` | `<loopBricks>` | `REPEAT_UNTIL_CONDITION` formula |
| `ForVariableFromToBrick` | `<loopBricks>` | `FOR_LOOP_FROM`, `FOR_LOOP_TO` + `<userVariable>` |
| `ForItemInUserListBrick` | `<loopBricks>` | `<userDataList>` (see below) |
| `ParameterizedBrick` | `<loopBricks>` | `<userLists>` + serialized `<endBrick>` (see below) |

### ❌ Invalid IF/ELSE (flat siblings — parses, crashes at runtime)

```xml
<brick type="IfLogicBeginBrick"> ... </brick>
<brick type="SetLookBrick"> ... </brick>
<brick type="IfLogicElseBrick"/>
<brick type="SetLookBrick"> ... </brick>
<brick type="IfLogicEndBrick"/>
```

### ✅ Valid IF/ELSE (nested branches; else branch serialized first)

```xml
<brick type="IfLogicBeginBrick">
  <brickId>uuid</brickId>
  <commentedOut>false</commentedOut>
  <formulaList>
    <formula category="IF_CONDITION">...</formula>
  </formulaList>
  <elseBranchBricks>
    <brick type="SetLookBrick">...</brick>
  </elseBranchBricks>
  <ifBranchBricks>
    <brick type="SetLookBrick">...</brick>
  </ifBranchBricks>
</brick>
```

### ❌ Invalid loop / ✅ valid loop

```xml
<!-- ❌ -->
<brick type="RepeatBrick"> ... </brick>
<brick type="MoveNStepsBrick"> ... </brick>
<brick type="LoopEndBrick"/>

<!-- ✅ -->
<brick type="RepeatBrick">
  <brickId>uuid</brickId>
  <commentedOut>false</commentedOut>
  <formulaList>
    <formula category="TIMES_TO_REPEAT">...</formula>
  </formulaList>
  <loopBricks>
    <brick type="MoveNStepsBrick">...</brick>
  </loopBricks>
</brick>
```

### Rules

- Empty branches/bodies are self-closing: `<ifBranchBricks/>`, `<loopBricks/>`.
- On `IfLogicBeginBrick`, `<elseBranchBricks>` is always present (self-closing
  when there's no else body).
- `IfThenLogicBeginBrick` (if without else) has only `<ifBranchBricks>` and no
  end brick.
- Never emit `IfLogicElseBrick`, `IfLogicEndBrick`, `IfThenLogicEndBrick`,
  `LoopEndBrick`, or `LoopEndlessBrick` as sibling bricks — they are
  parse-only legacy types.

### Exception: ParameterizedBrick serializes its end brick

Uniquely, `ParameterizedBrick`'s end brick is a nested `<endBrick>` *element*
(never a sibling), carrying an `ASSERT_LOOP_ACTUAL` formula:

```xml
<brick type="ParameterizedBrick">
  <brickId>uuid</brickId>
  <commentedOut>false</commentedOut>
  <userLists/>
  <endBrick type="ParameterizedEndBrick">
    <brickId>uuid</brickId>
    <commentedOut>false</commentedOut>
    <formulaList>
      <formula category="ASSERT_LOOP_ACTUAL">
        <additionalChildren/>
        <type>NUMBER</type>
        <value>0</value>
      </formula>
    </formulaList>
  </endBrick>
  <loopBricks>
    ...
  </loopBricks>
</brick>
```

## userDataList (UserDataBrick)

Bricks holding *both* a variable and a list (`ForItemInUserListBrick`,
`AssertUserListsBrick`) use a `<userDataList>` map. Variable entries get the
custom wrapper, list entries are plain:

```xml
<brick type="ForItemInUserListBrick">
  <brickId>uuid</brickId>
  <commentedOut>false</commentedOut>
  <formulaList/>
  <userDataList>
    <userData category="FOR_ITEM_IN_USERLIST_VARIABLE" type="UserVariable" serialization="custom">
      <userVariable>
        <default>
          <initialIndex>-1</initialIndex>
          <deviceValueKey>uuid</deviceValueKey>
          <name>myVar</name>
        </default>
      </userVariable>
    </userData>
    <userData category="FOR_ITEM_IN_USERLIST_LIST">
      <deviceListKey>uuid</deviceListKey>
      <initialIndex>-1</initialIndex>
      <name>myList</name>
    </userData>
  </userDataList>
  <loopBricks/>
</brick>
```

Categories: `FOR_ITEM_IN_USERLIST_VARIABLE`, `FOR_ITEM_IN_USERLIST_LIST`,
`ASSERT_LISTS_EXPECTED`, `ASSERT_LISTS_ACTUAL`.

## UserDefinedBrick (custom bricks)

Three parts linked by the same `userDefinedBrickID` UUID:

1. **Definition** in the sprite's `<userDefinedBrickList>`
   (`<isCallingBrick>false</isCallingBrick>`).
2. **Body**: a `UserDefinedScript` in `<scriptList>` with matching
   `<userDefinedBrickID>`.
3. **Calls** inside normal `brickList`s (`<isCallingBrick>true</isCallingBrick>`).

Definition with one label ("jump") and one input ("height"):

```xml
<brick type="UserDefinedBrick">
  <brickId>uuid</brickId>
  <commentedOut>false</commentedOut>
  <formulaList/>
  <isCallingBrick>false</isCallingBrick>
  <userDefinedBrickDataList>
    <userDefinedBrickLabel>
      <type>LABEL</type>
      <label>jump</label>
    </userDefinedBrickLabel>
    <userDefinedBrickInput>
      <type>INPUT</type>
      <initialIndex>-1</initialIndex>
      <input>
        <input>height</input>
      </input>
    </userDefinedBrickInput>
  </userDefinedBrickDataList>
  <userDefinedBrickID>uuid</userDefinedBrickID>
</brick>
```

Notes:

- The field name really is `isCallingBrick` (not `callingBrick`).
- `<userDefinedBrickInput>` double-nests the name:
  `<input><input>height</input></input>`.
- **Formulas on calling bricks are keyed by `input=`, not `category=`**:
  `<formula input="height">…</formula>`.

The body script:

```xml
<script type="UserDefinedScript" posX="0.0" posY="0.0">
  <brickList>...</brickList>
  <commentedOut>false</commentedOut>
  <scriptId>uuid</scriptId>
  <screenRefresh>true</screenRefresh>
  <userDefinedBrickID>same-uuid</userDefinedBrickID>
</script>
```

Inside the body, read a parameter with a formula node of type
`USER_DEFINED_BRICK_INPUT` whose `value` is the parameter name.
