# Complete sprite XML examples

All "valid" examples below were verified to parse against the project's actual
XStream configuration (`XstreamSerializer.getXstream()` — the same converters
and aliases used by `getSpriteFromXmlStringOrThrow()`). UUIDs shown are real
ones from serializer output — always generate fresh UUID v4 values for new
bricks/scripts.

## 1. Minimal sprite — one StartScript, one brick

```xml
<object type="Sprite" name="Hello">
  <lookList/>
  <soundList/>
  <scriptList>
    <script type="StartScript" posX="0.0" posY="0.0">
      <brickList>
        <brick type="SetXBrick">
          <brickId>9c1c1de4-6b51-4768-a32b-9a6ae4e5e1a1</brickId>
          <commentedOut>false</commentedOut>
          <formulaList>
            <formula category="X_POSITION">
              <additionalChildren/>
              <type>NUMBER</type>
              <value>100</value>
            </formula>
          </formulaList>
        </brick>
      </brickList>
      <commentedOut>false</commentedOut>
      <scriptId>5b2a7c1e-3d4f-4a6b-8c9d-0e1f2a3b4c5d</scriptId>
    </script>
  </scriptList>
  <nfcTagList/>
  <userVariables/>
  <userLists/>
  <userDefinedBrickList/>
</object>
```

## 2. Looks, sounds, variables, references, sprite-reference bricks

Verbatim serializer output: shows look/sound list entries, the userVariable
custom wrapper, second-use references, an inlined target sprite
(`pointedObject`), and a reference to it (`objectToClone`). Note the
sprite-level `<userVariables>` referencing back into the script.

```xml
<object type="Sprite" name="Hero">
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
        <brick type="SetVariableBrick">
          <brickId>20f0c305-3bfc-4f53-85ba-a3cc87d905ee</brickId>
          <commentedOut>false</commentedOut>
          <formulaList>
            <formula category="VARIABLE">
              <additionalChildren/>
              <type>NUMBER</type>
              <value>0</value>
            </formula>
          </formulaList>
          <userVariable type="UserVariable" serialization="custom">
            <userVariable>
              <default>
                <initialIndex>-1</initialIndex>
                <deviceValueKey>06bcb4cb-c281-4492-95c7-91f48a17cd5c</deviceValueKey>
                <name>score</name>
              </default>
            </userVariable>
          </userVariable>
        </brick>
        <brick type="SetVariableBrick">
          <brickId>d8ba7544-ec76-44c8-8e49-06186bdcb0dc</brickId>
          <commentedOut>false</commentedOut>
          <formulaList>
            <formula category="VARIABLE">
              <additionalChildren/>
              <type>NUMBER</type>
              <value>1</value>
            </formula>
          </formulaList>
          <userVariable reference="../../brick/userVariable"/>
        </brick>
        <brick type="SetLookBrick">
          <brickId>fccd479e-0eeb-4b88-99b3-c4cb864645a7</brickId>
          <commentedOut>false</commentedOut>
          <look reference="../../../../../lookList/look"/>
        </brick>
        <brick type="PointToBrick">
          <brickId>3c8cd8f5-5f48-449f-a8ea-6e8a8d9da1fb</brickId>
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
        <brick type="CloneBrick">
          <brickId>dcc43371-0516-4480-8051-79f3ada65c18</brickId>
          <commentedOut>false</commentedOut>
          <objectToClone reference="../../brick[4]/pointedObject"/>
        </brick>
      </brickList>
      <commentedOut>false</commentedOut>
      <scriptId>a2b52b04-c737-442b-8351-51ef55e7cf80</scriptId>
    </script>
  </scriptList>
  <nfcTagList/>
  <userVariables>
    <userVariable reference="../../scriptList/script/brickList/brick/userVariable"/>
  </userVariables>
  <userLists/>
  <userDefinedBrickList/>
</object>
```

## 3. Composite-heavy: WhenScript with forever / if-else / repeat

```xml
<object type="Sprite" name="Game">
  <lookList/>
  <soundList/>
  <scriptList>
    <script type="WhenScript" posX="0.0" posY="0.0">
      <brickList>
        <brick type="ForeverBrick">
          <brickId>0a98a1c2-1111-4222-8333-444455556666</brickId>
          <commentedOut>false</commentedOut>
          <loopBricks>
            <brick type="IfLogicBeginBrick">
              <brickId>1b98a1c2-1111-4222-8333-444455556666</brickId>
              <commentedOut>false</commentedOut>
              <formulaList>
                <formula category="IF_CONDITION">
                  <additionalChildren/>
                  <leftChild>
                    <additionalChildren/>
                    <type>SENSOR</type>
                    <value>OBJECT_X</value>
                  </leftChild>
                  <rightChild>
                    <additionalChildren/>
                    <type>NUMBER</type>
                    <value>200</value>
                  </rightChild>
                  <type>OPERATOR</type>
                  <value>GREATER_THAN</value>
                </formula>
              </formulaList>
              <elseBranchBricks>
                <brick type="RepeatBrick">
                  <brickId>2c98a1c2-1111-4222-8333-444455556666</brickId>
                  <commentedOut>false</commentedOut>
                  <formulaList>
                    <formula category="TIMES_TO_REPEAT">
                      <additionalChildren/>
                      <type>NUMBER</type>
                      <value>10</value>
                    </formula>
                  </formulaList>
                  <loopBricks>
                    <brick type="MoveNStepsBrick">
                      <brickId>3d98a1c2-1111-4222-8333-444455556666</brickId>
                      <commentedOut>false</commentedOut>
                      <formulaList>
                        <formula category="STEPS">
                          <additionalChildren/>
                          <type>NUMBER</type>
                          <value>5</value>
                        </formula>
                      </formulaList>
                    </brick>
                  </loopBricks>
                </brick>
              </elseBranchBricks>
              <ifBranchBricks>
                <brick type="SetXBrick">
                  <brickId>4e98a1c2-1111-4222-8333-444455556666</brickId>
                  <commentedOut>false</commentedOut>
                  <formulaList>
                    <formula category="X_POSITION">
                      <additionalChildren/>
                      <rightChild>
                        <additionalChildren/>
                        <type>NUMBER</type>
                        <value>200</value>
                      </rightChild>
                      <type>OPERATOR</type>
                      <value>MINUS</value>
                    </formula>
                  </formulaList>
                </brick>
              </ifBranchBricks>
            </brick>
          </loopBricks>
        </brick>
      </brickList>
      <commentedOut>false</commentedOut>
      <scriptId>5f98a1c2-1111-4222-8333-444455556666</scriptId>
    </script>
  </scriptList>
  <nfcTagList/>
  <userVariables/>
  <userLists/>
  <userDefinedBrickList/>
</object>
```

(The `SetXBrick` formula is unary negation: `MINUS` with only a `rightChild` = `-200`.)

## ❌ Invalid patterns — never generate these

Flat composite siblings (parses, crashes at runtime):

```xml
<brick type="IfLogicBeginBrick"> ... </brick>
<brick type="SetLookBrick"> ... </brick>
<brick type="IfLogicElseBrick"/>
<brick type="SetLookBrick"> ... </brick>
<brick type="IfLogicEndBrick"/>
```

```xml
<brick type="RepeatBrick"> ... </brick>
<brick type="MoveNStepsBrick"> ... </brick>
<brick type="LoopEndBrick"/>
```

`formulaList` on a script (fails to import — scripts use `formulaMap`):

```xml
<script type="WhenConditionScript">
  ...
  <formulaList>   <!-- ❌ "No such field ...Script.formulaList" -->
    <formula category="IF_CONDITION">...</formula>
  </formulaList>
</script>
```

Wrapper/event bricks inside a brickList (events are `<script>` elements):

```xml
<brickList>
  <brick type="WhenStartedBrick"/>   <!-- ❌ never in brickList -->
</brickList>
```
