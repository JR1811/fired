# Fired

## Content

Add new QoL features to campfires, candles, torches and  other fire-related Minecraft features

- use sticks on lit campfires to light up torches
- use torches on campfires and candles to light them up
- hot items burn users if they keep them without protection in their inventory
- etc.


## Configuration

No config mod is needed. Simply use the provided Gamerules, Tags and custom Datapack entries to adjust the features to your liking.

| Gamerule                | description                                                                                                                      |
| ----------------------- | -------------------------------------------------------------------------------------------------------------------------------- |
| igniteTorches           | toggle option to light up items into their ignited counterparts. This item change can be defined with the Datapack               |
| torchesIgnite           | toggle option to light up non-burning blocks using igniting items. Those items can be defined with the `fired:igniters` item tag |
| burningEntityCanIgnite  | toggle option to be able to light up items on other burning entities and self                                                    |
| hotItemIgniteEntities   | toggles all custom entries of items which will light users on fire with no protection                                            |
| hotItemBurnHealthSafety | adjusts the amount of health below which a user can't get ignited if they carry hot items                                        |
