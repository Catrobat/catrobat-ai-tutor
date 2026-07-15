# Scripts: event handlers and their XML

The 13 script types, their base structure, per-type extra fields, and the
script-level `formulaMap`.

## Base structure

```xml
<script type="ScriptType" posX="0.0" posY="0.0">
  <brickList>
    <brick type="...">...</brick>
  </brickList>
  <commentedOut>false</commentedOut>
  <scriptId>uuid-v4</scriptId>
  <!-- script-type-specific extras, omitted while null -->
</script>
```

| Field | Description |
|---|---|
| `type` | Script trigger type (table below) — the only legal values |
| `posX`, `posY` | Editor canvas position, cosmetic — leave unchanged |
| `brickList` | Ordered bricks to execute (`<brickList/>` when empty) |
| `commentedOut` | `true` disables the whole script |
| `scriptId` | UUID — never change; new scripts get a fresh UUID v4 |

## The 13 script types

| `type` | Trigger | Extra XML fields (omitted while null) |
|---|---|---|
| `StartScript` | Play button pressed | — |
| `WhenScript` | Sprite tapped | — |
| `WhenTouchDownScript` | Stage tapped anywhere | — |
| `WhenClonedScript` | This sprite starts as a clone | — |
| `BroadcastScript` | Broadcast message received | `<receivedMessage>` string |
| `WhenConditionScript` | Formula condition becomes true | `<formulaMap>` (see below) |
| `WhenBackgroundChangesScript` | Background switches to a look | `<look>` inline or reference into `lookList` |
| `WhenBounceOffScript` | Bounces off another sprite/edge | `<spriteToBounceOffName>` string (empty element when "") |
| `WhenGamepadButtonScript` | Cast/gamepad button pressed | `<action>` string |
| `WhenNfcScript` | NFC tag scanned | `<matchAll>` boolean (default `true` = any tag) + `<nfcTag>` reference when `matchAll=false` |
| `UserDefinedScript` | Body of a custom brick | `<screenRefresh>` boolean (always present) + `<userDefinedBrickID>` UUID + `<userDefinedBrickInputs>` once linked |
| `RaspiInterruptScript` | Raspberry Pi GPIO interrupt | `<pin>` and `<eventValue>` strings |
| `EmptyScript` | Placeholder, no trigger | — |

## Script-level formulas: `formulaMap`, not `formulaList`

A script's own trigger formula uses `<formulaMap>` as a direct child of
`<script>`, after `<scriptId>`. The inner `<formula category="...">` nodes are
identical to brick formulas — only the wrapper element differs. Using
`<formulaList>` on a script fails to import
("No such field …Script.formulaList").

```xml
<script type="WhenConditionScript" posX="0.0" posY="0.0">
  <brickList>...</brickList>
  <commentedOut>false</commentedOut>
  <scriptId>uuid</scriptId>
  <formulaMap>
    <formula category="IF_CONDITION">
      <additionalChildren/>
      <type>NUMBER</type>
      <value>1</value>
    </formula>
  </formulaMap>
</script>
```

## Events are scripts, not bricks

The editor shows event headers as bricks (`WhenStartedBrick`,
`BroadcastReceiverBrick`, `WhenBrick`, …) but those wrapper types never appear
in serialized `<brickList>`s — the event is the `<script>` element itself.
