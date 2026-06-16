---
name: pocketcode-sprite-xml
description: Authoritative reference for the Catroid/Pocket Code sprite XML format produced by XstreamSerializer.getXmlAsStringFromSprite and parsed by getSpriteFromXmlStringOrThrow. Use when generating sprite XML test fixtures, writing or debugging AI-tutor sprite responses, validating object/script/brick/formula XML elements, designing prompts that emit sprite XML, or diagnosing AiTutorSpriteValidator and XStream parse failures.
---

# PocketCode Sprite XML

Generate and edit sprite XML that imports cleanly into Pocket Code (Catroid).
This file holds the load-bearing rules; full inventories live in `references/`
(see the pointer table at the bottom). All XML structures were verified against
the project's actual serializer output, and all name catalogs (brick types,
formula categories, operators, functions, sensors) against its source code.

## Sprite skeleton (canonical child order)

```xml
<object type="Sprite" name="SpriteName">
  <lookList>
    <look fileName="12345.png" name="Look1">
      <isWebRequest>false</isWebRequest>
      <valid>true</valid>
    </look>
  </lookList>
  <soundList>
    <sound fileName="999.mp3" name="Music">
      <midiFile>false</midiFile>
    </sound>
  </soundList>
  <scriptList>
    <script type="StartScript" posX="0.0" posY="0.0">
      <brickList>
        <brick type="SetXBrick">
          <brickId>uuid-v4</brickId>
          <commentedOut>false</commentedOut>
          <formulaList>
            <formula category="X_POSITION">
              <additionalChildren/>
              <type>NUMBER</type>
              <value>0</value>
            </formula>
          </formulaList>
        </brick>
      </brickList>
      <commentedOut>false</commentedOut>
      <scriptId>uuid-v4</scriptId>
    </script>
  </scriptList>
  <nfcTagList/>
  <userVariables/>
  <userLists/>
  <userDefinedBrickList/>
</object>
```

`type` is the sprite class name: `Sprite` (normal), `GroupSprite`, `GroupItemSprite`.

## Three serializer-wide rules

1. **Sprite children appear in exactly the order shown above** (fixed by
   annotation). Everything else serializes **superclass fields first, then
   alphabetically within each class** — e.g. on a brick: `brickId`,
   `commentedOut` (base), then `formulaList`, then subclass fields A→Z.
2. **`null` fields are omitted entirely** — never write an empty element for an
   unset field. **Empty lists are kept** as self-closing tags: `<lookList/>`,
   `<brickList/>`, `<formulaList/>`, `<loopBricks/>`.
3. **Scripts use `<formulaMap>`, bricks use `<formulaList>`.** A
   `WhenConditionScript`'s trigger condition lives in `<formulaMap>` directly
   under `<script>`; every brick formula lives in `<formulaList>`. Mixing these
   up fails import.

## Composite bricks — the #1 crash cause

IF/ELSE and loops are **one brick with nested children**, never flat siblings:

```xml
<!-- ✅ valid -->
<brick type="IfLogicBeginBrick">
  <brickId>uuid</brickId>
  <commentedOut>false</commentedOut>
  <formulaList>
    <formula category="IF_CONDITION">...</formula>
  </formulaList>
  <elseBranchBricks>   <!-- else comes FIRST (alphabetical) -->
    <brick type="...">...</brick>
  </elseBranchBricks>
  <ifBranchBricks>
    <brick type="...">...</brick>
  </ifBranchBricks>
</brick>

<!-- ❌ invalid: parses but crashes at runtime -->
<brick type="IfLogicBeginBrick">...</brick>
<brick type="SetLookBrick">...</brick>
<brick type="IfLogicElseBrick"/>
<brick type="IfLogicEndBrick"/>
```

- Loops (`ForeverBrick`, `RepeatBrick`, `RepeatUntilBrick`,
  `ForVariableFromToBrick`, `ForItemInUserListBrick`, `ParameterizedBrick`)
  nest their body in `<loopBricks>`.
- If/else (`IfLogicBeginBrick`, `RaspiIfLogicBeginBrick`,
  `PhiroIfLogicBeginBrick`) nest in `<elseBranchBricks>` + `<ifBranchBricks>`;
  `IfThenLogicBeginBrick` has only `<ifBranchBricks>`.
- **Never emit** `IfLogicElseBrick`, `IfLogicEndBrick`, `IfThenLogicEndBrick`,
  `LoopEndBrick`, or `LoopEndlessBrick` — they exist only so legacy files parse.
- Exception: `ParameterizedBrick` serializes a nested `<endBrick
  type="ParameterizedEndBrick">` *element* (never a sibling brick).

## Formula cheat sheet

A formula is a binary expression tree. Node types: `NUMBER`, `STRING`,
`OPERATOR`, `FUNCTION`, `SENSOR`, `USER_VARIABLE`, `USER_LIST`,
`USER_DEFINED_BRICK_INPUT`, `BRACKET`, `COLLISION_FORMULA`.

```xml
<formula category="X_POSITION_CHANGE">  <!-- category = BrickField slot name -->
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

- Binary operators need both children; unary minus / `LOGICAL_NOT` omit `<leftChild>`.
- `<additionalChildren/>` is present in **every** node — empty except for
  three-argument functions (`JOIN3`, `IF_THEN_ELSE`), where it wraps the third
  argument in `<org.catrobat.catroid.formulaeditor.FormulaElement>` elements.
- `COLLISION_FORMULA` is a leaf whose `value` is the *other sprite's name*.
- On user-defined (custom) brick calls, formulas are keyed `input="paramName"`
  instead of `category="..."`.

## Rules for editing existing sprite XML

1. Never change a `brickId` or `scriptId`; new bricks get a fresh UUID v4.
2. Preserve `commentedOut` values unless asked to enable/disable.
3. Preserve `reference="..."` attributes (looks, sounds, variables, sprites)
   exactly; inline a full block only for the first occurrence or a new object.
4. Formula trees must be complete — correct child count for every operator/function.
5. `posX`/`posY` on scripts are cosmetic; leave them unchanged.
6. Return only the modified `<object>` element, no `code.xml` wrapper or XML declaration.
7. Use only verified `BrickField` category names, brick types, and script types
   (see references) — never invent names.
8. For `PointToBrick`/`GoToBrick`/`CloneBrick`, keep existing sprite
   references/inline blocks; never invent a nested sprite. `CloneBrick` without
   `<objectToClone>` clones itself.
9. Script-wrapper bricks (`WhenStartedBrick`, `BroadcastReceiverBrick`, …)
   never appear in a `<brickList>` — events are `<script>` elements.

## How this XML is consumed (Catroid repo)

| Step | Where |
|---|---|
| Sprite → XML | `catroid/src/main/java/org/catrobat/catroid/io/XstreamSerializer.java` — `getXmlAsStringFromSprite()` |
| XML → Sprite | same file — `getSpriteFromXmlStringOrThrow()` |
| Validation | `catroid/src/main/java/org/catrobat/catroid/ui/aiassist/validation/AiTutorSpriteValidator.kt` — 3 passes: `setParents()`, formula completeness per brick field, `getView()` dry-run |
| Apply | `catroid/src/main/java/org/catrobat/catroid/ui/recyclerview/fragment/ScriptFragment.java` — `applyProjectFromAiTutor()` |
| Legal type names | the `xstream.alias(...)` registry in `XstreamSerializer.java` (219 bricks, 13 scripts) |

Typical validator failures: a `FormulaBrick` missing a required `category`
entry ("SetXBrick is missing a required value (X_POSITION)"), broken parent
chains from flat composite siblings, unknown brick `type` (fails already at parse).

## Detailed references

| Need | Read |
|---|---|
| `<object>` children: looks, sounds, NFC tags, variables, lists, XStream references, sprite-embedding bricks | `references/sprite-structure.md` |
| All 13 script types and their extra fields, `formulaMap` | `references/scripts.md` |
| Operators (15), functions (53), sensors (165), formula tree edge cases | `references/formulas.md` |
| Every standard brick with description + serialized fields (motion, looks, sound, control, data, pen, testing…) | `references/bricks-core.md` |
| Hardware bricks (Lego, Drone, Sumo, Phiro, Arduino, Raspberry Pi, NFC, embroidery, plotter, laser) | `references/bricks-hardware.md` |
| Composite nesting in depth, `ParameterizedBrick`, custom bricks (`UserDefinedBrick`/`UserDefinedScript`), `userDataList` | `references/composite-and-userdefined.md` |
| Complete, import-verified sprite XML examples (valid + invalid) | `references/examples.md` |
