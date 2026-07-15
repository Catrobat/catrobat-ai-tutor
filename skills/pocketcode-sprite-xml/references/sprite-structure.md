# Sprite structure: `<object>` and its children

The `<object>` element and its asset/data children: looks, sounds, NFC tags,
variables, lists, XStream references, and sprite-embedding bricks.

## Top level

```xml
<object type="Sprite" name="SpriteName">
  <lookList>...</lookList>
  <soundList/>
  <scriptList>...</scriptList>
  <nfcTagList/>
  <userVariables/>
  <userLists/>
  <userDefinedBrickList/>
</object>
```

| Field | Description |
|---|---|
| `type` | `"Sprite"` normally; `"GroupSprite"` / `"GroupItemSprite"` for editor groups. Legacy `"SingleSprite"` maps to `Sprite` on load. |
| `name` | Display name of the sprite |
| children | Always in exactly this order; empty lists as self-closing tags |

## lookList

```xml
<lookList>
  <look fileName="1000103316.jpg" name="Man1">
    <isWebRequest>false</isWebRequest>
    <valid>true</valid>
  </look>
</lookList>
```

`fileName` = file on disk in the project directory; `name` = editor display
name (both are XML *attributes*). `isWebRequest` = image came from a URL;
`valid` = file exists and is usable.

## soundList

Same attribute pattern as looks, one body field:

```xml
<soundList>
  <sound fileName="999.mp3" name="Music">
    <midiFile>false</midiFile>
  </sound>
</soundList>
```

`midiFile` is `true` for synthesized MIDI sounds.

## nfcTagList

```xml
<nfcTagList>
  <nfcTag>
    <name>MyTag</name>
    <uid>0x04a224ba</uid>
  </nfcTag>
</nfcTagList>
```

Usually `<nfcTagList/>`. Referenced by `WhenNfcScript` when `matchAll` is `false`.

## userVariable (in bricks and at sprite level)

`UserVariable` uses a custom converter — note the wrapper attributes and
`<default>` nesting:

```xml
<userVariable type="UserVariable" serialization="custom">
  <userVariable>
    <default>
      <initialIndex>-1</initialIndex>
      <deviceValueKey>uuid</deviceValueKey>
      <name>variableName</name>
    </default>
  </userVariable>
</userVariable>
```

A legacy form without the `type` attribute (just the name as text) still
parses, but never generate it.

## userList (in bricks and at sprite level)

`UserList` is **plain** — different shape than `UserVariable`:

```xml
<userList>
  <deviceListKey>uuid</deviceListKey>
  <initialIndex>-1</initialIndex>
  <name>listName</name>
</userList>
```

Key is `deviceListKey` (not `deviceValueKey`); no custom wrapper, no `<default>`.

## Sprite-level userVariables / userLists

Because `scriptList` serializes before `userVariables`, a variable already
inlined inside a brick appears at sprite level as a **reference back into the
script** — this is normal output, don't "fix" it:

```xml
<userVariables>
  <userVariable reference="../../scriptList/script/brickList/brick/userVariable"/>
</userVariables>
```

## XStream references

Relative XPath-style `reference` attributes avoid duplicating objects. `[n]`
indices are 1-based; a tag name without an index means the first match.

```xml
<look reference="../../../../../lookList/look"/>
<userVariable reference="../../brick[2]/userVariable"/>
```

**Rule:** preserve `reference` attributes exactly. Inline a full block only at
the first occurrence of the object, or when the brick is new.

## Sprite-reference bricks embed the whole target sprite

`PointToBrick` (`pointedObject`), `GoToBrick` (`destinationSprite`), and
`CloneBrick` (`objectToClone`) hold plain sprite fields. In standalone sprite
XML the **entire target sprite is inlined**:

```xml
<brick type="PointToBrick">
  <brickId>uuid</brickId>
  <commentedOut>false</commentedOut>
  <pointedObject type="Sprite" name="Enemy">
    <lookList/>
    <soundList/>
    <scriptList/>
    <nfcTagList/>
    <userVariables/>
    <userLists/>
    <userDefinedBrickList/>
  </pointedObject>
</brick>
```

A second brick referencing the same sprite gets a reference:

```xml
<objectToClone reference="../../brick[4]/pointedObject"/>
```

When generating: omit the sprite field (`CloneBrick` then clones itself;
`GoToBrick` uses `<spinnerSelection>`: 80 = touch position, 81 = random
position, 82 = other sprite) or preserve the existing reference/inline block.
Never invent a nested sprite.
