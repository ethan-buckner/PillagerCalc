# PillagerCalc
A java program to generate lethal combos for the digital card game Hearthstone using Spectral Pillager.  Provided with a starting hand, PillagerCalc will find a lethal combo if one exists.  If one does not exist, PillagerCalc with try to find combos with the same pieces but more mana.

# Instructions
When the program prompts you to enter your combo pieces, use this series of terms:

"scabbs" for Scabbs Cutterbutter, "tenwu" for Tenwu of the Red Smoke, "foxy" for Foxy Fraud, "dancer" for Mailbox dancer, "step" for Shadowstep, "shark" for Spirit of the Shark, "potion" for Potion of Illusion, "prep" for Preparation, and "coin" for the coin.

This is a temporary solution for providing input that should be improved in future versions.

PillagerCalc can understand cost modifications by placing the modified cost surrounded by parentheses immediately after the name of the combo piece, like so: "potion(3)".

Targeted effects are printed in the combo string in functional notation, where the target of the card is parenthesized next to the card like so: "tewnu(scabbs)".
